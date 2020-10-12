package org.openjfx;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Article {
    private String path = null;
    private String title = null;
    private String description = null;
    private String submission_date = null;
    private String check_date = null;
    private String state = null;
    private Integer paper = null;
    private String category = null;
    private Integer number_of_pages = null;
    private Integer order_in_paper = null;
    private Button update_button = null;
    private Button accept_button = null;
    private Button reject_button = null;
    private Button revise_button = null;
    private TextField comments = null;
    private Button minus_button = null;
    private Button plus_button = null;

    public Article() {
    }

    public Article(String path, String title, String description, String submission_date, String check_date, String state, Integer paper, String categories, Integer number_of_pages, Integer order_in_paper, Button update_button, Button accept_button, Button reject_button, Button revise_button, TextField comments, Button minus_button, Button plus_button) {
        this.path = path;
        this.title = title;
        this.description = description;
        this.description = submission_date;
        this.description = check_date;
        this.state = state;
        this.paper = paper;
        this.category = categories;
        this.number_of_pages = number_of_pages;
        this.order_in_paper = order_in_paper;
        this.update_button = update_button;
        this.accept_button = accept_button;
        this.reject_button = reject_button;
        this.revise_button = revise_button;
        this.comments = comments;
        this.minus_button = minus_button;
        this.plus_button = plus_button;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getSubmission_date() {
        return submission_date;
    }

    public void setSubmission_date(String submission_date) {
        this.submission_date = submission_date;
    }


    public String getCheck_date() {
        return check_date;
    }

    public void setCheck_date(String check_date) {
        this.check_date = check_date;
    }


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getPaper() {
        return paper;
    }

    public void setPaper(Integer paper) {
        this.paper = paper;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getNumber_of_pages() {
        return number_of_pages;
    }

    public void setNumber_of_pages(Integer number_of_pages) {
        this.number_of_pages = number_of_pages;
    }

    public Integer getOrder_in_paper() {
        return order_in_paper;
    }

    public void setOrder_in_paper(Integer order_in_paper) {
        if(order_in_paper == 0)
            this.order_in_paper = null;
        else
            this.order_in_paper = order_in_paper;
    }

    public Button getUpdate_button() {
        return update_button;
    }

    public void setUpdate_button(Button update_button) {
        this.update_button = update_button;
        if(this.state.equals("ACCEPTED") || this.state.equals("REJECTED"))
            this.update_button.setDisable(true);
    }

    public Button getAccept_button() {
        return accept_button;
    }

    public void setAccept_button(Button accept_button) {
        this.accept_button = accept_button;
        if(this.state.equals("ACCEPTED") || this.state.equals("REJECTED") || this.state.equals("CHANGES_NEEDED"))
            this.accept_button.setDisable(true);
    }

    public Button getReject_button() {
        return reject_button;
    }

    public void setReject_button(Button reject_button) {
        this.reject_button = reject_button;
        if(this.state.equals("ACCEPTED") || this.state.equals("REJECTED") || this.state.equals("CHANGES_NEEDED"))
            this.reject_button.setDisable(true);
    }

    public Button getRevise_button() {
        return revise_button;
    }

    public void setRevise_button(Button revise_button) {
        this.revise_button = revise_button;
        if(this.state.equals("ACCEPTED") || this.state.equals("REJECTED") || this.state.equals("CHANGES_NEEDED"))
            this.revise_button.setDisable(true);
    }

    public TextField getComments() {
        return comments;
    }

    public void setComments(TextField comments) {
        this.comments = comments;
    }

    public Button getMinus_button() {
        return minus_button;
    }

    public void setMinus_button(Button minus_button) {
        this.minus_button = minus_button;
    }

    public Button getPlus_button() {
        return plus_button;
    }

    public void setPlus_button(Button plus_button) {
        this.plus_button = plus_button;
    }


}
