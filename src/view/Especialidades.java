package view;

import dao.Especialidadedao;
import model.Especialidade;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.*;
import java.util.ArrayList;

/**
 * Tela de gerenciamento de Especialidades
 */
public class Especialidades extends JFrame {
    
    private JTextField txtNome, txtBuscar;
    private JButton btnNovo, btnSalvar, btnEditar, btnExcluir, btnBuscar, btnVoltar;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private Especialidadedao dao;
    private int idSelecionado = 0;
    
    public Especialidades() {
        dao = new Especialidadedao();
        inicializarComponentes();
        carregarTabela();
    }
    
    private void inicializarComponentes() {
        setTitle("Gerenciamento de Especialidades");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                voltarParaMenuPrincipal();
            }
        });
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        
        add(criarPainelFormulario(), BorderLayout.NORTH);
        
        add(criarPainelTabela(), BorderLayout.CENTER);
        
        add(criarPainelBotoes(), BorderLayout.SOUTH);
    }
    
    private JPanel criarPainelFormulario() {
        JPanel painel = new JPanel(new GridLayout(1, 2, 10, 10));
        painel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(90, 70, 200)), "Dados da Especialidade", 
	            javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), new Color(90, 70, 200)));
        
        painel.add(new JLabel("Nome da Especialidade:"));
        txtNome = new JTextField();
        painel.add(txtNome);
        
        return painel;
    }
    
    private JPanel criarPainelTabela() {
        JPanel painel = new JPanel(new BorderLayout(5, 5));
        
        JPanel painelBusca = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelBusca.add(new JLabel("Buscar por nome:"));
	        painelBusca.setBackground(Color.WHITE);
        txtBuscar = new JTextField(20);
        painelBusca.add(txtBuscar);
        btnBuscar = new JButton("Buscar");
	        btnBuscar.setBackground(new Color(150, 150, 150)); // Cinza mais neutro
	        btnBuscar.setForeground(Color.WHITE);
	        btnBuscar.setFont(new Font("Arial", Font.BOLD, 14));
        btnBuscar.addActionListener(e -> buscarEspecialidades());
        painelBusca.add(btnBuscar);
        
        painel.add(painelBusca, BorderLayout.NORTH);
        
        String[] colunas = {"ID", "Nome da Especialidade"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tabela = new JTable(modeloTabela);
        tabela.setFillsViewportHeight(true);
        tabela.setFont(new Font("Arial", Font.PLAIN, 12));
        tabela.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
	        tabela.getTableHeader().setBackground(new Color(90, 70, 200));
	        tabela.getTableHeader().setForeground(Color.WHITE);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                preencherFormulario();
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(tabela);
        painel.add(scrollPane, BorderLayout.CENTER);
        
        return painel;
    }
    
    private JPanel criarPainelBotoes() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        btnNovo = new JButton("Novo");
	        btnNovo.setBackground(new Color(46, 204, 113)); // Verde
        btnNovo.setForeground(Color.WHITE);
        btnNovo.setFont(new Font("Arial", Font.BOLD, 14));
        btnNovo.addActionListener(e -> limparCampos());
        
        btnSalvar = new JButton("Salvar");
	        btnSalvar.setBackground(new Color(90, 70, 200)); // Roxo
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setFont(new Font("Arial", Font.BOLD, 14));
        btnSalvar.addActionListener(e -> salvarEspecialidade());
        
        btnEditar = new JButton("Editar");
	        btnEditar.setBackground(new Color(241, 196, 15)); // Amarelo
        btnEditar.setForeground(Color.BLACK);
        btnEditar.setFont(new Font("Arial", Font.BOLD, 14));
        btnEditar.addActionListener(e -> editarEspecialidade());
        
        btnExcluir = new JButton("Excluir");
	        btnExcluir.setBackground(new Color(231, 76, 60)); // Vermelho
        btnExcluir.setForeground(Color.WHITE);
        btnExcluir.setFont(new Font("Arial", Font.BOLD, 14));
        btnExcluir.addActionListener(e -> excluirEspecialidade());
        
        btnVoltar = new JButton("Voltar");
	        btnVoltar.setBackground(new Color(90, 70, 200).darker()); // Roxo Escuro
        btnVoltar.setForeground(Color.WHITE);
        btnVoltar.setFont(new Font("Arial", Font.BOLD, 14));
        btnVoltar.addActionListener(e -> voltarParaMenuPrincipal());
        
        painel.add(btnNovo);
        painel.add(btnSalvar);
        painel.add(btnEditar);
        painel.add(btnExcluir);
        painel.add(btnVoltar);
        
        return painel;
    }
    
    private void carregarTabela() {
        modeloTabela.setRowCount(0);
        ArrayList<Especialidade> lista = dao.listarTodos();
        
        for (Especialidade e : lista) {
            Object[] linha = {
                e.getId(),
                e.getNome()
            };
            modeloTabela.addRow(linha);
        }
    }
    
    private void salvarEspecialidade() {
        if (!validarCampos()) {
            return;
        }
        
        Especialidade especialidade = new Especialidade();
        especialidade.setNome(txtNome.getText().trim());
        
        if (dao.inserir(especialidade)) {
            JOptionPane.showMessageDialog(this, "Especialidade salva com sucesso!");
            carregarTabela();
            limparCampos();
        }
    }
    
    private void editarEspecialidade() {
        if (idSelecionado == 0) {
            JOptionPane.showMessageDialog(this, "Selecione uma especialidade na tabela!");
            return;
        }
        
        if (!validarCampos()) {
            return;
        }
        
        Especialidade especialidade = new Especialidade();
        especialidade.setId(idSelecionado);
        especialidade.setNome(txtNome.getText().trim());
        
        if (dao.atualizar(especialidade)) {
            JOptionPane.showMessageDialog(this, "Especialidade atualizada com sucesso!");
            carregarTabela();
            limparCampos();
        }
    }
    
    private void excluirEspecialidade() {
        if (idSelecionado == 0) {
            JOptionPane.showMessageDialog(this, "Selecione uma especialidade na tabela!");
            return;
        }
        
        int resposta = JOptionPane.showConfirmDialog(this, 
            "Deseja realmente excluir esta especialidade?", 
            "Confirmação", 
            JOptionPane.YES_NO_OPTION);
        
        if (resposta == JOptionPane.YES_OPTION) {
            if (dao.excluir(idSelecionado)) {
                JOptionPane.showMessageDialog(this, "Especialidade excluída com sucesso!");
                carregarTabela();
                limparCampos();
            }
        }
    }
    
    private void buscarEspecialidades() {
        String termo = txtBuscar.getText().trim();
        
        if (termo.isEmpty()) {
            carregarTabela();
            return;
        }
        
        modeloTabela.setRowCount(0);
        ArrayList<Especialidade> lista = dao.listarTodos();
        
        for (Especialidade e : lista) {
            if (e.getNome().toLowerCase().contains(termo.toLowerCase())) {
                Object[] linha = {
                    e.getId(),
                    e.getNome()
                };
                modeloTabela.addRow(linha);
            }
        }
    }
    
    private void preencherFormulario() {
        int linha = tabela.getSelectedRow();
        if (linha != -1) {
            idSelecionado = (int) modeloTabela.getValueAt(linha, 0);
            txtNome.setText((String) modeloTabela.getValueAt(linha, 1));
        }
    }
    
    private void limparCampos() {
        idSelecionado = 0;
        txtNome.setText("");
        txtBuscar.setText("");
        tabela.clearSelection();
        txtNome.requestFocus();
    }
    
    private boolean validarCampos() {
        if (txtNome.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "O campo Nome da Especialidade é obrigatório!");
            txtNome.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private void voltarParaMenuPrincipal() {
        this.dispose();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Especialidades().setVisible(true);
        });
    }
}
