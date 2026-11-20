# 🐾 Mãos e Patas — Backend API

API REST desenvolvida em **Java + Spring Boot** para o sistema de gestão da clínica veterinária **Mãos e Patas**. Este guia reúne tudo o que é necessário para subir o backend localmente e integrar o frontend (React/Vite).

---

## 🛠️ Stack Tecnológica

* **Java 21+** & **Spring Boot 3+**
* **Spring Security + JWT** (autenticação *stateless* via Bearer Token)
* **Spring Data JPA / Hibernate** + **MySQL 8**
* **Jakarta Validation** & **Lombok**

---

## 🚀 Como rodar o backend localmente

### 1. Pré-requisitos
* JDK 21 ou superior instalado
* MySQL Server rodando localmente na porta `3306`

### 2. Configuração do Banco (`application.yaml`)
O banco `maosepatas_db` é criado automaticamente na primeira execução (`createDatabaseIfNotExist=true`). Caso o seu MySQL local utilize usuário/senha diferentes de `root` / `root`, configure as variáveis de ambiente ou ajuste `src/main/resources/application.yaml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/maosepatas_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=America/Sao_Paulo
    username: ${DB_USER:root}
    password: ${DB_PASSWORD:root}
```

### 3. Executando a aplicação
Rode a classe `MaosepatasApplication` pela IDE ou via terminal:

```bash
./mvnw spring-boot:run
```

* **URL Base da API:** `http://localhost:8080`
* **Origens CORS liberadas:** `http://localhost:5173` (Vite) e `http://localhost:3000`
* **Fuso horário padrão:** `America/Sao_Paulo`

---

## 🔐 Autenticação e Primeiro Acesso

### Credenciais Padrão (Carga Inicial)
Ao subir o projeto pela primeira vez com o banco vazio, o `DataInitializer` cria automaticamente um administrador padrão:

* **E-mail:** `admin@maosepatas.com.br`
* **Senha:** `admin123`

### Como autenticar no Frontend
1. Faça uma requisição `POST /api/auth/login` enviando `email`, `senha` e `lembrarDeMim` (`true` ou `false`).
2. Salve o campo `token` retornado (ex.: `localStorage` ou `sessionStorage`).
3. Em **todas as rotas protegidas**, envie o token no cabeçalho HTTP:
   ```http
   Authorization: Bearer <seu_token_jwt>
   ```

#### Exemplo rápido com `fetch` (TypeScript/React)

```typescript
const API_URL = "http://localhost:8080/api";

// 1. Login
export async function login(email: string, senha: string, lembrarDeMim = false) {
  const response = await fetch(`${API_URL}/auth/login`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ email, senha, lembrarDeMim }),
  });

  if (!response.ok) throw await response.json();
  const data = await response.json();
  // data = { token, tipo: "Bearer", expiracaoMs, administrador: { id, nome, email } }
  localStorage.setItem("token", data.token);
  return data;
}

// 2. Requisição autenticada
export async function getDashboard() {
  const token = localStorage.getItem("token");
  const response = await fetch(`${API_URL}/dashboard`, {
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
    },
  });

  if (!response.ok) throw await response.json();
  return response.json();
}
```

---

## 📡 Mapa Completo de Endpoints

> **Legenda:** 🔓 Rota pública | 🔒 Exige `Authorization: Bearer <token>`

### 1. Autenticação (`/api/auth`)

| Acesso | Método | Rota | Body / Parâmetros | Retorno |
| :--- | :--- | :--- | :--- | :--- |
| 🔓 | `POST` | `/api/auth/login` | `{ "email": "...", "senha": "...", "lembrarDeMim": true }` | `200 OK` (`TokenResponseDTO`) |
| 🔓 | `POST` | `/api/auth/recuperar-senha` | `{ "email": "..." }` | `200 OK` (`{ "mensagem": "..." }`) — *código de 6 dígitos aparece no console do backend* |
| 🔓 | `POST` | `/api/auth/redefinir-senha` | `{ "email": "...", "codigoVerificacao": "123456", "novaSenha": "..." }` | `200 OK` (`{ "mensagem": "..." }`) |
| 🔒 | `POST` | `/api/auth/logout` | — | `204 No Content` *(limpe o token no front)* |

---

### 2. Dashboard (`/api/dashboard`)

| Acesso | Método | Rota | Descrição | Retorno |
| :--- | :--- | :--- | :--- | :--- |
| 🔒 | `GET` | `/api/dashboard` | Retorna indicadores gerais (totais de tutores, animais, atendimentos realizados/pendentes e próximos atendimentos) | `200 OK` (`DashboardResponseDTO`) |

---

### 3. Tutores (`/api/tutores`)

| Acesso | Método | Rota | Body / Query Params | Retorno |
| :--- | :--- | :--- | :--- | :--- |
| 🔒 | `POST` | `/api/tutores` | `TutorRequestDTO` (nome, cpf, endereço, lista de contatos) | `201 Created` (`TutorResponseDTO`) |
| 🔒 | `GET` | `/api/tutores` | — | `200 OK` (`List<TutorResponseDTO>`) |
| 🔒 | `GET` | `/api/tutores/pesquisar` | `?termo=...` *(busca por nome, CPF ou telefone)* | `200 OK` (`List<TutorResponseDTO>`) |
| 🔒 | `GET` | `/api/tutores/filtro` | `?comAnimais=true` ou `false` | `200 OK` (`List<TutorResponseDTO>`) |
| 🔒 | `GET` | `/api/tutores/{id}` | — | `200 OK` (`TutorResponseDTO`) |
| 🔒 | `PUT` | `/api/tutores/{id}` | `TutorRequestDTO` | `200 OK` (`TutorResponseDTO`) |
| 🔒 | `POST` | `/api/tutores/{id}/contatos` | `ContatoRequestDTO` (`telefone`, `whatsapp`, `observacao`) | `201 Created` (`TutorResponseDTO`) |
| 🔒 | `DELETE` | `/api/tutores/{id}/contatos/{contatoId}` | — | `200 OK` (`TutorResponseDTO`) |
| 🔒 | `DELETE` | `/api/tutores/{id}` | — *(bloqueado se houver animais vinculados)* | `204 No Content` |

