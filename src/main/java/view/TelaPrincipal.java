package view;

import controller.RoteadorDeTelas;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.App;

import java.io.InputStream;

public class TelaPrincipal {

    private static BorderPane root;

    public static void iniciarAplicacao(Stage stage) {
        root = new BorderPane();
        root.setStyle("-fx-background-color: #f8fafc;");

        root.setLeft(BarraLateral.criar(TelaPrincipal::trocarTela));


        trocarTela("Início");

        Scene scene = new Scene(root, 1200, 700);

        stage.setTitle("FastCar - Locadora de Veículos");
        stage.setScene(scene);

        try {
            InputStream iconStream = TelaPrincipal.class.getResourceAsStream("/Logo.jpg");
            if (iconStream != null) {
                stage.getIcons().add(new Image(iconStream));
            }
        } catch (Exception e) {
            System.err.println("Não foi possível carregar o ícone da janela.");
        }

        stage.setMaximized(true);
        stage.show();
    }

    public interface MenuClickListener {
        void onMenuClick(String menuName);
    }

    public static void trocarTela(String nomeMenu) {
        RoteadorDeTelas.trocarTela(nomeMenu, root);
    }
}