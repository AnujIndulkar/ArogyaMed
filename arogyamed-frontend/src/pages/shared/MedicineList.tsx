import { useState } from "react";
import { useForm } from "react-hook-form";
import { Pill, Plus, FileUp } from "lucide-react";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { toast } from "sonner";
import { DashboardLayout } from "@/components/layout/DashboardLayout";
import { Card } from "@/components/common/Card";
import { Button } from "@/components/common/Button";
import { Input } from "@/components/common/Input";
import { Modal } from "@/components/common/Modal";
import { DataTable, type Column } from "@/components/common/DataTable";
import { Badge } from "@/components/common/Badge";
import { useCrud } from "@/hooks/useCrud";
import { medicineService } from "@/services/domainServices";
import axiosInstance from "@/api/axiosInstance";
import { getMediaUrl } from "@/utils/media";
import type { Medicine } from "@/types/common.types";
import { useAuth } from "@/hooks/useAuth";

interface MedicineForm {
  medicineName: string;
  category: string;
  description: string;
  price: number;
  stockQuantity: number;
  batchNumber: string;
  manufacturingDate: string;
  expiryDate: string;
}

async function uploadMedicineImage(id: number, file: File): Promise<Medicine> {
  const formData = new FormData();
  formData.append("file", file);
  const response = await axiosInstance.post<Medicine>(`/medicines/${id}/image`, formData, {
    headers: { "Content-Type": "multipart/form-data" },
  });
  return response.data;
}

export default function MedicineList() {
   const { user } = useAuth();
  const { create, isCreating } = useCrud<Medicine>("medicines", medicineService);
  const queryClient = useQueryClient();

  // this company's own record (we need its id to create medicines)
  const { data: company } = useQuery({
    queryKey: ["my-company", user?.userId],
    queryFn: async () => {
      const response = await axiosInstance.get<{ id: number; companyName: string }>(`/companies/${user?.userId}`);
      return response.data;
    },
    enabled: !!user?.userId,
    retry: false,
  });

  // only this company's medicines
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
  const [open, setOpen] = useState(false);
  const [imageFile, setImageFile] = useState<File | null>(null);

  const { register, handleSubmit, reset } = useForm<MedicineForm>();

  const imageMutation = useMutation({
    mutationFn: ({ id, file }: { id: number; file: File }) => uploadMedicineImage(id, file),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["medicines"] });
    },
    onError: () => toast.error("Medicine saved, but the image failed to upload"),
  });

  const onSubmit = (values: MedicineForm) => {
    create(
            {
        ...values,
        companyId: company?.id,
        price: Number(values.price),
        stockQuantity: Number(values.stockQuantity),
      } as Partial<Medicine>,
      {
        onSuccess: (created: Medicine) => {
          if (imageFile) {
            imageMutation.mutate({ id: created.id, file: imageFile });
          }
          setOpen(false);
          setImageFile(null);
          reset();
        },
      } as any
    );
  };

  const columns: Column<Medicine>[] = [
    {
      header: "Medicine",
      accessor: (m) => (
        <div className="flex items-center gap-2.5">
          <div className="w-9 h-9 rounded-lg bg-primary-50 flex items-center justify-center shrink-0 overflow-hidden">
            {m.imageUrl ? (
              <img src={getMediaUrl(m.imageUrl) ?? ""} alt={m.medicineName} className="w-full h-full object-cover" />
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
    { header: "Category", accessor: (m) => m.category },
    { header: "Batch", accessor: (m) => <span className="font-mono text-xs">{m.batchNumber}</span> },
    { header: "Price", accessor: (m) => `₹${m.price}` },
    {
      header: "Stock",
      accessor: (m) => (
        <Badge tone={m.stockQuantity < 20 ? "warning" : "success"}>{m.stockQuantity} units</Badge>
      ),
    },
    { header: "Expiry", accessor: (m) => m.expiryDate },
  ];

  return (
    <DashboardLayout title="Medicines">
      <Card>
        <DataTable
          data={items}
          isLoading={isLoading}
          columns={columns}
          keyField={(m) => m.id}
          searchPlaceholder="Search medicines..."
          emptyIcon={Pill}
          emptyTitle="No medicines yet"
          emptyDescription="Add your first medicine to get started"
          headerActions={
            <Button size="sm" onClick={() => setOpen(true)}>
              <Plus size={15} /> Add medicine
            </Button>
          }
        />
      </Card>

      <Modal open={open} onClose={() => setOpen(false)} title="Add medicine">
        <form onSubmit={handleSubmit(onSubmit)} className="flex flex-col gap-4">
          <div className="flex flex-col gap-1.5">
            <label className="text-sm font-medium text-ink-700">Product photo (optional)</label>
            <label
              htmlFor="medicine-image"
              className="flex items-center justify-center gap-2 w-full border-2 border-dashed border-surface-border rounded-xl py-5 text-sm text-ink-500 hover:border-primary-300 hover:text-primary-600 transition-colors cursor-pointer"
            >
              <FileUp size={16} />
              {imageFile ? imageFile.name : "Choose an image"}
            </label>
            <input
              id="medicine-image"
              type="file"
              accept="image/*"
              onChange={(e) => setImageFile(e.target.files?.[0] ?? null)}
              className="hidden"
            />
          </div>

          <Input label="Medicine name" {...register("medicineName", { required: true })} />
          <Input label="Category" {...register("category", { required: true })} />

          <div className="flex flex-col gap-1.5">
            <label className="text-sm font-medium text-ink-700">Description (optional)</label>
            <textarea
              rows={3}
              placeholder="Usage, dosage instructions, key info shown to buyers..."
              className="w-full rounded-xl border border-surface-border bg-white px-4 py-2.5 text-sm outline-none focus:border-primary-400 focus:ring-2 focus:ring-primary-100 resize-none"
              {...register("description")}
            />
          </div>

          <div className="grid grid-cols-2 gap-3">
            <Input label="Price (₹)" type="number" step="0.01" {...register("price", { required: true })} />
            <Input label="Stock quantity" type="number" {...register("stockQuantity", { required: true })} />
          </div>
          <Input label="Batch number" {...register("batchNumber", { required: true })} />
          <div className="grid grid-cols-2 gap-3">
            <Input label="Manufacturing date" type="date" {...register("manufacturingDate", { required: true })} />
            <Input label="Expiry date" type="date" {...register("expiryDate", { required: true })} />
          </div>
          <Button type="submit" isLoading={isCreating || imageMutation.isPending} className="w-full mt-1">
            Add medicine
          </Button>
        </form>
      </Modal>
    </DashboardLayout>
  );
}