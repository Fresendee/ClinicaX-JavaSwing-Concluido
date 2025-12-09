package view;

import dao.Usuariodao;
import model.Usuario;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import dao.DatabaseInitializer;
import java.awt.event.ActionListener;


public class Login extends JFrame {

    private JTextField txtLogin;
    private JPasswordField txtSenha;
    private JButton btnLogin;

    public Login() {
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("Clinicax - Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel painelPrincipal = new JPanel(new GridBagLayout());
        painelPrincipal.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        
        JLabel lblTitulo = new JLabel("Acesso ao Sistema", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(90, 70, 200));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        painelPrincipal.add(lblTitulo, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        painelPrincipal.add(new JLabel("Login:"), gbc);

        gbc.gridx = 1;
        txtLogin = new JTextField(15);
        painelPrincipal.add(txtLogin, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        painelPrincipal.add(new JLabel("Senha:"), gbc);

        gbc.gridx = 1;
        txtSenha = new JPasswordField(15);
        painelPrincipal.add(txtSenha, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        btnLogin = new JButton("Entrar");
        btnLogin.setBackground(new Color(90, 70, 200));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                autenticarUsuario();
            }
        });
        painelPrincipal.add(btnLogin, gbc);
        
        add(painelPrincipal, BorderLayout.CENTER);
        
        JPanel painelRodape = new JPanel();
        painelRodape.setBackground(new Color(90, 70, 200));
        JLabel lblRodape = new JLabel("© Clinicax");
        lblRodape.setForeground(Color.WHITE);
        painelRodape.add(lblRodape);
        add(painelRodape, BorderLayout.SOUTH);
    }

    private void autenticarUsuario() {
        String login = txtLogin.getText();
        String senha = new String(txtSenha.getPassword());

        Usuariodao dao = new Usuariodao();
        Usuario usuario = dao.autenticar(login, senha);

        if (usuario != null) {
            JOptionPane.showMessageDialog(this, "Login realizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            
            this.dispose();
            
            App app = new App();
            app.setVisible(true);
            
        } else {
            JOptionPane.showMessageDialog(this, "Login ou senha inválidos.", "Erro de Autenticação", JOptionPane.ERROR_MESSAGE);
            txtSenha.setText("");
            txtLogin.requestFocus();
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
	        DatabaseInitializer.initialize();

	        SwingUtilities.invokeLater(() -> {
	            new Login().setVisible(true);
	        });
	    }
}
