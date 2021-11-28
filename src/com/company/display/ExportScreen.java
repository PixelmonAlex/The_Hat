package com.company.display;

import com.company.HatLogic;

import javax.swing.*;
import java.awt.*;

public class ExportScreen implements Screen{

    //Always place back / always remove
    //Colour themes

    //Export directory
    //Export as HTML, CSV, txt
    //Save directory
    //Use Desktop class to do above.

    private final JPanel miscPanel;
    private final JButton exportToHTML, exportToCSV, setSaveLocation, setExportLocation;
    private final HatLogic hatLogic;

    public ExportScreen(HatLogic hLogic) {
        hatLogic = hLogic;

        GridLayout layout = new GridLayout(6, 2);
        miscPanel = new JPanel();
        miscPanel.setLayout(layout);

        exportToHTML = new JButton("Export to HTML");
        exportToCSV = new JButton("Export to CSV");
        setSaveLocation = new JButton("Set Save Location");
        setExportLocation = new JButton("Set Export Location");

        setupButtons();

        miscPanel.add(exportToHTML);
        miscPanel.add(exportToCSV);
        miscPanel.add(setSaveLocation);
        miscPanel.add(setExportLocation);
    }

    private void setupButtons() {
        exportToHTML.addActionListener(e -> {
            int choice;
            if(hatLogic.getHatName().equals("")) {
                choice = JOptionPane.showConfirmDialog(Display.frame,
                        "Do you wish to name the HTML file?\nIf 'No' is selected, a default name will be generated.",
                        "Saving your Hat",
                        JOptionPane.YES_NO_OPTION);
            }
            else {
                choice = JOptionPane.showConfirmDialog(Display.frame,
                        "Do you wish to rename the HTML file?\nIf 'No' is selected, the old name will be used.",
                        "Saving your Hat",
                        JOptionPane.YES_NO_OPTION);
            }
            String input = "";
            if(choice == 0) {
                input = JOptionPane.showInputDialog(Display.frame,
                        "Enter a Name for your HTML below.",
                        "Saving your HTML",
                        JOptionPane.INFORMATION_MESSAGE);
            }

            if(!hatLogic.writeToHtml(input)) {
                JOptionPane.showMessageDialog(Display.frame,
                        "Your Hat was not exported.\nThis may be because your Hat was empty,\nOr that you cancelled when inputting a name.",
                        "Hat not Saved",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        exportToCSV.addActionListener(e -> {
            int choice;
            if(hatLogic.getHatName().equals("")) {
                choice = JOptionPane.showConfirmDialog(Display.frame,
                        "Do you wish to name the CSV file?\nIf 'No' is selected, a default name will be generated.",
                        "Saving your Hat",
                        JOptionPane.YES_NO_OPTION);
            }
            else {
                choice = JOptionPane.showConfirmDialog(Display.frame,
                        "Do you wish to rename the CSV file?\nIf 'No' is selected, the old name will be used.",
                        "Saving your Hat",
                        JOptionPane.YES_NO_OPTION);
            }

            String input = "";
            if(choice == 0) {
                input = JOptionPane.showInputDialog(Display.frame,
                        "Enter a Name for your CSV below.",
                        "Saving your CSV",
                        JOptionPane.INFORMATION_MESSAGE);
            }

            if(!hatLogic.writeToCsv(input)) {
                JOptionPane.showMessageDialog(Display.frame,
                        "Your Hat was not exported.\nThis may be because your Hat was empty,\nOr that you cancelled when inputting a name.",
                        "Hat not Saved",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        setSaveLocation.addActionListener(e -> {
            JFileChooser jfc = new JFileChooser();
            jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            if(jfc.showOpenDialog(Display.frame) == JFileChooser.APPROVE_OPTION) {
                hatLogic.setSavePath(jfc.getSelectedFile().getAbsolutePath());
            }
        });

        setExportLocation.addActionListener(e -> {
            JFileChooser jfc = new JFileChooser();
            jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            if(jfc.showOpenDialog(Display.frame) == JFileChooser.APPROVE_OPTION) {
                hatLogic.setExportPath(jfc.getSelectedFile().getAbsolutePath());
            }
        });
    }

    @Override
    public JComponent[] getComponents() {
        return new JComponent[] {miscPanel};
    }
}
