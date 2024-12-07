package com.mycompany.energiawusawlatach2001.model;

/**
 * Record representing a single energy data entry.
 * Uses enums for source and producer to ensure data validity.
 *
 * @author Aleksandra Nizio
 * @version 1.0
 */
public record EnergyData(
    int year,
    int month,
    String state,
    EnergySource source,
    EnergyProducer producer,
    double amount
) {}