package com.company;

import java.util.ArrayList;
import java.util.List;

public class HatData {

    private final List<String> data;
    private final String name;

    public HatData() {
        data = new ArrayList<>();
        name = "";
    }

    public HatData(String n) {
        data = new ArrayList<>();
        name = n;
    }

    public HatData(List<String> l) {
        data = l;
        name = "";
    }

    public HatData(String n, List<String> l) {
        name = n;
        data = l;
    }

    public List<String> getData() {
        return data;
    }

    public String getName() {
        return name;
    }
}
