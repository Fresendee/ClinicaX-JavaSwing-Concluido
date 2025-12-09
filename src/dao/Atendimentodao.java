package dao;

import model.Atendimento;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class Atendimentodao {
    
    // Inserir novo atendimento
    public boolean inserir(Atendimento atendimento) {
        String sql = "INSERT INTO atendimentos (paciente_id, medico_id, data_hora_atendimento, " +
                    "tipo_atendimento, motivo_atendimento, sintomas, diagnostico_inicial, " +
                    "prescricao, exames_solicitados, observacoes, status, valor_atendimento, " +
                    "forma_pagamento, atendimento_realizado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, atendimento.getPacienteId());
            stmt.setInt(2, atendimento.getMedicoId());
            stmt.setString(3, atendimento.getDataHoraAtendimento().toString());
            stmt.setString(4, atendimento.getTipoAtendimento());
            stmt.setString(5, atendimento.getMotivoAtendimento());
            stmt.setString(6, atendimento.getSintomas());
            stmt.setString(7, atendimento.getDiagnosticoInicial());
            stmt.setString(8, atendimento.getPrescricao());
            stmt.setString(9, atendimento.getExamesSolicitados());
            stmt.setString(10, atendimento.getObservacoes());
            stmt.setString(11, atendimento.getStatus());
            stmt.setDouble(12, atendimento.getValorAtendimento());
            stmt.setString(13, atendimento.getFormaPagamento());
            stmt.setBoolean(14, atendimento.isAtendimentoRealizado());
            
            stmt.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            System.err.println("Erro ao inserir atendimento: " + e.getMessage());
            return false;
        }
    }
    
  
    public boolean atualizar(Atendimento atendimento) {
        String sql = "UPDATE atendimentos SET paciente_id = ?, medico_id = ?, " +
                    "data_hora_atendimento = ?, tipo_atendimento = ?, motivo_atendimento = ?, " +
                    "sintomas = ?, diagnostico_inicial = ?, prescricao = ?, exames_solicitados = ?, " +
                    "observacoes = ?, status = ?, valor_atendimento = ?, forma_pagamento = ?, " +
                    "atendimento_realizado = ? WHERE id = ?";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, atendimento.getPacienteId());
            stmt.setInt(2, atendimento.getMedicoId());
            stmt.setString(3, atendimento.getDataHoraAtendimento().toString());
            stmt.setString(4, atendimento.getTipoAtendimento());
            stmt.setString(5, atendimento.getMotivoAtendimento());
            stmt.setString(6, atendimento.getSintomas());
            stmt.setString(7, atendimento.getDiagnosticoInicial());
            stmt.setString(8, atendimento.getPrescricao());
            stmt.setString(9, atendimento.getExamesSolicitados());
            stmt.setString(10, atendimento.getObservacoes());
            stmt.setString(11, atendimento.getStatus());
            stmt.setDouble(12, atendimento.getValorAtendimento());
            stmt.setString(13, atendimento.getFormaPagamento());
            stmt.setBoolean(14, atendimento.isAtendimentoRealizado());
            stmt.setInt(15, atendimento.getId());
            
            stmt.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar atendimento: " + e.getMessage());
            return false;
        }
    }
    
    
    public boolean excluir(int id) {
        String sql = "DELETE FROM atendimentos WHERE id = ?";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            System.err.println("Erro ao excluir atendimento: " + e.getMessage());
            return false;
        }
    }
    
    
    public List<Atendimento> listarTodos() {
        List<Atendimento> atendimentos = new ArrayList<>();
        String sql = "SELECT * FROM atendimentos ORDER BY data_hora_atendimento DESC";
        
        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                atendimentos.add(extrairAtendimento(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao listar atendimentos: " + e.getMessage());
        }
        
        return atendimentos;
    }
    
    
    public Atendimento buscarPorId(int id) {
        String sql = "SELECT * FROM atendimentos WHERE id = ?";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return extrairAtendimento(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar atendimento: " + e.getMessage());
        }
        
        return null;
    }
    
    
    public List<Atendimento> buscarPorPaciente(int pacienteId) {
        List<Atendimento> atendimentos = new ArrayList<>();
        String sql = "SELECT * FROM atendimentos WHERE paciente_id = ? ORDER BY data_hora_atendimento DESC";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, pacienteId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                atendimentos.add(extrairAtendimento(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar atendimentos por paciente: " + e.getMessage());
        }
        
        return atendimentos;
    }
    
    
    public List<Atendimento> buscarPorMedico(int medicoId) {
        List<Atendimento> atendimentos = new ArrayList<>();
        String sql = "SELECT * FROM atendimentos WHERE medico_id = ? ORDER BY data_hora_atendimento DESC";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, medicoId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                atendimentos.add(extrairAtendimento(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar atendimentos por médico: " + e.getMessage());
        }
        
        return atendimentos;
    }
    
    
    public List<Atendimento> buscarPorStatus(String status) {
        List<Atendimento> atendimentos = new ArrayList<>();
        String sql = "SELECT * FROM atendimentos WHERE status = ? ORDER BY data_hora_atendimento DESC";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                atendimentos.add(extrairAtendimento(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar atendimentos por status: " + e.getMessage());
        }
        
        return atendimentos;
    }
    
    
    private Atendimento extrairAtendimento(ResultSet rs) throws SQLException {
        Atendimento atendimento = new Atendimento();
        atendimento.setId(rs.getInt("id"));
        atendimento.setPacienteId(rs.getInt("paciente_id"));
        atendimento.setMedicoId(rs.getInt("medico_id"));
        atendimento.setDataHoraAtendimento(LocalDateTime.parse(rs.getString("data_hora_atendimento")));
        atendimento.setTipoAtendimento(rs.getString("tipo_atendimento"));
        atendimento.setMotivoAtendimento(rs.getString("motivo_atendimento"));
        atendimento.setSintomas(rs.getString("sintomas"));
        atendimento.setDiagnosticoInicial(rs.getString("diagnostico_inicial"));
        atendimento.setPrescricao(rs.getString("prescricao"));
        atendimento.setExamesSolicitados(rs.getString("exames_solicitados"));
        atendimento.setObservacoes(rs.getString("observacoes"));
        atendimento.setStatus(rs.getString("status"));
        atendimento.setValorAtendimento(rs.getDouble("valor_atendimento"));
        atendimento.setFormaPagamento(rs.getString("forma_pagamento"));
        atendimento.setAtendimentoRealizado(rs.getBoolean("atendimento_realizado"));
        return atendimento;
    }
}
