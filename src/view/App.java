package view;

import view.Pacientes;
import view.Medicos;
import view.Consultas;
import view.Especialidades;
import view.Atendimentos;
import view.Prontuarios;
import view.Login;

import dao.Conexao;
import javax.swing.*;
import java.awt.*;


public class App extends JFrame {
    
    public App() {
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        setTitle("Clinicax - Menu Principal");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        
        
        // Painel de Cabeçalho (simulando o fundo da imagem)
        JPanel painelCabecalho = new JPanel();
        painelCabecalho.setBackground(new Color(40, 40, 40)); // Cinza Escuro
        painelCabecalho.setPreferredSize(new Dimension(600, 40));
        
        JPanel painelTitulo = new JPanel();
        painelTitulo.setBackground(new Color(90, 70, 200)); // Roxo escuro
        painelTitulo.setPreferredSize(new Dimension(600, 60));
        JLabel lblTitulo = new JLabel("CLINICAX - MENU PRINCIPAL");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setForeground(Color.WHITE);
        painelTitulo.add(lblTitulo);
        
        JPanel painelNorte = new JPanel(new BorderLayout());
        painelNorte.add(painelCabecalho, BorderLayout.NORTH);
        painelNorte.add(painelTitulo, BorderLayout.CENTER);
        add(painelNorte, BorderLayout.NORTH);
        
        
        // Painel Central para os Cards
        JPanel painelCentral = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        painelCentral.setBackground(Color.WHITE); // Fundo branco
        
        // Os botões serão criados como cards
        // O FlowLayout é mais adequado para o estilo de "cards" lado a lado.
        
       
        // Botões como Cards
        
        // Botões como Cards (Novo Estilo)
        
        // Cor de destaque (Roxo Escuro)
        Color corDestaque = new Color(90, 70, 200);
        // Cor Secundária (Laranja/Marrom)
        Color corSecundaria = new Color(200, 100, 50);
        // Cor Neutra (Cinza Escuro)
        Color corNeutra = new Color(51, 50, 50);
        // Fundo do Card (Branco)
        Color fundoCard = Color.WHITE;
        
        // Card 1: Pacientes (Bonequinho)
        JButton btnPacientes = criarCard("Gerenciar Pacientes", "<html><center>Gerenciar<br>Pacientes</center></html>", "👤", fundoCard, corDestaque);
        btnPacientes.addActionListener(e -> abrirTelaPacientes());
        painelCentral.add(btnPacientes);
        
        // Card 2: Médicos (Médico)
        JButton btnMedicos = criarCard("Gerenciar Médicos", "<html><center>Gerenciar<br>Médicos</center></html>", "👨‍⚕️", fundoCard, corDestaque);
        btnMedicos.addActionListener(e -> abrirTelaMedicos());
        painelCentral.add(btnMedicos);
        
        // Card 3: Consultas (Caderno)
        JButton btnConsultas = criarCard("Agendar Consultas", "<html><center>Agendar<br>Consultas</center></html>", "🗒️", fundoCard, corSecundaria);
        btnConsultas.addActionListener(e -> abrirTelaConsultas());
        painelCentral.add(btnConsultas);
        
        // Card 4: Atendimentos (Registro)
        JButton btnAtendimentos = criarCard("Registrar Atendimentos", "<html><center>Registrar<br>Atendimentos</center></html>", "📝", fundoCard, corSecundaria);
        btnAtendimentos.addActionListener(e -> abrirTelaAtendimentos());
        painelCentral.add(btnAtendimentos);
        
        // Card 5: Prontuários (Lupa)
        JButton btnProntuarios = criarCard("Visualizar Prontuários", "<html><center>Visualizar<br>Prontuários</center></html>", "🔍", fundoCard, corDestaque);
        btnProntuarios.addActionListener(e -> abrirTelaProntuarios());
        painelCentral.add(btnProntuarios);
        
        // Card 6: Especialidades (Hospital)
        JButton btnEspecialidades = criarCard("Gerenciar Especialidades", "<html><center>Gerenciar<br>Especialidades</center></html>", "🏥", fundoCard, corSecundaria);
        btnEspecialidades.addActionListener(e -> abrirTelaEspecialidades());
        painelCentral.add(btnEspecialidades);
        
        // Card 7: Testar Conexão BD (Banco de Dados)
        JButton btnTestarConexao = criarCard("Testar Conexão BD", "<html><center>Testar<br>Conexão BD</center></html>", "🗄️", fundoCard, corNeutra);
        btnTestarConexao.addActionListener(e -> Conexao.testarConexao());
        painelCentral.add(btnTestarConexao);
        
        // Card 8: Sair (Sair)
        JButton btnSair = criarCard("Sair do Sistema", "<html><center>Sair do<br>Sistema</center></html>", "🚪", fundoCard, corNeutra);
        btnSair.addActionListener(e -> System.exit(0));
        painelCentral.add(btnSair);
        
        add(painelCentral, BorderLayout.CENTER);
        
     
        // Painel de Rodapé
        JPanel painelRodape = new JPanel();
        painelRodape.setBackground(new Color(90, 70, 201)); // Roxo escuro
        JLabel lblRodape = new JLabel("© 2025 Clinicax - Sistema de Gerenciamento Médico");
        lblRodape.setForeground(Color.WHITE);
        painelRodape.add(lblRodape);
        add(painelRodape, BorderLayout.SOUTH);
    }
    
