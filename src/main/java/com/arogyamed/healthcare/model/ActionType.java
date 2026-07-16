package com.arogyamed.healthcare.model;

public enum ActionType {

    // =========================
    // Authentication
    // =========================

    LOGIN,
    LOGOUT,
    PASSWORD_CHANGED,
    PASSWORD_RESET,

    // =========================
    // User Management
    // =========================

    USER_CREATED,
    USER_UPDATED,
    USER_DELETED,

    // =========================
    // Patient
    // =========================

    PATIENT_CREATED,
    PATIENT_UPDATED,
    PATIENT_DELETED,

    // =========================
    // Doctor
    // =========================

    DOCTOR_CREATED,
    DOCTOR_UPDATED,
    DOCTOR_DELETED,

    // =========================
    // Pharmacist
    // =========================

    PHARMACIST_CREATED,
    PHARMACIST_UPDATED,
    PHARMACIST_DELETED,

    // =========================
    // Medicine
    // =========================

    MEDICINE_CREATED,
    MEDICINE_UPDATED,
    MEDICINE_DELETED,

    // =========================
    // Inventory
    // =========================

    STOCK_ADDED,
    STOCK_UPDATED,
    STOCK_REMOVED,

    // =========================
    // Prescription
    // =========================

    PRESCRIPTION_CREATED,
    PRESCRIPTION_UPDATED,

    // =========================
    // Appointment
    // =========================

    APPOINTMENT_BOOKED,
    APPOINTMENT_UPDATED,
    APPOINTMENT_CANCELLED,
    APPOINTMENT_COMPLETED,

    // =========================
    // Order
    // =========================

    ORDER_CREATED,
    ORDER_UPDATED,
    ORDER_CANCELLED,
    ORDER_DELIVERED,

    // =========================
    // Payment
    // =========================

    PAYMENT_INITIATED,
    PAYMENT_COMPLETED,
    PAYMENT_FAILED,

    // =========================
    // Delivery
    // =========================

    DELIVERY_ASSIGNED,
    DELIVERY_STARTED,
    DELIVERY_COMPLETED,

    // =========================
    // KYC
    // =========================

    KYC_SUBMITTED,
    KYC_APPROVED,
    KYC_REJECTED,

    // =========================
    // Quality Check
    // =========================

    QUALITY_CHECK_CREATED,
    QUALITY_CHECK_APPROVED,
    QUALITY_CHECK_REJECTED,

    // =========================
    // Review
    // =========================

    REVIEW_CREATED,
    REVIEW_UPDATED,
    REVIEW_DELETED,

    // =========================
    // Emergency
    // =========================

    SOS_CREATED,
    AMBULANCE_ASSIGNED,

    // =========================
    // Admin
    // =========================

    ADMIN_LOGIN,
    ADMIN_UPDATED,
    ADMIN_SETTINGS_CHANGED,

    // =========================
    // Dashboard
    // =========================

    DASHBOARD_VIEWED
}