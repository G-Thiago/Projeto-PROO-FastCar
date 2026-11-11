package controller;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import view.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class RoteadorDeTelas {

    private static final Map<String, Supplier<Node>> rotas = new HashMap<>();

    static {
        rotas.put("Início", () -> new TelaInicio().criar());
        rotas.put("Veículos", () -> new TelaVeiculos().criar());
        rotas.put("Clientes", () -> new TelaClientes().criar());
        rotas.put("Nova Locação", () -> new TelaLocacao().criar());
        rotas.put("Devoluções", () -> new TelaDevolucoes().criar());
    }

    public static void trocarTela(String nomeMenu, BorderPane root) {
        Supplier<Node> telaCriadora = rotas.get(nomeMenu);

        if (telaCriadora != null) {
            root.setCenter(telaCriadora.get());
        } else {
            root.setCenter(new Label("Tela não encontrada"));
        }
    }
}