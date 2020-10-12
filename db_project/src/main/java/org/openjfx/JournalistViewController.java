package org.openjfx;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;


public class JournalistViewController {

    public Label introductionLabel;

    public TabPane tabPanel;

    public TableView<Article> articlesTable; // tab 1
    public GridPane editGrid;  // tab 2

    // tab 2 elements
    public TextField pathText;
    public TextField titleText;
    public TextField descriptionText;
    public ComboBox<Integer> paperChoice;
    public ComboBox<String> categoryChoice;
    public TextField numOfPagesText;
    public Button submitButton;
    public Label errorTextArticle;

    // tab 3
    public ComboBox<String> categoriesCombo;
    public TextField newCategoryText;
    public TextField newCategoryDescriptionText;
    public Label errorTextCategory;
    public Label statusLabel;
    public TextField commentsText;


    java.util.Date dt = new java.util.Date();
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");

    private String currentDate = sdf.format(dt);
    private String current_path = null;


    @FXML
    public void initialize() throws SQLException {
        if (App.role == App.Role.CHIEF)
            introductionLabel.setText(App.email + " - Chief Editor @ " + App.newspaper);
        else
            introductionLabel.setText(App.email + " - Journalist @ " + App.newspaper);

        TableColumn<Article, Control> path_column = new TableColumn<>("Path");
        path_column.setCellValueFactory(new PropertyValueFactory<>("path"));

        TableColumn<Article, String> title_column = new TableColumn<>("Title");
        title_column.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Article, String> description_column = new TableColumn<>("Description");
        description_column.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Article, Integer> paper_column = new TableColumn<>("Paper");
        paper_column.setCellValueFactory(new PropertyValueFactory<>("paper"));

        TableColumn<Article, String> category_column = new TableColumn<>("Category");
        category_column.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<Article, String> check_date_column = new TableColumn<>("Check Date");
        check_date_column.setCellValueFactory(new PropertyValueFactory<>("check_date"));

        TableColumn<Article, Integer> number_of_pages_column = new TableColumn<>("Number of pages");
        number_of_pages_column.setCellValueFactory(new PropertyValueFactory<>("number_of_pages"));

        TableColumn<Article, Integer> order_in_paper_column = new TableColumn<>("Order in paper");
        order_in_paper_column.setCellValueFactory(new PropertyValueFactory<>("order_in_paper"));

        TableColumn<Article, String> state_column = new TableColumn<>("State");
        state_column.setCellValueFactory(new PropertyValueFactory<>("state"));

        TableColumn<Article, TextField> comments_column = new TableColumn<>("Comments");
        comments_column.setCellValueFactory(new PropertyValueFactory<>("comments"));

        articlesTable.getColumns().add(path_column);
        articlesTable.getColumns().add(title_column);
        articlesTable.getColumns().add(description_column);
        articlesTable.getColumns().add(category_column);
        articlesTable.getColumns().add(check_date_column);
        articlesTable.getColumns().add(paper_column);
        articlesTable.getColumns().add(number_of_pages_column);
        articlesTable.getColumns().add(order_in_paper_column);
        articlesTable.getColumns().add(state_column);


        if(App.role == App.Role.JOURNALIST) {
            tabPanel.getTabs().remove(1);

            TableColumn<Article, Button> update_column = new TableColumn<>("Update");
            update_column.setCellValueFactory(new PropertyValueFactory<>("update_button"));
            articlesTable.getColumns().add(update_column);
            articlesTable.getColumns().add(comments_column);

        } else {
            TableColumn<Article, Button> accept_column = new TableColumn<>("Accept");
            accept_column.setCellValueFactory(new PropertyValueFactory<>("accept_button"));

            TableColumn<Article, Button> reject_column = new TableColumn<>("Reject");
            reject_column.setCellValueFactory(new PropertyValueFactory<>("reject_button"));

            TableColumn<Article, Button> revise_column = new TableColumn<>("Revise");
            revise_column.setCellValueFactory(new PropertyValueFactory<>("revise_button"));

            TableColumn<Article, Button> minus_column = new TableColumn<>("Minus");
            minus_column.setCellValueFactory(new PropertyValueFactory<>("minus_button"));

            TableColumn<Article, Button> plus_column = new TableColumn<>("Plus");
            plus_column.setCellValueFactory(new PropertyValueFactory<>("plus_button"));

            articlesTable.getColumns().add(accept_column);
            articlesTable.getColumns().add(reject_column);
            articlesTable.getColumns().add(revise_column);
            articlesTable.getColumns().add(comments_column);
            articlesTable.getColumns().add(minus_column);
            articlesTable.getColumns().add(plus_column);
        }

        populateTab1();
        populateTab2();
        populateTab3();
    }

