import axiosInstance from "@/api/axiosInstance";

export type DocumentType =
  | "AADHAAR"
  | "PAN"
  | "DRIVING_LICENSE"
  | "MEDICAL_LICENSE"
  | "PHARMACY_LICENSE"
  | "GST"
  | "COMPANY_REGISTRATION"
  | "PASSPORT"
  | "VOTER_ID"
  | "OTHER";

export type DocumentModuleType =
  | "DOCTOR"
  | "PHARMACIST"
  | "COMPANY"
  | "WHOLESALER"
  | "DELIVERY_PARTNER"
  | "PATIENT"
  | "KYC"
  | "OTHER";

export interface DocumentRecord {
  id: number;
  fileName: string;
  fileType: string;
  fileSize: number;
  fileSizeText: string;
  documentModule: DocumentModuleType;
  uploadedAt: string;
  downloadUrl: string;
  documentType: DocumentType;
  documentNumber?: string;
  uploadedById: number;
  uploadedByName: string;
  verificationStatus: "PENDING" | "VERIFIED" | "FAILED" | "REJECTED";
  verifiedByName?: string;
  expiryDate?: string;
  expired?: boolean;
  rejectionReason?: string;
}

export interface UploadDocumentPayload {
  file: File;
  documentModule: DocumentModuleType;
  referenceId: number;
  uploadedBy: number;
  documentType: DocumentType;
  documentNumber?: string;
  expiryDate?: string;
}

export const documentService = {
  getByUser: async (userId: number): Promise<DocumentRecord[]> => {
    const response = await axiosInstance.get<DocumentRecord[]>(`/documents/user/${userId}`);
    return response.data;
  },

  upload: async (payload: UploadDocumentPayload): Promise<DocumentRecord> => {
    const formData = new FormData();
    formData.append("file", payload.file);
    formData.append("documentModule", payload.documentModule);
    formData.append("referenceId", String(payload.referenceId));
    formData.append("uploadedBy", String(payload.uploadedBy));
    formData.append("documentType", payload.documentType);
    if (payload.documentNumber) formData.append("documentNumber", payload.documentNumber);
    if (payload.expiryDate) formData.append("expiryDate", payload.expiryDate);

    const response = await axiosInstance.post<DocumentRecord>("/documents/upload", formData, {
      headers: { "Content-Type": "multipart/form-data" },
    });

    return response.data;
  },
};