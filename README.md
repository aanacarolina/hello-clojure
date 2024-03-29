# hello-clojure

### Task 1:
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



### Question:

- Which server is best for this task?
Jetty 11 is the default container used with Pedestal. I decided to use Pedestal because it is the web framework must used at Nubank services.

Jetty provides a web server and servlet container, additionally providing support for HTTP/2, WebSocket, OSGi, JMX, JNDI, JAAS and many other integrations. (and I have no idea of what 98% of this sentence means ⚠️)


### Task 2:

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


### Task 3:
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

### Task 4:
- [+/-] Criar testes de integração para validar o retorno e status code dos endpoints
- [x] Criar namespace para testes de integração
- [x] Criar comando no lein para rodar os testes de integração
- [x] Usar o io.pedestal.test


### Task 5:
- [ ] Refatorar codigo para fazer uso de componentes
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

## Getting Started

1. Start the REPL
2. Load and evaluate the `hello.clj` file
3. Start the server - run `start` function
4. Go to [http://localhost:7171/hello?name=YourNameHere](http://localhost:7171/hello?name=Carol) to see: `Hello, {name}!`

Alternatively you can run the test-request functions provided.

