package com.mycompany.energiawusawlatach2001.model;

/**
 * Exception thrown when invalid energy data is provided.
 */
public class InvalidEnergyDataException extends Exception {

    /**
     *
     * @param message
     */
    public InvalidEnergyDataException(String message) {
        super(message);
    }
}
