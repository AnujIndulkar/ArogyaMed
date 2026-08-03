export type Role =
  | "PATIENT"
  | "DOCTOR"
  | "PHARMACIST"
  | "WHOLESALER"
  | "COMPANY"
  | "DELIVERY_PARTNER"
  | "ADMIN";

export interface LoginRequest {
  email: string;
  password: string;
}

export interface RegisterRequest {
  fullName: string;
  email: string;
  password: string;
  phoneNumber: string;
  role: Role;
}

export interface LoginResponse {
  token: string;
  userId: number;
  fullName: string;
  email: string;
  role: Role;
  message: string;
}

export interface RegisterResponse {
  userId: number;
  fullName: string;
  email: string;
  message: string;
}

export interface AuthUser {
  userId: number;
  fullName: string;
  email: string;
  role: Role;
}
