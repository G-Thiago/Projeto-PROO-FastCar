package view;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class TelaClientes {

    public static VBox criar() {
        VBox painel = new VBox(15);
        painel.setPadding(new Insets(25));
        painel.setStyle("-fx-background-color: #f8fafc;");

        // --- TOPO: Título e Botão de Novo Cliente ---
        HBox topo = new HBox();
        topo.setAlignment(Pos.CENTER_LEFT);

        Label lblTitulo = new Label("Clientes");
        lblTitulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #1e293b;");

        Region espaco = new Region();
        HBox.setHgrow(espaco, Priority.ALWAYS);

        Button btnNovoCliente = new Button("+ Novo Cliente");
        btnNovoCliente.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 6px; -fx-padding: 8px 14px; -fx-cursor: hand;");

        topo.getChildren().addAll(lblTitulo, espaco, btnNovoCliente);

        // --- BARRA DE PESQUISA ---
        HBox barraPesquisa = new HBox(10);
        barraPesquisa.setAlignment(Pos.CENTER_LEFT);

        TextField txtPesquisa = new TextField();
        txtPesquisa.setPromptText("🔍   Buscar por nome, CPF ou telefone...");
        txtPesquisa.setPrefWidth(350);
        txtPesquisa.setStyle("-fx-background-radius: 6px; -fx-border-color: #cbd5e1; -fx-border-radius: 6px; -fx-padding: 7px;");

        barraPesquisa.getChildren().add(txtPesquisa);

        // --- TABELA DE CLIENTES (Utilizando String[] para os dados) ---
        TableView<String[]> tabela = new TableView<>();
        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<String[], String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()[0]));

        TableColumn<String[], String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()[1]));

        TableColumn<String[], String> colCpf = new TableColumn<>("CPF");
        colCpf.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()[2]));

        TableColumn<String[], String> colTelefone = new TableColumn<>("Telefone");
        colTelefone.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()[3]));

        TableColumn<String[], String> colCnh = new TableColumn<>("CNH");
        colCnh.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()[4]));

        tabela.getColumns().addAll(colId, colNome, colCpf, colTelefone, colCnh);

        VBox.setVgrow(tabela, Priority.ALWAYS);
        painel.getChildren().addAll(topo, barraPesquisa, tabela);
        return painel;
    }
}