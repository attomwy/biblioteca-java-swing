package view;

import controller.CategoriaController;
import model.Categoria;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TelaCategoria extends JFrame {
    private final JTextField txtNome = new JTextField();
    private final DefaultTableModel modeloTabela = new DefaultTableModel(new Object[]{"ID", "Nome"}, 0);
    private final JTable tabela = new JTable(modeloTabela);
    private final CategoriaController controller = new CategoriaController();
    private int idSelecionado = 0;

    public TelaCategoria() {
        setTitle("Categorias");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        montarTela();
        carregarTabela();
    }

    private void montarTela() {
        JPanel formulario = new JPanel(new GridLayout(2, 2, 5, 5));
        formulario.add(new JLabel("Nome:"));
        formulario.add(txtNome);

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

    private void salvar() {
        if (txtNome.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite o nome.");
            return;
        }
        boolean ok = controller.salvar(new Categoria(0, txtNome.getText()));
        JOptionPane.showMessageDialog(this, ok ? "Categoria salva." : "Nao foi possivel salvar.");
        if (ok) limparEAtualizar();
    }

    private void alterar() {
        if (idSelecionado == 0) {
            JOptionPane.showMessageDialog(this, "Selecione uma categoria.");
            return;
        }
        boolean ok = controller.alterar(new Categoria(idSelecionado, txtNome.getText()));
        JOptionPane.showMessageDialog(this, ok ? "Categoria alterada." : "Nao foi possivel alterar.");
        if (ok) limparEAtualizar();
    }

    private void excluir() {
        if (idSelecionado == 0) {
            JOptionPane.showMessageDialog(this, "Selecione uma categoria.");
            return;
        }
        boolean ok = controller.excluir(idSelecionado);
        JOptionPane.showMessageDialog(this, ok ? "Categoria excluida." : "Nao foi possivel excluir.");
        if (ok) limparEAtualizar();
    }

    private void selecionarLinha() {
        int linha = tabela.getSelectedRow();
        if (linha >= 0) {
            idSelecionado = (int) modeloTabela.getValueAt(linha, 0);
            txtNome.setText(modeloTabela.getValueAt(linha, 1).toString());
        }
    }

    private void carregarTabela() {
        modeloTabela.setRowCount(0);
        for (Categoria item : controller.listar()) {
            modeloTabela.addRow(new Object[]{item.getId(), item.getNome()});
        }
    }

    private void limparEAtualizar() {
        txtNome.setText("");
        idSelecionado = 0;
        carregarTabela();
    }
}
