import { BACKEND_ORIGIN } from "@/constants/config";

export function getMediaUrl(path?: string | null): string | null {
  if (!path) return null;
  if (path.startsWith("http")) return path;
  return `${BACKEND_ORIGIN}${path}`;
}