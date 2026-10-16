package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexao {
    private static final String URL = "jdbc:sqlite:biblioteca.db";

    public static Connection conectar() {
        try {
            Connection c = DriverManager.getConnection(URL);
            try (Statement st = c.createStatement()) {
                st.execute("PRAGMA foreign_keys = ON");
                st.execute("CREATE TABLE IF NOT EXISTS categoria (id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT NOT NULL)");
                st.execute("CREATE TABLE IF NOT EXISTS usuario (id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT NOT NULL)");
                st.execute("CREATE TABLE IF NOT EXISTS livro (id INTEGER PRIMARY KEY AUTOINCREMENT, titulo TEXT NOT NULL, autor TEXT NOT NULL, categoria_id INTEGER NOT NULL, FOREIGN KEY (categoria_id) REFERENCES categoria(id))");
                st.execute("CREATE TABLE IF NOT EXISTS emprestimo (id INTEGER PRIMARY KEY AUTOINCREMENT, livro_id INTEGER NOT NULL, usuario_id INTEGER NOT NULL, data TEXT NOT NULL, FOREIGN KEY (livro_id) REFERENCES livro(id), FOREIGN KEY (usuario_id) REFERENCES usuario(id))");
            }
            return c;
        } catch (SQLException e) {
            System.out.println("Nao foi possivel conectar ao banco: " + e.getMessage());
            return null;
        }
    }
}
