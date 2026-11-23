package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.ModelClienteTabela;

public class ClientesDAO {

    public boolean cadastrar(
            String nome,
            String cpf,
            String telefone,
            String cnh) {

        String sql = "INSERT INTO clientes "
                + "(nome, cpf, telefone, cnh) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setString(2, cpf);
            stmt.setString(3, telefone);
            stmt.setString(4, cnh);

            stmt.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<ModelClienteTabela> listar() {
        List<ModelClienteTabela> clientes = new ArrayList<>();

        String sql = "SELECT * FROM clientes";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet resultado = stmt.executeQuery()) {

            while (resultado.next()) {
                ModelClienteTabela cliente = new ModelClienteTabela(
                        String.valueOf(resultado.getInt("id")),
                        resultado.getString("nome"),
                        resultado.getString("cpf"),
                        resultado.getString("telefone"),
                        resultado.getString("cnh"),
                        "-"
                );

                clientes.add(cliente);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clientes;
    }

    public boolean possuiLocacoes(int clienteId) {
        String sql = "SELECT COUNT(*) FROM locacoes WHERE cliente_id = ?";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, clienteId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean atualizar(int id, String nome, String cpf, String telefone, String cnh) {
        String sql = "UPDATE clientes SET nome = ?, cpf = ?, telefone = ?, cnh = ? WHERE id = ?";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setString(2, cpf);
            stmt.setString(3, telefone);
            stmt.setString(4, cnh);
            stmt.setInt(5, id);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM clientes WHERE id = ?";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}