import axiosInstance from "@/api/axiosInstance";
import { Badge, statusTone } from "@/components/common/Badge";
import { Card } from "@/components/common/Card";
import { DataTable, type Column } from "@/components/common/DataTable";
import { DashboardLayout } from "@/components/layout/DashboardLayout";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { ShoppingBag } from "lucide-react";
import { useState } from "react";
import { toast } from "sonner";

type PharmacyOrder = {
  orderId: number;
  patientName?: string | null;
  totalAmount: number;
  orderStatus: string;
  paymentMethod?: string | null;
  paymentStatus?: string | null;
  items: { medicineName: string; quantity: number }[];
};

// Must match the backend OrderStatus enum exactly
const STATUSES = [
  "CONFIRMED",
  "PROCESSING",
  "READY_FOR_DISPATCH",
  "DISPATCHED",
  "DELIVERED",
  "CANCELLED",
];

function errorMessage(err: unknown, fallback: string): string {
  const raw = (err as { response?: { data?: { message?: unknown } } })?.response?.data?.message;
  if (typeof raw !== "string") return fallback;
  const quoted = raw.match(/"([^"]+)"/);
  return quoted ? quoted[1] : raw;
}

const pretty = (s?: string | null) =>
  s ? s.replace(/_/g, " ").toLowerCase().replace(/^\w/, (c) => c.toUpperCase()) : "—";

export default function PharmacistOrders() {
  const queryClient = useQueryClient();
  const [tab, setTab] = useState<"available" | "mine">("available");

  const available = useQuery({
    queryKey: ["orders-available"],
    queryFn: async () => (await axiosInstance.get<PharmacyOrder[]>("/orders/available")).data,
    refetchInterval: 15000,
  });

  const mine = useQuery({
    queryKey: ["orders-assigned"],
    queryFn: async () => (await axiosInstance.get<PharmacyOrder[]>("/orders/assigned")).data,
  });

  const refresh = () => {
    queryClient.invalidateQueries({ queryKey: ["orders-available"] });
    queryClient.invalidateQueries({ queryKey: ["orders-assigned"] });
  };

  // POST /api/orders/{id}/accept
  const accept = useMutation({
    mutationFn: (id: number) => axiosInstance.post(`/orders/${id}/accept`),
    onSuccess: () => {
      toast.success("Order accepted. You can find it under My orders.");
      refresh();
      setTab("mine");
    },
    onError: (err) => {
      toast.error(errorMessage(err, "Couldn't accept the order"));
      refresh(); // somebody else may have taken it
    },
  });

  // PATCH /api/orders/{id}/status (own orders only)
  const changeStatus = useMutation({
    mutationFn: ({ id, status }: { id: number; status: string }) =>
      axiosInstance.patch(`/orders/${id}/status`, { status }),
    onSuccess: () => {
      toast.success("Order status updated");
      refresh();
    },
    onError: (err) => toast.error(errorMessage(err, "Couldn't update the order")),
  });

  const commonColumns: Column<PharmacyOrder>[] = [
    { header: "Order ID", accessor: (o) => <span className="font-mono text-ink-900">#{o.orderId}</span> },
    { header: "Patient", accessor: (o) => o.patientName ?? "—" },
    {
      header: "Medicines",
      accessor: (o) => {
        const first = o.items.slice(0, 2).map((l) => `${l.medicineName} ×${l.quantity}`).join(", ");
        const more = o.items.length > 2 ? ` +${o.items.length - 2} more` : "";
        return <span className="text-sm">{first}{more}</span>;
      },
    },
    { header: "Amount", accessor: (o) => `₹${o.totalAmount}` },
    {
      header: "Payment",
      accessor: (o) => (
        <span className="text-xs text-ink-700">
          {pretty(o.paymentMethod)} · {pretty(o.paymentStatus)}
        </span>
      ),
    },
  ];

  const availableColumns: Column<PharmacyOrder>[] = [
    ...commonColumns,
    {
      header: "Action",
      accessor: (o) => (
        <button
          type="button"
          disabled={accept.isPending}
          onClick={() => accept.mutate(o.orderId)}
          className="text-xs font-medium rounded-lg px-3 py-1.5 bg-primary-600 text-white hover:bg-primary-700 disabled:opacity-50"
        >
          {accept.isPending && accept.variables === o.orderId ? "Accepting..." : "Accept order"}
        </button>
      ),
    },
  ];

  const mineColumns: Column<PharmacyOrder>[] = [
    ...commonColumns,
    { header: "Status", accessor: (o) => <Badge tone={statusTone(o.orderStatus)}>{o.orderStatus}</Badge> },
    {
      header: "Update status",
      accessor: (o) => {
        const frozen = o.orderStatus === "CANCELLED" || o.orderStatus === "DELIVERED";
        return (
          <select
            value={o.orderStatus}
            disabled={frozen || changeStatus.isPending}
            onChange={(e) => changeStatus.mutate({ id: o.orderId, status: e.target.value })}
            className="text-xs border border-surface-border rounded-lg px-2 py-1.5 outline-none focus:border-primary-400 disabled:opacity-50"
          >
            {STATUSES.map((s) => (
              <option key={s} value={s}>{s}</option>
            ))}
          </select>
        );
      },
    },
  ];

  const availableCount = available.data?.length ?? 0;
  const mineCount = mine.data?.length ?? 0;

  const tabClass = (active: boolean) =>
    `text-sm font-medium rounded-full px-4 py-2 border transition-colors ${
      active
        ? "bg-primary-600 text-white border-primary-600"
        : "bg-white text-ink-700 border-surface-border hover:border-primary-400"
    }`;

  return (
    <DashboardLayout title="Orders">
      <Card>
        <div className="flex gap-2 mb-4">
          <button type="button" className={tabClass(tab === "available")} onClick={() => setTab("available")}>
            Available ({availableCount})
          </button>
          <button type="button" className={tabClass(tab === "mine")} onClick={() => setTab("mine")}>
            My orders ({mineCount})
          </button>
        </div>

        {tab === "available" ? (
          <DataTable
            data={available.data ?? []}
            isLoading={available.isLoading}
            columns={availableColumns}
            keyField={(o) => o.orderId}
            searchable={false}
            emptyIcon={ShoppingBag}
            emptyTitle="No new orders waiting"
          />
        ) : (
          <DataTable
            data={mine.data ?? []}
            isLoading={mine.isLoading}
            columns={mineColumns}
            keyField={(o) => o.orderId}
            searchable={false}
            emptyIcon={ShoppingBag}
            emptyTitle="You haven't accepted any orders yet"
          />
        )}
      </Card>
    </DashboardLayout>
  );
}