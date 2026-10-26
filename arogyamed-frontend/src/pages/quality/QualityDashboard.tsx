import { Badge } from "@/components/common/Badge";
import { Card } from "@/components/common/Card";
import { EmptyState } from "@/components/common/EmptyState";
import { StatCard } from "@/components/common/StatCard";
import { DashboardLayout } from "@/components/layout/DashboardLayout";
import { inspectionService, qualityTone } from "@/services/inspectionService";
import { useQuery } from "@tanstack/react-query";
import { CheckCircle2, ClipboardCheck, ClipboardList, XCircle } from "lucide-react";
import { Link } from "react-router-dom";

export default function QualityDashboard() {
  const { data: summary } = useQuery({
    queryKey: ["inspections-summary"],
    queryFn: inspectionService.summary,
    retry: false,
  });

  const { data: mine, isLoading } = useQuery({
    queryKey: ["inspections-mine"],
    queryFn: inspectionService.mine,
    retry: false,
  });

  const recent = (mine ?? []).slice(0, 5);

  return (
    <DashboardLayout title="Quality Inspector Dashboard">
      <div className="grid grid-cols-2 lg:grid-cols-4 gap-4">
        <StatCard index={0} label="Batches waiting" value={summary?.pending ?? 0} icon={ClipboardList} accent="warning" />
        <StatCard index={1} label="Approved by you" value={summary?.myApproved ?? 0} icon={CheckCircle2} accent="success" />
        <StatCard index={2} label="Rejected by you" value={summary?.myRejected ?? 0} icon={XCircle} accent="accent" />
        <StatCard index={3} label="Total inspections" value={summary?.myTotal ?? 0} icon={ClipboardCheck} accent="primary" />
      </div>

      <Card className="mt-6">
        <div className="flex items-center justify-between mb-4">
          <p className="font-display font-semibold text-ink-900">Recent inspections</p>
          <Link
            to="/quality-inspector/inspections"
            className="text-sm text-primary-600 font-medium hover:underline"
          >
            Inspect batches →
          </Link>
        </div>

        {isLoading ? (
          <div className="skeleton h-24 w-full rounded-2xl" />
        ) : recent.length === 0 ? (
          <EmptyState
            icon={ClipboardCheck}
            title="No inspections yet"
            description="Open Pending Inspections and review your first batch."
          />
        ) : (
          <div className="flex flex-col divide-y divide-surface-border">
            {recent.map((c) => (
              <div key={c.id} className="flex items-center justify-between py-3 gap-3">
                <div className="min-w-0">
                  <p className="text-sm font-medium text-ink-900 truncate">{c.medicineName}</p>
                  <p className="text-xs text-ink-500 truncate">
                    {c.companyName} · {c.batchNumber} · {c.inspectionDate}
                  </p>
                </div>
                <Badge tone={qualityTone(c.qualityStatus)}>{c.qualityStatus}</Badge>
              </div>
            ))}
          </div>
        )}
      </Card>
    </DashboardLayout>
  );
}