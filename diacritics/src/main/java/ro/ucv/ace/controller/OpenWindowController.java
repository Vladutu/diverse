package ro.ucv.ace.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.Normalizer;
import java.util.ResourceBundle;

/**
 * Created by Geo on 02.03.2017.
 */
public class OpenWindowController implements Initializable {

    @FXML
    private Label resultLabel;

    @FXML
    private Button openFileButton;

    @FXML
    public Button removeDiacriticsButton;

    @FXML
    public TextField pathTextField;

    private File subtitle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        removeDiacriticsButton.setDisable(true);
    }

    @FXML
    private void onChooseDiacriticsButtonClick(ActionEvent actionEvent) {

    }

    @FXML
    private void onOpenFileButtonClick(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();
        Window window = node.getScene().getWindow();

        openFile(window);
    }

    @FXML
    private void onRemoveDiacriticsButtonClick(ActionEvent actionEvent) {
        removeDiacritics();
    }

    private void removeDiacritics() {
        try {
            String content = FileUtils.readFileToString(subtitle, "ISO-8859-2");
            String path = subtitle.getAbsolutePath();

            content = Normalizer.normalize(content, Normalizer.Form.NFD);
            content = content.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");

            FileUtils.writeStringToFile(subtitle, content);

            resultLabel.setText("Diacritics removed successfully!");
            subtitle = null;
            removeDiacriticsButton.setDisable(true);
            pathTextField.setText("");
            resultLabel.requestFocus();
        } catch (IOException e) {
            displayErrorMessage();
        }
    }

    private void displayErrorMessage() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid file");
        alert.setContentText("An error occurred! Please try again!");

        alert.showAndWait();
    }


    private void openFile(Window window) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Sub files (*.srt, *.sub, *.sbv)",
                "*.srt", "*.sub", "*.sbv");
        fileChooser.getExtensionFilters().add(extensionFilter);
        subtitle = fileChooser.showOpenDialog(window);

        if (subtitle != null) {
            removeDiacriticsButton.setDisable(false);
            pathTextField.setText(subtitle.getAbsolutePath());
        } else {
            removeDiacriticsButton.setDisable(true);
            pathTextField.setText("");
        }
        resultLabel.setText("");
    }
}
