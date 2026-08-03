import { useQuery } from "@tanstack/react-query";
import { Warehouse, Building2, Truck, IndianRupee } from "lucide-react";
import {
  PieChart,
  Pie,
  Cell,
  Tooltip,
  ResponsiveContainer,
  Legend,
} from "recharts";
import { DashboardLayout } from "@/components/layout/DashboardLayout";
import { StatCard } from "@/components/common/StatCard";
import { Card } from "@/components/common/Card";
import { medicineService } from "@/services/domainServices";
import { chartPalette } from "@/theme/tokens";
import { TableSkeleton } from "@/components/common/EmptyState";

const categoryDistribution = [
  { name: "Cardiac", value: 32 },
  { name: "General", value: 48 },
  { name: "Pediatric", value: 20 },
  { name: "Orthopedic", value: 15 },
];

export default function WholesalerDashboard() {
  const { data: medicines, isLoading } = useQuery({
    queryKey: ["wholesaler-medicines"],
    queryFn: medicineService.getAll,
    retry: false,
  });

  const totalStock = medicines?.reduce((sum, m) => sum + m.stockQuantity, 0) ?? 0;

  return (
    <DashboardLayout title="Wholesaler Dashboard">
      <div className="grid grid-cols-2 lg:grid-cols-4 gap-4">
        <StatCard index={0} label="Total Stock Units" value={totalStock} icon={Warehouse} accent="primary" />
        <StatCard index={1} label="Partner Companies" value={18} icon={Building2} accent="accent" />
        <StatCard index={2} label="Pending Deliveries" value={9} icon={Truck} accent="warning" />
        <StatCard index={3} label="Monthly Revenue" value={286400} icon={IndianRupee} accent="success" trend="+11%" trendUp />
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-3 gap-5 mt-6">
        <Card className="lg:col-span-2">
          <p className="font-display font-semibold text-ink-900 mb-4">Stock by category</p>
          <ResponsiveContainer width="100%" height={260}>
            <PieChart>
              <Pie
                data={categoryDistribution}
                dataKey="value"
                nameKey="name"
                innerRadius={60}
                outerRadius={95}
                paddingAngle={3}
              >
                {categoryDistribution.map((_, i) => (
                  <Cell key={i} fill={chartPalette[i % chartPalette.length]} />
                ))}
              </Pie>
              <Tooltip contentStyle={{ borderRadius: 12, border: "1px solid #EDEEF5" }} />
              <Legend />
            </PieChart>
          </ResponsiveContainer>
        </Card>

        <Card>
          <p className="font-display font-semibold text-ink-900 mb-4">Stock levels</p>

          {isLoading ? (
            <TableSkeleton rows={4} />
          ) : (
            <div className="flex flex-col gap-3">
              {(medicines ?? []).slice(0, 5).map((m) => (
                <div key={m.id} className="flex items-center justify-between text-sm">
                  <span className="text-ink-700 truncate">{m.medicineName}</span>
                  <span className="font-mono text-ink-900 font-medium">{m.stockQuantity}</span>
                </div>
              ))}
              {(medicines ?? []).length === 0 && (
                <p className="text-sm text-ink-300 text-center py-6">No stock data</p>
              )}
            </div>
          )}
        </Card>
      </div>
    </DashboardLayout>
  );
}
