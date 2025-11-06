package model;

import java.time.LocalDate;

/**
 * Classe que representa o Prontuário médico de um paciente
 */
public class Prontuario {
    
    private int id;
    private int pacienteId;
    private int atendimentoId;
    private LocalDate dataRegistro;
    private String anamnese; // História clínica do paciente
    private String historicoFamiliar;
    private String alergias;
    private String medicamentosUso;
    private String doencasPreexistentes;
    private String cirurgiasAnteriores;
    private String habitosVida; // Tabagismo, etilismo, atividade física
    private String pressaoArterial;
    private double peso;
    private double altura;
    private double imc;
    private double temperatura;
    private int frequenciaCardiaca;
    private int frequenciaRespiratoria;
    private String exameFisico;
    private String hipoteseDiagnostica;
    private String condutaMedica;
    private String evolucao;
    private String resultadosExames;
    private String observacoesGerais;
    private String medicoProntuario;
    
    // Construtores
    public Prontuario() {
        this.dataRegistro = LocalDate.now();
    }
    
    public Prontuario(int id, int pacienteId, int atendimentoId, LocalDate dataRegistro,
                     String anamnese, String historicoFamiliar, String alergias,
                     String medicamentosUso, String doencasPreexistentes,
                     String cirurgiasAnteriores, String habitosVida, String pressaoArterial,
                     double peso, double altura, double imc, double temperatura,
                     int frequenciaCardiaca, int frequenciaRespiratoria, String exameFisico,
                     String hipoteseDiagnostica, String condutaMedica, String evolucao,
                     String resultadosExames, String observacoesGerais, String medicoProntuario) {
        this.id = id;
        this.pacienteId = pacienteId;
        this.atendimentoId = atendimentoId;
        this.dataRegistro = dataRegistro;
        this.anamnese = anamnese;
        this.historicoFamiliar = historicoFamiliar;
        this.alergias = alergias;
        this.medicamentosUso = medicamentosUso;
        this.doencasPreexistentes = doencasPreexistentes;
        this.cirurgiasAnteriores = cirurgiasAnteriores;
        this.habitosVida = habitosVida;
        this.pressaoArterial = pressaoArterial;
        this.peso = peso;
        this.altura = altura;
        this.imc = imc;
        this.temperatura = temperatura;
        this.frequenciaCardiaca = frequenciaCardiaca;
        this.frequenciaRespiratoria = frequenciaRespiratoria;
        this.exameFisico = exameFisico;
        this.hipoteseDiagnostica = hipoteseDiagnostica;
        this.condutaMedica = condutaMedica;
        this.evolucao = evolucao;
        this.resultadosExames = resultadosExames;
        this.observacoesGerais = observacoesGerais;
        this.medicoProntuario = medicoProntuario;
    }
    
    // Método para calcular IMC automaticamente
    public void calcularIMC() {
        if (altura > 0 && peso > 0) {
            this.imc = peso / (altura * altura);
        }
    }
    
