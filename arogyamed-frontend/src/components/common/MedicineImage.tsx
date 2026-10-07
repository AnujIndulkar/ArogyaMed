import { useState } from "react";
import { getMediaUrl } from "@/utils/media";
import { MedicineVisual } from "./MedicineVisual";

interface MedicineImageProps {
  name?: string;
  packSize?: string;
  imageUrl?: string | null;
  className?: string;
}

/**
 * Shows the real product photo when there is one. If there is no photo, or the
 * photo fails to load (link broken / blocked), it falls back to the drawn illustration.
 */
export function MedicineImage({ name, packSize, imageUrl, className = "" }: MedicineImageProps) {
  const [failed, setFailed] = useState(false);
  const src = getMediaUrl(imageUrl);

  if (!src || failed) {
    return <MedicineVisual name={name} packSize={packSize} className={className} />;
  }

  return (
    <div className={`flex items-center justify-center bg-white ${className}`}>
      <img
        src={src}
        alt={name ?? "Medicine"}
        loading="lazy"
        referrerPolicy="no-referrer"
        onError={() => setFailed(true)}
        className="w-full h-full object-contain p-2"
      />
    </div>
  );
}