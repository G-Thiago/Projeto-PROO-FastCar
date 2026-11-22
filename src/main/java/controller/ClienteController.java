package controller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import view.TelaClientes.ModelClienteTabela;
import dao.ClientesDAO;

public class ClienteController {

    private final ClientesDAO dao = new ClientesDAO();

    public void abrirModalEditarCliente(ModelClienteTabela cliente, Runnable aoConcluir) {
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
                criarLinhaCampo("Nome:", txtNome),
                criarLinhaCampo("CPF:", txtCpf),
                criarLinhaCampo("Telefone:", txtTelefone),
                criarLinhaCampo("CNH:", txtCnh),
                botoes
        );

        modalStage.setScene(new Scene(rootModal, 400, 380));
        modalStage.showAndWait();
    }

    public void confirmarExclusao(ModelClienteTabela cliente, Runnable aoConcluir) {
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

    private VBox criarLinhaCampo(String rotulo, TextField campo) {
        Label lbl = new Label(rotulo);
        lbl.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #334155;");
        return new VBox(4, lbl, campo);
    }
}
