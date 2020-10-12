package org.openjfx;

import javafx.scene.control.ComboBox;

public class Newspaper {
    private String name = null;
    private ComboBox<String> frequency = null;
    private ComboBox<String> chief_editor = null;

    public Newspaper() {
    }

    public Newspaper(String name, ComboBox<String> frequency, ComboBox<String> chief_editor) {
        this.name = name;
        this.frequency = frequency;
        this.chief_editor = chief_editor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ComboBox<String> getFrequency() {
        return frequency;
    }

    public void setFrequency(ComboBox<String> frequency) {
        this.frequency = frequency;
    }

    public ComboBox<String> getChief_editor() {
        return chief_editor;
    }

    public void setChief_editor(ComboBox<String> chief_editor) {
        this.chief_editor = chief_editor;
    }

}
