module com.crissystems.grafodinamicoconmatrizdeady {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.crissystems.grafodinamicoconmatrizdeady to javafx.fxml;
    exports com.crissystems.grafodinamicoconmatrizdeady;
}