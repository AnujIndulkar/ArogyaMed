import { documentService, type DocumentModuleType } from "@/services/documentService";
import type { RequiredDocumentConfig } from "@/types/registration.types";
import { useMutation } from "@tanstack/react-query";
import { CheckCircle2, FileUp, Fingerprint, Trash2 } from "lucide-react";
import { useRef, useState } from "react";
import { toast } from "sonner";

interface Props {
  config: RequiredDocumentConfig;
  documentModule: DocumentModuleType;
  referenceId: number;
  uploadedBy: number;
  reuseValue?: string;
  onUploaded: (documentId: number) => void;
}

export function DocumentUploadField({
  config,
  documentModule,
  referenceId,
  uploadedBy,
  reuseValue,
  onUploaded,
}: Props) {
  const inputRef = useRef<HTMLInputElement>(null);

  const [tab, setTab] = useState<"manual" | "digilocker">("manual");
  const [manualNumber, setManualNumber] = useState("");
  const [uploadedDoc, setUploadedDoc] = useState<{ id: number; fileName: string } | null>(null);

  const uploadMutation = useMutation({
    mutationFn: (file: File) =>
      documentService.upload({
        file,
        documentModule,
        referenceId,
        uploadedBy,
        documentType: config.documentType,
        documentNumber: config.reuseFieldAsNumber ? reuseValue : manualNumber || undefined,
      }),
    onSuccess: (doc) => {
      setUploadedDoc({ id: doc.id, fileName: doc.fileName });
      onUploaded(doc.id);
      toast.success(`${config.label} uploaded`);
    },
    onError: () => toast.error(`Couldn't upload ${config.label}`),
  });

  const deleteMutation = useMutation({
    mutationFn: (id: number) => documentService.remove(id),
    onSuccess: () => {
      setUploadedDoc(null);
      toast.success("Document removed — you can upload again");
    },
    onError: () => toast.error("Couldn't remove document"),
  });

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (file) uploadMutation.mutate(file);
  };

  return (
    <div className="border border-surface-border rounded-2xl p-4">
      <p className="text-sm font-medium text-ink-900">{config.label}</p>
      {config.description && <p className="text-xs text-ink-500 mt-0.5">{config.description}</p>}

      {uploadedDoc ? (
        <div className="mt-3 flex items-center justify-between bg-success-50 rounded-xl px-3 py-2.5">
          <div className="flex items-center gap-2 min-w-0">
            <CheckCircle2 size={16} className="text-success-600 shrink-0" />
            <span className="text-sm text-success-700 truncate">{uploadedDoc.fileName}</span>
          </div>
          <button
            type="button"
            onClick={() => deleteMutation.mutate(uploadedDoc.id)}
            className="text-ink-300 hover:text-accent-600 shrink-0"
          >
            <Trash2 size={15} />
          </button>
        </div>
      ) : (
        <>
          <div className="flex items-center gap-1.5 mt-3 mb-3">
            <button
              type="button"
              onClick={() => setTab("manual")}
              className={`text-xs font-medium px-3 py-1.5 rounded-lg transition-colors ${
                tab === "manual" ? "bg-primary-50 text-primary-700" : "text-ink-500 hover:bg-surface"
              }`}
            >
              Upload manually
            </button>
            <button
              type="button"
              onClick={() => setTab("digilocker")}
              className={`text-xs font-medium px-3 py-1.5 rounded-lg transition-colors ${
                tab === "digilocker" ? "bg-primary-50 text-primary-700" : "text-ink-500 hover:bg-surface"
              }`}
            >
              Verify via DigiLocker
            </button>
          </div>

          {tab === "manual" ? (
            <div className="flex flex-col gap-2.5">
              {!config.reuseFieldAsNumber && config.numberLabel && (
                <input
                  value={manualNumber}
                  onChange={(e) => setManualNumber(e.target.value)}
                  maxLength={config.numberMaxLength}
                  placeholder={config.numberLabel}
                  className="w-full rounded-xl border border-surface-border bg-white px-3.5 py-2 text-sm outline-none focus:border-primary-400"
                />
              )}

              <button
                type="button"
                onClick={() => inputRef.current?.click()}
                disabled={uploadMutation.isPending}
                className="flex items-center justify-center gap-2 w-full border-2 border-dashed border-surface-border rounded-xl py-4 text-sm text-ink-500 hover:border-primary-300 hover:text-primary-600 transition-colors disabled:opacity-50"
              >
                <FileUp size={16} />
                {uploadMutation.isPending ? "Uploading..." : "Choose file (image or PDF)"}
              </button>

              <input
                ref={inputRef}
                type="file"
                accept="image/*,.pdf"
                className="hidden"
                onChange={handleFileChange}
              />
            </div>
          ) : (
            <div className="flex flex-col items-center gap-2 border-2 border-dashed border-surface-border rounded-xl py-6 px-4 text-center">
              <Fingerprint size={22} className="text-ink-300" />
              <p className="text-xs text-ink-500">
                DigiLocker verification is coming soon. Please use manual upload for now.
              </p>
              <button
                type="button"
                disabled
                className="text-xs font-medium bg-surface text-ink-300 px-4 py-2 rounded-lg cursor-not-allowed"
              >
                Connect DigiLocker
              </button>
            </div>
          )}
        </>
      )}
    </div>
  );
}