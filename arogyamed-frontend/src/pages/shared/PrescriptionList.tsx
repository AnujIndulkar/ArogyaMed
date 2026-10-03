import { useState } from "react";
import { useForm } from "react-hook-form";
import { FileCheck, FileText, Plus, Check, X, Download, FileUp } from "lucide-react";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { toast } from "sonner";
import { DashboardLayout } from "@/components/layout/DashboardLayout";
import { Card } from "@/components/common/Card";
import { Button } from "@/components/common/Button";
import { Modal } from "@/components/common/Modal";
import { EmptyState, TableSkeleton } from "@/components/common/EmptyState";
import { Badge, statusTone } from "@/components/common/Badge";
import axiosInstance from "@/api/axiosInstance";
import { useAuth } from "@/hooks/useAuth";
import { getMediaUrl } from "@/utils/media";
import type { Prescription } from "@/types/common.types";

interface UploadForm {
  doctorName: string;
  clinicName: string;
  notes: string;
}

interface UploadPrescriptionPayload {
  file: File;
  patientId: number;
  doctorName?: string;
  clinicName?: string;
  notes?: string;
}

async function uploadPrescription(payload: UploadPrescriptionPayload): Promise<Prescription> {
  const formData = new FormData();
  formData.append("file", payload.file);
  formData.append("patientId", String(payload.patientId));
  if (payload.doctorName) formData.append("doctorName", payload.doctorName);
  if (payload.clinicName) formData.append("clinicName", payload.clinicName);
  if (payload.notes) formData.append("notes", payload.notes);

  const response = await axiosInstance.post<Prescription>("/prescriptions/upload", formData, {
    headers: { "Content-Type": "multipart/form-data" },
  });
  return response.data;
}

async function updatePrescriptionStatus(
  id: number,
  status: "VERIFIED" | "REJECTED"
): Promise<Prescription> {
  const response = await axiosInstance.put<Prescription>(
    `/prescriptions/${id}/status`,
    null,
    { params: { status } }
  );
  return response.data;
}

