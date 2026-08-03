import { useState } from "react";
import { useForm } from "react-hook-form";
import { Warehouse, Plus, Pencil } from "lucide-react";
import { DashboardLayout } from "@/components/layout/DashboardLayout";
import { Card } from "@/components/common/Card";
import { Button } from "@/components/common/Button";
import { Input } from "@/components/common/Input";
import { Modal } from "@/components/common/Modal";
import { DataTable, type Column } from "@/components/common/DataTable";
import { Badge } from "@/components/common/Badge";
import { useCrud } from "@/hooks/useCrud";
import { inventoryService } from "@/services/domainServices";
import type { Inventory } from "@/types/common.types";

interface InventoryForm {
  medicineId: number;
  quantityAvailable: number;
  minimumStockLevel: number;
}

export default function InventoryList() {
  const { items, isLoading, create, isCreating, update } = useCrud<Inventory>("inventory", inventoryService);
  const [open, setOpen] = useState(false);
  const [editing, setEditing] = useState<Inventory | null>(null);

  const { register, handleSubmit, reset, setValue } = useForm<InventoryForm>();

  const openCreate = () => {
    setEditing(null);
    reset({ medicineId: undefined, quantityAvailable: undefined, minimumStockLevel: undefined });
    setOpen(true);
  };

  const openEdit = (inv: Inventory) => {
    setEditing(inv);
    setValue("medicineId", inv.medicineId);
    setValue("quantityAvailable", inv.quantityAvailable);
    setValue("minimumStockLevel", inv.minimumStockLevel);
    setOpen(true);
  };

  const onSubmit = (values: InventoryForm) => {
    const payload = {
      medicineId: Number(values.medicineId),
      quantityAvailable: Number(values.quantityAvailable),
      minimumStockLevel: Number(values.minimumStockLevel),
    };

    if (editing) {
      update({ id: editing.id, payload }, { onSuccess: () => setOpen(false) } as any);
    } else {
      create(payload, { onSuccess: () => setOpen(false) } as any);
    }
  };

  const columns: Column<Inventory>[] = [
    { header: "Medicine ID", accessor: (i) => <span className="font-mono">#{i.medicineId}</span> },
    { header: "Available", accessor: (i) => i.quantityAvailable },
    { header: "Min. threshold", accessor: (i) => i.minimumStockLevel },
    {
      header: "Status",
      accessor: (i) => (
        <Badge tone={i.quantityAvailable <= i.minimumStockLevel ? "warning" : "success"}>
          {i.quantityAvailable <= i.minimumStockLevel ? "Low stock" : "In stock"}
        </Badge>
      ),
    },
    {
      header: "",
      accessor: (i) => (
        <button onClick={() => openEdit(i)} className="text-ink-300 hover:text-primary-600">
          <Pencil size={15} />
        </button>
      ),
      className: "text-right",
    },
  ];

  return (
    <DashboardLayout title="Inventory">
      <Card>
        <DataTable
          data={items}
          isLoading={isLoading}
          columns={columns}
          keyField={(i) => i.id}
          searchable={false}
          emptyIcon={Warehouse}
          emptyTitle="No inventory records"
          emptyDescription="Add stock records to start tracking"
          headerActions={
            <Button size="sm" onClick={openCreate}>
              <Plus size={15} /> Add stock
            </Button>
          }
        />
      </Card>

      <Modal open={open} onClose={() => setOpen(false)} title={editing ? "Update stock" : "Add stock record"}>
        <form onSubmit={handleSubmit(onSubmit)} className="flex flex-col gap-4">
          <Input label="Medicine ID" type="number" {...register("medicineId", { required: true })} disabled={!!editing} />
          <Input label="Quantity available" type="number" {...register("quantityAvailable", { required: true })} />
          <Input label="Minimum stock level" type="number" {...register("minimumStockLevel", { required: true })} />
          <Button type="submit" isLoading={isCreating} className="w-full mt-1">
            {editing ? "Update" : "Add"}
          </Button>
        </form>
      </Modal>
    </DashboardLayout>
  );
}
