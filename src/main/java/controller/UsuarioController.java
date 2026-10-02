package controller;

import conexao.Conexao;
import model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioController {

    public boolean salvar(Usuario usuario) {
        String sql = "INSERT INTO usuario (nome) VALUES (?)";
        Connection conexao = Conexao.conectar();
        if (conexao == null) return false;

        try (Connection c = conexao; PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, usuario.getNome());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao salvar usuario: " + e.getMessage());
            return false;
        }
    }

    public List<Usuario> listar() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuario ORDER BY id";
        Connection conexao = Conexao.conectar();
        if (conexao == null) return lista;

        try (Connection c = conexao;
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Usuario(rs.getInt("id"), rs.getString("nome")));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar usuario: " + e.getMessage());
        }
        return lista;
    }

    public boolean alterar(Usuario usuario) {
        String sql = "UPDATE usuario SET nome = ? WHERE id = ?";
        Connection conexao = Conexao.conectar();
        if (conexao == null) return false;

        try (Connection c = conexao; PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, usuario.getNome());
            ps.setInt(2, usuario.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao alterar usuario: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM usuario WHERE id = ?";
        Connection conexao = Conexao.conectar();
        if (conexao == null) return false;

        try (Connection c = conexao; PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir usuario: " + e.getMessage());
            return false;
        }
    }
}
