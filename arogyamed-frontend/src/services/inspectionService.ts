import axiosInstance from "@/api/axiosInstance";

export interface PendingBatch {
  medicineId: number;
  medicineName: string;
  companyName: string;
  batchNumber: string;
  manufacturingDate?: string | null;
  expiryDate?: string | null;
}

export interface InspectionSummary {
  pending: number;
  myApproved: number;
  myRejected: number;
  myTotal: number;
}

export type QualityStatus = "PENDING" | "APPROVED" | "REJECTED";

export interface QualityCheck {
  id: number;
  medicineId: number;
  medicineName: string;
  companyId: number;
  companyName: string;
  inspectorName?: string | null;
  batchNumber: string;
  packagingVerified: boolean;
  sealVerified: boolean;
  temperatureVerified: boolean;
  expiryVerified: boolean;
  inspectorRemarks?: string | null;
  inspectionDate?: string | null;
  qualityStatus: QualityStatus;
}

export interface SubmitInspection {
  medicineId: number;
  packagingVerified: boolean;
  sealVerified: boolean;
  temperatureVerified: boolean;
  expiryVerified: boolean;
  remarks: string;
  approve: boolean;
}

export const inspectionService = {
  pending: (q: string) =>
    axiosInstance
      .get<PendingBatch[]>("/inspections/pending", { params: { q, limit: 50 } })
      .then((r) => r.data),

  mine: () => axiosInstance.get<QualityCheck[]>("/inspections/mine").then((r) => r.data),

  summary: () => axiosInstance.get<InspectionSummary>("/inspections/summary").then((r) => r.data),

  submit: (body: SubmitInspection) =>
    axiosInstance.post<QualityCheck>("/inspections/submit", body).then((r) => r.data),
};

// the backend wraps its messages like: 400 BAD_REQUEST "the real message"
export function inspectionError(err: unknown, fallback: string): string {
  const raw = (err as { response?: { data?: { message?: unknown } } })?.response?.data?.message;
  if (typeof raw !== "string") return fallback;
  const quoted = raw.match(/"([^"]+)"/);
  return quoted ? quoted[1] : raw;
}

export function qualityTone(status: QualityStatus): "success" | "accent" | "warning" {
  if (status === "APPROVED") return "success";
  if (status === "REJECTED") return "accent";
  return "warning";
}

export function checksPassed(c: QualityCheck): number {
  return [c.packagingVerified, c.sealVerified, c.temperatureVerified, c.expiryVerified].filter(Boolean).length;
}