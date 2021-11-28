package com.company;
import com.company.display.Display;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.time.LocalDateTime;

public class HatLogic {
    private final Random random;
    private final ConfigData configData;
    private HatData hatData;

    public HatLogic(ConfigData cd) {
        hatData = new HatData();
        random = new Random();
        configData = cd;
    }

    /**
     * Returns all data from the Hat as an array of strings;
     * @return returns the data from the HatData instance as an array
     */
    public String[] getData() {
        return hatData.getData().toArray(new String[0]);
    }

    /**
     * Returns the name of the current Hat
     * @return name of the hat from the which the data was received.
     */
    public String getHatName() { return hatData.getName(); }

    /**
     * Adds a String to the ArrayList that represents the Hat.
     * @param s String to add to the Hat.
     */
    public void addItem(String s) {
        hatData.getData().add(s);
    }

    /**
     * Removes the item provided to the function from the ArrayList that represents the Hat.
     * @param s String to remove from the Hat.
     */
    public void removeItem(String s) {
        hatData.getData().remove(s);
    }

    /**
     * Empties the data ArrayList that represents the data in the Hat.
     */
    public void emptyHat() {
        hatData = new HatData();
    }

    /**
     * Returns a random item of data from the hat
     * @return random string from the data ArrayList.
     */
    public String drawItem(){
        if(!hatData.getData().isEmpty()) {
            return hatData.getData().get(Math.abs(random.nextInt() % hatData.getData().size()));
        }
        return "";
    }

    /**
     * Give a name to the current Hat.
     * @param name The name to assign to the current Hat.
     */
    public void nameHat(String name) {
        hatData = new HatData(name, hatData.getData());
    }

    /**
     * Set the save path for the Hats produced by this program
     * @param path path to save location
     */
    public void setSavePath(String path) {
        configData.setSavePath(path);
    }

    public void setExportPath(String path) {
        configData.setExportPath(path);
    }

    /**
     * Loads in a Hat from a txt save file.
     */
    public void loadFromTxt() {
        File saveFile;
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
        chooser.setCurrentDirectory(new File(configData.getSavePath()));

        if(chooser.showOpenDialog(Display.frame) == JFileChooser.APPROVE_OPTION) {
            saveFile = chooser.getSelectedFile();
            try {
                Scanner reader = new Scanner(saveFile);
                int lineNum = 0;
                String name = "";
                List<String> data = new ArrayList<>();

                while (reader.hasNextLine()) {
                    lineNum++;

                    if(lineNum == 1) {
                        name = reader.nextLine();
                    }
                    if(lineNum > 2) {
                        data.add(reader.nextLine());
                    }
                }

                //necessary because it adds an empty string to index 0 for some reason.
                data.remove(0);

                hatData = new HatData(name, data);

                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            JOptionPane.showMessageDialog(Display.frame,
                    "Error loading file, try again.",
                    "Alert",
                    JOptionPane.INFORMATION_MESSAGE);
        }

    }

    /**
     * Writes the current data in the HatData instance to disk as a txt file.
     */
    public boolean writeToTxt(String name) {
        if(!hatData.getData().isEmpty()){
            //if provided name is composed only of spaces, ignore - else set name of the data to the given string.
            if(name != null) {
                if (name.replaceAll(" ", "").length() > 0) {
                    hatData = new HatData(name, hatData.getData());
                }
            }
            else {
                return false;
            }
            // if hat does not have a name, set the current date and time as the name
            String fileName = (hatData.getName().equals("")) ? LocalDateTime.now()+"-Hat" : hatData.getName();
            fileName = fileName.replaceAll(":", ".");
            String filePath = configData.getSavePath() + "\\" + fileName + ".txt";
            System.out.println("path: "+filePath);

            try {
                File save = new File(filePath);
                save.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

            try {
                FileWriter writer = new FileWriter(filePath);
                writer.write(fileName+"\n\n");
                for(String s : hatData.getData()) {
                    writer.append(s).append("\n");
                }
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * Writes the data to a HTML file with the hat's name as a header and each item as an element in an unordered list.
     */
    public boolean writeToHtml(String name) {
        if(name != null) {
            if (name.replaceAll(" ", "").length() > 0) {
                hatData = new HatData(name, hatData.getData());
            }
        }
        else {
            return false;
        }
        if(!hatData.getData().isEmpty()){
            // if hat does not have a name, set the current date and time as the name
            String fileName = (hatData.getName().equals("")) ? LocalDateTime.now()+"-Hat" : hatData.getName();
            fileName = fileName.replaceAll(":", ".");
            String filePath = configData.getExportPath() + "\\" + fileName + ".html";
            System.out.println("path: "+filePath);

            try {
                File save = new File(filePath);
                if(!save.createNewFile()) {

                }

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

            try {
                FileWriter writer = new FileWriter(filePath);

                setupHtmlHeader(writer, fileName);

                writer.append("<body>\n");
                writer.append("<h1>"+fileName+"</h1>\n");
                writer.append("<ul>\n");
                for(String s : hatData.getData()) {
                    writer.append("<li>"+s+"</li>\n");
                }
                writer.append("</ul>\n");
                writer.append("</body>\n");
                writer.append("</html>");

                writer.close();

            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
        return false;
    }

    private void setupHtmlHeader(FileWriter w, String hatName) {
        try {
            w.append("<!DOCTYPE html>\n"
                    + "<html>\n"
                    + "<head>\n"
                    + "<meta charset=\"utf-8\"/>\n"
                    + "<title>"+hatName+"</title>\n"
                    + "</head>\n"
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes the current hat's data to a csv file with the hat name followed by the hat data.
     */
    public boolean writeToCsv(String name) {
        if(name != null) {
            if (name.replaceAll(" ", "").length() > 0) {
                hatData = new HatData(name, hatData.getData());
            }
        }
        else {
            return false;
        }
        if(!hatData.getData().isEmpty()) {
            // if hat does not have a name, set the current date and time as the name
            String fileName = (hatData.getName().equals("")) ? LocalDateTime.now()+"-Hat" : hatData.getName();
            fileName = fileName.replaceAll(":", ".");
            String filePath = configData.getExportPath() + "\\" + fileName + ".csv";

            try {
                File save = new File(filePath);
                save.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

            try {
                FileWriter writer = new FileWriter(filePath);
                writer.write(fileName+",");
                String[] data = hatData.getData().toArray(new String[0]);
                for(int i = 0; i < data.length-1; i++) {
                    writer.append(data[i]).append(",");
                }
                writer.append(data[data.length-1]);

                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
        return false;
    }
}
