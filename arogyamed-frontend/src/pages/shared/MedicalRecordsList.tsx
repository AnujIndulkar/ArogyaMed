import { useState } from "react";
import { useForm } from "react-hook-form";
import { FileText, Plus } from "lucide-react";
import { DashboardLayout } from "@/components/layout/DashboardLayout";
import { Card } from "@/components/common/Card";
import { Button } from "@/components/common/Button";
import { Input } from "@/components/common/Input";
import { Modal } from "@/components/common/Modal";
import { DataTable, type Column } from "@/components/common/DataTable";
import { useCrud } from "@/hooks/useCrud";
import { medicalRecordService } from "@/services/domainServices";
import { useAuth } from "@/hooks/useAuth";
import type { MedicalRecord } from "@/types/common.types";

interface RecordForm {
  patientId: number;
  diagnosis: string;
  treatment: string;
  recordDate: string;
}

export default function MedicalRecordsList() {
  const { user } = useAuth();
  const { items, isLoading, create, isCreating } = useCrud<MedicalRecord>(
    "medical-records",
    medicalRecordService
  );
  const [open, setOpen] = useState(false);
  const { register, handleSubmit, reset } = useForm<RecordForm>();

  const isDoctor = user?.role === "DOCTOR";

  const onSubmit = (values: RecordForm) => {
    create(
      { ...values, patientId: Number(values.patientId) } as Partial<MedicalRecord>,
      { onSuccess: () => { setOpen(false); reset(); } } as any
    );
  };

  const columns: Column<MedicalRecord>[] = [
    { header: "Patient", accessor: (r) => <span className="font-mono">#{r.patientId}</span> },
    { header: "Diagnosis", accessor: (r) => <span className="font-medium text-ink-900">{r.diagnosis}</span> },
    { header: "Treatment", accessor: (r) => r.treatment },
    { header: "Date", accessor: (r) => r.recordDate },
  ];

  return (
    <DashboardLayout title="Medical Records">
      <Card>
        <DataTable
          data={items}
          isLoading={isLoading}
          columns={columns}
          keyField={(r) => r.id}
          searchable={false}
          emptyIcon={FileText}
          emptyTitle="No medical records"
          emptyDescription={isDoctor ? "Add a record after a consultation" : "Your medical history will appear here"}
          headerActions={
            isDoctor && (
              <Button size="sm" onClick={() => setOpen(true)}>
                <Plus size={15} /> Add record
              </Button>
            )
          }
        />
      </Card>

      <Modal open={open} onClose={() => setOpen(false)} title="Add medical record">
        <form onSubmit={handleSubmit(onSubmit)} className="flex flex-col gap-4">
          <Input label="Patient ID" type="number" {...register("patientId", { required: true })} />
          <Input label="Diagnosis" {...register("diagnosis", { required: true })} />
          <Input label="Treatment" {...register("treatment", { required: true })} />
          <Input label="Record date" type="date" {...register("recordDate", { required: true })} />
          <Button type="submit" isLoading={isCreating} className="w-full mt-1">
            Save record
          </Button>
        </form>
      </Modal>
    </DashboardLayout>
  );
}
