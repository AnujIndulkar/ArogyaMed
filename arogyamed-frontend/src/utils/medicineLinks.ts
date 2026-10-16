// The link that goes inside the QR code.
// Locally it is http://localhost:5173/verify/491
// After deployment, set VITE_PUBLIC_URL=https://your-app.vercel.app so printed QR codes always point to the live site.
export function verifyUrl(medicineId: number | string): string {
  const base =
    (import.meta.env.VITE_PUBLIC_URL as string | undefined)?.replace(/\/$/, "") ||
    window.location.origin;
  return `${base}/verify/${medicineId}`;
}