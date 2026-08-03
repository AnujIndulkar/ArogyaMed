import axiosInstance from "@/api/axiosInstance";

/**
 * Generic REST CRUD wrapper — matches the layered Controller -> Service
 * pattern already used across every ArogyaMed backend module.
 */
export function createCrudService<T>(resourcePath: string) {
  return {
    getAll: async (): Promise<T[]> => {
      const response = await axiosInstance.get<T[]>(resourcePath);
      return response.data;
    },

    getById: async (id: number | string): Promise<T> => {
      const response = await axiosInstance.get<T>(`${resourcePath}/${id}`);
      return response.data;
    },

    create: async (payload: Partial<T>): Promise<T> => {
      const response = await axiosInstance.post<T>(resourcePath, payload);
      return response.data;
    },

    update: async (id: number | string, payload: Partial<T>): Promise<T> => {
      const response = await axiosInstance.put<T>(`${resourcePath}/${id}`, payload);
      return response.data;
    },

    remove: async (id: number | string): Promise<void> => {
      await axiosInstance.delete(`${resourcePath}/${id}`);
    },
  };
}
