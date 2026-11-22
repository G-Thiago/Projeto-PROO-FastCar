package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.time.format.DateTimeFormatter;
import view.TelaDevolucoes.ModelDevolucaoTabela;

public class LocacaoDAO {

    public static class ItemCliente {
        private final int id;
        private final String nome;
        private final String cpf;
        private final String telefone;
        private final String cnh;

        public ItemCliente(int id, String nome, String cpf, String telefone, String cnh) {
            this.id = id;
            this.nome = nome;
            this.cpf = cpf;
            this.telefone = telefone;
            this.cnh = cnh;
        }

        public int getId() { return id; }
        public String getNome() { return nome; }
        public String getCpf() { return cpf; }
        public String getTelefone() { return telefone; }
        public String getCnh() { return cnh; }

        @Override
        public String toString() {
            return nome + " (" + cpf + ")";
        }
    }

    public static class ItemVeiculo {
        private final int id;
        private final String marca;
        private final String modelo;
        private final String placa;
        private final double valorDiaria;
        private final String disponibilidade;

        public ItemVeiculo(int id, String marca, String modelo, String placa, double valorDiaria, String disponibilidade) {
            this.id = id;
            this.marca = marca;
            this.modelo = modelo;
            this.placa = placa;
            this.valorDiaria = valorDiaria;
            this.disponibilidade = disponibilidade;
        }

        public int getId() { return id; }
        public String getMarca() { return marca; }
        public String getModelo() { return modelo; }
        public String getPlaca() { return placa; }
        public double getValorDiaria() { return valorDiaria; }
        public String getDisponibilidade() { return disponibilidade; }

        @Override
        public String toString() {
            return modelo + " - " + placa + " (" + disponibilidade + ")";
        }
    }

    public List<ItemCliente> listarClientes() {
        List<ItemCliente> lista = new ArrayList<>();
        String sql = "SELECT id, nome, cpf, telefone, cnh FROM clientes ORDER BY nome";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new ItemCliente(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("telefone"),
                        rs.getString("cnh")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<ItemVeiculo> listarVeiculos() {
        List<ItemVeiculo> lista = new ArrayList<>();
        String sql = "SELECT id, marca, modelo, placa, valor_diaria, disponibilidade FROM veiculos ORDER BY modelo";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new ItemVeiculo(
                        rs.getInt("id"),
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getString("placa"),
                        rs.getDouble("valor_diaria"),
                        rs.getString("disponibilidade")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean registrarLocacao(
            int clienteId,
            int veiculoId,
            LocalDate dataRetirada,
            LocalDate dataDevolucaoPrevista,
            double valorTotal) {

        String sqlInsert = "INSERT INTO locacoes "
                + "(cliente_id, veiculo_id, data_retirada, data_devolucao_prevista, valor_total) "
                + "VALUES (?, ?, ?, ?, ?)";

        String sqlUpdateVeiculo = "UPDATE veiculos SET disponibilidade = 'Alugado' WHERE id = ?";

        try (Connection conexao = Conexao.conectar()) {
            conexao.setAutoCommit(false);

            try (PreparedStatement stmtInsert = conexao.prepareStatement(sqlInsert);
                 PreparedStatement stmtUpdate = conexao.prepareStatement(sqlUpdateVeiculo)) {

                stmtInsert.setInt(1, clienteId);
                stmtInsert.setInt(2, veiculoId);
                stmtInsert.setDate(3, Date.valueOf(dataRetirada));
                stmtInsert.setDate(4, Date.valueOf(dataDevolucaoPrevista));
                stmtInsert.setDouble(5, valorTotal);
                stmtInsert.executeUpdate();

                stmtUpdate.setInt(1, veiculoId);
                stmtUpdate.executeUpdate();

                conexao.commit();
                return true;

            } catch (SQLException ex) {
                conexao.rollback();
                ex.printStackTrace();
                return false;
            } finally {
                conexao.setAutoCommit(true);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<ModelDevolucaoTabela> listarEmAndamento() {
        List<ModelDevolucaoTabela> lista = new ArrayList<>();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String sql = "SELECT l.id, c.nome AS cliente_nome, v.modelo AS veiculo_modelo, "
                + "v.placa, l.data_retirada, l.data_devolucao_prevista "
                + "FROM locacoes l "
                + "JOIN clientes c ON l.cliente_id = c.id "
                + "JOIN veiculos v ON l.veiculo_id = v.id "
                + "LEFT JOIN devolucoes d ON d.locacao_id = l.id "
                + "WHERE d.id IS NULL "
                + "ORDER BY l.id DESC";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String dataRet = rs.getDate("data_retirada").toLocalDate().format(fmt);
                String dataPrev = rs.getDate("data_devolucao_prevista").toLocalDate().format(fmt);

                lista.add(new ModelDevolucaoTabela(
                        String.valueOf(rs.getInt("id")),
                        rs.getString("cliente_nome"),
                        rs.getString("veiculo_modelo"),
                        rs.getString("placa"),
                        dataRet,
                        dataPrev
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public boolean registrarDevolucao(
            int locacaoId,
            LocalDate dataDevolucao) {

        String sqlInsertDevolucao = "INSERT INTO devolucoes "
                + "(locacao_id, data_devolucao, veiculo_limpo, sem_avarias, tanque_verificado) "
                + "VALUES (?, ?, ?, ?, ?)";

        String sqlBuscarVeiculo = "SELECT veiculo_id FROM locacoes WHERE id = ?";

        String sqlAtualizarVeiculo = "UPDATE veiculos SET disponibilidade = 'Disponível' WHERE id = ?";

        try (Connection conexao = Conexao.conectar()) {
            conexao.setAutoCommit(false);

            try (PreparedStatement stmtInsert = conexao.prepareStatement(sqlInsertDevolucao);
                 PreparedStatement stmtBuscar = conexao.prepareStatement(sqlBuscarVeiculo)) {

                stmtInsert.setInt(1, locacaoId);
                stmtInsert.setDate(2, Date.valueOf(dataDevolucao));
                stmtInsert.setBoolean(3, false);
                stmtInsert.setBoolean(4, false);
                stmtInsert.setBoolean(5, false);
                stmtInsert.executeUpdate();

                stmtBuscar.setInt(1, locacaoId);
                int veiculoId = 0;
                try (ResultSet rs = stmtBuscar.executeQuery()) {
                    if (rs.next()) {
                        veiculoId = rs.getInt("veiculo_id");
                    }
                }

                if (veiculoId > 0) {
                    try (PreparedStatement stmtUpdate = conexao.prepareStatement(sqlAtualizarVeiculo)) {
                        stmtUpdate.setInt(1, veiculoId);
                        stmtUpdate.executeUpdate();
                    }
                }

                conexao.commit();
                return true;

            } catch (SQLException ex) {
                conexao.rollback();
                ex.printStackTrace();
                return false;
            } finally {
                conexao.setAutoCommit(true);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
