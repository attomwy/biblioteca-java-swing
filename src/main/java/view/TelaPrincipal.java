package view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class TelaPrincipal extends JFrame {
    public TelaPrincipal() {
        setTitle("Sistema de Biblioteca");
        setSize(420, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel titulo = new JLabel("Sistema de Biblioteca", SwingConstants.CENTER);
        add(titulo, BorderLayout.NORTH);

        JPanel painel = new JPanel(new GridLayout(4, 1, 10, 10));
        JButton btnCategorias = new JButton("Categorias");
        JButton btnLivros = new JButton("Livros");
        JButton btnUsuarios = new JButton("Usuarios");
        JButton btnEmprestimos = new JButton("Emprestimos");

        painel.add(btnCategorias);
        painel.add(btnLivros);
        painel.add(btnUsuarios);
        painel.add(btnEmprestimos);
        add(painel, BorderLayout.CENTER);

        btnCategorias.addActionListener(e -> new TelaCategoria().setVisible(true));
        btnLivros.addActionListener(e -> new TelaLivro().setVisible(true));
        btnUsuarios.addActionListener(e -> new TelaUsuario().setVisible(true));
        btnEmprestimos.addActionListener(e -> new TelaEmprestimo().setVisible(true));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaPrincipal().setVisible(true));
    }
}
