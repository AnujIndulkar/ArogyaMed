import { Truck, CheckCircle2, Navigation, Star } from "lucide-react";
import {
  BarChart,
  Bar,
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
import { colors } from "@/theme/tokens";

const deliveryTrend = [
  { day: "Mon", count: 8 },
  { day: "Tue", count: 12 },
  { day: "Wed", count: 6 },
  { day: "Thu", count: 14 },
  { day: "Fri", count: 10 },
  { day: "Sat", count: 16 },
  { day: "Sun", count: 5 },
];

const activeDeliveries = [
  { id: "DEL-2201", destination: "MG Road, Pune", status: "IN_TRANSIT" },
  { id: "DEL-2202", destination: "Baner, Pune", status: "PICKED_UP" },
  { id: "DEL-2203", destination: "Kothrud, Pune", status: "DELIVERED" },
];

export default function DeliveryDashboard() {
  return (
    <DashboardLayout title="Delivery Partner Dashboard">
      <div className="grid grid-cols-2 lg:grid-cols-4 gap-4">
        <StatCard index={0} label="Active Deliveries" value={4} icon={Truck} accent="primary" />
        <StatCard index={1} label="Completed Today" value={11} icon={CheckCircle2} accent="success" trend="+3 vs yesterday" trendUp />
        <StatCard index={2} label="Distance Covered (km)" value={62} icon={Navigation} accent="accent" />
        <StatCard index={3} label="Rating" value={5} icon={Star} accent="warning" />
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-3 gap-5 mt-6">
        <Card className="lg:col-span-2">
          <p className="font-display font-semibold text-ink-900 mb-1">Deliveries this week</p>
          <p className="text-xs text-ink-500 mb-4">Completed drop-offs per day</p>

          <ResponsiveContainer width="100%" height={220}>
            <BarChart data={deliveryTrend}>
              <CartesianGrid strokeDasharray="3 3" stroke={colors.background} vertical={false} />
              <XAxis dataKey="day" tick={{ fontSize: 12, fill: colors.ink500 }} axisLine={false} tickLine={false} />
              <YAxis tick={{ fontSize: 12, fill: colors.ink500 }} axisLine={false} tickLine={false} />
              <Tooltip contentStyle={{ borderRadius: 12, border: "1px solid #EDEEF5" }} />
              <Bar dataKey="count" fill={colors.accent} radius={[8, 8, 0, 0]} />
            </BarChart>
          </ResponsiveContainer>
        </Card>

        <Card>
          <p className="font-display font-semibold text-ink-900 mb-4">Active deliveries</p>
          <div className="flex flex-col gap-3">
            {activeDeliveries.map((d) => (
              <div key={d.id} className="flex items-center justify-between text-sm">
                <div>
                  <p className="font-mono text-ink-900">{d.id}</p>
                  <p className="text-ink-500 text-xs">{d.destination}</p>
                </div>
                <Badge tone={d.status === "DELIVERED" ? "success" : "primary"}>
                  {d.status.replace("_", " ")}
                </Badge>
              </div>
            ))}
          </div>
        </Card>
      </div>
    </DashboardLayout>
  );
}
