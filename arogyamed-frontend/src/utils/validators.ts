import { z } from "zod";

export const loginSchema = z.object({
  email: z.string().min(1, "Email is required").email("Enter a valid email"),
  password: z.string().min(1, "Password is required"),
});

export type LoginFormValues = z.infer<typeof loginSchema>;

export const registerSchema = z.object({
  fullName: z.string().min(2, "Full name must be at least 2 characters").max(100),
  email: z.string().min(1, "Email is required").email("Enter a valid email"),
  password: z.string().min(6, "Password must be at least 6 characters"),
  phoneNumber: z.string().regex(/^[0-9]{10}$/, "Phone number must be exactly 10 digits"),
  role: z.enum([
    "PATIENT",
    "DOCTOR",
    "PHARMACIST",
    "WHOLESALER",
    "COMPANY",
    "DELIVERY_PARTNER",
    "ADMIN",
  ]),
});

export type RegisterFormValues = z.infer<typeof registerSchema>;
