package petproject.homework1;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.util.Duration;

import javax.print.attribute.standard.Media;
import java.io.File;
import java.util.List;
import java.util.Random;

public class MainController {

    private ObservableList<String> trackList = FXCollections.observableArrayList();
    private MediaPlayer mediaPlayer;
    private List<File> audioFiles;
    private int currentIndex = 0;
    private boolean isRepeat = false;
    private boolean isShuffle = false;

    private ListView<String> listView;
    private Slider progressSlider;
    private Label trackLabel;
    private Label timeLabel;

    public void setListView(ListView<String> listView) {
        this.listView = listView;
        listView.setItems(trackList);
        listView.setOnMouseClicked(e -> {
            currentIndex = listView.getSelectionModel().getSelectedIndex();
            play();
        });
    }

    public void setProgressSlider(Slider slider) {
        this.progressSlider = slider;
    }

    public void setTrackLabel(Label label) {
        this.trackLabel = label;
    }

    public void setTimeLabel(Label label) {
        this.timeLabel = label;
    }

    public void openDirectory() {
        File dir = FileUtils.chooseDirectory();
        if (dir != null) {
            audioFiles = FileUtils.getAudioFiles(dir);
            trackList.clear();
            for (File file : audioFiles) {
                trackList.add(FileUtils.getFileName(file.getName()));
            }
        }
    }

    private void initPlayer(File file) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        Media media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);

        mediaPlayer.setOnReady(() -> {
            trackLabel.setText("Трек: " + FileUtils.getFileName(file.getName()));
            timeLabel.setText("00:00 / " + FileUtils.formatTime(media.getDuration()));
            progressSlider.setMax(media.getDuration().toSeconds());
        });

        mediaPlayer.currentTimeProperty().addListener((obs, oldTime, newTime) -> updateProgress());

        mediaPlayer.setOnEndOfMedia(() -> {
            if (isRepeat) {
                play();
            } else {
                next();
            }
        });
    }

    public void play() {
        if (audioFiles == null || audioFiles.isEmpty()) return;
        initPlayer(audioFiles.get(currentIndex));
        mediaPlayer.play();
        highlightCurrentTrack();
    }

    public void pause() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    public void next() {
        if (audioFiles == null || audioFiles.isEmpty()) return;
        if (isShuffle) {
            Random random = new Random();
            currentIndex = random.nextInt(audioFiles.size());
        } else {
            currentIndex = (currentIndex + 1) % audioFiles.size();
        }
        play();
    }

    public void previous() {
        if (audioFiles == null || audioFiles.isEmpty()) return;
        currentIndex = (currentIndex - 1 + audioFiles.size()) % audioFiles.size();
        play();
    }

    private void updateProgress() {
        if (mediaPlayer == null) return;
        Duration currentTime = mediaPlayer.getCurrentTime();
        progressSlider.setValue(currentTime.toSeconds());
        timeLabel.setText(FileUtils.formatTime(currentTime) + " / " + FileUtils.formatTime(mediaPlayer.getMedia().getDuration()));
    }

    public void seek() {
        if (mediaPlayer != null) {
            mediaPlayer.seek(Duration.seconds(progressSlider.getValue()));
        }
    }

    private void highlightCurrentTrack() {
        listView.getSelectionModel().select(currentIndex);
    }

    // Методы для управления режимами
    public void toggleRepeat() {
        isRepeat = !isRepeat;
    }

    public void toggleShuffle() {
        isShuffle = !isShuffle;
    }
}