export default function PrescriptionList() {
  const { user } = useAuth();
  const queryClient = useQueryClient();
  const [open, setOpen] = useState(false);
  const [file, setFile] = useState<File | null>(null);
  const { register, handleSubmit, reset } = useForm<UploadForm>();

  const isPharmacist = user?.role === "PHARMACIST";

  const { data: items, isLoading } = useQuery({
    queryKey: ["prescriptions"],
    queryFn: async () => {
      const response = await axiosInstance.get<Prescription[]>("/prescriptions");
      return response.data;
    },
    retry: false,
  });

  const uploadMutation = useMutation({
    mutationFn: uploadPrescription,
    onSuccess: () => {
      toast.success("Prescription uploaded — pending pharmacist review");
      queryClient.invalidateQueries({ queryKey: ["prescriptions"] });
      setOpen(false);
      setFile(null);
      reset();
    },
    onError: () => toast.error("Couldn't upload prescription"),
  });

  const statusMutation = useMutation({
    mutationFn: ({ id, status }: { id: number; status: "VERIFIED" | "REJECTED" }) =>
      updatePrescriptionStatus(id, status),
    onSuccess: () => {
      toast.success("Status updated");
      queryClient.invalidateQueries({ queryKey: ["prescriptions"] });
    },
    onError: () => toast.error("Couldn't update status"),
  });

  const onSubmit = (values: UploadForm) => {
    if (!user) return;
    if (!file) {
      toast.error("Please choose a prescription file to upload");
      return;
    }

    uploadMutation.mutate({
      file,
      patientId: user.userId,
      doctorName: values.doctorName || undefined,
      clinicName: values.clinicName || undefined,
      notes: values.notes || undefined,
    });
  };

  return (
    <DashboardLayout title="Prescriptions">
      <Card>
        <div className="flex items-center justify-between mb-5">
          <p className="text-sm text-ink-500">
            Upload a photo or PDF of your prescription for pharmacist verification.
          </p>
          {!isPharmacist && (
            <Button size="sm" onClick={() => setOpen(true)}>
              <Plus size={15} /> Upload prescription
            </Button>
          )}
        </div>

        {isLoading ? (
          <TableSkeleton rows={4} />
        ) : !items || items.length === 0 ? (
          <EmptyState
            icon={FileCheck}
            title="No prescriptions yet"
            description="Uploaded prescriptions will appear here"
          />
        ) : (
          <div className="flex flex-col divide-y divide-surface-border">
            {items.map((p) => (
              <div key={p.id} className="flex items-center justify-between py-4 gap-3">
                <div className="flex items-center gap-3 min-w-0">
                  <div className="w-10 h-10 rounded-xl bg-primary-50 text-primary-600 flex items-center justify-center shrink-0">
                    <FileText size={18} />
                  </div>
                  <div className="min-w-0">
                    <p className="font-medium text-ink-900 truncate">
                      {p.doctorName || p.diagnosis || `Prescription #${p.id}`}
                    </p>
                    <p className="text-xs text-ink-500 truncate">
                      Patient #{p.patientId}
                      {p.clinicName ? ` · ${p.clinicName}` : ""}
                      {p.notes ? ` · ${p.notes}` : ""}
                    </p>
                    {p.rejectionReason && (
                      <p className="text-xs text-accent-600 mt-0.5">{p.rejectionReason}</p>
                    )}
                  </div>
                </div>

                <div className="flex items-center gap-3 shrink-0">
                  <Badge tone={statusTone(p.status)}>{p.status}</Badge>

                  {p.prescriptionImageUrl && (
                    <a
                      href={getMediaUrl(p.prescriptionImageUrl) ?? "#"}
                      target="_blank"
                      rel="noreferrer"
                      className="text-ink-300 hover:text-primary-600"
                      title="View uploaded file"
                    >
                      <Download size={16} />
                    </a>
                  )}

                  {isPharmacist && p.status === "PENDING" && (
                    <div className="flex items-center gap-2">
                      <button
                        onClick={() => statusMutation.mutate({ id: p.id, status: "VERIFIED" })}
                        className="w-7 h-7 rounded-lg bg-success-50 text-success-600 flex items-center justify-center hover:bg-success-100"
                      >
                        <Check size={14} />
                      </button>
                      <button
                        onClick={() => statusMutation.mutate({ id: p.id, status: "REJECTED" })}
                        className="w-7 h-7 rounded-lg bg-accent-50 text-accent-600 flex items-center justify-center hover:bg-accent-100"
                      >
                        <X size={14} />
                      </button>
                    </div>
                  )}
                </div>
              </div>
            ))}
          </div>
        )}
      </Card>

      <Modal open={open} onClose={() => setOpen(false)} title="Upload prescription">
        <form onSubmit={handleSubmit(onSubmit)} className="flex flex-col gap-4">
          <div className="flex flex-col gap-1.5">
            <label className="text-sm font-medium text-ink-700">Prescription file</label>
            <label
              htmlFor="prescription-file"
              className="flex items-center justify-center gap-2 w-full border-2 border-dashed border-surface-border rounded-xl py-6 text-sm text-ink-500 hover:border-primary-300 hover:text-primary-600 transition-colors cursor-pointer"
            >
              <FileUp size={16} />
              {file ? file.name : "Choose file (image or PDF)"}
            </label>
            <input
              id="prescription-file"
              type="file"
              accept="image/*,.pdf"
              onChange={(e) => setFile(e.target.files?.[0] ?? null)}
              className="hidden"
            />
          </div>

          <div className="flex flex-col gap-1.5">
            <label className="text-sm font-medium text-ink-700">Doctor name (optional)</label>
            <input
              className="w-full rounded-xl border border-surface-border bg-white px-4 py-2.5 text-sm outline-none focus:border-primary-400 focus:ring-2 focus:ring-primary-100"
              placeholder="e.g. Dr. Sharma"
              {...register("doctorName")}
            />
          </div>

          <div className="flex flex-col gap-1.5">
            <label className="text-sm font-medium text-ink-700">Clinic / hospital (optional)</label>
            <input
              className="w-full rounded-xl border border-surface-border bg-white px-4 py-2.5 text-sm outline-none focus:border-primary-400 focus:ring-2 focus:ring-primary-100"
              {...register("clinicName")}
            />
          </div>

          <div className="flex flex-col gap-1.5">
            <label className="text-sm font-medium text-ink-700">Notes (optional)</label>
            <input
              className="w-full rounded-xl border border-surface-border bg-white px-4 py-2.5 text-sm outline-none focus:border-primary-400 focus:ring-2 focus:ring-primary-100"
              {...register("notes")}
            />
          </div>

          <Button type="submit" isLoading={uploadMutation.isPending} className="w-full mt-1">
            Upload
          </Button>
        </form>
      </Modal>
    </DashboardLayout>
  );
}
