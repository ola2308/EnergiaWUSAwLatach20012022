package com.mycompany.energiawusawlatach2001.controller;

import com.mycompany.energiawusawlatach2001.model.Model;
import com.mycompany.energiawusawlatach2001.model.EnergyData;
import com.mycompany.energiawusawlatach2001.model.InvalidEnergyDataException;
import com.mycompany.energiawusawlatach2001.model.EnergySource;
import com.mycompany.energiawusawlatach2001.model.EnergyProducer;
import com.mycompany.energiawusawlatach2001.view.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import java.util.Map;

/**
* The {@code Controller} class bridges the {@link Model} and {@link View}.
* It processes user input from the view and updates the model and/or view accordingly.
* 
* @author Aleksandra Nizio
* @version 1.0
*/
public class Controller {
   private final Model model;
   private final View view;

   /**
    * Initializes the controller with the specified model and view.
    * Sets up action listeners for the view components.
    *
    * @param model the {@link Model} instance
    * @param view  the {@link View} instance
    */
   public Controller(Model model, View view) {
       this.model = model;
       this.view = view;

       displayAllData();

       view.getDisplaySourcesButton().addActionListener(new DisplaySourcesListener());
       view.getSortButton().addActionListener(new SortProducersListener());
       view.getMinMaxButton().addActionListener(new MinMaxEnergyListener());
       view.getAddButton().addActionListener(new AddDataListener());
       view.getMostUsedSourceButton().addActionListener(new MostUsedSourceListener());
       view.getEnergyByMonthButton().addActionListener(new EnergyByMonthListener());
   }

   /**
    * Displays all energy data from the model in the view's table.
    */
   private void displayAllData() {
       view.clearTable();
       model.getEnergyDataList().forEach(data -> view.addTableRow(new Object[]{
           data.year(), 
           data.month(), 
           data.state(), 
           data.source().getDisplayName(), 
           data.producer().getDisplayName(), 
           data.amount()
       }));
   }

   /**
    * Listener for displaying unique energy sources.
    */
   private class DisplaySourcesListener implements ActionListener {
       @Override
       public void actionPerformed(ActionEvent e) {
           var sources = model.getEnergySources();
           StringBuilder sourcesList = new StringBuilder();
           sources.forEach(source -> sourcesList.append(source.getDisplayName()).append(", "));
           
           // Remove last comma and space
           if (sourcesList.length() > 0) {
               sourcesList.setLength(sourcesList.length() - 2);
           }
           
           JOptionPane.showMessageDialog(view, sourcesList.toString(),
                   "Energy Sources", JOptionPane.INFORMATION_MESSAGE);
       }
   }

   /**
    * Listener for sorting producers by total energy production.
    */
   private class SortProducersListener implements ActionListener {
       @Override
       public void actionPerformed(ActionEvent e) {
           var sortedProducers = model.sortProducersByTotalEnergy();
           StringBuilder message = new StringBuilder("Producers sorted by energy:\n");
           sortedProducers.forEach(entry -> message.append(entry.getKey().getDisplayName())
               .append(": ").append(entry.getValue()).append(" MWh\n"));
           JOptionPane.showMessageDialog(view, message.toString(),
                   "Sorted Producers", JOptionPane.INFORMATION_MESSAGE);
       }
   }

   /**
    * Listener for calculating minimum and maximum energy for each state.
    */
   private class MinMaxEnergyListener implements ActionListener {
       @Override
       public void actionPerformed(ActionEvent e) {
           var minEnergy = model.calculateMinEnergy();
           var maxEnergy = model.calculateMaxEnergy();
           StringBuilder message = new StringBuilder("Min/Max energy per state:\n");
           minEnergy.forEach((state, min) -> {
               double max = maxEnergy.get(state);
               message.append(state).append(" - Min: ").append(min)
                   .append(" MWh, Max: ").append(max).append(" MWh\n");
           });
           JOptionPane.showMessageDialog(view, message.toString(),
                   "Min/Max Energy", JOptionPane.INFORMATION_MESSAGE);
       }
   }

   /**
    * Listener for adding new energy data.
    */
   private class AddDataListener implements ActionListener {
       @Override
       public void actionPerformed(ActionEvent e) {
           try {
               String state = view.getStateField().getText();
               EnergySource source = (EnergySource) view.getSourceComboBox().getSelectedItem();
               EnergyProducer producer = (EnergyProducer) view.getProducerComboBox().getSelectedItem();
               double amount = Double.parseDouble(view.getAmountField().getText());
               int month = Integer.parseInt(view.getMonthField().getText());
               int year = Integer.parseInt(view.getYearField().getText());

               model.addEnergyData(new EnergyData(year, month, state, source, producer, amount));
               displayAllData();
               view.clearInputFields();
           } catch (NumberFormatException ex) {
               JOptionPane.showMessageDialog(view, "Invalid input! Ensure numeric fields are properly filled.",
                       "Input Error", JOptionPane.ERROR_MESSAGE);
           } catch (InvalidEnergyDataException ex) {
               JOptionPane.showMessageDialog(view, "Invalid energy data: " + ex.getMessage(),
                       "Input Error", JOptionPane.ERROR_MESSAGE);
           }
       }
   }

   /**
    * Listener for finding the most used energy source.
    */
   private class MostUsedSourceListener implements ActionListener {
       @Override
       public void actionPerformed(ActionEvent e) {
           EnergySource mostUsed = model.getMostUsedEnergySource();
           String message = mostUsed != null ? 
               "Most used energy source: " + mostUsed.getDisplayName() :
               "No energy source data available";
           JOptionPane.showMessageDialog(view, message,
                   "Most Used Source", JOptionPane.INFORMATION_MESSAGE);
       }
   }

   /**
    * Listener for displaying energy data for a specific month.
    */
   private class EnergyByMonthListener implements ActionListener {
       @Override
       public void actionPerformed(ActionEvent e) {
           try {
               int month = Integer.parseInt(view.getMonthInputField().getText());
               var energyByState = model.getEnergyByStateForMonth(month);
               StringBuilder message = new StringBuilder("Energy for month ").append(month).append(":\n");
               energyByState.forEach((state, total) -> 
                   message.append(state).append(": ").append(total).append(" MWh\n"));
               JOptionPane.showMessageDialog(view, message.toString(),
                       "Energy by Month", JOptionPane.INFORMATION_MESSAGE);
           } catch (NumberFormatException ex) {
               JOptionPane.showMessageDialog(view, "Invalid month input. Please enter a number between 1 and 12.",
                       "Input Error", JOptionPane.ERROR_MESSAGE);
           }
       }
   }
}