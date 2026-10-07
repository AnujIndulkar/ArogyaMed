import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * One-time tool: builds a small, clean medicines.json for ArogyaMed from the big
 * junioralive/Indian-Medicine-Dataset file (no libraries needed, plain Java 17+).
 *
 * Usage (from project root):
 *   java tools/PrepareMedicines.java indian_medicine_data.json src/main/resources/medicines.json
 *
 * Optional 3rd argument: the 1mg-style CSV ("India Medicines and Drug Info Dataset.csv").
 * When given, only medicines that have a real product image in that CSV are kept,
 * and the image URL is written into medicines.json.
 */
public class PrepareMedicines {

    static final int PER_COMPANY = 35; // medicines taken from each manufacturer

    // Manufacturers that become demo companies (name must match the dataset exactly)
    static final List<String> COMPANIES = List.of(
            "Sun Pharmaceutical Industries Ltd",
            "Cipla Ltd",
            "Lupin Ltd",
            "Torrent Pharmaceuticals Ltd",
            "Alkem Laboratories Ltd",
            "Zydus Cadila",
            "Micro Labs Ltd",
            "Abbott",
            "Mankind Pharma Ltd",
            "Dr Reddy's Laboratories Ltd",
            "Glenmark Pharmaceuticals Ltd",
            "Alembic Pharmaceuticals Ltd",
            "Macleods Pharmaceuticals Pvt Ltd",
            "Intas Pharmaceuticals Ltd");

    // category name -> keywords found in the main composition (checked in this order)
    static final LinkedHashMap<String, String[]> CATEGORY_RULES = new LinkedHashMap<>();

    static {
        CATEGORY_RULES.put("Pain & Fever", new String[]{"paracetamol", "ibuprofen", "diclofenac", "aceclofenac", "nimesulide", "etoricoxib", "naproxen"});
        CATEGORY_RULES.put("Antibiotics", new String[]{"amoxycillin", "amoxicillin", "azithromycin", "cefixime", "cefpodoxime", "ceftriaxone", "ofloxacin", "levofloxacin", "ciprofloxacin", "doxycycline", "linezolid", "metronidazole", "clarithromycin", "cefuroxime"});
        CATEGORY_RULES.put("Diabetes", new String[]{"metformin", "glimepiride", "gliclazide", "sitagliptin", "vildagliptin", "teneligliptin", "insulin", "dapagliflozin", "pioglitazone"});
        CATEGORY_RULES.put("Stomach & Acidity", new String[]{"pantoprazole", "rabeprazole", "omeprazole", "esomeprazole", "domperidone", "ranitidine", "famotidine", "ondansetron", "lansoprazole", "levosulpiride", "itopride", "sucralfate", "mosapride", "ursodeoxycholic", "lactulose", "racecadotril"});
        CATEGORY_RULES.put("Allergy & Cold", new String[]{"cetirizine", "levocetirizine", "montelukast", "fexofenadine", "chlorpheniramine", "bilastine"});
        CATEGORY_RULES.put("Heart & Blood Pressure", new String[]{"amlodipine", "telmisartan", "losartan", "olmesartan", "atenolol", "metoprolol", "ramipril", "enalapril", "clopidogrel", "aspirin", "cilnidipine", "ticagrelor", "prasugrel", "nebivolol", "bisoprolol", "carvedilol", "hydrochlorothiazide", "furosemide", "torsemide", "spironolactone", "ivabradine", "ranolazine", "trimetazidine", "warfarin"});
        CATEGORY_RULES.put("Cholesterol", new String[]{"atorvastatin", "rosuvastatin", "fenofibrate"});
        CATEGORY_RULES.put("Vitamins & Supplements", new String[]{"vitamin", "calcium", "folic", "iron", "cyanocobalamin", "methylcobalamin", "zinc", "cholecalciferol", "multivitamin"});
        CATEGORY_RULES.put("Respiratory", new String[]{"salbutamol", "levosalbutamol", "budesonide", "formoterol", "acebrophylline", "doxofylline", "ambroxol", "bromhexine", "guaifenesin", "dextromethorphan", "terbutaline", "theophylline"});
        CATEGORY_RULES.put("Neuro & Mental Health", new String[]{"sertraline", "escitalopram", "alprazolam", "clonazepam", "olanzapine", "levetiracetam", "sodium valproate", "gabapentin", "pregabalin", "amitriptyline", "nortriptyline", "duloxetine", "venlafaxine", "fluoxetine", "paroxetine", "lamotrigine", "carbamazepine", "oxcarbazepine", "risperidone", "quetiapine", "aripiprazole", "donepezil", "memantine"});
        CATEGORY_RULES.put("Thyroid", new String[]{"levothyroxine", "thyroxine", "carbimazole"});
        CATEGORY_RULES.put("Skin & Antifungal", new String[]{"clotrimazole", "fluconazole", "terbinafine", "ketoconazole", "mupirocin", "luliconazole", "itraconazole"});
    }

