import { Pill, QrCode } from "lucide-react";
import { useQuery } from "@tanstack/react-query";
import { DashboardLayout } from "@/components/layout/DashboardLayout";
import { Card } from "@/components/common/Card";
import { DataTable, type Column } from "@/components/common/DataTable";
import MedicineCodesButton from "@/components/common/MedicineCodesButton";
import ScanMedicineButton from "@/components/common/ScanMedicineButton";
import axiosInstance from "@/api/axiosInstance";
import { getMediaUrl } from "@/utils/media";
import { useAuth } from "@/hooks/useAuth";
import type { Medicine } from "@/types/common.types";

export default function BarcodePage() {
  const { user } = useAuth();

  // same two lookups as the Medicines page: first this company, then its medicines
  const { data: company } = useQuery({
    queryKey: ["my-company", user?.userId],
    queryFn: async () => {
      const response = await axiosInstance.get<{ id: number; companyName: string }>(
        `/companies/${user?.userId}`
      );
      return response.data;
    },
    enabled: !!user?.userId,
    retry: false,
  });

  const { data: items = [], isLoading } = useQuery({
    queryKey: ["medicines", "company", company?.companyName],
    queryFn: async () => {
      const response = await axiosInstance.get<Medicine[]>("/medicines/search/company", {
        params: { companyName: company?.companyName },
      });
      return response.data;
    },
    enabled: !!company?.companyName,
    retry: false,
  });

  const columns: Column<Medicine>[] = [
    {
      header: "Medicine",
      accessor: (m) => (
        <div className="flex items-center gap-2.5">
          <div className="w-9 h-9 rounded-lg bg-primary-50 flex items-center justify-center shrink-0 overflow-hidden">
            {m.imageUrl ? (
              <img
                src={getMediaUrl(m.imageUrl) ?? ""}
                alt={m.medicineName}
                className="w-full h-full object-cover"
              />
            ) : (
              <Pill size={16} className="text-primary-300" />
            )}
          </div>
          <div className="min-w-0">
            <span className="font-medium text-ink-900 block truncate">{m.medicineName}</span>
            <span className="text-xs text-ink-500 block truncate">{m.packSize}</span>
          </div>
        </div>
      ),
    },
    { header: "Batch", accessor: (m) => <span className="font-mono text-xs">{m.batchNumber}</span> },
    { header: "Expiry", accessor: (m) => m.expiryDate },
    {
      header: "QR & barcode",
      accessor: (m) => <MedicineCodesButton medicineId={m.id} medicineName={m.medicineName} />,
    },
  ];

  return (
    <DashboardLayout title="Barcode / QR">
      {/* NEW: the scan button, shown above the table on the right side */}
      <div className="mb-4 flex justify-end">
        <ScanMedicineButton />
      </div>

      <Card>
        <DataTable
          data={items}
          isLoading={isLoading}
          columns={columns}
          keyField={(m) => m.id}
          searchPlaceholder="Search medicines..."
          emptyIcon={QrCode}
          emptyTitle="No medicines yet"
          emptyDescription="Add a medicine first, then print its QR code and barcode here"
        />
      </Card>
    </DashboardLayout>
  );
}