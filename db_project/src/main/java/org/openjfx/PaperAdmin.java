package org.openjfx;

import javafx.scene.control.Spinner;

public class PaperAdmin {
    private Integer id = null;
    private String publish_date = null;
    private Integer pages = null;
    private Integer copies = null;
    private Spinner<Integer> copies_returned = null;

    public PaperAdmin() {
    }

    public PaperAdmin(Integer id, String publish_date, Integer pages, Integer copies, Spinner<Integer> copies_returned) {
        this.id = id;
        this.publish_date = publish_date;
        this.pages = pages;
        this.copies = copies;
        this.copies_returned = copies_returned;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPublish_date() {
        return publish_date;
    }

    public void setPublish_date(String publish_date) {
        this.publish_date = publish_date;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Integer getCopies() {
        return copies;
    }

    public void setCopies(Integer copies) {
        this.copies = copies;
    }

    public Spinner<Integer> getCopies_returned() {
        return copies_returned;
    }

    public void setCopies_returned(Spinner<Integer> spinner) {
        spinner.setEditable(true);
        this.copies_returned = spinner;
    }

}
