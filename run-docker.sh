#!/bin/bash

# Script para executar a aplicação Clinicax com Docker Compose
# Este script configura o X11 para permitir que o container exiba a interface gráfica

echo "==================================="
echo "Clinicax - Docker Compose Runner"
echo "==================================="
echo ""

# Permitir conexões X11 do localhost
echo "Configurando permissões X11..."
xhost +local:docker

# Verificar se o Docker está rodando
if ! docker info > /dev/null 2>&1; then
    echo "ERRO: Docker não está rodando. Por favor, inicie o Docker primeiro."
    exit 1
fi

# Criar diretório de dados se não existir
mkdir -p data

# Executar o docker-compose
echo "Iniciando a aplicação..."
docker-compose up --build

# Remover permissões X11 após encerrar
echo "Removendo permissões X11..."
xhost -local:docker

echo ""
echo "Aplicação encerrada."
