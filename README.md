# hello-clojure

## Basic Start

1. Start the REPL
2. Run the transactor (follow steps at Running with Datomic)
3. Load and evaluate the `components.clj` file
4. Start the server by executing the `ready-steady-go` function
5. Go to [http://localhost:7171/hello?name=YourNameHere](http://localhost:7171/hello?name=Carol) to see: `Hello, {name}!`

#### Running with Datomic
1 - Follow the tutorials below:
[Local Setup](https://docs.datomic.com/setup/setup.html) 
[Run a Transactor](https://docs.datomic.com/peer-tutorial/transactor.html)
O transactor pode ser iniciado (started) com logs da seguinte forma:
*~/dev/datomic-pro-1.0.7075/bin/transactor -Ddatomic.printConnectionInfo=true -verbose:gc -Xlog:gc config/samples/dev-transactor-template.properties*

2 - Para usar a interface grafica do Datomic use o comando: 
*~/dev/datomic-pro-1.0.7075/bin/console -p 8080 dev datomic:dev://localhost:4334/*
[Veja Starting the Console](https://docs.datomic.com/resources/console.html)


Alternatively you can run the test-request functions provided.

## Task 1:
- Create a Clojure WebApp
- Create a server
- Create a /hello route
- Get user name in parameter (query string)
- Response format: text or json 
- Response content: Hello, {name}! 
- Response status: 200

| route       | method | type   | description                                                       |
|-------------|--------|--------|-------------------------------------------------------------------|
| /hello      | get    | query  | say hello to user, if provided name, otherwise you are a stranger |
| /users      | get    | -      | returns all users in the database                                 |
| /users      | post   | body   | creates a new user with the provided name, surname, and age       |
| /users/:id  | get    | path   | returns the user with the specified UUID                          |
| /users/     | get    | query  | returns the user with the specified UUID, name, surname, or age   |
| /users/:id  | put    | body   | updates the user with the specified UUID                          |
| /users/:id  | delete | path   | deletes the user with the specified UUID                          |



## Question:

- Which server is best for this task?
Jetty 11 is the default container used with Pedestal. I decided to use Pedestal because it is the web framework must used at Nubank services.

Jetty is a web server that contains: a servlet container, additionally providing support for HTTP/2, WebSocket, OSGi, JMX, JNDI, JAAS and many other integrations. (and I have no idea of what 98% of this sentence means ⚠️)
 - web server - 
 - servlet container - controla meu ::http (o basico do req/res e seus "componentes" )


## Task 2:

- [x] Create an user with name / surname / age / id (handle UUID)
(atom)
- No need for DB - use an atom
- If a new user is added, we cannot delete the other - so it's added to the vector of user

- [x] Route post - Create - status 201
- [x] Route get - list all users - status 200
- [x] Route delete - deletes an user by ID (path param) - status 204
- [x] Route get (path param) - gets user id , response all user info
status success 200 / not found 404
- [x] Route put - send id via path param and send all user info via request-body


Nice to have:
- [x] Route get - list all users - status 200
- [x] Params via query params to search for users in the list 


## Task 3:
Let's create a digital bank
- [x] Entity User wants to open a bank account (entity account)
- [x] Endpoint post to create account (users/id/accounts) - id via path and account via req-body
- [x] Return schema -> user name / type (savings and checking) / deposit (0.00) for each / operation: opening of each type / status (auto on creation: active)
- [x] se chamar endpoint create de novo cria de novo.
- [x] Endpoint GET to get user accounts (users/id/accounts) - id via path and account via req-body
- [ ] Endpoint GET to get user deposits for a specific account type (users/id/accounts/id/type) - id via path and account via req-body (an account can be both saving AND checking) ~~(carol PF (poup e cc) e PJ )~~
;type can be an entity (polymorphism)

Nice to have
- [ ] query da poupanca os depositos de um periodo (still no deposits)
- [ ] MGM bonus deposit of 100 when creating 

## Task 4:
- [+/-] Criar testes de integração para validar o retorno e status code dos endpoints
- [x] Criar namespace para testes de integração
- [x] Criar comando no lein para rodar os testes de integração
- [x] Usar o io.pedestal.test


## Task 5:
- [x] Refatorar codigo para fazer uso de componentes
- [x] https://github.com/thalfm/clojure-hex
- Vamos criar o system-map no repl - Adicionar apenas db
(def system-map
  (component/system-map
          :db (new-atomdatabase) ;criou o record aqui
        ))
; Apos a criacao do system-map iremos inicializar os componentes de fato. 
(def components (component/start system-map))

;buscar o conteudo do componente para ver se saem os usuarios cadastrados mesmo
get-in
Minha pasta components aqui é como se fossem as libs do nubank 

Gerar com server primeiro e dps com db tbm.
Controller ainda nao esta acessando os componentes. (usar main)

---
#_{:account/user-id 
 :account/id 
 :account/status
 :account/type
 :account/amount 
 }


#_{:user/id
 :user/name
 :user/surname
 :user/age
 :user/accounts [ ]
}


## Task 6:
- [x] Use diplomat arch 
- [x] Use schemas 
- [x] Criar interceptor do server 
printar a request de POST , para ver a estrutura
vamos usar o :json-params e ai usamos essa parte para o schema do user (model/user) no wire-in
- [x] Criar wire-in (entender dados da request -  o que enviamos do postman - json (DS) MAS...)
schema req - relativo a chave (model)
valor é o que tipamos (schema prismatic - wire)
mapear o que vem da minha request
GET nao faz parte


## Task 7:
- [ ] Refatorar tudo para utilizar a arquitetura atual
- [x] Apontar pros e contras da atual arquitetura que estamos usando agora em comparação à anterior
- [ ] Refatorar testes de integração (usando state-flow)
- [ ] Como criar mocks http server (server que starta e para pra cada execucao dos testes = setup and teardown)
- [ ] Criar um flow e testar repetidas vezes algum get (vai startar o component do server e vai dar stop no server?)
- [x] Criar testes unitarios (logic and adapters)


## Task 8:
- [ ] flow de create - de response
- [ ] flow de db persisted
- [ ] criar helper do component de DB
- [ ] Revisar documentacao do helper (adicionar exemplo mais basico com components - 5 steps da revisao)



