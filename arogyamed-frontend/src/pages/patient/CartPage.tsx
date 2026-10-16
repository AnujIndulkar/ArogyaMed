import axiosInstance from "@/api/axiosInstance";
import { Button } from "@/components/common/Button";
import { Card } from "@/components/common/Card";
import { EmptyState } from "@/components/common/EmptyState";
import { Modal } from "@/components/common/Modal";
import { DashboardLayout } from "@/components/layout/DashboardLayout";
import { useCart } from "@/hooks/useCart";
import {
  ArrowLeft,
  Banknote,
  CreditCard,
  Landmark,
  Minus,
  Plus,
  ShoppingBag,
  Smartphone,
  Trash2,
} from "lucide-react";
import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { toast } from "sonner";

// What the backend sends back after checkout / payment
interface CheckoutResponse {
  orderId: number;
  totalAmount: number;
  orderStatus: string;
  paymentStatus: "PENDING" | "SUCCESS" | "FAILED" | "REFUNDED";
  paymentMethod: string;
  transactionId: string;
}

const METHODS = [
  { value: "CASH_ON_DELIVERY", label: "Cash on delivery", hint: "Pay when your order arrives", icon: Banknote },
  { value: "UPI", label: "UPI", hint: "Google Pay, PhonePe, Paytm and more", icon: Smartphone },
  { value: "CARD", label: "Credit / Debit card", hint: "Visa, Mastercard, RuPay", icon: CreditCard },
  { value: "NET_BANKING", label: "Net banking", hint: "All major banks", icon: Landmark },
] as const;

type MethodValue = (typeof METHODS)[number]["value"];

// The backend error looks like:  400 BAD_REQUEST "Azee 500 has only 3 in stock"
// Show only the readable part inside the quotes.
function errorMessage(err: unknown, fallback: string): string {
  const raw = (err as { response?: { data?: { message?: unknown } } })?.response?.data?.message;
  if (typeof raw !== "string") return fallback;
  const quoted = raw.match(/"([^"]+)"/);
  return quoted ? quoted[1] : raw;
}

