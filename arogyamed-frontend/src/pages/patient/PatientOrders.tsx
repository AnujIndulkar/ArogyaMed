import axiosInstance from "@/api/axiosInstance";
import { Badge, statusTone } from "@/components/common/Badge";
import { Card } from "@/components/common/Card";
import { EmptyState, TableSkeleton } from "@/components/common/EmptyState";
import { DashboardLayout } from "@/components/layout/DashboardLayout";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { ShoppingBag } from "lucide-react";
import { toast } from "sonner";

// One order as the backend sends it from GET /api/orders/my
interface MyOrder {
  orderId: number;
  totalAmount: number;
  orderStatus: string;
  orderDate: string;
  paymentMethod?: string;
  paymentStatus?: string;
  transactionId?: string;
  items: {
    medicineId: number;
    medicineName: string;
    quantity: number;
    price: number;
    subtotal: number;
  }[];
}

const METHOD_LABEL: Record<string, string> = {
  CASH_ON_DELIVERY: "Cash on delivery",
  UPI: "UPI",
  CARD: "Card",
  NET_BANKING: "Net banking",
  WALLET: "Wallet",
};

// Inline colours so they do not depend on the Tailwind theme
function paymentChip(order: MyOrder): { label: string; bg: string; fg: string } {
  const cod = order.paymentMethod === "CASH_ON_DELIVERY";
  switch (order.paymentStatus) {
    case "SUCCESS":
      return { label: "Paid", bg: "#ecfdf5", fg: "#047857" };
    case "REFUNDED":
      return { label: "Refunded", bg: "#eff6ff", fg: "#1d4ed8" };
    case "FAILED":
      return { label: "Not paid", bg: "#fef2f2", fg: "#b91c1c" };
    case "PENDING":
      return cod
        ? { label: "Pay on delivery", bg: "#fffbeb", fg: "#b45309" }
        : { label: "Payment pending", bg: "#fffbeb", fg: "#b45309" };
    default:
      return { label: "No payment record", bg: "#f3f4f6", fg: "#4b5563" };
  }
}

function errorMessage(err: unknown, fallback: string): string {
  const raw = (err as { response?: { data?: { message?: unknown } } })?.response?.data?.message;
  if (typeof raw !== "string") return fallback;
  const quoted = raw.match(/"([^"]+)"/);
  return quoted ? quoted[1] : raw;
}

export default function PatientOrders() {
  const queryClient = useQueryClient();

  // only MY orders: the server picks the patient from the login token
  const { data: orders = [], isLoading } = useQuery({
    queryKey: ["my-orders"],
    queryFn: async () => {
      const response = await axiosInstance.get<MyOrder[]>("/orders/my");
      return response.data;
    },
    retry: false,
  });

  const cancelMutation = useMutation({
    mutationFn: (orderId: number) => axiosInstance.post(`/orders/${orderId}/cancel`),
    onSuccess: () => {
      toast.success("Order cancelled");
      queryClient.invalidateQueries({ queryKey: ["my-orders"] });
      queryClient.invalidateQueries({ queryKey: ["catalog-medicines"] }); // stock came back
    },
    onError: (err) => toast.error(errorMessage(err, "Couldn't cancel the order")),
  });

  const handleCancel = (orderId: number) => {
    if (window.confirm("Cancel this order?")) {
      cancelMutation.mutate(orderId);
    }
  };

  return (
    <DashboardLayout title="My Orders">
      {isLoading ? (
        <TableSkeleton rows={3} />
      ) : orders.length === 0 ? (
        <Card>
          <EmptyState
            icon={ShoppingBag}
            title="No orders yet"
            description="Your orders will show up here after you check out"
          />
        </Card>
      ) : (
        <div className="flex flex-col gap-4">
          {orders.map((order) => {
            const chip = paymentChip(order);
            const canCancel = order.orderStatus === "PENDING" || order.orderStatus === "CONFIRMED";

            return (
              <Card key={order.orderId}>
                <div className="flex flex-wrap items-start justify-between gap-3">
                  <div>
                    <p className="font-display font-semibold text-ink-900">Order #{order.orderId}</p>
                    <p className="text-xs text-ink-500 mt-0.5">
                      {new Date(order.orderDate).toLocaleString("en-IN", {
                        dateStyle: "medium",
                        timeStyle: "short",
                      })}
                    </p>
                  </div>

                  <div className="flex flex-wrap items-center gap-2">
                    <Badge tone={statusTone(order.orderStatus as never)}>{order.orderStatus}</Badge>
                    <span
                      style={{ background: chip.bg, color: chip.fg }}
                      className="rounded-full px-2.5 py-1 text-[11px] font-semibold"
                    >
                      {chip.label}
                    </span>
                  </div>
                </div>

                <div className="mt-4 divide-y divide-surface-border text-sm">
                  {order.items.map((item) => (
                    <div key={item.medicineId} className="flex items-center justify-between py-2">
                      <span className="min-w-0 truncate text-ink-900">
                        {item.medicineName}
                        <span className="text-ink-500"> × {item.quantity}</span>
                      </span>
                      <span className="shrink-0 text-ink-700">₹{item.subtotal}</span>
                    </div>
                  ))}
                  {order.items.length === 0 && (
                    <p className="py-2 text-ink-500">No item details for this order.</p>
                  )}
                </div>

                <div className="mt-3 flex flex-wrap items-center justify-between gap-3 border-t border-surface-border pt-3">
                  <div className="text-xs text-ink-500">
                    {order.paymentMethod && (
                      <span>{METHOD_LABEL[order.paymentMethod] ?? order.paymentMethod}</span>
                    )}
                    {order.transactionId && order.paymentStatus === "SUCCESS" && (
                      <span className="font-mono"> · {order.transactionId}</span>
                    )}
                  </div>

                  <div className="flex items-center gap-4">
                    <span className="font-display font-bold text-ink-900">₹{order.totalAmount}</span>
                    {canCancel && (
                      <button
                        type="button"
                        onClick={() => handleCancel(order.orderId)}
                        disabled={cancelMutation.isPending}
                        className="rounded-lg border border-surface-border px-3 py-1.5 text-xs font-medium text-ink-700 hover:border-accent-500 hover:text-accent-600 disabled:opacity-40"
                      >
                        Cancel order
                      </button>
                    )}
                  </div>
                </div>
              </Card>
            );
          })}
        </div>
      )}
    </DashboardLayout>
  );
}