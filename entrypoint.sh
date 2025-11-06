#!/bin/bash

# Script de inicialização para garantir que o banco de dados seja criado

echo "Iniciando aplicação Clinicax..."

# Verificar se o banco de dados existe
if [ ! -f /app/clinicax.db ]; then
    echo "Banco de dados não encontrado. Será criado automaticamente pela aplicação."
fi

# Executar a aplicação Java
exec java -cp "dist/Clinicax.jar:lib/sqlite-jdbc.jar:lib/slf4j-api.jar:lib/slf4j-simple.jar" view.Login
