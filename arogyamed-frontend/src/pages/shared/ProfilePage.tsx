import { useForm } from "react-hook-form";
import { User, Mail, Phone, Shield } from "lucide-react";
import { toast } from "sonner";
import { DashboardLayout } from "@/components/layout/DashboardLayout";
import { Card } from "@/components/common/Card";
import { Button } from "@/components/common/Button";
import { Input } from "@/components/common/Input";
import { Badge } from "@/components/common/Badge";
import { useAuth } from "@/hooks/useAuth";
import { userService } from "@/services/domainServices";
import { ROLE_LABEL } from "@/constants/roles";
import { useMutation } from "@tanstack/react-query";

interface ProfileForm {
  fullName: string;
  phoneNumber: string;
}

export default function ProfilePage() {
  const { user } = useAuth();

  const { register, handleSubmit } = useForm<ProfileForm>({
    defaultValues: { fullName: user?.fullName, phoneNumber: "" },
  });

  const mutation = useMutation({
    mutationFn: (payload: Partial<ProfileForm>) =>
      userService.update(user!.userId, payload as any),
    onSuccess: () => toast.success("Profile updated"),
    onError: () => toast.error("Couldn't update profile"),
  });

  if (!user) return null;

  const initials = user.fullName
    .split(" ")
    .map((n) => n[0])
    .slice(0, 2)
    .join("")
    .toUpperCase();

  return (
    <DashboardLayout title="Profile">
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-5">
        <Card className="flex flex-col items-center text-center py-10">
          <div className="w-20 h-20 rounded-3xl bg-gradient-primary flex items-center justify-center text-white text-2xl font-display font-bold mb-4">
            {initials}
          </div>
          <p className="font-display font-bold text-lg text-ink-900">{user.fullName}</p>
          <p className="text-sm text-ink-500">{user.email}</p>
          <Badge tone="primary">{ROLE_LABEL[user.role]}</Badge>
        </Card>

        <Card className="lg:col-span-2">
          <p className="font-display font-semibold text-ink-900 mb-5">Account details</p>

          <form
            onSubmit={handleSubmit((values) => mutation.mutate(values))}
            className="flex flex-col gap-4"
          >
            <Input label="Full name" icon={<User size={16} />} {...register("fullName")} />
            <Input label="Email" icon={<Mail size={16} />} value={user.email} disabled />
            <Input label="Phone number" icon={<Phone size={16} />} {...register("phoneNumber")} />
            <div className="flex items-center gap-2 text-xs text-ink-500 bg-surface rounded-xl p-3">
              <Shield size={14} />
              Role and email cannot be changed here — contact support if needed.
            </div>
            <Button type="submit" isLoading={mutation.isPending} className="w-fit">
              Save changes
            </Button>
          </form>
        </Card>
      </div>
    </DashboardLayout>
  );
}
