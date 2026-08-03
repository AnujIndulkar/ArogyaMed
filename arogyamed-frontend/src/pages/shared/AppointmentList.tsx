import { useState } from "react";
import { useForm } from "react-hook-form";
import { Calendar, Plus } from "lucide-react";
import { DashboardLayout } from "@/components/layout/DashboardLayout";
import { Card } from "@/components/common/Card";
import { Button } from "@/components/common/Button";
import { Input } from "@/components/common/Input";
import { Modal } from "@/components/common/Modal";
import { DataTable, type Column } from "@/components/common/DataTable";
import { Badge, statusTone } from "@/components/common/Badge";
import { useCrud } from "@/hooks/useCrud";
import { appointmentService } from "@/services/domainServices";
import { useAuth } from "@/hooks/useAuth";
import type { Appointment } from "@/types/common.types";

interface AppointmentForm {
  doctorId: number;
  appointmentDate: string;
  appointmentTime: string;
  reason: string;
}

export default function AppointmentList() {
  const { user } = useAuth();
  const { items, isLoading, create, isCreating, update } = useCrud<Appointment>(
    "appointments",
    appointmentService
  );
  const [open, setOpen] = useState(false);
  const { register, handleSubmit, reset } = useForm<AppointmentForm>();

  const isDoctor = user?.role === "DOCTOR";

  const onSubmit = (values: AppointmentForm) => {
    create(
      {
        doctorId: Number(values.doctorId),
        appointmentDate: values.appointmentDate,
        appointmentTime: values.appointmentTime,
        reason: values.reason,
        status: "PENDING",
      } as Partial<Appointment>,
      { onSuccess: () => { setOpen(false); reset(); } } as any
    );
  };

  const columns: Column<Appointment>[] = [
    { header: "Patient", accessor: (a) => <span className="font-mono">#{a.patientId}</span> },
    { header: "Doctor", accessor: (a) => <span className="font-mono">#{a.doctorId}</span> },
    { header: "Date", accessor: (a) => a.appointmentDate },
    { header: "Time", accessor: (a) => a.appointmentTime },
    { header: "Reason", accessor: (a) => a.reason },
    {
      header: "Status",
      accessor: (a) =>
        isDoctor ? (
          <select
            defaultValue={a.status}
            onChange={(e) =>
              update({ id: a.id, payload: { status: e.target.value as Appointment["status"] } })
            }
            className="text-xs border border-surface-border rounded-lg px-2 py-1.5 outline-none focus:border-primary-400"
          >
            {["PENDING", "CONFIRMED", "COMPLETED", "CANCELLED"].map((s) => (
              <option key={s} value={s}>{s}</option>
            ))}
          </select>
        ) : (
          <Badge tone={statusTone(a.status)}>{a.status}</Badge>
        ),
    },
  ];

  return (
    <DashboardLayout title="Appointments">
      <Card>
        <DataTable
          data={items}
          isLoading={isLoading}
          columns={columns}
          keyField={(a) => a.id}
          searchable={false}
          emptyIcon={Calendar}
          emptyTitle="No appointments yet"
          headerActions={
            !isDoctor && (
              <Button size="sm" onClick={() => setOpen(true)}>
                <Plus size={15} /> Book appointment
              </Button>
            )
          }
        />
      </Card>

      <Modal open={open} onClose={() => setOpen(false)} title="Book an appointment">
        <form onSubmit={handleSubmit(onSubmit)} className="flex flex-col gap-4">
          <Input label="Doctor ID" type="number" {...register("doctorId", { required: true })} />
          <div className="grid grid-cols-2 gap-3">
            <Input label="Date" type="date" {...register("appointmentDate", { required: true })} />
            <Input label="Time" type="time" {...register("appointmentTime", { required: true })} />
          </div>
          <Input label="Reason for visit" {...register("reason", { required: true })} />
          <Button type="submit" isLoading={isCreating} className="w-full mt-1">
            Book appointment
          </Button>
        </form>
      </Modal>
    </DashboardLayout>
  );
}
