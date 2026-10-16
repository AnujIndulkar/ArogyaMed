import axiosInstance from "@/api/axiosInstance";
import type {
  LoginRequest,
  LoginResponse,
  OtpSendResponse,
  OtpVerifyResponse,
  RegisterRequest,
  RegisterResponse,
} from "@/types/auth.types";

export const authService = {
  login: async (data: LoginRequest): Promise<LoginResponse> => {
    const response = await axiosInstance.post<LoginResponse>("/auth/login", data);
    return response.data;
  },

  register: async (data: RegisterRequest): Promise<RegisterResponse> => {
    const response = await axiosInstance.post<RegisterResponse>("/auth/register", data);
    return response.data;
  },

  // email verification (OTP) before registration
  sendEmailOtp: async (email: string): Promise<OtpSendResponse> => {
    const response = await axiosInstance.post<OtpSendResponse>("/auth/otp/send", { email });
    return response.data;
  },

  verifyEmailOtp: async (email: string, otp: string): Promise<OtpVerifyResponse> => {
    const response = await axiosInstance.post<OtpVerifyResponse>("/auth/otp/verify", { email, otp });
    return response.data;
  },
};
