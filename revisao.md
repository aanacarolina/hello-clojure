- O que é REST?
(representational state transfer)
É um padrao arquitetural para aplicacacoes web, pois faz uso do protocolo http (v1.1 - que é utilizada pela maior parte dos browsers).
É stateless! 
Não é flexível (ex: 1 endpoint para nome / 1 endpoint para cpf )
Finding/query foge um pouco do padrao, pois fica mais aberto, mas é aceito.

- O QUE É RESTFUL?
Implementa os padroes REST.
VERBOS HTTP 
PATH RECURSO (entidade - pode ser dinamico)
STATUS CODES
HATEOAS (Hypermedia as the Engine of Application State)

- O que é HTTP?
Protocolo de transferencia de hipertexto
Uma string contendo todas as informacoes necessarias para comunicar com servidores.
Ambas para  REQ(client) e RES(server)
Cache pode estar nesse nivel tbm, mas não no REST.  

[Anatomy of an HTTP GET request](https://www.oreilly.com/library/view/head-first-servlets/9780596516680/ch01s13.html)
[Modelo OSI - Open Systems Interconnection](https://community.cisco.com/t5/image/serverpage/image-id/180291iDA59C8DFF9920CD8?v=v2)

- REPL
Lein
Calva/Cursive

- URL x URI
TODO

- HTTP Status 
Padronizacao de respostas do servidor

Mais usados
200 - update
201 - post (creation)
202 - fluxos asyncs
204 - delecoes (confirma mas nao "devolve" nada)

301 - DNS ou HOST NAME change (faz redirect automaticamente)

- O que é uma API?
Application Programming Interface 

- O que é um interceptor?
Levar ao pé da letra. Intercepta algo. Um pilha (antes ou depois). 

No caso do pedestal estamos tratando de aplicações web, logo intercepta req e res.

- Arquitetura Hexagonal
 
 ![Arquitetura Hexagonal](hexarch.png)
 Video: [Complex Made Bearable -  Diplomat Architecture](https://www.youtube.com/watch?v=ct5aWqhHARs)

- Ports 
implementam comunicacao com mundo externo (http)
ex: components 

- Adapters
Fazem o Wire dos ports para os controllers.
accessan todos os compnentes e dependencia explicita


Controllers
Executam as logics 
nao devem estar acopladas


- Datomic 
transactor 


- Components
Processo unico

- Tests
seed - dados pra popular db 
mock - dados ou funcoes de mentirinha para qualquer parte dos tests, nao so db.
duble - funcao que executada no lugar da funcao real

TODO:
Pesquisar:
DTO 
Modelo de dominio - https://guia.dev/pt/pillars/business/domain-model.html

Entity: modelos da aplicação no banco
Repository: camada de comunicação com o banco
Service: regras de negócio
Controller: rotas, respostas e requisições com o cliente

State flow

inicializar components com init
pegar o componenent de http para fazer o teste do pedestal (req)
criar flow de requisicoes com state e fazendo a requisicao

