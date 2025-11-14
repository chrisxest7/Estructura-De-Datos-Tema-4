module com.crissystems.buscarnumdearbolbinario {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.crissystems.buscarnumdearbolbinario to javafx.fxml;
    exports com.crissystems.buscarnumdearbolbinario;
}