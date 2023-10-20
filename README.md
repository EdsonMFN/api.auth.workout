# api.auth.workout
Aplicação de autenticação e autorização, onde é realizado todas as permições de acesso e de segurança da aplicação vinculada api.service.workout.
## Ferramentas utilizadas
- Api rest;
- JAVA 17;
- Spring Boot;
- Spring Security;
- Geração de TokenJwt e refresh token;
- MySql;
- JPA/Hibernate.
### Endpoints de usuario/Aluno: 
Algumas permições são restritas entre Aluno, Professor, Admin e Gestor.

    - (POST) http://localhost:8090/acesso/login - Acesso do aluno;
  	- (GET)  http://localhost:8080/academia - Lista de academias;
  	- (GET) http://localhost:8080/fichaDeTreino/aluno - Lista de fichas de treino;

### Professor: 

    - (POST) http://localhost:8090/acesso/login - Acesso do professor;
  	- (GET)  http://localhost:8080/aluno - Lista de alunos;
    - (POST) http://localhost:8080/fichaDeTreino - Adicionar ficha de treino;
    - (PUT) http://localhost:8080/fichaDeTreino - Alterar ficha de treino;
  	- (GET) http://localhost:8080/fichaDeTreino/aluno - Lista de fichas de treino;
    - (DELETE) http://localhost:8080/fichaDeTreino - Deletar ficha de treino;

### Admin: 

    - (POST) http://localhost:8090/acesso/login - Acesso do admin;
  	- (GET)  http://localhost:8090/acesso - Lista de usuarios;
    - (POST) http://localhost:8080/aluno - Adicionar aluno;
    - (PUT) http://localhost:8080/aluno - Alterar aluno;
  	- (GET) http://localhost:8080/aluno - Lista de aluno;
    - (DELETE) http://localhost:8080/aluno - Deletar aluno;
    
### Gestor: 

    - (POST) http://localhost:8090/acesso/login - Acesso do gestor;
    - (POST) http://localhost:8090/acesso/login/cafastro - Cadastro de usuarios;
  	- (GET)  http://localhost:8090/acesso - Lista de usuarios;
    - (POST) http://localhost:8080/endereco/** - Adicionar, listar, atualizar e deletar endereço;
    - (POST)  http://localhost:8080/academia/**- Adicionar, listar, atualizar e deletar de academia;
