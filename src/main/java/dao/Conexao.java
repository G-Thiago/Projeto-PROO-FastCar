package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static final String URL = "jdbc:mysql://localhost:3306/fastcar";

    private static final String USUARIO = "fastcar_app";

    private static final String SENHA = "FastCarTeste2026!";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection( URL, USUARIO, SENHA );
    }
}