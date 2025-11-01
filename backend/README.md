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
docker compose up -- build
```



Exemplo com curl (autenticação básica):

```powershell
curl -u aluno:senha123 http://localhost:8080/api/cursos
```

Exemplo com Postman:

URL: `http://localhost:8080/api/cursos`

Método: `GET`

Authorization → Basic Auth: usuário = `aluno`, senha = `senha123`
# Backend Spring Boot

CRUD com MongoDB Atlas