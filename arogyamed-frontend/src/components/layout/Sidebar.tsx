import { NavLink } from "react-router-dom";
import { useState } from "react";
import { ChevronsLeft, ChevronsRight, LogOut } from "lucide-react";
import { useAuth } from "@/hooks/useAuth";
import { NAV_ITEMS } from "@/constants/navigation";
import { ROLE_LABEL } from "@/constants/roles";

export function Sidebar() {
  const { user, logout } = useAuth();
  const [collapsed, setCollapsed] = useState(false);

  if (!user) return null;

  const items = NAV_ITEMS[user.role];

  return (
    <aside
      className={`hidden md:flex flex-col h-screen sticky top-0 bg-white border-r border-surface-border transition-all duration-300 ${
        collapsed ? "w-[76px]" : "w-64"
      }`}
    >
      <div className="flex items-center gap-2.5 px-5 h-16 border-b border-surface-border">
        <div className="w-9 h-9 shrink-0 rounded-xl bg-gradient-primary flex items-center justify-center text-white font-display font-bold">
          A
        </div>
        {!collapsed && (
          <span className="font-display font-bold text-ink-900 text-lg truncate">
            ArogyaMed
          </span>
        )}
      </div>

      <nav className="flex-1 px-3 py-4 flex flex-col gap-1 overflow-y-auto">
        {items.map((item) => (
          <NavLink
            key={item.path}
            to={item.path}
            className={({ isActive }) =>
              `flex items-center gap-3 px-3 py-2.5 rounded-xl text-sm font-medium transition-all duration-150 ${
                isActive
                  ? "bg-primary-50 text-primary-700"
                  : "text-ink-500 hover:bg-surface-border/60 hover:text-ink-900"
              }`
            }
          >
            <item.icon size={18} className="shrink-0" />
            {!collapsed && <span className="truncate">{item.label}</span>}
          </NavLink>
        ))}
      </nav>

      <div className="p-3 border-t border-surface-border flex flex-col gap-1">
        {!collapsed && (
          <div className="px-3 py-2 mb-1">
            <p className="text-sm font-medium text-ink-900 truncate">{user.fullName}</p>
            <p className="text-xs text-ink-500">{ROLE_LABEL[user.role]}</p>
          </div>
        )}

        <button
          onClick={logout}
          className="flex items-center gap-3 px-3 py-2.5 rounded-xl text-sm font-medium text-ink-500 hover:bg-accent-50 hover:text-accent-600 transition-colors"
        >
          <LogOut size={18} />
          {!collapsed && <span>Log out</span>}
        </button>

        <button
          onClick={() => setCollapsed((c) => !c)}
          className="flex items-center gap-3 px-3 py-2.5 rounded-xl text-sm font-medium text-ink-300 hover:bg-surface-border/60 hover:text-ink-700 transition-colors"
        >
          {collapsed ? <ChevronsRight size={18} /> : <ChevronsLeft size={18} />}
          {!collapsed && <span>Collapse</span>}
        </button>
      </div>
    </aside>
  );
}
