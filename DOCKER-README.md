# Clinicax - Execução com Docker Compose

Este documento descreve como executar a aplicação **Clinicax** utilizando Docker e Docker Compose. A aplicação é uma interface gráfica Java Swing que requer suporte a X11 para exibição da GUI.

## Pré-requisitos

Antes de executar a aplicação, certifique-se de ter instalado:

1. **Docker** (versão 20.10 ou superior)
2. **Docker Compose** (versão 1.29 ou superior)
3. **Servidor X11** (já disponível em sistemas Linux com interface gráfica)

### Instalação do Docker

#### Ubuntu/Debian
```bash
sudo apt-get update
sudo apt-get install docker.io docker-compose
sudo systemctl start docker
sudo systemctl enable docker
sudo usermod -aG docker $USER
```

#### Fedora/RHEL
```bash
sudo dnf install docker docker-compose
sudo systemctl start docker
sudo systemctl enable docker
sudo usermod -aG docker $USER
```

**Nota:** Após adicionar seu usuário ao grupo docker, faça logout e login novamente para que as alterações tenham efeito.

## Estrutura de Arquivos Docker

O projeto agora inclui os seguintes arquivos relacionados ao Docker:

- **Dockerfile**: Define a imagem Docker com JDK 24 e dependências X11
- **docker-compose.yml**: Orquestra o container e configura volumes e rede
- **.dockerignore**: Especifica arquivos a serem ignorados no build
- **run-docker.sh**: Script auxiliar para executar a aplicação (Linux)
- **DOCKER-README.md**: Este arquivo de documentação

## Como Executar

### Método 1: Usando o Script (Recomendado para Linux)

O método mais simples é usar o script fornecido:

```bash
./run-docker.sh
```

Este script automaticamente:
- Configura as permissões X11
- Cria os diretórios necessários
- Executa o docker-compose
- Remove as permissões X11 ao finalizar

### Método 2: Usando Docker Compose Diretamente

Se preferir executar manualmente:

```bash
# 1. Permitir conexões X11 do Docker
xhost +local:docker

# 2. Criar diretório de dados
mkdir -p data

# 3. Executar o container
docker-compose up --build

# 4. Após encerrar, remover permissões X11
xhost -local:docker
```

## Comandos Úteis

### Construir a imagem sem executar
```bash
docker-compose build
```

### Executar em segundo plano (detached mode)
```bash
docker-compose up -d
```

### Ver logs do container
```bash
docker-compose logs -f
```

### Parar o container
```bash
docker-compose down
```

### Remover volumes e dados
```bash
docker-compose down -v
```

### Reconstruir a imagem do zero
```bash
docker-compose build --no-cache
```

## Persistência de Dados

O banco de dados SQLite é persistido através de volumes Docker:

- **Volume nomeado**: `clinicax-data` armazena dados em `/app/data`
- **Bind mount**: `./clinicax.db` mapeia o arquivo de banco diretamente

Isso garante que os dados não sejam perdidos quando o container é removido.

## Troubleshooting

### Erro: "cannot open display"

Este erro indica que o container não consegue se conectar ao servidor X11. Soluções:

1. Execute `xhost +local:docker` antes de iniciar o container
2. Verifique se a variável `DISPLAY` está definida: `echo $DISPLAY`
3. Certifique-se de estar executando em um ambiente com interface gráfica

### Erro: "permission denied" ao executar run-docker.sh

Torne o script executável:
```bash
chmod +x run-docker.sh
```

### Erro: "docker: command not found"

Instale o Docker seguindo as instruções na seção de pré-requisitos.

### A interface não aparece

1. Verifique se o servidor X11 está rodando
2. Tente executar `xhost +` (menos seguro, mas útil para testes)
3. Verifique os logs: `docker-compose logs`

### Problemas de performance gráfica

A renderização via X11 forwarding pode ser mais lenta que a execução nativa. Para melhor performance, considere:

1. Executar nativamente sem Docker
2. Usar VNC ou outras soluções de desktop remoto
3. Otimizar configurações de rede do Docker

## Execução em Windows e macOS

A execução de aplicações GUI Java Swing com Docker em Windows e macOS requer configuração adicional de servidor X11:

### Windows
1. Instale [VcXsrv](https://sourceforge.net/projects/vcxsrv/) ou [Xming](https://sourceforge.net/projects/xming/)
2. Configure o servidor X11 para aceitar conexões
3. Ajuste a variável `DISPLAY` no docker-compose.yml

### macOS
1. Instale [XQuartz](https://www.xquartz.org/)
2. Configure permissões de rede
3. Ajuste a variável `DISPLAY` no docker-compose.yml

**Nota:** A configuração para Windows e macOS é mais complexa e pode apresentar limitações. Para esses sistemas, recomenda-se a execução nativa da aplicação.

## Arquitetura da Solução

A solução Docker para esta aplicação consiste em:

1. **Imagem base**: OpenJDK 24 Slim
2. **Dependências X11**: Bibliotecas necessárias para renderização gráfica
3. **Build tool**: Apache Ant para compilação do projeto
4. **Volumes**: Persistência do banco de dados SQLite
5. **Network mode host**: Facilita o compartilhamento do socket X11

## Considerações de Segurança

- O comando `xhost +local:docker` permite que containers Docker acessem o servidor X11
- Esta configuração é segura para desenvolvimento local
- Para ambientes de produção, considere soluções mais robustas de isolamento
- O script remove automaticamente as permissões após a execução

## Suporte

Para problemas ou dúvidas relacionadas ao Docker:

1. Verifique a documentação oficial do Docker
2. Consulte os logs do container
3. Revise as configurações de rede e volumes

Para problemas relacionados à aplicação Clinicax, consulte a documentação original do projeto.

---

**Versão do Docker Compose**: 3.8  
**Última atualização**: Novembro 2025
