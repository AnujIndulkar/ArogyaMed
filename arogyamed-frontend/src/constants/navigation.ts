import type { Role } from "@/types/auth.types";
import type { LucideIcon } from "lucide-react";
import {
  Ambulance,
  BarChart3,
  Bell,
  Building2,
  Calendar,
  ClipboardList,
  FileCheck,
  FileText,
  LayoutDashboard,
  Package,
  Pill,
  ScanLine,
  ShieldCheck,
  ShoppingCart,
  Siren,
  Sparkles,
  Star,
  Stethoscope,
  Truck,
  User,
  Users,
  Warehouse,
} from "lucide-react";

export interface NavItem {
  label: string;
  path: string;
  icon: LucideIcon;
}

const COMMON_ITEMS: NavItem[] = [
  { label: "Notifications", path: "/notifications", icon: Bell },
  { label: "Profile", path: "/profile", icon: User },
];

export const NAV_ITEMS: Record<Role, NavItem[]> = {
  PATIENT: [
    { label: "Dashboard", path: "/patient/dashboard", icon: LayoutDashboard },
    { label: "Appointments", path: "/patient/appointments", icon: Calendar },
    { label: "Orders", path: "/patient/orders", icon: ShoppingCart },
    { label: "Medicines", path: "/patient/medicines", icon: Pill },
    { label: "Prescriptions", path: "/patient/prescriptions", icon: FileCheck },
    { label: "Medical Records", path: "/patient/medical-records", icon: FileText },
    { label: "Symptom Checker", path: "/patient/symptom-checker", icon: Sparkles },
    { label: "SOS & Ambulance", path: "/ambulance", icon: Siren },
    { label: "Reviews", path: "/patient/reviews", icon: Star },
    ...COMMON_ITEMS,
  ],
  DOCTOR: [
    { label: "Dashboard", path: "/doctor/dashboard", icon: LayoutDashboard },
    { label: "Appointments", path: "/doctor/appointments", icon: Calendar },
    { label: "Medical Records", path: "/doctor/medical-records", icon: FileText },
    { label: "Patients", path: "/doctor/patients", icon: Users },
    { label: "My Documents", path: "/documents", icon: FileCheck },
    ...COMMON_ITEMS,
  ],
  PHARMACIST: [
    { label: "Dashboard", path: "/pharmacist/dashboard", icon: LayoutDashboard },
    { label: "Inventory", path: "/pharmacist/inventory", icon: Package },
    { label: "Orders", path: "/pharmacist/orders", icon: ShoppingCart },
    { label: "Prescriptions", path: "/pharmacist/prescriptions", icon: FileCheck },
    { label: "My Documents", path: "/documents", icon: FileCheck },
    ...COMMON_ITEMS,
  ],
  WHOLESALER: [
    { label: "Dashboard", path: "/wholesaler/dashboard", icon: LayoutDashboard },
    { label: "Inventory", path: "/wholesaler/inventory", icon: Warehouse },
    { label: "Companies", path: "/wholesaler/companies", icon: Building2 },
    { label: "My Documents", path: "/documents", icon: FileCheck },
    ...COMMON_ITEMS,
  ],
  COMPANY: [
    { label: "Dashboard", path: "/company/dashboard", icon: LayoutDashboard },
    { label: "Medicines", path: "/company/medicines", icon: Pill },
    { label: "Barcode / QR", path: "/company/barcodes", icon: ScanLine },
    { label: "My Documents", path: "/documents", icon: FileCheck },
    ...COMMON_ITEMS,
  ],
  DELIVERY_PARTNER: [
    { label: "Dashboard", path: "/delivery/dashboard", icon: LayoutDashboard },
    { label: "Deliveries", path: "/delivery/deliveries", icon: Truck },
    { label: "Ambulance", path: "/ambulance", icon: Ambulance },
    { label: "My Documents", path: "/documents", icon: FileCheck },
    ...COMMON_ITEMS,
  ],
  ADMIN: [
    { label: "Dashboard", path: "/admin/dashboard", icon: LayoutDashboard },
    { label: "Users", path: "/admin/users", icon: Users },
    { label: "Doctors", path: "/admin/doctors", icon: Stethoscope },
    { label: "KYC", path: "/admin/kyc", icon: ShieldCheck },
    { label: "Audit Log", path: "/admin/audit-log", icon: ClipboardList },
    { label: "Reports", path: "/admin/reports", icon: BarChart3 },
    ...COMMON_ITEMS,
  ],
};