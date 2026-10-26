import axiosInstance from "@/api/axiosInstance";

/* ---------------- patient side ---------------- */

export type SosStatus = "PENDING" | "AMBULANCE_ASSIGNED" | "IN_PROGRESS" | "COMPLETED" | "CANCELLED";

export interface MySos {
  sosId: number;
  status: SosStatus;
  emergencyType?: string | null;
  location?: string | null;
  createdAt?: string | null;
  bookingStatus?: string | null;
  ambulanceNumber?: string | null;
  driverName?: string | null;
  driverPhone?: string | null;
  etaMinutes?: number | null;
}

export interface CreateSos {
  latitude: number | null;
  longitude: number | null;
  location: string;
  emergencyType: string;
}

export const patientSosService = {
  create: (body: CreateSos) => axiosInstance.post<MySos>("/sos/my", body).then((r) => r.data),

  // the backend answers 204 (no body) when the patient has no open emergency
  active: () =>
    axiosInstance.get<MySos>("/sos/my/active").then((r) => (r.status === 204 || !r.data ? null : r.data)),

  cancel: (sosId: number) => axiosInstance.post<MySos>(`/sos/my/${sosId}/cancel`).then((r) => r.data),
};

/* ---------------- provider side ---------------- */

export type AmbulanceStatus = "AVAILABLE" | "ON_DUTY" | "MAINTENANCE";
export type BookingStatus = "BOOKED" | "ACCEPTED" | "ON_THE_WAY" | "COMPLETED" | "CANCELLED";

export interface ProviderAmbulance {
  id: number;
  ambulanceNumber: string;
  driverName: string;
  driverPhone: string;
  currentLocation?: string | null;
  status: AmbulanceStatus;
  available: boolean;
  registrationNumber?: string | null;
  verified?: boolean | null;
}

export interface ProviderSummary {
  ambulanceStatus: AmbulanceStatus;
  openRequests: number;
  activeTrips: number;
  completedTrips: number;
  totalTrips: number;
}

export interface SosRequest {
  id: number;
  patientName: string;
  emergencyType?: string | null;
  location?: string | null;
  latitude?: number | null;
  longitude?: number | null;
  createdAt?: string | null;
}

export interface Booking {
  id: number;
  patientName: string;
  sosId?: number | null;
  pickupLocation?: string | null;
  destination?: string | null;
  status: BookingStatus;
  bookedAt?: string | null;
  completedAt?: string | null;
  etaMinutes?: number | null;
  emergencyLevel?: string | null;
}

export const providerService = {
  me: () => axiosInstance.get<ProviderAmbulance>("/ambulance-provider/me").then((r) => r.data),

  summary: () => axiosInstance.get<ProviderSummary>("/ambulance-provider/summary").then((r) => r.data),

  setStatus: (status: "AVAILABLE" | "MAINTENANCE") =>
    axiosInstance
      .patch<ProviderAmbulance>("/ambulance-provider/status", null, { params: { status } })
      .then((r) => r.data),

  requests: () => axiosInstance.get<SosRequest[]>("/ambulance-provider/requests").then((r) => r.data),

  accept: (sosId: number, eta?: number) =>
    axiosInstance
      .post<Booking>(`/ambulance-provider/requests/${sosId}/accept`, null, { params: eta ? { eta } : {} })
      .then((r) => r.data),

  bookings: () => axiosInstance.get<Booking[]>("/ambulance-provider/bookings").then((r) => r.data),

  setBookingStatus: (id: number, status: BookingStatus) =>
    axiosInstance
      .patch<Booking>(`/ambulance-provider/bookings/${id}/status`, null, { params: { status } })
      .then((r) => r.data),
};

/* ---------------- helpers ---------------- */

// the backend wraps its messages like: 409 CONFLICT "the real message"
export function apiMessage(err: unknown, fallback: string): string {
  const e = err as { response?: { status?: number; data?: { message?: unknown } } };

  // no reply at all: backend stopped, restarting, or blocked
  if (!e?.response) {
    return "Cannot reach the server. Check that the backend is running.";
  }

  const raw = e.response.data?.message;
  if (typeof raw === "string" && raw.trim()) {
    const quoted = raw.match(/"([^"]+)"/);
    return quoted ? quoted[1] : raw;
  }

  const status = e.response.status;
  if (status === 403) return "Access denied (error 403). Please log out and log in again.";
  return `${fallback} (error ${status ?? "unknown"})`;
}

export function bookingTone(status: string): "primary" | "success" | "warning" | "accent" | "neutral" {
  if (status === "ACCEPTED") return "primary";
  if (status === "ON_THE_WAY") return "warning";
  if (status === "COMPLETED") return "success";
  if (status === "CANCELLED") return "accent";
  return "neutral";
}

export function label(status: string): string {
  return status.replaceAll("_", " ").toLowerCase().replace(/^\w/, (c) => c.toUpperCase());
}

export function when(date?: string | null): string {
  return date ? new Date(date).toLocaleString() : "-";
}