import type { Role } from "@/types/auth.types";
import { motion } from "framer-motion";
import {
    ArrowRight,
    Building2,
    HeartPulse,
    Pill,
    Stethoscope,
    Truck,
    Warehouse,
} from "lucide-react";
import { Link } from "react-router-dom";

const ROLE_CARDS: { role: Role; icon: typeof HeartPulse; description: string }[] = [
  { role: "PATIENT", icon: HeartPulse, description: "Book doctors, order medicine, track your health" },
  { role: "DOCTOR", icon: Stethoscope, description: "Consult patients and manage appointments" },
  { role: "PHARMACIST", icon: Pill, description: "Run your pharmacy's orders and inventory" },
  { role: "WHOLESALER", icon: Warehouse, description: "Supply stock to pharmacies at scale" },
  { role: "COMPANY", icon: Building2, description: "Manufacture and distribute medicines" },
  { role: "DELIVERY_PARTNER", icon: Truck, description: "Deliver orders, earn on your schedule" },
];

export default function RoleSelect() {
  return (
    <div className="min-h-screen bg-surface flex flex-col items-center justify-center p-6">
      <div className="max-w-3xl w-full">
        <div className="text-center mb-10">
          <div className="w-11 h-11 rounded-2xl bg-gradient-primary flex items-center justify-center text-white font-display font-bold mx-auto mb-4">
            A
          </div>
          <h1 className="font-display text-3xl font-bold text-ink-900">Join ArogyaMed</h1>
          <p className="text-ink-500 mt-2">Choose how you'll be using the platform</p>
        </div>

        <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
          {ROLE_CARDS.map((card, i) => (
            <motion.div
              key={card.role}
              initial={{ opacity: 0, y: 12 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ delay: i * 0.05 }}
            >
              <Link
                to={`/register/${card.role.toLowerCase().replace("_", "-")}`}
                className="card card-hover p-5 flex items-center gap-4 group"
              >
                <div className="w-12 h-12 rounded-2xl bg-primary-50 text-primary-600 flex items-center justify-center shrink-0">
                  <card.icon size={22} />
                </div>
                <div className="flex-1 min-w-0">
                  <p className="font-display font-semibold text-ink-900">
                    {card.role.replace("_", " ").replace(/\b\w/g, (c) => c.toUpperCase())}
                  </p>
                  <p className="text-xs text-ink-500 mt-0.5">{card.description}</p>
                </div>
                <ArrowRight size={18} className="text-ink-300 group-hover:text-primary-600 transition-colors shrink-0" />
              </Link>
            </motion.div>
          ))}
        </div>

        <p className="text-center text-sm text-ink-500 mt-8">
          Already have an account?{" "}
          <Link to="/login" className="text-primary-600 font-medium hover:underline">
            Sign in
          </Link>
        </p>
      </div>
    </div>
  );
}