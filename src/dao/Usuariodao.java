package dao;

import model.Usuario;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 * Classe DAO para operações com Usuários no banco de dados
 */
public class Usuariodao {
    
    /**
     * Tenta autenticar um usuário com o login e senha fornecidos.
     * @param login O login do usuário.
     * @param senha A senha do usuário.
     * @return O objeto Usuario se a autenticação for bem-sucedida, ou null caso contrário.
     */
    public Usuario autenticar(String login, String senha) {
        Connection conn = Conexao.conectar();
        // Em um projeto real, a senha deve ser hasheada e comparada com o hash armazenado.
        // Para simplificar, faremos uma comparação direta.
        String sql = "SELECT id, login, senha FROM usuarios WHERE login = ? AND senha = ?";
        Usuario usuario = null;
        
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, login);
            stmt.setString(2, senha);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setLogin(rs.getString("login"));
                usuario.setSenha(rs.getString("senha"));
            }
            
            rs.close();
            stmt.close();
            Conexao.desconectar(conn);
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Erro ao autenticar usuário!\n" + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
        
        return usuario;
    }
    
    /**
     * Insere um novo usuário no banco de dados.
     * Este método será usado apenas para criar o usuário inicial, se necessário.
     * @param usuario O objeto Usuario a ser inserido.
     * @return true se a inserção for bem-sucedida, false caso contrário.
     */
    public boolean inserir(Usuario usuario) {
        Connection conn = Conexao.conectar();
        String sql = "INSERT INTO usuarios (login, senha) VALUES (?, ?)";
        
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, usuario.getLogin());
            stmt.setString(2, usuario.getSenha());
            
            stmt.executeUpdate();
            stmt.close();
            Conexao.desconectar(conn);
            
            return true;
            
        } catch (SQLException e) {
            // Não exibe erro se for uma tentativa de inserir um usuário que já existe
            // (por exemplo, se a tabela já tiver sido criada e populada).
            System.err.println("Erro ao inserir usuário: " + e.getMessage());
            return false;
        }
    }
}
