package view;

import dao.Atendimentodao;
import dao.Pacientesdao;
import dao.Medicodao;
import model.Atendimento;
import model.Paciente;
import model.Medico;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Interface gráfica para gerenciar Atendimentos
 */
public class Atendimentos extends JFrame {
    
    private Atendimentodao atendimentoDao;
    private Pacientesdao pacienteDao;
    private Medicodao medicoDao;
    
    // Componentes da interface
    private JTable tabelaAtendimentos;
    private DefaultTableModel modeloTabela;
    private JComboBox<String> cbPaciente, cbMedico, cbTipoAtendimento, cbStatus, cbFormaPagamento;
    private JTextField txtMotivo, txtValor;
    private JTextArea txtSintomas, txtDiagnostico, txtPrescricao, txtExames, txtObservacoes;
    private JCheckBox chkRealizado;
    private JButton btnNovo, btnSalvar, btnAtualizar, btnExcluir, btnLimpar, btnBuscar, btnVoltar;
    
    private int atendimentoSelecionadoId = -1;
    
    public Atendimentos() {
        atendimentoDao = new Atendimentodao();
        pacienteDao = new Pacientesdao();
        medicoDao = new Medicodao();
        
        inicializarComponentes();
        carregarAtendimentos();
        carregarPacientes();
        carregarMedicos();
    }
    
