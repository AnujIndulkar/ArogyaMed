import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";

interface VerifyResult {
  id: number;
  medicineName: string;
  genericName?: string;
  packSize?: string;
  companyName?: string;
  batchNumber?: string;
  manufacturingDate?: string;
  expiryDate?: string;
  price?: number;
  imageUrl?: string;
  barcode: string;
  inStock: boolean;
  status: "VALID" | "EXPIRING_SOON" | "EXPIRED";
  statusMessage: string;
}

// Inline colours on purpose: they do not depend on the Tailwind theme
const STATUS_STYLE = {
  VALID: { bg: "#ecfdf5", border: "#10b981", text: "#047857", label: "Registered and in date" },
  EXPIRING_SOON: { bg: "#fffbeb", border: "#f59e0b", text: "#b45309", label: "Expiring soon" },
  EXPIRED: { bg: "#fef2f2", border: "#ef4444", text: "#b91c1c", label: "Expired" },
} as const;

export default function VerifyMedicine() {
  const { id } = useParams<{ id: string }>();
  const [data, setData] = useState<VerifyResult | null>(null);
  const [loading, setLoading] = useState(true);
  const [notFound, setNotFound] = useState(false);
  const [imgFailed, setImgFailed] = useState(false);

  useEffect(() => {
    const base = import.meta.env.VITE_API_BASE_URL as string; // ends with /api
    setLoading(true);
    setNotFound(false);
    fetch(`${base}/medicines/verify/${id}`)
      .then((res) => {
        if (!res.ok) throw new Error("not found");
        return res.json();
      })
      .then((json: VerifyResult) => setData(json))
      .catch(() => setNotFound(true))
      .finally(() => setLoading(false));
  }, [id]);

  return (
    <div className="min-h-screen bg-gray-50 px-4 py-8">
      <div className="mx-auto max-w-md">
        <p className="mb-4 text-center text-sm font-semibold tracking-wide text-gray-500">
          ArogyaMed · Medicine verification
        </p>

        {loading && <p className="text-center text-gray-500">Checking this medicine…</p>}

        {!loading && notFound && (
          <div className="rounded-xl border border-red-300 bg-red-50 p-6 text-center">
            <p className="text-lg font-semibold text-red-700">Not recognised</p>
            <p className="mt-2 text-sm text-red-700">
              This code is not registered on ArogyaMed. Do not rely on this medicine until you have
              checked it with a pharmacist.
            </p>
          </div>
        )}

        {!loading && data && (
          <div className="overflow-hidden rounded-xl border border-gray-200 bg-white shadow-sm">
            <div
              style={{
                background: STATUS_STYLE[data.status].bg,
                borderBottom: `2px solid ${STATUS_STYLE[data.status].border}`,
                color: STATUS_STYLE[data.status].text,
              }}
              className="px-5 py-4"
            >
              <p className="text-lg font-bold">{STATUS_STYLE[data.status].label}</p>
              <p className="text-sm">{data.statusMessage}</p>
            </div>

            <div className="p-5">
              <div className="flex gap-4">
                {data.imageUrl && !imgFailed && (
                  <img
                    src={data.imageUrl}
                    alt={data.medicineName}
                    onError={() => setImgFailed(true)}
                    className="h-24 w-24 rounded-lg border border-gray-100 object-contain"
                  />
                )}
                <div className="min-w-0">
                  <h1 className="text-xl font-bold text-gray-900">{data.medicineName}</h1>
                  {data.genericName && (
                    <p className="mt-1 text-sm text-gray-600">{data.genericName}</p>
                  )}
                  {data.packSize && <p className="text-sm text-gray-500">{data.packSize}</p>}
                </div>
              </div>

              <dl className="mt-5 divide-y divide-gray-100 text-sm">
                <Row label="Manufacturer" value={data.companyName} />
                <Row label="Batch number" value={data.batchNumber} />
                <Row label="Manufactured" value={data.manufacturingDate} />
                <Row label="Expiry date" value={data.expiryDate} />
                <Row label="Barcode" value={data.barcode} />
                <Row label="Availability" value={data.inStock ? "In stock" : "Out of stock"} />
                {data.price != null && <Row label="MRP" value={`₹${data.price}`} />}
              </dl>

              <Link
                to={`/patient/medicines/${data.id}`}
                className="mt-5 block rounded-lg bg-gray-900 px-4 py-2 text-center text-sm font-medium text-white"
              >
                View full medicine page
              </Link>
            </div>
          </div>
        )}

        <p className="mt-6 text-center text-xs text-gray-400">
          “Registered” means this batch exists in the ArogyaMed database. It does not replace a
          pharmacist’s check. This is a demo platform.
        </p>
      </div>
    </div>
  );
}

function Row({ label, value }: { label: string; value?: string }) {
  if (!value) return null;
  return (
    <div className="flex justify-between gap-4 py-2">
      <dt className="text-gray-500">{label}</dt>
      <dd className="text-right font-medium text-gray-900">{value}</dd>
    </div>
  );
}