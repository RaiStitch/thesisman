module pt.ul.fc.di.css.javafxexample {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
	requires javafx.graphics;

    opens pt.ul.fc.di.css.thesisman to javafx.fxml, javafx.web;
    opens pt.ul.fc.di.css.thesisman.presentation.control to javafx.fxml;
    exports pt.ul.fc.di.css.thesisman;
}
