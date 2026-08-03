import { Navigate, Route, Routes } from "react-router-dom";
import { ProtectedRoute } from "./ProtectedRoute";
import { RequireVerification } from "./RequireVerification";

import Home from "@/pages/Home";
import Login from "@/pages/auth/Login";
import RoleRegister from "@/pages/auth/RoleRegister";
import RoleSelect from "@/pages/auth/RoleSelect";

import AdminDashboard from "@/pages/admin/AdminDashboard";
import AmbulanceSOS from "@/pages/ambulance/AmbulanceSOS";
import CompanyDashboard from "@/pages/company/CompanyDashboard";
import DeliveryDashboard from "@/pages/delivery/DeliveryDashboard";
import DoctorDashboard from "@/pages/doctor/DoctorDashboard";
import CartPage from "@/pages/patient/CartPage";
import MedicineCatalog from "@/pages/patient/MedicineCatalog";
import PatientDashboard from "@/pages/patient/PatientDashboard";
import SymptomChecker from "@/pages/patient/SymptomChecker";
import PharmacistDashboard from "@/pages/pharmacist/PharmacistDashboard";
import WholesalerDashboard from "@/pages/wholesaler/WholesalerDashboard";

import AppointmentList from "@/pages/shared/AppointmentList";
import DocumentsPage from "@/pages/shared/DocumentsPage";
import InventoryList from "@/pages/shared/InventoryList";
import MedicalRecordsList from "@/pages/shared/MedicalRecordsList";
import MedicineList from "@/pages/shared/MedicineList";
import NotificationsList from "@/pages/shared/NotificationsList";
import OrderList from "@/pages/shared/OrderList";
import PrescriptionList from "@/pages/shared/PrescriptionList";
import ProfilePage from "@/pages/shared/ProfilePage";
import ReviewsList from "@/pages/shared/ReviewsList";

import AuditLogTable from "@/pages/admin/AuditLogTable";
import KYCTable from "@/pages/admin/KYCTable";
import UsersTable from "@/pages/admin/UsersTable";

const Placeholder = ({ label }: { label: string }) => (
  <div className="min-h-screen flex items-center justify-center bg-surface">
    <p className="font-display text-xl text-ink-500">{label}</p>
  </div>
);

export default function AppRoutes() {
  return (
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/login" element={<Login />} />
      <Route path="/register" element={<RoleSelect />} />
      <Route path="/register/:role" element={<RoleRegister />} />

      {/* Accessible to any authenticated role — not verification-gated */}
      <Route element={<ProtectedRoute />}>
        <Route path="/ambulance" element={<AmbulanceSOS />} />
        <Route path="/profile" element={<ProfilePage />} />
        <Route path="/notifications" element={<NotificationsList />} />
        <Route path="/documents" element={<DocumentsPage />} />
      </Route>

      {/* Patient — no verification gate */}
      <Route element={<ProtectedRoute allowedRoles={["PATIENT"]} />}>
        <Route path="/patient/dashboard" element={<PatientDashboard />} />
        <Route path="/patient/symptom-checker" element={<SymptomChecker />} />
        <Route path="/patient/appointments" element={<AppointmentList />} />
        <Route path="/patient/orders" element={<OrderList />} />
        <Route path="/patient/medicines" element={<MedicineCatalog />} />
        <Route path="/patient/cart" element={<CartPage />} />
        <Route path="/patient/prescriptions" element={<PrescriptionList />} />
        <Route path="/patient/medical-records" element={<MedicalRecordsList />} />
        <Route path="/patient/reviews" element={<ReviewsList />} />
      </Route>

      {/* Doctor — verification gated */}
      <Route element={<ProtectedRoute allowedRoles={["DOCTOR"]} />}>
        <Route element={<RequireVerification />}>
          <Route path="/doctor/dashboard" element={<DoctorDashboard />} />
          <Route path="/doctor/appointments" element={<AppointmentList />} />
          <Route path="/doctor/medical-records" element={<MedicalRecordsList />} />
          <Route path="/doctor/patients" element={<Placeholder label="Patients — coming next" />} />
        </Route>
      </Route>

      {/* Pharmacist — verification gated */}
      <Route element={<ProtectedRoute allowedRoles={["PHARMACIST"]} />}>
        <Route element={<RequireVerification />}>
          <Route path="/pharmacist/dashboard" element={<PharmacistDashboard />} />
          <Route path="/pharmacist/inventory" element={<InventoryList />} />
          <Route path="/pharmacist/orders" element={<OrderList />} />
          <Route path="/pharmacist/prescriptions" element={<PrescriptionList />} />
        </Route>
      </Route>

      {/* Wholesaler — verification gated */}
      <Route element={<ProtectedRoute allowedRoles={["WHOLESALER"]} />}>
        <Route element={<RequireVerification />}>
          <Route path="/wholesaler/dashboard" element={<WholesalerDashboard />} />
          <Route path="/wholesaler/inventory" element={<InventoryList />} />
          <Route path="/wholesaler/companies" element={<Placeholder label="Companies — coming next" />} />
        </Route>
      </Route>

      {/* Company — verification gated */}
      <Route element={<ProtectedRoute allowedRoles={["COMPANY"]} />}>
        <Route element={<RequireVerification />}>
          <Route path="/company/dashboard" element={<CompanyDashboard />} />
          <Route path="/company/medicines" element={<MedicineList />} />
          <Route path="/company/barcodes" element={<Placeholder label="Barcode / QR — coming next" />} />
        </Route>
      </Route>

      {/* Delivery Partner — verification gated */}
      <Route element={<ProtectedRoute allowedRoles={["DELIVERY_PARTNER"]} />}>
        <Route element={<RequireVerification />}>
          <Route path="/delivery/dashboard" element={<DeliveryDashboard />} />
          <Route path="/delivery/deliveries" element={<Placeholder label="Deliveries — coming next" />} />
        </Route>
      </Route>

      {/* Admin — no verification gate */}
      <Route element={<ProtectedRoute allowedRoles={["ADMIN"]} />}>
        <Route path="/admin/dashboard" element={<AdminDashboard />} />
        <Route path="/admin/users" element={<UsersTable />} />
        <Route path="/admin/doctors" element={<Placeholder label="Doctors — coming next" />} />
        <Route path="/admin/kyc" element={<KYCTable />} />
        <Route path="/admin/audit-log" element={<AuditLogTable />} />
        <Route path="/admin/reports" element={<Placeholder label="Reports — coming next" />} />
      </Route>

      <Route path="/unauthorized" element={<Placeholder label="You don't have access to this page" />} />
      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
  );
}