import { Badge } from "@/components/common/Badge";
import { Card } from "@/components/common/Card";
import { MedicineImage } from "@/components/common/MedicineImage";
import { TableSkeleton } from "@/components/common/EmptyState";
import { DashboardLayout } from "@/components/layout/DashboardLayout";
import { useCart } from "@/hooks/useCart";
import { medicineService } from "@/services/domainServices";
import type { Medicine } from "@/types/common.types";
import { useQuery } from "@tanstack/react-query";
import { motion } from "framer-motion";
import { Minus, Pill, Plus, Search, ShoppingCart } from "lucide-react";
import { useMemo, useState } from "react";
import { Link } from "react-router-dom";
import { toast } from "sonner";

function MedicineCard({ medicine }: { medicine: Medicine }) {
  const { items, addToCart, updateQuantity } = useCart();
  const inCart = items.find((i) => i.medicineId === medicine.id);
  const outOfStock = medicine.stockQuantity === 0;

  return (
    <Card hover className="!p-0 overflow-hidden flex flex-col">
      <Link to={`/patient/medicines/${medicine.id}`} className="block h-36 relative">
        <MedicineImage
          name={medicine.medicineName}
          packSize={medicine.packSize}
          imageUrl={medicine.imageUrl}
          className="w-full h-full"
        />
        {medicine.stockQuantity < 20 && medicine.stockQuantity > 0 && (
          <span className="absolute top-2 right-2 bg-white/90 text-warning-600 text-[10px] font-semibold px-2 py-1 rounded-full">
            Only {medicine.stockQuantity} left
          </span>
        )}
        {outOfStock && (
          <span className="absolute top-2 right-2 bg-white/90 text-accent-600 text-[10px] font-semibold px-2 py-1 rounded-full">
            Out of stock
          </span>
        )}
      </Link>

      <div className="p-4 flex flex-col flex-1">
        <span className="text-[11px] font-medium text-primary-600 uppercase tracking-wide">
          {medicine.category}
        </span>
        <Link
          to={`/patient/medicines/${medicine.id}`}
          className="font-display font-semibold text-ink-900 mt-1 truncate hover:text-primary-600"
        >
          {medicine.medicineName}
        </Link>
        <p className="text-xs text-ink-500 mt-0.5 truncate">{medicine.packSize}</p>
        <p className="text-[11px] text-ink-300 mt-0.5 truncate">{medicine.genericName}</p>

        <div className="flex items-center justify-between mt-3">
          <span className="font-display font-bold text-lg text-ink-900">₹{medicine.price}</span>
          <Badge tone="success">In stock</Badge>
        </div>

        <div className="mt-4">
          {!inCart ? (
            <button
              disabled={outOfStock}
              onClick={() => {
                addToCart(medicine);
                toast.success(`${medicine.medicineName} added to cart`);
              }}
              className="btn-primary w-full text-sm !py-2.5 disabled:opacity-40"
            >
              <ShoppingCart size={15} /> Add to cart
            </button>
          ) : (
            <div className="flex items-center justify-between bg-primary-50 rounded-xl px-2 py-1.5">
              <button
                onClick={() => updateQuantity(medicine.id, inCart.quantity - 1)}
                className="w-7 h-7 rounded-lg bg-white flex items-center justify-center text-primary-600 shadow-sm"
              >
                <Minus size={13} />
              </button>
              <span className="text-sm font-semibold text-primary-700">{inCart.quantity} in cart</span>
              <button
                onClick={() => updateQuantity(medicine.id, inCart.quantity + 1)}
                className="w-7 h-7 rounded-lg bg-white flex items-center justify-center text-primary-600 shadow-sm"
              >
                <Plus size={13} />
              </button>
            </div>
          )}
        </div>
      </div>
    </Card>
  );
}

export default function MedicineCatalog() {
  const { data: medicines, isLoading } = useQuery({
    queryKey: ["catalog-medicines"],
    queryFn: medicineService.getAll,
    retry: false,
  });

  const { totalItems } = useCart();
  const [search, setSearch] = useState("");
  const [category, setCategory] = useState("ALL");
  const [visible, setVisible] = useState(24);

  const categories = useMemo(() => {
    const set = new Set((medicines ?? []).map((m) => m.category));
    return ["ALL", ...Array.from(set)];
  }, [medicines]);

  const filtered = (medicines ?? []).filter((m) => {
    const matchesSearch = m.medicineName.toLowerCase().includes(search.toLowerCase());
    const matchesCategory = category === "ALL" || m.category === category;
    return matchesSearch && matchesCategory;
  });

  const shown = filtered.slice(0, visible);

  return (
    <DashboardLayout title="Medicines">
      <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-3 mb-5">
        <div className="flex items-center gap-2 bg-white rounded-xl px-3.5 py-2.5 border border-surface-border w-full sm:max-w-xs">
          <Search size={16} className="text-ink-300 shrink-0" />
          <input
            value={search}
            onChange={(e) => { setSearch(e.target.value); setVisible(24); }}
            placeholder="Search medicine..."
            className="bg-transparent outline-none text-sm placeholder:text-ink-300 w-full"
          />
        </div>

        <div className="flex items-center gap-3">
          <select
            value={category}
            onChange={(e) => { setCategory(e.target.value); setVisible(24); }}
            className="text-sm border border-surface-border rounded-xl px-3 py-2.5 outline-none focus:border-primary-400 bg-white"
          >
            {categories.map((c) => (
              <option key={c} value={c}>{c === "ALL" ? "All categories" : c}</option>
            ))}
          </select>

          <Link
            to="/patient/cart"
            className="relative btn-secondary !py-2.5 flex items-center gap-2 text-sm shrink-0"
          >
            <ShoppingCart size={16} />
            Cart
            {totalItems > 0 && (
              <span className="absolute -top-2 -right-2 w-5 h-5 rounded-full bg-accent-500 text-white text-[10px] font-bold flex items-center justify-center">
                {totalItems}
              </span>
            )}
          </Link>
        </div>
      </div>

      {isLoading ? (
        <TableSkeleton rows={6} />
      ) : filtered.length === 0 ? (
        <Card className="flex flex-col items-center py-16">
          <Pill size={32} className="text-primary-200 mb-3" />
          <p className="font-display font-semibold text-ink-900">No medicines found</p>
          <p className="text-sm text-ink-500 mt-1">Try a different search or category</p>
        </Card>
      ) : (
        <>
          <div className="grid grid-cols-2 sm:grid-cols-3 lg:grid-cols-4 gap-4">
            {shown.map((m, i) => (
              <motion.div
                key={m.id}
                initial={{ opacity: 0, y: 12 }}
                animate={{ opacity: 1, y: 0 }}
                transition={{ delay: Math.min(i, 12) * 0.03 }}
              >
                <MedicineCard medicine={m} />
              </motion.div>
            ))}
          </div>

          {filtered.length > shown.length && (
            <div className="flex flex-col items-center gap-2 mt-6">
              <p className="text-xs text-ink-500">
                Showing {shown.length} of {filtered.length} medicines
              </p>
              <button onClick={() => setVisible((v) => v + 24)} className="btn-secondary text-sm !py-2.5">
                Load more
              </button>
            </div>
          )}
        </>
      )}
    </DashboardLayout>
  );
}