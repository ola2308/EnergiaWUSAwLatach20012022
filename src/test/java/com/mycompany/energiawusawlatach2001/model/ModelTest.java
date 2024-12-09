package com.mycompany.energiawusawlatach2001.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.*;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive parameterized unit tests for the Model class.
 * Tests include sample data initialized in the Model class constructor.
 */
public class ModelTest {
    private Model model;
    
    @BeforeEach
    void setUp() {
        model = new Model();
    }
    
    /**
     * Tests the addition of valid energy data.
     * Test parameters include various combinations of valid values.
     */
    @ParameterizedTest(name = "Adding data: year={0}, month={1}, amount={2}")
    @MethodSource("provideValidEnergyData")
    void testAddEnergyData_ValidData(int year, int month, double amount) throws InvalidEnergyDataException {
        int initialSize = model.getEnergyDataList().size();
        EnergyData data = new EnergyData(
            year, 
            month, 
            "CA", 
            EnergySource.SOLAR, 
            EnergyProducer.RENEWABLE_ENERGY_COMPANIES, 
            amount
        );
        
        model.addEnergyData(data);
        assertEquals(initialSize + 1, model.getEnergyDataList().size());
        assertTrue(model.getEnergyDataList().contains(data));
    }
    
    /**
     * Provides test data for valid energy data scenarios.
     * @return Stream of arguments containing valid year, month, and amount combinations
     */
    static Stream<Arguments> provideValidEnergyData() {
        return Stream.of(
            // Standard cases
            Arguments.of(2010, 6, 5000.0),
            // Boundary cases for years
            Arguments.of(2001, 6, 1000.0),
            Arguments.of(2022, 6, 1000.0),
            // Boundary cases for months
            Arguments.of(2010, 1, 1000.0),
            Arguments.of(2010, 12, 1000.0)
        );
    }
    
    /**
     * Tests adding data with invalid years.
     * Tests values outside the allowed year range.
     */
    @ParameterizedTest(name = "Invalid year: {0}")
    @ValueSource(ints = {2000, 2023, 1999, 2024})
    void testAddEnergyData_InvalidYear(int year) {
        assertThrows(InvalidEnergyDataException.class, () -> {
            model.addEnergyData(new EnergyData(
                year, 1, "CA", EnergySource.SOLAR,
                EnergyProducer.RENEWABLE_ENERGY_COMPANIES, 1000.0
            ));
        });
    }
    
    /**
     * Tests adding data with invalid months.
     * Tests values outside the allowed month range.
     */
    @ParameterizedTest(name = "Invalid month: {0}")
    @ValueSource(ints = {0, 13, -1, 24})
    void testAddEnergyData_InvalidMonth(int month) {
        assertThrows(InvalidEnergyDataException.class, () -> {
            model.addEnergyData(new EnergyData(
                2010, month, "CA", EnergySource.SOLAR,
                EnergyProducer.RENEWABLE_ENERGY_COMPANIES, 1000.0
            ));
        });
    }
    
    /**
     * Tests adding data with invalid states.
     * Tests various invalid values for the state field.
     */
    @ParameterizedTest(name = "Invalid state: {0}")
    @MethodSource("provideInvalidStates")
    void testAddEnergyData_InvalidState(String state) {
        assertThrows(InvalidEnergyDataException.class, () -> {
            model.addEnergyData(new EnergyData(
                2001, 1, state, EnergySource.SOLAR,
                EnergyProducer.RENEWABLE_ENERGY_COMPANIES, 1000.0
            ));
        });
    }
    
    /**
     * Provides test data for invalid state scenarios.
     * @return Stream of invalid state values
     */
    static Stream<String> provideInvalidStates() {
        return Stream.of(null, "", "   ", "\t", "\n");
    }
    
    /**
     * Tests retrieving unique energy sources.
     * Tests various combinations of energy sources.
     */
    @ParameterizedTest(name = "Testing energy sources: set {index}")
    @MethodSource("provideEnergySourceData")
    void testGetEnergySources(List<EnergyData> additionalData, Set<EnergySource> expectedSources) 
            throws InvalidEnergyDataException {
        for (EnergyData data : additionalData) {
            model.addEnergyData(data);
        }
        
        Set<EnergySource> actualSources = model.getEnergySources();
        assertEquals(expectedSources.size(), actualSources.size());
        assertTrue(actualSources.containsAll(expectedSources));
    }
    
