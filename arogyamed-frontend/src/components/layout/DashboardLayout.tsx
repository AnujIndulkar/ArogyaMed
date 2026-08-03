import type { ReactNode } from "react";
import { motion } from "framer-motion";
import { Sidebar } from "./Sidebar";
import { Navbar } from "./Navbar";

export function DashboardLayout({
  title,
  children,
}: {
  title: string;
  children: ReactNode;
}) {
  return (
    <div className="flex min-h-screen bg-surface">
      <Sidebar />

      <div className="flex-1 min-w-0">
        <Navbar title={title} />

        <motion.main
          initial={{ opacity: 0, y: 10 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.35 }}
          className="p-6 max-w-[1400px] mx-auto"
        >
          {children}
        </motion.main>
      </div>
    </div>
  );
}
