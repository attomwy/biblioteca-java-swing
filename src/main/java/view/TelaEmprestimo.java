package view;

import controller.EmprestimoController;
import model.Emprestimo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TelaEmprestimo extends JFrame {
    private final JTextField txtLivroId = new JTextField();
    private final JTextField txtUsuarioId = new JTextField();
    private final JTextField txtData = new JTextField();
    private final DefaultTableModel modeloTabela = new DefaultTableModel(new Object[]{"ID", "Livro ID", "Usuario ID", "Data"}, 0);
    private final JTable tabela = new JTable(modeloTabela);
    private final EmprestimoController controller = new EmprestimoController();
    private int idSelecionado = 0;

    public TelaEmprestimo() {
        setTitle("Emprestimos");
        setSize(650, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        montarTela();
        carregarTabela();
    }

    private void montarTela() {
        JPanel formulario = new JPanel(new GridLayout(3, 2, 5, 5));
        formulario.add(new JLabel("ID do livro:"));
        formulario.add(txtLivroId);
        formulario.add(new JLabel("ID do usuario:"));
        formulario.add(txtUsuarioId);
        formulario.add(new JLabel("Data (AAAA-MM-DD):"));
        formulario.add(txtData);

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

    private Emprestimo lerFormulario() {
        try {
            int livroId = Integer.parseInt(txtLivroId.getText());
            int usuarioId = Integer.parseInt(txtUsuarioId.getText());
            return new Emprestimo(idSelecionado, livroId, usuarioId, txtData.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Os IDs devem ser numeros.");
            return null;
        }
    }

    private void salvar() {
        Emprestimo emprestimo = lerFormulario();
        if (emprestimo == null || txtData.getText().trim().isEmpty()) return;
        emprestimo.setId(0);
        boolean ok = controller.salvar(emprestimo);
        JOptionPane.showMessageDialog(this, ok ? "Emprestimo salvo." : "Nao foi possivel salvar.");
        if (ok) limparEAtualizar();
    }

    private void alterar() {
        if (idSelecionado == 0) {
            JOptionPane.showMessageDialog(this, "Selecione um emprestimo.");
            return;
        }
        Emprestimo emprestimo = lerFormulario();
        if (emprestimo == null) return;
        boolean ok = controller.alterar(emprestimo);
        JOptionPane.showMessageDialog(this, ok ? "Emprestimo alterado." : "Nao foi possivel alterar.");
        if (ok) limparEAtualizar();
    }

    private void excluir() {
        if (idSelecionado == 0) {
            JOptionPane.showMessageDialog(this, "Selecione um emprestimo.");
            return;
        }
        boolean ok = controller.excluir(idSelecionado);
        JOptionPane.showMessageDialog(this, ok ? "Emprestimo excluido." : "Nao foi possivel excluir.");
        if (ok) limparEAtualizar();
    }

    private void selecionarLinha() {
        int linha = tabela.getSelectedRow();
        if (linha >= 0) {
            idSelecionado = (int) modeloTabela.getValueAt(linha, 0);
            txtLivroId.setText(modeloTabela.getValueAt(linha, 1).toString());
            txtUsuarioId.setText(modeloTabela.getValueAt(linha, 2).toString());
            txtData.setText(modeloTabela.getValueAt(linha, 3).toString());
        }
    }

    private void carregarTabela() {
        modeloTabela.setRowCount(0);
        for (Emprestimo item : controller.listar()) {
            modeloTabela.addRow(new Object[]{item.getId(), item.getLivroId(), item.getUsuarioId(), item.getData()});
        }
    }

    private void limparEAtualizar() {
        txtLivroId.setText("");
        txtUsuarioId.setText("");
        txtData.setText("");
        idSelecionado = 0;
        carregarTabela();
    }
}
