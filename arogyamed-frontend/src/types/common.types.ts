export interface Patient {
  userId: number;
  fullName: string;
  age: number;
  gender: string;
  bloodGroup?: string;
  city?: string;
}

export interface Doctor {
  id: number;
  userId: number;
  fullName: string;
  specialization: string;
  qualification: string;
  experienceYears: number;
  hospitalName: string;
  consultationFee: number;
}

export interface Appointment {
  id: number;
  patientId: number;
  doctorId: number;
  appointmentDate: string;
  appointmentTime: string;
  reason: string;
  status: "PENDING" | "CONFIRMED" | "COMPLETED" | "CANCELLED";
}

export interface Order {
  id: number;
  patientId: number;
  totalAmount: number;
  status: "PENDING" | "CONFIRMED" | "SHIPPED" | "DELIVERED" | "CANCELLED";
  createdAt?: string;
}

export interface Ambulance {
  id: number;
  ambulanceNumber: string;
  driverName: string;
  driverPhone: string;
  available: boolean;
  status: string;
}

export interface SOSRequest {
  id: number;
  patientId: number;
  emergencyType: string;
  location: string;
  latitude: number;
  longitude: number;
  status: string;
  createdAt: string;
}

export interface PaginatedResult<T> {
  content: T[];
  totalElements: number;
}

export interface AppUser {
  id: number;
  fullName: string;
  email: string;
  phoneNumber: string;
  role: string;
  active?: boolean;
  createdAt?: string;
}

export interface KYC {
  id: number;
  userId: number;
  documentType: string;
  documentNumber: string;
  status: "PENDING" | "VERIFIED" | "REJECTED";
  submittedAt?: string;
}

export interface AuditLog {
  id: number;
  action: string;
  performedBy: string;
  entityName?: string;
  timestamp: string;
  details?: string;
}

export interface Review {
  id: number;
  patientId: number;
  targetName?: string;
  rating: number;
  comment: string;
  createdAt?: string;
}

export interface Prescription {
  id: number;
  patientId: number;
  doctorId: number;
  medicineDetails: string;
  status: "PENDING" | "VERIFIED" | "REJECTED";
  issuedAt?: string;
}

export interface MedicalRecord {
  id: number;
  patientId: number;
  diagnosis: string;
  treatment: string;
  recordDate: string;
  doctorName?: string;
}

export interface Inventory {
  id: number;
  medicineId: number;
  medicineName?: string;
  quantityAvailable: number;
  minimumStockLevel: number;
}

export interface Medicine {
  id: number;
  medicineName: string;
  category: string;
  price: number;
  stockQuantity: number;
  expiryDate: string;
  manufacturingDate: string;
  batchNumber: string;
  imageUrl?: string | null;
}

export interface AppUser {
  id: number;
  fullName: string;
  email: string;
  phoneNumber: string;
  role: string;
  active?: boolean;
  createdAt?: string;
  profileImageUrl?: string | null;
}

export interface Notification {
  id: number;
  title: string;
  message: string;
  isRead: boolean;
  createdAt: string;
}
