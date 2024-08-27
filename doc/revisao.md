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


- get - tem limites menores do que consegue enviar pelos params
inclusive por limites impostos pelo servidor 
    tbm questoes de securanca, mesmo enconded

 headers: access-control-allow-origin:(verificar quem está fazendo a solicitação para aceitar ou nao)

 - path-params = Identifica o recurso especificado do dominio, assim como no nosso diretorio local 
 (/Users/carolina.silva/dev/nu)
 Usamos por convenção um placeholder para que seja seja preenchido dinamicamente 
 /Users/:user-login/dev/nu
  www.xxx.com/users/:user-id/permissions 
  www.xxx.com/users/:user-id/permissions/:id-permission 
  www.xxx.com/doc/debug/get/works (navegação) OU www.xxx.com/doc/debug/get?status=works(filtro)
 ----

 - query-params = filtrar informações

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
são nossas dependencias

defrecord 
maneiras

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

;;===================== PLAIN FLOW lib
;setup =  components
;http/endpoints = http.helpers - 
;https://github.com/nubank/state-flow?tab=readme-ov-file#writing-helpers
; precisei criar o helper que as docs ja tinham prontos - utilizando o servelet do jetty (http/service-fn) e passando para o funcao pedestal da funcao (test/response-for)
;;===================== NUBANK Testing routes with Servlet
;https://nubank.atlassian.net/wiki/spaces/ENGCHAP/pages/263675510883/Integration+test#Testing-routes-with-Servlet

;defflow é uma macro -  usa o componente para iniciliaza o servidor e usa datomic in mem
;setup = sim  -  teardown = (nenhum)?


;;===================== CALCULUS-101 repo
; https://github.com/nubank/calculus-101


