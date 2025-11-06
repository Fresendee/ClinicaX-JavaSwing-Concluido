# Solução para o Banco de Dados SQLite

## Problema Identificado

**Erro exibido:**
```
Erro ao conectar com o banco de dados!
[SQLITE_CANTOPEN] Unable to open the database file (unable to open database file)
```

**Causa:**
O banco de dados SQLite `clinicax.db` é criado automaticamente pela aplicação no primeiro uso, mas precisa de permissões corretas e um volume persistente no Docker.

## Solução Aplicada

### 1. Volume Persistente

O `docker-compose.yml` foi ajustado para usar um **volume nomeado** que persiste todo o diretório `/app`:

```yaml
volumes:
  - clinicax-data:/app
```

Isso garante que:
- O banco de dados `clinicax.db` seja criado na primeira execução
- Os dados sejam mantidos entre reinicializações do container
- As permissões sejam preservadas

### 2. Inicialização Automática

A aplicação possui um inicializador de banco de dados (`DatabaseInitializer.java`) que:
- Cria o arquivo `clinicax.db` automaticamente
- Cria as tabelas necessárias (pacientes, médicos, consultas, especialidades)
- Executa na primeira vez que a aplicação é iniciada

## Como Funciona

### Primeira Execução

1. O container é iniciado
2. A aplicação detecta que o banco não existe
3. O `DatabaseInitializer` cria o arquivo `clinicax.db`
4. As tabelas são criadas automaticamente
5. A aplicação está pronta para uso

### Execuções Subsequentes

1. O container é iniciado
2. O banco de dados já existe no volume `clinicax-data`
3. A aplicação se conecta ao banco existente
4. Todos os dados anteriores estão disponíveis

## Verificando o Banco de Dados

### Ver o conteúdo do volume

```bash
# Listar volumes
docker volume ls

# Inspecionar o volume
docker volume inspect clinicax_clinicax-data

# Acessar o container e verificar o banco
docker compose exec clinicax ls -lah /app/clinicax.db
```

### Acessar o banco de dados diretamente

```bash
# Entrar no container
docker compose exec clinicax bash

# Abrir o SQLite
sqlite3 /app/clinicax.db

# Listar tabelas
.tables

# Ver estrutura de uma tabela
.schema pacientes

# Sair
.quit
```

## Backup e Restauração

### Fazer Backup

```bash
# Copiar o banco de dados do container para o host
docker compose cp clinicax:/app/clinicax.db ./backup-clinicax.db
```

### Restaurar Backup

```bash
# Copiar o banco de dados do host para o container
docker compose cp ./backup-clinicax.db clinicax:/app/clinicax.db

# Ou parar o container e substituir o volume
docker compose down
docker volume rm clinicax_clinicax-data
# Criar novo volume e copiar o backup
```

## Resetar o Banco de Dados

Se você quiser começar do zero:

```bash
# Parar o container
docker compose down

# Remover o volume
docker volume rm clinicax_clinicax-data

# Iniciar novamente (criará um novo banco vazio)
docker compose up
```

## Persistência de Dados

Os dados são armazenados em um **volume Docker nomeado** chamado `clinicax-data`. Este volume:

- ✅ Persiste entre reinicializações do container
- ✅ Persiste mesmo após `docker compose down`
- ✅ Só é removido com `docker volume rm`
- ✅ Pode ser copiado para backup
- ✅ Pode ser compartilhado entre containers

## Localização do Banco

### No Container
```
/app/clinicax.db
```

### No Host (volume Docker)
```
/var/lib/docker/volumes/clinicax_clinicax-data/_data/clinicax.db
```

**Nota:** Não é recomendado acessar diretamente os arquivos em `/var/lib/docker/volumes/`. Use os comandos Docker para manipular volumes.

## Troubleshooting

### Erro: "database is locked"

**Causa:** Múltiplas instâncias tentando acessar o banco simultaneamente.

**Solução:**
```bash
# Parar todos os containers
docker compose down

# Iniciar novamente
docker compose up
```

### Erro: "disk I/O error"

**Causa:** Problemas de permissão ou espaço em disco.

**Solução:**
```bash
# Verificar espaço em disco
df -h

# Verificar permissões do volume
docker volume inspect clinicax_clinicax-data
```

### Banco não persiste após reiniciar

**Causa:** Volume não está configurado corretamente.

**Solução:** Verifique se o `docker-compose.yml` tem a seção `volumes` correta:
```yaml
volumes:
  - clinicax-data:/app

volumes:
  clinicax-data:
    driver: local
```

## Alternativa: Bind Mount

Se preferir ter o banco de dados em um diretório específico do host:

```yaml
volumes:
  - /tmp/.X11-unix:/tmp/.X11-unix:rw
  - ./data:/app/data
  - ./clinicax.db:/app/clinicax.db
```

**Vantagens:**
- Fácil acesso ao arquivo do banco
- Backup simples (copiar arquivo)

**Desvantagens:**
- Problemas de permissão podem ocorrer
- Menos portável entre sistemas

## Estrutura do Banco de Dados

O banco `clinicax.db` contém as seguintes tabelas:

- **pacientes**: Informações dos pacientes
- **medicos**: Cadastro de médicos
- **especialidades**: Especialidades médicas
- **consultas**: Agendamento de consultas

Todas as tabelas são criadas automaticamente pelo `DatabaseInitializer` na primeira execução.

---

**Conclusão:** O banco de dados agora está configurado para ser criado automaticamente na primeira execução e persistir entre reinicializações do container. Não é necessário criar o banco manualmente.
