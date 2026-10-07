import { Badge } from "@/components/common/Badge";
import { Card } from "@/components/common/Card";
import { MedicineImage } from "@/components/common/MedicineImage";
import { dosageFormLabel, medicineKind } from "@/components/common/MedicineVisual";
import { DashboardLayout } from "@/components/layout/DashboardLayout";
import { useCart } from "@/hooks/useCart";
import { medicineService } from "@/services/domainServices";
import { useQuery } from "@tanstack/react-query";
import { ArrowLeft, Building2, CalendarClock, FlaskConical, Info, Minus, Plus, ShoppingCart } from "lucide-react";
import { Link, useParams } from "react-router-dom";
import { toast } from "sonner";

function formatExpiry(date?: string) {
  if (!date) return "-";
  const d = new Date(date);
  if (isNaN(d.getTime())) return date;
  return d.toLocaleDateString("en-IN", { month: "short", year: "numeric" });
}

// "strip of 10 tablets" -> 10
function unitCount(packSize?: string): number | null {
  const m = packSize?.match(/(\d+)\s*(tablet|capsule)/i);
  return m ? Number(m[1]) : null;
}

export default function MedicineDetail() {
  const { id } = useParams();
  const { items, addToCart, updateQuantity } = useCart();

  const { data: medicine, isLoading, isError } = useQuery({
    queryKey: ["medicine", id],
    queryFn: () => medicineService.getById(id as string),
    enabled: !!id,
    retry: false,
  });

  // same query key as the catalog, so it is usually already cached
  const { data: all } = useQuery({
    queryKey: ["catalog-medicines"],
    queryFn: medicineService.getAll,
    retry: false,
  });

  if (isLoading) {
    return (
      <DashboardLayout title="Medicine details">
        <Card className="h-72 animate-pulse">{null}</Card>
      </DashboardLayout>
    );
  }

  if (isError || !medicine) {
    return (
      <DashboardLayout title="Medicine details">
        <Card className="flex flex-col items-center py-16">
          <p className="font-display font-semibold text-ink-900">Medicine not found</p>
          <Link to="/patient/medicines" className="btn-secondary mt-4 text-sm">
            Back to medicines
          </Link>
        </Card>
      </DashboardLayout>
    );
  }

  const kind = medicineKind(medicine.medicineName, medicine.packSize);
  const inCart = items.find((i) => i.medicineId === medicine.id);
  const outOfStock = medicine.stockQuantity === 0;
  const units = unitCount(medicine.packSize);
  const similar = (all ?? [])
    .filter((m) => m.category === medicine.category && m.id !== medicine.id)
    .slice(0, 4);

  return (
    <DashboardLayout title="Medicine details">
      <Link to="/patient/medicines" className="inline-flex items-center gap-1.5 text-sm text-ink-500 hover:text-primary-600 mb-4">
        <ArrowLeft size={15} /> Back to medicines
      </Link>

      <div className="grid grid-cols-1 lg:grid-cols-3 gap-5">
        {/* ---- main product card ---- */}
        <Card className="lg:col-span-2">
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div className="rounded-2xl overflow-hidden border border-surface-border h-64 md:h-full min-h-[16rem]">
              <MedicineImage
                name={medicine.medicineName}
                packSize={medicine.packSize}
                imageUrl={medicine.imageUrl}
                className="w-full h-full"
              />
            </div>

            <div className="flex flex-col">
              <span className="text-[11px] font-medium text-primary-600 uppercase tracking-wide">
                {medicine.category}
              </span>
              <h2 className="font-display font-bold text-2xl text-ink-900 mt-1">{medicine.medicineName}</h2>
              {medicine.packSize && <p className="text-ink-500 mt-1">{medicine.packSize}</p>}
              {medicine.genericName && (
                <p className="text-sm text-ink-700 mt-3">
                  <span className="text-ink-500">Composition: </span>
                  {medicine.genericName}
                </p>
              )}

              <div className="mt-5">
                <p className="font-display font-bold text-3xl text-ink-900">₹{medicine.price}</p>
                <p className="text-xs text-ink-500 mt-1">
                  {units ? `₹${(medicine.price / units).toFixed(2)} per unit · ` : ""}MRP, inclusive of all taxes
                </p>
              </div>

              <div className="mt-3">
                {outOfStock ? (
                  <Badge tone="warning">Out of stock</Badge>
                ) : medicine.stockQuantity < 20 ? (
                  <Badge tone="warning">Only {medicine.stockQuantity} left</Badge>
                ) : (
                  <Badge tone="success">In stock</Badge>
                )}
              </div>

              <div className="mt-auto pt-6">
                {!inCart ? (
                  <button
                    disabled={outOfStock}
                    onClick={() => {
                      addToCart(medicine);
                      toast.success(`${medicine.medicineName} added to cart`);
                    }}
                    className="btn-primary w-full flex items-center justify-center gap-2"
                  >
                    <ShoppingCart size={16} /> Add to cart
                  </button>
                ) : (
                  <div className="flex flex-col gap-3">
                    <div className="flex items-center justify-between bg-primary-50 rounded-xl px-3 py-2">
                      <button
                        onClick={() => updateQuantity(medicine.id, inCart.quantity - 1)}
                        className="w-8 h-8 rounded-lg bg-white flex items-center justify-center text-primary-600 shadow-sm"
                      >
                        <Minus size={14} />
                      </button>
                      <span className="text-sm font-semibold text-primary-700">{inCart.quantity} in cart</span>
                      <button
                        onClick={() => updateQuantity(medicine.id, inCart.quantity + 1)}
                        className="w-8 h-8 rounded-lg bg-white flex items-center justify-center text-primary-600 shadow-sm"
                      >
                        <Plus size={14} />
                      </button>
                    </div>
                    <Link to="/patient/cart" className="btn-secondary text-center text-sm">
                      Go to cart
                    </Link>
                  </div>
                )}
              </div>
            </div>
          </div>

          {/* ---- quick facts ---- */}
          <div className="grid grid-cols-2 sm:grid-cols-4 gap-4 mt-6 pt-6 border-t border-surface-border">
            <div>
              <p className="text-[11px] uppercase tracking-wide text-ink-300 flex items-center gap-1">
                <Building2 size={12} /> Made by
              </p>
              <p className="text-sm font-medium text-ink-900 mt-1">{medicine.companyName ?? "-"}</p>
            </div>
            <div>
              <p className="text-[11px] uppercase tracking-wide text-ink-300 flex items-center gap-1">
                <FlaskConical size={12} /> Dosage form
              </p>
              <p className="text-sm font-medium text-ink-900 mt-1">{dosageFormLabel(kind)}</p>
            </div>
            <div>
              <p className="text-[11px] uppercase tracking-wide text-ink-300 flex items-center gap-1">
                <CalendarClock size={12} /> Expiry
              </p>
              <p className="text-sm font-medium text-ink-900 mt-1">{formatExpiry(medicine.expiryDate)}</p>
            </div>
            <div>
              <p className="text-[11px] uppercase tracking-wide text-ink-300">Batch</p>
              <p className="text-sm font-mono text-ink-900 mt-1">{medicine.batchNumber}</p>
            </div>
          </div>
        </Card>

        {/* ---- side card ---- */}
        <Card className="h-fit">
          <p className="font-display font-semibold text-ink-900 flex items-center gap-2">
            <Info size={16} className="text-primary-500" /> About this medicine
          </p>
          <dl className="mt-4 flex flex-col gap-4 text-sm">
            <div>
              <dt className="text-ink-500">Composition</dt>
              <dd className="text-ink-900 mt-0.5">{medicine.genericName || "Not available"}</dd>
            </div>
            <div>
              <dt className="text-ink-500">Details</dt>
              <dd className="text-ink-900 mt-0.5">{medicine.description || "Not available"}</dd>
            </div>
            <div>
              <dt className="text-ink-500">Category</dt>
              <dd className="text-ink-900 mt-0.5">{medicine.category}</dd>
            </div>
          </dl>
          <p className="text-xs text-ink-500 mt-5 pt-4 border-t border-surface-border">
            Use only as directed by a doctor or pharmacist. Demo data for illustration - not medical advice.
          </p>
        </Card>
      </div>

      {/* ---- similar medicines ---- */}
      {similar.length > 0 && (
        <div className="mt-8">
          <p className="font-display font-semibold text-ink-900 mb-3">Similar medicines</p>
          <div className="grid grid-cols-2 lg:grid-cols-4 gap-4">
            {similar.map((m) => (
              <Link key={m.id} to={`/patient/medicines/${m.id}`}>
                <Card hover className="!p-0 overflow-hidden">
                  <MedicineImage name={m.medicineName} packSize={m.packSize} imageUrl={m.imageUrl} className="h-24" />
                  <div className="p-3">
                    <p className="text-sm font-medium text-ink-900 truncate">{m.medicineName}</p>
                    <p className="text-xs text-ink-500 truncate">{m.packSize}</p>
                    <p className="text-sm font-semibold text-ink-900 mt-1">₹{m.price}</p>
                  </div>
                </Card>
              </Link>
            ))}
          </div>
        </div>
      )}
    </DashboardLayout>
  );
}