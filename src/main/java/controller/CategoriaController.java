package controller;

import conexao.Conexao;
import model.Categoria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaController {

    public boolean salvar(Categoria categoria) {
        String sql = "INSERT INTO categoria (nome) VALUES (?)";
        Connection conexao = Conexao.conectar();
        if (conexao == null) return false;

        try (Connection c = conexao; PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, categoria.getNome());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao salvar categoria: " + e.getMessage());
            return false;
        }
    }

    public List<Categoria> listar() {
        List<Categoria> lista = new ArrayList<>();
        String sql = "SELECT * FROM categoria ORDER BY id";
        Connection conexao = Conexao.conectar();
        if (conexao == null) return lista;

        try (Connection c = conexao;
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Categoria(rs.getInt("id"), rs.getString("nome")));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar categoria: " + e.getMessage());
        }
        return lista;
    }

    public boolean alterar(Categoria categoria) {
        String sql = "UPDATE categoria SET nome = ? WHERE id = ?";
        Connection conexao = Conexao.conectar();
        if (conexao == null) return false;

        try (Connection c = conexao; PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, categoria.getNome());
            ps.setInt(2, categoria.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao alterar categoria: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM categoria WHERE id = ?";
        Connection conexao = Conexao.conectar();
        if (conexao == null) return false;

        try (Connection c = conexao; PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir categoria: " + e.getMessage());
            return false;
        }
    }
}
