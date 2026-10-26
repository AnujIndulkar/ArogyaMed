import { Badge } from "@/components/common/Badge";
import { Button } from "@/components/common/Button";
import { Card } from "@/components/common/Card";
import { EmptyState } from "@/components/common/EmptyState";
import { DashboardLayout } from "@/components/layout/DashboardLayout";
import { apiMessage, providerService, when, type SosRequest } from "@/services/ambulanceService";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { MapPin, Siren } from "lucide-react";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { toast } from "sonner";

export default function ProviderRequests() {
  const queryClient = useQueryClient();
  const navigate = useNavigate();

  // ETA typed per request, keyed by request id
  const [eta, setEta] = useState<Record<number, string>>({});

  const { data: requests, isLoading } = useQuery({
    queryKey: ["provider-requests"],
    queryFn: providerService.requests,
    refetchInterval: 8000,
    retry: false,
  });

  const acceptMutation = useMutation({
    mutationFn: ({ id, minutes }: { id: number; minutes?: number }) => providerService.accept(id, minutes),
    onSuccess: () => {
      toast.success("Request accepted. Head to the patient.");
      ["provider-requests", "provider-bookings", "provider-summary", "provider-me"].forEach((key) =>
        queryClient.invalidateQueries({ queryKey: [key] })
      );
      navigate("/ambulance-provider/bookings");
    },
    onError: (err) => {
      toast.error(apiMessage(err, "Could not accept this request"));
      // another ambulance may have taken it: refresh the list
      queryClient.invalidateQueries({ queryKey: ["provider-requests"] });
    },
  });

  const accept = (r: SosRequest) => {
    const raw = eta[r.id]?.trim();
    const minutes = raw ? Number(raw) : undefined;

    if (raw && (!Number.isInteger(minutes) || (minutes as number) < 1 || (minutes as number) > 180)) {
      toast.error("ETA must be a whole number between 1 and 180 minutes");
      return;
    }

    acceptMutation.mutate({ id: r.id, minutes });
  };

  const mapsLink = (r: SosRequest) =>
    r.latitude != null && r.longitude != null
      ? `https://www.google.com/maps?q=${r.latitude},${r.longitude}`
      : null;

  return (
    <DashboardLayout title="Emergency Requests">
      {isLoading ? (
        <div className="skeleton h-32 w-full rounded-2xl" />
      ) : !requests || requests.length === 0 ? (
        <Card>
          <EmptyState
            icon={Siren}
            title="No open emergencies"
            description="New requests appear here automatically. Make sure you are set to Available."
          />
        </Card>
      ) : (
        <div className="grid grid-cols-1 lg:grid-cols-2 gap-4">
          {requests.map((r) => {
            const link = mapsLink(r);
            return (
              <Card key={r.id}>
                <div className="flex items-start justify-between gap-3">
                  <div className="min-w-0">
                    <p className="font-display font-semibold text-ink-900 truncate">{r.patientName}</p>
                    <p className="text-xs text-ink-500">Sent {when(r.createdAt)}</p>
                  </div>
                  <Badge tone="accent">{r.emergencyType || "Emergency"}</Badge>
                </div>

                <p className="text-sm text-ink-700 mt-3 flex items-center gap-1.5">
                  <MapPin size={14} className="text-ink-500 shrink-0" />
                  <span className="truncate">{r.location || "Location not shared"}</span>
                </p>
                {link && (
                  <a
                    href={link}
                    target="_blank"
                    rel="noreferrer"
                    className="text-xs text-primary-600 font-medium hover:underline mt-1 inline-block"
                  >
                    Open in Google Maps
                  </a>
                )}

                <div className="flex items-center gap-2 mt-4">
                  <input
                    type="number"
                    min={1}
                    max={180}
                    placeholder="ETA (min)"
                    value={eta[r.id] ?? ""}
                    onChange={(e) => setEta((prev) => ({ ...prev, [r.id]: e.target.value }))}
                    className="w-28 rounded-xl border border-surface-border px-3 py-2 text-sm focus:outline-none focus:border-primary-400"
                  />
                  <Button
                    className="flex-1"
                    isLoading={acceptMutation.isPending && acceptMutation.variables?.id === r.id}
                    onClick={() => accept(r)}
                  >
                    Accept request
                  </Button>
                </div>
              </Card>
            );
          })}
        </div>
      )}
    </DashboardLayout>
  );
}