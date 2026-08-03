import { useState } from "react";
import { useForm } from "react-hook-form";
import { FileCheck, Plus, Check, X } from "lucide-react";
import { DashboardLayout } from "@/components/layout/DashboardLayout";
import { Card } from "@/components/common/Card";
import { Button } from "@/components/common/Button";
import { Input } from "@/components/common/Input";
import { Modal } from "@/components/common/Modal";
import { DataTable, type Column } from "@/components/common/DataTable";
import { Badge, statusTone } from "@/components/common/Badge";
import { useCrud } from "@/hooks/useCrud";
import { prescriptionService } from "@/services/domainServices";
import { useAuth } from "@/hooks/useAuth";
import type { Prescription } from "@/types/common.types";

interface PrescriptionForm {
  doctorId: number;
  medicineDetails: string;
}

export default function PrescriptionList() {
  const { user } = useAuth();
  const { items, isLoading, create, isCreating, update } = useCrud<Prescription>(
    "prescriptions",
    prescriptionService
  );
  const [open, setOpen] = useState(false);
  const { register, handleSubmit, reset } = useForm<PrescriptionForm>();

  const isPharmacist = user?.role === "PHARMACIST";

  const onSubmit = (values: PrescriptionForm) => {
    create(
      { ...values, doctorId: Number(values.doctorId), status: "PENDING" } as Partial<Prescription>,
      { onSuccess: () => { setOpen(false); reset(); } } as any
    );
  };

  const columns: Column<Prescription>[] = [
    { header: "Patient", accessor: (p) => <span className="font-mono">#{p.patientId}</span> },
    { header: "Doctor", accessor: (p) => <span className="font-mono">#{p.doctorId}</span> },
    { header: "Details", accessor: (p) => p.medicineDetails },
    { header: "Status", accessor: (p) => <Badge tone={statusTone(p.status)}>{p.status}</Badge> },
    {
      header: "",
      accessor: (p) =>
        isPharmacist && p.status === "PENDING" ? (
          <div className="flex items-center gap-2">
            <button
              onClick={() => update({ id: p.id, payload: { status: "VERIFIED" } })}
              className="w-7 h-7 rounded-lg bg-success-50 text-success-600 flex items-center justify-center hover:bg-success-100"
            >
              <Check size={14} />
            </button>
            <button
              onClick={() => update({ id: p.id, payload: { status: "REJECTED" } })}
              className="w-7 h-7 rounded-lg bg-accent-50 text-accent-600 flex items-center justify-center hover:bg-accent-100"
            >
              <X size={14} />
            </button>
          </div>
        ) : null,
    },
  ];

  return (
    <DashboardLayout title="Prescriptions">
      <Card>
        <DataTable
          data={items}
          isLoading={isLoading}
          columns={columns}
          keyField={(p) => p.id}
          searchable={false}
          emptyIcon={FileCheck}
          emptyTitle="No prescriptions yet"
          headerActions={
            !isPharmacist && (
              <Button size="sm" onClick={() => setOpen(true)}>
                <Plus size={15} /> Upload prescription
              </Button>
            )
          }
        />
      </Card>

      <Modal open={open} onClose={() => setOpen(false)} title="Upload prescription">
        <form onSubmit={handleSubmit(onSubmit)} className="flex flex-col gap-4">
          <Input label="Doctor ID" type="number" {...register("doctorId", { required: true })} />
          <Input label="Medicine details" {...register("medicineDetails", { required: true })} />
          <Button type="submit" isLoading={isCreating} className="w-full mt-1">
            Submit
          </Button>
        </form>
      </Modal>
    </DashboardLayout>
  );
}
