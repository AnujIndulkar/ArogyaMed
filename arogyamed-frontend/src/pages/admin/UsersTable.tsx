import { Users } from "lucide-react";
import { DashboardLayout } from "@/components/layout/DashboardLayout";
import { Card } from "@/components/common/Card";
import { DataTable, type Column } from "@/components/common/DataTable";
import { Badge } from "@/components/common/Badge";
import { useCrud } from "@/hooks/useCrud";
import { userService } from "@/services/domainServices";
import type { AppUser } from "@/types/common.types";

const roleTone: Record<string, "primary" | "success" | "warning" | "accent" | "neutral"> = {
  ADMIN: "accent",
  DOCTOR: "primary",
  PATIENT: "success",
  PHARMACIST: "warning",
};

export default function UsersTable() {
  const { items, isLoading, remove } = useCrud<AppUser>("admin-users", userService);

  const columns: Column<AppUser>[] = [
    { header: "Name", accessor: (u) => <span className="font-medium text-ink-900">{u.fullName}</span> },
    { header: "Email", accessor: (u) => u.email },
    { header: "Phone", accessor: (u) => u.phoneNumber },
    {
      header: "Role",
      accessor: (u) => <Badge tone={roleTone[u.role] ?? "neutral"}>{u.role}</Badge>,
    },
    {
      header: "Status",
      accessor: (u) => <Badge tone={u.active === false ? "accent" : "success"}>{u.active === false ? "Inactive" : "Active"}</Badge>,
    },
    {
      header: "",
      accessor: (u) => (
        <button
          onClick={() => remove(u.id)}
          className="text-xs text-accent-600 hover:underline"
        >
          Deactivate
        </button>
      ),
      className: "text-right",
    },
  ];

  return (
    <DashboardLayout title="User Management">
      <Card>
        <DataTable
          data={items}
          isLoading={isLoading}
          columns={columns}
          keyField={(u) => u.id}
          searchPlaceholder="Search users..."
          emptyIcon={Users}
          emptyTitle="No users found"
        />
      </Card>
    </DashboardLayout>
  );
}
