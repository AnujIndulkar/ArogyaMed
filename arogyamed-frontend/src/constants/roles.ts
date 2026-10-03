import type { Role } from "@/types/auth.types";

export const ROLE_DASHBOARD_PATH: Record<Role, string> = {
  PATIENT: "/patient/dashboard",
  DOCTOR: "/doctor/dashboard",
  PHARMACIST: "/pharmacist/dashboard",
  WHOLESALER: "/wholesaler/dashboard",
  COMPANY: "/company/dashboard",
  DELIVERY_PARTNER: "/delivery/dashboard",
  QUALITY_INSPECTOR: "/quality-inspector/dashboard",
  AMBULANCE_PROVIDER: "/ambulance-provider/dashboard",
  ADMIN: "/admin/dashboard",
};

export const ROLE_LABEL: Record<Role, string> = {
  PATIENT: "Patient",
  DOCTOR: "Doctor",
  PHARMACIST: "Pharmacist",
  WHOLESALER: "Wholesaler",
  COMPANY: "Company",
  DELIVERY_PARTNER: "Delivery Partner",
  QUALITY_INSPECTOR: "Quality Inspector",
  AMBULANCE_PROVIDER: "Ambulance Provider",
  ADMIN: "Admin",
};

export const ROLE_OPTIONS: Role[] = [
  "PATIENT",
  "DOCTOR",
  "PHARMACIST",
  "WHOLESALER",
  "COMPANY",
  "DELIVERY_PARTNER",
  "QUALITY_INSPECTOR",
  "AMBULANCE_PROVIDER",
  "ADMIN",
];