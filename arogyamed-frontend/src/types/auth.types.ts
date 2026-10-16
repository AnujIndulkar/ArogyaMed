export type Role =
  | "PATIENT"
  | "DOCTOR"
  | "PHARMACIST"
  | "WHOLESALER"
  | "COMPANY"
  | "DELIVERY_PARTNER"
  | "QUALITY_INSPECTOR"
  | "AMBULANCE_PROVIDER"
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
  emailVerificationToken: string;
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

export interface OtpSendResponse {
  message: string;
  resendAfterSeconds: number;
  expiresInSeconds: number;
}

export interface OtpVerifyResponse {
  verificationToken: string;
  expiresInSeconds: number;
}

export interface AuthUser {
  userId: number;
  fullName: string;
  email: string;
  role: Role;
  profileImageUrl?: string | null;
}