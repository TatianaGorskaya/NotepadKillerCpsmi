package NotepadKiller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;

import java.awt.*;
import java.io.File;
import java.util.Arrays;

public class NotepadController {
    @FXML
    private TextArea areaText;
    private TextFile currentTextFile;
    private NotepadModel model;

    public NotepadController(NotepadModel model) {
        this.model = model;
    }

    @FXML
    private void onSave() {
        TextFile textFile = new TextFile(currentTextFile.getFile(), Arrays.asList(areaText.getText().split("\n")));
        model.save(textFile);
    }

    @FXML
    private void onLoad() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("./"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            IOResult<TextFile> io = model.load(file.toPath());

            if (io.isOk() && io.hasData()) {
                currentTextFile = io.getData();

                areaText.clear();
                currentTextFile.getContent().forEach(line -> areaText.appendText(line +"\n"));
            } else {
                System.out.println("Ошибка");
            }
        }
    }

    @FXML
    private void onClose() {
        model.close();

    }

    @FXML
    private void onAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("About");
        alert.setContentText("Такой себе блокнот");
        alert.show();

    }
}
