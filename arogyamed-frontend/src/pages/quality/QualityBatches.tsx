import { Badge } from "@/components/common/Badge";
import { Card } from "@/components/common/Card";
import { DataTable, type Column } from "@/components/common/DataTable";
import { DashboardLayout } from "@/components/layout/DashboardLayout";
import {
  checksPassed,
  inspectionService,
  qualityTone,
  type QualityCheck,
  type QualityStatus,
} from "@/services/inspectionService";
import { useQuery } from "@tanstack/react-query";
import { Package } from "lucide-react";
import { useMemo, useState } from "react";

type Filter = "ALL" | QualityStatus;

const FILTERS: { key: Filter; label: string }[] = [
  { key: "ALL", label: "All" },
  { key: "APPROVED", label: "Approved" },
  { key: "REJECTED", label: "Rejected" },
];

export default function QualityBatches() {
  const [filter, setFilter] = useState<Filter>("ALL");

  const { data, isLoading } = useQuery({
    queryKey: ["inspections-mine"],
    queryFn: inspectionService.mine,
    retry: false,
  });

  const rows = useMemo(
    () => (data ?? []).filter((c) => filter === "ALL" || c.qualityStatus === filter),
    [data, filter]
  );

  const columns: Column<QualityCheck>[] = [
    {
      header: "Medicine",
      accessor: (c) => <span className="font-medium text-ink-900">{c.medicineName}</span>,
    },
    { header: "Company", accessor: (c) => c.companyName },
    { header: "Batch", accessor: (c) => <span className="font-mono text-xs">{c.batchNumber}</span> },
    { header: "Checks", accessor: (c) => `${checksPassed(c)} / 4 passed` },
    { header: "Date", accessor: (c) => c.inspectionDate ?? "—" },
    {
      header: "Result",
      accessor: (c) => <Badge tone={qualityTone(c.qualityStatus)}>{c.qualityStatus}</Badge>,
    },
    {
      header: "Remarks",
      accessor: (c) => (
        <span className="text-xs text-ink-500 line-clamp-2 max-w-[220px] inline-block">
          {c.inspectorRemarks || "—"}
        </span>
      ),
    },
  ];

  return (
    <DashboardLayout title="Inspected Batches">
      <Card>
        <div className="flex gap-2 mb-5">
          {FILTERS.map((f) => (
            <button
              key={f.key}
              onClick={() => setFilter(f.key)}
              className={`px-3.5 py-1.5 rounded-full text-xs font-medium border transition-colors ${
                filter === f.key
                  ? "bg-primary-50 text-primary-700 border-primary-200"
                  : "bg-white text-ink-500 border-surface-border hover:border-primary-200"
              }`}
            >
              {f.label}
            </button>
          ))}
        </div>

        <DataTable
          data={rows}
          columns={columns}
          isLoading={isLoading}
          keyField={(c) => c.id}
          searchable={false}
          emptyIcon={Package}
          emptyTitle="No inspected batches"
          emptyDescription="Batches you approve or reject will appear here."
        />
      </Card>
    </DashboardLayout>
  );
}