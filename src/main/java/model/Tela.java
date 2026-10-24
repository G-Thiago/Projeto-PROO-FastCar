package model;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Tela {

    private static BorderPane root;

    public static void iniciarAplicacao(Stage stage) {
        root = new BorderPane();
        root.setStyle("-fx-background-color: #f8fafc;");

        // Adiciona a barra lateral informando o comportamento de clique para trocar de tela
        root.setLeft(criarSidebar(menu -> trocarTela(menu)));

        // Inicia o app na tela de Início por padrão
        trocarTela("Início");

        Scene scene = new Scene(root, 1200, 700);
        stage.setTitle("FastCar - Locadora de Veículos");
        stage.setScene(scene);
        stage.show();
    }

    // --- BARRA LATERAL (Sidebar) ---
    public interface MenuClickListener {
        void onMenuClick(String menuName);
    }

    public static VBox criarSidebar(MenuClickListener listener) {
        VBox sidebar = new VBox();
        sidebar.setPrefWidth(220);
        sidebar.setStyle("-fx-background-color: #0d1b2a;");
        sidebar.setPadding(new Insets(20, 15, 20, 15));

        // Logotipo
        VBox logoBox = new VBox(2);
        logoBox.setPadding(new Insets(0, 0, 25, 0));
        Label lblTituloLogo = new Label("FastCar");
        lblTituloLogo.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");
        Label lblSubLogo = new Label("Locadora de Veículos");
        lblSubLogo.setStyle("-fx-text-fill: #94a3b8; -fx-font-size: 10px;");
        logoBox.getChildren().addAll(lblTituloLogo, lblSubLogo);

        // Menu de Navegação
        VBox menuBox = new VBox(8);
        VBox.setVgrow(menuBox, Priority.ALWAYS);

        Button btnInicio = criarBotaoMenu("", "Início", listener);
        Button btnVeiculos = criarBotaoMenu("", "Veículos", listener);
        Button btnClientes = criarBotaoMenu("", "Clientes", listener);
        Button btnNovaLocacao = criarBotaoMenu("", "Nova Locação", listener);
        Button btnDevolucoes = criarBotaoMenu("", "Devoluções", listener);

        menuBox.getChildren().addAll(btnInicio, btnVeiculos, btnClientes, btnNovaLocacao, btnDevolucoes);

        // Rodapé (Configurações)
        VBox footerBox = new VBox();
        footerBox.setPadding(new Insets(10, 0, 0, 0));
        Button btnConfiguracoes = criarBotaoMenu("⚙", "Configurações", listener);
        footerBox.getChildren().add(btnConfiguracoes);

        sidebar.getChildren().addAll(logoBox, menuBox, footerBox);
        return sidebar;
    }

    private static Button criarBotaoMenu(String icone, String texto, MenuClickListener listener) {
        Button btn = new Button(icone + "   " + texto);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setPadding(new Insets(10, 12, 10, 12));
        btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #cbd5e1; -fx-font-size: 13px;");

        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #1e293b; -fx-text-fill: white; -fx-font-size: 13px; -fx-background-radius: 6px;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #cbd5e1; -fx-font-size: 13px;"));

        btn.setOnAction(e -> {
            if (listener != null) {
                listener.onMenuClick(texto);
            }
        });

        return btn;
    }

    // --- GERENCIADOR DE TROCA DE TELAS ---
    public static void trocarTela(String nomeMenu) {
        switch (nomeMenu) {
            case "Início":
                root.setCenter(criarTelaInicio());
                break;
            case "Veículos":
                root.setCenter(criarTelaVeiculos());
                break;
            case "Clientes":
                root.setCenter(criarTelaSimples("Tela de Clientes (Em construção)"));
                break;
            case "Nova Locação":
                root.setCenter(criarTelaSimples("Tela de Nova Locação (Em construção)"));
                break;
            case "Devoluções":
                root.setCenter(criarTelaSimples("Tela de Devoluções (Em construção)"));
                break;
            case "Configurações":
                root.setCenter(criarTelaSimples("Tela de Configurações"));
                break;
            default:
                root.setCenter(new Label("Tela não encontrada"));
                break;
        }
    }

    // --- TELA DE VEÍCULOS (Baseada na imagem de referência) ---
    private static VBox criarTelaVeiculos() {
        VBox painel = new VBox(15);
        painel.setPadding(new Insets(20));

        HBox topo = new HBox();
        topo.setAlignment(Pos.CENTER_LEFT);
        Label lblTitulo = new Label("Veículos");
        lblTitulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #1e293b;");

        Region espaco = new Region();
        HBox.setHgrow(espaco, Priority.ALWAYS);

        Button btnNovoVeiculo = new Button("+ Novo Veículo");
        btnNovoVeiculo.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5px;");
        topo.getChildren().addAll(lblTitulo, espaco, btnNovoVeiculo);


        TableView<String[]> tabela = new TableView<>();
        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<String[], String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()[0]));

        TableColumn<String[], String> colMarca = new TableColumn<>("Marca");
        colMarca.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()[1]));

        TableColumn<String[], String> colModelo = new TableColumn<>("Modelo");
        colModelo.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()[2]));

        TableColumn<String[], String> colAno = new TableColumn<>("Ano");
        colAno.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()[3]));

        TableColumn<String[], String> colPlaca = new TableColumn<>("Placa");
        colPlaca.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()[4]));

        TableColumn<String[], String> colDiaria = new TableColumn<>("Valor da diária");
        colDiaria.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()[5]));

        TableColumn<String[], String> colDisponibilidade = new TableColumn<>("Disponibilidade");
        colDisponibilidade.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()[6]));

        tabela.getColumns().addAll(colId, colMarca, colModelo, colAno, colPlaca, colDiaria, colDisponibilidade);

        VBox.setVgrow(tabela, Priority.ALWAYS);
        painel.getChildren().addAll(topo, tabela);
        return painel;
    }

    private static VBox criarTelaInicio() {
        VBox painel = new VBox(15);
        painel.setPadding(new Insets(20));
        Label lbl = new Label("Início - Visão Geral do Sistema");
        lbl.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #1e293b;");
        painel.getChildren().add(lbl);
        return painel;
    }

    private static VBox criarTelaSimples(String titulo) {
        VBox painel = new VBox(15);
        painel.setPadding(new Insets(20));
        Label lbl = new Label(titulo);
        lbl.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #1e293b;");
        painel.getChildren().add(lbl);
        return painel;
    }
}