export default function CartPage() {
  const { items, updateQuantity, removeFromCart, clearCart, totalAmount } = useCart();
  const navigate = useNavigate();

  const [method, setMethod] = useState<MethodValue>("CASH_ON_DELIVERY");
  const [placing, setPlacing] = useState(false);
  const [paying, setPaying] = useState(false);
  // set after checkout for online payments: opens the demo payment window
  const [pendingOrder, setPendingOrder] = useState<CheckoutResponse | null>(null);

  const isOnline = method !== "CASH_ON_DELIVERY";
  const methodLabel = METHODS.find((m) => m.value === pendingOrder?.paymentMethod)?.label ?? "Online payment";

  const finishSuccess = (message: string) => {
    toast.success(message);
    clearCart();
    navigate("/patient/orders");
  };

  // Step 1: create the order. Only ids and quantities are sent;
  // the server works out prices and the total itself.
  const handleCheckout = async () => {
    setPlacing(true);
    try {
      const { data } = await axiosInstance.post<CheckoutResponse>("/orders/checkout", {
        items: items.map((i) => ({ medicineId: i.medicineId, quantity: i.quantity })),
        paymentMethod: method,
      });

      if (method === "CASH_ON_DELIVERY") {
        finishSuccess("Order placed! You will pay cash on delivery.");
      } else {
        setPendingOrder(data); // open the demo payment window
      }
    } catch (err) {
      toast.error(errorMessage(err, "Couldn't place order. Please try again."));
    } finally {
      setPlacing(false);
    }
  };

  // Step 2 (online only): the demo gateway
  const handlePay = async (simulateFailure: boolean, cancelled = false) => {
    if (!pendingOrder) return;
    setPaying(true);
    try {
      await new Promise((resolve) => setTimeout(resolve, 900)); // looks like a real gateway working
      const { data } = await axiosInstance.post<CheckoutResponse>(
        `/orders/${pendingOrder.orderId}/pay`,
        { simulateFailure }
      );
      setPendingOrder(null);

      if (data.paymentStatus === "SUCCESS") {
        finishSuccess("Payment successful! Your order is confirmed.");
      } else if (cancelled) {
        toast.info("Payment cancelled. Your cart is still here.");
      } else {
        toast.error("Payment failed. The order was cancelled, so you can try again.");
      }
    } catch (err) {
      toast.error(errorMessage(err, "Payment could not be completed."));
    } finally {
      setPaying(false);
    }
  };

  return (
    <DashboardLayout title="Your Cart">
      <Link to="/patient/medicines" className="inline-flex items-center gap-1.5 text-sm text-ink-500 hover:text-primary-600 mb-5">
        <ArrowLeft size={15} /> Continue shopping
      </Link>

      {items.length === 0 ? (
        <Card>
          <EmptyState
            icon={ShoppingBag}
            title="Your cart is empty"
            description="Add some medicines from the catalog to get started"
          />
        </Card>
      ) : (
        <div className="grid grid-cols-1 lg:grid-cols-3 gap-5">
          <Card className="lg:col-span-2">
            <div className="flex flex-col divide-y divide-surface-border">
              {items.map((item) => (
                <div key={item.medicineId} className="flex items-center justify-between py-4">
                  <div className="min-w-0">
                    <p className="font-medium text-ink-900 truncate">{item.medicineName}</p>
                    <p className="text-sm text-ink-500">₹{item.price} / unit</p>
                  </div>

                  <div className="flex items-center gap-4 shrink-0">
                    <div className="flex items-center gap-2 bg-surface rounded-xl px-1.5 py-1">
                      <button
                        onClick={() => updateQuantity(item.medicineId, item.quantity - 1)}
                        className="w-7 h-7 rounded-lg bg-white flex items-center justify-center text-ink-700 shadow-sm"
                      >
                        <Minus size={13} />
                      </button>
                      <span className="text-sm font-medium w-5 text-center">{item.quantity}</span>
                      <button
                        onClick={() => updateQuantity(item.medicineId, item.quantity + 1)}
                        className="w-7 h-7 rounded-lg bg-white flex items-center justify-center text-ink-700 shadow-sm"
                      >
                        <Plus size={13} />
                      </button>
                    </div>

                    <span className="font-display font-semibold text-ink-900 w-16 text-right">
                      ₹{item.price * item.quantity}
                    </span>

                    <button
                      onClick={() => removeFromCart(item.medicineId)}
                      className="text-ink-300 hover:text-accent-600"
                    >
                      <Trash2 size={16} />
                    </button>
                  </div>
                </div>
              ))}
            </div>
          </Card>

          <Card className="h-fit">
            <p className="font-display font-semibold text-ink-900 mb-4">Order summary</p>

            <div className="flex flex-col gap-2 text-sm">
              <div className="flex justify-between text-ink-500">
                <span>Subtotal</span>
                <span>₹{totalAmount}</span>
              </div>
              <div className="flex justify-between text-ink-500">
                <span>Delivery</span>
                <span className="text-success-600 font-medium">Free</span>
              </div>
            </div>

            <div className="border-t border-surface-border mt-4 pt-4 flex justify-between font-display font-bold text-ink-900">
              <span>Total</span>
              <span>₹{totalAmount}</span>
            </div>

            {/* Payment method */}
            <p className="font-display font-semibold text-ink-900 mt-6 mb-3">Payment method</p>
            <div className="flex flex-col gap-2">
              {METHODS.map((m) => {
                const selected = method === m.value;
                const Icon = m.icon;
                return (
                  <button
                    key={m.value}
                    type="button"
                    onClick={() => setMethod(m.value)}
                    className={`flex items-center gap-3 rounded-xl border px-3.5 py-2.5 text-left transition-colors ${
                      selected
                        ? "border-primary-400 bg-primary-50"
                        : "border-surface-border bg-white hover:border-primary-200"
                    }`}
                  >
                    <Icon size={18} className={selected ? "text-primary-600" : "text-ink-500"} />
                    <span className="min-w-0">
                      <span className="block text-sm font-medium text-ink-900">{m.label}</span>
                      <span className="block text-xs text-ink-500">{m.hint}</span>
                    </span>
                  </button>
                );
              })}
            </div>

            <Button onClick={handleCheckout} isLoading={placing} className="w-full mt-5">
              {isOnline ? "Continue to payment" : "Place order"}
            </Button>
          </Card>
        </div>
      )}

      {/* Demo payment window (online methods only) */}
      <Modal open={!!pendingOrder} onClose={() => !paying && handlePay(true, true)} title="Complete payment">
        <div className="flex flex-col gap-4">
          <div className="rounded-xl bg-warning-50 px-3.5 py-2.5 text-xs font-medium text-warning-600">
            Demo payment: no real money is charged.
          </div>

          <div className="text-center">
            <p className="text-sm text-ink-500">{methodLabel}</p>
            <p className="font-display font-bold text-3xl text-ink-900 mt-1">₹{pendingOrder?.totalAmount}</p>
            <p className="text-xs text-ink-500 mt-1">Order #{pendingOrder?.orderId}</p>
          </div>

          <Button onClick={() => handlePay(false)} isLoading={paying} className="w-full">
            Pay ₹{pendingOrder?.totalAmount}
          </Button>

          <button
            type="button"
            disabled={paying}
            onClick={() => handlePay(true)}
            className="text-sm text-ink-500 hover:text-accent-600 disabled:opacity-40"
          >
            Simulate a failed payment
          </button>
        </div>
      </Modal>
    </DashboardLayout>
  );
}