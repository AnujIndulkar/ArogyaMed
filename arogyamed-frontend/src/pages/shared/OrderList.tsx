import { ShoppingBag } from "lucide-react";
import { DashboardLayout } from "@/components/layout/DashboardLayout";
import { Card } from "@/components/common/Card";
import { DataTable, type Column } from "@/components/common/DataTable";
import { Badge, statusTone } from "@/components/common/Badge";
import { useCrud } from "@/hooks/useCrud";
import { orderService } from "@/services/domainServices";
import type { Order } from "@/types/common.types";

export default function OrderList() {
  const { items, isLoading, update } = useCrud<Order>("orders", orderService);

  const columns: Column<Order>[] = [
    { header: "Order ID", accessor: (o) => <span className="font-mono text-ink-900">#{o.id}</span> },
    { header: "Patient", accessor: (o) => <span className="font-mono">#{o.patientId}</span> },
    { header: "Amount", accessor: (o) => `₹${o.totalAmount}` },
    { header: "Status", accessor: (o) => <Badge tone={statusTone(o.status)}>{o.status}</Badge> },
    {
      header: "Update status",
      accessor: (o) => (
        <select
          defaultValue={o.status}
          onChange={(e) => update({ id: o.id, payload: { status: e.target.value as Order["status"] } })}
          className="text-xs border border-surface-border rounded-lg px-2 py-1.5 outline-none focus:border-primary-400"
        >
          {["PENDING", "CONFIRMED", "SHIPPED", "DELIVERED", "CANCELLED"].map((s) => (
            <option key={s} value={s}>{s}</option>
          ))}
        </select>
      ),
    },
  ];

  return (
    <DashboardLayout title="Orders">
      <Card>
        <DataTable
          data={items}
          isLoading={isLoading}
          columns={columns}
          keyField={(o) => o.id}
          searchable={false}
          emptyIcon={ShoppingBag}
          emptyTitle="No orders yet"
        />
      </Card>
    </DashboardLayout>
  );
}
