import { Badge, statusTone } from "@/components/common/Badge";
import { Button } from "@/components/common/Button";
import { Card } from "@/components/common/Card";
import { EmptyState, TableSkeleton } from "@/components/common/EmptyState";
import { Modal } from "@/components/common/Modal";
import { DashboardLayout } from "@/components/layout/DashboardLayout";
import { useAuth } from "@/hooks/useAuth";
import {
    documentService,
    type DocumentModuleType,
    type DocumentType,
} from "@/services/documentService";
import type { Role } from "@/types/auth.types";
import { getMediaUrl } from "@/utils/media";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { Download, FileCheck, FileText, Plus, ShieldAlert, Trash2 } from "lucide-react";
import { useState } from "react";
import { useForm } from "react-hook-form";
import { useLocation } from "react-router-dom";
import { toast } from "sonner";

const ROLE_TO_MODULE: Partial<Record<Role, DocumentModuleType>> = {
  DOCTOR: "DOCTOR",
  PHARMACIST: "PHARMACIST",
  COMPANY: "COMPANY",
  WHOLESALER: "WHOLESALER",
  DELIVERY_PARTNER: "DELIVERY_PARTNER",
};

const DOCUMENT_TYPE_OPTIONS: { value: DocumentType; label: string }[] = [
  { value: "AADHAAR", label: "Aadhaar Card" },
  { value: "PAN", label: "PAN Card" },
  { value: "MEDICAL_LICENSE", label: "Medical License / Degree" },
  { value: "PHARMACY_LICENSE", label: "Pharmacy License" },
  { value: "DRIVING_LICENSE", label: "Driving License" },
  { value: "GST", label: "GST Certificate" },
  { value: "COMPANY_REGISTRATION", label: "Company Registration" },
  { value: "OTHER", label: "Other" },
];

interface UploadForm {
  documentType: DocumentType;
  documentNumber: string;
  expiryDate: string;
}

