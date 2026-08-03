import type { ReactNode } from "react";
import { motion } from "framer-motion";
import { Activity, ShieldCheck, Truck } from "lucide-react";

export function AuthLayout({
  children,
  title,
  subtitle,
}: {
  children: ReactNode;
  title: string;
  subtitle: string;
}) {
  return (
    <div className="min-h-screen w-full flex bg-surface">
      <div className="hidden lg:flex lg:w-1/2 relative overflow-hidden bg-gradient-primary">
        <div className="absolute inset-0 bg-gradient-mesh" />
        <div className="absolute -top-20 -left-20 w-72 h-72 bg-white/10 rounded-full blur-3xl" />
        <div className="absolute bottom-10 right-10 w-96 h-96 bg-accent-500/20 rounded-full blur-3xl" />

        <div className="relative z-10 flex flex-col justify-between p-12 text-white w-full">
          <div className="flex items-center gap-2">
            <div className="w-9 h-9 rounded-xl bg-white/20 backdrop-blur flex items-center justify-center font-display font-bold">
              A
            </div>
            <span className="font-display font-bold text-lg">ArogyaMed</span>
          </div>

          <div>
            <motion.h1
              initial={{ opacity: 0, y: 16 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ duration: 0.6 }}
              className="font-display text-4xl font-bold leading-tight max-w-md"
            >
              Healthcare, connected end to end.
            </motion.h1>

            <motion.p
              initial={{ opacity: 0, y: 16 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ duration: 0.6, delay: 0.1 }}
              className="mt-4 text-white/70 max-w-sm"
            >
              One platform for patients, doctors, pharmacies, and the entire
              medicine supply chain.
            </motion.p>

            <motion.div
              initial={{ opacity: 0, y: 16 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ duration: 0.6, delay: 0.2 }}
              className="mt-10 grid grid-cols-3 gap-3"
            >
              {[
                { icon: Activity, label: "Live Tracking" },
                { icon: ShieldCheck, label: "Verified Medicine" },
                { icon: Truck, label: "Fast Delivery" },
              ].map(({ icon: Icon, label }) => (
                <div key={label} className="glass rounded-2xl p-4 flex flex-col gap-2">
                  <Icon size={18} />
                  <span className="text-xs font-medium text-white/90">{label}</span>
                </div>
              ))}
            </motion.div>
          </div>

          <p className="text-xs text-white/50">
            © {new Date().getFullYear()} ArogyaMed. Enterprise Healthcare Platform.
          </p>
        </div>
      </div>

      <div className="flex-1 flex items-center justify-center p-6 sm:p-12">
        <motion.div
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.5 }}
          className="w-full max-w-sm"
        >
          <div className="lg:hidden flex items-center gap-2 mb-8">
            <div className="w-9 h-9 rounded-xl bg-gradient-primary flex items-center justify-center text-white font-display font-bold">
              A
            </div>
            <span className="font-display font-bold text-lg text-ink-900">ArogyaMed</span>
          </div>

          <h2 className="font-display text-2xl font-bold text-ink-900">{title}</h2>
          <p className="text-ink-500 text-sm mt-1.5 mb-8">{subtitle}</p>

          {children}
        </motion.div>
      </div>
    </div>
  );
}