	    private JButton criarCard(String nome, String texto, String icone, Color fundo, Color corTexto) {
	        JButton card = new JButton();
	        card.setPreferredSize(new Dimension(150, 150)); // Tamanho do card
	        card.setLayout(new BorderLayout());
	        card.setBackground(fundo);
	        card.setForeground(corTexto);
	        card.setFocusPainted(false);
	        card.setBorderPainted(false);
	        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
	        
	        // Painel para o Ícone (parte superior)
	        JPanel painelIcone = new JPanel(new BorderLayout());
	        painelIcone.setBackground(fundo);
	        
		        // Ícone (simulado com JLabel e fonte grande)
		        JLabel lblIcone = new JLabel(icone, SwingConstants.CENTER);
		        lblIcone.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48)); // Usando fonte Emoji para ícones
		        lblIcone.setForeground(corTexto);
		        painelIcone.add(lblIcone, BorderLayout.CENTER);
	        
		        // Texto do Card (parte inferior)
		        JLabel lblTextoPrincipal = new JLabel(texto, SwingConstants.CENTER);
		        lblTextoPrincipal.setFont(new Font("Arial", Font.BOLD, 14));
		        lblTextoPrincipal.setForeground(corTexto);
		        
		        // Adiciona o ícone e o texto ao botão
		        card.add(painelIcone, BorderLayout.CENTER);
		        card.add(lblTextoPrincipal, BorderLayout.SOUTH);
	        
	        // Efeito Hover
	        card.addMouseListener(new java.awt.event.MouseAdapter() {
	            public void mouseEntered(java.awt.event.MouseEvent evt) {
	                card.setBackground(fundo.darker());
	                painelIcone.setBackground(fundo.darker());
	            }
	            public void mouseExited(java.awt.event.MouseEvent evt) {
	                card.setBackground(fundo);
	                painelIcone.setBackground(fundo);
	            }
	        });
	        
	        return card;
	    }
    
    private void abrirTelaPacientes() {
        Pacientes telaPacientes = new Pacientes();
        telaPacientes.setVisible(true);
    }
    
    private void abrirTelaMedicos() {
        Medicos telaMedicos = new Medicos();
        telaMedicos.setVisible(true);
    }
    
    private void abrirTelaConsultas() {
        Consultas telaConsultas = new Consultas();
        telaConsultas.setVisible(true);
    }
    
    private void abrirTelaEspecialidades() {
        Especialidades telaEspecialidades = new Especialidades();
        telaEspecialidades.setVisible(true);
    }
    
    private void abrirTelaAtendimentos() {
        Atendimentos telaAtendimentos = new Atendimentos();
        telaAtendimentos.setVisible(true);
    }
    
    private void abrirTelaProntuarios() {
        Prontuarios telaProntuarios = new Prontuarios();
        telaProntuarios.setVisible(true);
    }
    
    public static void main(String[] args) {
        // Aplica um Look and Feel mais moderno
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }       
        
	        SwingUtilities.invokeLater(() -> {
	            // Inicia com a tela de Login
	            Login login = new Login();
	            login.setVisible(true);
	        });
	    }
}