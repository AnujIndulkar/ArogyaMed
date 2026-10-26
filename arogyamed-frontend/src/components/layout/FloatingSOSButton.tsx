import { useAuth } from "@/hooks/useAuth";
import { apiMessage, patientSosService } from "@/services/ambulanceService";
import { useQueryClient } from "@tanstack/react-query";
import { AnimatePresence, motion } from "framer-motion";
import { Loader2, MapPin, PhoneCall, Siren, X } from "lucide-react";
import { useEffect, useRef, useState } from "react";
import { createPortal } from "react-dom";
import { useNavigate } from "react-router-dom";
import { toast } from "sonner";

const HOLD_DURATION_MS = 3000;

type Coords = { latitude: number; longitude: number } | null;

// ask the browser for a position, but never wait more than a few seconds in an emergency
function getPosition(): Promise<Coords> {
  return new Promise((resolve) => {
    if (!navigator.geolocation) return resolve(null);
    navigator.geolocation.getCurrentPosition(
      (pos) => resolve({ latitude: pos.coords.latitude, longitude: pos.coords.longitude }),
      () => resolve(null),
      { timeout: 6000, maximumAge: 60000 }
    );
  });
}

export function FloatingSOSButton() {
  const navigate = useNavigate();
  const queryClient = useQueryClient();
  const { user, isAuthenticated } = useAuth();

  const [progress, setProgress] = useState(0);
  const [holding, setHolding] = useState(false);
  const [confirmed, setConfirmed] = useState(false);
  const [sending, setSending] = useState(false);

  const frameRef = useRef<number | undefined>(undefined);
  const startRef = useRef<number>(0);
  const holdingRef = useRef(false);

  // stop the animation if the page is left while holding
  useEffect(() => {
    return () => {
      if (frameRef.current) cancelAnimationFrame(frameRef.current);
    };
  }, []);

  const startHold = () => {
    if (holdingRef.current) return;
    holdingRef.current = true;
    setHolding(true);
    startRef.current = performance.now();

    const tick = (now: number) => {
      const pct = Math.min((now - startRef.current) / HOLD_DURATION_MS, 1);
      setProgress(pct);

      if (pct >= 1) {
        holdingRef.current = false;
        setHolding(false);
        setConfirmed(true);
        return;
      }

      frameRef.current = requestAnimationFrame(tick);
    };

    frameRef.current = requestAnimationFrame(tick);
  };

  const cancelHold = () => {
    if (!holdingRef.current) return;
    holdingRef.current = false;
    if (frameRef.current) cancelAnimationFrame(frameRef.current);
    setHolding(false);
    setProgress(0);

    // released early: tell the person what to do
    toast.info("Hold the SOS button for 3 seconds to send an emergency alert.");
  };

  const close = () => {
    setConfirmed(false);
    setProgress(0);
  };

  const sendSos = async () => {
    if (!isAuthenticated) {
      close();
      toast.error("Please sign in as a patient to send an SOS, or call 108 now.");
      navigate("/login");
      return;
    }

    if (user?.role !== "PATIENT") {
      close();
      toast.error("SOS requests can only be sent from a patient account. Call 108 for help.");
      return;
    }

    setSending(true);
    try {
      const coords = await getPosition();

      await patientSosService.create({
        latitude: coords?.latitude ?? null,
        longitude: coords?.longitude ?? null,
        location: coords
          ? `${coords.latitude.toFixed(5)}, ${coords.longitude.toFixed(5)}`
          : "Location not shared",
        emergencyType: "General emergency",
      });

      await queryClient.invalidateQueries({ queryKey: ["my-sos"] });
      close();
      toast.success("SOS sent. Nearby ambulances are being notified.");
      navigate("/ambulance");
    } catch (err) {
      toast.error(apiMessage(err, "Could not send SOS. Please call 108."));
    } finally {
      setSending(false);
    }
  };

  const circumference = 2 * Math.PI * 30;

  // rendered into <body> so no page wrapper can cover, clip or move it
  return createPortal(
    <>
      <div className="fixed bottom-6 right-6 z-[60] flex flex-col items-end gap-2">
        {holding && !confirmed && (
          <span className="text-xs font-medium bg-ink-900 text-white px-3 py-1.5 rounded-full shadow-soft">
            Keep holding to confirm SOS...
          </span>
        )}

        <button
          type="button"
          onPointerDown={startHold}
          onPointerUp={cancelHold}
          onPointerLeave={cancelHold}
          onPointerCancel={cancelHold}
          onContextMenu={(e) => e.preventDefault()}
          aria-label="Hold for 3 seconds to send an emergency SOS"
          style={{ touchAction: "none", userSelect: "none" }}
          className="relative w-16 h-16 rounded-full bg-gradient-accent text-white shadow-glow-accent flex items-center justify-center active:scale-95 transition-transform"
        >
          <span className="pointer-events-none absolute inset-0 rounded-full bg-accent-500 animate-pulse-ring" />

          <svg className="pointer-events-none absolute inset-0 -rotate-90 w-16 h-16">
            <circle cx="32" cy="32" r="30" fill="none" stroke="white" strokeWidth="3" strokeOpacity="0.4" />
            <circle
              cx="32"
              cy="32"
              r="30"
              fill="none"
              stroke="white"
              strokeWidth="3"
              strokeDasharray={circumference}
              strokeDashoffset={circumference * (1 - progress)}
              strokeLinecap="round"
              style={{ transition: holding ? "none" : "stroke-dashoffset 0.2s ease" }}
            />
          </svg>

          <Siren size={26} className="pointer-events-none relative z-10" />
        </button>
      </div>

      <AnimatePresence>
        {confirmed && (
          <motion.div
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            exit={{ opacity: 0 }}
            className="fixed inset-0 z-[70] bg-ink-900/50 backdrop-blur-sm flex items-center justify-center p-4"
            onClick={() => !sending && close()}
          >
            <motion.div
              initial={{ opacity: 0, scale: 0.92, y: 16 }}
              animate={{ opacity: 1, scale: 1, y: 0 }}
              exit={{ opacity: 0, scale: 0.92, y: 16 }}
              transition={{ type: "spring", damping: 22, stiffness: 300 }}
              onClick={(e) => e.stopPropagation()}
              className="bg-white rounded-3xl shadow-soft max-w-sm w-full p-6"
            >
              <div className="flex items-center justify-between mb-4">
                <div className="w-12 h-12 rounded-2xl bg-gradient-accent flex items-center justify-center text-white">
                  <Siren size={22} />
                </div>
                <button
                  onClick={close}
                  disabled={sending}
                  aria-label="Close"
                  className="text-ink-300 hover:text-ink-700 disabled:opacity-50"
                >
                  <X size={20} />
                </button>
              </div>

              <h3 className="font-display font-bold text-xl text-ink-900">Emergency SOS</h3>
              <p className="text-sm text-ink-500 mt-1">
                We'll alert available ambulances and share your location. Only confirm if this is a real emergency.
              </p>

              <div className="mt-5 flex flex-col gap-2">
                <button
                  onClick={sendSos}
                  disabled={sending}
                  className="w-full bg-gradient-accent text-white font-medium rounded-xl px-5 py-3 shadow-glow-accent hover:-translate-y-0.5 transition-transform flex items-center justify-center gap-2 disabled:opacity-60 disabled:pointer-events-none"
                >
                  {sending ? <Loader2 size={16} className="animate-spin" /> : <MapPin size={16} />}
                  {sending ? "Sending..." : "Confirm & Send Location"}
                </button>

                <a
                  href="tel:108"
                  className="w-full text-center border border-surface-border rounded-xl px-5 py-3 text-sm font-medium text-ink-700 hover:bg-surface transition-colors flex items-center justify-center gap-2"
                >
                  <PhoneCall size={16} />
                  Call Emergency Services (108)
                </a>

                <button
                  onClick={close}
                  disabled={sending}
                  className="w-full text-center text-sm font-medium text-ink-500 py-2 hover:text-ink-700 disabled:opacity-50"
                >
                  Cancel
                </button>
              </div>
            </motion.div>
          </motion.div>
        )}
      </AnimatePresence>
    </>,
    document.body
  );
}