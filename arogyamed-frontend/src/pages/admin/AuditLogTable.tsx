import { ClipboardList } from "lucide-react";
import { DashboardLayout } from "@/components/layout/DashboardLayout";
import { Card } from "@/components/common/Card";
import { DataTable, type Column } from "@/components/common/DataTable";
import { useCrud } from "@/hooks/useCrud";
import { auditLogService } from "@/services/domainServices";
import type { AuditLog } from "@/types/common.types";

export default function AuditLogTable() {
  const { items, isLoading } = useCrud<AuditLog>("admin-audit-log", auditLogService);

  const columns: Column<AuditLog>[] = [
    { header: "Action", accessor: (a) => <span className="font-medium text-ink-900">{a.action}</span> },
    { header: "Performed by", accessor: (a) => a.performedBy },
    { header: "Entity", accessor: (a) => a.entityName ?? "—" },
    { header: "Timestamp", accessor: (a) => new Date(a.timestamp).toLocaleString() },
  ];

  return (
    <DashboardLayout title="Audit Log">
      <Card>
        <DataTable
          data={items}
          isLoading={isLoading}
          columns={columns}
          keyField={(a) => a.id}
          searchPlaceholder="Search activity..."
          emptyIcon={ClipboardList}
          emptyTitle="No activity recorded yet"
        />
      </Card>
    </DashboardLayout>
  );
}
