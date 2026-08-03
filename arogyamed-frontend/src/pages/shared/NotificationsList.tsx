import { Card } from "@/components/common/Card";
import { EmptyState, TableSkeleton } from "@/components/common/EmptyState";
import { DashboardLayout } from "@/components/layout/DashboardLayout";
import { useAuth } from "@/hooks/useAuth";
import { notificationService } from "@/services/notificationService";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { motion } from "framer-motion";
import { Bell, BellRing } from "lucide-react";

export default function NotificationsList() {
  const { user } = useAuth();
  const queryClient = useQueryClient();

  const { data: items, isLoading } = useQuery({
    queryKey: ["my-notifications", user?.userId],
    queryFn: () => notificationService.getByUser(user!.userId),
    enabled: !!user,
    retry: false,
  });

  const markReadMutation = useMutation({
    mutationFn: (id: number) => notificationService.markAsRead(id),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["my-notifications"] });
    },
  });

  if (!user) return null;

  return (
    <DashboardLayout title="Notifications">
      <Card>
        {isLoading ? (
          <TableSkeleton rows={5} />
        ) : !items || items.length === 0 ? (
          <EmptyState icon={Bell} title="You're all caught up" description="New notifications will appear here" />
        ) : (
          <div className="flex flex-col divide-y divide-surface-border">
            {items.map((n, i) => (
              <motion.div
                key={n.id}
                initial={{ opacity: 0, x: 8 }}
                animate={{ opacity: 1, x: 0 }}
                transition={{ delay: i * 0.04 }}
                className={`flex items-start gap-3 py-4 px-1 cursor-pointer ${!n.isRead ? "bg-primary-50/40" : ""}`}
                onClick={() => !n.isRead && markReadMutation.mutate(n.id)}
              >
                <div className={`w-9 h-9 rounded-xl flex items-center justify-center shrink-0 ${!n.isRead ? "bg-primary-100 text-primary-600" : "bg-surface-border text-ink-300"}`}>
                  <BellRing size={16} />
                </div>
                <div className="min-w-0">
                  <p className="text-sm font-medium text-ink-900">{n.title}</p>
                  <p className="text-sm text-ink-500 mt-0.5">{n.message}</p>
                  <p className="text-xs text-ink-300 mt-1">{new Date(n.createdAt).toLocaleString()}</p>
                </div>
              </motion.div>
            ))}
          </div>
        )}
      </Card>
    </DashboardLayout>
  );
}