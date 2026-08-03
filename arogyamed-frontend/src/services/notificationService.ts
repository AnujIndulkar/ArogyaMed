import axiosInstance from "@/api/axiosInstance";
import type { Notification } from "@/types/common.types";

export const notificationService = {
  getByUser: async (userId: number): Promise<Notification[]> => {
    const response = await axiosInstance.get<Notification[]>(`/notifications/user/${userId}`);
    return response.data;
  },

  markAsRead: async (id: number): Promise<Notification> => {
    const response = await axiosInstance.put<Notification>(`/notifications/${id}/read`);
    return response.data;
  },
};