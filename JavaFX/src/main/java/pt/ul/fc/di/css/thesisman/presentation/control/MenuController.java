package pt.ul.fc.di.css.thesisman.presentation.control;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.stage.FileChooser;
import pt.ul.fc.di.css.thesisman.presentation.model.DataModel;

public class MenuController {

    private DataModel model;

    @FXML
    private MenuBar menuBar;

    public void initModel(DataModel model) {
        if (this.model != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }
        this.model = model;
    }

    @FXML
    public void load() {
        FileChooser chooser = new FileChooser();
        File file = chooser.showOpenDialog(menuBar.getScene().getWindow());
        if (file != null) {
            try {
                model.loadData(file);
            } catch (Exception exc) {
                // handle exception...
            }
        }
    }

    @FXML
    public void save() {

        // similar to load...

    }
    
    @FXML
    public void exit() {
        menuBar.getScene().getWindow().hide();
    }
}
