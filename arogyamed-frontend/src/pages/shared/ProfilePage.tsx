import { useForm } from "react-hook-form";
import { User, Mail, Phone, Shield, Camera, Trash2 } from "lucide-react";
import { toast } from "sonner";
import { useRef, useState, useEffect } from "react";
import { DashboardLayout } from "@/components/layout/DashboardLayout";
import { Card } from "@/components/common/Card";
import { Button } from "@/components/common/Button";
import { Input } from "@/components/common/Input";
import { Badge } from "@/components/common/Badge";
import { useAuth } from "@/hooks/useAuth";
import { userService } from "@/services/domainServices";
import { mediaService } from "@/services/mediaService";
import { ROLE_LABEL } from "@/constants/roles";
import { getMediaUrl } from "@/utils/media";
import { useMutation, useQuery } from "@tanstack/react-query";

interface ProfileForm {
  fullName: string;
  phoneNumber: string;
}

export default function ProfilePage() {
  const { user, updateUser } = useAuth();
  const fileInputRef = useRef<HTMLInputElement>(null);
  const [previewUrl, setPreviewUrl] = useState<string | null>(null);

  const { data: fullUser } = useQuery({
    queryKey: ["me", user?.userId],
    queryFn: () => userService.getById(user!.userId),
    enabled: !!user,
  });

  useEffect(() => {
    if (fullUser?.profileImageUrl !== undefined) {
      setPreviewUrl(fullUser.profileImageUrl ?? null);
    }
  }, [fullUser?.profileImageUrl]);

  const { register, handleSubmit } = useForm<ProfileForm>({
    defaultValues: { fullName: user?.fullName, phoneNumber: "" },
  });

  const mutation = useMutation({
    mutationFn: (payload: Partial<ProfileForm>) =>
      userService.update(user!.userId, payload as any),
    onSuccess: (_, values) => {
      if (values.fullName) updateUser({ fullName: values.fullName });
      toast.success("Profile updated");
    },
    onError: () => toast.error("Couldn't update profile"),
  });

  const uploadPhotoMutation = useMutation({
    mutationFn: (file: File) => mediaService.uploadProfilePicture(user!.userId, file),
    onSuccess: (data) => {
      setPreviewUrl(data.profileImageUrl ?? null);
      updateUser({ profileImageUrl: data.profileImageUrl });
      toast.success("Profile picture updated");
    },
    onError: () => toast.error("Couldn't upload profile picture"),
  });

  const removePhotoMutation = useMutation({
    mutationFn: () => mediaService.removeProfilePicture(user!.userId),
    onSuccess: () => {
      setPreviewUrl(null);
      updateUser({ profileImageUrl: null });
      toast.success("Profile picture removed");
    },
    onError: () => toast.error("Couldn't remove profile picture"),
  });

  if (!user) return null;

  const initials = user.fullName
    .split(" ")
    .map((n) => n[0])
    .slice(0, 2)
    .join("")
    .toUpperCase();

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (file) uploadPhotoMutation.mutate(file);
    e.target.value = "";
  };

  const photoSrc = getMediaUrl(previewUrl);
  const isBusy = uploadPhotoMutation.isPending || removePhotoMutation.isPending;

  return (
    <DashboardLayout title="Profile">
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-5">
        <Card className="flex flex-col items-center text-center py-10">
          <div className="relative mb-4">
            {photoSrc ? (
              <img
                src={photoSrc}
                alt={user.fullName}
                className="w-20 h-20 rounded-3xl object-cover"
              />
            ) : (
              <div className="w-20 h-20 rounded-3xl bg-gradient-primary flex items-center justify-center text-white text-2xl font-display font-bold">
                {initials}
              </div>
            )}

            <button
              type="button"
              onClick={() => fileInputRef.current?.click()}
              disabled={isBusy}
              title={photoSrc ? "Change photo" : "Add photo"}
              className="absolute -bottom-1.5 -right-1.5 w-7 h-7 rounded-full bg-white shadow-soft border border-surface-border flex items-center justify-center text-ink-500 hover:text-primary-600 disabled:opacity-50"
            >
              <Camera size={13} />
            </button>

            <input
              ref={fileInputRef}
              type="file"
              accept="image/*"
              className="hidden"
              onChange={handleFileChange}
            />
          </div>

          {photoSrc && (
            <button
              type="button"
              onClick={() => removePhotoMutation.mutate()}
              disabled={isBusy}
              className="flex items-center gap-1 text-xs text-ink-500 hover:text-accent-600 mb-3 disabled:opacity-50"
            >
              <Trash2 size={12} /> Remove photo
            </button>
          )}

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