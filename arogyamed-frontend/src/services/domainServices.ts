import { createCrudService } from "./crudService";
import type {
  Patient,
  Doctor,
  Medicine,
  Appointment,
  Order,
  Ambulance,
  SOSRequest,
  AppUser,
  KYC,
  AuditLog,
  Notification,
  Review,
  Prescription,
  MedicalRecord,
  Inventory,
} from "@/types/common.types";

export const patientService = createCrudService<Patient>("/patients");
export const doctorService = createCrudService<Doctor>("/doctors");
export const medicineService = createCrudService<Medicine>("/medicines");
export const appointmentService = createCrudService<Appointment>("/appointments");
export const orderService = createCrudService<Order>("/orders");
export const ambulanceService = createCrudService<Ambulance>("/ambulances");
export const sosService = createCrudService<SOSRequest>("/sos");

export const userService = createCrudService<AppUser>("/users");
export const kycService = createCrudService<KYC>("/kyc");
export const auditLogService = createCrudService<AuditLog>("/audit-logs");
export const notificationService = createCrudService<Notification>("/notifications");
export const reviewService = createCrudService<Review>("/reviews");
export const prescriptionService = createCrudService<Prescription>("/prescriptions");
export const medicalRecordService = createCrudService<MedicalRecord>("/medical-records");
export const inventoryService = createCrudService<Inventory>("/inventories");

