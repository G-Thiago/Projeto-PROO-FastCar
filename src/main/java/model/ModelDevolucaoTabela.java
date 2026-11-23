package model;

import javafx.scene.control.Button;
import view.TelaDevolucoes;

public class ModelDevolucaoTabela {
    private String idLocacao, cliente, veiculo, placa, dataRetirada, previsaoDevolucao;
    private Button acoes;

    public ModelDevolucaoTabela(String idLocacao, String cliente, String veiculo, String placa, String dataRetirada, String previsaoDevolucao) {
        this.idLocacao = idLocacao;
        this.cliente = cliente;
        this.veiculo = veiculo;
        this.placa = placa;
        this.dataRetirada = dataRetirada;
        this.previsaoDevolucao = previsaoDevolucao;

        this.acoes = new Button("Devolver");
        this.acoes.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-cursor: hand; -fx-font-size: 11px;");
        this.acoes.setOnAction(e -> TelaDevolucoes.abrirModalDevolucao(idLocacao, cliente, veiculo, null));
    }

    public void configurarAcao(Runnable aoAtualizarTabela) {
        this.acoes.setOnAction(e -> TelaDevolucoes.abrirModalDevolucao(idLocacao, cliente, veiculo, aoAtualizarTabela));
    }

    public String getIdLocacao() { return idLocacao; }
    public String getCliente() { return cliente; }
    public String getVeiculo() { return veiculo; }
    public String getPlaca() { return placa; }
    public String getDataRetirada() { return dataRetirada; }
    public String getPrevisaoDevolucao() { return previsaoDevolucao; }
    public Button getAcoes() { return acoes; }
}