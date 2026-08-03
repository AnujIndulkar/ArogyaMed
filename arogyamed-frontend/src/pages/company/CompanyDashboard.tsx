import { useQuery } from "@tanstack/react-query";
import { Pill, ScanLine, ShieldAlert, Warehouse } from "lucide-react";
import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  Tooltip,
  ResponsiveContainer,
  CartesianGrid,
} from "recharts";
import { DashboardLayout } from "@/components/layout/DashboardLayout";
import { StatCard } from "@/components/common/StatCard";
import { Card } from "@/components/common/Card";
import { Badge } from "@/components/common/Badge";
import { TableSkeleton } from "@/components/common/EmptyState";
import { medicineService } from "@/services/domainServices";
import { colors } from "@/theme/tokens";

const productionTrend = [
  { month: "Feb", batches: 40 },
  { month: "Mar", batches: 55 },
  { month: "Apr", batches: 48 },
  { month: "May", batches: 62 },
  { month: "Jun", batches: 70 },
  { month: "Jul", batches: 65 },
];

export default function CompanyDashboard() {
  const { data: medicines, isLoading } = useQuery({
    queryKey: ["company-medicines"],
    queryFn: medicineService.getAll,
    retry: false,
  });

  return (
    <DashboardLayout title="Company Dashboard">
      <div className="grid grid-cols-2 lg:grid-cols-4 gap-4">
        <StatCard index={0} label="Total Medicines" value={medicines?.length ?? 0} icon={Pill} accent="primary" />
        <StatCard index={1} label="Verified Batches" value={214} icon={ScanLine} accent="success" trend="98% verified" trendUp />
        <StatCard index={2} label="Counterfeit Alerts" value={2} icon={ShieldAlert} accent="accent" />
        <StatCard index={3} label="Active Wholesalers" value={26} icon={Warehouse} accent="warning" />
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-3 gap-5 mt-6">
        <Card className="lg:col-span-2">
          <p className="font-display font-semibold text-ink-900 mb-1">Production output</p>
          <p className="text-xs text-ink-500 mb-4">Batches manufactured per month</p>

          <ResponsiveContainer width="100%" height={220}>
            <LineChart data={productionTrend}>
              <CartesianGrid strokeDasharray="3 3" stroke={colors.background} vertical={false} />
              <XAxis dataKey="month" tick={{ fontSize: 12, fill: colors.ink500 }} axisLine={false} tickLine={false} />
              <YAxis tick={{ fontSize: 12, fill: colors.ink500 }} axisLine={false} tickLine={false} />
              <Tooltip contentStyle={{ borderRadius: 12, border: "1px solid #EDEEF5" }} />
              <Line type="monotone" dataKey="batches" stroke={colors.secondary} strokeWidth={2.5} dot={{ r: 3 }} />
            </LineChart>
          </ResponsiveContainer>
        </Card>

        <Card>
          <p className="font-display font-semibold text-ink-900 mb-4">Recent medicines</p>

          {isLoading ? (
            <TableSkeleton rows={4} />
          ) : (
            <div className="flex flex-col gap-3">
              {(medicines ?? []).slice(0, 5).map((m) => (
                <div key={m.id} className="flex items-center justify-between text-sm">
                  <span className="text-ink-700 truncate">{m.medicineName}</span>
                  <Badge tone="success">Verified</Badge>
                </div>
              ))}
              {(medicines ?? []).length === 0 && (
                <p className="text-sm text-ink-300 text-center py-6">No medicines yet</p>
              )}
            </div>
          )}
        </Card>
      </div>
    </DashboardLayout>
  );
}
