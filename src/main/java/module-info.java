@SuppressWarnings("JavaModuleNaming")
module com.a7 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.a7 to javafx.fxml;
    opens com.a7.gui to javafx.fxml;
    exports com.a7;
    exports com.a7.gui;

    exports com.a7.controller;
    exports com.a7.model.exceptions;
    exports com.a7.model.expressions;
    exports com.a7.model.garbageCollector;
    exports com.a7.model.programState;
    exports com.a7.model.statements;
    exports com.a7.model.types;
    exports com.a7.model.utility;
    exports com.a7.model.values;
    exports com.a7.repository;
}