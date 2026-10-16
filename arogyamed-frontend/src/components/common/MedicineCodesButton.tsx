import { useEffect, useRef, useState } from "react";
import { QRCodeSVG } from "qrcode.react";
import Barcode from "react-barcode";
import { verifyUrl } from "@/utils/medicineLinks";

interface Props {
  medicineId: number;
  medicineName: string;
}

export default function MedicineCodesButton({ medicineId, medicineName }: Props) {
  const [open, setOpen] = useState(false);
  const [barcode, setBarcode] = useState<string | null>(null);
  const [batch, setBatch] = useState<string | undefined>();
  const [failed, setFailed] = useState(false);
  const printRef = useRef<HTMLDivElement>(null);

  // Ask the backend for the barcode when the pop-up opens, so the number
  // is calculated in one place only (MedicineCodeUtil.java).
  useEffect(() => {
    if (!open || barcode) return;
    const base = import.meta.env.VITE_API_BASE_URL as string; // ends with /api
    fetch(`${base}/medicines/verify/${medicineId}`)
      .then((res) => {
        if (!res.ok) throw new Error("failed");
        return res.json();
      })
      .then((d) => {
        setBarcode(d.barcode);
        setBatch(d.batchNumber);
      })
      .catch(() => setFailed(true));
  }, [open, barcode, medicineId]);

  // Opens a clean window containing only the label, then prints it
  const handlePrint = () => {
    if (!printRef.current) return;
    const w = window.open("", "_blank", "width=480,height=640");
    if (!w) return;
    w.document.write(
      `<html><head><title>Medicine label</title></head>` +
        `<body style="font-family:sans-serif;text-align:center;padding:24px">` +
        `${printRef.current.innerHTML}</body></html>`
    );
    w.document.close();
    w.focus();
    w.print();
  };

  return (
    <>
      <button
        type="button"
        onClick={(e) => {
          e.stopPropagation(); // do not trigger the row/card click behind it
          setOpen(true);
        }}
        className="rounded-md border border-gray-300 px-3 py-1 text-xs font-medium text-gray-700 hover:bg-gray-50"
      >
        QR &amp; barcode
      </button>

      {open && (
        <div
          className="fixed inset-0 z-50 flex items-center justify-center bg-black/50 p-4"
          onClick={() => setOpen(false)}
        >
          <div
            className="w-full max-w-sm rounded-xl bg-white p-6 shadow-xl"
            onClick={(e) => e.stopPropagation()}
          >
            <div ref={printRef} style={{ textAlign: "center" }}>
              <p style={{ fontWeight: 700, fontSize: 16, margin: 0 }}>{medicineName}</p>
              {batch && (
                <p style={{ color: "#6b7280", fontSize: 12, margin: "4px 0 16px" }}>
                  Batch {batch}
                </p>
              )}

              <div style={{ display: "flex", justifyContent: "center" }}>
                <QRCodeSVG value={verifyUrl(medicineId)} size={160} />
              </div>
              <p style={{ color: "#6b7280", fontSize: 11, margin: "6px 0 16px" }}>
                Scan to verify this medicine
              </p>

              {barcode ? (
                <div style={{ display: "flex", justifyContent: "center" }}>
                  <Barcode value={barcode} format="EAN13" width={1.6} height={60} fontSize={14} />
                </div>
              ) : (
                <p style={{ color: "#6b7280", fontSize: 12 }}>
                  {failed ? "Could not load the barcode." : "Loading barcode…"}
                </p>
              )}
            </div>

            <div className="mt-5 flex gap-3">
              <button
                type="button"
                onClick={handlePrint}
                className="flex-1 rounded-lg bg-gray-900 px-4 py-2 text-sm font-medium text-white"
              >
                Print label
              </button>
              <button
                type="button"
                onClick={() => setOpen(false)}
                className="flex-1 rounded-lg border border-gray-300 px-4 py-2 text-sm font-medium text-gray-700"
              >
                Close
              </button>
            </div>
          </div>
        </div>
      )}
    </>
  );
}