import type { Role } from "@/types/auth.types";
import type { DocumentType } from "@/services/documentService";

export type RegistrationFieldType =
  | "text"
  | "number"
  | "email"
  | "tel"
  | "date"
  | "select"
  | "textarea";

export interface RegistrationFieldOption {
  value: string;
  label: string;
}

export interface RegistrationField {
  name: string;
  label: string;
  type: RegistrationFieldType;
  required?: boolean;
  placeholder?: string;
  options?: RegistrationFieldOption[];
}

export interface RequiredDocumentConfig {
  documentType: DocumentType;
  label: string;
  description?: string;
  reuseFieldAsNumber?: string;
  numberLabel?: string;
  numberMaxLength?: number;
}

export interface RoleRegistrationConfig {
  role: Role;
  title: string;
  subtitle: string;
  profileEndpoint: string;
  isVerificationGated: boolean;
  fields: RegistrationField[];
  requiredDocuments: RequiredDocumentConfig[];
}