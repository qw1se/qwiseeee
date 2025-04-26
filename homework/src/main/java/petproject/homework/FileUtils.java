package petproject.homework1;

import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    public static File chooseDirectory() {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Выберите папку с аудиофайлами");
        return chooser.showDialog(new Stage());
    }

    public static List<File> getAudioFiles(File directory) {
        List<File> audioFiles = new ArrayList<>();
        if (directory != null && directory.isDirectory()) {
            for (File file : directory.listFiles()) {
                if (file.getName().endsWith(".mp3") || file.getName().endsWith(".wav")) {
                    audioFiles.add(file);
                }
            }
        }
        return audioFiles;
    }

    public static String formatTime(Duration duration) {
        int minutes = (int) duration.toMinutes();
        int seconds = (int) (duration.toSeconds() % 60);
        return String.format("%02d:%02d", minutes, seconds);
    }

    public static String getFileName(String path) {
        return path.replaceAll("\\.(mp3|wav)$", "");
    }
}
