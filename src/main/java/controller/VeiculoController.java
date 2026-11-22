package controller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import view.TelaVeiculos.ModelVeiculoTabela;
import dao.VeiculoDAO;

public class VeiculoController {

    private final VeiculoDAO dao = new VeiculoDAO();

    public void abrirModalAlterarStatus(ModelVeiculoTabela veiculo, Runnable aoConcluir) {
        Stage modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.setTitle("Alterar Status");
        modalStage.setResizable(false);

        VBox rootModal = new VBox(15);
        rootModal.setPadding(new Insets(20));
        rootModal.setStyle("-fx-background-color: white;");

        Label lblTitulo = new Label(
                "Alterar disponibilidade — " + veiculo.getMarca() + " " + veiculo.getModelo() + " (" + veiculo.getPlaca() + ")"
        );
        lblTitulo.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #1e293b;");
        lblTitulo.setWrapText(true);

        ComboBox<String> cbStatus = new ComboBox<>();
        cbStatus.getItems().addAll("Disponível", "Alugado", "Em manutenção");
        cbStatus.setValue(veiculo.getDisponibilidade());
        cbStatus.setMaxWidth(Double.MAX_VALUE);

        Button btnSalvar = new Button("Salvar");
        btnSalvar.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");
        btnSalvar.setOnAction(e -> {
            boolean ok = dao.atualizarDisponibilidade(
                    Integer.parseInt(veiculo.getId()),
                    cbStatus.getValue()
            );
            if (ok) {
                Alert alerta = new Alert(Alert.AlertType.INFORMATION, "Status atualizado com sucesso!");
                alerta.setHeaderText(null);
                alerta.showAndWait();

                if (aoConcluir != null) {
                    aoConcluir.run();
                }
                modalStage.close();
            } else {
                Alert alerta = new Alert(Alert.AlertType.ERROR, "Erro ao atualizar status. Confira o console.");
                alerta.setHeaderText(null);
                alerta.showAndWait();
            }
        });

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setStyle("-fx-background-color: #e2e8f0; -fx-text-fill: #1e293b; -fx-cursor: hand;");
        btnCancelar.setOnAction(e -> modalStage.close());

        HBox botoes = new HBox(10, btnSalvar, btnCancelar);
        botoes.setAlignment(Pos.CENTER_RIGHT);

        rootModal.getChildren().addAll(lblTitulo, cbStatus, botoes);
        modalStage.setScene(new Scene(rootModal, 360, 190));
        modalStage.showAndWait();
    }

    public void confirmarExclusao(ModelVeiculoTabela veiculo, Runnable aoConcluir) {
        if ("Alugado".equalsIgnoreCase(veiculo.getDisponibilidade())) {
            Alert aviso = new Alert(
                    Alert.AlertType.WARNING,
                    "Não é possível excluir um veículo que está alugado. Conclua ou cancele a locação ativa primeiro."
            );
            aviso.setHeaderText(null);
            aviso.showAndWait();
            return;
        }

        Alert confirmacao = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Deseja realmente excluir o veículo " + veiculo.getMarca() + " " + veiculo.getModelo() + " (" + veiculo.getPlaca() + ")?",
                ButtonType.YES,
                ButtonType.NO
        );
        confirmacao.setHeaderText(null);

        confirmacao.showAndWait().ifPresent(resposta -> {
            if (resposta == ButtonType.YES) {
                boolean excluiu = dao.excluir(Integer.parseInt(veiculo.getId()));
                if (excluiu) {
                    Alert alerta = new Alert(Alert.AlertType.INFORMATION, "Veículo excluído com sucesso!");
                    alerta.setHeaderText(null);
                    alerta.showAndWait();

                    if (aoConcluir != null) {
                        aoConcluir.run();
                    }
                } else {
                    Alert alerta = new Alert(
                            Alert.AlertType.ERROR,
                            "Não foi possível excluir o veículo. Verifique se ele possui locações vinculadas."
                    );
                    alerta.setHeaderText(null);
                    alerta.showAndWait();
                }
            }
        });
    }
}