    @FXML
    private void switchToLogin() throws IOException {
        App.setRoot("login");
    }

    public void populateTab1() throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        List<Article> articlesList = new ArrayList<>();

        try {
            connection = Database.getDBConnection();
            connection.setAutoCommit(false);

            if (App.role == App.Role.CHIEF) {
                String query = "SELECT * FROM article WHERE article.newspaper = ? ORDER BY paper, state, order_in_paper";
                statement = connection.prepareStatement(query);
                statement.setString(1, App.newspaper);
            }
            else {
                String query = "SELECT * FROM article RIGHT JOIN submission ON article.path = submission.article WHERE submission.journalist = ?";
                statement = connection.prepareStatement(query);
                statement.setString(1, App.email);
            }
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Article article = new Article();
                article.setPath(resultSet.getString("path"));
                article.setTitle(resultSet.getString("title"));
                article.setDescription(resultSet.getString("description"));
                article.setState(resultSet.getString("state"));
                article.setPaper(resultSet.getInt("paper"));
                article.setCategory(resultSet.getString("category"));
                article.setCheck_date(resultSet.getString("check_date"));
                article.setNumber_of_pages(resultSet.getInt("num_of_pages"));
                article.setOrder_in_paper(resultSet.getInt("order_in_paper"));
                article.setComments(new TextField(resultSet.getString("comments")));

                if (App.role == App.Role.JOURNALIST) {
                    Button updateButton = new Button("Update");
                    updateButton.setOnAction((event) -> {
                        current_path = article.getPath();
                        tabPanel.getSelectionModel().select(1);

                        pathText.setText(article.getPath());
                        titleText.setText(article.getTitle());
                        descriptionText.setText(article.getDescription());
                        paperChoice.setValue(article.getPaper());
                        categoryChoice.setValue(article.getCategory());
                        numOfPagesText.setText(Integer.toString(article.getNumber_of_pages()));
                        commentsText.setText(article.getComments().getText());
                        statusLabel.setText("Update Article");
                        pathText.setDisable(true);
                    });
                    article.setUpdate_button(updateButton);
                    article.getComments().setDisable(true);
                } else if (App.role == App.Role.CHIEF) {
                    Button acceptButton = new Button("Accept");
                    acceptButton.setOnAction((event) -> {
                        try {
                            setArticleState(article, "ACCEPTED");
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    });
                    article.setAccept_button(acceptButton);

                    Button rejectButton = new Button("Reject");
                    rejectButton.setOnAction((event) -> {
                        try {
                            setArticleState(article, "REJECTED");
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    });
                    article.setReject_button(rejectButton);

                    Button reviseButton = new Button("Revise");
                    reviseButton.setOnAction((event) -> {
                        try {
                            setArticleState(article, "CHANGES_NEEDED");
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    });
                    article.setRevise_button(reviseButton);
                    Button plusButton = new Button("+");
                    article.setPlus_button(plusButton);
                    Button minusButton = new Button("-");
                    article.setMinus_button(minusButton);

                    if (!article.getState().equals("INITIAL"))
                        article.getComments().setDisable(true);
                }
                articlesList.add(article);
            }


            for (int i=0;i<articlesList.size();i++) {

                if (App.role == App.Role.CHIEF) {
                    if (!articlesList.get(i).getState().equals("ACCEPTED")) {
                        // DISABLE BOTH BUTTONS
                        articlesList.get(i).getMinus_button().setDisable(true);
                        articlesList.get(i).getPlus_button().setDisable(true);
                    } else {

                        if (articlesList.get(i).getOrder_in_paper() == null || articlesList.get(i).getOrder_in_paper() == 1) {
                            articlesList.get(i).getMinus_button().setDisable(true);
                        }

                        if (i + 1 >= articlesList.size() || articlesList.get(i + 1).getOrder_in_paper() == null || articlesList.get(i + 1).getOrder_in_paper() == 1) {
                            articlesList.get(i).getPlus_button().setDisable(true);
                        }

                        if (!articlesList.get(i).getPlus_button().isDisabled()) {
                            String current_path = articlesList.get(i).getPath();
                            String next_path = articlesList.get(i + 1).getPath();

                            articlesList.get(i).getPlus_button().setOnAction((event) -> {
                                try {
                                    increaseOrderInPaper(current_path, next_path);
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                articlesTable.getItems().clear();
                                try {
                                    populateTab1();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            });
                        }

                        if (!articlesList.get(i).getMinus_button().isDisabled()) {
                            String previous_path = articlesList.get(i - 1).getPath();
                            String current_path = articlesList.get(i).getPath();

                            articlesList.get(i).getMinus_button().setOnAction((event) -> {
                                try {
                                    increaseOrderInPaper(previous_path, current_path);
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                articlesTable.getItems().clear();
                                try {
                                    populateTab1();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            });
                        }
                    }
                }

                articlesTable.getItems().add(articlesList.get(i));
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
    }

    public void populateTab2 () throws SQLException {
        for(Integer x : getPapers())
            paperChoice.getItems().add(x);
        for(String x : getCategories())
            categoryChoice.getItems().add(x);
    }

    public void populateTab3() throws SQLException {
        newCategoryText.setText("");
        newCategoryDescriptionText.setText("");

        List<String> categories = getCategories();

        categoriesCombo.getItems().clear();
        for (String c : categories)
            categoriesCombo.getItems().add(c);

        categoryChoice.getItems().clear();
        for (String c : categories)
            categoryChoice.getItems().add(c);
    }

    public void clearEditFields() {
        current_path = null;
        statusLabel.setText("Insert Article");

        pathText.setText("");
        pathText.setDisable(false);

        titleText.setText("");
        descriptionText.setText("");
        paperChoice.valueProperty().set(null);
        categoryChoice.valueProperty().set(null);
        numOfPagesText.setText("");
        commentsText.setText("");
        errorTextArticle.setText("");
        errorTextCategory.setText("");
    }

    public List<String> getCategories() throws SQLException {

        List<String> categoriesList = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = Database.getDBConnection();
            connection.setAutoCommit(false);
            String query = "SELECT name FROM category";
            statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                categoriesList.add(resultSet.getString("name"));
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

        return categoriesList;
    }

    public List<Integer> getPapers() throws SQLException {

        ArrayList<Integer> newspaper_papers = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = Database.getDBConnection();
            connection.setAutoCommit(false);
            String query = "SELECT id FROM paper WHERE newspaper = ? AND publish_date > ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, App.newspaper);
            statement.setString(2, currentDate);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                newspaper_papers.add(resultSet.getInt("id"));
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

        return newspaper_papers;
    }

    public void setArticleState(Article article, String state) throws SQLException {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = Database.getDBConnection();
            connection.setAutoCommit(false);

            String query = "UPDATE article SET state = ?, comments = ? WHERE path = ?";
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, state);
            if (App.role == App.Role.CHIEF)
                statement.setString(2, article.getComments().getText());
            else if (App.role == App.Role.JOURNALIST)
                statement.setNull(2, Types.VARCHAR);
            statement.setString(3, article.getPath());

            statement.executeUpdate();
            connection.commit();

            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                System.out.println(resultSet.getInt(1));
            }

            articlesTable.getItems().clear();
            populateTab1();

        } catch (Exception exception) {
            exception.printStackTrace();
            errorTextArticle.setText(exception.getMessage());
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
    public void onButtonClick() throws SQLException {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        if(current_path == null) {  // INSERT
            try {
                connection = Database.getDBConnection();
                connection.setAutoCommit(false);
                String query = "INSERT INTO article (path, paper, title, description, category, num_of_pages, comments, newspaper) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                int counter = 1;
                statement.setString(counter++, pathText.getText());
                statement.setInt(counter++, paperChoice.getValue());
                statement.setString(counter++, titleText.getText());
                statement.setString(counter++, descriptionText.getText());
                statement.setString(counter++, categoryChoice.getValue());
                statement.setInt(counter++, Integer.parseInt(numOfPagesText.getText()));
                statement.setString(counter++, commentsText.getText());
                statement.setString(counter, App.newspaper);

                statement.executeUpdate();
                connection.commit();

                resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    System.out.println(resultSet.getInt(1));
                }

                query = "INSERT INTO submission (journalist, article, date) VALUES (?, ?, ?)";
                statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                counter = 1;
                statement.setString(counter++, App.email);
                statement.setString(counter++, pathText.getText());
                statement.setString(counter, currentDate);

                statement.executeUpdate();
                connection.commit();

                resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    System.out.println(resultSet.getInt(1));
                }

                articlesTable.getItems().clear();
                populateTab1();

                tabPanel.getSelectionModel().select(0);
                clearEditFields();

            } catch (Exception exception) {
                System.out.println(exception.getMessage());
                errorTextArticle.setText(exception.getMessage());
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

        } else {    // UPDATE (SET path = current_path)
            try {
                connection = Database.getDBConnection();
                connection.setAutoCommit(false);

                String query = "UPDATE article SET paper = ?, title = ?, description = ?, category = ?, num_of_pages = ?, state = ?, comments = ? WHERE path = ?";
                statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                int counter = 1;
                statement.setInt(counter++, paperChoice.getValue());
                statement.setString(counter++, titleText.getText());
                statement.setString(counter++, descriptionText.getText());
                statement.setString(counter++, categoryChoice.getValue());
                statement.setInt(counter++, Integer.parseInt(numOfPagesText.getText()));
                statement.setString(counter++, "INITIAL");
                statement.setString(counter++, commentsText.getText());
                statement.setString(counter, current_path);

                statement.executeUpdate();
                connection.commit();

                resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    System.out.println(resultSet.getInt(1));
                }

                query = "UPDATE submission SET date = ? WHERE journalist = ? AND article = ?";
                statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                counter = 1;
                statement.setString(counter++, currentDate);
                statement.setString(counter++, App.email);
                statement.setString(counter, current_path);

                statement.executeUpdate();
                connection.commit();

                resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    System.out.println(resultSet.getInt(1));
                }

                articlesTable.getItems().clear();
                populateTab1();
                tabPanel.getSelectionModel().select(0);
                clearEditFields();

            } catch (Exception exception) {
                System.out.println(exception.getMessage());
                errorTextArticle.setText(exception.getMessage());
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

    }

    private void increaseOrderInPaper(String increase_path, String decrease_path) throws SQLException {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = Database.getDBConnection();
            connection.setAutoCommit(false);

            String query = "UPDATE article SET order_in_paper = order_in_paper+1 WHERE path = ?";
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, increase_path);

            statement.executeUpdate();
            connection.commit();

            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                System.out.println(resultSet.getInt(1));
            }

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            errorTextArticle.setText(exception.getMessage());
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

        connection = null;
        statement = null;
        resultSet = null;

        try {
            connection = Database.getDBConnection();
            connection.setAutoCommit(false);

            String query = "UPDATE article SET order_in_paper = order_in_paper-1 WHERE path = ?";
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, decrease_path);

            statement.executeUpdate();
            connection.commit();

            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                System.out.println(resultSet.getInt(1));
            }

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            errorTextArticle.setText(exception.getMessage());
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
    public void insertCategory() throws SQLException {
        String newCategory = newCategoryText.getText();
        String parentCategory = categoriesCombo.getValue();
        String description = newCategoryDescriptionText.getText();

        if (newCategory == null) {
            errorTextCategory.setText("EMPTY CATEGORY!");
        } else {

            Connection connection = null;
            PreparedStatement statement = null;
            ResultSet resultSet = null;

                try {
                    connection = Database.getDBConnection();
                    connection.setAutoCommit(false);
                    String query = "INSERT INTO category (name, description, my_category) VALUES (?, ?, ?)";
                    statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                    int counter = 1;
                    statement.setString(counter++, newCategory);
                    statement.setString(counter++, description);

                    if (parentCategory != null)
                        statement.setString(counter, parentCategory);
                    else
                        statement.setNull(counter, Types.VARCHAR);

                    statement.executeUpdate();
                    connection.commit();

                    resultSet = statement.getGeneratedKeys();
                    if (resultSet.next()) {
                        System.out.println(resultSet.getInt(1));
                    }

                    populateTab3();

                } catch (Exception exception) {
                    System.out.println(exception.getMessage());
                    errorTextArticle.setText(exception.getMessage());
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
    }
}
