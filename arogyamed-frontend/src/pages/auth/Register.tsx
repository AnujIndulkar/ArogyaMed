import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { useNavigate, Link } from "react-router-dom";
import { Mail, Lock, User, Phone } from "lucide-react";
import { toast } from "sonner";
import { useMutation } from "@tanstack/react-query";

import { AuthLayout } from "@/components/common/AuthLayout";
import { Input } from "@/components/common/Input";
import { Button } from "@/components/common/Button";
import { registerSchema, type RegisterFormValues } from "@/utils/validators";
import { authService } from "@/services/authService";
import { ROLE_OPTIONS, ROLE_LABEL } from "@/constants/roles";

export default function Register() {
  const navigate = useNavigate();

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<RegisterFormValues>({
    resolver: zodResolver(registerSchema),
    defaultValues: { role: "PATIENT" },
  });

  const mutation = useMutation({
    mutationFn: authService.register,
    onSuccess: () => {
      toast.success("Account created — please sign in");
      navigate("/login");
    },
    onError: (error: any) => {
      toast.error(error?.response?.data?.message || "Registration failed");
    },
  });

  const onSubmit = (values: RegisterFormValues) => {
    mutation.mutate(values);
  };

  return (
    <AuthLayout title="Create your account" subtitle="Join ArogyaMed as a patient, doctor, or partner">
      <form onSubmit={handleSubmit(onSubmit)} className="flex flex-col gap-4">
        <Input
          label="Full name"
          placeholder="Jane Doe"
          icon={<User size={16} />}
          error={errors.fullName?.message}
          {...register("fullName")}
        />

        <Input
          label="Email"
          type="email"
          placeholder="you@example.com"
          icon={<Mail size={16} />}
          error={errors.email?.message}
          {...register("email")}
        />

        <Input
          label="Phone number"
          placeholder="9876543210"
          icon={<Phone size={16} />}
          error={errors.phoneNumber?.message}
          {...register("phoneNumber")}
        />

        <Input
          label="Password"
          type="password"
          placeholder="••••••••"
          icon={<Lock size={16} />}
          error={errors.password?.message}
          {...register("password")}
        />

        <div className="flex flex-col gap-1.5">
          <label className="text-sm font-medium text-ink-700">I am a...</label>
          <select
            className="w-full rounded-xl border border-surface-border bg-white px-4 py-2.5 text-sm text-ink-900 outline-none focus:border-primary-400 focus:ring-2 focus:ring-primary-100"
            {...register("role")}
          >
            {ROLE_OPTIONS.map((role) => (
              <option key={role} value={role}>
                {ROLE_LABEL[role]}
              </option>
            ))}
          </select>
        </div>

        <Button type="submit" isLoading={mutation.isPending} className="w-full mt-2">
          Create account
        </Button>
      </form>

      <p className="text-center text-sm text-ink-500 mt-6">
        Already have an account?{" "}
        <Link to="/login" className="text-primary-600 font-medium hover:underline">
          Sign in
        </Link>
      </p>
    </AuthLayout>
  );
}
