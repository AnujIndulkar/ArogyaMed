import axiosInstance from "@/api/axiosInstance";

export type UrgencyLevel = "LOW" | "MEDIUM" | "HIGH" | "EMERGENCY";

export interface SymptomCheckRequest {
  patientId?: number;
  symptoms: string;
  age?: number;
  gender?: string;
}

export interface RecommendedDoctor {
  id: number;
  fullName: string;
  specialization: string;
  qualification: string;
  experienceYears: number;
  hospitalName: string;
  consultationFee: number;
}

export interface SymptomCheckResponse {
  inputSymptoms: string;
  possibleConditions: string[];
  recommendedSpecialization: string;
  urgencyLevel: UrgencyLevel;
  recommendedDoctors: RecommendedDoctor[];
  aiGenerated: boolean;
  disclaimer: string;
}

export const aiService = {
  checkSymptoms: async (data: SymptomCheckRequest): Promise<SymptomCheckResponse> => {
    const response = await axiosInstance.post<SymptomCheckResponse>(
      "/ai/symptom-checker",
      data
    );
    return response.data;
  },
};
