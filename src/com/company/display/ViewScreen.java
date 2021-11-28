package com.company.display;

import com.company.HatLogic;

import javax.swing.*;

public class ViewScreen implements Screen {

    private final HatLogic hatLogic;

    private JScrollPane scrollPane;

    public ViewScreen(HatLogic hat) {
        hatLogic = hat;
    }

    private void updateContents() {
        //create list out of all items from the hat
        scrollPane = new JScrollPane(new JList(hatLogic.getData()));
    }

    @Override
    public JComponent[] getComponents() {
        updateContents();
        return new JComponent[] {scrollPane};
    }
}
