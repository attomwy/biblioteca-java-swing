package controller;

import conexao.Conexao;
import model.Livro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LivroController {

    public boolean salvar(Livro livro) {
        String sql = "INSERT INTO livro (titulo, autor, categoria_id) VALUES (?, ?, ?)";
        Connection conexao = Conexao.conectar();
        if (conexao == null) return false;

        try (Connection c = conexao; PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, livro.getTitulo());
            ps.setString(2, livro.getAutor());
            ps.setInt(3, livro.getCategoriaId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao salvar livro: " + e.getMessage());
            return false;
        }
    }

    public List<Livro> listar() {
        List<Livro> lista = new ArrayList<>();
        String sql = "SELECT * FROM livro ORDER BY id";
        Connection conexao = Conexao.conectar();
        if (conexao == null) return lista;

        try (Connection c = conexao;
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Livro(rs.getInt("id"), rs.getString("titulo"), rs.getString("autor"), rs.getInt("categoria_id")));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar livro: " + e.getMessage());
        }
        return lista;
    }

    public boolean alterar(Livro livro) {
        String sql = "UPDATE livro SET titulo = ?, autor = ?, categoria_id = ? WHERE id = ?";
        Connection conexao = Conexao.conectar();
        if (conexao == null) return false;

        try (Connection c = conexao; PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, livro.getTitulo());
            ps.setString(2, livro.getAutor());
            ps.setInt(3, livro.getCategoriaId());
            ps.setInt(4, livro.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao alterar livro: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM livro WHERE id = ?";
        Connection conexao = Conexao.conectar();
        if (conexao == null) return false;

        try (Connection c = conexao; PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir livro: " + e.getMessage());
            return false;
        }
    }
}