    // Método para classificar IMC
    public String classificarIMC() {
        if (imc < 18.5) return "Abaixo do peso";
        else if (imc < 25) return "Peso normal";
        else if (imc < 30) return "Sobrepeso";
        else if (imc < 35) return "Obesidade Grau I";
        else if (imc < 40) return "Obesidade Grau II";
        else return "Obesidade Grau III";
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
    
    public int getAtendimentoId() {
        return atendimentoId;
    }
    
    public void setAtendimentoId(int atendimentoId) {
        this.atendimentoId = atendimentoId;
    }
    
    public LocalDate getDataRegistro() {
        return dataRegistro;
    }
    
    public void setDataRegistro(LocalDate dataRegistro) {
        this.dataRegistro = dataRegistro;
    }
    
    public String getAnamnese() {
        return anamnese;
    }
    
    public void setAnamnese(String anamnese) {
        this.anamnese = anamnese;
    }
    
    public String getHistoricoFamiliar() {
        return historicoFamiliar;
    }
    
    public void setHistoricoFamiliar(String historicoFamiliar) {
        this.historicoFamiliar = historicoFamiliar;
    }
    
    public String getAlergias() {
        return alergias;
    }
    
    public void setAlergias(String alergias) {
        this.alergias = alergias;
    }
    
    public String getMedicamentosUso() {
        return medicamentosUso;
    }
    
    public void setMedicamentosUso(String medicamentosUso) {
        this.medicamentosUso = medicamentosUso;
    }
    
    public String getDoencasPreexistentes() {
        return doencasPreexistentes;
    }
    
    public void setDoencasPreexistentes(String doencasPreexistentes) {
        this.doencasPreexistentes = doencasPreexistentes;
    }
    
    public String getCirurgiasAnteriores() {
        return cirurgiasAnteriores;
    }
    
    public void setCirurgiasAnteriores(String cirurgiasAnteriores) {
        this.cirurgiasAnteriores = cirurgiasAnteriores;
    }
    
    public String getHabitosVida() {
        return habitosVida;
    }
    
    public void setHabitosVida(String habitosVida) {
        this.habitosVida = habitosVida;
    }
    
    public String getPressaoArterial() {
        return pressaoArterial;
    }
    
    public void setPressaoArterial(String pressaoArterial) {
        this.pressaoArterial = pressaoArterial;
    }
    
    public double getPeso() {
        return peso;
    }
    
    public void setPeso(double peso) {
        this.peso = peso;
        calcularIMC();
    }
    
    public double getAltura() {
        return altura;
    }
    
    public void setAltura(double altura) {
        this.altura = altura;
        calcularIMC();
    }
    
    public double getImc() {
        return imc;
    }
    
    public void setImc(double imc) {
        this.imc = imc;
    }
    
    public double getTemperatura() {
        return temperatura;
    }
    
    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }
    
    public int getFrequenciaCardiaca() {
        return frequenciaCardiaca;
    }
    
    public void setFrequenciaCardiaca(int frequenciaCardiaca) {
        this.frequenciaCardiaca = frequenciaCardiaca;
    }
    
    public int getFrequenciaRespiratoria() {
        return frequenciaRespiratoria;
    }
    
    public void setFrequenciaRespiratoria(int frequenciaRespiratoria) {
        this.frequenciaRespiratoria = frequenciaRespiratoria;
    }
    
    public String getExameFisico() {
        return exameFisico;
    }
    
    public void setExameFisico(String exameFisico) {
        this.exameFisico = exameFisico;
    }
    
    public String getHipoteseDiagnostica() {
        return hipoteseDiagnostica;
    }
    
    public void setHipoteseDiagnostica(String hipoteseDiagnostica) {
        this.hipoteseDiagnostica = hipoteseDiagnostica;
    }
    
    public String getCondutaMedica() {
        return condutaMedica;
    }
    
    public void setCondutaMedica(String condutaMedica) {
        this.condutaMedica = condutaMedica;
    }
    
    public String getEvolucao() {
        return evolucao;
    }
    
    public void setEvolucao(String evolucao) {
        this.evolucao = evolucao;
    }
    
    public String getResultadosExames() {
        return resultadosExames;
    }
    
    public void setResultadosExames(String resultadosExames) {
        this.resultadosExames = resultadosExames;
    }
    
    public String getObservacoesGerais() {
        return observacoesGerais;
    }
    
    public void setObservacoesGerais(String observacoesGerais) {
        this.observacoesGerais = observacoesGerais;
    }
    
    public String getMedicoProntuario() {
        return medicoProntuario;
    }
    
    public void setMedicoProntuario(String medicoProntuario) {
        this.medicoProntuario = medicoProntuario;
    }
    
    @Override
    public String toString() {
        return "Prontuário #" + id + " - Paciente ID: " + pacienteId + " - " + dataRegistro;
    }
}
