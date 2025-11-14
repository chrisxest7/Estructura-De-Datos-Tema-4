module com.crissystems.grafo {
    requires javafxcontrols;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.crissystems.grafo to javafx.fxml;
    exports com.crissystems.grafo;
}