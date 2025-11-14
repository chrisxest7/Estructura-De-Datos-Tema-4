module com.crissystems.grafocondfsvisual {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.crissystems.grafocondfsvisual to javafx.fxml;
    exports com.crissystems.grafocondfsvisual;
}