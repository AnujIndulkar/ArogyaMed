import axiosInstance from "@/api/axiosInstance";
import { Badge, statusTone } from "@/components/common/Badge";
import { Card } from "@/components/common/Card";
import { DataTable, type Column } from "@/components/common/DataTable";
import { DashboardLayout } from "@/components/layout/DashboardLayout";
import { useCrud } from "@/hooks/useCrud";
import { orderService } from "@/services/domainServices";
import type { Order } from "@/types/common.types";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { ShoppingBag } from "lucide-react";
import { toast } from "sonner";

// Must match the backend OrderStatus enum exactly
const STATUSES = [
  "PENDING",
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

export default function OrderList() {
  const { items, isLoading } = useCrud<Order>("orders", orderService);
  const queryClient = useQueryClient();

  // PATCH /api/orders/{id}/status  (staff only)
  const statusMutation = useMutation({
    mutationFn: ({ id, status }: { id: number; status: string }) =>
      axiosInstance.patch(`/orders/${id}/status`, { status }),
    onSuccess: () => {
      toast.success("Order status updated");
      queryClient.invalidateQueries({ queryKey: ["orders"] });
    },
    onError: (err) => toast.error(errorMessage(err, "Couldn't update the order")),
  });

  const columns: Column<Order>[] = [
    { header: "Order ID", accessor: (o) => <span className="font-mono text-ink-900">#{o.id}</span> },
    {
      header: "Patient",
      accessor: (o) => {
        const name = (o as Order & { patientName?: string }).patientName;
        return name ? name : <span className="font-mono">#{o.patientId}</span>;
      },
    },
    { header: "Amount", accessor: (o) => `₹${o.totalAmount}` },
    { header: "Status", accessor: (o) => <Badge tone={statusTone(o.status)}>{o.status}</Badge> },
    {
      header: "Update status",
      accessor: (o) => {
        const frozen = String(o.status) === "CANCELLED" || String(o.status) === "DELIVERED";
        return (
          <select
            value={o.status}
            disabled={frozen || statusMutation.isPending}
            onChange={(e) => statusMutation.mutate({ id: o.id, status: e.target.value })}
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

  return (
    <DashboardLayout title="Orders">
      <Card>
        <DataTable
          data={items}
          isLoading={isLoading}
          columns={columns}
          keyField={(o) => o.id}
          searchable={false}
          emptyIcon={ShoppingBag}
          emptyTitle="No orders yet"
        />
      </Card>
    </DashboardLayout>
  );
}