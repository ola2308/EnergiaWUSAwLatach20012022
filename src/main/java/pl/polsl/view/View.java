package com.mycompany.energiawusawlatach2001.view;

import com.mycompany.energiawusawlatach2001.model.EnergySource;
import com.mycompany.energiawusawlatach2001.model.EnergyProducer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * The View class represents the user interface of the application.
 * It contains various buttons and text fields that the user interacts with
 * to add, display, sort, and calculate energy data.
 * This class follows the MVC pattern and represents the presentation layer.
 * 
 * @author Aleksandra Nizio
 * @version 1.0
 */
public class View extends JFrame {
    /**
     * Buttons for user actions.
     */
    private final JButton displaySourcesButton;
    private final JButton sortButton;
    private final JButton minMaxButton;
    private final JButton addButton;
    private final JButton mostUsedSourceButton;
    private final JButton energyByMonthButton;

    /**
     * Text fields and combo boxes for user input.
     */
    private final JTextField stateField;
    private final JComboBox<EnergySource> sourceComboBox;
    private final JTextField amountField;
    private final JTextField monthField;
    private final JTextField yearField;
    private final JTextField monthInputField;
    private final JComboBox<EnergyProducer> producerComboBox;

    /**
     * Table for displaying energy data.
     */
    private final JTable dataTable;
    private final DefaultTableModel tableModel;

