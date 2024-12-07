package com.mycompany.energiawusawlatach2001.model;

/**
 * Enum representing different types of energy sources.
 * Contains predefined values for common energy sources with their display names.
 *
 * @author Aleksandra Nizio
 * @version 1.0
 */
public enum EnergySource {

    /**
     *
     */
    COAL("Coal"),

    /**
     *
     */
    NATURAL_GAS("Natural Gas"),

    /**
     *
     */
    WIND("Wind"),

    /**
     *
     */
    SOLAR("Solar"),

    /**
     *
     */
    HYDROELECTRIC("Hydroelectric");

    private final String displayName;

    /**
     * Constructor for EnergySource enum.
     *
     * @param displayName the human-readable name of the energy source
     */
    EnergySource(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Gets the display name of the energy source.
     *
     * @return the formatted display name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Converts a string to corresponding EnergySource enum value.
     *
     * @param text the string to convert
     * @return the matching EnergySource enum value
     * @throws InvalidEnergyDataException if no matching energy source is found
     */
    public static EnergySource fromString(String text) throws InvalidEnergyDataException {
        for (EnergySource source : EnergySource.values()) {
            if (source.displayName.equalsIgnoreCase(text)) {
                return source;
            }
        }
        throw new InvalidEnergyDataException("Invalid energy source: " + text);
    }
}