package view;

import controller.MaskUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TelaVeiculos implements Tela {

    @Override
    public Node criar() {
        VBox containerPrincipal = new VBox(20);
        containerPrincipal.setPadding(new Insets(20));
        containerPrincipal.setStyle("-fx-background-color: #f5f7fa;");

        HBox cabecalho = new HBox();
        cabecalho.setAlignment(Pos.CENTER_LEFT);

        Label lblTitulo = new Label("Veículos");
        lblTitulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #1e293b;");

        Region espacador = new Region();
        HBox.setHgrow(espacador, Priority.ALWAYS);

        Button btnNovoVeiculo = new Button("+ Novo Veículo");
        btnNovoVeiculo.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 8px 15px;");
        btnNovoVeiculo.setOnAction(e -> abrirModalNovoVeiculo());

        cabecalho.getChildren().addAll(lblTitulo, espacador, btnNovoVeiculo);
        containerPrincipal.getChildren().add(cabecalho);

        HBox barraBusca = new HBox(10);
        barraBusca.setAlignment(Pos.CENTER_LEFT);
        barraBusca.setPadding(new Insets(10));
        barraBusca.setStyle("-fx-background-color: white; -fx-border-color: #e2e8f0; -fx-border-radius: 6px; -fx-background-radius: 6px;");

        TextField txtBusca = new TextField();
        txtBusca.setPromptText("🔍 Buscar por placa, modelo ou marca...");
        txtBusca.setPrefWidth(400);
        txtBusca.setStyle("-fx-pref-height: 32px;");

        barraBusca.getChildren().add(txtBusca);
        containerPrincipal.getChildren().add(barraBusca);

        TableView<ModelVeiculoTabela> tabela = new TableView<>();
        tabela.setStyle("-fx-background-color: white; -fx-border-color: #e2e8f0;");
        tabela.setPlaceholder(new Label("Nenhum veículo cadastrado."));
        VBox.setVgrow(tabela, Priority.ALWAYS);

        Label lblVazio = new Label("Nenhum veículo cadastrado no momento.");
        lblVazio.setStyle("-fx-text-fill: #64748b; -fx-font-size: 13px;");
        tabela.setPlaceholder(lblVazio);

        TableColumn<ModelVeiculoTabela, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(50);

        TableColumn<ModelVeiculoTabela, String> colFoto = new TableColumn<>("Foto");
        colFoto.setCellValueFactory(new PropertyValueFactory<>("foto"));
        colFoto.setPrefWidth(80);

        TableColumn<ModelVeiculoTabela, String> colMarca = new TableColumn<>("Marca");
        colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colMarca.setPrefWidth(100);

        TableColumn<ModelVeiculoTabela, String> colModelo = new TableColumn<>("Modelo");
        colModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        colModelo.setPrefWidth(120);

        TableColumn<ModelVeiculoTabela, String> colAno = new TableColumn<>("Ano");
        colAno.setCellValueFactory(new PropertyValueFactory<>("ano"));
        colAno.setPrefWidth(70);

        TableColumn<ModelVeiculoTabela, String> colPlaca = new TableColumn<>("Placa");
        colPlaca.setCellValueFactory(new PropertyValueFactory<>("placa"));
        colPlaca.setPrefWidth(90);

        TableColumn<ModelVeiculoTabela, String> colDiaria = new TableColumn<>("Valor da diária");
        colDiaria.setCellValueFactory(new PropertyValueFactory<>("valorDiaria"));
        colDiaria.setPrefWidth(110);

        TableColumn<ModelVeiculoTabela, String> colDisponibilidade = new TableColumn<>("Disponibilidade");
        colDisponibilidade.setCellValueFactory(new PropertyValueFactory<>("disponibilidade"));
        colDisponibilidade.setPrefWidth(110);

        TableColumn<ModelVeiculoTabela, String> colAcoes = new TableColumn<>("Ações");
        colAcoes.setCellValueFactory(new PropertyValueFactory<>("acoes"));
        colAcoes.setPrefWidth(80);

        tabela.getColumns().addAll(colId, colFoto, colMarca, colModelo, colAno, colPlaca, colDiaria, colDisponibilidade, colAcoes);

        VBox painelTabela = new VBox(tabela);
        painelTabela.setStyle("-fx-background-color: white; -fx-border-color: #e2e8f0; -fx-border-radius: 6px; -fx-background-radius: 6px;");
        containerPrincipal.getChildren().add(painelTabela);

        return containerPrincipal;
    }

    private void abrirModalNovoVeiculo() {
        Stage modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.setTitle(" Novo Veículo");
        modalStage.setResizable(false);

        VBox rootModal = new VBox(15);
        rootModal.setPadding(new Insets(20));
        rootModal.setStyle("-fx-background-color: white;");
        rootModal.setAlignment(Pos.TOP_LEFT);

        Label lblModalTitulo = new Label(" Adicionar Novo Veículo");
        lblModalTitulo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #1e293b;");

        TextField txtMarca = new TextField();
        txtMarca.setPromptText("Ex: Toyota");

        TextField txtModelo = new TextField();
        txtModelo.setPromptText("Ex: Corolla");

        TextField txtAno = new TextField();
        txtAno.setPromptText("Ex: 2022");
        MaskUtils.aplicarMascaraAno(txtAno);

        TextField txtPlaca = new TextField();
        txtPlaca.setPromptText("Ex: ABC-1023");
        MaskUtils.aplicarMascaraPlaca(txtPlaca);

        TextField txtValorDiaria = new TextField();
        txtValorDiaria.setPromptText("Ex: 150,00");

        ComboBox<String> cbDisponibilidade = new ComboBox<>();
        cbDisponibilidade.getItems().addAll("Disponível", "Alugado", "Em manutenção");
        cbDisponibilidade.setValue("Disponível");
        cbDisponibilidade.setMaxWidth(Double.MAX_VALUE);

        // Chamada direta do método herdado da interface Tela
        rootModal.getChildren().addAll(
                lblModalTitulo,
                new Separator(),
                criarCampoForm("Marca:", txtMarca),
                criarCampoForm("Modelo:", txtModelo),
                criarCampoForm("Ano:", txtAno),
                criarCampoForm("Placa:", txtPlaca),
                criarCampoForm("Valor da Diária (R$):", txtValorDiaria),
                criarCampoForm("Disponibilidade:", cbDisponibilidade)
        );

        Button btnSalvar = new Button("Salvar");
        btnSalvar.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");
        btnSalvar.setOnAction(e -> {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION, "Interface visual acionada com sucesso! (Sem DAO)");
            alerta.setHeaderText(null);
            alerta.showAndWait();
            modalStage.close();
        });

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setStyle("-fx-background-color: #e2e8f0; -fx-text-fill: #1e293b; -fx-cursor: hand;");
        btnCancelar.setOnAction(e -> modalStage.close());

        HBox boxBotoes = new HBox(10, btnSalvar, btnCancelar);
        boxBotoes.setAlignment(Pos.CENTER_RIGHT);
        rootModal.getChildren().add(boxBotoes);

        Scene scene = new Scene(rootModal, 420, 520);
        modalStage.setScene(scene);
        modalStage.showAndWait();
    }

    public static class ModelVeiculoTabela {
        private String id, foto, marca, modelo, ano, placa, valorDiaria, disponibilidade, acoes;

        public ModelVeiculoTabela(String id, String foto, String marca, String modelo, String ano, String placa, String valorDiaria, String disponibilidade, String acoes) {
            this.id = id; this.foto = foto; this.marca = marca; this.modelo = modelo; this.ano = ano; this.placa = placa; this.valorDiaria = valorDiaria; this.disponibilidade = disponibilidade; this.acoes = acoes;
        }

        public String getId() { return id; }
        public String getFoto() { return foto; }
        public String getMarca() { return marca; }
        public String getModelo() { return modelo; }
        public String getAno() { return ano; }
        public String getPlaca() { return placa; }
        public String getValorDiaria() { return valorDiaria; }
        public String getDisponibilidade() { return disponibilidade; }
        public String getAcoes() { return acoes; }
    }
}