package view;

import controller.MaskUtils;
import dao.ClientesDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.ModelClienteTabela;

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

        cabecalho.getChildren().addAll(lblTitulo, espacador, btnNovoCliente);
        containerPrincipal.getChildren().add(cabecalho);

        TableView<ModelClienteTabela> tabela = new TableView<>();
        btnNovoCliente.setOnAction(e -> abrirModalNovoCliente(tabela));
        tabela.setStyle("-fx-background-color: white; -fx-border-color: #e2e8f0;");
        VBox.setVgrow(tabela, Priority.ALWAYS);

        Label lblVazio = new Label("Nenhum cliente cadastrado no momento.");
        lblVazio.setStyle("-fx-text-fill: #64748b; -fx-font-size: 13px;");
        tabela.setPlaceholder(lblVazio);

        TableColumn<ModelClienteTabela, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(60);

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

        TableColumn<ModelClienteTabela, HBox> colAcoes = new TableColumn<>("Ações");
        colAcoes.setCellValueFactory(new PropertyValueFactory<>("acoes"));
        colAcoes.setPrefWidth(160);

        tabela.getColumns().addAll(colId, colNome, colCpf, colTelefone, colCnh, colAcoes);
        carregarTabela(tabela);

        VBox painelTabela = new VBox(tabela);
        painelTabela.setStyle("-fx-background-color: white; -fx-border-color: #e2e8f0; -fx-border-radius: 6px; -fx-background-radius: 6px;");
        containerPrincipal.getChildren().add(painelTabela);

        return containerPrincipal;
    }

    private void carregarTabela(TableView<ModelClienteTabela> tabela) {
        ClientesDAO dao = new ClientesDAO();
        var lista = dao.listar();
        lista.forEach(item -> item.configurarAcoes(
                cliente -> abrirModalEditarCliente(cliente, () -> carregarTabela(tabela)),
                cliente -> confirmarExclusao(cliente, () -> carregarTabela(tabela))
        ));
        tabela.getItems().setAll(lista);
    }

    private void abrirModalNovoCliente(TableView<ModelClienteTabela> tabela) {
        Stage modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.setTitle("Novo Cliente");
        modalStage.setResizable(false);

        VBox rootModal = new VBox(15);
        rootModal.setPadding(new Insets(20));
        rootModal.setStyle("-fx-background-color: white;");
        rootModal.setAlignment(Pos.TOP_LEFT);

        Label lblModalTitulo = new Label("Adicionar Novo Cliente");
        lblModalTitulo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #1e293b;");

        TextField txtNome = new TextField();
        txtNome.setPromptText("Ex: João Silva");

        TextField txtCpf = new TextField();
        txtCpf.setPromptText("Ex: 123.456.789-00");
        MaskUtils.aplicarMascaraCpf(txtCpf);

        TextField txtTelefone = new TextField();
        txtTelefone.setPromptText("Ex: (11) 99999-9999");
        MaskUtils.aplicarMascaraTelefone(txtTelefone);

        TextField txtCnh = new TextField();
        txtCnh.setPromptText("Ex: 12345678900");
        MaskUtils.aplicarMascaraCNH(txtCnh);

        rootModal.getChildren().addAll(
                lblModalTitulo,
                new Separator(),
                criarCampoForm("Nome:", txtNome),
                criarCampoForm("CPF:", txtCpf),
                criarCampoForm("Telefone:", txtTelefone),
                criarCampoForm("CNH:", txtCnh)
        );

        Button btnSalvar = new Button("Salvar");
        btnSalvar.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");
        btnSalvar.setOnAction(e -> {
            if (txtNome.getText().isBlank() || txtCpf.getText().isBlank()) {
                Alert aviso = new Alert(Alert.AlertType.WARNING, "Preencha ao menos o nome e o CPF do cliente.");
                aviso.setHeaderText(null);
                aviso.showAndWait();
                return;
            }

            ClientesDAO dao = new ClientesDAO();
            boolean salvou = dao.cadastrar(
                    txtNome.getText().trim(),
                    txtCpf.getText().trim(),
                    txtTelefone.getText().trim(),
                    txtCnh.getText().trim()
            );

            if (salvou) {
                Alert alerta = new Alert(Alert.AlertType.INFORMATION, "Cliente cadastrado com sucesso!");
                alerta.setHeaderText(null);
                alerta.showAndWait();

                carregarTabela(tabela);
                modalStage.close();
            } else {
                Alert alerta = new Alert(Alert.AlertType.ERROR, "Erro ao cadastrar cliente. Confira o console.");
                alerta.setHeaderText(null);
                alerta.showAndWait();
            }
        });

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setStyle("-fx-background-color: #e2e8f0; -fx-text-fill: #1e293b; -fx-cursor: hand;");
        btnCancelar.setOnAction(e -> modalStage.close());

        HBox boxBotoes = new HBox(10, btnSalvar, btnCancelar);
        boxBotoes.setAlignment(Pos.CENTER_RIGHT);
        rootModal.getChildren().add(boxBotoes);

        Scene scene = new Scene(rootModal, 420, 440);
        modalStage.setScene(scene);
        modalStage.showAndWait();
    }

    private void abrirModalEditarCliente(ModelClienteTabela cliente, Runnable aoConcluir) {
        ClientesDAO dao = new ClientesDAO();

        Stage modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.setTitle("Editar Cliente");
        modalStage.setResizable(false);

        VBox rootModal = new VBox(15);
        rootModal.setPadding(new Insets(20));
        rootModal.setStyle("-fx-background-color: white;");

        Label lblTitulo = new Label("Editar Dados — " + cliente.getNome());
        lblTitulo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #1e293b;");
        lblTitulo.setWrapText(true);

        TextField txtNome = new TextField(cliente.getNome());
        txtNome.setPromptText("Nome completo");

        TextField txtCpf = new TextField(cliente.getCpf());
        txtCpf.setPromptText("000.000.000-00");
        MaskUtils.aplicarMascaraCpf(txtCpf);

        TextField txtTelefone = new TextField(cliente.getTelefone());
        txtTelefone.setPromptText("(00) 00000-0000");
        MaskUtils.aplicarMascaraTelefone(txtTelefone);

        TextField txtCnh = new TextField(cliente.getCnh());
        txtCnh.setPromptText("Número da CNH");
        MaskUtils.aplicarMascaraCNH(txtCnh);

        Button btnSalvar = new Button("Salvar");
        btnSalvar.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");
        btnSalvar.setOnAction(e -> {
            if (txtNome.getText().isBlank() || txtCpf.getText().isBlank()) {
                Alert aviso = new Alert(Alert.AlertType.WARNING, "Preencha ao menos o nome e o CPF do cliente.");
                aviso.setHeaderText(null);
                aviso.showAndWait();
                return;
            }

            boolean atualizou = dao.atualizar(
                    Integer.parseInt(cliente.getId()),
                    txtNome.getText().trim(),
                    txtCpf.getText().trim(),
                    txtTelefone.getText().trim(),
                    txtCnh.getText().trim()
            );

            if (atualizou) {
                Alert alerta = new Alert(Alert.AlertType.INFORMATION, "Cliente atualizado com sucesso!");
                alerta.setHeaderText(null);
                alerta.showAndWait();

                if (aoConcluir != null) {
                    aoConcluir.run();
                }
                modalStage.close();
            } else {
                Alert alerta = new Alert(Alert.AlertType.ERROR, "Erro ao atualizar cliente. Confira o console.");
                alerta.setHeaderText(null);
                alerta.showAndWait();
            }
        });

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setStyle("-fx-background-color: #e2e8f0; -fx-text-fill: #1e293b; -fx-cursor: hand;");
        btnCancelar.setOnAction(e -> modalStage.close());

        HBox botoes = new HBox(10, btnSalvar, btnCancelar);
        botoes.setAlignment(Pos.CENTER_RIGHT);

        rootModal.getChildren().addAll(
                lblTitulo,
                new Separator(),
                criarCampoForm("Nome:", txtNome),
                criarCampoForm("CPF:", txtCpf),
                criarCampoForm("Telefone:", txtTelefone),
                criarCampoForm("CNH:", txtCnh),
                botoes
        );

        modalStage.setScene(new Scene(rootModal, 400, 380));
        modalStage.showAndWait();
    }

    private void confirmarExclusao(ModelClienteTabela cliente, Runnable aoConcluir) {
        ClientesDAO dao = new ClientesDAO();
        int clienteId = Integer.parseInt(cliente.getId());

        if (dao.possuiLocacoes(clienteId)) {
            Alert aviso = new Alert(
                    Alert.AlertType.WARNING,
                    "Não é possível excluir um cliente que possui locações registradas."
            );
            aviso.setHeaderText(null);
            aviso.showAndWait();
            return;
        }

        Alert confirmacao = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Deseja realmente excluir o cliente " + cliente.getNome() + " (" + cliente.getCpf() + ")?",
                ButtonType.YES,
                ButtonType.NO
        );
        confirmacao.setHeaderText(null);

        confirmacao.showAndWait().ifPresent(resposta -> {
            if (resposta == ButtonType.YES) {
                boolean excluiu = dao.excluir(clienteId);
                if (excluiu) {
                    Alert alerta = new Alert(Alert.AlertType.INFORMATION, "Cliente excluído com sucesso!");
                    alerta.setHeaderText(null);
                    alerta.showAndWait();

                    if (aoConcluir != null) {
                        aoConcluir.run();
                    }
                } else {
                    Alert alerta = new Alert(
                            Alert.AlertType.ERROR,
                            "Não foi possível excluir o cliente. Confira o console."
                    );
                    alerta.setHeaderText(null);
                    alerta.showAndWait();
                }
            }
        });
    }
}