    /**
     * Provides test data for energy source combinations.
     * @return Stream of arguments containing energy data lists and expected source sets
     */
    static Stream<Arguments> provideEnergySourceData() {
        Set<EnergySource> allSources = new HashSet<>(Arrays.asList(
            EnergySource.COAL, EnergySource.NATURAL_GAS, EnergySource.WIND,
            EnergySource.HYDROELECTRIC, EnergySource.SOLAR
        ));
        
        return Stream.of(
            Arguments.of(new ArrayList<EnergyData>(), allSources),
            Arguments.of(
                List.of(new EnergyData(2010, 1, "NY", EnergySource.SOLAR, 
                    EnergyProducer.RENEWABLE_ENERGY_COMPANIES, 1000.0)),
                allSources
            )
        );
    }
    
    /**
     * Tests sorting producers by total energy production.
     * Tests various scenarios of production data.
     */
    @ParameterizedTest(name = "Testing sorting: scenario {index}")
    @MethodSource("provideSortingTestData")
    void testSortProducersByTotalEnergy(List<EnergyData> additionalData, 
            List<Map.Entry<EnergyProducer, Double>> expectedOrder) throws InvalidEnergyDataException {
        for (EnergyData data : additionalData) {
            model.addEnergyData(data);
        }
        
        List<Map.Entry<EnergyProducer, Double>> sortedProducers = model.sortProducersByTotalEnergy();
        assertEquals(expectedOrder.size(), sortedProducers.size());
        
        for (int i = 0; i < expectedOrder.size(); i++) {
            assertEquals(expectedOrder.get(i).getKey(), sortedProducers.get(i).getKey());
            assertEquals(expectedOrder.get(i).getValue(), sortedProducers.get(i).getValue(), 0.01);
        }
    }
    
    /**
     * Provides test data for sorting scenarios.
     * @return Stream of arguments containing energy data lists and expected sorted orders
     */
    static Stream<Arguments> provideSortingTestData() {
        Map<EnergyProducer, Double> baseTotals = new HashMap<>();
        baseTotals.put(EnergyProducer.INDEPENDENT_POWER_PRODUCERS, 138500.0);
        baseTotals.put(EnergyProducer.ELECTRIC_UTILITIES, 46903.0);
        baseTotals.put(EnergyProducer.RENEWABLE_ENERGY_COMPANIES, 3000.0);
        baseTotals.put(EnergyProducer.COMBINED_HEAT_AND_POWER, 90.0);

        Map<EnergyProducer, Double> extendedTotals = new HashMap<>(baseTotals);
        extendedTotals.put(EnergyProducer.RENEWABLE_ENERGY_COMPANIES, 8000.0);
        
        List<Map.Entry<EnergyProducer, Double>> baseExpectedOrder = baseTotals.entrySet()
            .stream()
            .sorted(Map.Entry.<EnergyProducer, Double>comparingByValue().reversed())
            .toList();
            
        List<Map.Entry<EnergyProducer, Double>> extendedExpectedOrder = extendedTotals.entrySet()
            .stream()
            .sorted(Map.Entry.<EnergyProducer, Double>comparingByValue().reversed())
            .toList();
            
        return Stream.of(
            Arguments.of(new ArrayList<EnergyData>(), baseExpectedOrder),
            Arguments.of(
                List.of(new EnergyData(2010, 1, "NY", EnergySource.SOLAR, 
                    EnergyProducer.RENEWABLE_ENERGY_COMPANIES, 5000.0)),
                extendedExpectedOrder
            )
        );
    }
    
