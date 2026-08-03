import { useQuery } from "@tanstack/react-query";
import { Calendar, ShoppingBag, Pill, HeartPulse, ArrowUpRight, Sparkles } from "lucide-react";
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
import { Badge, statusTone } from "@/components/common/Badge";
import { TableSkeleton } from "@/components/common/EmptyState";
import { orderService, appointmentService } from "@/services/domainServices";
import { colors } from "@/theme/tokens";
import { Link } from "react-router-dom";

const vitalsTrend = [
  { day: "Mon", bp: 118 },
  { day: "Tue", bp: 121 },
  { day: "Wed", bp: 116 },
  { day: "Thu", bp: 119 },
  { day: "Fri", bp: 114 },
  { day: "Sat", bp: 117 },
  { day: "Sun", bp: 115 },
];

export default function PatientDashboard() {
  const { data: orders, isLoading: ordersLoading } = useQuery({
    queryKey: ["patient-orders"],
    queryFn: orderService.getAll,
    retry: false,
  });

  const { data: appointments, isLoading: apptLoading } = useQuery({
    queryKey: ["patient-appointments"],
    queryFn: appointmentService.getAll,
    retry: false,
  });

  return (
    <DashboardLayout title="Patient Dashboard">
      <div className="grid grid-cols-2 lg:grid-cols-4 gap-4">
        <StatCard
          index={0}
          label="Upcoming Appointments"
          value={appointments?.length ?? 0}
          icon={Calendar}
          accent="primary"
        />
        <StatCard
          index={1}
          label="Active Orders"
          value={orders?.length ?? 0}
          icon={ShoppingBag}
          accent="accent"
        />
        <StatCard index={2} label="Medicines Due" value={3} icon={Pill} accent="warning" trend="Refill soon" />
        <StatCard index={3} label="Health Score" value={86} icon={HeartPulse} accent="success" trend="+4%" trendUp />
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-3 gap-5 mt-6">
        <Card className="lg:col-span-2">
          <div className="flex items-center justify-between mb-1">
            <p className="font-display font-semibold text-ink-900">Blood pressure trend</p>
            <Badge tone="success">Stable</Badge>
          </div>
          <p className="text-xs text-ink-500 mb-4">Last 7 days, systolic (mmHg)</p>

          <ResponsiveContainer width="100%" height={220}>
            <LineChart data={vitalsTrend}>
              <CartesianGrid strokeDasharray="3 3" stroke={colors.background} vertical={false} />
              <XAxis dataKey="day" tick={{ fontSize: 12, fill: colors.ink500 }} axisLine={false} tickLine={false} />
              <YAxis domain={[100, 130]} tick={{ fontSize: 12, fill: colors.ink500 }} axisLine={false} tickLine={false} />
              <Tooltip contentStyle={{ borderRadius: 12, border: `1px solid #EDEEF5` }} />
              <Line type="monotone" dataKey="bp" stroke={colors.primary} strokeWidth={2.5} dot={{ r: 3 }} />
            </LineChart>
          </ResponsiveContainer>
        </Card>

        <Card className="bg-gradient-primary text-white flex flex-col justify-between">
          <div>
            <div className="w-10 h-10 rounded-xl bg-white/20 flex items-center justify-center mb-4">
              <Sparkles size={18} />
            </div>
            <p className="font-display font-bold text-lg">Not feeling well?</p>
            <p className="text-sm text-white/70 mt-1.5">
              Describe your symptoms and get an instant specialization
              recommendation.
            </p>
          </div>
          <Link
            to="/patient/symptom-checker"
            className="mt-6 bg-white text-primary-700 font-medium rounded-xl px-4 py-2.5 text-sm flex items-center justify-center gap-1.5 hover:-translate-y-0.5 transition-transform"
          >
            Check symptoms <ArrowUpRight size={15} />
          </Link>
        </Card>
      </div>

      <Card className="mt-5">
        <div className="flex items-center justify-between mb-4">
          <p className="font-display font-semibold text-ink-900">Recent orders</p>
          <Link to="/patient/orders" className="text-sm text-primary-600 font-medium">
            View all
          </Link>
        </div>

        {ordersLoading ? (
          <TableSkeleton rows={4} />
        ) : (
          <div className="overflow-x-auto">
            <table className="w-full text-sm">
              <thead>
                <tr className="text-left text-ink-500 border-b border-surface-border">
                  <th className="pb-3 font-medium">Order ID</th>
                  <th className="pb-3 font-medium">Amount</th>
                  <th className="pb-3 font-medium">Status</th>
                </tr>
              </thead>
              <tbody>
                {(orders ?? []).slice(0, 5).map((order) => (
                  <tr key={order.id} className="border-b border-surface-border last:border-0">
                    <td className="py-3 font-mono text-ink-900">#{order.id}</td>
                    <td className="py-3 text-ink-700">₹{order.totalAmount}</td>
                    <td className="py-3">
                      <Badge tone={statusTone(order.status)}>{order.status}</Badge>
                    </td>
                  </tr>
                ))}
                {(orders ?? []).length === 0 && (
                  <tr>
                    <td colSpan={3} className="py-8 text-center text-ink-300">
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
