package view;

import dao.Prontuariodao;
import dao.Pacientesdao;
import model.Prontuario;
import model.Paciente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Interface gráfica para gerenciar Prontuários Médicos
 */
public class Prontuarios extends JFrame {
    
    private Prontuariodao prontuarioDao;
    private Pacientesdao pacienteDao;
    
    // Componentes da interface
    private JTable tabelaProntuarios;
    private DefaultTableModel modeloTabela;
    private JComboBox<String> cbPaciente;
    private JTextField txtAtendimentoId, txtPeso, txtAltura, txtIMC, txtTemperatura;
    private JTextField txtFreqCardiaca, txtFreqRespiratoria, txtPressao, txtMedico;
    private JTextArea txtAnamnese, txtHistFamiliar, txtAlergias, txtMedicamentos;
    private JTextArea txtDoencas, txtCirurgias, txtHabitos, txtExameFisico;
    private JTextArea txtHipotese, txtConduta, txtEvolucao, txtResultados, txtObservacoes;
    private JButton btnNovo, btnSalvar, btnAtualizar, btnExcluir, btnLimpar, btnBuscar, btnCalcularIMC, btnVoltar;
    private JLabel lblClassificacaoIMC;
    
    private int prontuarioSelecionadoId = -1;
    
    public Prontuarios() {
        prontuarioDao = new Prontuariodao();
        pacienteDao = new Pacientesdao();
        
        inicializarComponentes();
        carregarProntuarios();
        carregarPacientes();
    }
    
