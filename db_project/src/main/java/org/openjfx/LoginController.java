package org.openjfx;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import at.favre.lib.crypto.bcrypt.BCrypt;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    public TextField loginEmail;
    public PasswordField loginPassword;
    public Button loginButton;
    public Label errorLabel;

    private List<String> publishers = new ArrayList<>();
    private List<String> publishers_passwords = new ArrayList<>();
    private List<String> chief_editors = new ArrayList<>();
    private List<String> chief_editors_newspapers = new ArrayList<>();
    private List<String> chief_editors_passwords = new ArrayList<>();
    private List<String> journalists = new ArrayList<>();
    private List<String> journalists_newspapers = new ArrayList<>();
    private List<String> journalists_passwords = new ArrayList<>();
    private List<String> administratives = new ArrayList<>();
    private List<String> administratives_newspapers = new ArrayList<>();
    private List<String> administratives_passwords = new ArrayList<>();


    @FXML
    public void initialize() {
        errorLabel.setText("");

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = Database.getDBConnection();
            connection.setAutoCommit(false);

            /* Publishers */
            String query = "SELECT email, password FROM publisher";
            statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                publishers.add(resultSet.getString("email"));
                publishers_passwords.add(resultSet.getString("password"));
            }

            /* Chief Editors */
            query = "SELECT chief_editor, newspaper.name, password FROM newspaper LEFT JOIN worker ON newspaper.chief_editor = worker.email";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                chief_editors.add(resultSet.getString("chief_editor"));
                chief_editors_newspapers.add(resultSet.getString("name"));
                chief_editors_passwords.add(resultSet.getString("password"));
            }

            /* Journalists */
            query = "SELECT journalist.email, password, newspaper FROM journalist LEFT JOIN works ON journalist.email = works.worker LEFT JOIN worker ON journalist.email = worker.email";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                journalists.add(resultSet.getString("email"));
                journalists_newspapers.add(resultSet.getString("newspaper"));
                journalists_passwords.add(resultSet.getString("password"));
            }

            /* Administrators */
            query = "SELECT administrative.email, password, newspaper FROM administrative LEFT JOIN works ON administrative.email = works.worker LEFT JOIN worker ON administrative.email = worker.email";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                administratives.add(resultSet.getString("email"));
                administratives_newspapers.add(resultSet.getString("newspaper"));
                administratives_passwords.add(resultSet.getString("password"));
            }

            System.out.println(administratives);
            System.out.println(administratives_newspapers);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (App.email != null) {
            loginEmail.setText(App.email);
            loginEmail.end();
        }
    }

    @FXML
    private void switchToJournalistViewController() throws IOException {

        if (publishers.contains(loginEmail.getText())) {
            System.out.println("Publisher");
            App.email = loginEmail.getText();
            int index = publishers.indexOf(loginEmail.getText());

            String input_password = loginPassword.getText();
            BCrypt.Result result = BCrypt.verifyer().verify(input_password.toCharArray(), publishers_passwords.get(index));

            if (result.verified) {
                App.role = App.Role.PUBLISHER;
                App.setRoot("publisher_view");
            } else {
                errorLabel.setText("Wrong Password!!!");
                System.out.println("Wrong Password!!!");
            }

        } else if (chief_editors.contains(loginEmail.getText())) {
            System.out.println("Chief Editor");
            App.email = loginEmail.getText();
            int index = chief_editors.indexOf(loginEmail.getText());
            App.newspaper = chief_editors_newspapers.get(index);

            String input_password = loginPassword.getText();
            BCrypt.Result result = BCrypt.verifyer().verify(input_password.toCharArray(), chief_editors_passwords.get(index));

            if (result.verified) {
                System.out.println(App.email);
                App.role = App.Role.CHIEF;
                System.out.println(App.newspaper);
                App.setRoot("journalist_view");
            } else {
                errorLabel.setText("Wrong Password!!!");
                System.out.println("Wrong Password!!!");
            }

        } else if (journalists.contains(loginEmail.getText())) {
            System.out.println("Journalist");
            App.email = loginEmail.getText();
            int index = journalists.indexOf(loginEmail.getText());
            App.newspaper = journalists_newspapers.get(index);

            String input_password = loginPassword.getText();
            BCrypt.Result result = BCrypt.verifyer().verify(input_password.toCharArray(), journalists_passwords.get(index));

            if (result.verified) {
                System.out.println(App.email);
                App.role = App.Role.JOURNALIST;
                System.out.println(App.newspaper);
                App.setRoot("journalist_view");
            } else {
                errorLabel.setText("Wrong Password!!!");
                System.out.println("Wrong Password!!!");
            }

        } else if (administratives.contains(loginEmail.getText())) {
            System.out.println("Administrative");
            App.email = loginEmail.getText();
            int index = administratives.indexOf(loginEmail.getText());
            App.newspaper = administratives_newspapers.get(index);

            String input_password = loginPassword.getText();
            BCrypt.Result result = BCrypt.verifyer().verify(input_password.toCharArray(), administratives_passwords.get(index));
            if (result.verified) {
                System.out.println(App.email);
                App.role = App.Role.ADMIN;
                System.out.println(App.newspaper);
                App.setRoot("administrative_view");
            } else {
                errorLabel.setText("Wrong Password!!!");
                System.out.println("Wrong Password!!!");
            }

        } else {
            errorLabel.setText("Account not found!");
            System.out.println("Account not found!");
        }

    }

}