    // word found in pack label/name -> dosage form
    static final String[][] FORMS = {
            {"tablet", "Tablet"}, {"capsule", "Capsule"}, {"syrup", "Syrup"}, {"suspension", "Suspension"},
            {"injection", "Injection"}, {"cream", "Cream"}, {"ointment", "Ointment"}, {"gel", "Gel"},
            {"drop", "Drops"}, {"lotion", "Lotion"}, {"inhaler", "Inhaler"}};

    record Med(int id, String name, double price, String manufacturer, String packSize,
               String genericName, String category, String form, String imageUrl) {
    }

    // the CSV uses this one picture for every product that has no real photo
    static final String PLACEHOLDER_IMAGE_MARK = "hx2gxivwmeoxxxsc1hix";

    public static void main(String[] args) throws IOException {
        Path src = Path.of(args.length > 0 ? args[0] : "indian_medicine_data.json");
        Path dst = Path.of(args.length > 1 ? args[1] : "medicines.json");

        Map<String, String> images = args.length > 2 ? loadImages(Path.of(args[2])) : Map.of();
        boolean imagesOnly = !images.isEmpty();

        String json = Files.readString(src, StandardCharsets.UTF_8);
        List<Map<String, String>> data = parseArrayOfFlatObjects(json);
        System.out.println("Read " + data.size() + " records");

        Set<String> wanted = new HashSet<>(COMPANIES);
        Map<String, List<Med>> byCompany = new HashMap<>();

        for (Map<String, String> x : data) {
            if (!"FALSE".equals(x.get("Is_discontinued")) || !"allopathy".equals(x.get("type"))) continue;
            String company = x.get("manufacturer_name");
            if (company == null || !wanted.contains(company)) continue;

            double price;
            int id;
            try {
                price = Double.parseDouble(x.getOrDefault("price(\u20b9)", "0"));
                id = Integer.parseInt(x.get("id"));
            } catch (NumberFormatException e) {
                continue;
            }
            String name = clean(x.get("name"));
            String c1 = clean(x.get("short_composition1"));
            String c2 = clean(x.get("short_composition2"));
            if (price <= 0 || name.isEmpty() || c1.isEmpty()) continue;

            String imageUrl = images.get(name.toLowerCase());
            if (imagesOnly && imageUrl == null) continue;

            String pack = clean(x.get("pack_size_label"));
            String generic = c2.isEmpty() ? c1 : c1 + " + " + c2;
            byCompany.computeIfAbsent(company, k -> new ArrayList<>()).add(
                    new Med(id, name, Math.round(price * 100.0) / 100.0, company, pack, generic,
                            categoryFor(c1), formFor(pack, name), imageUrl));
        }

        List<Med> result = new ArrayList<>();
        for (String company : COMPANIES) {
            List<Med> rows = byCompany.getOrDefault(company, List.of());
            rows = new ArrayList<>(rows);
            rows.sort(Comparator.comparingInt(Med::id)); // low id = older, well-known brands

            // group by category (skip duplicate names)
            Set<String> seen = new HashSet<>();
            Map<String, Deque<Med>> buckets = new LinkedHashMap<>();
            for (Med m : rows) {
                if (!seen.add(m.name().toLowerCase())) continue;
                buckets.computeIfAbsent(m.category(), k -> new ArrayDeque<>()).add(m);
            }

            // one from each category in turn so every company has variety; "General" goes last
            List<String> order = new ArrayList<>(buckets.keySet());
            order.remove("General");
            order.sort((a, b) -> Integer.compare(buckets.get(b).size(), buckets.get(a).size()));
            if (buckets.containsKey("General")) order.add("General");

            int picked = 0;
            boolean added = true;
            while (picked < PER_COMPANY && added) {
                added = false;
                for (String cat : order) {
                    Deque<Med> q = buckets.get(cat);
                    if (!q.isEmpty() && picked < PER_COMPANY) {
                        result.add(q.poll());
                        picked++;
                        added = true;
                    }
                }
            }
        }

        writeJson(dst, result);
        System.out.println("Wrote " + result.size() + " medicines from " + COMPANIES.size() + " companies -> " + dst);
    }

