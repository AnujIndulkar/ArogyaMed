import { ShieldCheck, Check, X } from "lucide-react";
import { DashboardLayout } from "@/components/layout/DashboardLayout";
import { Card } from "@/components/common/Card";
import { DataTable, type Column } from "@/components/common/DataTable";
import { Badge, statusTone } from "@/components/common/Badge";
import { useCrud } from "@/hooks/useCrud";
import { kycService } from "@/services/domainServices";
import type { KYC } from "@/types/common.types";

export default function KYCTable() {
  const { items, isLoading, update } = useCrud<KYC>("admin-kyc", kycService);

  const columns: Column<KYC>[] = [
    { header: "User", accessor: (k) => <span className="font-mono">#{k.userId}</span> },
    { header: "Document type", accessor: (k) => k.documentType },
    { header: "Document number", accessor: (k) => <span className="font-mono text-xs">{k.documentNumber}</span> },
    { header: "Status", accessor: (k) => <Badge tone={statusTone(k.status)}>{k.status}</Badge> },
    {
      header: "",
      accessor: (k) =>
        k.status === "PENDING" ? (
          <div className="flex items-center gap-2">
            <button
              onClick={() => update({ id: k.id, payload: { status: "VERIFIED" } })}
              className="w-7 h-7 rounded-lg bg-success-50 text-success-600 flex items-center justify-center hover:bg-success-100"
            >
              <Check size={14} />
            </button>
            <button
              onClick={() => update({ id: k.id, payload: { status: "REJECTED" } })}
              className="w-7 h-7 rounded-lg bg-accent-50 text-accent-600 flex items-center justify-center hover:bg-accent-100"
            >
              <X size={14} />
            </button>
          </div>
        ) : null,
    },
  ];

  return (
    <DashboardLayout title="KYC Verification">
      <Card>
        <DataTable
          data={items}
          isLoading={isLoading}
          columns={columns}
          keyField={(k) => k.id}
          searchable={false}
          emptyIcon={ShieldCheck}
          emptyTitle="No KYC submissions"
        />
      </Card>
    </DashboardLayout>
  );
}
