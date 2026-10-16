import axiosInstance from "@/api/axiosInstance";
import { Card } from "@/components/common/Card";
import { DataTable, type Column } from "@/components/common/DataTable";
import { DashboardLayout } from "@/components/layout/DashboardLayout";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { Package, Search } from "lucide-react";
import { useMemo, useState } from "react";
import { toast } from "sonner";

type StockItem = {
  medicineId: number;
  medicineName: string;
  category?: string | null;
  companyName?: string | null;
  batchNumber?: string | null;
  price?: number | null;
  stockQuantity: number;
  expiryDate?: string | null;
  stockStatus: "IN_STOCK" | "LOW_STOCK" | "OUT_OF_STOCK";
  expiryStatus: "OK" | "EXPIRING_SOON" | "EXPIRED";
};

type FilterKey = "ALL" | "LOW" | "OUT" | "EXPIRING";

function errorMessage(err: unknown, fallback: string): string {
  const raw = (err as { response?: { data?: { message?: unknown } } })?.response?.data?.message;
  if (typeof raw !== "string") return fallback;
  const quoted = raw.match(/"([^"]+)"/);
  return quoted ? quoted[1] : raw;
}

const STOCK_BADGE: Record<StockItem["stockStatus"], { label: string; cls: string }> = {
  IN_STOCK: { label: "In stock", cls: "bg-green-50 text-green-700" },
  LOW_STOCK: { label: "Low stock", cls: "bg-amber-50 text-amber-700" },
  OUT_OF_STOCK: { label: "Out of stock", cls: "bg-red-50 text-red-700" },
};

function Chip({ cls, children }: { cls: string; children: React.ReactNode }) {
  return (
    <span className={`inline-block rounded-full px-2.5 py-0.5 text-xs font-medium ${cls}`}>
      {children}
    </span>
  );
}

// Small "add N units" box shown on every row
function RestockCell({
  item,
  busy,
  onRestock,
}: {
  item: StockItem;
  busy: boolean;
  onRestock: (medicineId: number, quantity: number) => void;
}) {
  const [qty, setQty] = useState("10");
  const n = Number(qty);
  const valid = Number.isInteger(n) && n >= 1 && n <= 100000;

  return (
    <div className="flex items-center gap-2">
      <input
        type="number"
        min={1}
        value={qty}
        onChange={(e) => setQty(e.target.value)}
        className="w-20 text-xs border border-surface-border rounded-lg px-2 py-1.5 outline-none focus:border-primary-400"
      />
      <button
        type="button"
        disabled={!valid || busy}
        onClick={() => onRestock(item.medicineId, n)}
        className="text-xs font-medium rounded-lg px-3 py-1.5 bg-primary-600 text-white hover:bg-primary-700 disabled:opacity-50"
      >
        {busy ? "Adding..." : "Restock"}
      </button>
    </div>
  );
}

