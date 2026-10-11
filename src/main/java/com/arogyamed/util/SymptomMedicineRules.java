package com.arogyamed.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Very small, deliberately conservative rule set for the AI Symptom Checker.
 * It only ever points to two common over-the-counter ingredients
 * (paracetamol, cetirizine) for mild, short-lived symptoms, and it stays silent
 * for children, pregnancy, long-lasting or serious-sounding symptoms.
 * It never gives a dose.
 */
public final class SymptomMedicineRules {

    private SymptomMedicineRules() {
    }

    /** An ingredient to look for in the medicine catalog, and why it is mentioned. */
    public record Rule(String ingredient, String purpose) {
    }

    private static final String[] PARACETAMOL_WORDS = {
            "fever", "temperature", "headache", "head ache", "body ache", "body pain",
            "bodyache", "muscle pain", "muscle ache", "toothache", "tooth pain"
    };

    private static final String[] ANTIHISTAMINE_WORDS = {
            "runny nose", "sneez", "allergy", "allergic", "itching", "itchy", "hives", "hay fever",
            "watery eyes"
    };

    // any of these -> do not suggest medicines at all, just send the person to a doctor
    private static final String[] STOP_WORDS = {
            "pregnan", "breastfeed", "breast feed", "nursing", "liver", "kidney", "allergic to",
            "severe", "unbearable", "blood", "stiff neck", "confusion", "fainting", "faint",
            "jaundice", "swelling of", "swollen lips", "difficulty swallowing", "high fever",
            "overdose", "alcohol"
    };

    // "3 days", "5 days", "2 weeks", "a week", "month" ... = lasting too long for self-care
    private static final Pattern LONG_LASTING = Pattern.compile(
            "\\b([3-9]|[1-9][0-9])\\s*(days?|weeks?|months?)\\b|\\b(a|one)\\s*(week|month)\\b|\\b(weeks|months)\\b");

    /** false = do not show any medicine suggestion for this input. */
    public static boolean isSafeForSuggestions(String symptoms, Integer age) {
        if (symptoms == null || symptoms.isBlank()) return false;
        if (age != null && (age < 12 || age > 100)) return false;

        String text = symptoms.toLowerCase(Locale.ROOT);
        for (String w : STOP_WORDS) {
            if (text.contains(w)) return false;
        }
        return !LONG_LASTING.matcher(text).find();
    }

    /** Which ingredients match the described symptoms (empty list if none). */
    public static List<Rule> match(String symptoms) {
        List<Rule> rules = new ArrayList<>();
        if (symptoms == null) return rules;

        String text = symptoms.toLowerCase(Locale.ROOT);

        if (containsAny(text, PARACETAMOL_WORDS)) {
            rules.add(new Rule("paracetamol",
                    "Paracetamol is commonly used for fever and mild pain such as headache or body ache."));
        }
        if (containsAny(text, ANTIHISTAMINE_WORDS)) {
            rules.add(new Rule("cetirizine",
                    "Cetirizine-type antihistamines are commonly used for allergy symptoms such as sneezing, runny nose or itching."));
        }
        return rules;
    }

    private static boolean containsAny(String text, String[] words) {
        for (String w : words) {
            if (text.contains(w)) return true;
        }
        return false;
    }
}
