import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { useNavigate, Link } from "react-router-dom";
import { Mail, Lock } from "lucide-react";
import { toast } from "sonner";
import { useMutation } from "@tanstack/react-query";

import { AuthLayout } from "@/components/common/AuthLayout";
import { Input } from "@/components/common/Input";
import { Button } from "@/components/common/Button";
import { loginSchema, type LoginFormValues } from "@/utils/validators";
import { authService } from "@/services/authService";
import { useAuth } from "@/hooks/useAuth";
import { ROLE_DASHBOARD_PATH } from "@/constants/roles";

export default function Login() {
  const navigate = useNavigate();
  const { login } = useAuth();

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<LoginFormValues>({
    resolver: zodResolver(loginSchema),
  });

  const mutation = useMutation({
    mutationFn: authService.login,
    onSuccess: (data) => {
      login(data);
      toast.success(`Welcome back, ${data.fullName.split(" ")[0]}`);
      navigate(ROLE_DASHBOARD_PATH[data.role]);
    },
    onError: (error: any) => {
      toast.error(error?.response?.data?.message || "Invalid email or password");
    },
  });

  const onSubmit = (values: LoginFormValues) => {
    mutation.mutate(values);
  };

  return (
    <AuthLayout title="Welcome back" subtitle="Sign in to continue to your dashboard">
      <form onSubmit={handleSubmit(onSubmit)} className="flex flex-col gap-4">
        <Input
          label="Email"
          type="email"
          placeholder="you@example.com"
          icon={<Mail size={16} />}
          error={errors.email?.message}
          {...register("email")}
        />

        <Input
          label="Password"
          type="password"
          placeholder="••••••••"
          icon={<Lock size={16} />}
          error={errors.password?.message}
          {...register("password")}
        />

        <Button type="submit" isLoading={mutation.isPending} className="w-full mt-2">
          Sign in
        </Button>
      </form>

      <p className="text-center text-sm text-ink-500 mt-6">
        Don't have an account?{" "}
        <Link to="/register" className="text-primary-600 font-medium hover:underline">
          Create one
        </Link>
      </p>
    </AuthLayout>
  );
}
