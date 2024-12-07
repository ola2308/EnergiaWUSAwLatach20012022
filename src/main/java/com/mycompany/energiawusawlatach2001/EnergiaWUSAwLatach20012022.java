package com.mycompany.energiawusawlatach2001;

import com.mycompany.energiawusawlatach2001.model.Model;
import com.mycompany.energiawusawlatach2001.model.EnergyData;
import com.mycompany.energiawusawlatach2001.model.InvalidEnergyDataException;
import com.mycompany.energiawusawlatach2001.model.EnergySource;
import com.mycompany.energiawusawlatach2001.model.EnergyProducer;
import com.mycompany.energiawusawlatach2001.view.View;
import com.mycompany.energiawusawlatach2001.controller.Controller;
import javax.swing.JOptionPane;

/**
* The EnergiaWUSAwLatach20012022 class is the entry point of the application.
* It initializes the Model, View, and Controller components and starts the GUI.
*
* @author Aleksandra Nizio
* @version 1.0
*/
public class EnergiaWUSAwLatach20012022 {
   /**
    * The main method serves as the entry point of the application.
    *
    * @param args command-line arguments: [state] [source] [amount] [month] [year] [producer]
    *             where source must be one of: Coal, Natural Gas, Wind, Solar, Hydroelectric
    *             and producer must be one of: Electric Utilities, Independent Power Producers, 
    *             Combined Heat and Power, Nuclear Power Plants, Renewable Energy Companies,
    *             Hydroelectric Facilities, Fossil Fuel Plants, Geothermal Plants
    */
   public static void main(String[] args) {
       Model model = new Model();
       
       if (args.length == 6) {
           String state = args[0];
           try {
               double amount = Double.parseDouble(args[2]);
               int month = Integer.parseInt(args[3]);
               int year = Integer.parseInt(args[4]);
               
               // Convert string arguments to enum values
               EnergySource source = EnergySource.fromString(args[1]);
               EnergyProducer producer = EnergyProducer.fromString(args[5]);
               
               // Create EnergyData object and add to model
               EnergyData energyData = new EnergyData(year, month, state, source, producer, amount);
               model.addEnergyData(energyData);
           } catch (NumberFormatException e) {
               JOptionPane.showMessageDialog(null,
                       "Invalid number format in command-line arguments. The application will start without initial data.",
                       "Input Error", JOptionPane.ERROR_MESSAGE);
           } catch (InvalidEnergyDataException e) {
               JOptionPane.showMessageDialog(null,
                       "Invalid energy data: " + e.getMessage() + ". The application will start without initial data.",
                       "Input Error", JOptionPane.ERROR_MESSAGE);
           }
       } else if (args.length > 0 && args.length < 6) {
           JOptionPane.showMessageDialog(null,
                   "Incorrect number of command-line arguments. The application will start without initial data.",
                   "Input Error", JOptionPane.ERROR_MESSAGE);
       }

       View view = new View();
       new Controller(model, view);
       view.setVisible(true);
   }
}