package view;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class TelaGenerica {
    public static VBox criar(String titulo) {
        VBox painel = new VBox(15);
        painel.setPadding(new Insets(25));
        Label lbl = new Label(titulo);
        lbl.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #1e293b;");
        painel.getChildren().add(lbl);
        return painel;
    }
}