import { Badge } from "@/components/common/Badge";
import { Button } from "@/components/common/Button";
import { Card } from "@/components/common/Card";
import { StatCard } from "@/components/common/StatCard";
import { DashboardLayout } from "@/components/layout/DashboardLayout";
import {
  apiMessage,
  providerService,
  type AmbulanceStatus,
} from "@/services/ambulanceService";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { Ambulance, CheckCircle2, Route, Siren } from "lucide-react";
import { Link } from "react-router-dom";
import { toast } from "sonner";

const STATUS_TONE: Record<AmbulanceStatus, "success" | "warning" | "accent"> = {
  AVAILABLE: "success",
  ON_DUTY: "warning",
  MAINTENANCE: "accent",
};

export default function ProviderDashboard() {
  const queryClient = useQueryClient();

  const { data: me } = useQuery({
    queryKey: ["provider-me"],
    queryFn: providerService.me,
    retry: false,
  });

  const { data: summary } = useQuery({
    queryKey: ["provider-summary"],
    queryFn: providerService.summary,
    refetchInterval: 10000,
    retry: false,
  });

  const statusMutation = useMutation({
    mutationFn: (status: "AVAILABLE" | "MAINTENANCE") => providerService.setStatus(status),
    onSuccess: (a) => {
      toast.success(a.status === "AVAILABLE" ? "You are available for requests" : "Marked as under maintenance");
      queryClient.invalidateQueries({ queryKey: ["provider-me"] });
      queryClient.invalidateQueries({ queryKey: ["provider-summary"] });
    },
    onError: (err) => toast.error(apiMessage(err, "Could not change status")),
  });

  const status = me?.status;
  const onDuty = status === "ON_DUTY";

  return (
    <DashboardLayout title="Ambulance Provider Dashboard">
      <div className="grid grid-cols-2 lg:grid-cols-4 gap-4">
        <StatCard index={0} label="Open requests" value={summary?.openRequests ?? 0} icon={Siren} accent="accent" />
        <StatCard index={1} label="Active trips" value={summary?.activeTrips ?? 0} icon={Route} accent="warning" />
        <StatCard index={2} label="Completed trips" value={summary?.completedTrips ?? 0} icon={CheckCircle2} accent="success" />
        <StatCard index={3} label="All trips" value={summary?.totalTrips ?? 0} icon={Ambulance} accent="primary" />
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-5 mt-6">
        <Card>
          <div className="flex items-center justify-between mb-4">
            <p className="font-display font-semibold text-ink-900">Your ambulance</p>
            {status && <Badge tone={STATUS_TONE[status]}>{status.replace("_", " ")}</Badge>}
          </div>

          {me ? (
            <div className="text-sm text-ink-700 flex flex-col gap-1.5">
              <p>Vehicle number: <span className="font-mono">{me.ambulanceNumber}</span></p>
              <p>Registration: <span className="font-mono">{me.registrationNumber || "-"}</span></p>
              <p>Driver: {me.driverName}</p>
              <p>Phone: {me.driverPhone}</p>
              <p>Base location: {me.currentLocation || "-"}</p>
            </div>
          ) : (
            <div className="skeleton h-24 w-full rounded-2xl" />
          )}

          <div className="flex flex-wrap gap-2 mt-5">
            <Button
              size="sm"
              disabled={onDuty || status === "AVAILABLE"}
              isLoading={statusMutation.isPending}
              onClick={() => statusMutation.mutate("AVAILABLE")}
            >
              Go available
            </Button>
            <Button
              size="sm"
              variant="secondary"
              disabled={onDuty || status === "MAINTENANCE"}
              isLoading={statusMutation.isPending}
              onClick={() => statusMutation.mutate("MAINTENANCE")}
            >
              Under maintenance
            </Button>
          </div>

          {onDuty && (
            <p className="text-xs text-ink-500 mt-3">
              You are on a trip. Finish or cancel it in Bookings to change your status.
            </p>
          )}
        </Card>

        <Card>
          <p className="font-display font-semibold text-ink-900 mb-2">What to do</p>
          <ol className="text-sm text-ink-700 list-decimal pl-5 flex flex-col gap-1.5">
            <li>Set yourself to <b>Available</b>.</li>
            <li>
              Open <Link to="/ambulance-provider/requests" className="text-primary-600 font-medium hover:underline">Requests</Link>{" "}
              and accept an emergency. Add an ETA so the patient knows how long to wait.
            </li>
            <li>
              Track the trip in{" "}
              <Link to="/ambulance-provider/bookings" className="text-primary-600 font-medium hover:underline">Bookings</Link>:
              start the trip, then complete it.
            </li>
          </ol>
        </Card>
      </div>
    </DashboardLayout>
  );
}