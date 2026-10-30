package view;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.interfaces_classesAbstratas.TelaGenerica;

import java.io.InputStream;

public class TelaPrincipal {

    private static BorderPane root;

    public static void iniciarAplicacao(Stage stage) {
        root = new BorderPane();
        root.setStyle("-fx-background-color: #f8fafc;");

        // Insere a barra lateral fixa utilizando a classe BarraLateral
        root.setLeft(BarraLateral.criar(TelaPrincipal::trocarTela));


        trocarTela("Início");

        Scene scene = new Scene(root, 1200, 700);
        stage.setTitle("FastCar - Locadora de Veículos");
        stage.setScene(scene);

        // Trocar icone
        try {
            InputStream iconStream = TelaPrincipal.class.getResourceAsStream("/Logo.jpg");
            if (iconStream != null) {
                stage.getIcons().add(new Image(iconStream));
            }
        } catch (Exception e) {
            System.err.println("Não foi possível carregar o ícone da janela.");
        }

        stage.show();
    }

    public interface MenuClickListener {
        void onMenuClick(String menuName);
    }

    public static void trocarTela(String nomeMenu) {
        switch (nomeMenu) {
            case "Início":
                root.setCenter(TelaInicio.criar());
                break;
            case "Veículos":
                root.setCenter(TelaVeiculos.criar());
                break;
            case "Clientes":
                root.setCenter(TelaClientes.criar());
                break;
            case "Nova Locação":
                root.setCenter(TelaGenerica.criar("Tela de Nova Locação (Em construção)"));
                break;
            case "Devoluções":
                root.setCenter(TelaDevolucoes.criar());
                break;
            case "Configurações":
                root.setCenter(TelaGenerica.criar("Tela de Configurações"));
                break;
            default:
                root.setCenter(new Label("Tela não encontrada"));
                break;
        }
    }
}