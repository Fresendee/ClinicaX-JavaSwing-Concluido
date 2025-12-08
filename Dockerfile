# Dockerfile para aplicação Java Swing - Clinicax
# Usa Eclipse Temurin com Debian para melhor compatibilidade com ferramentas de build

FROM eclipse-temurin:21-jdk-jammy

# Instalar dependências necessárias para GUI (X11), ferramentas de build, dos2unix e Xvfb
RUN apt-get update && apt-get install -y \
    libxext6 \
    libxrender1 \
    libxtst6 \
    libxi6 \
    libxrandr2 \
    libxcursor1 \
    libxinerama1 \
    libxfixes3 \
    libfreetype6 \
    fontconfig \
    x11-apps \
    xvfb \
    x11-utils \
    ant \
    dos2unix \
    && rm -rf /var/lib/apt/lists/*

# Definir diretório de trabalho
WORKDIR /app

# Copiar todos os arquivos do projeto
COPY . .

# Compilar o projeto usando Ant
RUN ant clean jar

# Corrigir quebras de linha (CRLF para LF) e tornar executável o script de inicialização
RUN dos2unix /app/entrypoint.sh \
    && chmod +x /app/entrypoint.sh

# Definir variável de ambiente para o display X11 (Removido, será definido no entrypoint.sh)
# ENV DISPLAY=:0

# Comando para executar a aplicação usando o script de inicialização
ENTRYPOINT ["/app/entrypoint.sh"]
