package org.openjfx;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class AdministrativeViewController {

    public TableView<PaperAdmin> papersTable;
    public TabPane tabPanel;
    public Label introductionLabel;

    public TableView<Journalist> financialTable;
    public Label totalMoneyLabel;
    public Spinner<Integer> monthsSpinner;


    java.util.Date dt = new java.util.Date();
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
    String currentDate = sdf.format(dt);

    @FXML
    public void initialize() throws SQLException {
        introductionLabel.setText(App.email + " - Administrative @ " + App.newspaper);

        monthsSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
            System.out.println("Old value: " + oldValue + " | New value: " + newValue);
            try {

                populateFinancialData(newValue);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });


        TableColumn<Journalist, Integer> email_column = new TableColumn<>("Email");
        email_column.setCellValueFactory(new PropertyValueFactory<>("email"));
        financialTable.getColumns().add(email_column);

        TableColumn<Journalist, Integer> money_column = new TableColumn<>("Money");
        money_column.setCellValueFactory(new PropertyValueFactory<>("money"));
        financialTable.getColumns().add(money_column);


        populateFinancialData(1);



        TableColumn<PaperAdmin, Integer> id_column = new TableColumn<>("ID");
        id_column.setCellValueFactory(new PropertyValueFactory<>("id"));
        papersTable.getColumns().add(id_column);

        TableColumn<PaperAdmin, String> publish_date_column = new TableColumn<>("Publish Date");
        publish_date_column.setCellValueFactory(new PropertyValueFactory<>("publish_date"));
        papersTable.getColumns().add(publish_date_column);

        TableColumn<PaperAdmin, Integer> pages_column = new TableColumn<>("Pages");
        pages_column.setCellValueFactory(new PropertyValueFactory<>("pages"));
        papersTable.getColumns().add(pages_column);

        TableColumn<PaperAdmin, Integer> copies_column = new TableColumn<>("Copies");
        copies_column.setCellValueFactory(new PropertyValueFactory<>("copies"));
        papersTable.getColumns().add(copies_column);

        TableColumn<PaperAdmin, Spinner<Integer>> copies_returned_column = new TableColumn<>("Returned");
        copies_returned_column.setCellValueFactory(new PropertyValueFactory<>("copies_returned"));
        papersTable.getColumns().add(copies_returned_column);


        populatePapersData();
    }

    public void populatePapersData () {

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = Database.getDBConnection();
            connection.setAutoCommit(false);

            String query = "SELECT * FROM paper WHERE newspaper = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, App.newspaper);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                PaperAdmin newPaper = new PaperAdmin();
                newPaper.setId(resultSet.getInt("id"));
                newPaper.setPublish_date(resultSet.getString("publish_date"));
                newPaper.setPages(resultSet.getInt("pages"));
                newPaper.setCopies(resultSet.getInt("copies"));
                newPaper.setCopies_returned(new Spinner<>(resultSet.getInt("copies_returned"), resultSet.getInt("copies"), 0, 1));

                newPaper.getCopies_returned().valueProperty().addListener((obs, oldValue, newValue) -> {

                    System.out.println("Old value: " + oldValue + " | New value: " + newValue);
                    try {
                        changeCopiesReturned(newPaper.getId(), newPaper.getCopies_returned().getValue());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date date1 = format.parse(newPaper.getPublish_date());
                java.util.Date date2 = format.parse(currentDate);
                if (date1.compareTo(date2) > 0) {
                    newPaper.getCopies_returned().setDisable(true);
                }

                System.out.println(newPaper);
                papersTable.getItems().add(newPaper);
            }

        } catch (SQLException | ParseException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }


    public void populateFinancialData (int months) throws SQLException {
        financialTable.getItems().clear();

        Connection connection = null;
        CallableStatement statement = null;
        ResultSet resultSet;
        try {
            connection = Database.getDBConnection();
            connection.setAutoCommit(false);

            String storedProcedureCall = "{call CalculateFinancialData(?, ?)};";

            statement = connection.prepareCall(storedProcedureCall);
            statement.setInt(1, months);

            statement.registerOutParameter(2, java.sql.Types.FLOAT);
            statement.execute();

            float sum = statement.getInt(2);
            //System.out.println("---->>>>>" + sum);
            totalMoneyLabel.setText("Total Spends: " + sum + "€");

            resultSet = statement.getResultSet();

            if (resultSet.next()) {
                financialTable.getItems().add(new Journalist(resultSet.getString(1), resultSet.getFloat(2)));
            }

            boolean result = statement.getMoreResults();

            while(result) {
                resultSet = statement.getResultSet();
                if (resultSet.next()) {
                    financialTable.getItems().add(new Journalist(resultSet.getString(1), resultSet.getFloat(2)));
                }
                result = statement.getMoreResults();
            }

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            if (null != connection) {
                connection.rollback();
            }
        } finally {
            if (null != statement) {
                statement.close();
            }
            if (null != connection) {
                connection.close();
            }
        }
    }


    private void changeCopiesReturned (Integer id, Integer copies_returned) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = Database.getDBConnection();
            connection.setAutoCommit(false);


            String query = "UPDATE paper SET copies_returned = ? WHERE newspaper = ? AND id = ?";
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, copies_returned);
            statement.setString(2, App.newspaper);
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