package view;

import dao.Consultadao;
import dao.Medicodao;
import dao.Pacientesdao;
import model.Consulta;
import model.Medico;
import model.Paciente;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Tela de gerenciamento de Consultas
 */
public class Consultas extends JFrame {
    
    private JComboBox<String> cmbPaciente, cmbMedico;
    private JTextField txtData, txtHora, txtBuscar;
    private JTextArea txtObservacoes;
    private JButton btnNovo, btnSalvar, btnEditar, btnExcluir, btnBuscar, btnVoltar;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private Consultadao consultaDao;
    private Pacientesdao pacienteDao;
    private Medicodao medicoDao;
    private int idSelecionado = 0;
    
    private Map<String, Integer> mapPacientes;
    private Map<String, Integer> mapMedicos;
    
    public Consultas() {
        try {
            consultaDao = new Consultadao();
            pacienteDao = new Pacientesdao();
            medicoDao = new Medicodao();
            mapPacientes = new HashMap<>();
            mapMedicos = new HashMap<>();
            
            inicializarComponentes();
            
            // Adicionei proteções aqui para evitar travamentos
            carregarComboBoxes();
            carregarTabela();
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao iniciar tela de consultas: " + e.getMessage());
        }
    }
    
    private void inicializarComponentes() {
        setTitle("Gerenciamento de Consultas");
        setSize(1200, 700);
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
        painel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(90, 70, 200)), "Dados da Consulta", 
                javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), new Color(90, 70, 200)));
        
        painel.add(new JLabel("Paciente:"));
        cmbPaciente = new JComboBox<>();
        painel.add(cmbPaciente);
        
        painel.add(new JLabel("Médico:"));
        cmbMedico = new JComboBox<>();
        painel.add(cmbMedico);
        
        painel.add(new JLabel("Data (DD/MM/AAAA):"));
        txtData = new JTextField();
        painel.add(txtData);
        
        painel.add(new JLabel("Hora (HH:MM):"));
        txtHora = new JTextField();
        painel.add(txtHora);
        
        painel.add(new JLabel("Observações:"));
        txtObservacoes = new JTextArea(3, 20);
        JScrollPane scrollObs = new JScrollPane(txtObservacoes);
        painel.add(scrollObs);
        
        return painel;
    }
    
    private JPanel criarPainelTabela() {
        JPanel painel = new JPanel(new BorderLayout(5, 5));
        
        // Painel de busca
        JPanel painelBusca = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelBusca.add(new JLabel("Buscar por paciente:"));
            painelBusca.setBackground(Color.WHITE);
        txtBuscar = new JTextField(20);
        painelBusca.add(txtBuscar);
        btnBuscar = new JButton("Buscar");
            btnBuscar.setBackground(new Color(150, 150, 150)); // Cinza mais neutro
            btnBuscar.setForeground(Color.WHITE);
            btnBuscar.setFont(new Font("Arial", Font.BOLD, 14));
        btnBuscar.addActionListener(e -> buscarConsultas());
        painelBusca.add(btnBuscar);
        
        painel.add(painelBusca, BorderLayout.NORTH);
        
       
        String[] colunas = {"ID", "Paciente", "Médico", "Data", "Hora", "Observações", "ID Paciente", "ID Médico"};
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
        
        tabela.getColumnModel().getColumn(6).setMinWidth(0);
        tabela.getColumnModel().getColumn(6).setMaxWidth(0);
        tabela.getColumnModel().getColumn(6).setWidth(0);
        tabela.getColumnModel().getColumn(7).setMinWidth(0);
        tabela.getColumnModel().getColumn(7).setMaxWidth(0);
        tabela.getColumnModel().getColumn(7).setWidth(0);
        
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
        btnSalvar.addActionListener(e -> salvarConsulta());
        
        btnEditar = new JButton("Editar");
            btnEditar.setBackground(new Color(241, 196, 15)); // Amarelo
        btnEditar.setForeground(Color.BLACK);
        btnEditar.setFont(new Font("Arial", Font.BOLD, 14));
        btnEditar.addActionListener(e -> editarConsulta());
        
        btnExcluir = new JButton("Excluir");
            btnExcluir.setBackground(new Color(231, 76, 60)); // Vermelho
        btnExcluir.setForeground(Color.WHITE);
        btnExcluir.setFont(new Font("Arial", Font.BOLD, 14));
        btnExcluir.addActionListener(e -> excluirConsulta());
        
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
    
    private void carregarComboBoxes() {
        cmbPaciente.removeAllItems();
        cmbMedico.removeAllItems();
        mapPacientes.clear();
        mapMedicos.clear();
        
        
        ArrayList<Paciente> pacientes = pacienteDao.listarTodos();
        if (pacientes != null) {
            for (Paciente p : pacientes) {
                String nome = p.getNome() + " (CPF: " + p.getCpf() + ")";
                cmbPaciente.addItem(nome);
                mapPacientes.put(nome, p.getId());
            }
        }
        
     
        ArrayList<Medico> medicos = medicoDao.listarTodos();
        if (medicos != null) {
            for (Medico m : medicos) {
                String nome = m.getNome() + " (CRM: " + m.getCrm() + ")";
                cmbMedico.addItem(nome);
                mapMedicos.put(nome, m.getId());
            }
        }
    }
    
    
    private void carregarTabela() {
        modeloTabela.setRowCount(0);
        ArrayList<Consulta> lista = consultaDao.listar();
        
        if (lista == null) return; // Proteção extra se não houver lista
        
        for (Consulta c : lista) {
           
            Paciente p = pacienteDao.buscarPorId(c.getIdPaciente());
            Medico m = medicoDao.buscarPorId(c.getIdMedico());
            
       
            String nomePaciente = (p != null) ? p.getNome() : "Paciente não encontrado (ID " + c.getIdPaciente() + ")";
            String nomeMedico = (m != null) ? m.getNome() : "Médico não encontrado (ID " + c.getIdMedico() + ")";
            
            Object[] linha = {
                c.getId(),
                nomePaciente,
                nomeMedico,
                c.getDataConsulta(),
                c.getHoraConsulta(),
                c.getObservacoes(),
                c.getIdPaciente(), // Coluna escondida
                c.getIdMedico()    // Coluna escondida
            };
            modeloTabela.addRow(linha);
        }
    }
    
    private void salvarConsulta() {
        if (!validarCampos()) {
            return;
        }
        
        Consulta consulta = new Consulta();
        
        String pacienteSelecionado = (String) cmbPaciente.getSelectedItem();
        String medicoSelecionado = (String) cmbMedico.getSelectedItem();
        
        consulta.setIdPaciente(mapPacientes.get(pacienteSelecionado));
        consulta.setIdMedico(mapMedicos.get(medicoSelecionado));
        consulta.setDataConsulta(txtData.getText().trim());
        consulta.setHoraConsulta(txtHora.getText().trim());
        consulta.setObservacoes(txtObservacoes.getText().trim());
        
        if (consultaDao.inserir(consulta)) {
            JOptionPane.showMessageDialog(this, "Consulta salva com sucesso!");
            carregarTabela();
            limparCampos();
        }
    }
    
    private void editarConsulta() {
        if (idSelecionado == 0) {
            JOptionPane.showMessageDialog(this, "Selecione uma consulta na tabela!");
            return;
        }
        
        if (!validarCampos()) {
            return;
        }
        
        Consulta consulta = new Consulta();
        consulta.setId(idSelecionado);
        
        String pacienteSelecionado = (String) cmbPaciente.getSelectedItem();
        String medicoSelecionado = (String) cmbMedico.getSelectedItem();
        
        consulta.setIdPaciente(mapPacientes.get(pacienteSelecionado));
        consulta.setIdMedico(mapMedicos.get(medicoSelecionado));
        consulta.setDataConsulta(txtData.getText().trim());
        consulta.setHoraConsulta(txtHora.getText().trim());
        consulta.setObservacoes(txtObservacoes.getText().trim());
        
        if (consultaDao.atualizar(consulta)) {
            JOptionPane.showMessageDialog(this, "Consulta atualizada com sucesso!");
            carregarTabela();
            limparCampos();
        }
    }
    
    private void excluirConsulta() {
        if (idSelecionado == 0) {
            JOptionPane.showMessageDialog(this, "Selecione uma consulta na tabela!");
            return;
        }
        
        int resposta = JOptionPane.showConfirmDialog(this, 
            "Deseja realmente excluir esta consulta?", 
            "Confirmação", 
            JOptionPane.YES_NO_OPTION);
        
        if (resposta == JOptionPane.YES_OPTION) {
            if (consultaDao.excluir(idSelecionado)) {
                JOptionPane.showMessageDialog(this, "Consulta excluída com sucesso!");
                carregarTabela();
                limparCampos();
            }
        }
    }
    
    private void buscarConsultas() {
        String termo = txtBuscar.getText().trim().toLowerCase();
        
        if (termo.isEmpty()) {
            carregarTabela();
            return;
        }
        
        modeloTabela.setRowCount(0);
        ArrayList<Consulta> lista = consultaDao.listar();
        
        if (lista == null) return;
        
        for (Consulta c : lista) {
         
            Paciente p = pacienteDao.buscarPorId(c.getIdPaciente());
            String nomePaciente = (p != null) ? p.getNome() : "";
            
            if (nomePaciente.toLowerCase().contains(termo)) {
                Medico m = medicoDao.buscarPorId(c.getIdMedico());
                String nomeMedico = (m != null) ? m.getNome() : "Desconhecido";
                
                Object[] linha = {
                    c.getId(),
                    nomePaciente,
                    nomeMedico,
                    c.getDataConsulta(),
                    c.getHoraConsulta(),
                    c.getObservacoes(),
                    c.getIdPaciente(),
                    c.getIdMedico()
                };
                modeloTabela.addRow(linha);
            }
        }
    }
    
    private void preencherFormulario() {
        int linha = tabela.getSelectedRow();
        if (linha != -1) {
            idSelecionado = (int) modeloTabela.getValueAt(linha, 0);
            
            
            int idPaciente = (int) modeloTabela.getValueAt(linha, 6);
            int idMedico = (int) modeloTabela.getValueAt(linha, 7);
            
            
            for (Map.Entry<String, Integer> entry : mapPacientes.entrySet()) {
                if (entry.getValue().equals(idPaciente)) {
                    cmbPaciente.setSelectedItem(entry.getKey());
                    break;
                }
            }
            
            for (Map.Entry<String, Integer> entry : mapMedicos.entrySet()) {
                if (entry.getValue().equals(idMedico)) {
                    cmbMedico.setSelectedItem(entry.getKey());
                    break;
                }
            }
            
            txtData.setText((String) modeloTabela.getValueAt(linha, 3));
            txtHora.setText((String) modeloTabela.getValueAt(linha, 4));
            txtObservacoes.setText((String) modeloTabela.getValueAt(linha, 5));
        }
    }
    
    private void limparCampos() {
        idSelecionado = 0;
        cmbPaciente.setSelectedIndex(-1);
        cmbMedico.setSelectedIndex(-1);
        txtData.setText("");
        txtHora.setText("");
        txtObservacoes.setText("");
        txtBuscar.setText("");
        tabela.clearSelection();
    }
    
    private boolean validarCampos() {
        if (cmbPaciente.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um Paciente!");
            return false;
        }
        if (cmbMedico.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um Médico!");
            return false;
        }
        if (txtData.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "O campo Data é obrigatório!");
            txtData.requestFocus();
            return false;
        }
        if (txtHora.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "O campo Hora é obrigatório!");
            txtHora.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private void voltarParaMenuPrincipal() {
        this.dispose();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Consultas().setVisible(true);
        });
    }
}