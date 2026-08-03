import { Bell, Search } from "lucide-react";
import { useAuth } from "@/hooks/useAuth";

export function Navbar({ title }: { title: string }) {
  const { user } = useAuth();

  const initials = user?.fullName
    ?.split(" ")
    .map((n) => n[0])
    .slice(0, 2)
    .join("")
    .toUpperCase();

  return (
    <header className="glass sticky top-0 z-20 h-16 flex items-center justify-between px-6 border-b border-surface-border">
      <h1 className="font-display font-bold text-lg text-ink-900">{title}</h1>

      <div className="flex items-center gap-3">
        <div className="hidden sm:flex items-center gap-2 bg-surface rounded-xl px-3.5 py-2 border border-surface-border w-64">
          <Search size={16} className="text-ink-300" />
          <input
            placeholder="Search..."
            className="bg-transparent outline-none text-sm placeholder:text-ink-300 w-full"
          />
        </div>

        <button className="relative w-10 h-10 rounded-xl flex items-center justify-center hover:bg-surface-border/60 transition-colors">
          <Bell size={18} className="text-ink-500" />
          <span className="absolute top-2 right-2 w-2 h-2 bg-accent-500 rounded-full" />
        </button>

        <div className="w-9 h-9 rounded-xl bg-gradient-primary flex items-center justify-center text-white text-xs font-semibold">
          {initials || "U"}
        </div>
      </div>
    </header>
  );
}
