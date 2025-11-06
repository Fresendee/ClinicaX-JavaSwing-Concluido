package view;

import dao.Medicodao;
import model.Medico;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.*;
import java.util.ArrayList;

/**
 * Tela de gerenciamento de Médicos
 */
public class Medicos extends JFrame {
    
    private JTextField txtNome, txtCrm, txtEspecialidade, txtTelefone, txtEmail, txtEndereco, txtBuscar;
    private JButton btnNovo, btnSalvar, btnEditar, btnExcluir, btnBuscar, btnVoltar;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private Medicodao dao;
    private int idSelecionado = 0;
    
    public Medicos() {
        dao = new Medicodao();
        inicializarComponentes();
        carregarTabela();
    }
    
    private void inicializarComponentes() {
        setTitle("Gerenciamento de Médicos");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                voltarParaMenuPrincipal();
            }
        });
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        
        // Painel superior - Formulário
        add(criarPainelFormulario(), BorderLayout.NORTH);
        
        // Painel central - Tabela
        add(criarPainelTabela(), BorderLayout.CENTER);
        
        // Painel inferior - Botões
        add(criarPainelBotoes(), BorderLayout.SOUTH);
    }
    
    private JPanel criarPainelFormulario() {
        JPanel painel = new JPanel(new GridLayout(3, 4, 10, 10));
        painel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(90, 70, 200)), "Dados do Médico", 
	            javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), new Color(90, 70, 200)));
        
        painel.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        painel.add(txtNome);
        
        painel.add(new JLabel("CRM:"));
        txtCrm = new JTextField();
        painel.add(txtCrm);
        
        painel.add(new JLabel("Especialidade:"));
        txtEspecialidade = new JTextField();
        painel.add(txtEspecialidade);
        
        painel.add(new JLabel("Telefone:"));
        txtTelefone = new JTextField();
        painel.add(txtTelefone);
        
        painel.add(new JLabel("E-mail:"));
        txtEmail = new JTextField();
        painel.add(txtEmail);
        
        painel.add(new JLabel("Endereço:"));
        txtEndereco = new JTextField();
        painel.add(txtEndereco);
        
        return painel;
    }
    
    private JPanel criarPainelTabela() {
        JPanel painel = new JPanel(new BorderLayout(5, 5));
        
        // Painel de busca
        JPanel painelBusca = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelBusca.add(new JLabel("Buscar por nome:"));
	        painelBusca.setBackground(Color.WHITE);
        txtBuscar = new JTextField(20);
        painelBusca.add(txtBuscar);
        btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> buscarMedicos());
	        btnBuscar.setBackground(new Color(150, 150, 150)); // Cinza mais neutro
	        btnBuscar.setForeground(Color.WHITE);
	        btnBuscar.setFont(new Font("Arial", Font.BOLD, 14));
        painelBusca.add(btnBuscar);
        
        painel.add(painelBusca, BorderLayout.NORTH);
        
        // Tabela
        String[] colunas = {"ID", "Nome", "CRM", "Especialidade", "Telefone", "E-mail", "Endereço"};
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
        btnSalvar.addActionListener(e -> salvarMedico());
        
        btnEditar = new JButton("Editar");
	        btnEditar.setBackground(new Color(241, 196, 15)); // Amarelo
        btnEditar.setForeground(Color.BLACK);
        btnEditar.setFont(new Font("Arial", Font.BOLD, 14));
        btnEditar.addActionListener(e -> editarMedico());
        
        btnExcluir = new JButton("Excluir");
	        btnExcluir.setBackground(new Color(231, 76, 60)); // Vermelho
        btnExcluir.setForeground(Color.WHITE);
        btnExcluir.setFont(new Font("Arial", Font.BOLD, 14));
        btnExcluir.addActionListener(e -> excluirMedico());
        
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
        ArrayList<Medico> lista = dao.listarTodos();
        
        for (Medico m : lista) {
            Object[] linha = {
                m.getId(),
                m.getNome(),
                m.getCrm(),
                m.getEspecialidade(),
                m.getTelefone(),
                m.getEmail(),
                m.getEndereco()
            };
            modeloTabela.addRow(linha);
        }
    }
    
    private void salvarMedico() {
        if (!validarCampos()) {
            return;
        }
        
        Medico medico = new Medico();
        medico.setNome(txtNome.getText().trim());
        medico.setCrm(txtCrm.getText().trim());
        medico.setEspecialidade(txtEspecialidade.getText().trim());
        medico.setTelefone(txtTelefone.getText().trim());
        medico.setEmail(txtEmail.getText().trim());
        medico.setEndereco(txtEndereco.getText().trim());
        
        if (dao.inserir(medico)) {
            JOptionPane.showMessageDialog(this, "Médico salvo com sucesso!");
            carregarTabela();
            limparCampos();
        }
    }
    
    private void editarMedico() {
        if (idSelecionado == 0) {
            JOptionPane.showMessageDialog(this, "Selecione um médico na tabela!");
            return;
        }
        
        if (!validarCampos()) {
            return;
        }
        
        Medico medico = new Medico();
        medico.setId(idSelecionado);
        medico.setNome(txtNome.getText().trim());
        medico.setCrm(txtCrm.getText().trim());
        medico.setEspecialidade(txtEspecialidade.getText().trim());
        medico.setTelefone(txtTelefone.getText().trim());
        medico.setEmail(txtEmail.getText().trim());
        medico.setEndereco(txtEndereco.getText().trim());
        
        if (dao.atualizar(medico)) {
            JOptionPane.showMessageDialog(this, "Médico atualizado com sucesso!");
            carregarTabela();
            limparCampos();
        }
    }
    
    private void excluirMedico() {
        if (idSelecionado == 0) {
            JOptionPane.showMessageDialog(this, "Selecione um médico na tabela!");
            return;
        }
        
        int resposta = JOptionPane.showConfirmDialog(this, 
            "Deseja realmente excluir este médico?", 
            "Confirmação", 
            JOptionPane.YES_NO_OPTION);
        
        if (resposta == JOptionPane.YES_OPTION) {
            if (dao.excluir(idSelecionado)) {
                JOptionPane.showMessageDialog(this, "Médico excluído com sucesso!");
                carregarTabela();
                limparCampos();
            }
        }
    }
    
    private void buscarMedicos() {
        String termo = txtBuscar.getText().trim();
        
        if (termo.isEmpty()) {
            carregarTabela();
            return;
        }
        
        modeloTabela.setRowCount(0);
        ArrayList<Medico> lista = dao.buscarPorNome(termo);
        
        for (Medico m : lista) {
            Object[] linha = {
                m.getId(),
                m.getNome(),
                m.getCrm(),
                m.getEspecialidade(),
                m.getTelefone(),
                m.getEmail(),
                m.getEndereco()
            };
            modeloTabela.addRow(linha);
        }
    }
    
    private void preencherFormulario() {
        int linha = tabela.getSelectedRow();
        if (linha != -1) {
            idSelecionado = (int) modeloTabela.getValueAt(linha, 0);
            txtNome.setText((String) modeloTabela.getValueAt(linha, 1));
            txtCrm.setText((String) modeloTabela.getValueAt(linha, 2));
            txtEspecialidade.setText((String) modeloTabela.getValueAt(linha, 3));
            txtTelefone.setText((String) modeloTabela.getValueAt(linha, 4));
            txtEmail.setText((String) modeloTabela.getValueAt(linha, 5));
            txtEndereco.setText((String) modeloTabela.getValueAt(linha, 6));
        }
    }
    
    private void limparCampos() {
        idSelecionado = 0;
        txtNome.setText("");
        txtCrm.setText("");
        txtEspecialidade.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");
        txtEndereco.setText("");
        txtBuscar.setText("");
        tabela.clearSelection();
        txtNome.requestFocus();
    }
    
    private boolean validarCampos() {
        if (txtNome.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "O campo Nome é obrigatório!");
            txtNome.requestFocus();
            return false;
        }
        
        if (txtCrm.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "O campo CRM é obrigatório!");
            txtCrm.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private void voltarParaMenuPrincipal() {
        this.dispose();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Medicos().setVisible(true);
        });
    }
}
