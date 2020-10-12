package org.openjfx;

import javafx.scene.control.Spinner;

public class PaperPublisher {
    private Integer id = null;
    private String publish_date = null;
    private Integer pages = null;
    private Spinner<Integer> copies = null;
    private Integer copies_returned = null;
    private Integer data = null;


    public PaperPublisher() {
    }

    public PaperPublisher(Integer id, String publish_date, Integer pages, Spinner<Integer> copies, Integer copies_returned, Integer data) {
        this.id = id;
        this.publish_date = publish_date;
        this.pages = pages;
        this.copies = copies;
        this.copies_returned = copies_returned;
        this.data = data;
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

    public Spinner<Integer> getCopies() {
        return copies;
    }

    public void setCopies(Spinner<Integer> copies) {
        copies.setEditable(true);
        this.copies = copies;
    }

    public Integer getCopies_returned() {
        return copies_returned;
    }

    public void setCopies_returned(Integer copies_returned) {
        this.copies_returned = copies_returned;
    }

    public Integer getData() {
        return data;
    }

    public void setData(Integer data) {
        this.data = data;
    }

}
