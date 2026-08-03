import { forwardRef, type InputHTMLAttributes, type ReactNode } from "react";

interface InputProps extends InputHTMLAttributes<HTMLInputElement> {
  label?: string;
  error?: string;
  icon?: ReactNode;
}

export const Input = forwardRef<HTMLInputElement, InputProps>(
  ({ label, error, icon, className = "", ...props }, ref) => {
    return (
      <div className="flex flex-col gap-1.5">
        {label && <label className="text-sm font-medium text-ink-700">{label}</label>}

        <div className="relative">
          {icon && (
            <div className="absolute left-3.5 top-1/2 -translate-y-1/2 text-ink-300">
              {icon}
            </div>
          )}

          <input
            ref={ref}
            className={`w-full rounded-xl border bg-white px-4 py-2.5 text-sm text-ink-900 placeholder:text-ink-300 transition-all duration-200 outline-none
              ${icon ? "pl-11" : ""}
              ${
                error
                  ? "border-accent-500 focus:ring-2 focus:ring-accent-100"
                  : "border-surface-border focus:border-primary-400 focus:ring-2 focus:ring-primary-100"
              }
              ${className}`}
            {...props}
          />
        </div>

        {error && <span className="text-xs text-accent-600">{error}</span>}
      </div>
    );
  }
);

Input.displayName = "Input";
