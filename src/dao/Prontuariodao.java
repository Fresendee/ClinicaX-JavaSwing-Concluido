package dao;

import model.Prontuario;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe DAO para gerenciar operações de Prontuário no banco de dados
 */
public class Prontuariodao {
    
    // Inserir novo prontuário
    public boolean inserir(Prontuario prontuario) {
        String sql = "INSERT INTO prontuarios (paciente_id, atendimento_id, data_registro, " +
                    "anamnese, historico_familiar, alergias, medicamentos_uso, doencas_preexistentes, " +
                    "cirurgias_anteriores, habitos_vida, pressao_arterial, peso, altura, imc, " +
                    "temperatura, frequencia_cardiaca, frequencia_respiratoria, exame_fisico, " +
                    "hipotese_diagnostica, conduta_medica, evolucao, resultados_exames, " +
                    "observacoes_gerais, medico_prontuario) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, prontuario.getPacienteId());
            stmt.setInt(2, prontuario.getAtendimentoId());
            stmt.setString(3, prontuario.getDataRegistro().toString());
            stmt.setString(4, prontuario.getAnamnese());
            stmt.setString(5, prontuario.getHistoricoFamiliar());
            stmt.setString(6, prontuario.getAlergias());
            stmt.setString(7, prontuario.getMedicamentosUso());
            stmt.setString(8, prontuario.getDoencasPreexistentes());
            stmt.setString(9, prontuario.getCirurgiasAnteriores());
            stmt.setString(10, prontuario.getHabitosVida());
            stmt.setString(11, prontuario.getPressaoArterial());
            stmt.setDouble(12, prontuario.getPeso());
            stmt.setDouble(13, prontuario.getAltura());
            stmt.setDouble(14, prontuario.getImc());
            stmt.setDouble(15, prontuario.getTemperatura());
            stmt.setInt(16, prontuario.getFrequenciaCardiaca());
            stmt.setInt(17, prontuario.getFrequenciaRespiratoria());
            stmt.setString(18, prontuario.getExameFisico());
            stmt.setString(19, prontuario.getHipoteseDiagnostica());
            stmt.setString(20, prontuario.getCondutaMedica());
            stmt.setString(21, prontuario.getEvolucao());
            stmt.setString(22, prontuario.getResultadosExames());
            stmt.setString(23, prontuario.getObservacoesGerais());
            stmt.setString(24, prontuario.getMedicoProntuario());
            
            stmt.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            System.err.println("Erro ao inserir prontuário: " + e.getMessage());
            return false;
        }
    }
    
    // Atualizar prontuário existente
    public boolean atualizar(Prontuario prontuario) {
        String sql = "UPDATE prontuarios SET paciente_id = ?, atendimento_id = ?, data_registro = ?, " +
                    "anamnese = ?, historico_familiar = ?, alergias = ?, medicamentos_uso = ?, " +
                    "doencas_preexistentes = ?, cirurgias_anteriores = ?, habitos_vida = ?, " +
                    "pressao_arterial = ?, peso = ?, altura = ?, imc = ?, temperatura = ?, " +
                    "frequencia_cardiaca = ?, frequencia_respiratoria = ?, exame_fisico = ?, " +
                    "hipotese_diagnostica = ?, conduta_medica = ?, evolucao = ?, resultados_exames = ?, " +
                    "observacoes_gerais = ?, medico_prontuario = ? WHERE id = ?";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, prontuario.getPacienteId());
            stmt.setInt(2, prontuario.getAtendimentoId());
            stmt.setString(3, prontuario.getDataRegistro().toString());
            stmt.setString(4, prontuario.getAnamnese());
            stmt.setString(5, prontuario.getHistoricoFamiliar());
            stmt.setString(6, prontuario.getAlergias());
            stmt.setString(7, prontuario.getMedicamentosUso());
            stmt.setString(8, prontuario.getDoencasPreexistentes());
            stmt.setString(9, prontuario.getCirurgiasAnteriores());
            stmt.setString(10, prontuario.getHabitosVida());
            stmt.setString(11, prontuario.getPressaoArterial());
            stmt.setDouble(12, prontuario.getPeso());
            stmt.setDouble(13, prontuario.getAltura());
            stmt.setDouble(14, prontuario.getImc());
            stmt.setDouble(15, prontuario.getTemperatura());
            stmt.setInt(16, prontuario.getFrequenciaCardiaca());
            stmt.setInt(17, prontuario.getFrequenciaRespiratoria());
            stmt.setString(18, prontuario.getExameFisico());
            stmt.setString(19, prontuario.getHipoteseDiagnostica());
            stmt.setString(20, prontuario.getCondutaMedica());
            stmt.setString(21, prontuario.getEvolucao());
            stmt.setString(22, prontuario.getResultadosExames());
            stmt.setString(23, prontuario.getObservacoesGerais());
            stmt.setString(24, prontuario.getMedicoProntuario());
            stmt.setInt(25, prontuario.getId());
            
            stmt.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar prontuário: " + e.getMessage());
            return false;
        }
    }
    
    // Excluir prontuário
    public boolean excluir(int id) {
        String sql = "DELETE FROM prontuarios WHERE id = ?";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            System.err.println("Erro ao excluir prontuário: " + e.getMessage());
            return false;
        }
    }
    
    // Listar todos os prontuários
    public List<Prontuario> listarTodos() {
        List<Prontuario> prontuarios = new ArrayList<>();
        String sql = "SELECT * FROM prontuarios ORDER BY data_registro DESC";
        
        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                prontuarios.add(extrairProntuario(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao listar prontuários: " + e.getMessage());
        }
        
        return prontuarios;
    }
    
    // Buscar prontuário por ID
    public Prontuario buscarPorId(int id) {
        String sql = "SELECT * FROM prontuarios WHERE id = ?";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return extrairProntuario(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar prontuário: " + e.getMessage());
        }
        
        return null;
    }
    
    // Buscar prontuários por paciente
    public List<Prontuario> buscarPorPaciente(int pacienteId) {
        List<Prontuario> prontuarios = new ArrayList<>();
        String sql = "SELECT * FROM prontuarios WHERE paciente_id = ? ORDER BY data_registro DESC";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, pacienteId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                prontuarios.add(extrairProntuario(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar prontuários por paciente: " + e.getMessage());
        }
        
        return prontuarios;
    }
    
    // Buscar prontuário por atendimento
    public Prontuario buscarPorAtendimento(int atendimentoId) {
        String sql = "SELECT * FROM prontuarios WHERE atendimento_id = ?";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, atendimentoId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return extrairProntuario(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar prontuário por atendimento: " + e.getMessage());
        }
        
        return null;
    }
    
    // Método auxiliar para extrair prontuário do ResultSet
    private Prontuario extrairProntuario(ResultSet rs) throws SQLException {
        Prontuario prontuario = new Prontuario();
        prontuario.setId(rs.getInt("id"));
        prontuario.setPacienteId(rs.getInt("paciente_id"));
        prontuario.setAtendimentoId(rs.getInt("atendimento_id"));
        prontuario.setDataRegistro(LocalDate.parse(rs.getString("data_registro")));
        prontuario.setAnamnese(rs.getString("anamnese"));
        prontuario.setHistoricoFamiliar(rs.getString("historico_familiar"));
        prontuario.setAlergias(rs.getString("alergias"));
        prontuario.setMedicamentosUso(rs.getString("medicamentos_uso"));
        prontuario.setDoencasPreexistentes(rs.getString("doencas_preexistentes"));
        prontuario.setCirurgiasAnteriores(rs.getString("cirurgias_anteriores"));
        prontuario.setHabitosVida(rs.getString("habitos_vida"));
        prontuario.setPressaoArterial(rs.getString("pressao_arterial"));
        prontuario.setPeso(rs.getDouble("peso"));
        prontuario.setAltura(rs.getDouble("altura"));
        prontuario.setImc(rs.getDouble("imc"));
        prontuario.setTemperatura(rs.getDouble("temperatura"));
        prontuario.setFrequenciaCardiaca(rs.getInt("frequencia_cardiaca"));
        prontuario.setFrequenciaRespiratoria(rs.getInt("frequencia_respiratoria"));
        prontuario.setExameFisico(rs.getString("exame_fisico"));
        prontuario.setHipoteseDiagnostica(rs.getString("hipotese_diagnostica"));
        prontuario.setCondutaMedica(rs.getString("conduta_medica"));
        prontuario.setEvolucao(rs.getString("evolucao"));
        prontuario.setResultadosExames(rs.getString("resultados_exames"));
        prontuario.setObservacoesGerais(rs.getString("observacoes_gerais"));
        prontuario.setMedicoProntuario(rs.getString("medico_prontuario"));
        return prontuario;
    }
}
