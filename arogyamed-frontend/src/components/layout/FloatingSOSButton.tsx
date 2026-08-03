import { AnimatePresence, motion } from "framer-motion";
import { MapPin, PhoneCall, Siren, X } from "lucide-react";
import { useRef, useState } from "react";
import { useNavigate } from "react-router-dom";
import { toast } from "sonner";

const HOLD_DURATION_MS = 3000;

export function FloatingSOSButton() {
  const navigate = useNavigate();

  const [progress, setProgress] = useState(0);
  const [holding, setHolding] = useState(false);
  const [confirmed, setConfirmed] = useState(false);

  const frameRef = useRef<number | undefined>(undefined);
  const startRef = useRef<number>(0);

  const startHold = () => {
    setHolding(true);
    startRef.current = performance.now();

    const tick = (now: number) => {
      const elapsed = now - startRef.current;
      const pct = Math.min(elapsed / HOLD_DURATION_MS, 1);
      setProgress(pct);

      if (pct >= 1) {
        setConfirmed(true);
        setHolding(false);
        return;
      }

      frameRef.current = requestAnimationFrame(tick);
    };

    frameRef.current = requestAnimationFrame(tick);
  };

  const cancelHold = () => {
    if (frameRef.current) cancelAnimationFrame(frameRef.current);
    setHolding(false);
    setProgress(0);
  };

  const circumference = 2 * Math.PI * 30;

  return (
    <>
      <div className="fixed bottom-6 right-6 z-40 flex flex-col items-end gap-2">
        {holding && !confirmed && (
          <span className="text-xs font-medium bg-ink-900 text-white px-3 py-1.5 rounded-full shadow-soft">
            Keep holding to confirm SOS...
          </span>
        )}

        <button
          onMouseDown={startHold}
          onMouseUp={cancelHold}
          onMouseLeave={cancelHold}
          onTouchStart={startHold}
          onTouchEnd={cancelHold}
          className="relative w-16 h-16 rounded-full bg-gradient-accent text-white shadow-glow-accent flex items-center justify-center active:scale-95 transition-transform"
        >
          <span className="absolute inset-0 rounded-full bg-accent-500 animate-pulse-ring" />

          <svg className="absolute inset-0 -rotate-90 w-16 h-16">
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

          <Siren size={26} className="relative z-10" />
        </button>
      </div>

      <AnimatePresence>
        {confirmed && (
          <motion.div
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            exit={{ opacity: 0 }}
            className="fixed inset-0 z-50 bg-ink-900/50 backdrop-blur-sm flex items-center justify-center p-4"
            onClick={() => setConfirmed(false)}
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
                <button onClick={() => setConfirmed(false)} className="text-ink-300 hover:text-ink-700">
                  <X size={20} />
                </button>
              </div>

              <h3 className="font-display font-bold text-xl text-ink-900">Emergency SOS</h3>
              <p className="text-sm text-ink-500 mt-1">
                We'll alert the nearest ambulance and share your live location. Only confirm if this is a real emergency.
              </p>

              <div className="mt-5 flex flex-col gap-2">
                <button
                  onClick={() => {
                    setConfirmed(false);
                    setProgress(0);
                    toast.success("SOS sent — nearest ambulance is being notified");
                    navigate("/ambulance");
                  }}
                  className="w-full bg-gradient-accent text-white font-medium rounded-xl px-5 py-3 shadow-glow-accent hover:-translate-y-0.5 transition-transform flex items-center justify-center gap-2"
                >
                  <MapPin size={16} />
                  Confirm & Send Location
                </button>

                
                  href="tel:108"
                  className="w-full text-center border border-surface-border rounded-xl px-5 py-3 text-sm font-medium text-ink-700 hover:bg-surface transition-colors flex items-center justify-center gap-2"

                  <a>
                  <PhoneCall size={16} />
                  Call Emergency Services (108)
                </a>

                <button
                  onClick={() => {
                    setConfirmed(false);
                    setProgress(0);
                  }}
                  className="w-full text-center text-sm font-medium text-ink-500 py-2 hover:text-ink-700"
                >
                  Cancel
                </button>
              </div>
            </motion.div>
          </motion.div>
        )}
      </AnimatePresence>
    </>
  );
}