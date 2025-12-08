#!/bin/bash

# Inicia o Xvfb (Servidor X Virtual) em segundo plano
# Isso permite que a aplicação Swing (GUI) seja executada em um ambiente sem tela.
Xvfb :99 -screen 0 1280x1024x24 &
export DISPLAY=:99

# Script de inicialização para garantir que o banco de dados seja criado

echo "Iniciando aplicação Clinicax..."

# Verificar se o banco de dados existe
if [ ! -f /app/clinicax.db ]; then
    echo "Banco de dados não encontrado. Será criado automaticamente pela aplicação."
fi

# Executar a aplicação Java
# O 'exec' garante que o processo Java substitua o processo do shell, mantendo o container ativo.
exec java -cp "dist/Clinicax.jar:lib/sqlite-jdbc.jar:lib/slf4j-api.jar:lib/slf4j-simple.jar" view.Login
