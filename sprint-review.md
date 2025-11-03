# Sprint Review - Gestão de Cursos

## História de Usuário Implementada
**Como** coordenador de curso  
**Quero** cadastrar e listar cursos da faculdade  
**Para** manter o controle de quais estão ativos  

## Funcionalidades Entregues

### 1. Modelo de Dados
- [x] Entidade `Curso` criada com atributos:
  - id (identificador único)
  - nome (nome do curso)
  - cargaHoraria (duração em horas)
  - ativo (status do curso)

### 2. API REST (/api/cursos)
- [x] Endpoints implementados:
  - GET /api/cursos (listar todos)
  - GET /api/cursos/{id} (buscar por id)
  - POST /api/cursos (criar novo)
  - PUT /api/cursos/{id} (atualizar)
  - DELETE /api/cursos/{id} (excluir)

### 3. Segurança e Permissões
- [x] Controle de acesso implementado:
  - ROLE_COORDENADOR: acesso total (CRUD)
  - ROLE_ALUNO: apenas leitura (GET)

## Testes Realizados
- [x] CRUD básico funcionando
- [x] Validações de permissões
- [x] Retorno apropriado de status HTTP
- [x] Persistência no MongoDB

## Próximos Passos
- [ ] Adicionar validações de campos (ex: carga horária mínima)
- [ ] Implementar filtros na listagem
- [ ] Adicionar logs de auditoria
- [ ] Melhorar documentação da API

## Feedback do Time
- Implementação atende requisitos básicos
- Segurança bem implementada com roles
- API REST seguindo padrões
- Testes cobrem principais casos de uso