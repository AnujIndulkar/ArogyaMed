package com.arogyamed.util;

/**
 * Builds and reads EAN-13 barcodes for medicines.
 * Format: 890 (India prefix) + 9-digit medicine id + 1 check digit = 13 digits.
 * The barcode is calculated from the id, so nothing extra is stored in the database.
 */
public final class MedicineCodeUtil {

    private static final String PREFIX = "890";

    private MedicineCodeUtil() {
    }

    public static String barcodeFor(Long id) {
        String base = PREFIX + String.format("%09d", id);
        return base + checkDigit(base);
    }

    /** Returns the medicine id for a valid barcode, or null if the barcode is not valid. */
    public static Long idFromBarcode(String code) {
        if (code == null || !code.matches("\\d{13}") || !code.startsWith(PREFIX)) {
            return null;
        }
        int expected = checkDigit(code.substring(0, 12));
        if (expected != code.charAt(12) - '0') {
            return null;
        }
        return Long.parseLong(code.substring(3, 12));
    }

    // Standard EAN-13 check digit: digits at even positions x1, odd positions x3
    private static int checkDigit(String twelveDigits) {
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            int digit = twelveDigits.charAt(i) - '0';
            sum += (i % 2 == 0) ? digit : digit * 3;
        }
        return (10 - (sum % 10)) % 10;
    }
}
