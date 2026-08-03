import { useState } from "react";
import { useForm } from "react-hook-form";
import { Star, Plus } from "lucide-react";
import { DashboardLayout } from "@/components/layout/DashboardLayout";
import { Card } from "@/components/common/Card";
import { Button } from "@/components/common/Button";
import { Input } from "@/components/common/Input";
import { Modal } from "@/components/common/Modal";
import { EmptyState, TableSkeleton } from "@/components/common/EmptyState";
import { useCrud } from "@/hooks/useCrud";
import { reviewService } from "@/services/domainServices";
import type { Review } from "@/types/common.types";

interface ReviewForm {
  targetName: string;
  rating: number;
  comment: string;
}

export default function ReviewsList() {
  const { items, isLoading, create, isCreating } = useCrud<Review>("reviews", reviewService);
  const [open, setOpen] = useState(false);
  const { register, handleSubmit, reset } = useForm<ReviewForm>({ defaultValues: { rating: 5 } });

  const onSubmit = (values: ReviewForm) => {
    create(
      { ...values, rating: Number(values.rating) } as Partial<Review>,
      { onSuccess: () => { setOpen(false); reset(); } } as any
    );
  };

  return (
    <DashboardLayout title="Reviews & Ratings">
      <Card>
        <div className="flex items-center justify-end mb-5">
          <Button size="sm" onClick={() => setOpen(true)}>
            <Plus size={15} /> Write a review
          </Button>
        </div>

        {isLoading ? (
          <TableSkeleton rows={4} />
        ) : !items || items.length === 0 ? (
          <EmptyState icon={Star} title="No reviews yet" description="Share your experience after a consultation or order" />
        ) : (
          <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
            {items.map((r) => (
              <div key={r.id} className="border border-surface-border rounded-2xl p-4">
                <div className="flex items-center justify-between">
                  <p className="font-medium text-ink-900">{r.targetName ?? "ArogyaMed"}</p>
                  <div className="flex items-center gap-0.5">
                    {Array.from({ length: 5 }).map((_, i) => (
                      <Star
                        key={i}
                        size={13}
                        className={i < r.rating ? "fill-warning-500 text-warning-500" : "text-surface-border"}
                      />
                    ))}
                  </div>
                </div>
                <p className="text-sm text-ink-500 mt-2">{r.comment}</p>
              </div>
            ))}
          </div>
        )}
      </Card>

      <Modal open={open} onClose={() => setOpen(false)} title="Write a review">
        <form onSubmit={handleSubmit(onSubmit)} className="flex flex-col gap-4">
          <Input label="Doctor / Pharmacy name" {...register("targetName", { required: true })} />
          <Input label="Rating (1-5)" type="number" min={1} max={5} {...register("rating", { required: true })} />
          <Input label="Comment" {...register("comment", { required: true })} />
          <Button type="submit" isLoading={isCreating} className="w-full mt-1">
            Submit review
          </Button>
        </form>
      </Modal>
    </DashboardLayout>
  );
}
