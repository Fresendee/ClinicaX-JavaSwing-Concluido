package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import model.Usuario;
import dao.Usuariodao;
import javax.swing.JOptionPane;

/**
 * Classe responsável por inicializar o banco de dados SQLite,
 * criando as tabelas necessárias se elas não existirem.
 */
public class DatabaseInitializer {

    public static void initialize() {
        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement()) {

            if (conn != null) {
                // SQL para criar a tabela de Usuários
                String sqlUsuarios = "CREATE TABLE IF NOT EXISTS usuarios (" +
                                      "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                      "login TEXT NOT NULL UNIQUE," +
                                      "senha TEXT NOT NULL" +
                                      ");";
                
                
                stmt.execute(sqlUsuarios);
                
               
                Usuariodao usuarioDao = new Usuariodao();
                Usuario usuarioPadrao = new Usuario();
                usuarioPadrao.setLogin("admin");
                usuarioPadrao.setSenha("admin"); // Senha simples para demonstração
                usuarioDao.inserir(usuarioPadrao);
                
                
                String sqlPacientes = "CREATE TABLE IF NOT EXISTS pacientes (" +
                                      "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                      "nome TEXT NOT NULL," +
                                      "cpf TEXT UNIQUE NOT NULL," +
                                      "dataNascimento TEXT," +
                                      "telefone TEXT," +
                                      "endereco TEXT," +
                                      "email TEXT," +
                                      "convenio TEXT" +
                                      ");";
                
                
                String sqlMedicos = "CREATE TABLE IF NOT EXISTS medicos (" +
                                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                    "nome TEXT NOT NULL," +
                                    "crm TEXT UNIQUE NOT NULL," +
                                    "especialidade TEXT," +
                                    "telefone TEXT," +
                                    "email TEXT," +
                                    "endereco TEXT" +
                                    ");";
                
                
                String sqlConsultas = "CREATE TABLE IF NOT EXISTS consultas (" +
                                      "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                      "idPaciente INTEGER NOT NULL," +
                                      "idMedico INTEGER NOT NULL," +
                                      "dataConsulta TEXT NOT NULL," +
                                      "horaConsulta TEXT NOT NULL," +
                                      "observacoes TEXT," +
                                      "FOREIGN KEY(idPaciente) REFERENCES pacientes(id)," +
                                      "FOREIGN KEY(idMedico) REFERENCES medicos(id)" +
                                      ");";
                
               
                String sqlEspecialidades = "CREATE TABLE IF NOT EXISTS especialidades (" +
                                           "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                           "nome TEXT UNIQUE NOT NULL" +
                                           ");";
                
            
                String sqlAtendimentos = "CREATE TABLE IF NOT EXISTS atendimentos (" +
                                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                        "paciente_id INTEGER NOT NULL," +
                                        "medico_id INTEGER NOT NULL," +
                                        "data_hora_atendimento TEXT NOT NULL," +
                                        "tipo_atendimento TEXT," +
                                        "motivo_atendimento TEXT," +
                                        "sintomas TEXT," +
                                        "diagnostico_inicial TEXT," +
                                        "prescricao TEXT," +
                                        "exames_solicitados TEXT," +
                                        "observacoes TEXT," +
                                        "status TEXT DEFAULT 'Aguardando'," +
                                        "valor_atendimento REAL DEFAULT 0.0," +
                                        "forma_pagamento TEXT," +
                                        "atendimento_realizado INTEGER DEFAULT 0," +
                                        "FOREIGN KEY(paciente_id) REFERENCES pacientes(id)," +
                                        "FOREIGN KEY(medico_id) REFERENCES medicos(id)" +
                                        ");";
                
             
                String sqlProntuarios = "CREATE TABLE IF NOT EXISTS prontuarios (" +
                                       "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                       "paciente_id INTEGER NOT NULL," +
                                       "atendimento_id INTEGER," +
                                       "data_registro TEXT NOT NULL," +
                                       "anamnese TEXT," +
                                       "historico_familiar TEXT," +
                                       "alergias TEXT," +
                                       "medicamentos_uso TEXT," +
                                       "doencas_preexistentes TEXT," +
                                       "cirurgias_anteriores TEXT," +
                                       "habitos_vida TEXT," +
                                       "pressao_arterial TEXT," +
                                       "peso REAL DEFAULT 0.0," +
                                       "altura REAL DEFAULT 0.0," +
                                       "imc REAL DEFAULT 0.0," +
                                       "temperatura REAL DEFAULT 0.0," +
                                       "frequencia_cardiaca INTEGER DEFAULT 0," +
                                       "frequencia_respiratoria INTEGER DEFAULT 0," +
                                       "exame_fisico TEXT," +
                                       "hipotese_diagnostica TEXT," +
                                       "conduta_medica TEXT," +
                                       "evolucao TEXT," +
                                       "resultados_exames TEXT," +
                                       "observacoes_gerais TEXT," +
                                       "medico_prontuario TEXT," +
                                       "FOREIGN KEY(paciente_id) REFERENCES pacientes(id)," +
                                       "FOREIGN KEY(atendimento_id) REFERENCES atendimentos(id)" +
                                       ");";

                
                stmt.execute(sqlPacientes);
                stmt.execute(sqlMedicos);
                stmt.execute(sqlConsultas);
                stmt.execute(sqlEspecialidades);
                stmt.execute(sqlAtendimentos);
                stmt.execute(sqlProntuarios);
                
                System.out.println("Tabelas criadas ou já existentes.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Erro ao inicializar o banco de dados (SQLite)!\n" + e.getMessage(), 
                "Erro de Inicialização", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
