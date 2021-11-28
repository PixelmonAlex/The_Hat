package com.company.display;

import com.company.HatLogic;
import javax.swing.*;
import java.awt.*;

public class Display {
    public static final JFrame frame = new JFrame("The Hat App");
    private final JPanel mainPanel;
    private final JButton addToHat, drawFromHat, viewHat, emptyHat, misc, saveHat, loadHat;
    private final ViewScreen viewScreen;
    private final ExportScreen exportScreen;
    private Screen currentScreen;

    private final HatLogic hatLogic;

    public Display(HatLogic hat) {
        hatLogic = hat;

        int buttonWidth = 140, buttonHeight = 25;

        //initial frame setup
        frame.setSize(new Dimension(buttonWidth*7, 400));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //setup menu bar
        JMenuBar menuBar = new JMenuBar();

        addToHat = new JButton("Add Item to Hat");
        addToHat.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        drawFromHat = new JButton("Draw From Hat");
        drawFromHat.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        emptyHat = new JButton("Empty Hat");
        emptyHat.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        viewHat = new JButton("View Hat Contents");
        viewHat.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        misc = new JButton("Miscellaneous");
        misc.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        saveHat = new JButton("Save Current Hat");
        saveHat.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        loadHat = new JButton("Load Saved Hat");
        loadHat.setPreferredSize(new Dimension(buttonWidth, buttonHeight));

        setupButtonFuncs();

        menuBar.add(addToHat);
        menuBar.add(drawFromHat);
        menuBar.add(emptyHat);
        menuBar.add(saveHat);
        menuBar.add(loadHat);
        menuBar.add(viewHat);
        menuBar.add(misc);

        //setup JPanel and layout
        GridLayout splitter = new GridLayout(1, 1);
        mainPanel = new JPanel(splitter);

        //setup screens
        viewScreen = new ViewScreen(hatLogic);
        exportScreen = new ExportScreen(hatLogic);
        currentScreen = viewScreen;

        for(JComponent c : currentScreen.getComponents()) {
            mainPanel.add(c);
        }

        frame.add(mainPanel);

        frame.setJMenuBar(menuBar);
        frame.setVisible(true);
    }

    private void updateDisplay() {
        for(Component c : mainPanel.getComponents()) {
            mainPanel.remove(c);
        }

        for(JComponent c : currentScreen.getComponents()) {
            mainPanel.add(c);
        }

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void setupButtonFuncs() {
        addToHat.addActionListener(e -> {
            currentScreen = viewScreen;
            updateDisplay();
            String input;
            //prompt data entry
            input = JOptionPane.showInputDialog(Display.frame,
                    "Enter Item to add to the Hat",
                    "Alert!",
                    JOptionPane.INFORMATION_MESSAGE);

            //add entered data into hat logic if it isn't composed solely of spaces;
            if(input != null) {
                if (input.replaceAll(" ", "").length() > 0) {
                    hatLogic.addItem(input);
                }
            }
            updateDisplay();
        });

        drawFromHat.addActionListener(e -> {
            String item = hatLogic.drawItem();
            if(!item.equals("")) {
                int optionChosen = JOptionPane.showOptionDialog(Display.frame,
                        item,
                        "You Drew...",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        new String[]{"Place Back", "Remove"},
                        "Place Back");

                //if the user clicked the 'remove' option
                if (optionChosen == 1) {
                    hatLogic.removeItem(item);
                }
            }
            else {
                JOptionPane.showMessageDialog(Display.frame,
                        "The Hat is empty!\nAdd some Items to start using the Hat.",
                        "Alert",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            currentScreen = viewScreen;
            updateDisplay();
        });

        viewHat.addActionListener(e -> {
            currentScreen = viewScreen;
            updateDisplay();
        });

        emptyHat.addActionListener(e -> {
            //empty hat data
            hatLogic.emptyHat();
            currentScreen = viewScreen;
            updateDisplay();

            JOptionPane.showMessageDialog(frame,
                    "Emptied all Items from the Hat! \nAdd some new Items to use the Hat again.",
                    "Action Completed",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        misc.addActionListener(e -> {
            currentScreen = exportScreen;
            updateDisplay();
        });

        saveHat.addActionListener(e -> {
            int choice;
            if(hatLogic.getHatName().equals("")) {
                choice = JOptionPane.showConfirmDialog(Display.frame,
                        "Do you wish to name your Hat?\nIf 'No' is selected, a default name will be generated.",
                        "Saving your Hat",
                        JOptionPane.YES_NO_OPTION);
            }
            else {
                choice = JOptionPane.showConfirmDialog(Display.frame,
                        "Do you wish to rename your Hat?\nIf 'No' is selected, the old name will be used.",
                        "Saving your Hat",
                        JOptionPane.YES_NO_OPTION);
            }
            String input = "";
            if(choice == 0) {
                input = JOptionPane.showInputDialog(Display.frame,
                        "Enter a Name for your Hat below.",
                        "Saving your Hat",
                        JOptionPane.INFORMATION_MESSAGE);
            }


            boolean complete = hatLogic.writeToTxt(input);
            if(!complete) {
                JOptionPane.showMessageDialog(Display.frame,
                        "Your Hat was not saved.\nThis may be because your Hat was empty,\n" +
                                "Or that you cancelled when inputting a name.",
                        "Hat not Saved",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        loadHat.addActionListener(e -> {
            hatLogic.loadFromTxt();
            updateDisplay();
        });
    }
}
