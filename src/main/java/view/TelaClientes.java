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

public class TelaClientes implements Tela {

    @Override
    public Node criar() {
        VBox containerPrincipal = new VBox(20);
        containerPrincipal.setPadding(new Insets(20));
        containerPrincipal.setStyle("-fx-background-color: #f5f7fa;");

        HBox cabecalho = new HBox();
        cabecalho.setAlignment(Pos.CENTER_LEFT);

        Label lblTitulo = new Label("Clientes");
        lblTitulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #1e293b;");

        Region espacador = new Region();
        HBox.setHgrow(espacador, Priority.ALWAYS);

        Button btnNovoCliente = new Button("+ Novo Cliente");
        btnNovoCliente.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 8px 15px;");
        btnNovoCliente.setOnAction(e -> abrirModalNovoCliente());

        cabecalho.getChildren().addAll(lblTitulo, espacador, btnNovoCliente);
        containerPrincipal.getChildren().add(cabecalho);

        HBox barraBusca = new HBox(10);
        barraBusca.setAlignment(Pos.CENTER_LEFT);
        barraBusca.setPadding(new Insets(10));
        barraBusca.setStyle("-fx-background-color: white; -fx-border-color: #e2e8f0; -fx-border-radius: 6px; -fx-background-radius: 6px;");

        TextField txtBusca = new TextField();
        txtBusca.setPromptText("🔍 Buscar por nome, CPF ou telefone...");
        txtBusca.setPrefWidth(400);
        txtBusca.setStyle("-fx-pref-height: 32px;");

        barraBusca.getChildren().add(txtBusca);
        containerPrincipal.getChildren().add(barraBusca);

        TableView<ModelClienteTabela> tabela = new TableView<>();
        tabela.setStyle("-fx-background-color: white; -fx-border-color: #e2e8f0;");
        tabela.setPlaceholder(new Label("Nenhum cliente cadastrado."));
        VBox.setVgrow(tabela, Priority.ALWAYS);

        Label lblVazio = new Label("Nenhum cliente cadastrado no momento.");
        lblVazio.setStyle("-fx-text-fill: #64748b; -fx-font-size: 13px;");
        tabela.setPlaceholder(lblVazio);

        TableColumn<ModelClienteTabela, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(50);

        TableColumn<ModelClienteTabela, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colNome.setPrefWidth(180);

        TableColumn<ModelClienteTabela, String> colCpf = new TableColumn<>("CPF");
        colCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colCpf.setPrefWidth(130);

        TableColumn<ModelClienteTabela, String> colTelefone = new TableColumn<>("Telefone");
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colTelefone.setPrefWidth(130);

        TableColumn<ModelClienteTabela, String> colCnh = new TableColumn<>("CNH");
        colCnh.setCellValueFactory(new PropertyValueFactory<>("cnh"));
        colCnh.setPrefWidth(130);

        TableColumn<ModelClienteTabela, String> colAcoes = new TableColumn<>("Ações");
        colAcoes.setCellValueFactory(new PropertyValueFactory<>("acoes"));
        colAcoes.setPrefWidth(80);

        tabela.getColumns().addAll(colId, colNome, colCpf, colTelefone, colCnh, colAcoes);

        VBox painelTabela = new VBox(tabela);
        painelTabela.setStyle("-fx-background-color: white; -fx-border-color: #e2e8f0; -fx-border-radius: 6px; -fx-background-radius: 6px;");
        containerPrincipal.getChildren().add(painelTabela);

        return containerPrincipal;
    }

    private void abrirModalNovoCliente() {
        Stage modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.setTitle(" Novo Cliente");
        modalStage.setResizable(false);

        VBox rootModal = new VBox(15);
        rootModal.setPadding(new Insets(20));
        rootModal.setStyle("-fx-background-color: white;");
        rootModal.setAlignment(Pos.TOP_LEFT);

        Label lblModalTitulo = new Label(" Adicionar Novo Cliente");
        lblModalTitulo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #1e293b;");

        TextField txtNome = new TextField();
        txtNome.setPromptText("Ex: Ana Clara Souza");

        TextField txtCpf = new TextField();
        txtCpf.setPromptText("000.000.000-00");
        MaskUtils.aplicarMascaraCpf(txtCpf);

        TextField txtTelefone = new TextField();
        txtTelefone.setPromptText("(00) 00000-0000");
        MaskUtils.aplicarMascaraTelefone(txtTelefone);

        TextField txtCnh = new TextField();
        txtCnh.setPromptText("Ex: 12345678901");
        MaskUtils.aplicarMascaraCNH(txtCnh);
        rootModal.getChildren().addAll(
                lblModalTitulo,
                new Separator(),
                criarCampoForm("Nome Completo:", txtNome),
                criarCampoForm("CPF:", txtCpf),
                criarCampoForm("Telefone:", txtTelefone),
                criarCampoForm("CNH:", txtCnh)
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

        Scene scene = new Scene(rootModal, 420, 460);
        modalStage.setScene(scene);
        modalStage.showAndWait();
    }

    public static class ModelClienteTabela {
        private String id, nome, cpf, telefone, cnh, acoes;

        public ModelClienteTabela(String id, String nome, String cpf, String telefone, String cnh, String acoes) {
            this.id = id;
            this.nome = nome;
            this.cpf = cpf;
            this.telefone = telefone;
            this.cnh = cnh;
            this.acoes = acoes;
        }

        public String getId() { return id; }
        public String getNome() { return nome; }
        public String getCpf() { return cpf; }
        public String getTelefone() { return telefone; }
        public String getCnh() { return cnh; }
        public String getAcoes() { return acoes; }
    }
}