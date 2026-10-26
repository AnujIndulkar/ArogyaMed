import { Badge } from "@/components/common/Badge";
import { Button } from "@/components/common/Button";
import { Card } from "@/components/common/Card";
import { DataTable, type Column } from "@/components/common/DataTable";
import { Modal } from "@/components/common/Modal";
import { DashboardLayout } from "@/components/layout/DashboardLayout";
import {
  inspectionError,
  inspectionService,
  type PendingBatch,
  type SubmitInspection,
} from "@/services/inspectionService";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { ClipboardList, Search } from "lucide-react";
import { useEffect, useState } from "react";
import { toast } from "sonner";

type Checks = {
  packagingVerified: boolean;
  sealVerified: boolean;
  temperatureVerified: boolean;
  expiryVerified: boolean;
};

const EMPTY_CHECKS: Checks = {
  packagingVerified: false,
  sealVerified: false,
  temperatureVerified: false,
  expiryVerified: false,
};

const CHECK_ITEMS: { key: keyof Checks; label: string; hint: string }[] = [
  { key: "packagingVerified", label: "Packaging is intact", hint: "No damage, correct labels and printing" },
  { key: "sealVerified", label: "Seal is unbroken", hint: "Tamper-proof seal present and undamaged" },
  { key: "temperatureVerified", label: "Storage temperature is correct", hint: "Kept within the allowed range" },
  { key: "expiryVerified", label: "Expiry date is valid", hint: "Not expired and matches the label" },
];

function isExpired(date?: string | null): boolean {
  return !!date && new Date(date) < new Date(new Date().toDateString());
}

export default function QualityInspections() {
  const queryClient = useQueryClient();

  const [search, setSearch] = useState("");
  const [q, setQ] = useState("");
  const [selected, setSelected] = useState<PendingBatch | null>(null);
  const [checks, setChecks] = useState<Checks>(EMPTY_CHECKS);
  const [remarks, setRemarks] = useState("");

  // wait a moment after typing before asking the server
  useEffect(() => {
    const timer = setTimeout(() => setQ(search.trim()), 300);
    return () => clearTimeout(timer);
  }, [search]);

  const { data: batches, isLoading } = useQuery({
    queryKey: ["inspections-pending", q],
    queryFn: () => inspectionService.pending(q),
    retry: false,
  });

  const submitMutation = useMutation({
    mutationFn: (body: SubmitInspection) => inspectionService.submit(body),
    onSuccess: (_, body) => {
      toast.success(body.approve ? "Batch approved" : "Batch rejected");
      closeModal();
      queryClient.invalidateQueries({ queryKey: ["inspections-pending"] });
      queryClient.invalidateQueries({ queryKey: ["inspections-mine"] });
      queryClient.invalidateQueries({ queryKey: ["inspections-summary"] });
    },
    onError: (err) => toast.error(inspectionError(err, "Couldn't save the inspection")),
  });

  function openModal(batch: PendingBatch) {
    setSelected(batch);
    setChecks(EMPTY_CHECKS);
    setRemarks("");
  }

  function closeModal() {
    setSelected(null);
  }

  function submit(approve: boolean) {
    if (!selected) return;
    submitMutation.mutate({ medicineId: selected.medicineId, ...checks, remarks: remarks.trim(), approve });
  }

  const allChecked = Object.values(checks).every(Boolean);

  const columns: Column<PendingBatch>[] = [
    {
      header: "Medicine",
      accessor: (b) => <span className="font-medium text-ink-900">{b.medicineName}</span>,
    },
    { header: "Company", accessor: (b) => b.companyName },
    { header: "Batch", accessor: (b) => <span className="font-mono text-xs">{b.batchNumber}</span> },
    { header: "Manufactured", accessor: (b) => b.manufacturingDate ?? "—" },
    {
      header: "Expiry",
      accessor: (b) =>
        isExpired(b.expiryDate) ? <Badge tone="accent">Expired {b.expiryDate}</Badge> : (b.expiryDate ?? "—"),
    },
    {
      header: "",
      accessor: (b) => (
        <Button size="sm" onClick={() => openModal(b)}>
          Inspect
        </Button>
      ),
      className: "text-right",
    },
  ];

  return (
    <DashboardLayout title="Pending Inspections">
      <Card>
        <div className="flex items-center gap-2 bg-surface rounded-xl px-3.5 py-2.5 border border-surface-border w-full max-w-xs mb-5">
          <Search size={16} className="text-ink-300 shrink-0" />
          <input
            value={search}
            onChange={(e) => setSearch(e.target.value)}
            placeholder="Search by medicine name"
            className="bg-transparent outline-none text-sm placeholder:text-ink-300 w-full"
          />
        </div>

        <DataTable
          data={batches}
          columns={columns}
          isLoading={isLoading}
          keyField={(b) => b.medicineId}
          searchable={false}
          emptyIcon={ClipboardList}
          emptyTitle="Nothing to inspect"
          emptyDescription={q ? "No waiting batch matches your search." : "Every batch has been inspected."}
        />

        <p className="text-xs text-ink-500 mt-4">Showing up to 50 batches. Use the search to find a specific medicine.</p>
      </Card>

      <Modal open={!!selected} onClose={closeModal} title="Inspect batch">
        {selected && (
          <div className="flex flex-col gap-4">
            <div>
              <p className="font-display font-semibold text-ink-900">{selected.medicineName}</p>
              <p className="text-xs text-ink-500 mt-0.5">
                {selected.companyName} · <span className="font-mono">{selected.batchNumber}</span>
              </p>
              {isExpired(selected.expiryDate) && (
                <p className="text-xs text-red-600 mt-1">This batch expired on {selected.expiryDate}.</p>
              )}
            </div>

            <div className="flex flex-col gap-2.5">
              {CHECK_ITEMS.map((item) => (
                <label key={item.key} className="flex items-start gap-3 cursor-pointer">
                  <input
                    type="checkbox"
                    checked={checks[item.key]}
                    onChange={(e) => setChecks((c) => ({ ...c, [item.key]: e.target.checked }))}
                    className="mt-1 h-4 w-4 accent-primary-600"
                  />
                  <span>
                    <span className="text-sm font-medium text-ink-900">{item.label}</span>
                    <span className="block text-xs text-ink-500">{item.hint}</span>
                  </span>
                </label>
              ))}
            </div>

            <div className="flex flex-col gap-1.5">
              <label className="text-sm font-medium text-ink-700">
                Remarks <span className="text-ink-400 font-normal">(required to reject)</span>
              </label>
              <textarea
                rows={3}
                maxLength={1000}
                value={remarks}
                onChange={(e) => setRemarks(e.target.value)}
                placeholder="Describe any problem you found"
                className="w-full rounded-xl border border-surface-border bg-white px-4 py-2.5 text-sm outline-none focus:border-primary-400 focus:ring-2 focus:ring-primary-100 resize-none"
              />
            </div>

            <div className="flex gap-3">
              <Button
                variant="danger"
                className="flex-1"
                disabled={remarks.trim().length === 0}
                isLoading={submitMutation.isPending && submitMutation.variables?.approve === false}
                onClick={() => submit(false)}
              >
                Reject
              </Button>
              <Button
                className="flex-1"
                disabled={!allChecked}
                isLoading={submitMutation.isPending && submitMutation.variables?.approve === true}
                onClick={() => submit(true)}
              >
                Approve
              </Button>
            </div>

            {!allChecked && (
              <p className="text-xs text-ink-500 text-center">Tick all four checks to enable Approve.</p>
            )}
          </div>
        )}
      </Modal>
    </DashboardLayout>
  );
}