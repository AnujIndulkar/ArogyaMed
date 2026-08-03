import type { ReactNode } from "react";

type BadgeTone = "primary" | "success" | "warning" | "accent" | "neutral";

const toneStyles: Record<BadgeTone, string> = {
  primary: "bg-primary-50 text-primary-700",
  success: "bg-success-50 text-success-600",
  warning: "bg-warning-50 text-warning-600",
  accent: "bg-accent-50 text-accent-600",
  neutral: "bg-surface-border text-ink-500",
};

export function Badge({ tone = "neutral", children }: { tone?: BadgeTone; children: ReactNode }) {
  return (
    <span
      className={`inline-flex items-center px-2.5 py-1 rounded-full text-xs font-medium ${toneStyles[tone]}`}
    >
      {children}
    </span>
  );
}

export function statusTone(status: string): BadgeTone {
  const map: Record<string, BadgeTone> = {
    PENDING: "warning",
    CONFIRMED: "primary",
    COMPLETED: "success",
    DELIVERED: "success",
    VERIFIED: "success",
    CANCELLED: "accent",
    REJECTED: "accent",
    FAILED: "accent",
    SHIPPED: "primary",
  };

  return map[status?.toUpperCase()] ?? "neutral";
}