    private void inicializarComponentes() {
        setTitle("Gerenciar Atendimentos");
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
        
        JPanel painelTitulo = new JPanel(new BorderLayout());
        painelTitulo.setBackground(new Color(90, 70, 200));
        
        JLabel lblTitulo = new JLabel("GERENCIAMENTO DE ATENDIMENTOS", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        
        btnVoltar = new JButton("Voltar");
	        btnVoltar.setBackground(new Color(90, 70, 200).darker());
        btnVoltar.setForeground(Color.WHITE);
        btnVoltar.setFont(new Font("Arial", Font.BOLD, 14));
        btnVoltar.setFocusPainted(false);
        btnVoltar.addActionListener(e -> voltarParaMenuPrincipal());
        
        JPanel painelBotaoVoltar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelBotaoVoltar.setOpaque(false);
        painelBotaoVoltar.add(btnVoltar);
        
        painelTitulo.add(painelBotaoVoltar, BorderLayout.WEST);
        painelTitulo.add(lblTitulo, BorderLayout.CENTER);
        add(painelTitulo, BorderLayout.NORTH);
        
        JPanel painelCentral = new JPanel(new GridLayout(1, 2, 10, 10));
        
        JPanel painelFormulario = criarPainelFormulario();
        painelCentral.add(new JScrollPane(painelFormulario));
        
        JPanel painelTabela = criarPainelTabela();
        painelCentral.add(painelTabela);
        
        add(painelCentral, BorderLayout.CENTER);
        
        JPanel painelBotoes = criarPainelBotoes();
        add(painelBotoes, BorderLayout.SOUTH);
    }
    
    private JPanel criarPainelFormulario() {
        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        int linha = 0;
        
        gbc.gridx = 0; gbc.gridy = linha;
        painel.add(new JLabel("Paciente:"), gbc);
        gbc.gridx = 1;
        cbPaciente = new JComboBox<>();
        painel.add(cbPaciente, gbc);
        linha++;
        
        gbc.gridx = 0; gbc.gridy = linha;
        painel.add(new JLabel("Médico:"), gbc);
        gbc.gridx = 1;
        cbMedico = new JComboBox<>();
        painel.add(cbMedico, gbc);
        linha++;
        
        gbc.gridx = 0; gbc.gridy = linha;
        painel.add(new JLabel("Tipo:"), gbc);
        gbc.gridx = 1;
        cbTipoAtendimento = new JComboBox<>(new String[]{"Consulta", "Retorno", "Emergência", "Exame"});
        painel.add(cbTipoAtendimento, gbc);
        linha++;
        
        gbc.gridx = 0; gbc.gridy = linha;
        painel.add(new JLabel("Motivo:"), gbc);
        gbc.gridx = 1;
        txtMotivo = new JTextField(20);
        painel.add(txtMotivo, gbc);
        linha++;
        
        gbc.gridx = 0; gbc.gridy = linha;
        painel.add(new JLabel("Sintomas:"), gbc);
        gbc.gridx = 1;
        txtSintomas = new JTextArea(3, 20);
        painel.add(new JScrollPane(txtSintomas), gbc);
        linha++;
        
        gbc.gridx = 0; gbc.gridy = linha;
        painel.add(new JLabel("Diagnóstico:"), gbc);
        gbc.gridx = 1;
        txtDiagnostico = new JTextArea(3, 20);
        painel.add(new JScrollPane(txtDiagnostico), gbc);
        linha++;
        
        gbc.gridx = 0; gbc.gridy = linha;
        painel.add(new JLabel("Prescrição:"), gbc);
        gbc.gridx = 1;
        txtPrescricao = new JTextArea(3, 20);
        painel.add(new JScrollPane(txtPrescricao), gbc);
        linha++;
        
        gbc.gridx = 0; gbc.gridy = linha;
        painel.add(new JLabel("Exames:"), gbc);
        gbc.gridx = 1;
        txtExames = new JTextArea(2, 20);
        painel.add(new JScrollPane(txtExames), gbc);
        linha++;
        
        gbc.gridx = 0; gbc.gridy = linha;
        painel.add(new JLabel("Status:"), gbc);
        gbc.gridx = 1;
        cbStatus = new JComboBox<>(new String[]{"Aguardando", "Em Atendimento", "Finalizado", "Cancelado"});
        painel.add(cbStatus, gbc);
        linha++;
        
        gbc.gridx = 0; gbc.gridy = linha;
        painel.add(new JLabel("Valor (R$):"), gbc);
        gbc.gridx = 1;
        txtValor = new JTextField(20);
        painel.add(txtValor, gbc);
        linha++;
        
        gbc.gridx = 0; gbc.gridy = linha;
        painel.add(new JLabel("Pagamento:"), gbc);
        gbc.gridx = 1;
        cbFormaPagamento = new JComboBox<>(new String[]{"Dinheiro", "Cartão", "Convênio", "PIX"});
        painel.add(cbFormaPagamento, gbc);
        linha++;
        
        gbc.gridx = 0; gbc.gridy = linha;
        painel.add(new JLabel("Observações:"), gbc);
        gbc.gridx = 1;
        txtObservacoes = new JTextArea(3, 20);
        painel.add(new JScrollPane(txtObservacoes), gbc);
        linha++;
        
        gbc.gridx = 0; gbc.gridy = linha;
        gbc.gridwidth = 2;
        chkRealizado = new JCheckBox("Atendimento Realizado");
        painel.add(chkRealizado, gbc);
        
        return painel;
    }
    
    private JPanel criarPainelTabela() {
        JPanel painel = new JPanel(new BorderLayout());
        
        String[] colunas = {"ID", "Paciente ID", "Médico ID", "Data/Hora", "Tipo", "Status"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tabelaAtendimentos = new JTable(modeloTabela);
        tabelaAtendimentos.setFillsViewportHeight(true);
        tabelaAtendimentos.setFont(new Font("Arial", Font.PLAIN, 12));
        tabelaAtendimentos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
	        tabelaAtendimentos.getTableHeader().setBackground(new Color(90, 70, 200));
	        tabelaAtendimentos.getTableHeader().setForeground(Color.WHITE);
        tabelaAtendimentos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaAtendimentos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    selecionarAtendimento();
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(tabelaAtendimentos);
        painel.add(scrollPane, BorderLayout.CENTER);
        
        return painel;
    }
    
    private JPanel criarPainelBotoes() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
	        painel.setBackground(Color.WHITE);
        
	        btnNovo = criarBotao("Novo", new Color(46, 204, 113)); // Verde
	        btnSalvar = criarBotao("Salvar", new Color(90, 70, 200)); // Roxo
	        btnAtualizar = criarBotao("Atualizar", new Color(241, 196, 15)); // Amarelo
	        btnExcluir = criarBotao("Excluir", new Color(231, 76, 60)); // Vermelho
	        btnLimpar = criarBotao("Limpar", new Color(150, 150, 150)); // Cinza
	        btnBuscar = criarBotao("Buscar", new Color(150, 150, 150)); // Cinza
        
        btnNovo.addActionListener(e -> limparCampos());
        btnSalvar.addActionListener(e -> salvarAtendimento());
        btnAtualizar.addActionListener(e -> atualizarAtendimento());
        btnExcluir.addActionListener(e -> excluirAtendimento());
        btnLimpar.addActionListener(e -> limparCampos());
        btnBuscar.addActionListener(e -> carregarAtendimentos());
        
        painel.add(btnNovo);
        painel.add(btnSalvar);
        painel.add(btnAtualizar);
        painel.add(btnExcluir);
        painel.add(btnLimpar);
        painel.add(btnBuscar);
        
        return painel;
    }
    
    private JButton criarBotao(String texto, Color cor) {
        JButton botao = new JButton(texto);
        botao.setPreferredSize(new Dimension(120, 40));
        botao.setFont(new Font("Arial", Font.BOLD, 12));
        botao.setBackground(cor);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setBorderPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return botao;
    }
    
    private void carregarPacientes() {
        cbPaciente.removeAllItems();
        List<Paciente> pacientes = pacienteDao.listarTodos();
        for (Paciente p : pacientes) {
            cbPaciente.addItem(p.getId() + " - " + p.getNome());
        }
    }
    
    private void carregarMedicos() {
        cbMedico.removeAllItems();
        List<Medico> medicos = medicoDao.listarTodos();
        for (Medico m : medicos) {
            cbMedico.addItem(m.getId() + " - " + m.getNome());
        }
    }
    
    private void carregarAtendimentos() {
        modeloTabela.setRowCount(0);
        List<Atendimento> atendimentos = atendimentoDao.listarTodos();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        
        for (Atendimento a : atendimentos) {
            Object[] linha = {
                a.getId(),
                a.getPacienteId(),
                a.getMedicoId(),
                a.getDataHoraAtendimento().format(formatter),
                a.getTipoAtendimento(),
                a.getStatus()
            };
            modeloTabela.addRow(linha);
        }
    }
    
    private void salvarAtendimento() {
        try {
            Atendimento atendimento = obterAtendimentoDoFormulario();
            
            if (atendimentoDao.inserir(atendimento)) {
                JOptionPane.showMessageDialog(this, "Atendimento salvo com sucesso!");
                carregarAtendimentos();
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao salvar atendimento!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void atualizarAtendimento() {
        if (atendimentoSelecionadoId == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um atendimento para atualizar!");
            return;
        }
        
        try {
            Atendimento atendimento = obterAtendimentoDoFormulario();
            atendimento.setId(atendimentoSelecionadoId);
            
            if (atendimentoDao.atualizar(atendimento)) {
                JOptionPane.showMessageDialog(this, "Atendimento atualizado com sucesso!");
                carregarAtendimentos();
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar atendimento!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void excluirAtendimento() {
        if (atendimentoSelecionadoId == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um atendimento para excluir!");
            return;
        }
        
        int confirmacao = JOptionPane.showConfirmDialog(this, 
            "Tem certeza que deseja excluir este atendimento?", 
            "Confirmar Exclusão", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacao == JOptionPane.YES_OPTION) {
            if (atendimentoDao.excluir(atendimentoSelecionadoId)) {
                JOptionPane.showMessageDialog(this, "Atendimento excluído com sucesso!");
                carregarAtendimentos();
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao excluir atendimento!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void selecionarAtendimento() {
        int linhaSelecionada = tabelaAtendimentos.getSelectedRow();
        if (linhaSelecionada != -1) {
            atendimentoSelecionadoId = (int) modeloTabela.getValueAt(linhaSelecionada, 0);
            Atendimento atendimento = atendimentoDao.buscarPorId(atendimentoSelecionadoId);
            
            if (atendimento != null) {
                preencherFormulario(atendimento);
            }
        }
    }
    
    private void preencherFormulario(Atendimento a) {
        cbPaciente.setSelectedItem(a.getPacienteId() + " - ");
        cbMedico.setSelectedItem(a.getMedicoId() + " - ");
        cbTipoAtendimento.setSelectedItem(a.getTipoAtendimento());
        txtMotivo.setText(a.getMotivoAtendimento());
        txtSintomas.setText(a.getSintomas());
        txtDiagnostico.setText(a.getDiagnosticoInicial());
        txtPrescricao.setText(a.getPrescricao());
        txtExames.setText(a.getExamesSolicitados());
        cbStatus.setSelectedItem(a.getStatus());
        txtValor.setText(String.valueOf(a.getValorAtendimento()));
        cbFormaPagamento.setSelectedItem(a.getFormaPagamento());
        txtObservacoes.setText(a.getObservacoes());
        chkRealizado.setSelected(a.isAtendimentoRealizado());
    }
    
    private Atendimento obterAtendimentoDoFormulario() {
        Atendimento atendimento = new Atendimento();
        
        String pacienteSel = (String) cbPaciente.getSelectedItem();
        int pacienteId = Integer.parseInt(pacienteSel.split(" - ")[0]);
        
        String medicoSel = (String) cbMedico.getSelectedItem();
        int medicoId = Integer.parseInt(medicoSel.split(" - ")[0]);
        
        atendimento.setPacienteId(pacienteId);
        atendimento.setMedicoId(medicoId);
        atendimento.setDataHoraAtendimento(LocalDateTime.now());
        atendimento.setTipoAtendimento((String) cbTipoAtendimento.getSelectedItem());
        atendimento.setMotivoAtendimento(txtMotivo.getText());
        atendimento.setSintomas(txtSintomas.getText());
        atendimento.setDiagnosticoInicial(txtDiagnostico.getText());
        atendimento.setPrescricao(txtPrescricao.getText());
        atendimento.setExamesSolicitados(txtExames.getText());
        atendimento.setStatus((String) cbStatus.getSelectedItem());
        atendimento.setValorAtendimento(Double.parseDouble(txtValor.getText().isEmpty() ? "0" : txtValor.getText()));
        atendimento.setFormaPagamento((String) cbFormaPagamento.getSelectedItem());
        atendimento.setObservacoes(txtObservacoes.getText());
        atendimento.setAtendimentoRealizado(chkRealizado.isSelected());
        
        return atendimento;
    }
    
    private void limparCampos() {
        atendimentoSelecionadoId = -1;
        if (cbPaciente.getItemCount() > 0) cbPaciente.setSelectedIndex(0);
        if (cbMedico.getItemCount() > 0) cbMedico.setSelectedIndex(0);
        cbTipoAtendimento.setSelectedIndex(0);
        txtMotivo.setText("");
        txtSintomas.setText("");
        txtDiagnostico.setText("");
        txtPrescricao.setText("");
        txtExames.setText("");
        cbStatus.setSelectedIndex(0);
        txtValor.setText("");
        cbFormaPagamento.setSelectedIndex(0);
        txtObservacoes.setText("");
        chkRealizado.setSelected(false);
        tabelaAtendimentos.clearSelection();
    }
    
    private void voltarParaMenuPrincipal() {
        this.dispose();
    }
}
