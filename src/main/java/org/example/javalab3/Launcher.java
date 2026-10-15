
package org.example.javalab3;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.javalab3.controller.BookEditDialogController;
import org.example.javalab3.controller.BookOverviewController;
import org.example.javalab3.model.Author;
import org.example.javalab3.model.Book;

import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

public class Launcher extends Application {

    private Stage primaryStage;

    private final ObservableList<Book> bookData =
            FXCollections.observableArrayList();

    private File currentFile;

    private final Preferences preferences =
            Preferences.userNodeForPackage(Launcher.class);

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;

        boolean loaded = loadLastFile();

        if (!loaded) {
            createBooks();
        }

        FXMLLoader loader = new FXMLLoader(
                Launcher.class.getResource(
                        "/org/example/javalab3/BookOverview.fxml"
                )
        );

        AnchorPane root = loader.load();

        BookOverviewController controller = loader.getController();
        controller.setMainApp(this);

        BorderPane mainRoot = new BorderPane();
        mainRoot.setTop(createMenuBar());
        mainRoot.setCenter(root);

        Scene scene = new Scene(mainRoot);

        primaryStage.setTitle("Электронный каталог книг");

        var iconStream = Launcher.class.getResourceAsStream(
                "/org/example/javalab3/images/book-icon.png"
        );

        if (iconStream != null) {
            primaryStage.getIcons().add(new Image(iconStream));
        }

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void createBooks() {
        Author dostoevsky = new Author(
                "Фёдор Михайлович Достоевский",
                "+7 (900) 111-11-11", "dostoevsky@mail.ru", 4.9);

        Author tolstoy = new Author(
                "Лев Николаевич Толстой",
                "+7 (900) 222-22-22", "tolstoy@mail.ru", 5.0);

        Author pushkin = new Author(
                "Александр Сергеевич Пушкин",
                "+7 (900) 333-33-33", "pushkin@mail.ru", 4.9);

        Author bulgakov = new Author(
                "Михаил Афанасьевич Булгаков",
                "+7 (900) 444-44-44", "bulgakov@mail.ru", 4.8);

        Author chekhov = new Author(
                "Антон Павлович Чехов",
                "+7 (900) 555-55-55", "chekhov@mail.ru", 4.8);

        Author gogol = new Author(
                "Николай Васильевич Гоголь",
                "+7 (900) 666-66-66", "gogol@mail.ru", 4.7);

        Author turgenev = new Author(
                "Иван Сергеевич Тургенев",
                "+7 (900) 777-77-77", "turgenev@mail.ru", 4.7);

        Author remark = new Author(
                "Эрих Мария Ремарк",
                "+7 (900) 888-88-88", "remark@mail.ru", 4.9);

        Author rowling = new Author(
                "Джоан Роулинг",
                "+7 (900) 999-99-99", "rowling@mail.ru", 4.8);

        Author king = new Author(
                "Стивен Кинг",
                "+7 (901) 111-11-11", "king@mail.ru", 4.9);

        Author hemingway = new Author(
                "Эрнест Хемингуэй",
                "+7 (901) 222-22-22", "hemingway@mail.ru", 4.8);

        Author martin = new Author(
                "Джордж Мартин",
                "+7 (901) 333-33-33", "martin@mail.ru", 4.7);

        bookData.setAll(
                new Book("Преступление и наказание", "Твёрдый",
                        "Эксмо", 2020, "Роман", dostoevsky),
                new Book("Идиот", "Твёрдый",
                        "АСТ", 2019, "Роман", dostoevsky),
                new Book("Война и мир", "Твёрдый",
                        "Азбука", 2021, "Роман", tolstoy),
                new Book("Анна Каренина", "Мягкий",
                        "Эксмо", 2020, "Роман", tolstoy),
                new Book("Евгений Онегин", "Твёрдый",
                        "Азбука", 2018, "Роман", pushkin),
                new Book("Мастер и Маргарита", "Твёрдый",
                        "АСТ", 2022, "Роман", bulgakov),
                new Book("Вишнёвый сад", "Мягкий",
                        "Эксмо", 2017, "Пьеса", chekhov),
                new Book("Мёртвые души", "Твёрдый",
                        "Азбука", 2021, "Сатира", gogol),
                new Book("Отцы и дети", "Мягкий",
                        "АСТ", 2019, "Роман", turgenev),
                new Book("Три товарища", "Твёрдый",
                        "Эксмо", 2020, "Роман", remark),
                new Book("Гарри Поттер и философский камень", "Твёрдый",
                        "Махаон", 2021, "Фэнтези", rowling),
                new Book("Зелёная миля", "Твёрдый",
                        "АСТ", 2022, "Фантастика", king),
                new Book("Старик и море", "Мягкий",
                        "АСТ", 2018, "Повесть", hemingway),
                new Book("Игра престолов", "Твёрдый",
                        "АСТ", 2020, "Фэнтези", martin)
        );
    }

