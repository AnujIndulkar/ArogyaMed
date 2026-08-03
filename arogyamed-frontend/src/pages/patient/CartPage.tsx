import { Button } from "@/components/common/Button";
import { Card } from "@/components/common/Card";
import { EmptyState } from "@/components/common/EmptyState";
import { DashboardLayout } from "@/components/layout/DashboardLayout";
import { useCart } from "@/hooks/useCart";
import { orderService } from "@/services/domainServices";
import type { Order } from "@/types/common.types";
import { useMutation } from "@tanstack/react-query";
import { ArrowLeft, Minus, Plus, ShoppingBag, Trash2 } from "lucide-react";
import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { toast } from "sonner";

export default function CartPage() {
  const { items, updateQuantity, removeFromCart, clearCart, totalAmount } = useCart();
  const navigate = useNavigate();
  const [placing, setPlacing] = useState(false);

  const mutation = useMutation({
    mutationFn: (payload: Partial<Order>) => orderService.create(payload),
    onMutate: () => setPlacing(true),
    onSuccess: () => {
      toast.success("Order placed successfully!");
      clearCart();
      navigate("/patient/orders");
    },
    onError: () => {
      toast.error("Couldn't place order. Please try again.");
      setPlacing(false);
    },
  });

  const handleCheckout = () => {
    mutation.mutate({
      totalAmount,
      status: "PENDING",
    } as Partial<Order>);
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

            <Button onClick={handleCheckout} isLoading={placing} className="w-full mt-5">
              Place order
            </Button>
          </Card>
        </div>
      )}
    </DashboardLayout>
  );
}