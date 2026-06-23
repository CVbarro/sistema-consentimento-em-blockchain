# 🔐 Sistema de Consentimento em Blockchain

> Sistema de gerenciamento de consentimento de cookies registrado em blockchain, desenvolvido em Java. Garante transparência, imutabilidade e rastreabilidade dos registros de consentimento dos usuários, em conformidade com a LGPD.

---

## 📋 Sobre o Projeto

Este projeto implementa um sistema de consentimento de cookies utilizando tecnologia blockchain como camada de persistência imutável. Cada ação de consentimento (aceite, recusa ou revogação) é registrada como um bloco na cadeia, garantindo um histórico auditável e à prova de adulteração.

A arquitetura combina os princípios da LGPD (Lei Geral de Proteção de Dados) com as propriedades fundamentais do blockchain — descentralização, imutabilidade e transparência — para criar um registro confiável das escolhas de privacidade dos usuários.

---

## 🚀 Funcionalidades

- **Registro de Consentimento** — armazena o aceite ou recusa de cookies em um bloco da blockchain
- **Revogação de Consentimento** — permite ao usuário revogar seu consentimento a qualquer momento, com o registro imutável da ação
- **Validação da Cadeia** — verifica a integridade de toda a blockchain, detectando qualquer alteração indevida
- **Histórico Auditável** — consulta o histórico completo de consentimentos de um usuário
- **Hashing com SHA-256** — cada bloco é identificado por um hash criptográfico que encadeia os blocos de forma segura

---

## 🛠️ Tecnologias

| Tecnologia | Versão | Uso |
|---|---|---|
| Java | 11+ | Linguagem principal |
| Maven | 3.x | Gerenciador de dependências e build |
| SHA-256 | — | Algoritmo de hashing dos blocos |

---

## 📁 Estrutura do Projeto

```
sistema-consentimento-em-blockchain/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── sistemaConsentimento/
│                   └── Cookie/
│                       ├── Block.java          # Estrutura de um bloco da blockchain
│                       ├── Blockchain.java     # Lógica da cadeia de blocos
│                       ├── Consent.java        # Modelo de dados do consentimento
│                       └── Main.java           # Ponto de entrada da aplicação
├── LICENSE
└── README.md
```

---

## ▶️ Como Executar

### Pré-requisitos

- Java 11 ou superior instalado
- Maven 3.x instalado

### Passos

1. Clone o repositório:
   ```bash
   git clone https://github.com/CVbarro/sistema-consentimento-em-blockchain.git
   cd sistema-consentimento-em-blockchain
   ```

2. Compile o projeto:
   ```bash
   mvn compile
   ```

3. Execute a aplicação:
   ```bash
   mvn exec:java -Dexec.mainClass="com.sistemaConsentimento.Cookie.Main"
   ```

   Ou, alternativamente, compile e execute direto com o Java:
   ```bash
   javac -d out src/main/java/com/sistemaConsentimento/Cookie/*.java
   java -cp out com.sistemaConsentimento.Cookie.Main
   ```

---

## 🧠 Como Funciona

### Estrutura de um Bloco

Cada registro de consentimento gera um bloco contendo:

```
┌────────────────────────────────────────┐
│  Index          │ Posição na cadeia    │
│  Timestamp      │ Data/hora do evento  │
│  Dados          │ Ação de consentimento│
│  Hash Anterior  │ Liga ao bloco acima  │
│  Hash Atual     │ SHA-256 deste bloco  │
└────────────────────────────────────────┘
```

### Fluxo de Consentimento

```
Usuário acessa o site
        │
        ▼
Sistema exibe banner de cookies
        │
   ┌────┴────┐
   │         │
Aceita    Recusa
   │         │
   └────┬────┘
        ▼
Criação do bloco de consentimento
        │
        ▼
Hash SHA-256 calculado com base
no bloco anterior + dados atuais
        │
        ▼
Bloco adicionado à blockchain
        │
        ▼
Registro imutável confirmado ✅
```

### Validação de Integridade

A cada consulta, o sistema recalcula os hashes de toda a cadeia e compara com os valores armazenados. Qualquer adulteração em um bloco invalida todos os blocos subsequentes, tornando a fraude imediatamente detectável.

---

## 📜 Conformidade com a LGPD

Este sistema foi projetado levando em conta os seguintes princípios da LGPD (Lei nº 13.709/2018):

- **Art. 8º** — O consentimento deve ser fornecido por escrito ou por outro meio que demonstre a manifestação de vontade do titular
- **Art. 9º** — O titular tem direito ao acesso facilitado às informações sobre o tratamento de seus dados
- **Art. 15º** — O término do tratamento de dados ocorre quando o titular solicita a revogação do consentimento
- **Rastreabilidade** — Todo histórico de consentimento fica registrado de forma auditável

---

## 🤝 Contribuindo

Contribuições são bem-vindas! Para contribuir:

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/minha-feature`)
3. Commit suas alterações (`git commit -m 'feat: adiciona minha feature'`)
4. Faça o push para a branch (`git push origin feature/minha-feature`)
5. Abra um Pull Request

---

## 📄 Licença

Este projeto está licenciado sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---

## 👤 Autor

Desenvolvido por **CVbarro**

[![GitHub](https://img.shields.io/badge/GitHub-CVbarro-181717?style=flat&logo=github)](https://github.com/CVbarro)