    // ------------------------------------------------------------------ CSV (images)

    /** medicine name (lower case) -> real image url, read from the 1mg-style CSV */
    static Map<String, String> loadImages(Path csv) throws IOException {
        Map<String, String> map = new HashMap<>();
        try (BufferedReader in = Files.newBufferedReader(csv, StandardCharsets.UTF_8)) {
            List<String> header = readCsvRecord(in);
            if (header == null) return map;
            int nameCol = header.indexOf("Medicine Name");
            int imgCol = header.indexOf("Image URL");
            if (nameCol < 0 || imgCol < 0) {
                throw new IllegalStateException("CSV needs 'Medicine Name' and 'Image URL' columns, found: " + header);
            }
            List<String> row;
            while ((row = readCsvRecord(in)) != null) {
                if (row.size() <= Math.max(nameCol, imgCol)) continue;
                String name = clean(row.get(nameCol)).toLowerCase();
                String url = row.get(imgCol).trim();
                if (name.isEmpty() || !url.startsWith("http") || url.contains(PLACEHOLDER_IMAGE_MARK)) continue;
                map.putIfAbsent(name, url);
            }
        }
        System.out.println("Read " + map.size() + " medicine names with a real image from the CSV");
        return map;
    }

    /** Reads one CSV record (handles quotes, commas and line breaks inside quotes). */
    static List<String> readCsvRecord(BufferedReader in) throws IOException {
        List<String> fields = new ArrayList<>();
        StringBuilder cur = new StringBuilder();
        boolean inQuotes = false;
        boolean any = false;
        int c;
        while ((c = in.read()) != -1) {
            any = true;
            char ch = (char) c;
            if (inQuotes) {
                if (ch == '"') {
                    in.mark(1);
                    int next = in.read();
                    if (next == '"') cur.append('"');
                    else {
                        inQuotes = false;
                        if (next != -1) in.reset();
                    }
                } else cur.append(ch);
            } else if (ch == '"') {
                inQuotes = true;
            } else if (ch == ',') {
                fields.add(cur.toString());
                cur.setLength(0);
            } else if (ch == '\n') {
                fields.add(cur.toString());
                return fields;
            } else if (ch != '\r') {
                cur.append(ch);
            }
        }
        if (!any) return null;
        fields.add(cur.toString());
        return fields;
    }

    // ------------------------------------------------------------------ helpers

    static String clean(String s) {
        return s == null ? "" : s.replaceAll("\\s+", " ").trim();
    }

    static String categoryFor(String composition) {
        String c = composition.toLowerCase();
        for (Map.Entry<String, String[]> e : CATEGORY_RULES.entrySet()) {
            for (String k : e.getValue()) {
                if (c.contains(k)) return e.getKey();
            }
        }
        return "General";
    }

    static String formFor(String pack, String name) {
        String text = (pack + " " + name).toLowerCase();
        for (String[] f : FORMS) {
            if (text.contains(f[0])) return f[1];
        }
        return "Other";
    }

    static void writeJson(Path dst, List<Med> meds) throws IOException {
        StringBuilder sb = new StringBuilder("{\n \"companies\": [\n");
        for (int i = 0; i < COMPANIES.size(); i++) {
            sb.append("  ").append(q(COMPANIES.get(i))).append(i < COMPANIES.size() - 1 ? ",\n" : "\n");
        }
        sb.append(" ],\n \"medicines\": [\n");
        for (int i = 0; i < meds.size(); i++) {
            Med m = meds.get(i);
            sb.append("  {\"name\": ").append(q(m.name()))
                    .append(", \"price\": ").append(m.price())
                    .append(", \"manufacturer\": ").append(q(m.manufacturer()))
                    .append(", \"packSize\": ").append(q(m.packSize()))
                    .append(", \"genericName\": ").append(q(m.genericName()))
                    .append(", \"category\": ").append(q(m.category()))
                    .append(", \"form\": ").append(q(m.form()))
                    .append(m.imageUrl() != null ? ", \"imageUrl\": " + q(m.imageUrl()) : "")
                    .append("}").append(i < meds.size() - 1 ? ",\n" : "\n");
        }
        sb.append(" ]\n}\n");
        if (dst.getParent() != null) Files.createDirectories(dst.getParent());
        Files.writeString(dst, sb.toString(), StandardCharsets.UTF_8);
    }