export default function PharmacistStock() {
  const queryClient = useQueryClient();
  const [search, setSearch] = useState("");
  const [filter, setFilter] = useState<FilterKey>("ALL");

  const { data, isLoading } = useQuery({
    queryKey: ["stock"],
    queryFn: async () => (await axiosInstance.get<StockItem[]>("/stock")).data,
  });
  const items = data ?? [];

  // PATCH /api/stock/{id}/restock
  const restock = useMutation({
    mutationFn: ({ medicineId, quantity }: { medicineId: number; quantity: number }) =>
      axiosInstance.patch(`/stock/${medicineId}/restock`, { quantity }),
    onSuccess: (_res, vars) => {
      toast.success(`Added ${vars.quantity} units to stock`);
      queryClient.invalidateQueries({ queryKey: ["stock"] });
      queryClient.invalidateQueries({ queryKey: ["catalog-medicines"] });
    },
    onError: (err) => toast.error(errorMessage(err, "Couldn't update the stock")),
  });

  const counts = useMemo(
    () => ({
      ALL: items.length,
      LOW: items.filter((i) => i.stockStatus === "LOW_STOCK").length,
      OUT: items.filter((i) => i.stockStatus === "OUT_OF_STOCK").length,
      EXPIRING: items.filter((i) => i.expiryStatus !== "OK").length,
    }),
    [items],
  );

  const rows = useMemo(() => {
    const q = search.trim().toLowerCase();
    return items.filter((i) => {
      const matches =
        !q ||
        [i.medicineName, i.companyName, i.batchNumber, i.category].some((v) =>
          (v ?? "").toLowerCase().includes(q),
        );
      if (!matches) return false;
      if (filter === "LOW") return i.stockStatus === "LOW_STOCK";
      if (filter === "OUT") return i.stockStatus === "OUT_OF_STOCK";
      if (filter === "EXPIRING") return i.expiryStatus !== "OK";
      return true;
    });
  }, [items, search, filter]);

  const columns: Column<StockItem>[] = [
    {
      header: "Medicine",
      accessor: (i) => (
        <div>
          <p className="font-medium text-ink-900">{i.medicineName}</p>
          <p className="text-xs text-ink-500">
            {[i.companyName, i.category].filter(Boolean).join(" · ") || "—"}
          </p>
        </div>
      ),
    },
    { header: "Batch", accessor: (i) => <span className="font-mono text-xs">{i.batchNumber ?? "—"}</span> },
    {
      header: "Expiry",
      accessor: (i) => (
        <div>
          <span className="text-sm">{i.expiryDate ?? "—"}</span>
          {i.expiryStatus === "EXPIRED" && (
            <div className="mt-1"><Chip cls="bg-red-50 text-red-700">Expired</Chip></div>
          )}
          {i.expiryStatus === "EXPIRING_SOON" && (
            <div className="mt-1"><Chip cls="bg-amber-50 text-amber-700">Expiring soon</Chip></div>
          )}
        </div>
      ),
    },
    { header: "Price", accessor: (i) => (i.price != null ? `₹${i.price}` : "—") },
    {
      header: "Stock",
      accessor: (i) => (
        <div>
          <span className="font-semibold text-ink-900">{i.stockQuantity}</span>
          <div className="mt-1">
            <Chip cls={STOCK_BADGE[i.stockStatus].cls}>{STOCK_BADGE[i.stockStatus].label}</Chip>
          </div>
        </div>
      ),
    },
    {
      header: "Add stock",
      accessor: (i) => (
        <RestockCell
          item={i}
          busy={restock.isPending && restock.variables?.medicineId === i.medicineId}
          onRestock={(medicineId, quantity) => restock.mutate({ medicineId, quantity })}
        />
      ),
    },
  ];

  const chips: { key: FilterKey; label: string }[] = [
    { key: "ALL", label: "All" },
    { key: "LOW", label: "Low stock" },
    { key: "OUT", label: "Out of stock" },
    { key: "EXPIRING", label: "Expiring / expired" },
  ];

  return (
    <DashboardLayout title="Inventory">
      <Card>
        <div className="flex flex-col gap-4 mb-4 md:flex-row md:items-center md:justify-between">
          <div className="flex flex-wrap gap-2">
            {chips.map((c) => (
              <button
                key={c.key}
                type="button"
                onClick={() => setFilter(c.key)}
                className={`text-xs font-medium rounded-full px-3 py-1.5 border transition-colors ${
                  filter === c.key
                    ? "bg-primary-600 text-white border-primary-600"
                    : "bg-white text-ink-700 border-surface-border hover:border-primary-400"
                }`}
              >
                {c.label} ({counts[c.key]})
              </button>
            ))}
          </div>

          <div className="relative md:w-72">
            <Search className="absolute left-3 top-1/2 -translate-y-1/2 h-4 w-4 text-ink-400" />
            <input
              value={search}
              onChange={(e) => setSearch(e.target.value)}
              placeholder="Search name, company, batch..."
              className="w-full text-sm border border-surface-border rounded-xl pl-9 pr-3 py-2 outline-none focus:border-primary-400"
            />
          </div>
        </div>

        <DataTable
          data={rows}
          isLoading={isLoading}
          columns={columns}
          keyField={(i) => i.medicineId}
          searchable={false}
          emptyIcon={Package}
          emptyTitle="No medicines match"
        />
      </Card>
    </DashboardLayout>
  );
}