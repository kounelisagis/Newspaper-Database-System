package org.openjfx;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class PublisherViewController {

    public Label introductionLabel;

    public TabPane tabPanel;

    public TableView<Newspaper> newspapersTable;
    public TableView<PaperPublisher> papersTable;

    java.util.Date dt = new java.util.Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String currentDate = sdf.format(dt);


    @FXML
    public void initialize() {
        introductionLabel.setText("Publisher: " + App.email);

        TableColumn<Newspaper, String> name_column = new TableColumn<>("Newspaper");
        name_column.setCellValueFactory(new PropertyValueFactory<>("name"));
        newspapersTable.getColumns().add(name_column);

        TableColumn<Newspaper, ComboBox<String>> frequency_column = new TableColumn<>("Frequency");
        frequency_column.setCellValueFactory(new PropertyValueFactory<>("frequency"));
        newspapersTable.getColumns().add(frequency_column);

        TableColumn<Newspaper, ComboBox<String>> chief_editor_column = new TableColumn<>("Editor in Chief");
        chief_editor_column.setCellValueFactory(new PropertyValueFactory<>("chief_editor"));
        newspapersTable.getColumns().add(chief_editor_column);

        populateTab1();

        // TABLE 2

        TableColumn<PaperPublisher, Integer> id_column = new TableColumn<>("ID");
        id_column.setCellValueFactory(new PropertyValueFactory<>("id"));
        papersTable.getColumns().add(id_column);

        TableColumn<PaperPublisher, String> publish_date_column = new TableColumn<>("Publish Date");
        publish_date_column.setCellValueFactory(new PropertyValueFactory<>("publish_date"));
        papersTable.getColumns().add(publish_date_column);

        TableColumn<PaperPublisher, Integer> pages_column = new TableColumn<>("Pages");
        pages_column.setCellValueFactory(new PropertyValueFactory<>("pages"));
        papersTable.getColumns().add(pages_column);

        TableColumn<PaperPublisher, Spinner<Integer>> copies_column = new TableColumn<>("Copies");
        copies_column.setCellValueFactory(new PropertyValueFactory<>("copies"));
        papersTable.getColumns().add(copies_column);

        TableColumn<PaperPublisher, Integer> copies_returned_column = new TableColumn<>("Returned");
        copies_returned_column.setCellValueFactory(new PropertyValueFactory<>("copies_returned"));
        papersTable.getColumns().add(copies_returned_column);

        TableColumn<PaperPublisher, Integer> data_column = new TableColumn<>("Data");
        data_column.setCellValueFactory(new PropertyValueFactory<>("data"));
        papersTable.getColumns().add(data_column);

        populateTab2();
    }


    public List<String> getJournalists(String newspaper) throws SQLException {
        List<String> journalistsList = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = Database.getDBConnection();
            connection.setAutoCommit(false);
            String query = "SELECT * FROM journalist LEFT JOIN works ON journalist.email = works.worker WHERE works.newspaper = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, newspaper);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                journalistsList.add(resultSet.getString("email"));
            }

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        } finally {
            if (null != statement) {
                statement.close();
            }
            if (null != connection) {
                connection.close();
            }
        }

        return journalistsList;
    }

    public void populateTab1 () {

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = Database.getDBConnection();
            connection.setAutoCommit(false);

            String query = "SELECT * FROM newspaper WHERE publisher = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, App.email);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Newspaper newNewspaper = new Newspaper();
                newNewspaper.setName(resultSet.getString("name"));

                ComboBox<String> frequencyCombo = new ComboBox<>();
                for (String c : new String[]{"daily", "weekly", "monthly"})
                    frequencyCombo.getItems().add(c);
                frequencyCombo.getSelectionModel().select(resultSet.getString("frequency"));
                newNewspaper.setFrequency(frequencyCombo);

                ComboBox<String> chiefCombo = new ComboBox<>();
                for (String c : getJournalists(newNewspaper.getName()))
                    chiefCombo.getItems().add(c);
                chiefCombo.getSelectionModel().select(resultSet.getString("chief_editor"));
                newNewspaper.setChief_editor(chiefCombo);


                frequencyCombo.getSelectionModel().selectedItemProperty().addListener( (options, oldValue, newValue) -> {
                    System.out.println(newNewspaper.getName());
                    System.out.println(newNewspaper.getFrequency().getValue());
                    System.out.println(newNewspaper.getChief_editor().getValue());

                    try {
                        changeNewspaperInfo(newNewspaper.getName(), newNewspaper.getFrequency().getValue(), newNewspaper.getChief_editor().getValue());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                );

                chiefCombo.getSelectionModel().selectedItemProperty().addListener( (options, oldValue, newValue) -> {
                    System.out.println(newNewspaper.getName());
                    System.out.println(newNewspaper.getFrequency().getValue());
                    System.out.println(newNewspaper.getChief_editor().getValue());

                    try {
                        changeNewspaperInfo(newNewspaper.getName(), newNewspaper.getFrequency().getValue(), newNewspaper.getChief_editor().getValue());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                );

                newspapersTable.getItems().add(newNewspaper);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void changeNewspaperInfo(String newspaper, String frequency, String chief_editor) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = Database.getDBConnection();
            connection.setAutoCommit(false);

            String query = "UPDATE newspaper SET frequency = ?, chief_editor = ? WHERE name = ?";
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, frequency);
            statement.setString(2, chief_editor);
            statement.setString(3, newspaper);

            statement.executeUpdate();
            connection.commit();

            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                System.out.println(resultSet.getInt(1));
            }

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            if (null != connection) {
                connection.rollback();
            }
        } finally {
            if (null != resultSet) {
                resultSet.close();
            }
            if (null != statement) {
                statement.close();
            }
            if (null != connection) {
                connection.close();
            }
        }
    }

    public void populateTab2 () {

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = Database.getDBConnection();
            connection.setAutoCommit(false);

            String query = "SELECT * FROM paper LEFT JOIN newspaper ON paper.newspaper = newspaper.name WHERE newspaper.publisher = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, App.email);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                PaperPublisher newPaper = new PaperPublisher();
                newPaper.setId(resultSet.getInt("id"));
                newPaper.setPublish_date(resultSet.getString("publish_date"));
                newPaper.setPages(resultSet.getInt("pages"));
                newPaper.setCopies(new Spinner<>(resultSet.getInt("copies"), 1000000000, 0, 1));
                newPaper.setCopies_returned(resultSet.getInt("copies_returned"));

                String newspaper = resultSet.getString("newspaper");

                newPaper.getCopies().valueProperty().addListener((obs, oldValue, newValue) -> {
                    System.out.println("Old value: " + oldValue + " | New value: " + newValue);
                    try {
                        changeCopies(newPaper.getId(), newPaper.getCopies().getValue(), newspaper);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date date1 = format.parse(newPaper.getPublish_date());
                java.util.Date date2 = format.parse(currentDate);
                if (date1.compareTo(date2) <= 0) {
                    newPaper.getCopies().setDisable(true);
                    newPaper.setData(newPaper.getCopies().getValue() - newPaper.getCopies_returned());
                }

                System.out.println(newPaper);
                papersTable.getItems().add(newPaper);
            }

        } catch (SQLException | ParseException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }


    private void changeCopies (Integer id, Integer copies, String newspaper) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = Database.getDBConnection();
            connection.setAutoCommit(false);

            String query = "UPDATE paper SET copies = ? WHERE newspaper = ? AND id = ?";
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, copies);
            statement.setString(2, newspaper);
            statement.setInt(3, id);

            statement.executeUpdate();
            connection.commit();

            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                System.out.println(resultSet.getInt(1));
            }

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            if (null != connection) {
                connection.rollback();
            }
        } finally {
            if (null != resultSet) {
                resultSet.close();
            }

            if (null != statement) {
                statement.close();
            }

            if (null != connection) {
                connection.close();
            }
        }
    }


    @FXML
    private void switchToLogin() throws IOException {
        App.setRoot("login");
    }
}