    /**
     * Tests calculating minimum and maximum energy for states.
     * Tests various scenarios of energy data.
     */
    @ParameterizedTest(name = "Testing min/max: scenario {index}")
    @MethodSource("provideMinMaxTestData")
    void testCalculateMinMaxEnergy(List<EnergyData> additionalData,
            Map<String, Double> expectedMin, Map<String, Double> expectedMax) throws InvalidEnergyDataException {
        for (EnergyData data : additionalData) {
            model.addEnergyData(data);
        }
        
        assertEquals(expectedMin, model.calculateMinEnergy());
        assertEquals(expectedMax, model.calculateMaxEnergy());
    }
    
    /**
     * Provides test data for minimum and maximum energy calculations.
     * @return Stream of arguments containing energy data and expected min/max values
     */
    static Stream<Arguments> provideMinMaxTestData() {
        Map<String, Double> baseMin = new HashMap<>();
        baseMin.put("AK", 90.0);
        baseMin.put("CA", 3000.0);
        
        Map<String, Double> baseMax = new HashMap<>();
        baseMax.put("AK", 46903.0);
        baseMax.put("CA", 102000.0);
        
        Map<String, Double> extendedMin = new HashMap<>(baseMin);
        extendedMin.put("NY", 5000.0);
        
        Map<String, Double> extendedMax = new HashMap<>(baseMax);
        extendedMax.put("NY", 5000.0);
        
        return Stream.of(
            Arguments.of(new ArrayList<EnergyData>(), baseMin, baseMax),
            Arguments.of(
                List.of(new EnergyData(2010, 1, "NY", EnergySource.SOLAR, 
                    EnergyProducer.RENEWABLE_ENERGY_COMPANIES, 5000.0)),
                extendedMin, extendedMax
            )
        );
    }
    
    /**
     * Tests finding the most frequently used energy source.
     * Tests various scenarios of source usage.
     */
    @ParameterizedTest(name = "Testing most used source: scenario {index}")
    @MethodSource("provideMostUsedSourceData")
    void testGetMostUsedEnergySource(List<EnergyData> additionalData, 
            Set<EnergySource> expectedPossibleSources) throws InvalidEnergyDataException {
        for (EnergyData data : additionalData) {
            model.addEnergyData(data);
        }
        
        EnergySource mostUsed = model.getMostUsedEnergySource();
        assertTrue(expectedPossibleSources.contains(mostUsed));
    }
    
    /**
     * Provides test data for most used energy source scenarios.
     * @return Stream of arguments containing energy data and expected source sets
     */
    static Stream<Arguments> provideMostUsedSourceData() {
        return Stream.of(
            Arguments.of(
                new ArrayList<EnergyData>(),
                new HashSet<>(Arrays.asList(EnergySource.values()))
            ),
            Arguments.of(
                List.of(new EnergyData(2010, 1, "NY", EnergySource.SOLAR, 
                    EnergyProducer.RENEWABLE_ENERGY_COMPANIES, 1000.0)),
                Set.of(EnergySource.SOLAR)
            )
        );
    }
    
    /**
     * Tests retrieving energy by state for specific months.
     * Tests various scenarios of monthly data.
     */
    @ParameterizedTest(name = "Testing monthly energy: month {0}")
    @MethodSource("provideMonthlyEnergyData")
    void testGetEnergyByStateForMonth(int month, Map<String, Double> expectedResults) {
        Map<String, Double> actualResults = model.getEnergyByStateForMonth(month);
        assertEquals(expectedResults.size(), actualResults.size());
        expectedResults.forEach((state, expected) -> 
            assertEquals(expected, actualResults.get(state), 0.01));
    }
    
    /**
     * Provides test data for monthly energy calculations.
     * @return Stream of arguments containing months and expected energy values by state
     */
    static Stream<Arguments> provideMonthlyEnergyData() {
        Map<String, Double> month1Results = new HashMap<>();
        month1Results.put("AK", 46993.0);
        month1Results.put("CA", 105000.0);
        
        Map<String, Double> month2Results = new HashMap<>();
        month2Results.put("AK", 36500.0);
        
        Map<String, Double> emptyResults = new HashMap<>();
        
        return Stream.of(
            Arguments.of(1, month1Results),
            Arguments.of(2, month2Results),
            Arguments.of(3, emptyResults)
        );
    }
}