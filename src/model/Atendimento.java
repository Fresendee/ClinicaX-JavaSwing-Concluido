package model;

import java.time.LocalDateTime;

/**
 * Classe que representa um Atendimento médico na clínica
 */
public class Atendimento {
    
    private int id;
    private int pacienteId;
    private int medicoId;
    private LocalDateTime dataHoraAtendimento;
    private String tipoAtendimento; // Consulta, Retorno, Emergência, Exame
    private String motivoAtendimento;
    private String sintomas;
    private String diagnosticoInicial;
    private String prescricao;
    private String examesSolicitados;
    private String observacoes;
    private String status; // Aguardando, Em Atendimento, Finalizado, Cancelado
    private double valorAtendimento;
    private String formaPagamento; // Dinheiro, Cartão, Convênio, PIX
    private boolean atendimentoRealizado;
    
    // Construtores
    public Atendimento() {
        this.dataHoraAtendimento = LocalDateTime.now();
        this.status = "Aguardando";
        this.atendimentoRealizado = false;
    }
    
    public Atendimento(int id, int pacienteId, int medicoId, LocalDateTime dataHoraAtendimento,
                      String tipoAtendimento, String motivoAtendimento, String sintomas,
                      String diagnosticoInicial, String prescricao, String examesSolicitados,
                      String observacoes, String status, double valorAtendimento,
                      String formaPagamento, boolean atendimentoRealizado) {
        this.id = id;
        this.pacienteId = pacienteId;
        this.medicoId = medicoId;
        this.dataHoraAtendimento = dataHoraAtendimento;
        this.tipoAtendimento = tipoAtendimento;
        this.motivoAtendimento = motivoAtendimento;
        this.sintomas = sintomas;
        this.diagnosticoInicial = diagnosticoInicial;
        this.prescricao = prescricao;
        this.examesSolicitados = examesSolicitados;
        this.observacoes = observacoes;
        this.status = status;
        this.valorAtendimento = valorAtendimento;
        this.formaPagamento = formaPagamento;
        this.atendimentoRealizado = atendimentoRealizado;
    }
    
    // Getters e Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getPacienteId() {
        return pacienteId;
    }
    
    public void setPacienteId(int pacienteId) {
        this.pacienteId = pacienteId;
    }
    
    public int getMedicoId() {
        return medicoId;
    }
    
    public void setMedicoId(int medicoId) {
        this.medicoId = medicoId;
    }
    
    public LocalDateTime getDataHoraAtendimento() {
        return dataHoraAtendimento;
    }
    
    public void setDataHoraAtendimento(LocalDateTime dataHoraAtendimento) {
        this.dataHoraAtendimento = dataHoraAtendimento;
    }
    
    public String getTipoAtendimento() {
        return tipoAtendimento;
    }
    
    public void setTipoAtendimento(String tipoAtendimento) {
        this.tipoAtendimento = tipoAtendimento;
    }
    
    public String getMotivoAtendimento() {
        return motivoAtendimento;
    }
    
    public void setMotivoAtendimento(String motivoAtendimento) {
        this.motivoAtendimento = motivoAtendimento;
    }
    
    public String getSintomas() {
        return sintomas;
    }
    
    public void setSintomas(String sintomas) {
        this.sintomas = sintomas;
    }
    
    public String getDiagnosticoInicial() {
        return diagnosticoInicial;
    }
    
    public void setDiagnosticoInicial(String diagnosticoInicial) {
        this.diagnosticoInicial = diagnosticoInicial;
    }
    
    public String getPrescricao() {
        return prescricao;
    }
    
    public void setPrescricao(String prescricao) {
        this.prescricao = prescricao;
    }
    
    public String getExamesSolicitados() {
        return examesSolicitados;
    }
    
    public void setExamesSolicitados(String examesSolicitados) {
        this.examesSolicitados = examesSolicitados;
    }
    
    public String getObservacoes() {
        return observacoes;
    }
    
    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public double getValorAtendimento() {
        return valorAtendimento;
    }
    
    public void setValorAtendimento(double valorAtendimento) {
        this.valorAtendimento = valorAtendimento;
    }
    
    public String getFormaPagamento() {
        return formaPagamento;
    }
    
    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }
    
    public boolean isAtendimentoRealizado() {
        return atendimentoRealizado;
    }
    
    public void setAtendimentoRealizado(boolean atendimentoRealizado) {
        this.atendimentoRealizado = atendimentoRealizado;
    }
    
    @Override
    public String toString() {
        return "Atendimento #" + id + " - " + tipoAtendimento + " - " + status;
    }
}
