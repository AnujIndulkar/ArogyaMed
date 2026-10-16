import { useEffect, useRef, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Html5Qrcode, Html5QrcodeSupportedFormats } from "html5-qrcode";

const READER_ID = "medicine-scanner";

export default function ScanMedicineButton() {
  const navigate = useNavigate();
  const [open, setOpen] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [manual, setManual] = useState("");
  const handled = useRef(false); // the camera fires many times per second; act only once

  const close = () => {
    setOpen(false);
    setError(null);
    setManual("");
  };

  // Work out what was scanned and open the verify page
  const handleCode = async (raw: string) => {
    if (handled.current) return;
    const text = raw.trim();

    // 1) QR code from our labels: https://.../verify/491
    const link = text.match(/\/verify\/(\d+)/);
    if (link) {
      handled.current = true;
      close();
      navigate(`/verify/${link[1]}`);
      return;
    }

    // 2) EAN-13 barcode: ask the backend which medicine it belongs to
    if (/^\d{13}$/.test(text)) {
      handled.current = true;
      try {
        const base = import.meta.env.VITE_API_BASE_URL as string; // ends with /api
        const res = await fetch(`${base}/medicines/barcode/${text}`);
        if (!res.ok) throw new Error("not found");
        const data = await res.json();
        close();
        navigate(`/verify/${data.id}`);
      } catch {
        setError("This barcode is not registered on ArogyaMed.");
      } finally {
        handled.current = false;
      }
      return;
    }

    setError("This code is not an ArogyaMed medicine code.");
  };

  // Start the camera when the pop-up opens, stop it when it closes
  useEffect(() => {
    if (!open) return;
    handled.current = false;

    let scanner: Html5Qrcode | null = null;
    let cancelled = false;
    let started = false;

    // small delay so the pop-up is in the page and React's double-run in dev does not clash
    const timer = setTimeout(async () => {
      scanner = new Html5Qrcode(READER_ID, {
        formatsToSupport: [Html5QrcodeSupportedFormats.QR_CODE, Html5QrcodeSupportedFormats.EAN_13],
        verbose: false,
      });
      const config = { fps: 10 };
      const onSuccess = (text: string) => handleCode(text);
      const onFrame = () => {}; // called for every frame with no code; ignore

      try {
        try {
          // phones: use the back camera
          await scanner.start({ facingMode: "environment" }, config, onSuccess, onFrame);
        } catch {
          // laptops usually have no "back" camera: use the first camera that exists
          const cameras = await Html5Qrcode.getCameras();
          if (!cameras.length) throw new Error("NotFoundError");
          await scanner.start(cameras[0].id, config, onSuccess, onFrame);
        }

        if (cancelled) {
          await scanner.stop();
          scanner.clear();
        } else {
          started = true;
        }
      } catch (err) {
        console.error("Camera error:", err);
        const msg = String(err);
        if (msg.includes("NotAllowed") || msg.includes("Permission")) {
          setError("Camera permission is blocked. Click the icon left of the address bar and allow the camera, then reopen this window.");
        } else if (msg.includes("NotFound")) {
          setError("No camera found on this device. Type the barcode number below instead.");
        } else if (msg.includes("NotReadable")) {
          setError("The camera is being used by another app. Close it and try again.");
        } else {
          setError("Camera not available. Type the barcode number below instead.");
        }
      }
    }, 150);

    return () => {
      cancelled = true;
      clearTimeout(timer);
      if (scanner && started) {
        scanner.stop().then(() => scanner?.clear()).catch(() => {});
      }
    };
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [open]);

  return (
    <>
      <button
        type="button"
        onClick={() => setOpen(true)}
        className="rounded-lg border border-gray-300 bg-white px-4 py-2 text-sm font-medium text-gray-700 hover:bg-gray-50"
      >
        Scan barcode / QR
      </button>

      {open && (
        <div
          className="fixed inset-0 z-50 flex items-center justify-center bg-black/60 p-4"
          onClick={close}
        >
          <div
            className="w-full max-w-sm rounded-xl bg-white p-5 shadow-xl"
            onClick={(e) => e.stopPropagation()}
          >
            <p className="text-base font-bold text-gray-900">Scan a medicine</p>
            <p className="mb-3 text-xs text-gray-500">
              Point the camera at the QR code or the barcode on the pack.
            </p>

            <div
              id={READER_ID}
              className="min-h-[200px] overflow-hidden rounded-lg bg-gray-100"
            />

            {error && <p className="mt-3 text-sm text-red-600">{error}</p>}

            <form
              className="mt-4 flex gap-2"
              onSubmit={(e) => {
                e.preventDefault();
                handleCode(manual);
              }}
            >
              <input
                value={manual}
                onChange={(e) => setManual(e.target.value)}
                inputMode="numeric"
                placeholder="Or type the 13-digit barcode"
                className="min-w-0 flex-1 rounded-lg border border-gray-300 px-3 py-2 text-sm outline-none focus:border-gray-500"
              />
              <button
                type="submit"
                className="rounded-lg bg-gray-900 px-4 py-2 text-sm font-medium text-white"
              >
                Find
              </button>
            </form>

            <button
              type="button"
              onClick={close}
              className="mt-3 w-full rounded-lg border border-gray-300 px-4 py-2 text-sm font-medium text-gray-700"
            >
              Close
            </button>
          </div>
        </div>
      )}
    </>
  );
}