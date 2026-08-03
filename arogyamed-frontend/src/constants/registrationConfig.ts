import type { Role } from "@/types/auth.types";
import type { RoleRegistrationConfig } from "@/types/registration.types";

export const REGISTRATION_CONFIG: Record<Role, RoleRegistrationConfig> = {
  PATIENT: {
    role: "PATIENT",
    title: "Patient",
    subtitle: "Book doctors, order medicine, and manage your health",
    profileEndpoint: "/patients",
    isVerificationGated: false,
    fields: [
      { name: "age", label: "Age", type: "number", required: true },
      {
        name: "gender",
        label: "Gender",
        type: "select",
        required: true,
        options: [
          { value: "MALE", label: "Male" },
          { value: "FEMALE", label: "Female" },
          { value: "OTHER", label: "Other" },
        ],
      },
      { name: "bloodGroup", label: "Blood group", type: "text", placeholder: "e.g. O+" },
      { name: "dateOfBirth", label: "Date of birth", type: "date", required: true },
      { name: "city", label: "City", type: "text", required: true },
      { name: "state", label: "State", type: "text", required: true },
      { name: "pincode", label: "Pincode", type: "text", required: true },
      { name: "emergencyContactName", label: "Emergency contact name", type: "text" },
      { name: "emergencyContactNumber", label: "Emergency contact number", type: "text" },
    ],
    requiredDocuments: [],
  },

  DOCTOR: {
    role: "DOCTOR",
    title: "Doctor",
    subtitle: "Consult patients and manage appointments on ArogyaMed",
    profileEndpoint: "/doctors",
    isVerificationGated: true,
    fields: [
      { name: "specialization", label: "Specialization", type: "text", required: true, placeholder: "e.g. Cardiologist" },
      { name: "qualification", label: "Qualification", type: "text", required: true, placeholder: "e.g. MBBS, MD" },
      { name: "experienceYears", label: "Years of experience", type: "number", required: true },
      { name: "hospitalName", label: "Hospital / Clinic name", type: "text", required: true },
      { name: "consultationFee", label: "Consultation fee (₹)", type: "number", required: true },
      { name: "licenseNumber", label: "Medical license number", type: "text", required: true },
    ],
    requiredDocuments: [
      {
        documentType: "MEDICAL_LICENSE",
        label: "Medical license / degree certificate",
        description: "Your MBBS/MD degree or medical council registration certificate",
        reuseFieldAsNumber: "licenseNumber",
      },
      {
        documentType: "AADHAAR",
        label: "Aadhaar card",
        description: "Government-issued identity proof",
        numberLabel: "Last 4 digits of Aadhaar",
        numberMaxLength: 4,
      },
    ],
  },

  PHARMACIST: {
    role: "PHARMACIST",
    title: "Pharmacist",
    subtitle: "Manage your pharmacy's inventory, orders, and prescriptions",
    profileEndpoint: "/pharmacists",
    isVerificationGated: true,
    fields: [
      { name: "pharmacyName", label: "Pharmacy name", type: "text", required: true },
      { name: "licenseNumber", label: "Pharmacy license number", type: "text", required: true },
      { name: "experienceYears", label: "Years of experience", type: "number", required: true },
      { name: "pharmacyAddress", label: "Pharmacy address", type: "textarea", required: true },
    ],
    requiredDocuments: [
      {
        documentType: "PHARMACY_LICENSE",
        label: "Pharmacy license certificate",
        reuseFieldAsNumber: "licenseNumber",
      },
      {
        documentType: "AADHAAR",
        label: "Aadhaar card",
        numberLabel: "Last 4 digits of Aadhaar",
        numberMaxLength: 4,
      },
    ],
  },

  WHOLESALER: {
    role: "WHOLESALER",
    title: "Wholesaler",
    subtitle: "Supply medicine stock to pharmacies at scale",
    profileEndpoint: "/wholesalers",
    isVerificationGated: true,
    fields: [
      { name: "companyName", label: "Business name", type: "text", required: true },
      { name: "licenseNumber", label: "Drug license number", type: "text", required: true },
      { name: "gstNumber", label: "GST number", type: "text", required: true },
      { name: "warehouseAddress", label: "Warehouse address", type: "textarea", required: true },
      { name: "contactPerson", label: "Contact person", type: "text", required: true },
    ],
    requiredDocuments: [
      {
        documentType: "GST",
        label: "GST certificate",
        reuseFieldAsNumber: "gstNumber",
      },
      {
        documentType: "COMPANY_REGISTRATION",
        label: "Business registration certificate",
      },
    ],
  },

  COMPANY: {
    role: "COMPANY",
    title: "Medicine Company",
    subtitle: "Manufacture and distribute medicines through ArogyaMed",
    profileEndpoint: "/companies",
    isVerificationGated: true,
    fields: [
      { name: "companyName", label: "Company name", type: "text", required: true },
      { name: "licenseNumber", label: "Manufacturing license number", type: "text", required: true },
      { name: "gstNumber", label: "GST number", type: "text", required: true },
      { name: "companyAddress", label: "Company address", type: "textarea", required: true },
      { name: "contactPerson", label: "Contact person", type: "text", required: true },
    ],
    requiredDocuments: [
      {
        documentType: "GST",
        label: "GST certificate",
        reuseFieldAsNumber: "gstNumber",
      },
      {
        documentType: "COMPANY_REGISTRATION",
        label: "Company registration certificate",
      },
    ],
  },

  DELIVERY_PARTNER: {
    role: "DELIVERY_PARTNER",
    title: "Delivery Partner",
    subtitle: "Deliver medicine orders and earn on your schedule",
    profileEndpoint: "/delivery-partners",
    isVerificationGated: true,
    fields: [
      { name: "vehicleNumber", label: "Vehicle number", type: "text", required: true, placeholder: "e.g. MH12AB1234" },
      {
        name: "vehicleType",
        label: "Vehicle type",
        type: "select",
        required: true,
        options: [
          { value: "BIKE", label: "Bike" },
          { value: "SCOOTER", label: "Scooter" },
          { value: "CAR", label: "Car" },
          { value: "VAN", label: "Van" },
        ],
      },
      { name: "drivingLicenseNumber", label: "Driving license number", type: "text", required: true },
    ],
    requiredDocuments: [
      {
        documentType: "DRIVING_LICENSE",
        label: "Driving license",
        reuseFieldAsNumber: "drivingLicenseNumber",
      },
      {
        documentType: "AADHAAR",
        label: "Aadhaar card",
        numberLabel: "Last 4 digits of Aadhaar",
        numberMaxLength: 4,
      },
    ],
  },

  ADMIN: {
    role: "ADMIN",
    title: "Admin",
    subtitle: "",
    profileEndpoint: "",
    isVerificationGated: false,
    fields: [],
    requiredDocuments: [],
  },
};