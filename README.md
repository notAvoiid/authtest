[JAVA_BADGE]:https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white
[SPRING_BADGE]:https://img.shields.io/badge/spring-%2382B54B.svg?style=for-the-badge&logo=spring&logoColor=white
[JUNIT_BADGE]:https://img.shields.io/badge/JUnit5-25A162.svg?style=for-the-badge&logo=JUnit5&logoColor=white
[MOCKITO_BADGE]:https://img.shields.io/badge/Mockito-4D4D4D.svg?style=for-the-badge&logo=Mockito&logoColor=white


<h1 align="center" style="font-weight: bold;">Authentication Test Project 🧪</h1>

![spring][SPRING_BADGE]
![java][JAVA_BADGE]
![junit][JUNIT_BADGE]
![mockito][MOCKITO_BADGE]

<br>

<p align="center">
  <b>Projeto para testar métodos de autenticação com cobertura de testes.</b>
</p>

## 🚀 Começando

Este projeto é uma API de autenticação desenvolvida com Spring Boot, JPA e Spring Security, projetada para fornecer uma base sólida para testes automatizados. JUnit e Mockito foram utilizados para garantir a alta confiabilidade do sistema.

## ⚙️ Tecnologias

- **Linguagem**: Java
- **Framework**: Spring Boot (Web, Jpa, Spring Security)
- **Banco de Dados**: PostgreSQL
- **Ferramenta de construção**: Maven
- **Testes**: JUnit 5, Mockito
- **Contêinerização**: Docker

## 🔄 Clonando

Clone o projeto usando HTTPS:
```
git clone https://github.com/notAvoiid/authtest.git
```

Ou, se preferir usar SSH:
```
git clone git@github.com:notAvoiid/authtest.git
```

## 🟢 Executando o projeto
```bash
# 1. Navegue até o diretório do projeto.
cd authtest

# 2. (Opcional) Para Linux: Verifica e para o PostgreSQL caso esteja sendo usado em background.
sudo service postgresql stop

# 3. Crie um arquivo na raiz do projeto chamado `.env`.
touch .env 

# 4. Abra o arquivo e substitua pelos valores apropriados

DB_PASSWORD=${DB_PASSWORD}
DB_USER=${DB_USERNAME}
DB_DATABASE=${DB_URL}
SECRET_TOKEN=${SECRET_TOKEN}

# 5. Altere as configurações no `application.properties`

spring.datasource.url=${DB_DATABASE}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

# 6. Inicialize o projeto utilizando o docker-compose.yml:

docker compose --env-file .env up --build -d
```

## 📄 Documentação

1. Certifique-se de que o projeto está rodando localmente.
2. Navegue até `http://localhost:8080/swagger-ui/index.html#/` no seu navegador ou clique aqui segurando CTRL: [Swagger](http://localhost:8080/swagger-ui/index.html#/)  

## 📫 Contribuição

Para me ajudar a melhorar o projeto ou me ajudar a melhorar:

1. Clone: `git clone https://github.com/notAvoiid/authtest.git` ou `git clone git@github.com:notAvoiid/authtest.git`
2. Criando sua própria feature: `git checkout -b feature/NAME`
3. Siga os padrões de commit.
4. Abra um Pull Request explicando o problema resolvido ou a feature implementada. Prints com detalhes são importantes!

## Documentações que podem ajudar

[📝 Como criar um Pull Request](https://www.atlassian.com/br/git/tutorials/making-a-pull-request)

[💾 Padrões de commits](https://gist.github.com/joshbuchea/6f47e86d2510bce28f8e7f42ae84c716)
