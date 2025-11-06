# Guia Rápido - Clinicax Docker

## 🚀 Início Rápido

### 1. Configurar Permissões X11
```bash
xhost +local:docker
```

### 2. Iniciar a Aplicação
```bash
docker compose up --build
```

### 3. Parar a Aplicação
```bash
# Pressione Ctrl+C no terminal
# Ou em outro terminal:
docker compose down
```

### 4. Limpar Permissões X11 (opcional)
```bash
xhost -local:docker
```

## 📝 Comandos Úteis

### Ver logs
```bash
docker compose logs -f
```

### Reiniciar do zero (apaga o banco de dados)
```bash
docker compose down
docker volume rm clinicax_clinicax-data
docker compose up --build
```

### Fazer backup do banco de dados
```bash
docker compose cp clinicax:/app/clinicax.db ./backup-$(date +%Y%m%d).db
```

### Verificar volumes
```bash
docker volume ls
docker volume inspect clinicax_clinicax-data
```

## ⚠️ Problemas Comuns

### Erro: "cannot open display"
**Solução:** Execute `xhost +local:docker` antes de iniciar

### Erro: "permission denied"
**Solução:** Adicione seu usuário ao grupo docker:
```bash
sudo usermod -aG docker $USER
newgrp docker
```

### Erro: "bind source path does not exist"
**Solução:** Use a versão mais recente do `docker-compose.yml` que usa volumes nomeados

### Interface não aparece
**Solução:** Verifique se está em um ambiente com interface gráfica (não SSH sem X11)

## 📦 O Que Está Incluído

- **Dockerfile**: Imagem com Java 21 e dependências X11
- **docker-compose.yml**: Orquestração do container
- **Volume persistente**: Banco de dados mantido entre reinicializações
- **Suporte X11**: Interface gráfica funcional

## 🎯 Primeira Execução

Na primeira vez que você executar:
1. O Docker vai baixar a imagem base (~400 MB)
2. Compilar o projeto com Apache Ant
3. Criar a imagem final (~600-700 MB)
4. Iniciar o container
5. A aplicação criará o banco de dados automaticamente

**Tempo estimado:** 3-5 minutos na primeira execução

## 💡 Dicas

- Use `./run-docker.sh` para iniciar com um único comando
- O banco de dados é criado automaticamente na primeira execução
- Os dados persistem entre reinicializações
- Para resetar tudo, remova o volume `clinicax_clinicax-data`

## 📚 Documentação Completa

- **DOCKER-README.md** - Guia completo do Docker
- **CORRECOES-DOCKER.md** - Histórico de correções
- **DATABASE-FIX.md** - Detalhes sobre o banco de dados
- **JAVA-VERSION-FIX.md** - Informações sobre a versão Java

---

**Versão:** 1.0  
**Data:** Novembro 2025  
**Java:** 21 LTS  
**Docker Compose:** 2.x
