import type { Role } from "@/types/auth.types";

export const ROLE_DASHBOARD_PATH: Record<Role, string> = {
  PATIENT: "/patient/dashboard",
  DOCTOR: "/doctor/dashboard",
  PHARMACIST: "/pharmacist/dashboard",
  WHOLESALER: "/wholesaler/dashboard",
  COMPANY: "/company/dashboard",
  DELIVERY_PARTNER: "/delivery/dashboard",
  ADMIN: "/admin/dashboard",
};

export const ROLE_LABEL: Record<Role, string> = {
  PATIENT: "Patient",
  DOCTOR: "Doctor",
  PHARMACIST: "Pharmacist",
  WHOLESALER: "Wholesaler",
  COMPANY: "Company",
  DELIVERY_PARTNER: "Delivery Partner",
  ADMIN: "Admin",
};

export const ROLE_OPTIONS: Role[] = [
  "PATIENT",
  "DOCTOR",
  "PHARMACIST",
  "WHOLESALER",
  "COMPANY",
  "DELIVERY_PARTNER",
  "ADMIN",
];
