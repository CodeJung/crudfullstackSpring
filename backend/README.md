

## Backend — visão do aluno

Eu sou aluno e uso este projeto para ver os cursos ofertados pela faculdade.

O backend é uma API em Spring Boot que fornece endpoints para cursos e outras entidades. Abaixo descrevo o que eu, como aluno, preciso saber para testar e usar.

### O que eu posso (como aluno)

- Listar todos os cursos
- Ver detalhes de um curso

### O que eu não posso (somente coordenador)

- Criar, editar ou excluir cursos (essas ações exigem `ROLE_COORDENADOR`)

### Endpoints principais

| Método | Endpoint | Descrição | Role mínima |
|--------|----------|-----------|------------|
| GET    | `/api/cursos` | Lista todos os cursos | ALUNO ou COORDENADOR |
| GET    | `/api/cursos/{id}` | Obtém detalhes de um curso | ALUNO ou COORDENADOR |
| POST   | `/api/cursos` | Cria um novo curso | COORDENADOR |
| PUT    | `/api/cursos/{id}` | Atualiza curso existente | COORDENADOR |
| DELETE | `/api/cursos/{id}` | Exclui um curso | COORDENADOR |

### Como testar rápido 

Iniciar o backend:

```powershell
docker compose up -- bild
```

Testar via navegador ou ferramenta REST (Postman / curl).

Exemplo com curl (autenticação básica):

```powershell
user name: aluno senha: senha123 http://localhost:8080/api/cursos
```

Exemplo com Postman:

URL: `http://localhost:8080/api/cursos`

Método: `GET`

Authorization → Basic Auth: usuário = `aluno`, senha = `senha123`

---

## 2️⃣ Configurar autenticação básica

Todos os endpoints exigem login:

No Postman, abra a aba Authorization da requisição.

Tipo: Basic Auth

Usuário e senha:

Aluno:

Username: `aluno`

Password: `senha123`

Coordenador:

Username: `coordenador`

Password: `senha123`

O aluno só pode listar cursos, o coordenador pode criar, editar e excluir.

---

## 3️⃣ Testando os endpoints

a) Listar todos os cursos (GET)

- Método: `GET`
- URL: `http://localhost:8080/api/cursos`
- Authorization: Basic Auth (use `aluno` ou `coordenador`)
- Body: nenhum

Clique em Send — Deve retornar JSON com todos os cursos.

b) Ver detalhes de um curso (GET)

- Método: `GET`
- URL: `http://localhost:8080/api/cursos/{id}`
	- Ex.: `http://localhost:8080/api/cursos/1`
- Authorization: Basic Auth
- Body: nenhum

Send → deve retornar JSON do curso ou 404 se ID não existir.

c) Criar um curso (POST) — somente coordenador

- Método: `POST`
- URL: `http://localhost:8080/api/cursos`
- Authorization: Basic Auth (coordenador)
- Body → raw → JSON:

```json
{
	"nome": "Matemática",
	"cargaHoraria": 60,
	"ativo": true
}
```

Send → deve retornar JSON do curso criado com status 201.

d) Atualizar um curso (PUT) — somente coordenador

- Método: `PUT`
- URL: `http://localhost:8080/api/cursos/{id}`
	- Ex.: `http://localhost:8080/api/cursos/1`
- Authorization: Basic Auth (coordenador)
- Body → raw → JSON:

```json
{
	"nome": "Matemática Avançada",
	"cargaHoraria": 80,
	"ativo": true
}
```

Send → deve retornar JSON atualizado com status 200.

e) Excluir um curso (DELETE) — somente coordenador

- Método: `DELETE`
- URL: `http://localhost:8080/api/cursos/{id}`
	- Ex.: `http://localhost:8080/api/cursos/1`
- Authorization: Basic Auth (coordenador)
