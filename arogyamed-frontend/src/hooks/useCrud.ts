import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import { toast } from "sonner";

interface CrudService<T> {
  getAll: () => Promise<T[]>;
  create: (payload: Partial<T>) => Promise<T>;
  update: (id: number | string, payload: Partial<T>) => Promise<T>;
  remove: (id: number | string) => Promise<void>;
}

export function useCrud<T>(queryKey: string, service: CrudService<T>) {
  const queryClient = useQueryClient();

  const listQuery = useQuery({
    queryKey: [queryKey],
    queryFn: service.getAll,
    retry: false,
  });

  const invalidate = () => queryClient.invalidateQueries({ queryKey: [queryKey] });

  const createMutation = useMutation({
    mutationFn: service.create,
    onSuccess: () => {
      toast.success("Created successfully");
      invalidate();
    },
    onError: (error: any) => {
      toast.error(error?.response?.data?.message || "Failed to create");
    },
  });

  const updateMutation = useMutation({
    mutationFn: ({ id, payload }: { id: number | string; payload: Partial<T> }) =>
      service.update(id, payload),
    onSuccess: () => {
      toast.success("Updated successfully");
      invalidate();
    },
    onError: (error: any) => {
      toast.error(error?.response?.data?.message || "Failed to update");
    },
  });

  const deleteMutation = useMutation({
    mutationFn: service.remove,
    onSuccess: () => {
      toast.success("Deleted successfully");
      invalidate();
    },
    onError: (error: any) => {
      toast.error(error?.response?.data?.message || "Failed to delete");
    },
  });

  return {
    items: listQuery.data,
    isLoading: listQuery.isLoading,
    create: createMutation.mutate,
    isCreating: createMutation.isPending,
    update: updateMutation.mutate,
    isUpdating: updateMutation.isPending,
    remove: deleteMutation.mutate,
  };
}
