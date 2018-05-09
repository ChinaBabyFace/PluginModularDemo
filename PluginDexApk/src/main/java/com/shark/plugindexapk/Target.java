package com.shark.plugindexapk;

public class Target {
    private String name = "Shark";

    private String showName() {
        return name+"@"+System.currentTimeMillis();
    }
}
