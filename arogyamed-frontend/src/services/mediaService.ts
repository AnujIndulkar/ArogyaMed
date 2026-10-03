import axiosInstance from "@/api/axiosInstance";

export interface ProfilePictureUser {
  id: number;
  fullName: string;
  email: string;
  phoneNumber: string;
  address?: string;
  role: string;
  verified: boolean;
  profileImageUrl?: string | null;
}

export const mediaService = {
  uploadProfilePicture: async (userId: number, file: File): Promise<ProfilePictureUser> => {
    const formData = new FormData();
    formData.append("file", file);

    const response = await axiosInstance.post<ProfilePictureUser>(
      `/users/${userId}/profile-picture`,
      formData,
      { headers: { "Content-Type": "multipart/form-data" } }
    );

    return response.data;
  },

  removeProfilePicture: async (userId: number): Promise<ProfilePictureUser> => {
    const response = await axiosInstance.delete<ProfilePictureUser>(
      `/users/${userId}/profile-picture`
    );

    return response.data;
  },
};