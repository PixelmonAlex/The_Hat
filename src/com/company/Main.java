package com.company;

import com.company.display.Display;

public class Main {

    public Main() {
        ConfigData configData = new ConfigData();
        HatLogic hatLogic = new HatLogic(configData);
        Display display = new Display(hatLogic);

    }

    public static void main(String[] args) {
        new Main();
    }
}
