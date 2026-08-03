import type { ButtonHTMLAttributes, ReactNode } from "react";
import { Loader2 } from "lucide-react";

interface ButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {
  variant?: "primary" | "secondary" | "ghost" | "danger";
  size?: "sm" | "md";
  isLoading?: boolean;
  children: ReactNode;
}

export function Button({
  variant = "primary",
  size = "md",
  isLoading = false,
  children,
  className = "",
  disabled,
  ...props
}: ButtonProps) {
  const base =
    "inline-flex items-center justify-center gap-2 rounded-xl font-medium transition-all duration-200 disabled:opacity-50 disabled:pointer-events-none";

  const sizes = {
    sm: "px-3.5 py-1.5 text-xs",
    md: "px-5 py-2.5 text-sm",
  };

  const variants = {
    primary:
      "bg-gradient-primary text-white shadow-glow hover:shadow-lg hover:-translate-y-0.5 active:translate-y-0",
    secondary:
      "bg-white text-ink-900 border border-surface-border hover:border-primary-300 hover:bg-primary-50",
    ghost: "text-ink-500 hover:text-ink-900 hover:bg-surface-border/50",
    danger:
      "bg-gradient-accent text-white shadow-glow-accent hover:shadow-lg hover:-translate-y-0.5",
  };

  return (
    <button
      className={`${base} ${sizes[size]} ${variants[variant]} ${className}`}
      disabled={disabled || isLoading}
      {...props}
    >
      {isLoading && <Loader2 size={16} className="animate-spin" />}
      {children}
    </button>
  );
}
