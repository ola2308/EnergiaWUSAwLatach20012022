package com.mycompany.energiawusawlatach2001.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The {@code Model} class serves as the application's data layer, managing energy data
 * and providing business logic for operations like sorting, filtering, and aggregations.
 *
 * This class uses the {@link EnergyData} record to represent individual energy entries.
 * 
 * Note: Lombok annotations (@NoArgsConstructor, @Getter) are used for generating constructors
 * and getter methods but may not function if Lombok is not properly configured in the project.
 * 
 * @author Aleksandra Nizio
 * @version 1.0
 */
@NoArgsConstructor
@Getter
public class Model {

    /**
     * List of energy data entries. Preloaded with sample data for demonstration purposes.
     */
    private final List<EnergyData> energyDataList = new ArrayList<>(List.of(
        new EnergyData(2001, 1, "AK", EnergySource.COAL, EnergyProducer.ELECTRIC_UTILITIES, 46903),
        new EnergyData(2001, 2, "AK", EnergySource.NATURAL_GAS, EnergyProducer.INDEPENDENT_POWER_PRODUCERS, 36500),
        new EnergyData(2002, 1, "AK", EnergySource.WIND, EnergyProducer.COMBINED_HEAT_AND_POWER, 90),
        new EnergyData(2001, 1, "CA", EnergySource.HYDROELECTRIC, EnergyProducer.INDEPENDENT_POWER_PRODUCERS, 102000),
        new EnergyData(2002, 1, "CA", EnergySource.SOLAR, EnergyProducer.RENEWABLE_ENERGY_COMPANIES, 3000)
    ));

    /**
     * Adds a new {@link EnergyData} entry to the list.
     * 
     * @param data the {@code EnergyData} entry to add
     * @throws InvalidEnergyDataException if the energy data is invalid
     */
    public void addEnergyData(EnergyData data) throws InvalidEnergyDataException {
        if (data == null) {
            throw new InvalidEnergyDataException("Energy data cannot be null");
        }
        if (data.amount() <= 0) {
            throw new InvalidEnergyDataException("Energy amount must be greater than 0");
        }
        if (data.month() < 1 || data.month() > 12) {
            throw new InvalidEnergyDataException("Month must be between 1 and 12");
        }
        if (data.year() < 2001 || data.year() > 2022) {
            throw new InvalidEnergyDataException("Year must be between 2001 and 2022");
        }
        if (data.state() == null || data.state().trim().isEmpty()) {
            throw new InvalidEnergyDataException("State cannot be empty");
        }
        energyDataList.add(data);
    }

    /**
     * Retrieves a set of unique energy sources.
     * 
     * @return a set of unique energy source types
     */
    public Set<EnergySource> getEnergySources() {
        return energyDataList.stream()
            .map(EnergyData::source)
            .collect(Collectors.toSet());
    }

    /**
     * Sorts energy producers by their total energy production in descending order.
     * 
     * @return a list of producers and their corresponding total energy production
     */
    public List<Map.Entry<EnergyProducer, Double>> sortProducersByTotalEnergy() {
        Map<EnergyProducer, Double> producerEnergyMap = energyDataList.stream()
            .collect(Collectors.groupingBy(EnergyData::producer, Collectors.summingDouble(EnergyData::amount)));
        return producerEnergyMap.entrySet()
            .stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .collect(Collectors.toList());
    }

    /**
     * Calculates the minimum energy production for each state.
     * 
     * @return a map of states and their minimum energy production
     */
    public Map<String, Double> calculateMinEnergy() {
        return energyDataList.stream()
            .collect(Collectors.groupingBy(EnergyData::state, Collectors.minBy(Comparator.comparingDouble(EnergyData::amount))))
            .entrySet()
            .stream()
            .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().get().amount()));
    }

    /**
     * Calculates the maximum energy production for each state.
     * 
     * @return a map of states and their maximum energy production
     */
    public Map<String, Double> calculateMaxEnergy() {
        return energyDataList.stream()
            .collect(Collectors.groupingBy(EnergyData::state, Collectors.maxBy(Comparator.comparingDouble(EnergyData::amount))))
            .entrySet()
            .stream()
            .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().get().amount()));
    }

    /**
     * Determines the most frequently used energy source.
     * 
     * @return the most used energy source
     */
    public EnergySource getMostUsedEnergySource() {
        return energyDataList.stream()
            .collect(Collectors.groupingBy(EnergyData::source, Collectors.counting()))
            .entrySet()
            .stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse(null);
    }

    /**
     * Retrieves the total energy production by state for a specific month.
     * 
     * @param month the month (1-12) to filter the data
     * @return a map of states and their total energy production for the specified month
     */
    public Map<String, Double> getEnergyByStateForMonth(int month) {
        return energyDataList.stream()
            .filter(data -> data.month() == month)
            .collect(Collectors.groupingBy(EnergyData::state, Collectors.summingDouble(EnergyData::amount)));
    }
}