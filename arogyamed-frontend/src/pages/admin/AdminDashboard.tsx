import { useQuery } from "@tanstack/react-query";
import { Users, ShoppingBag, IndianRupee, ShieldCheck } from "lucide-react";
import {
  AreaChart,
  Area,
  XAxis,
  YAxis,
  Tooltip,
  ResponsiveContainer,
  CartesianGrid,
  PieChart,
  Pie,
  Cell,
  Legend,
} from "recharts";
import { DashboardLayout } from "@/components/layout/DashboardLayout";
import { StatCard } from "@/components/common/StatCard";
import { Card } from "@/components/common/Card";
import { Badge } from "@/components/common/Badge";
import { patientService, orderService } from "@/services/domainServices";
import { colors, chartPalette } from "@/theme/tokens";

const growthTrend = [
  { month: "Feb", users: 320 },
  { month: "Mar", users: 410 },
  { month: "Apr", users: 480 },
  { month: "May", users: 590 },
  { month: "Jun", users: 710 },
  { month: "Jul", users: 860 },
];

const roleDistribution = [
  { name: "Patients", value: 62 },
  { name: "Doctors", value: 12 },
  { name: "Pharmacists", value: 9 },
  { name: "Others", value: 17 },
];

const recentActivity = [
  { action: "New KYC submitted", by: "Wholesaler #204", time: "5m ago" },
  { action: "Medicine batch verified", by: "Company #12", time: "22m ago" },
  { action: "SOS request resolved", by: "Ambulance #7", time: "1h ago" },
  { action: "New pharmacist registered", by: "Admin", time: "2h ago" },
];

export default function AdminDashboard() {
  const { data: patients } = useQuery({
    queryKey: ["admin-patients"],
    queryFn: patientService.getAll,
    retry: false,
  });

  const { data: orders } = useQuery({
    queryKey: ["admin-orders"],
    queryFn: orderService.getAll,
    retry: false,
  });

  const revenue = orders?.reduce((sum, o) => sum + (o.totalAmount ?? 0), 0) ?? 0;

  return (
    <DashboardLayout title="Admin Dashboard">
      <div className="grid grid-cols-2 lg:grid-cols-4 gap-4">
        <StatCard index={0} label="Total Users" value={patients?.length ?? 0} icon={Users} accent="primary" />
        <StatCard index={1} label="Total Orders" value={orders?.length ?? 0} icon={ShoppingBag} accent="accent" />
        <StatCard index={2} label="Platform Revenue" value={Math.round(revenue)} icon={IndianRupee} accent="success" trend="+9%" trendUp />
        <StatCard index={3} label="Pending KYC" value={6} icon={ShieldCheck} accent="warning" />
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-3 gap-5 mt-6">
        <Card className="lg:col-span-2">
          <p className="font-display font-semibold text-ink-900 mb-1">Platform growth</p>
          <p className="text-xs text-ink-500 mb-4">Cumulative registered users</p>

          <ResponsiveContainer width="100%" height={220}>
            <AreaChart data={growthTrend}>
              <defs>
                <linearGradient id="growthFill" x1="0" y1="0" x2="0" y2="1">
                  <stop offset="0%" stopColor={colors.primary} stopOpacity={0.25} />
                  <stop offset="100%" stopColor={colors.primary} stopOpacity={0} />
                </linearGradient>
              </defs>
              <CartesianGrid strokeDasharray="3 3" stroke={colors.background} vertical={false} />
              <XAxis dataKey="month" tick={{ fontSize: 12, fill: colors.ink500 }} axisLine={false} tickLine={false} />
              <YAxis tick={{ fontSize: 12, fill: colors.ink500 }} axisLine={false} tickLine={false} />
              <Tooltip contentStyle={{ borderRadius: 12, border: "1px solid #EDEEF5" }} />
              <Area type="monotone" dataKey="users" stroke={colors.primary} strokeWidth={2.5} fill="url(#growthFill)" />
            </AreaChart>
          </ResponsiveContainer>
        </Card>

        <Card>
          <p className="font-display font-semibold text-ink-900 mb-4">User distribution</p>
          <ResponsiveContainer width="100%" height={200}>
            <PieChart>
              <Pie data={roleDistribution} dataKey="value" nameKey="name" innerRadius={45} outerRadius={75} paddingAngle={3}>
                {roleDistribution.map((_, i) => (
                  <Cell key={i} fill={chartPalette[i % chartPalette.length]} />
                ))}
              </Pie>
              <Tooltip contentStyle={{ borderRadius: 12, border: "1px solid #EDEEF5" }} />
              <Legend wrapperStyle={{ fontSize: 12 }} />
            </PieChart>
          </ResponsiveContainer>
        </Card>
      </div>

      <Card className="mt-5">
        <p className="font-display font-semibold text-ink-900 mb-4">Recent activity</p>
        <div className="flex flex-col divide-y divide-surface-border">
          {recentActivity.map((a, i) => (
            <div key={i} className="flex items-center justify-between py-3">
              <div>
                <p className="text-sm text-ink-900 font-medium">{a.action}</p>
                <p className="text-xs text-ink-500">{a.by}</p>
              </div>
              <Badge tone="neutral">{a.time}</Badge>
            </div>
          ))}
        </div>
      </Card>
    </DashboardLayout>
  );
}
