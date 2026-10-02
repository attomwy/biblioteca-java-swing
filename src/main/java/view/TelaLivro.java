package view;

import controller.LivroController;
import model.Livro;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TelaLivro extends JFrame {
    private final JTextField txtTitulo = new JTextField();
    private final JTextField txtAutor = new JTextField();
    private final JTextField txtCategoriaId = new JTextField();
    private final DefaultTableModel modeloTabela = new DefaultTableModel(new Object[]{"ID", "Titulo", "Autor", "Categoria ID"}, 0);
    private final JTable tabela = new JTable(modeloTabela);
    private final LivroController controller = new LivroController();
    private int idSelecionado = 0;

    public TelaLivro() {
        setTitle("Livros");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        montarTela();
        carregarTabela();
    }

    private void montarTela() {
        JPanel formulario = new JPanel(new GridLayout(3, 2, 5, 5));
        formulario.add(new JLabel("Titulo:"));
        formulario.add(txtTitulo);
        formulario.add(new JLabel("Autor:"));
        formulario.add(txtAutor);
        formulario.add(new JLabel("ID da categoria:"));
        formulario.add(txtCategoriaId);

        JPanel botoes = new JPanel();
        JButton salvar = new JButton("Salvar");
        JButton alterar = new JButton("Alterar");
        JButton excluir = new JButton("Excluir");
        JButton atualizar = new JButton("Atualizar");
        botoes.add(salvar);
        botoes.add(alterar);
        botoes.add(excluir);
        botoes.add(atualizar);

        add(formulario, BorderLayout.NORTH);
        add(new JScrollPane(tabela), BorderLayout.CENTER);
        add(botoes, BorderLayout.SOUTH);

        salvar.addActionListener(e -> salvar());
        alterar.addActionListener(e -> alterar());
        excluir.addActionListener(e -> excluir());
        atualizar.addActionListener(e -> carregarTabela());
        tabela.getSelectionModel().addListSelectionListener(e -> selecionarLinha());
    }

    private Livro lerFormulario() {
        try {
            int categoriaId = Integer.parseInt(txtCategoriaId.getText());
            return new Livro(idSelecionado, txtTitulo.getText(), txtAutor.getText(), categoriaId);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "O ID da categoria deve ser um numero.");
            return null;
        }
    }

    private void salvar() {
        Livro livro = lerFormulario();
        if (livro == null || txtTitulo.getText().trim().isEmpty() || txtAutor.getText().trim().isEmpty()) return;
        livro.setId(0);
        boolean ok = controller.salvar(livro);
        JOptionPane.showMessageDialog(this, ok ? "Livro salvo." : "Nao foi possivel salvar.");
        if (ok) limparEAtualizar();
    }

    private void alterar() {
        if (idSelecionado == 0) {
            JOptionPane.showMessageDialog(this, "Selecione um livro.");
            return;
        }
        Livro livro = lerFormulario();
        if (livro == null) return;
        boolean ok = controller.alterar(livro);
        JOptionPane.showMessageDialog(this, ok ? "Livro alterado." : "Nao foi possivel alterar.");
        if (ok) limparEAtualizar();
    }

    private void excluir() {
        if (idSelecionado == 0) {
            JOptionPane.showMessageDialog(this, "Selecione um livro.");
            return;
        }
        boolean ok = controller.excluir(idSelecionado);
        JOptionPane.showMessageDialog(this, ok ? "Livro excluido." : "Nao foi possivel excluir.");
        if (ok) limparEAtualizar();
    }

    private void selecionarLinha() {
        int linha = tabela.getSelectedRow();
        if (linha >= 0) {
            idSelecionado = (int) modeloTabela.getValueAt(linha, 0);
            txtTitulo.setText(modeloTabela.getValueAt(linha, 1).toString());
            txtAutor.setText(modeloTabela.getValueAt(linha, 2).toString());
            txtCategoriaId.setText(modeloTabela.getValueAt(linha, 3).toString());
        }
    }

    private void carregarTabela() {
        modeloTabela.setRowCount(0);
        for (Livro item : controller.listar()) {
            modeloTabela.addRow(new Object[]{item.getId(), item.getTitulo(), item.getAutor(), item.getCategoriaId()});
        }
    }

    private void limparEAtualizar() {
        txtTitulo.setText("");
        txtAutor.setText("");
        txtCategoriaId.setText("");
        idSelecionado = 0;
        carregarTabela();
    }
}
