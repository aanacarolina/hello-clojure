(ns hello-clojure.response)

❗❗❗❗❗❗❗❗❗❗❗❗❗❗❗❗❗❗ {:json-params ({:account-type checking, :deposit 0} {:account-type savings, :deposit 0}), 
                    
                    :account
                     {[({:account-type "checking", :deposit 0} {:account-type "savings", :deposit 0})]
                      ({:account-type "checking", :deposit 0} {:account-type "savings", :deposit 0})}},


{#uuid "457d6d45-0d79-45c8-a743-892170ce356d"
 [{:account-type "checking", :deposit 0} {:account-type "savings", :deposit 0}]}


---------------
;(test-request :get "/hello")
{:status 200,
 :body "Hello, stranger\n",
 :headers
 {"Strict-Transport-Security" "max-age=31536000; includeSubdomains",
  "X-Frame-Options" "DENY",
  "X-Content-Type-Options" "nosniff",
  "X-XSS-Protection" "1; mode=block",
  "X-Download-Options" "noopen",
  "X-Permitted-Cross-Domain-Policies" "none",
  "Content-Security-Policy"
  "object-src 'none'; script-src 'unsafe-inline' 'unsafe-eval' 'strict-dynamic' https: http:;",
  "Content-Type" "text/plain"}}

;(test-request-post :post "/users" {:json-params {:name "John", :surname "Doe", :age 30}} {"Content-Type" "text/plain"})
{:status 201,
 :body "new user created[#uuid \"d4e151c8-2b16-49e6-afe1-594383f6b9d0\" {:name nil, :surname nil, :age nil}]",
 :headers
 {"Strict-Transport-Security" "max-age=31536000; includeSubdomains",
  "X-Frame-Options" "DENY",
  "X-Content-Type-Options" "nosniff",
  "X-XSS-Protection" "1; mode=block",
  "X-Download-Options" "noopen",
  "X-Permitted-Cross-Domain-Policies" "none",
  "Content-Security-Policy"
  "object-src 'none'; script-src 'unsafe-inline' 'unsafe-eval' 'strict-dynamic' https: http:;",
  "Content-Type" "text/plain"}}


----
user-by-id
content-type application/edn
{:name "Chaves", :surname "S.", :age 8, :accounts [{:account-uuid #uuid "f061f807-fd80-4536-9242-7d084a6c2b81", :status "Active", :type "checking", :amount 0} {:account-uuid #uuid "0f17da9d-9a0c-4332-9b7f-9a7f965f0299", :status "Active", :type "savings", :amount 0}]}

user-by-id-json body

content-type application/json
{
    "name": "Chaves",
    "surname": "S.",
    "age": 8,
    "accounts": [
        {
            "account-uuid": "f061f807-fd80-4536-9242-7d084a6c2b81",
            "status": "Active",
            "type": "checking",
            "amount": 0
        },
        {
            "account-uuid": "0f17da9d-9a0c-4332-9b7f-9a7f965f0299",
            "status": "Active",
            "type": "savings",
            "amount": 0
        }
    ]
}
----

 ✅✅✅✅✅✅✅✅✅✅✅✅ 
{:json-params {:name Json, :surname Friday, :age 13}, :protocol HTTP/1.1, :async-supported? true, :remote-addr 127.0.0.1, :servlet-response #object[org.eclipse.jetty.server.Response 0x6769f204 HTTP/1.1 200 
 Date: Mon, 22 Apr 2024 11:51:05 GMT], :servlet #object[io.pedestal.http.servlet.FnServlet 0x5af489b4 io.pedestal.http.servlet.FnServlet@5af489b4], :headers {accept */*, user-agent PostmanRuntime/7.37.3, connection keep-alive, postman-token 9ccfd6cd-5c97-4b81-8945-056ba493e659, host localhost:7171, accept-encoding gzip, deflate, br, content-length 62, content-type application/json}, :server-port 7171, :servlet-request #object[org.eclipse.jetty.server.Request 0x3695e63c Request(POST //localhost:7171/users)@3695e63c], :components {:db #components.db.AtomDatabase{:atom-database #atom[{#uuid "457d6d45-0d79-45c8-a743-892170ce356d" {:name Dona Florinda, :surname S., :age 35}, #uuid "3f3955f0-7c3c-46e4-a489-dfbd38cf2cad" {:name Json, :surname Friday, :age 13}, #uuid "11e735a5-feaa-458a-8c62-449ba5aa60dc" {:name Chaves, :surname S., :age 8, :accounts [{:account-uuid #uuid "f061f807-fd80-4536-9242-7d084a6c2b81", :status Active, :type checking, :amount 0} {:account-uuid #uuid "0f17da9d-9a0c-4332-9b7f-9a7f965f0299", :status Active, :type savings, :amount 0}]}, #uuid "4ddb9ee3-00d3-4c7b-b3a8-cc212b3da5f1" {:name json , :surname UMM, :age 13}, #uuid "e291f340-7e1b-4f78-9cd6-22afeb04eebc" {:name Quico, :surname S., :age 7}, #uuid "c7c6ecd8-9430-4245-b750-01f14683019e" {:name nil, :surname nil, :age nil}, #uuid "4b96037f-f9a3-490f-99f3-5f9b32006edc" {:name Chiquinha, :surname S., :age 7}, #uuid "fb3281be-11c4-4907-83d0-722df301032f" {:name Seu Madruga, :surname S., :age 42}, #uuid "18f67225-d681-48a5-bf5c-776e30334131" {:name json , :surname UMM, :age 13}} 0x54e2d213]}}, :content-length 62, :content-type application/json, :path-info /users, :character-encoding UTF-8, :url-for #delay[{:status :pending, :val nil} 0x1dcb1a3c], :uri /users, :server-name localhost, :query-string nil, :path-params {}, :body #object[org.eclipse.jetty.server.HttpInputOverHTTP 0x351a3749 HttpInputOverHTTP@351a3749[c=62,q=0,[0]=null,s=STREAM]], :scheme :http, :request-method :post, :context-path } ✅✅✅✅✅✅✅✅✅✅✅✅