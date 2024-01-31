# hello-clojure

### Task 1:
- Create a Clojure WebApp
- Create a server
- Create a /hello route
- Get user name in parameter (query string)
- Response format: text or json 
- Response content: Hello, {name}! 
- Response status: 200

| route  | method | 
|--------|--------|
| /hello | get    | 

### Question:

- Which server is best for this task?
Jetty 11 is the default container used with Pedestal. I decided to use Pedestal because it is the web framework must used at Nubank services.

Jetty provides a web server and servlet container, additionally providing support for HTTP/2, WebSocket, OSGi, JMX, JNDI, JAAS and many other integrations. (and I have no idea of what 98% of this sentence means ⚠️)

## Getting Started

1. Start the REPL
2. Load and evaluate the `hello.clj` file
3. Start the server - run `start` function
4. Go to [http://localhost:7171/hello?name=YourNameHere](http://localhost:7171/hello?name=Carol) to see: `Hello, {name}!`



