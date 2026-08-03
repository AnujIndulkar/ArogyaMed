import { motion } from "framer-motion";
import type { LucideIcon } from "lucide-react";
import { useEffect, useState } from "react";

interface StatCardProps {
  label: string;
  value: number;
  icon: LucideIcon;
  trend?: string;
  trendUp?: boolean;
  accent?: "primary" | "accent" | "success" | "warning";
  index?: number;
}

const accentStyles = {
  primary: "bg-primary-50 text-primary-600",
  accent: "bg-accent-50 text-accent-600",
  success: "bg-success-50 text-success-600",
  warning: "bg-warning-50 text-warning-600",
};

function useCountUp(target: number, duration = 900) {
  const [value, setValue] = useState(0);

  useEffect(() => {
    let start: number | null = null;
    let frame: number;

    const step = (timestamp: number) => {
      if (start === null) start = timestamp;
      const progress = Math.min((timestamp - start) / duration, 1);
      setValue(Math.floor(progress * target));

      if (progress < 1) {
        frame = requestAnimationFrame(step);
      }
    };

    frame = requestAnimationFrame(step);
    return () => cancelAnimationFrame(frame);
  }, [target, duration]);

  return value;
}

export function StatCard({
  label,
  value,
  icon: Icon,
  trend,
  trendUp,
  accent = "primary",
  index = 0,
}: StatCardProps) {
  const animatedValue = useCountUp(value);

  return (
    <motion.div
      initial={{ opacity: 0, y: 16 }}
      animate={{ opacity: 1, y: 0 }}
      transition={{ duration: 0.4, delay: index * 0.06 }}
      className="card p-5 card-hover"
    >
      <div className="flex items-start justify-between">
        <div className={`w-10 h-10 rounded-xl flex items-center justify-center ${accentStyles[accent]}`}>
          <Icon size={18} />
        </div>

        {trend && (
          <span
            className={`text-xs font-medium px-2 py-1 rounded-full ${
              trendUp ? "bg-success-50 text-success-600" : "bg-accent-50 text-accent-600"
            }`}
          >
            {trend}
          </span>
        )}
      </div>

      <p className="mt-4 text-2xl font-display font-bold text-ink-900 font-mono">
        {animatedValue.toLocaleString()}
      </p>
      <p className="text-sm text-ink-500 mt-0.5">{label}</p>
    </motion.div>
  );
}
