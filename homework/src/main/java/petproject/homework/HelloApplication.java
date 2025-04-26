package petproject.homework1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) {
        MainController controller = new MainController();

        // Кнопки управления
        Button openFolderButton = new Button("Открыть папку");
        Button playButton = new Button("Play");
        Button pauseButton = new Button("Pause");
        Button stopButton = new Button("Stop");
        Button nextButton = new Button("Next");
        Button previousButton = new Button("Previous");

        // Список треков
        ListView<String> listView = new ListView<>();
        controller.setListView(listView);

        // Слайдер и метки времени
        Slider progressSlider = new Slider();
        Label trackLabel = new Label("Трек: ");
        Label timeLabel = new Label("00:00 / 00:00");

        controller.setProgressSlider(progressSlider);
        controller.setTrackLabel(trackLabel);
        controller.setTimeLabel(timeLabel);

        // Связываем кнопки с действиями
        openFolderButton.setOnAction(e -> controller.openDirectory());
        playButton.setOnAction(e -> controller.play());
        pauseButton.setOnAction(e -> controller.pause());
        stopButton.setOnAction(e -> controller.stop());
        nextButton.setOnAction(e -> controller.next());
        previousButton.setOnAction(e -> controller.previous());

        progressSlider.setOnMouseReleased(e -> controller.seek());

        // Компоновка
        HBox controls = new HBox(5, playButton, pauseButton, stopButton, previousButton, nextButton);
        VBox leftPanel = new VBox(10, openFolderButton, listView);
        VBox bottomPanel = new VBox(5, trackLabel, progressSlider, timeLabel);

        BorderPane root = new BorderPane();
        root.setLeft(leftPanel);
        root.setCenter(controls);
        root.setBottom(bottomPanel);

        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("JavaFX Аудио-Плеер");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}