    /**
     * Constructor initializes the GUI components, such as buttons, text fields, 
     * and table for user interaction.
     * It sets up the layout of the user interface and configures the frame settings.
     * Accessibility features are added.
     */
    public View() {
        setTitle("Energy Data Management");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set main layout
        setLayout(new BorderLayout());

        // Input panel on the left
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Initialize text fields and combo boxes with accessible descriptions
        stateField = new JTextField(10);
        stateField.getAccessibleContext().setAccessibleDescription("Enter the state code here");

        sourceComboBox = new JComboBox<>(EnergySource.values());
        sourceComboBox.getAccessibleContext().setAccessibleDescription("Select the energy source");
        sourceComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, 
                    int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof EnergySource) {
                    setText(((EnergySource) value).getDisplayName());
                }
                return this;
            }
        });

        amountField = new JTextField(10);
        amountField.getAccessibleContext().setAccessibleDescription("Enter the amount of energy in MWh here");

        monthField = new JTextField(10);
        monthField.getAccessibleContext().setAccessibleDescription("Enter the month here (1-12)");

        yearField = new JTextField(10);
        yearField.getAccessibleContext().setAccessibleDescription("Enter the year here (2001-2022)");

        producerComboBox = new JComboBox<>(EnergyProducer.values());
        producerComboBox.getAccessibleContext().setAccessibleDescription("Select the energy producer");
        producerComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, 
                    int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof EnergyProducer) {
                    setText(((EnergyProducer) value).getDisplayName());
                }
                return this;
            }
        });
        // Labels with mnemonics
        JLabel stateLabel = new JLabel("State:");
        stateLabel.setDisplayedMnemonic(KeyEvent.VK_T);
        stateLabel.setLabelFor(stateField);

        JLabel sourceLabel = new JLabel("Source:");
        sourceLabel.setDisplayedMnemonic(KeyEvent.VK_U);
        sourceLabel.setLabelFor(sourceComboBox);

        JLabel amountLabel = new JLabel("Amount [MWh]:");
        amountLabel.setDisplayedMnemonic(KeyEvent.VK_I);
        amountLabel.setLabelFor(amountField);

        JLabel monthLabel = new JLabel("Month:");
        monthLabel.setDisplayedMnemonic(KeyEvent.VK_M);
        monthLabel.setLabelFor(monthField);

        JLabel yearLabel = new JLabel("Year:");
        yearLabel.setDisplayedMnemonic(KeyEvent.VK_R);
        yearLabel.setLabelFor(yearField);

        JLabel producerLabel = new JLabel("Producer:");
        producerLabel.setDisplayedMnemonic(KeyEvent.VK_P);
        producerLabel.setLabelFor(producerComboBox);

        // Initialize buttons with tooltips and mnemonics
        addButton = new JButton("Add Data");
        addButton.setToolTipText("Click to add new energy data");
        addButton.setMnemonic(KeyEvent.VK_D);
        addButton.getAccessibleContext().setAccessibleDescription("Adds new energy data");

        mostUsedSourceButton = new JButton("Most Used Source");
        mostUsedSourceButton.setToolTipText("Click to display the most used energy source");
        mostUsedSourceButton.setMnemonic(KeyEvent.VK_N);
        mostUsedSourceButton.getAccessibleContext().setAccessibleDescription("Displays the most used energy source");

        energyByMonthButton = new JButton("Energy by Month");
        energyByMonthButton.setToolTipText("Click to display energy produced in a specific month for each state");
        energyByMonthButton.setMnemonic(KeyEvent.VK_E);
        energyByMonthButton.getAccessibleContext().setAccessibleDescription("Displays energy by state for a given month");

        monthInputField = new JTextField(10);
        monthInputField.getAccessibleContext().setAccessibleDescription("Enter the month to search (1-12)");

        // Set components on the input panel
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(stateLabel, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(sourceLabel, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        inputPanel.add(amountLabel, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        inputPanel.add(monthLabel, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        inputPanel.add(yearLabel, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        inputPanel.add(producerLabel, gbc);

        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 1; gbc.gridy = 0;
        inputPanel.add(stateField, gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        inputPanel.add(sourceComboBox, gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        inputPanel.add(amountField, gbc);

        gbc.gridx = 1; gbc.gridy = 3;
        inputPanel.add(monthField, gbc);

        gbc.gridx = 1; gbc.gridy = 4;
        inputPanel.add(yearField, gbc);

        gbc.gridx = 1; gbc.gridy = 5;
        inputPanel.add(producerComboBox, gbc);

        gbc.gridx = 1; gbc.gridy = 6;
        inputPanel.add(addButton, gbc);

        // Ensure the input panel doesn't get too small
        inputPanel.setMinimumSize(new Dimension(300, 300));
        inputPanel.setPreferredSize(new Dimension(350, 350));

        // Buttons panel at the top
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        displaySourcesButton = new JButton("Show Sources");
        displaySourcesButton.setToolTipText("Click to display the energy sources used");
        displaySourcesButton.setMnemonic(KeyEvent.VK_S);
        displaySourcesButton.getAccessibleContext().setAccessibleDescription("Displays the energy sources used");

        sortButton = new JButton("Sort Producers by Energy");
        sortButton.setToolTipText("Click to sort producers by total energy produced");
        sortButton.setMnemonic(KeyEvent.VK_O);
        sortButton.getAccessibleContext().setAccessibleDescription("Sorts the energy producers by total amount");

        minMaxButton = new JButton("Calculate Min/Max Energy");
        minMaxButton.setToolTipText("Click to calculate minimum and maximum energy per state");
        minMaxButton.setMnemonic(KeyEvent.VK_M);
        minMaxButton.getAccessibleContext().setAccessibleDescription("Calculates the minimum and maximum energy per state");

        buttonPanel.add(displaySourcesButton);
        buttonPanel.add(sortButton);
        buttonPanel.add(minMaxButton);
        buttonPanel.add(mostUsedSourceButton);

        // Panel for energy by month
        JPanel monthPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel monthInputLabel = new JLabel("Month:");
        monthInputLabel.setLabelFor(monthInputField);
        monthInputLabel.setDisplayedMnemonic(KeyEvent.VK_H);
        monthPanel.add(monthInputLabel);
        monthPanel.add(monthInputField);
        monthPanel.add(energyByMonthButton);

        monthPanel.setBorder(BorderFactory.createTitledBorder("Energy by Month"));

        // Initialize table for displaying results
        String[] columnNames = {"Year", "Month", "State", "Source", "Producer", "Generation"};
        tableModel = new DefaultTableModel(columnNames, 0);
        dataTable = new JTable(tableModel);

        // Set preferred viewport size for the table
        dataTable.setPreferredScrollableViewportSize(new Dimension(600, 200));
        dataTable.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(dataTable);

        // Add components to the main window
        add(inputPanel, BorderLayout.WEST);
        add(buttonPanel, BorderLayout.NORTH);
        add(monthPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.EAST);
    }

    /**
     * Clears the table data.
     */
    public void clearTable() {
        tableModel.setRowCount(0);
    }

    /**
     * Adds a row to the table.
     * 
     * @param rowData the data to add
     */
    public void addTableRow(Object[] rowData) {
        tableModel.addRow(rowData);
    }

    /**
     * Clears the input fields.
     */
    public void clearInputFields() {
        stateField.setText("");
        sourceComboBox.setSelectedIndex(0);
        amountField.setText("");
        monthField.setText("");
        yearField.setText("");
        producerComboBox.setSelectedIndex(0);
        monthInputField.setText("");
    }

    // Getters for all components

    /**
     *
     * @return
     */
    public JButton getDisplaySourcesButton() {
        return displaySourcesButton;
    }

    /**
     *
     * @return
     */
    public JButton getSortButton() {
        return sortButton;
    }

    /**
     *
     * @return
     */
    public JButton getMinMaxButton() {
        return minMaxButton;
    }

    /**
     *
     * @return
     */
    public JButton getAddButton() {
        return addButton;
    }

    /**
     *
     * @return
     */
    public JButton getMostUsedSourceButton() {
        return mostUsedSourceButton;
    }

    /**
     *
     * @return
     */
    public JButton getEnergyByMonthButton() {
        return energyByMonthButton;
    }

    /**
     *
     * @return
     */
    public JTextField getStateField() {
        return stateField;
    }

    /**
     *
     * @return
     */
    public JComboBox<EnergySource> getSourceComboBox() {
        return sourceComboBox;
    }

    /**
     *
     * @return
     */
    public JTextField getAmountField() {
        return amountField;
    }

    /**
     *
     * @return
     */
    public JTextField getMonthField() {
        return monthField;
    }

    /**
     *
     * @return
     */
    public JTextField getYearField() {
        return yearField;
    }

    /**
     *
     * @return
     */
    public JComboBox<EnergyProducer> getProducerComboBox() {
        return producerComboBox;
    }

    /**
     *
     * @return
     */
    public JTextField getMonthInputField() {
        return monthInputField;
    }
}