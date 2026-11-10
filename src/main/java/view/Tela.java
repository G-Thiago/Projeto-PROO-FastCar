package view;

import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public interface Tela {
    Node criar();

    default VBox criarCampoForm(String rotulo, Control componente) {
        VBox campoBox = new VBox(5);
        Label lbl = new Label(rotulo);
        lbl.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #64748b;");

        componente.setStyle(componente.getStyle() + "; -fx-pref-height: 32px;");
        campoBox.getChildren().addAll(lbl, componente);
        return campoBox;
    }
}