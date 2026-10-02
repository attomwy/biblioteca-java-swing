package controller;

import conexao.Conexao;
import model.Emprestimo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmprestimoController {

    public boolean salvar(Emprestimo emprestimo) {
        String sql = "INSERT INTO emprestimo (livro_id, usuario_id, data) VALUES (?, ?, ?)";
        Connection conexao = Conexao.conectar();
        if (conexao == null) return false;

        try (Connection c = conexao; PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, emprestimo.getLivroId());
            ps.setInt(2, emprestimo.getUsuarioId());
            ps.setString(3, emprestimo.getData());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao salvar emprestimo: " + e.getMessage());
            return false;
        }
    }

    public List<Emprestimo> listar() {
        List<Emprestimo> lista = new ArrayList<>();
        String sql = "SELECT * FROM emprestimo ORDER BY id";
        Connection conexao = Conexao.conectar();
        if (conexao == null) return lista;

        try (Connection c = conexao;
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Emprestimo(rs.getInt("id"), rs.getInt("livro_id"), rs.getInt("usuario_id"), rs.getString("data")));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar emprestimo: " + e.getMessage());
        }
        return lista;
    }

    public boolean alterar(Emprestimo emprestimo) {
        String sql = "UPDATE emprestimo SET livro_id = ?, usuario_id = ?, data = ? WHERE id = ?";
        Connection conexao = Conexao.conectar();
        if (conexao == null) return false;

        try (Connection c = conexao; PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, emprestimo.getLivroId());
            ps.setInt(2, emprestimo.getUsuarioId());
            ps.setString(3, emprestimo.getData());
            ps.setInt(4, emprestimo.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao alterar emprestimo: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM emprestimo WHERE id = ?";
        Connection conexao = Conexao.conectar();
        if (conexao == null) return false;

        try (Connection c = conexao; PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir emprestimo: " + e.getMessage());
            return false;
        }
    }
}
