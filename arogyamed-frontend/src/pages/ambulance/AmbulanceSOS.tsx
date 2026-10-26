import { useEffect, useState } from "react";
import { MapContainer, TileLayer, Marker, Popup, Circle } from "react-leaflet";
import { motion } from "framer-motion";
import { toast } from "sonner";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { Ambulance, PhoneCall, Clock, Siren, MapPin } from "lucide-react";
import { DashboardLayout } from "@/components/layout/DashboardLayout";
import { Card } from "@/components/common/Card";
import { Badge } from "@/components/common/Badge";
import { Button } from "@/components/common/Button";
import { useAuth } from "@/hooks/useAuth";
import { apiMessage, label, patientSosService, when } from "@/services/ambulanceService";
import { fixLeafletIcons, ambulanceIcon } from "@/utils/leafletIcons";

fixLeafletIcons();

const DEFAULT_CENTER: [number, number] = [18.5204, 73.8567]; // Pune fallback

const emergencyContacts = [
  { label: "National Ambulance", number: "108" },
  { label: "Police", number: "100" },
  { label: "Women Helpline", number: "1091" },
];

function offset([lat, lng]: [number, number], dLat: number, dLng: number): [number, number] {
  return [lat + dLat, lng + dLng];
}

export default function AmbulanceSOS() {
  const { user } = useAuth();
  const queryClient = useQueryClient();
  const isPatient = user?.role === "PATIENT";

  const [center, setCenter] = useState<[number, number]>(DEFAULT_CENTER);
  const [locating, setLocating] = useState(true);

  useEffect(() => {
    if (!navigator.geolocation) {
      setLocating(false);
      return;
    }

    navigator.geolocation.getCurrentPosition(
      (pos) => {
        setCenter([pos.coords.latitude, pos.coords.longitude]);
        setLocating(false);
      },
      () => {
        setLocating(false);
      },
      { timeout: 5000 }
    );
  }, []);

  // the patient's open emergency, refreshed so they see when an ambulance accepts
  const { data: sos } = useQuery({
    queryKey: ["my-sos"],
    queryFn: patientSosService.active,
    enabled: isPatient,
    refetchInterval: 8000,
    retry: false,
  });

  const cancelMutation = useMutation({
    mutationFn: (id: number) => patientSosService.cancel(id),
    onSuccess: () => {
      toast.success("Emergency cancelled");
      queryClient.invalidateQueries({ queryKey: ["my-sos"] });
    },
    onError: (err) => toast.error(apiMessage(err, "Could not cancel the request")),
  });

  const nearbyAmbulances = [
    { id: "AMB-101", pos: offset(center, 0.008, 0.006), driver: "Suresh Patil", eta: "6 min", vehicle: "MH-12-AB-3345" },
    { id: "AMB-104", pos: offset(center, -0.005, 0.009), driver: "Ravi Kumar", eta: "9 min", vehicle: "MH-12-CD-7712" },
    { id: "AMB-107", pos: offset(center, 0.004, -0.01), driver: "Amit Joshi", eta: "12 min", vehicle: "MH-12-EF-1029" },
  ];

  const handleBook = (id: string) => {
    toast.info(`${id} is sample data. Hold the SOS button to send a real request.`);
  };

  return (
    <DashboardLayout title="Ambulance & SOS">
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-5">
        {/* Map */}
        <Card className="lg:col-span-2 !p-0 overflow-hidden h-[520px]">
          {locating ? (
            <div className="h-full flex items-center justify-center flex-col gap-3">
              <div className="w-8 h-8 border-2 border-primary-200 border-t-primary-600 rounded-full animate-spin" />
              <p className="text-sm text-ink-500">Getting your location...</p>
            </div>
          ) : (
            <MapContainer center={center} zoom={14} className="h-full w-full">
              <TileLayer
                attribution='&copy; OpenStreetMap contributors'
                url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
              />

              <Circle center={center} radius={300} pathOptions={{ color: "#6D5EF7", fillOpacity: 0.08 }} />

              <Marker position={center}>
                <Popup>Your location</Popup>
              </Marker>

              {nearbyAmbulances.map((amb) => (
                <Marker key={amb.id} position={amb.pos} icon={ambulanceIcon}>
                  <Popup>
                    <p className="font-semibold">{amb.id}</p>
                    <p className="text-xs">{amb.driver} · {amb.vehicle}</p>
                    <p className="text-xs">ETA: {amb.eta}</p>
                  </Popup>
                </Marker>
              ))}
            </MapContainer>
          )}
        </Card>

        {/* Side panel */}
        <div className="flex flex-col gap-5">
          {sos ? (
            <Card className="border-accent-200">
              <div className="flex items-center justify-between mb-3">
                <p className="font-display font-semibold text-ink-900 flex items-center gap-2">
                  <Siren size={17} className="text-accent-600" />
                  Your emergency
                </p>
                <Badge tone={sos.status === "PENDING" ? "warning" : "success"}>{label(sos.status)}</Badge>
              </div>

              {sos.status === "PENDING" ? (
                <>
                  <p className="text-sm text-ink-700">
                    Request sent. Waiting for an ambulance to accept. This page updates by itself.
                  </p>
                  <p className="text-xs text-ink-500 mt-2">Sent {when(sos.createdAt)}</p>
                  <p className="text-xs text-ink-500">Location: {sos.location || "not shared"}</p>

                  <div className="flex gap-2 mt-4">
                    <a
                      href="tel:108"
                      className="flex-1 text-center border border-surface-border rounded-xl px-3 py-2 text-sm font-medium text-ink-700 hover:bg-surface"
                    >
                      Call 108
                    </a>
                    <Button
                      variant="secondary"
                      size="sm"
                      isLoading={cancelMutation.isPending}
                      onClick={() => cancelMutation.mutate(sos.sosId)}
                    >
                      Cancel request
                    </Button>
                  </div>
                </>
              ) : (
                <>
                  <p className="text-sm font-medium text-ink-900">
                    {sos.status === "IN_PROGRESS" ? "Your ambulance is on the way" : "An ambulance accepted your request"}
                  </p>

                  <div className="mt-3 text-sm text-ink-700 flex flex-col gap-1">
                    <p>Vehicle: <span className="font-mono">{sos.ambulanceNumber || "-"}</span></p>
                    <p>Driver: {sos.driverName || "-"}</p>
                    {sos.etaMinutes ? (
                      <p className="flex items-center gap-1"><Clock size={13} /> About {sos.etaMinutes} min away</p>
                    ) : null}
                  </div>

                  {sos.driverPhone && (
                    <a
                      href={`tel:${sos.driverPhone}`}
                      className="mt-4 w-full text-center bg-gradient-primary text-white rounded-xl px-3 py-2 text-sm font-medium flex items-center justify-center gap-2"
                    >
                      <PhoneCall size={15} /> Call driver ({sos.driverPhone})
                    </a>
                  )}
                </>
              )}
            </Card>
          ) : (
            <Card className="bg-gradient-accent text-white">
              <div className="flex items-center gap-3">
                <div className="w-11 h-11 rounded-2xl bg-white/20 flex items-center justify-center">
                  <Siren size={20} />
                </div>
                <div>
                  <p className="font-display font-bold">Emergency?</p>
                  <p className="text-xs text-white/80">Hold the SOS button, bottom-right, for 3 seconds</p>
                </div>
              </div>
            </Card>
          )}

          <Card>
            <p className="font-display font-semibold text-ink-900 mb-4 flex items-center gap-2">
              <Ambulance size={17} className="text-primary-600" />
              Nearby ambulances
              <Badge tone="neutral">Sample</Badge>
            </p>

            <div className="flex flex-col gap-3">
              {nearbyAmbulances.map((amb, i) => (
                <motion.div
                  key={amb.id}
                  initial={{ opacity: 0, x: 12 }}
                  animate={{ opacity: 1, x: 0 }}
                  transition={{ delay: i * 0.08 }}
                  className="border border-surface-border rounded-2xl p-3.5"
                >
                  <div className="flex items-center justify-between">
                    <p className="font-mono text-sm font-medium text-ink-900">{amb.id}</p>
                    <Badge tone="success">Available</Badge>
                  </div>
                  <p className="text-xs text-ink-500 mt-1">{amb.driver} · {amb.vehicle}</p>

                  <div className="flex items-center justify-between mt-3">
                    <span className="flex items-center gap-1 text-xs text-ink-500">
                      <Clock size={12} /> ETA {amb.eta}
                    </span>
                    <Button size="sm" variant="secondary" onClick={() => handleBook(amb.id)}>
                      Details
                    </Button>
                  </div>
                </motion.div>
              ))}
            </div>
          </Card>

          <Card>
            <p className="font-display font-semibold text-ink-900 mb-4 flex items-center gap-2">
              <PhoneCall size={17} className="text-accent-600" />
              Emergency contacts
            </p>

            <div className="flex flex-col divide-y divide-surface-border">
              {emergencyContacts.map((c) => (
                <a
                  key={c.number}
                  href={`tel:${c.number}`}
                  className="flex items-center justify-between py-2.5 hover:text-primary-600"
                >
                  <span className="text-sm text-ink-700">{c.label}</span>
                  <span className="text-sm font-mono font-medium text-ink-900">{c.number}</span>
                </a>
              ))}
            </div>
          </Card>

          <div className="flex items-center gap-1.5 text-xs text-ink-500 px-1">
            <MapPin size={12} />
            <span>Your location is shared only when you send an SOS</span>
          </div>
        </div>
      </div>
    </DashboardLayout>
  );
}