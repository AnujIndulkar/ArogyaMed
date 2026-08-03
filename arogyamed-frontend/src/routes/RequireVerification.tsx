import { useAuth } from "@/hooks/useAuth";
import { documentService } from "@/services/documentService";
import type { Role } from "@/types/auth.types";
import { useQuery } from "@tanstack/react-query";
import { Navigate, Outlet, useLocation } from "react-router-dom";

// Roles that must complete document verification before accessing their dashboard
const GATED_ROLES: Role[] = ["DOCTOR", "PHARMACIST", "WHOLESALER", "COMPANY", "DELIVERY_PARTNER"];

export function RequireVerification() {
  const { user } = useAuth();
  const location = useLocation();

  const shouldCheck = !!user && GATED_ROLES.includes(user.role);

  const { data: documents, isLoading } = useQuery({
    queryKey: ["verification-check", user?.userId],
    queryFn: () => documentService.getByUser(user!.userId),
    enabled: shouldCheck,
    retry: false,
  });

  if (!shouldCheck) {
    return <Outlet />;
  }

  if (isLoading) {
    return (
      <div className="min-h-screen flex items-center justify-center bg-surface">
        <div className="w-8 h-8 border-2 border-primary-200 border-t-primary-600 rounded-full animate-spin" />
      </div>
    );
  }

  const hasUploadedDocuments = !!documents && documents.length > 0;

  // Avoid redirect loop: don't bounce away from /documents itself
  if (!hasUploadedDocuments && location.pathname !== "/documents") {
    return <Navigate to="/documents" replace state={{ verificationRequired: true }} />;
  }

  return <Outlet />;
}