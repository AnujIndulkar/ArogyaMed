import { Badge } from "@/components/common/Badge";
import { Card } from "@/components/common/Card";
import { TableSkeleton } from "@/components/common/EmptyState";
import { StatCard } from "@/components/common/StatCard";
import { DashboardLayout } from "@/components/layout/DashboardLayout";
import { medicineService, orderService } from "@/services/domainServices";
import { colors } from "@/theme/tokens";
import { useQuery } from "@tanstack/react-query";
import { AlertTriangle, FileCheck, IndianRupee, Package } from "lucide-react";
import { useState } from "react";
import {
  Area,
  AreaChart,
  CartesianGrid,
  ResponsiveContainer,
  Tooltip,
  XAxis,
  YAxis,
} from "recharts";

type Period = "week" | "month" | "year";

const salesByPeriod: Record<Period, { label: string; sales: number }[]> = {
  week: [
    { label: "Mon", sales: 22 },
    { label: "Tue", sales: 31 },
    { label: "Wed", sales: 18 },
    { label: "Thu", sales: 27 },
    { label: "Fri", sales: 35 },
    { label: "Sat", sales: 41 },
    { label: "Sun", sales: 19 },
  ],
  month: [
    { label: "Week 1", sales: 148 },
    { label: "Week 2", sales: 172 },
    { label: "Week 3", sales: 159 },
    { label: "Week 4", sales: 201 },
  ],
  year: [
    { label: "Jan", sales: 620 },
    { label: "Feb", sales: 540 },
    { label: "Mar", sales: 710 },
    { label: "Apr", sales: 680 },
    { label: "May", sales: 790 },
    { label: "Jun", sales: 830 },
    { label: "Jul", sales: 760 },
    { label: "Aug", sales: 890 },
    { label: "Sep", sales: 810 },
    { label: "Oct", sales: 920 },
    { label: "Nov", sales: 870 },
    { label: "Dec", sales: 950 },
  ],
};

const PERIOD_LABELS: Record<Period, string> = {
  week: "This week",
  month: "This month",
  year: "This year",
};

export default function PharmacistDashboard() {
  const [period, setPeriod] = useState<Period>("week");

  const { data: orders, isLoading: ordersLoading } = useQuery({
    queryKey: ["pharmacist-orders"],
    queryFn: orderService.getAll,
    retry: false,
  });

  const { data: medicines, isLoading: medLoading } = useQuery({
    queryKey: ["pharmacist-medicines"],
    queryFn: medicineService.getAll,
    retry: false,
  });

  const lowStock = medicines?.filter((m) => m.stockQuantity < 20).length ?? 0;

  return (
    <DashboardLayout title="Pharmacist Dashboard">
      <div className="grid grid-cols-2 lg:grid-cols-4 gap-4">
        <StatCard index={0} label="Total Orders" value={orders?.length ?? 0} icon={Package} accent="primary" />
        <StatCard index={1} label="Low Stock Items" value={lowStock} icon={AlertTriangle} accent="warning" trend="Reorder needed" />
        <StatCard index={2} label="Pending Prescriptions" value={7} icon={FileCheck} accent="accent" />
        <StatCard index={3} label="Today's Revenue" value={12480} icon={IndianRupee} accent="success" trend="+6%" trendUp />
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-3 gap-5 mt-6">
        <Card className="lg:col-span-2">
          <div className="flex items-center justify-between mb-1">
            <p className="font-display font-semibold text-ink-900">Sales — {PERIOD_LABELS[period]}</p>

            <div className="flex items-center gap-1 bg-surface rounded-lg p-1">
              {(["week", "month", "year"] as Period[]).map((p) => (
                <button
                  key={p}
                  onClick={() => setPeriod(p)}
                  className={`text-xs font-medium px-3 py-1.5 rounded-md transition-colors capitalize ${
                    period === p
                      ? "bg-white text-primary-700 shadow-sm"
                      : "text-ink-500 hover:text-ink-900"
                  }`}
                >
                  {p}
                </button>
              ))}
            </div>
          </div>
          <p className="text-xs text-ink-500 mb-4">Number of orders fulfilled</p>

          <ResponsiveContainer width="100%" height={220}>
            <AreaChart data={salesByPeriod[period]}>
              <defs>
                <linearGradient id="salesFill" x1="0" y1="0" x2="0" y2="1">
                  <stop offset="0%" stopColor={colors.primary} stopOpacity={0.25} />
                  <stop offset="100%" stopColor={colors.primary} stopOpacity={0} />
                </linearGradient>
              </defs>
              <CartesianGrid strokeDasharray="3 3" stroke={colors.background} vertical={false} />
              <XAxis dataKey="label" tick={{ fontSize: 12, fill: colors.ink500 }} axisLine={false} tickLine={false} />
              <YAxis tick={{ fontSize: 12, fill: colors.ink500 }} axisLine={false} tickLine={false} />
              <Tooltip contentStyle={{ borderRadius: 12, border: "1px solid #EDEEF5" }} />
              <Area type="monotone" dataKey="sales" stroke={colors.primary} strokeWidth={2.5} fill="url(#salesFill)" />
            </AreaChart>
          </ResponsiveContainer>
        </Card>

        <Card>
          <p className="font-display font-semibold text-ink-900 mb-4">Inventory snapshot</p>

          {medLoading ? (
            <TableSkeleton rows={4} />
          ) : (
            <div className="flex flex-col gap-3">
              {(medicines ?? []).slice(0, 5).map((m) => (
                <div key={m.id} className="flex items-center justify-between text-sm">
                  <span className="text-ink-700 truncate">{m.medicineName}</span>
                  <Badge tone={m.stockQuantity < 20 ? "warning" : "success"}>
                    {m.stockQuantity} units
                  </Badge>
                </div>
              ))}
              {(medicines ?? []).length === 0 && (
                <p className="text-sm text-ink-300 text-center py-6">No medicines yet</p>
              )}
            </div>
          )}
        </Card>
      </div>

      <Card className="mt-5">
        <p className="font-display font-semibold text-ink-900 mb-4">Recent orders</p>

        {ordersLoading ? (
          <TableSkeleton rows={4} />
        ) : (
          <div className="overflow-x-auto">
            <table className="w-full text-sm">
              <thead>
                <tr className="text-left text-ink-500 border-b border-surface-border">
                  <th className="pb-3 font-medium">Order ID</th>
                  <th className="pb-3 font-medium">Patient</th>
                  <th className="pb-3 font-medium">Amount</th>
                  <th className="pb-3 font-medium">Status</th>
                </tr>
              </thead>
              <tbody>
                {(orders ?? []).slice(0, 6).map((o) => (
                  <tr key={o.id} className="border-b border-surface-border last:border-0">
                    <td className="py-3 font-mono text-ink-900">#{o.id}</td>
                    <td className="py-3 text-ink-700">#{o.patientId}</td>
                    <td className="py-3 text-ink-700">₹{o.totalAmount}</td>
                    <td className="py-3">
                      <Badge tone={o.status === "PENDING" ? "warning" : o.status === "DELIVERED" ? "success" : "primary"}>
                        {o.status}
                      </Badge>
                    </td>
                  </tr>
                ))}
                {(orders ?? []).length === 0 && (
                  <tr>
                    <td colSpan={4} className="py-8 text-center text-ink-300">
                      No orders yet
                    </td>
                  </tr>
                )}
              </tbody>
            </table>
          </div>
        )}
      </Card>
    </DashboardLayout>
  );
}