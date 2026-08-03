import { useQuery } from "@tanstack/react-query";
import { Calendar, Users, IndianRupee, Star } from "lucide-react";
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
import { Badge, statusTone } from "@/components/common/Badge";
import { TableSkeleton } from "@/components/common/EmptyState";
import { appointmentService } from "@/services/domainServices";
import { colors } from "@/theme/tokens";

const weeklyAppointments = [
  { day: "Mon", count: 6 },
  { day: "Tue", count: 9 },
  { day: "Wed", count: 5 },
  { day: "Thu", count: 11 },
  { day: "Fri", count: 8 },
  { day: "Sat", count: 4 },
  { day: "Sun", count: 2 },
];

export default function DoctorDashboard() {
  const { data: appointments, isLoading } = useQuery({
    queryKey: ["doctor-appointments"],
    queryFn: appointmentService.getAll,
    retry: false,
  });

  return (
    <DashboardLayout title="Doctor Dashboard">
      <div className="grid grid-cols-2 lg:grid-cols-4 gap-4">
        <StatCard index={0} label="Today's Appointments" value={appointments?.length ?? 0} icon={Calendar} accent="primary" />
        <StatCard index={1} label="Total Patients" value={214} icon={Users} accent="accent" trend="+12 this month" trendUp />
        <StatCard index={2} label="Consultation Revenue" value={48200} icon={IndianRupee} accent="success" trend="+8%" trendUp />
        <StatCard index={3} label="Patient Rating" value={5} icon={Star} accent="warning" />
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-3 gap-5 mt-6">
        <Card className="lg:col-span-2">
          <p className="font-display font-semibold text-ink-900 mb-1">Appointments this week</p>
          <p className="text-xs text-ink-500 mb-4">Consultations booked per day</p>

          <ResponsiveContainer width="100%" height={220}>
            <BarChart data={weeklyAppointments}>
              <CartesianGrid strokeDasharray="3 3" stroke={colors.background} vertical={false} />
              <XAxis dataKey="day" tick={{ fontSize: 12, fill: colors.ink500 }} axisLine={false} tickLine={false} />
              <YAxis tick={{ fontSize: 12, fill: colors.ink500 }} axisLine={false} tickLine={false} />
              <Tooltip contentStyle={{ borderRadius: 12, border: "1px solid #EDEEF5" }} />
              <Bar dataKey="count" fill={colors.primary} radius={[8, 8, 0, 0]} />
            </BarChart>
          </ResponsiveContainer>
        </Card>

        <Card>
          <p className="font-display font-semibold text-ink-900 mb-4">Specialization</p>
          <div className="flex flex-col gap-3 text-sm">
            <div className="flex justify-between">
              <span className="text-ink-500">Hospital</span>
              <span className="font-medium text-ink-900">City Care Hospital</span>
            </div>
            <div className="flex justify-between">
              <span className="text-ink-500">Experience</span>
              <span className="font-medium text-ink-900">9 years</span>
            </div>
            <div className="flex justify-between">
              <span className="text-ink-500">Consultation Fee</span>
              <span className="font-medium text-ink-900">₹500</span>
            </div>
            <div className="flex justify-between">
              <span className="text-ink-500">License</span>
              <Badge tone="success">Verified</Badge>
            </div>
          </div>
        </Card>
      </div>

      <Card className="mt-5">
        <p className="font-display font-semibold text-ink-900 mb-4">Upcoming appointments</p>

        {isLoading ? (
          <TableSkeleton rows={4} />
        ) : (
          <div className="overflow-x-auto">
            <table className="w-full text-sm">
              <thead>
                <tr className="text-left text-ink-500 border-b border-surface-border">
                  <th className="pb-3 font-medium">Patient ID</th>
                  <th className="pb-3 font-medium">Date</th>
                  <th className="pb-3 font-medium">Reason</th>
                  <th className="pb-3 font-medium">Status</th>
                </tr>
              </thead>
              <tbody>
                {(appointments ?? []).slice(0, 6).map((a) => (
                  <tr key={a.id} className="border-b border-surface-border last:border-0">
                    <td className="py-3 font-mono text-ink-900">#{a.patientId}</td>
                    <td className="py-3 text-ink-700">{a.appointmentDate}</td>
                    <td className="py-3 text-ink-700">{a.reason}</td>
                    <td className="py-3">
                      <Badge tone={statusTone(a.status)}>{a.status}</Badge>
                    </td>
                  </tr>
                ))}
                {(appointments ?? []).length === 0 && (
                  <tr>
                    <td colSpan={4} className="py-8 text-center text-ink-300">
                      No appointments scheduled
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