export default function DocumentsPage() {
  const { user } = useAuth();
  const location = useLocation();
  const isRequired = (location.state as { verificationRequired?: boolean } | null)?.verificationRequired;
  const queryClient = useQueryClient();
  const [open, setOpen] = useState(false);
  const [file, setFile] = useState<File | null>(null);

  const { register, handleSubmit, reset } = useForm<UploadForm>();

  const { data: documents, isLoading } = useQuery({
    queryKey: ["my-documents", user?.userId],
    queryFn: () => documentService.getByUser(user!.userId),
    enabled: !!user,
    retry: false,
  });

  const uploadMutation = useMutation({
    mutationFn: documentService.upload,
    onSuccess: () => {
      toast.success("Document uploaded — pending verification");
      queryClient.invalidateQueries({ queryKey: ["my-documents"] });
      setOpen(false);
      setFile(null);
      reset();
    },
    onError: () => toast.error("Upload failed"),
  });

  const deleteMutation = useMutation({
    mutationFn: documentService.remove,
    onSuccess: () => {
      toast.success("Document deleted");
      queryClient.invalidateQueries({ queryKey: ["my-documents"] });
    },
    onError: () => toast.error("Couldn't delete document"),
  });

  if (!user) return null;

  const documentModule = ROLE_TO_MODULE[user.role] ?? "OTHER";

  const onSubmit = (values: UploadForm) => {
    if (!file) {
      toast.error("Please select a file");
      return;
    }

    uploadMutation.mutate({
      file,
      documentModule,
      referenceId: user.userId,
      uploadedBy: user.userId,
      documentType: values.documentType,
      documentNumber: values.documentNumber || undefined,
      expiryDate: values.expiryDate || undefined,
    });
  };

  return (
    <DashboardLayout title="My Documents">
      {isRequired && (
        <div className="flex items-start gap-3 bg-warning-50 border border-warning-100 rounded-2xl p-4 mb-5">
          <ShieldAlert size={18} className="text-warning-600 shrink-0 mt-0.5" />
          <div>
            <p className="text-sm font-medium text-ink-900">Verification required</p>
            <p className="text-sm text-ink-500 mt-0.5">
              For patient safety, professional accounts must upload at least one identity or
              license document before accessing the dashboard. Uploads are reviewed by our admin team.
            </p>
          </div>
        </div>
      )}

      <Card>
        <div className="flex items-center justify-between mb-5">
          <p className="text-sm text-ink-500">
            Upload your license, certificate, and identity documents for verification.
          </p>
          <Button size="sm" onClick={() => setOpen(true)}>
            <Plus size={15} /> Upload document
          </Button>
        </div>

        {isLoading ? (
          <TableSkeleton rows={4} />
        ) : !documents || documents.length === 0 ? (
          <EmptyState
            icon={FileCheck}
            title="No documents uploaded yet"
            description="Add your license, degree, or ID proof to get verified"
          />
        ) : (
          <div className="flex flex-col divide-y divide-surface-border">
            {documents.map((doc) => (
              <div key={doc.id} className="flex items-center justify-between py-4">
                <div className="flex items-center gap-3 min-w-0">
                  <div className="w-10 h-10 rounded-xl bg-primary-50 text-primary-600 flex items-center justify-center shrink-0">
                    <FileText size={18} />
                  </div>
                  <div className="min-w-0">
                    <p className="font-medium text-ink-900 truncate">{doc.fileName}</p>
                    <p className="text-xs text-ink-500">
                      {doc.documentType.replace(/_/g, " ")} · {doc.fileSizeText}
                      {doc.documentNumber ? ` · ${doc.documentNumber}` : ""}
                    </p>
                    {doc.rejectionReason && (
                      <p className="text-xs text-accent-600 mt-0.5">{doc.rejectionReason}</p>
                    )}
                  </div>
                </div>

                <div className="flex items-center gap-3 shrink-0">
                  <Badge tone={statusTone(doc.verificationStatus)}>{doc.verificationStatus}</Badge>
                  <a
                   href={getMediaUrl(doc.downloadUrl) ?? "#"}
                    target="_blank"
                    rel="noreferrer"
                    className="text-ink-300 hover:text-primary-600"
                  >
                    <Download size={16} />
                  </a>
                  <button
                    onClick={() => deleteMutation.mutate(doc.id)}
                    className="text-ink-300 hover:text-accent-600"
                  >
                    <Trash2 size={16} />
                  </button>
                </div>
              </div>
            ))}
          </div>
        )}
      </Card>

      <Modal open={open} onClose={() => setOpen(false)} title="Upload document">
        <form onSubmit={handleSubmit(onSubmit)} className="flex flex-col gap-4">
          <div className="flex flex-col gap-1.5">
            <label className="text-sm font-medium text-ink-700">Document type</label>
            <select
              className="w-full rounded-xl border border-surface-border bg-white px-4 py-2.5 text-sm outline-none focus:border-primary-400 focus:ring-2 focus:ring-primary-100"
              {...register("documentType", { required: true })}
            >
              {DOCUMENT_TYPE_OPTIONS.map((opt) => (
                <option key={opt.value} value={opt.value}>{opt.label}</option>
              ))}
            </select>
          </div>

          <div className="flex flex-col gap-1.5">
            <label className="text-sm font-medium text-ink-700">Document number (optional)</label>
            <input
              className="w-full rounded-xl border border-surface-border bg-white px-4 py-2.5 text-sm outline-none focus:border-primary-400 focus:ring-2 focus:ring-primary-100"
              {...register("documentNumber")}
            />
          </div>

          <div className="flex flex-col gap-1.5">
            <label className="text-sm font-medium text-ink-700">Expiry date (optional)</label>
            <input
              type="date"
              className="w-full rounded-xl border border-surface-border bg-white px-4 py-2.5 text-sm outline-none focus:border-primary-400 focus:ring-2 focus:ring-primary-100"
              {...register("expiryDate")}
            />
          </div>

          <div className="flex flex-col gap-1.5">
            <label className="text-sm font-medium text-ink-700">File</label>
            <input
              type="file"
              accept="image/*,.pdf"
              onChange={(e) => setFile(e.target.files?.[0] ?? null)}
              className="w-full text-sm text-ink-500 file:mr-3 file:py-2 file:px-4 file:rounded-xl file:border-0 file:bg-primary-50 file:text-primary-700 file:text-sm file:font-medium"
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