    private void inicializarComponentes() {
        setTitle("Gerenciar Prontuários Médicos");
        setSize(1400, 800);
                setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                voltarParaMenuPrincipal();
            }
        });
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        
        // Painel superior com título e botão Voltar
        JPanel painelTitulo = new JPanel(new BorderLayout());
        painelTitulo.setBackground(new Color(90, 70, 200));
        
        JLabel lblTitulo = new JLabel("PRONTUÁRIOS MÉDICOS", SwingConstants.CENTER);
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
        
        // Painel central com abas
        JTabbedPane abas = new JTabbedPane();
        
        // Aba 1: Dados Gerais
        JPanel painelDadosGerais = criarPainelDadosGerais();
        abas.addTab("Dados Gerais", painelDadosGerais);
        
        // Aba 2: Sinais Vitais
        JPanel painelSinaisVitais = criarPainelSinaisVitais();
        abas.addTab("Sinais Vitais", painelSinaisVitais);
        
        // Aba 3: Exame e Diagnóstico
        JPanel painelExame = criarPainelExame();
        abas.addTab("Exame e Diagnóstico", painelExame);
        
        // Aba 4: Lista de Prontuários
        JPanel painelLista = criarPainelLista();
        abas.addTab("Lista de Prontuários", painelLista);
        
        add(abas, BorderLayout.CENTER);
        
        // Painel inferior com botões
        JPanel painelBotoes = criarPainelBotoes();
        add(painelBotoes, BorderLayout.SOUTH);
    }
    
    private JPanel criarPainelDadosGerais() {
        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        int linha = 0;
        
        // Paciente
        gbc.gridx = 0; gbc.gridy = linha; gbc.gridwidth = 1;
        painel.add(new JLabel("Paciente:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        cbPaciente = new JComboBox<>();
        cbPaciente.setPreferredSize(new Dimension(300, 25));
        painel.add(cbPaciente, gbc);
        linha++;
        
        // Atendimento ID
        gbc.gridx = 0; gbc.gridy = linha; gbc.gridwidth = 1;
        painel.add(new JLabel("ID Atendimento:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        txtAtendimentoId = new JTextField(20);
        painel.add(txtAtendimentoId, gbc);
        linha++;
        
        // Anamnese
        gbc.gridx = 0; gbc.gridy = linha; gbc.gridwidth = 1;
        painel.add(new JLabel("Anamnese:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        txtAnamnese = new JTextArea(4, 40);
        painel.add(new JScrollPane(txtAnamnese), gbc);
        linha++;
        
        // Histórico Familiar
        gbc.gridx = 0; gbc.gridy = linha; gbc.gridwidth = 1;
        painel.add(new JLabel("Histórico Familiar:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        txtHistFamiliar = new JTextArea(3, 40);
        painel.add(new JScrollPane(txtHistFamiliar), gbc);
        linha++;
        
        // Alergias
        gbc.gridx = 0; gbc.gridy = linha; gbc.gridwidth = 1;
        painel.add(new JLabel("Alergias:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        txtAlergias = new JTextArea(2, 40);
        painel.add(new JScrollPane(txtAlergias), gbc);
        linha++;
        
        // Medicamentos em Uso
        gbc.gridx = 0; gbc.gridy = linha; gbc.gridwidth = 1;
        painel.add(new JLabel("Medicamentos em Uso:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        txtMedicamentos = new JTextArea(3, 40);
        painel.add(new JScrollPane(txtMedicamentos), gbc);
        linha++;
        
        // Doenças Preexistentes
        gbc.gridx = 0; gbc.gridy = linha; gbc.gridwidth = 1;
        painel.add(new JLabel("Doenças Preexistentes:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        txtDoencas = new JTextArea(3, 40);
        painel.add(new JScrollPane(txtDoencas), gbc);
        linha++;
        
        // Cirurgias Anteriores
        gbc.gridx = 0; gbc.gridy = linha; gbc.gridwidth = 1;
        painel.add(new JLabel("Cirurgias Anteriores:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        txtCirurgias = new JTextArea(2, 40);
        painel.add(new JScrollPane(txtCirurgias), gbc);
        linha++;
        
        // Hábitos de Vida
        gbc.gridx = 0; gbc.gridy = linha; gbc.gridwidth = 1;
        painel.add(new JLabel("Hábitos de Vida:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        txtHabitos = new JTextArea(3, 40);
        painel.add(new JScrollPane(txtHabitos), gbc);
        
        return painel;
    }
    
    private JPanel criarPainelSinaisVitais() {
        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        int linha = 0;
        
        // Peso
        gbc.gridx = 0; gbc.gridy = linha;
        painel.add(new JLabel("Peso (kg):"), gbc);
        gbc.gridx = 1;
        txtPeso = new JTextField(15);
        painel.add(txtPeso, gbc);
        linha++;
        
        // Altura
        gbc.gridx = 0; gbc.gridy = linha;
        painel.add(new JLabel("Altura (m):"), gbc);
        gbc.gridx = 1;
        txtAltura = new JTextField(15);
        painel.add(txtAltura, gbc);
        linha++;
        
        // IMC
        gbc.gridx = 0; gbc.gridy = linha;
        painel.add(new JLabel("IMC:"), gbc);
        gbc.gridx = 1;
        txtIMC = new JTextField(15);
        txtIMC.setEditable(false);
        painel.add(txtIMC, gbc);
        linha++;
        
        // Botão Calcular IMC
        gbc.gridx = 0; gbc.gridy = linha; gbc.gridwidth = 2;
        btnCalcularIMC = new JButton("Calcular IMC");
        btnCalcularIMC.addActionListener(e -> calcularIMC());
        painel.add(btnCalcularIMC, gbc);
        linha++;
        
        // Classificação IMC
        gbc.gridx = 0; gbc.gridy = linha; gbc.gridwidth = 2;
        lblClassificacaoIMC = new JLabel("");
        lblClassificacaoIMC.setFont(new Font("Arial", Font.BOLD, 14));
        painel.add(lblClassificacaoIMC, gbc);
        linha++;
        
        // Temperatura
        gbc.gridx = 0; gbc.gridy = linha; gbc.gridwidth = 1;
        painel.add(new JLabel("Temperatura (°C):"), gbc);
        gbc.gridx = 1;
        txtTemperatura = new JTextField(15);
        painel.add(txtTemperatura, gbc);
        linha++;
        
        // Pressão Arterial
        gbc.gridx = 0; gbc.gridy = linha;
        painel.add(new JLabel("Pressão Arterial:"), gbc);
        gbc.gridx = 1;
        txtPressao = new JTextField(15);
        painel.add(txtPressao, gbc);
        linha++;
        
        // Frequência Cardíaca
        gbc.gridx = 0; gbc.gridy = linha;
        painel.add(new JLabel("Freq. Cardíaca (bpm):"), gbc);
        gbc.gridx = 1;
        txtFreqCardiaca = new JTextField(15);
        painel.add(txtFreqCardiaca, gbc);
        linha++;
        
        // Frequência Respiratória
        gbc.gridx = 0; gbc.gridy = linha;
        painel.add(new JLabel("Freq. Respiratória (irpm):"), gbc);
        gbc.gridx = 1;
        txtFreqRespiratoria = new JTextField(15);
        painel.add(txtFreqRespiratoria, gbc);
        
        return painel;
    }
    
    private JPanel criarPainelExame() {
        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH;
        
        int linha = 0;
        
        // Exame Físico
        gbc.gridx = 0; gbc.gridy = linha; gbc.gridwidth = 1;
        painel.add(new JLabel("Exame Físico:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        txtExameFisico = new JTextArea(4, 40);
        painel.add(new JScrollPane(txtExameFisico), gbc);
        linha++;
        
        // Hipótese Diagnóstica
        gbc.gridx = 0; gbc.gridy = linha; gbc.gridwidth = 1;
        painel.add(new JLabel("Hipótese Diagnóstica:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        txtHipotese = new JTextArea(3, 40);
        painel.add(new JScrollPane(txtHipotese), gbc);
        linha++;
        
        // Conduta Médica
        gbc.gridx = 0; gbc.gridy = linha; gbc.gridwidth = 1;
        painel.add(new JLabel("Conduta Médica:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        txtConduta = new JTextArea(4, 40);
        painel.add(new JScrollPane(txtConduta), gbc);
        linha++;
        
        // Evolução
        gbc.gridx = 0; gbc.gridy = linha; gbc.gridwidth = 1;
        painel.add(new JLabel("Evolução:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        txtEvolucao = new JTextArea(3, 40);
        painel.add(new JScrollPane(txtEvolucao), gbc);
        linha++;
        
        // Resultados de Exames
        gbc.gridx = 0; gbc.gridy = linha; gbc.gridwidth = 1;
        painel.add(new JLabel("Resultados de Exames:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        txtResultados = new JTextArea(4, 40);
        painel.add(new JScrollPane(txtResultados), gbc);
        linha++;
        
        // Observações Gerais
        gbc.gridx = 0; gbc.gridy = linha; gbc.gridwidth = 1;
        painel.add(new JLabel("Observações Gerais:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        txtObservacoes = new JTextArea(3, 40);
        painel.add(new JScrollPane(txtObservacoes), gbc);
        linha++;
        
        // Médico Responsável
        gbc.gridx = 0; gbc.gridy = linha; gbc.gridwidth = 1;
        painel.add(new JLabel("Médico Responsável:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        txtMedico = new JTextField(40);
        painel.add(txtMedico, gbc);
        
        return painel;
    }
    
    private JPanel criarPainelLista() {
        JPanel painel = new JPanel(new BorderLayout());
        
        String[] colunas = {"ID", "Paciente ID", "Atendimento ID", "Data", "Médico"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tabelaProntuarios = new JTable(modeloTabela);
        tabelaProntuarios.setFillsViewportHeight(true);
        tabelaProntuarios.setFont(new Font("Arial", Font.PLAIN, 12));
        tabelaProntuarios.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
	        tabelaProntuarios.getTableHeader().setBackground(new Color(90, 70, 200));
	        tabelaProntuarios.getTableHeader().setForeground(Color.WHITE);
        tabelaProntuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaProntuarios.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    selecionarProntuario();
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(tabelaProntuarios);
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
	        btnBuscar = criarBotao("Buscar", new Color(150, 150, 150)); // Cinza;
        
        btnNovo.addActionListener(e -> limparCampos());
        btnSalvar.addActionListener(e -> salvarProntuario());
        btnAtualizar.addActionListener(e -> atualizarProntuario());
        btnExcluir.addActionListener(e -> excluirProntuario());
        btnLimpar.addActionListener(e -> limparCampos());
        btnBuscar.addActionListener(e -> carregarProntuarios());
        
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
        botao.setPreferredSize(new Dimension(130, 40));
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
    
    private void carregarProntuarios() {
        modeloTabela.setRowCount(0);
        List<Prontuario> prontuarios = prontuarioDao.listarTodos();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        for (Prontuario p : prontuarios) {
            Object[] linha = {
                p.getId(),
                p.getPacienteId(),
                p.getAtendimentoId(),
                p.getDataRegistro().format(formatter),
                p.getMedicoProntuario()
            };
            modeloTabela.addRow(linha);
        }
    }
    
    private void calcularIMC() {
        try {
            double peso = Double.parseDouble(txtPeso.getText());
            double altura = Double.parseDouble(txtAltura.getText());
            
            if (altura > 0 && peso > 0) {
                double imc = peso / (altura * altura);
                txtIMC.setText(String.format("%.2f", imc));
                
                String classificacao = "";
                Color cor = Color.BLACK;
                
                if (imc < 18.5) {
                    classificacao = "Abaixo do peso";
                    cor = new Color(52, 152, 219);
                } else if (imc < 25) {
                    classificacao = "Peso normal";
                    cor = new Color(46, 204, 113);
                } else if (imc < 30) {
                    classificacao = "Sobrepeso";
                    cor = new Color(241, 196, 15);
                } else if (imc < 35) {
                    classificacao = "Obesidade Grau I";
                    cor = new Color(230, 126, 34);
                } else if (imc < 40) {
                    classificacao = "Obesidade Grau II";
                    cor = new Color(231, 76, 60);
                } else {
                    classificacao = "Obesidade Grau III";
                    cor = new Color(192, 57, 43);
                }
                
                lblClassificacaoIMC.setText("Classificação: " + classificacao);
                lblClassificacaoIMC.setForeground(cor);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Digite valores válidos para peso e altura!");
        }
    }
    
    private void salvarProntuario() {
        try {
            Prontuario prontuario = obterProntuarioDoFormulario();
            
            if (prontuarioDao.inserir(prontuario)) {
                JOptionPane.showMessageDialog(this, "Prontuário salvo com sucesso!");
                carregarProntuarios();
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao salvar prontuário!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void atualizarProntuario() {
        if (prontuarioSelecionadoId == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um prontuário para atualizar!");
            return;
        }
        
        try {
            Prontuario prontuario = obterProntuarioDoFormulario();
            prontuario.setId(prontuarioSelecionadoId);
            
            if (prontuarioDao.atualizar(prontuario)) {
                JOptionPane.showMessageDialog(this, "Prontuário atualizado com sucesso!");
                carregarProntuarios();
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar prontuário!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void excluirProntuario() {
        if (prontuarioSelecionadoId == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um prontuário para excluir!");
            return;
        }
        
        int confirmacao = JOptionPane.showConfirmDialog(this, 
            "Tem certeza que deseja excluir este prontuário?", 
            "Confirmar Exclusão", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacao == JOptionPane.YES_OPTION) {
            if (prontuarioDao.excluir(prontuarioSelecionadoId)) {
                JOptionPane.showMessageDialog(this, "Prontuário excluído com sucesso!");
                carregarProntuarios();
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao excluir prontuário!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void selecionarProntuario() {
        int linhaSelecionada = tabelaProntuarios.getSelectedRow();
        if (linhaSelecionada != -1) {
            prontuarioSelecionadoId = (int) modeloTabela.getValueAt(linhaSelecionada, 0);
            Prontuario prontuario = prontuarioDao.buscarPorId(prontuarioSelecionadoId);
            
            if (prontuario != null) {
                preencherFormulario(prontuario);
            }
        }
    }
    
    private void preencherFormulario(Prontuario p) {
        cbPaciente.setSelectedItem(p.getPacienteId() + " - ");
        txtAtendimentoId.setText(String.valueOf(p.getAtendimentoId()));
        txtAnamnese.setText(p.getAnamnese());
        txtHistFamiliar.setText(p.getHistoricoFamiliar());
        txtAlergias.setText(p.getAlergias());
        txtMedicamentos.setText(p.getMedicamentosUso());
        txtDoencas.setText(p.getDoencasPreexistentes());
        txtCirurgias.setText(p.getCirurgiasAnteriores());
        txtHabitos.setText(p.getHabitosVida());
        txtPeso.setText(String.valueOf(p.getPeso()));
        txtAltura.setText(String.valueOf(p.getAltura()));
        txtIMC.setText(String.valueOf(p.getImc()));
        txtTemperatura.setText(String.valueOf(p.getTemperatura()));
        txtPressao.setText(p.getPressaoArterial());
        txtFreqCardiaca.setText(String.valueOf(p.getFrequenciaCardiaca()));
        txtFreqRespiratoria.setText(String.valueOf(p.getFrequenciaRespiratoria()));
        txtExameFisico.setText(p.getExameFisico());
        txtHipotese.setText(p.getHipoteseDiagnostica());
        txtConduta.setText(p.getCondutaMedica());
        txtEvolucao.setText(p.getEvolucao());
        txtResultados.setText(p.getResultadosExames());
        txtObservacoes.setText(p.getObservacoesGerais());
        txtMedico.setText(p.getMedicoProntuario());
    }
    
    private Prontuario obterProntuarioDoFormulario() {
        Prontuario prontuario = new Prontuario();
        
        String pacienteSel = (String) cbPaciente.getSelectedItem();
        int pacienteId = Integer.parseInt(pacienteSel.split(" - ")[0]);
        
        prontuario.setPacienteId(pacienteId);
        prontuario.setAtendimentoId(txtAtendimentoId.getText().isEmpty() ? 0 : Integer.parseInt(txtAtendimentoId.getText()));
        prontuario.setDataRegistro(LocalDate.now());
        prontuario.setAnamnese(txtAnamnese.getText());
        prontuario.setHistoricoFamiliar(txtHistFamiliar.getText());
        prontuario.setAlergias(txtAlergias.getText());
        prontuario.setMedicamentosUso(txtMedicamentos.getText());
        prontuario.setDoencasPreexistentes(txtDoencas.getText());
        prontuario.setCirurgiasAnteriores(txtCirurgias.getText());
        prontuario.setHabitosVida(txtHabitos.getText());
        prontuario.setPeso(txtPeso.getText().isEmpty() ? 0 : Double.parseDouble(txtPeso.getText()));
        prontuario.setAltura(txtAltura.getText().isEmpty() ? 0 : Double.parseDouble(txtAltura.getText()));
        prontuario.setImc(txtIMC.getText().isEmpty() ? 0 : Double.parseDouble(txtIMC.getText()));
        prontuario.setTemperatura(txtTemperatura.getText().isEmpty() ? 0 : Double.parseDouble(txtTemperatura.getText()));
        prontuario.setPressaoArterial(txtPressao.getText());
        prontuario.setFrequenciaCardiaca(txtFreqCardiaca.getText().isEmpty() ? 0 : Integer.parseInt(txtFreqCardiaca.getText()));
        prontuario.setFrequenciaRespiratoria(txtFreqRespiratoria.getText().isEmpty() ? 0 : Integer.parseInt(txtFreqRespiratoria.getText()));
        prontuario.setExameFisico(txtExameFisico.getText());
        prontuario.setHipoteseDiagnostica(txtHipotese.getText());
        prontuario.setCondutaMedica(txtConduta.getText());
        prontuario.setEvolucao(txtEvolucao.getText());
        prontuario.setResultadosExames(txtResultados.getText());
        prontuario.setObservacoesGerais(txtObservacoes.getText());
        prontuario.setMedicoProntuario(txtMedico.getText());
        
        return prontuario;
    }
    
    private void limparCampos() {
        prontuarioSelecionadoId = -1;
        if (cbPaciente.getItemCount() > 0) cbPaciente.setSelectedIndex(0);
        txtAtendimentoId.setText("");
        txtAnamnese.setText("");
        txtHistFamiliar.setText("");
        txtAlergias.setText("");
        txtMedicamentos.setText("");
        txtDoencas.setText("");
        txtCirurgias.setText("");
        txtHabitos.setText("");
        txtPeso.setText("");
        txtAltura.setText("");
        txtIMC.setText("");
        lblClassificacaoIMC.setText("");
        txtTemperatura.setText("");
        txtPressao.setText("");
        txtFreqCardiaca.setText("");
        txtFreqRespiratoria.setText("");
        txtExameFisico.setText("");
        txtHipotese.setText("");
        txtConduta.setText("");
        txtEvolucao.setText("");
        txtResultados.setText("");
        txtObservacoes.setText("");
        txtMedico.setText("");
        tabelaProntuarios.clearSelection();
    }
    
    private void voltarParaMenuPrincipal() {
        this.dispose();
    }
}
