import { Badge } from "@/components/common/Badge";
import { Button } from "@/components/common/Button";
import { Card } from "@/components/common/Card";
import { EmptyState } from "@/components/common/EmptyState";
import { DashboardLayout } from "@/components/layout/DashboardLayout";
import {
  apiMessage,
  bookingTone,
  label,
  providerService,
  when,
  type Booking,
  type BookingStatus,
} from "@/services/ambulanceService";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { MapPin, Route } from "lucide-react";
import { useState } from "react";
import { toast } from "sonner";

type Filter = "ALL" | "ACTIVE" | "DONE";

const ACTIVE: BookingStatus[] = ["BOOKED", "ACCEPTED", "ON_THE_WAY"];

// the moves a provider can make from each status (the server enforces the same rules)
const ACTIONS: Record<string, { next: BookingStatus; text: string; danger?: boolean }[]> = {
  BOOKED: [
    { next: "ACCEPTED", text: "Accept" },
    { next: "CANCELLED", text: "Cancel", danger: true },
  ],
  ACCEPTED: [
    { next: "ON_THE_WAY", text: "Start trip" },
    { next: "CANCELLED", text: "Cancel", danger: true },
  ],
  ON_THE_WAY: [
    { next: "COMPLETED", text: "Complete trip" },
    { next: "CANCELLED", text: "Cancel", danger: true },
  ],
};

export default function ProviderBookings() {
  const queryClient = useQueryClient();
  const [filter, setFilter] = useState<Filter>("ALL");

  const { data: bookings, isLoading } = useQuery({
    queryKey: ["provider-bookings"],
    queryFn: providerService.bookings,
    refetchInterval: 10000,
    retry: false,
  });

  const statusMutation = useMutation({
    mutationFn: ({ id, status }: { id: number; status: BookingStatus }) =>
      providerService.setBookingStatus(id, status),
    onSuccess: (b) => {
      toast.success(`Trip marked as ${label(b.status).toLowerCase()}`);
      ["provider-bookings", "provider-summary", "provider-me", "provider-requests"].forEach((key) =>
        queryClient.invalidateQueries({ queryKey: [key] })
      );
    },
    onError: (err) => toast.error(apiMessage(err, "Could not update this trip")),
  });

  const change = (b: Booking, next: BookingStatus) => {
    if (next === "CANCELLED" && !window.confirm("Cancel this trip? The emergency goes back to the open list.")) {
      return;
    }
    statusMutation.mutate({ id: b.id, status: next });
  };

  const list = (bookings ?? []).filter((b) =>
    filter === "ALL" ? true : filter === "ACTIVE" ? ACTIVE.includes(b.status) : !ACTIVE.includes(b.status)
  );

  return (
    <DashboardLayout title="My Bookings">
      <div className="flex gap-2 mb-4">
        {(["ALL", "ACTIVE", "DONE"] as Filter[]).map((f) => (
          <button
            key={f}
            onClick={() => setFilter(f)}
            className={`px-4 py-1.5 rounded-full text-sm font-medium border transition-colors ${
              filter === f
                ? "bg-primary-50 border-primary-300 text-primary-700"
                : "border-surface-border text-ink-500 hover:text-ink-900"
            }`}
          >
            {f === "ALL" ? "All" : f === "ACTIVE" ? "Active" : "Finished"}
          </button>
        ))}
      </div>

      {isLoading ? (
        <div className="skeleton h-32 w-full rounded-2xl" />
      ) : list.length === 0 ? (
        <Card>
          <EmptyState
            icon={Route}
            title="No trips here"
            description="Accepted emergencies show up as trips you can start and complete."
          />
        </Card>
      ) : (
        <div className="grid grid-cols-1 lg:grid-cols-2 gap-4">
          {list.map((b) => (
            <Card key={b.id}>
              <div className="flex items-start justify-between gap-3">
                <div className="min-w-0">
                  <p className="font-display font-semibold text-ink-900 truncate">{b.patientName}</p>
                  <p className="text-xs text-ink-500">Booked {when(b.bookedAt)}</p>
                </div>
                <Badge tone={bookingTone(b.status)}>{label(b.status)}</Badge>
              </div>

              <div className="text-sm text-ink-700 mt-3 flex flex-col gap-1">
                <p className="flex items-center gap-1.5">
                  <MapPin size={14} className="text-ink-500 shrink-0" />
                  <span className="truncate">{b.pickupLocation || "Pickup not shared"}</span>
                </p>
                {b.etaMinutes ? <p className="text-xs text-ink-500">ETA given: {b.etaMinutes} min</p> : null}
                {b.completedAt ? <p className="text-xs text-ink-500">Completed {when(b.completedAt)}</p> : null}
              </div>

              {ACTIONS[b.status] && (
                <div className="flex gap-2 mt-4">
                  {ACTIONS[b.status].map((a) => (
                    <Button
                      key={a.next}
                      size="sm"
                      variant={a.danger ? "secondary" : "primary"}
                      disabled={statusMutation.isPending}
                      onClick={() => change(b, a.next)}
                    >
                      {a.text}
                    </Button>
                  ))}
                </div>
              )}
            </Card>
          ))}
        </div>
      )}
    </DashboardLayout>
  );
}