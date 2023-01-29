@SuppressWarnings("JavaModuleNaming")
module com.example.a7 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.a7 to javafx.fxml;
    opens com.a7.gui to javafx.fxml;
    exports com.a7;
    exports com.a7.gui;
}