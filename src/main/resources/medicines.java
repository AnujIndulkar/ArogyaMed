package com.arogyamed.util;

import com.arogyamed.model.Medicine;
import com.arogyamed.repository.MedicineRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class MedicineDataSeeder implements CommandLineRunner {

    @Autowired
    private MedicineRepository medicineRepository;

    @Override
    public void run(String... args) throws Exception {
        // Prevent re-seeding if data already exists
        if (medicineRepository.count() > 0) {
            System.out.println("Medicines database already populated. Skipping seed initialization.");
            return;
        }

        System.out.println("Starting automated medicine data migration from JSON dataset...");

        ObjectMapper mapper = new ObjectMapper();
        // Load file from src/main/resources/medicines.java
        InputStream inputStream = new ClassPathResource("medicines.java").getInputStream();
        JsonNode rootNode = mapper.readTree(inputStream);

        List<Medicine> medicineBatch = new ArrayList<>();
        Random random = new Random();

        if (rootNode.isArray()) {
            for (JsonNode node : rootNode) {
                Medicine medicine = new Medicine();

                // 1. Exact Mappings from Open Source JSON -> Your JPA Entity fields
                medicine.setMedicineName(node.get("name").asText());
                medicine.setPrice(Double.parseDouble(node.get("price(₹)").asText()));
                medicine.setCategory(node.get("type").asText()); // e.g., "allopathy"
                medicine.setGenericName(node.get("short_composition1").asText()); // e.g., "Amoxycillin (500mg)"

                // Combining fields into your description
                String desc = "Pack Size: " + node.get("pack_size_label").asText() +
                        ". Secondary Composition: " + node.get("short_composition2").asText();
                medicine.setDescription(desc);

                // 2. Programmatic Fallback Mappings for fields missing in JSON
                medicine.setBatchNumber("BATCH-" + (10000 + random.nextInt(90000)));
                medicine.setStockQuantity(50 + random.nextInt(200)); // Dynamic real-world stock simulation
                medicine.setImageUrl("https://arogya-med-cdn.com");

                // Realistic Date Calculations
                LocalDate today = LocalDate.now();
                medicine.setManufacturingDate(today.minusMonths(random.nextInt(6) + 1));
                medicine.setExpiryDate(today.plusYears(random.nextInt(2) + 1));

                // Note: Company association can remain null initially or can be linked to a default ID
                medicine.setCompany(null);

                medicineBatch.add(medicine);
            }
        }

        // Efficient Bulk database save operation
        medicineRepository.saveAll(medicineBatch);
        System.out.println("Data seeding complete! Successfully loaded " + medicineBatch.size() + " records into 'medicines' table.");
    }
}