    private MenuBar createMenuBar() {
        Menu fileMenu = new Menu("Файл");

        MenuItem newItem = new MenuItem("Новый каталог");
        newItem.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
        newItem.setOnAction(event -> newCatalog());

        MenuItem openItem = new MenuItem("Открыть...");
        openItem.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));
        openItem.setOnAction(event -> openFile());

        MenuItem saveItem = new MenuItem("Сохранить");
        saveItem.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
        saveItem.setOnAction(event -> saveFile());

        MenuItem saveAsItem = new MenuItem("Сохранить как...");
        saveAsItem.setOnAction(event -> saveFileAs());

        MenuItem exitItem = new MenuItem("Выход");
        exitItem.setOnAction(event -> primaryStage.close());

        fileMenu.getItems().addAll(
                newItem,
                new SeparatorMenuItem(),
                openItem,
                saveItem,
                saveAsItem,
                new SeparatorMenuItem(),
                exitItem
        );

        return new MenuBar(fileMenu);
    }

    private void newCatalog() {
        bookData.clear();
        currentFile = null;
        preferences.remove("lastFilePath");
    }

    private void openFile() {
        FileChooser chooser = createFileChooser("Открыть XML-каталог");
        File file = chooser.showOpenDialog(primaryStage);

        if (file == null) {
            return;
        }

        try {
            var books = BookXmlStorage.load(file);
            bookData.setAll(books);
            currentFile = file;
            rememberFile(file);
        } catch (Exception ex) {
            showFileError("Не удалось открыть XML-файл", ex);
        }
    }

    private void saveFile() {
        if (currentFile == null) {
            saveFileAs();
            return;
        }

        try {
            BookXmlStorage.save(bookData, currentFile);
            rememberFile(currentFile);
        } catch (Exception ex) {
            showFileError("Не удалось сохранить XML-файл", ex);
        }
    }

    private void saveFileAs() {
        FileChooser chooser = createFileChooser("Сохранить XML-каталог");
        chooser.setInitialFileName("books.xml");

        File file = chooser.showSaveDialog(primaryStage);

        if (file == null) {
            return;
        }

        if (!file.getName().toLowerCase().endsWith(".xml")) {
            file = new File(file.getAbsolutePath() + ".xml");
        }

        try {
            BookXmlStorage.save(bookData, file);
            currentFile = file;
            rememberFile(file);
        } catch (Exception ex) {
            showFileError("Не удалось сохранить XML-файл", ex);
        }
    }

    private FileChooser createFileChooser(String title) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle(title);

        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(
                        "XML-файлы (*.xml)", "*.xml")
        );

        if (currentFile != null
                && currentFile.getParentFile() != null
                && currentFile.getParentFile().isDirectory()) {
            chooser.setInitialDirectory(currentFile.getParentFile());
        }

        return chooser;
    }

    private void rememberFile(File file) {
        preferences.put("lastFilePath", file.getAbsolutePath());
    }

    private boolean loadLastFile() {
        String path = preferences.get("lastFilePath", null);

        if (path == null) {
            return false;
        }

        File file = new File(path);

        if (!file.isFile()) {
            return false;
        }

        try {
            bookData.setAll(BookXmlStorage.load(file));
            currentFile = file;
            return true;
        } catch (Exception ex) {
            showFileError("Не удалось загрузить последний XML-файл", ex);
            return false;
        }
    }

    private void showFileError(String message, Exception ex) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(primaryStage);
        alert.setTitle("Ошибка");
        alert.setHeaderText(message);
        alert.setContentText(
                ex.getMessage() == null ? ex.toString() : ex.getMessage()
        );
        alert.showAndWait();
    }

    public ObservableList<Book> getBookData() {
        return bookData;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public boolean showBookEditDialog(Book book) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    Launcher.class.getResource(
                            "/org/example/javalab3/BookEditDialog.fxml"
                    )
            );

            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle(
                    book == null ? "Добавление книги" : "Изменение книги"
            );

            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);

            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            var iconStream = Launcher.class.getResourceAsStream(
                    "/org/example/javalab3/images/book-icon.png"
            );

            if (iconStream != null) {
                dialogStage.getIcons().add(new Image(iconStream));
            }

            BookEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            if (book != null) {
                controller.setBook(book);
            }

            dialogStage.showAndWait();
            return controller.isOkClicked();

        } catch (IOException ex) {
            showFileError("Не удалось открыть окно редактирования", ex);
            return false;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
