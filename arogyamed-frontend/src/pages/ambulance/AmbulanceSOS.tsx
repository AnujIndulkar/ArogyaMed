import { useEffect, useState } from "react";
import { MapContainer, TileLayer, Marker, Popup, Circle } from "react-leaflet";
import { motion } from "framer-motion";
import { toast } from "sonner";
import {
  Ambulance,
  PhoneCall,
  Navigation,
  Clock,
  Siren,
  MapPin,
} from "lucide-react";
import { DashboardLayout } from "@/components/layout/DashboardLayout";
import { Card } from "@/components/common/Card";
import { Badge } from "@/components/common/Badge";
import { Button } from "@/components/common/Button";
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

  const nearbyAmbulances = [
    { id: "AMB-101", pos: offset(center, 0.008, 0.006), driver: "Suresh Patil", eta: "6 min", vehicle: "MH-12-AB-3345" },
    { id: "AMB-104", pos: offset(center, -0.005, 0.009), driver: "Ravi Kumar", eta: "9 min", vehicle: "MH-12-CD-7712" },
    { id: "AMB-107", pos: offset(center, 0.004, -0.01), driver: "Amit Joshi", eta: "12 min", vehicle: "MH-12-EF-1029" },
  ];

  const handleBook = (id: string) => {
    toast.success(`${id} is on the way — live tracking started`);
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
          <Card className="bg-gradient-accent text-white">
            <div className="flex items-center gap-3">
              <div className="w-11 h-11 rounded-2xl bg-white/20 flex items-center justify-center">
                <Siren size={20} />
              </div>
              <div>
                <p className="font-display font-bold">Emergency?</p>
                <p className="text-xs text-white/80">Use the SOS button, bottom-right</p>
              </div>
            </div>
          </Card>

          <Card>
            <p className="font-display font-semibold text-ink-900 mb-4 flex items-center gap-2">
              <Ambulance size={17} className="text-primary-600" />
              Nearby ambulances
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
                    <Button size="sm" onClick={() => handleBook(amb.id)}>
                      Book now
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
            <span>Location updates automatically shared during active bookings</span>
          </div>
        </div>
      </div>
    </DashboardLayout>
  );
}