> 💡 **Dica de integração:** Os objetos de contato retornados nos DTOs já trazem o link pronto do WhatsApp (`https://wa.me/55...`) gerado pelo backend para uso direto em botões de ação.

---

### 4. Animais (`/api/animais`)

| Acesso | Método | Rota | Body / Query Params | Retorno |
| :--- | :--- | :--- | :--- | :--- |
| 🔒 | `POST` | `/api/animais` | `AnimalRequestDTO` (dados do pet + `tutorId`) | `201 Created` (`AnimalResponseDTO`) |
| 🔒 | `POST` | `/api/animais/lote` | `AnimalLoteRequestDTO` (`tutorId` + lista de animais) | `201 Created` (`List<AnimalResponseDTO>`) |
| 🔒 | `GET` | `/api/animais` | — | `200 OK` (`List<AnimalResumoDTO>`) |
| 🔒 | `GET` | `/api/animais/tutor/{tutorId}` | — | `200 OK` (`List<AnimalResumoDTO>`) |
| 🔒 | `GET` | `/api/animais/pesquisar` | `?nome=...` | `200 OK` (`List<AnimalResumoDTO>`) |
| 🔒 | `GET` | `/api/animais/{id}` | — | `200 OK` (`AnimalResponseDTO`) |
| 🔒 | `PUT` | `/api/animais/{id}` | `AnimalRequestDTO` | `200 OK` (`AnimalResponseDTO`) |
| 🔒 | `DELETE` | `/api/animais/{id}` | — | `204 No Content` |

---

### 5. Atendimentos (`/api/atendimentos`)

| Acesso | Método | Rota | Body / Query Params | Retorno |
| :--- | :--- | :--- | :--- | :--- |
| 🔒 | `POST` | `/api/atendimentos` | `AtendimentoRequestDTO` (`animalId`, `tipo`, `dataAtendimento`, `descricao`, etc.) | `201 Created` (`AtendimentoResponseDTO`) |
| 🔒 | `GET` | `/api/atendimentos` | — | `200 OK` (`List<AtendimentoResumoDTO>`) |
| 🔒 | `GET` | `/api/atendimentos/animal/{animalId}` | — | `200 OK` (`List<AtendimentoResumoDTO>`) |
| 🔒 | `GET` | `/api/atendimentos/pesquisar` | `?termo=...` | `200 OK` (`List<AtendimentoResumoDTO>`) |
| 🔒 | `GET` | `/api/atendimentos/filtrar` | `?tipo=...&realizado=true&inicio=2026-11-01T00:00:00&fim=2026-11-30T23:59:59` *(todos opcionais)* | `200 OK` (`List<AtendimentoResumoDTO>`) |
| 🔒 | `GET` | `/api/atendimentos/contadores` | — | `200 OK` (`Map<String, Long>` — totais gerais, realizados e pendentes) |
| 🔒 | `GET` | `/api/atendimentos/{id}` | — | `200 OK` (`AtendimentoResponseDTO`) |
| 🔒 | `PATCH` | `/api/atendimentos/{id}/realizar` | — *(marca o atendimento como concluído)* | `200 OK` (`AtendimentoResponseDTO`) |
| 🔒 | `PUT` | `/api/atendimentos/{id}` | `AtendimentoRequestDTO` | `200 OK` (`AtendimentoResponseDTO`) |
| 🔒 | `DELETE` | `/api/atendimentos/{id}` | — | `204 No Content` |

> 📅 **Formato de Datas:** Envie campos `LocalDateTime` no padrão ISO-8601 (`YYYY-MM-DDTHH:mm:ss`, ex.: `"2026-11-25T14:30:00"`).

---

### 6. Perfil do Administrador (`/api/administradores`)

| Acesso | Método | Rota | Body | Retorno |
| :--- | :--- | :--- | :--- | :--- |
| 🔒 | `GET` | `/api/administradores/perfil` | — *(identificado automaticamente pelo token JWT)* | `200 OK` (`AdministradorResponseDTO`) |
| 🔒 | `PUT` | `/api/administradores/perfil` | `AdministradorUpdateDTO` (`nome`, `email`) | `200 OK` (`AdministradorResponseDTO`) |
| 🔒 | `PATCH` | `/api/administradores/senha` | `AlterarSenhaRequestDTO` (`senhaAtual`, `novaSenha`) | `204 No Content` |

---

## ⚠️ Tratamento Padronizado de Erros

Todas as exceções de validação, regra de negócio ou autenticação retornam um JSON padronizado para facilitar a exibição de alertas/toasts no frontend:

```json
{
  "timestamp": "2026-11-20T09:30:00",
  "status": 400,
  "error": "Regra de negócio",
  "message": "Já existe um tutor cadastrado com o CPF informado",
  "path": "/api/tutores"
}
```

### Principais Status HTTP
* **`200 OK` / `201 Created` / `204 No Content`:** Sucesso na operação.
* **`400 Bad Request`:** Erro de validação de campos (`@Valid`) ou violação de regra de negócio (ex.: CPF/microchip duplicado, exclusão de tutor com animais ativos).
* **`401 Unauthorized`:** Token ausente, expirado ou credenciais de login inválidas (redirecionar usuário para `/login`).
* **`404 Not Found`:** Recurso (`id`) não encontrado no banco.