import { useState } from "react";
import { useForm } from "react-hook-form";
import { Pill, Plus } from "lucide-react";
import { DashboardLayout } from "@/components/layout/DashboardLayout";
import { Card } from "@/components/common/Card";
import { Button } from "@/components/common/Button";
import { Input } from "@/components/common/Input";
import { Modal } from "@/components/common/Modal";
import { DataTable, type Column } from "@/components/common/DataTable";
import { Badge } from "@/components/common/Badge";
import { useCrud } from "@/hooks/useCrud";
import { medicineService } from "@/services/domainServices";
import type { Medicine } from "@/types/common.types";

interface MedicineForm {
  medicineName: string;
  category: string;
  price: number;
  stockQuantity: number;
  batchNumber: string;
  manufacturingDate: string;
  expiryDate: string;
}

export default function MedicineList() {
  const { items, isLoading, create, isCreating } = useCrud<Medicine>("medicines", medicineService);
  const [open, setOpen] = useState(false);

  const { register, handleSubmit, reset } = useForm<MedicineForm>();

  const onSubmit = (values: MedicineForm) => {
    create(
      { ...values, price: Number(values.price), stockQuantity: Number(values.stockQuantity) } as Partial<Medicine>,
      { onSuccess: () => { setOpen(false); reset(); } } as any
    );
  };

  const columns: Column<Medicine>[] = [
    { header: "Medicine", accessor: (m) => <span className="font-medium text-ink-900">{m.medicineName}</span> },
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
          <Input label="Medicine name" {...register("medicineName", { required: true })} />
          <Input label="Category" {...register("category", { required: true })} />
          <div className="grid grid-cols-2 gap-3">
            <Input label="Price (₹)" type="number" step="0.01" {...register("price", { required: true })} />
            <Input label="Stock quantity" type="number" {...register("stockQuantity", { required: true })} />
          </div>
          <Input label="Batch number" {...register("batchNumber", { required: true })} />
          <div className="grid grid-cols-2 gap-3">
            <Input label="Manufacturing date" type="date" {...register("manufacturingDate", { required: true })} />
            <Input label="Expiry date" type="date" {...register("expiryDate", { required: true })} />
          </div>
          <Button type="submit" isLoading={isCreating} className="w-full mt-1">
            Add medicine
          </Button>
        </form>
      </Modal>
    </DashboardLayout>
  );
}
