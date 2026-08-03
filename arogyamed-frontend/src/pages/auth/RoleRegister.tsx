import { zodResolver } from "@hookform/resolvers/zod";
import { useMutation } from "@tanstack/react-query";
import { ArrowLeft, Check, Lock, Mail, Phone, User } from "lucide-react";
import { useState } from "react";
import { useForm } from "react-hook-form";
import { Link, useNavigate, useParams } from "react-router-dom";
import { toast } from "sonner";

import axiosInstance from "@/api/axiosInstance";
import { Button } from "@/components/common/Button";
import { Input } from "@/components/common/Input";
import { DocumentUploadField } from "@/components/registration/DocumentUploadField";
import { REGISTRATION_CONFIG } from "@/constants/registrationConfig";
import { authService } from "@/services/authService";
import type { DocumentModuleType } from "@/services/documentService";
import type { Role } from "@/types/auth.types";
import { registerSchema, type RegisterFormValues } from "@/utils/validators";

const ROLE_SLUG_MAP: Record<string, Role> = {
  patient: "PATIENT",
  doctor: "DOCTOR",
  pharmacist: "PHARMACIST",
  wholesaler: "WHOLESALER",
  company: "COMPANY",
  "delivery-partner": "DELIVERY_PARTNER",
};

export default function RoleRegister() {
  const { role: roleSlug } = useParams<{ role: string }>();
  const navigate = useNavigate();

  const role = roleSlug ? ROLE_SLUG_MAP[roleSlug] : undefined;
  const config = role ? REGISTRATION_CONFIG[role] : undefined;

  const [step, setStep] = useState<1 | 2 | 3>(1);
  const [userId, setUserId] = useState<number | null>(null);
  const [profileValues, setProfileValues] = useState<Record<string, any>>({});
  const [uploadedDocIds, setUploadedDocIds] = useState<number[]>([]);

  const accountForm = useForm<RegisterFormValues>({
    resolver: zodResolver(registerSchema),
    defaultValues: { role },
  });

  const profileForm = useForm<Record<string, any>>();

  const accountMutation = useMutation({
    mutationFn: authService.register,
    onSuccess: (data) => {
      setUserId(data.userId);
      toast.success("Account created — let's finish your profile");
      setStep(config?.fields.length ? 2 : 3);
    },
    onError: (error: any) => {
      toast.error(error?.response?.data?.message || "Registration failed");
    },
  });

  const profileMutation = useMutation({
    mutationFn: (values: Record<string, any>) =>
      axiosInstance.post(config!.profileEndpoint, { userId, ...values }),
    onSuccess: (_, values) => {
      setProfileValues(values);
      if (config!.requiredDocuments.length > 0) {
        setStep(3);
      } else {
        finishRegistration();
      }
    },
    onError: () => toast.error("Couldn't save profile details"),
  });

  if (!config || !role) {
    return (
      <div className="min-h-screen flex items-center justify-center bg-surface">
        <p className="text-ink-500">Unknown role. <Link to="/register" className="text-primary-600">Go back</Link></p>
      </div>
    );
  }

  const finishRegistration = () => {
    toast.success(
      config.isVerificationGated
        ? "Registration complete — sign in to upload any additional documents"
        : "Registration complete — you can sign in now"
    );
    navigate("/login");
  };

  const allDocsUploaded = uploadedDocIds.length >= config.requiredDocuments.length;

  return (
    <div className="min-h-screen bg-surface flex items-center justify-center p-6">
      <div className="w-full max-w-lg">
        <Link to="/register" className="inline-flex items-center gap-1.5 text-sm text-ink-500 hover:text-primary-600 mb-6">
          <ArrowLeft size={15} /> Choose a different role
        </Link>

        <div className="card p-7">
          <div className="flex items-center gap-2 mb-1">
            {[1, 2, 3].filter((s) => s !== 2 || config.fields.length > 0).filter((s) => s !== 3 || config.requiredDocuments.length > 0).map((s) => (
              <div
                key={s}
                className={`h-1.5 flex-1 rounded-full ${step >= s ? "bg-primary-500" : "bg-surface-border"}`}
              />
            ))}
          </div>

          <h1 className="font-display text-2xl font-bold text-ink-900 mt-4">
            {config.title} sign up
          </h1>
          <p className="text-ink-500 text-sm mt-1 mb-6">{config.subtitle}</p>

          {step === 1 && (
            <form
              onSubmit={accountForm.handleSubmit((values) => accountMutation.mutate({ ...values, role }))}
              className="flex flex-col gap-4"
            >
              <Input
                label="Full name"
                icon={<User size={16} />}
                error={accountForm.formState.errors.fullName?.message}
                {...accountForm.register("fullName")}
              />
              <Input
                label="Email"
                type="email"
                icon={<Mail size={16} />}
                error={accountForm.formState.errors.email?.message}
                {...accountForm.register("email")}
              />
              <Input
                label="Phone number"
                icon={<Phone size={16} />}
                error={accountForm.formState.errors.phoneNumber?.message}
                {...accountForm.register("phoneNumber")}
              />
              <Input
                label="Password"
                type="password"
                icon={<Lock size={16} />}
                error={accountForm.formState.errors.password?.message}
                {...accountForm.register("password")}
              />
              <Button type="submit" isLoading={accountMutation.isPending} className="w-full mt-1">
                Continue
              </Button>
            </form>
          )}

          {step === 2 && (
            <form
              onSubmit={profileForm.handleSubmit((values) => profileMutation.mutate(values))}
              className="flex flex-col gap-4"
            >
              {config.fields.map((field) => (
                <div key={field.name} className="flex flex-col gap-1.5">
                  <label className="text-sm font-medium text-ink-700">{field.label}</label>

                  {field.type === "select" ? (
                    <select
                      className="w-full rounded-xl border border-surface-border bg-white px-4 py-2.5 text-sm outline-none focus:border-primary-400 focus:ring-2 focus:ring-primary-100"
                      {...profileForm.register(field.name, { required: field.required })}
                    >
                      <option value="">Select...</option>
                      {field.options?.map((opt) => (
                        <option key={opt.value} value={opt.value}>{opt.label}</option>
                      ))}
                    </select>
                  ) : field.type === "textarea" ? (
                    <textarea
                      rows={3}
                      placeholder={field.placeholder}
                      className="w-full rounded-xl border border-surface-border bg-white px-4 py-2.5 text-sm outline-none focus:border-primary-400 focus:ring-2 focus:ring-primary-100 resize-none"
                      {...profileForm.register(field.name, { required: field.required })}
                    />
                  ) : (
                    <input
                      type={field.type}
                      placeholder={field.placeholder}
                      className="w-full rounded-xl border border-surface-border bg-white px-4 py-2.5 text-sm outline-none focus:border-primary-400 focus:ring-2 focus:ring-primary-100"
                      {...profileForm.register(field.name, { required: field.required })}
                    />
                  )}
                </div>
              ))}

              <Button type="submit" isLoading={profileMutation.isPending} className="w-full mt-1">
                {config.requiredDocuments.length > 0 ? "Continue to documents" : "Finish sign up"}
              </Button>
            </form>
          )}

          {step === 3 && userId && (
            <div className="flex flex-col gap-4">
              {config.requiredDocuments.map((doc) => (
                <DocumentUploadField
                  key={doc.documentType}
                  config={doc}
                  documentModule={role as DocumentModuleType}
                  referenceId={userId}
                  uploadedBy={userId}
                  reuseValue={doc.reuseFieldAsNumber ? profileValues[doc.reuseFieldAsNumber] : undefined}
                  onUploaded={(id) => setUploadedDocIds((prev) => [...prev, id])}
                />
              ))}

              <Button onClick={finishRegistration} disabled={!allDocsUploaded} className="w-full mt-1">
                <Check size={16} /> Finish sign up
              </Button>

              {!allDocsUploaded && (
                <p className="text-xs text-ink-500 text-center">
                  Upload all documents above to continue
                </p>
              )}
            </div>
          )}
        </div>
      </div>
    </div>
  );
}