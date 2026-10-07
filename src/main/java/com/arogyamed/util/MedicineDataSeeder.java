package com.arogyamed.util;

import com.arogyamed.model.Company;
import com.arogyamed.model.Medicine;
import com.arogyamed.model.Role;
import com.arogyamed.model.User;
import com.arogyamed.repository.CompanyRepository;
import com.arogyamed.repository.MedicineRepository;
import com.arogyamed.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Seeds demo companies + medicines on first start (only when the medicines table is empty).
 * Data: junioralive/Indian-Medicine-Dataset (MIT), trimmed by tools/PrepareMedicines.java
 */
@Component
@RequiredArgsConstructor
public class MedicineDataSeeder implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(MedicineDataSeeder.class);
    private static final String DEMO_PASSWORD = "Demo@123";

    private final MedicineRepository medicineRepository;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.seed.reset:false}")
    private boolean resetMedicines;

    @Override
    public void run(String... args) throws Exception {
        if (resetMedicines) {
            medicineRepository.deleteAll();
            log.info("Old medicines deleted (app.seed.reset=true).");
        }
        if (medicineRepository.count() > 0) {
            log.info("Medicines already present. Skipping seed.");
            return;
        }

        ClassPathResource resource = new ClassPathResource("medicines.json");
        if (!resource.exists()) {
            log.warn("medicines.json not found in src/main/resources. Skipping seed.");
            return;
        }

        JsonNode root;
        try (InputStream in = resource.getInputStream()) {
            root = new ObjectMapper().readTree(in);
        }

        // 1) demo companies (each with a verified COMPANY user)
        Map<String, Company> companies = new HashMap<>();
        int index = 0;
        for (JsonNode nameNode : root.get("companies")) {
            String name = nameNode.asText();
            companies.put(name, getOrCreateCompany(name, index++));
        }

        // 2) medicines
        Random random = new Random();
        LocalDate today = LocalDate.now();
        List<Medicine> batch = new ArrayList<>();

        for (JsonNode m : root.get("medicines")) {
            Company company = companies.get(m.get("manufacturer").asText());
            if (company == null) continue;

            String form = m.get("form").asText();
            String pack = m.get("packSize").asText();

            Medicine med = new Medicine();
            med.setMedicineName(truncate(m.get("name").asText(), 255));
            med.setCategory(m.get("category").asText());
            med.setGenericName(truncate(m.get("genericName").asText(), 255));
            med.setPackSize(truncate(pack, 255));
            med.setDescription(truncate(form + " - " + pack + ". Manufactured by "
                    + company.getCompanyName() + ".", 255));
            med.setPrice(m.get("price").asDouble());
            med.setCompany(company);

            // not in the dataset -> generated demo values
            med.setBatchNumber("BATCH-" + (10000 + random.nextInt(90000)));
            med.setStockQuantity(50 + random.nextInt(200));
            med.setManufacturingDate(today.minusMonths(1 + random.nextInt(6)));
            med.setExpiryDate(today.plusYears(1 + random.nextInt(2)));
            med.setImageUrl(m.hasNonNull("imageUrl") ? m.get("imageUrl").asText() : null);

            batch.add(med);
        }

        medicineRepository.saveAll(batch);
        log.info("Seeded {} companies and {} medicines.", companies.size(), batch.size());
        log.info("Demo company logins: <company-slug>@arogyamed-demo.com / {}  (e.g. cipla-ltd@arogyamed-demo.com)",
                DEMO_PASSWORD);
    }

    private Company getOrCreateCompany(String name, int index) {
        String email = slug(name) + "@arogyamed-demo.com";

        User user = userRepository.findByEmail(email).orElseGet(() -> {
            User u = new User();
            u.setFullName(name);
            u.setEmail(email);
            u.setPhoneNumber(String.format("90000%05d", index + 1));
            u.setPassword(passwordEncoder.encode(DEMO_PASSWORD));
            u.setAddress("India");
            u.setRole(Role.COMPANY);
            u.setVerified(true);
            return userRepository.save(u);
        });

        return companyRepository.findByUserId(user.getId()).orElseGet(() -> {
            Company c = new Company();
            c.setUser(user);
            c.setCompanyName(name);
            c.setLicenseNumber("DEMO-LIC-" + (1000 + index));
            c.setGstNumber("DEMO-GST-" + (1000 + index));
            c.setCompanyAddress("India");
            c.setContactPerson("Demo Contact");
            return companyRepository.save(c);
        });
    }

    private String slug(String s) {
        return s.toLowerCase().replaceAll("[^a-z0-9]+", "-").replaceAll("(^-|-$)", "");
    }

    private String truncate(String s, int max) {
        if (s == null) return null;
        return s.length() <= max ? s : s.substring(0, max);
    }
}
