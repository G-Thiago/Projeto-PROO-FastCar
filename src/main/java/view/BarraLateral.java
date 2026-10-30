package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import java.io.InputStream;

public class BarraLateral {

    public static VBox criar(TelaPrincipal.MenuClickListener listener) {
        VBox sidebarBox = new VBox(15);
        sidebarBox.setPrefWidth(240);
        sidebarBox.setMaxWidth(240);
        sidebarBox.setStyle("-fx-background-color: #010019;");
        sidebarBox.setPadding(new Insets(20, 15, 20, 15));

        // --- Logo no topo da barra lateral ---
        VBox logoBox = new VBox(10);
        logoBox.setAlignment(Pos.CENTER);
        logoBox.setPadding(new Insets(0, 0, 10, 0));

        try {
            InputStream is = BarraLateral.class.getResourceAsStream("/Logo.jpg");
            if (is != null) {
                ImageView logoView = new ImageView(new Image(is));
                logoView.setFitWidth(130);
                logoView.setPreserveRatio(true);
                logoBox.getChildren().add(logoView);
            } else {
                Label lblTituloLogo = new Label("FASTCAR");
                lblTituloLogo.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");
                logoBox.getChildren().add(lblTituloLogo);
            }
        } catch (Exception e) {
            Label lblTituloLogo = new Label("FASTCAR");
            lblTituloLogo.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");
            logoBox.getChildren().add(lblTituloLogo);
        }

        // Menu de Navegação
        VBox menuBox = new VBox(8);
        VBox.setVgrow(menuBox, Priority.ALWAYS);

        Button btnInicio = criarBotaoMenu("🏠", "Início", listener);
        Button btnVeiculos = criarBotaoMenu("🚗", "Veículos", listener);
        Button btnClientes = criarBotaoMenu("👥", "Clientes", listener);
        Button btnNovaLocacao = criarBotaoMenu("📄", "Nova Locação", listener);
        Button btnDevolucoes = criarBotaoMenu("🔄", "Devoluções", listener);

        menuBox.getChildren().addAll(btnInicio, btnVeiculos, btnClientes, btnNovaLocacao, btnDevolucoes);

        // Rodapé (Configurações)
        VBox footerBox = new VBox();
        footerBox.setPadding(new Insets(10, 0, 0, 0));
        Button btnConfiguracoes = criarBotaoMenu("⚙", "Configurações", listener);
        footerBox.getChildren().add(btnConfiguracoes);

        sidebarBox.getChildren().addAll(logoBox, menuBox, footerBox);
        return sidebarBox;
    }

    private static Button criarBotaoMenu(String icone, String texto, TelaPrincipal.MenuClickListener listener) {
        Button btn = new Button(icone + "   " + texto);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setPadding(new Insets(10, 12, 10, 12));
        btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #cbd5e1; -fx-font-size: 13px; -fx-cursor: hand;");

        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #1e293b; -fx-text-fill: white; -fx-font-size: 13px; -fx-background-radius: 6px; -fx-cursor: hand;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #cbd5e1; -fx-font-size: 13px; -fx-cursor: hand;"));

        btn.setOnAction(e -> {
            if (listener != null) {
                listener.onMenuClick(texto);
            }
        });

        return btn;
    }
}