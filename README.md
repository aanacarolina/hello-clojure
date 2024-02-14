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
- If a new user is added, we cannot delete the other - so it added to the vector of user

- [x] Route post - Create - status 201
- [x] Route get - list all users - status 200
- [x] Route delete - deletes an user by ID (path param) - status 204
- [x] Route get (path param) - gets user id , response all user info
status success 200 / not found 404
- [?] Route put - send id via path param and send all user info via request-body


Nice to have:
ok - Route get - list all users - status 200
not yet - Params via query params to search for users in the list 

## Getting Started

1. Start the REPL
2. Load and evaluate the `hello.clj` file
3. Start the server - run `start` function
4. Go to [http://localhost:7171/hello?name=YourNameHere](http://localhost:7171/hello?name=Carol) to see: `Hello, {name}!`

Alternatively you can run the test-request functions provided.