    /** JSON string literal with escaping. */
    static String q(String s) {
        StringBuilder sb = new StringBuilder("\"");
        for (char c : s.toCharArray()) {
            switch (c) {
                case '"' -> sb.append("\\\"");
                case '\\' -> sb.append("\\\\");
                case '\n' -> sb.append("\\n");
                case '\r' -> sb.append("\\r");
                case '\t' -> sb.append("\\t");
                default -> {
                    if (c < 0x20) sb.append(String.format("\\u%04x", (int) c));
                    else sb.append(c);
                }
            }
        }
        return sb.append('"').toString();
    }

    /**
     * Tiny parser for exactly this dataset's shape: [ {"key": "string value", ...}, ... ]
     * (flat objects, string values only). Handles escapes like \" \\ \/ \n and \\uXXXX.
     */
    static List<Map<String, String>> parseArrayOfFlatObjects(String s) {
        List<Map<String, String>> list = new ArrayList<>();
        int[] pos = {0};
        skipWs(s, pos);
        expect(s, pos, '[');
        skipWs(s, pos);
        if (s.charAt(pos[0]) == ']') return list;
        while (true) {
            skipWs(s, pos);
            expect(s, pos, '{');
            Map<String, String> obj = new HashMap<>();
            skipWs(s, pos);
            if (s.charAt(pos[0]) != '}') {
                while (true) {
                    skipWs(s, pos);
                    String key = readString(s, pos);
                    skipWs(s, pos);
                    expect(s, pos, ':');
                    skipWs(s, pos);
                    obj.put(key, readValue(s, pos));
                    skipWs(s, pos);
                    if (s.charAt(pos[0]) == ',') {
                        pos[0]++;
                    } else break;
                }
            }
            skipWs(s, pos);
            expect(s, pos, '}');
            list.add(obj);
            skipWs(s, pos);
            if (s.charAt(pos[0]) == ',') {
                pos[0]++;
            } else break;
        }
        skipWs(s, pos);
        expect(s, pos, ']');
        return list;
    }

    static String readValue(String s, int[] pos) {
        if (s.charAt(pos[0]) == '"') return readString(s, pos);
        int start = pos[0]; // number, true/false/null -> keep as text
        while (pos[0] < s.length() && ",}] \n\r\t".indexOf(s.charAt(pos[0])) < 0) pos[0]++;
        String v = s.substring(start, pos[0]);
        return v.equals("null") ? null : v;
    }

    static String readString(String s, int[] pos) {
        expect(s, pos, '"');
        StringBuilder sb = new StringBuilder();
        while (true) {
            char c = s.charAt(pos[0]++);
            if (c == '"') break;
            if (c == '\\') {
                char n = s.charAt(pos[0]++);
                switch (n) {
                    case 'n' -> sb.append('\n');
                    case 't' -> sb.append('\t');
                    case 'r' -> sb.append('\r');
                    case 'b' -> sb.append('\b');
                    case 'f' -> sb.append('\f');
                    case 'u' -> {
                        sb.append((char) Integer.parseInt(s.substring(pos[0], pos[0] + 4), 16));
                        pos[0] += 4;
                    }
                    default -> sb.append(n); // \" \\ \/
                }
            } else sb.append(c);
        }
        return sb.toString();
    }

    static void skipWs(String s, int[] pos) {
        while (pos[0] < s.length() && Character.isWhitespace(s.charAt(pos[0]))) pos[0]++;
    }

    static void expect(String s, int[] pos, char c) {
        if (s.charAt(pos[0]) != c)
            throw new IllegalStateException("Expected '" + c + "' at position " + pos[0] + " but found '" + s.charAt(pos[0]) + "'");
        pos[0]++;
    }
}