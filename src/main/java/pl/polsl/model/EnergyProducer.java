package com.mycompany.energiawusawlatach2001.model;

/**
 * Enum representing different types of energy producers.
 * Contains predefined values for common energy producers with their display names.
 *
 * @author Aleksandra Nizio
 * @version 1.0
 */
public enum EnergyProducer {

    /**
     *
     */
    ELECTRIC_UTILITIES("Electric Utilities"),

    /**
     *
     */
    INDEPENDENT_POWER_PRODUCERS("Independent Power Producers"),

    /**
     *
     */
    COMBINED_HEAT_AND_POWER("Combined Heat and Power"),

    /**
     *
     */
    NUCLEAR_POWER_PLANTS("Nuclear Power Plants"),

    /**
     *
     */
    RENEWABLE_ENERGY_COMPANIES("Renewable Energy Companies"),

    /**
     *
     */
    HYDROELECTRIC_FACILITIES("Hydroelectric Facilities"),

    /**
     *
     */
    FOSSIL_FUEL_PLANTS("Fossil Fuel Plants"),

    /**
     *
     */
    GEOTHERMAL_PLANTS("Geothermal Plants");

    private final String displayName;

    /**
     * Constructor for EnergyProducer enum.
     *
     * @param displayName the human-readable name of the energy producer
     */
    EnergyProducer(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Gets the display name of the energy producer.
     *
     * @return the formatted display name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Converts a string to corresponding EnergyProducer enum value.
     *
     * @param text the string to convert
     * @return the matching EnergyProducer enum value
     * @throws InvalidEnergyDataException if no matching energy producer is found
     */
    public static EnergyProducer fromString(String text) throws InvalidEnergyDataException {
        for (EnergyProducer producer : EnergyProducer.values()) {
            if (producer.displayName.equalsIgnoreCase(text)) {
                return producer;
            }
        }
        throw new InvalidEnergyDataException("Invalid energy producer: " + text);
    }
}