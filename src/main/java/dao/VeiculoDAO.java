package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import view.TelaVeiculos.ModelVeiculoTabela;

public class VeiculoDAO {

    public boolean cadastrar(
            String marca,
            String modelo,
            int ano,
            String placa,
            double valorDiaria,
            String disponibilidade) {

        String sql = "INSERT INTO veiculos "
                + "(marca, modelo, ano, placa, valor_diaria, disponibilidade) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, marca);
            stmt.setString(2, modelo);
            stmt.setInt(3, ano);
            stmt.setString(4, placa);
            stmt.setDouble(5, valorDiaria);
            stmt.setString(6, disponibilidade);

            stmt.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<ModelVeiculoTabela> listar() {
        List<ModelVeiculoTabela> veiculos = new ArrayList<>();

        String sql = "SELECT * FROM veiculos";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet resultado = stmt.executeQuery()) {

            while (resultado.next()) {
                ModelVeiculoTabela veiculo = new ModelVeiculoTabela(
                        String.valueOf(resultado.getInt("id")),
                        "-",
                        resultado.getString("marca"),
                        resultado.getString("modelo"),
                        String.valueOf(resultado.getInt("ano")),
                        resultado.getString("placa"),
                        String.format(
                                Locale.forLanguageTag("pt-BR"),
                                "R$ %.2f",
                                resultado.getDouble("valor_diaria")
                        ),
                        resultado.getString("disponibilidade"),
                        "-"
                );

                veiculos.add(veiculo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return veiculos;
    }
    public boolean atualizarDisponibilidade(int id, String novaDisponibilidade) {
        String sql = "UPDATE veiculos SET disponibilidade = ? WHERE id = ?";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, novaDisponibilidade);
            stmt.setInt(2, id);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluir(int id) {
        String sqlDeleteDevolucoes = "DELETE FROM devolucoes WHERE locacao_id IN (SELECT id FROM locacoes WHERE veiculo_id = ?)";
        String sqlDeleteLocacoes = "DELETE FROM locacoes WHERE veiculo_id = ?";
        String sqlDeleteVeiculo = "DELETE FROM veiculos WHERE id = ?";

        try (Connection conexao = Conexao.conectar()) {
            conexao.setAutoCommit(false);

            try (PreparedStatement stmtDev = conexao.prepareStatement(sqlDeleteDevolucoes);
                 PreparedStatement stmtLoc = conexao.prepareStatement(sqlDeleteLocacoes);
                 PreparedStatement stmtVei = conexao.prepareStatement(sqlDeleteVeiculo)) {

                stmtDev.setInt(1, id);
                stmtDev.executeUpdate();

                stmtLoc.setInt(1, id);
                stmtLoc.executeUpdate();

                stmtVei.setInt(1, id);
                int linhasAfetadas = stmtVei.executeUpdate();

                conexao.commit();
                return linhasAfetadas > 0;

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
