(ns hello-clojure.req-with-components)

(def db-interceptor-request
  {:protocol "HTTP/1.1",
   :async-supported? true,
   :remote-addr "127.0.0.1",
   :servlet-response #object[org.eclipse.jetty.server.Response 0x670494af "HTTP/1.1 200 \nDate: Fri, 12 Apr 2024 13:49:43 GMT\r\n\r\n"],
   :servlet #object[io.pedestal.http.servlet.FnServlet 0x393f3ac5 "io.pedestal.http.servlet.FnServlet@393f3ac5"],
   :headers {"sec-fetch-site" "none", "sec-ch-ua-mobile" "?0", "host" "localhost:7171", "user-agent" "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36", "sec-fetch-user" "?1", "sec-ch-ua" "\"Google Chrome\";v=\"123\", \"Not:A-Brand\";v=\"8\", \"Chromium\";v=\"123\"", "sec-ch-ua-platform" "\"macOS\"", "connection" "keep-alive", "upgrade-insecure-requests" "1", "accept" "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7", "accept-language" "en-US,en;q=0.9,pt;q=0.8,it;q=0.7", "sec-fetch-dest" "document", "accept-encoding" "gzip, deflate, br, zstd", "sec-fetch-mode" "navigate"},
   :server-port 7171, :servlet-request #object[org.eclipse.jetty.server.Request 0x633b1c10 "Request(GET //localhost:7171/users)@633b1c10"],
   :components {:db #components.db.AtomDatabase
                     {:atom-database #atom
                                      [{#uuid "11e735a5-feaa-458a-8c62-449ba5aa60dc" {:name "Chaves", :surname "S.", :age 8,
                                                                                      :accounts [{:account-uuid #uuid "f061f807-fd80-4536-9242-7d084a6c2b81", :status "Active", :type "checking", :amount 0}
                                                                                                 {:account-uuid #uuid "0f17da9d-9a0c-4332-9b7f-9a7f965f0299", :status "Active", :type "savings", :amount 0}]},
                                        #uuid "e291f340-7e1b-4f78-9cd6-22afeb04eebc" {:name "Quico", :surname "S.", :age 7},
                                        #uuid "4b96037f-f9a3-490f-99f3-5f9b32006edc" {:name "Chiquinha", :surname "S.", :age 7},
                                        #uuid "fb3281be-11c4-4907-83d0-722df301032f" {:name "Seu Madruga", :surname "S.", :age 42},
                                        #uuid "457d6d45-0d79-45c8-a743-892170ce356d" {:name "Dona Florinda", :surname "S.", :age 35}}
                                       0x50300402]}},
   :path-info "/users",
   :url-for #delay[{:status :pending, :val nil} 0x2206162b],
   :uri "/users",
   :server-name "localhost",
   :query-string nil,
   :path-params {},
   :body #object[org.eclipse.jetty.server.HttpInputOverHTTP 0x280e17b3 "HttpInputOverHTTP@280e17b3[c=0,q=0,[0]=null,s=STREAM]"],
   :scheme :http,
   :request-method :get,
   :context-path ""})



List of all users: 
{#uuid "11e735a5-feaa-458a-8c62-449ba5aa60dc" {:name "Chaves", :surname "S.", :age 8, :accounts [{:account-uuid #uuid "f061f807-fd80-4536-9242-7d084a6c2b81", :status "Active", :type "checking", :amount 0} {:account-uuid #uuid "0f17da9d-9a0c-4332-9b7f-9a7f965f0299", :status "Active", :type "savings", :amount 0}]}, #uuid "e291f340-7e1b-4f78-9cd6-22afeb04eebc" {:name "Quico", :surname "S.", :age 7}, #uuid "4b96037f-f9a3-490f-99f3-5f9b32006edc" {:name "Chiquinha", :surname "S.", :age 7}, #uuid "fb3281be-11c4-4907-83d0-722df301032f" {:name "Seu Madruga", :surname "S.", :age 42}, #uuid "457d6d45-0d79-45c8-a743-892170ce356d" {:name "Dona Florinda", :surname "S.", :age 35}}

  {:protocol HTTP/1.1, :async-supported? true, :remote-addr 127.0.0.1, :servlet-response #object[org.eclipse.jetty.server.Response 0xff68fc HTTP/1.1 200 
 Date: Wed, 17 Apr 2024 12:49:02 GMT
 
], :servlet #object[io.pedestal.http.servlet.FnServlet 0x3c7a3382 io.pedestal.http.servlet.FnServlet@3c7a3382], :headers {accept */*, user-agent PostmanRuntime/7.37.3, connection keep-alive, postman-token 57412fc2-a3ba-4600-81be-d5bc2b078a3a, host localhost:7171, accept-encoding gzip, deflate, br, content-length 66, content-type text/plain}, :server-port 7171, :servlet-request #object[org.eclipse.jetty.server.Request 0x51859c8 Request(POST //localhost:7171/users)@51859c8], :components {:db #components.db.AtomDatabase{:atom-database #atom[{#uuid "11e735a5-feaa-458a-8c62-449ba5aa60dc" {:name Chaves, :surname S., :age 8, :accounts [{:account-uuid #uuid "f061f807-fd80-4536-9242-7d084a6c2b81", :status Active, :type checking, :amount 0} {:account-uuid #uuid "0f17da9d-9a0c-4332-9b7f-9a7f965f0299", :status Active, :type savings, :amount 0}]}, #uuid "e291f340-7e1b-4f78-9cd6-22afeb04eebc" {:name Quico, :surname S., :age 7}, #uuid "4b96037f-f9a3-490f-99f3-5f9b32006edc" {:name Chiquinha, :surname S., :age 7}, #uuid "fb3281be-11c4-4907-83d0-722df301032f" {:name Seu Madruga, :surname S., :age 42}, #uuid "457d6d45-0d79-45c8-a743-892170ce356d" {:name Dona Florinda, :surname S., :age 35}} 0x220bde24]}}, :content-length 66, :content-type text/plain, :path-info /users, :url-for #delay[{:status :pending, :val nil} 0x6f509e72], :uri /users, :server-name localhost, :query-string nil, :path-params {}, :body #object[org.eclipse.jetty.server.HttpInputOverHTTP 0x34eb996 HttpInputOverHTTP@34eb996[c=0,q=0,[0]=null,s=STREAM]], :scheme :http, :request-method :post, :context-path } 😀😀😀😀😀


✅✅✅✅✅✅✅✅✅✅✅✅ 
{:json-params {:name fbgfgfg, :surname UMM, :age 5}, :protocol HTTP/1.1, :async-supported? true, :remote-addr 127.0.0.1, 
 :servlet-response #object[org.eclipse.jetty.server.Response 0x6699ed0b HTTP/1.1 200 
 Date: Fri, 19 Apr 2024 19:42:37 GMT], 
 :servlet #object[io.pedestal.http.servlet.FnServlet 0x626f8bf4 io.pedestal.http.servlet.FnServlet@626f8bf4], 
 :headers {accept */*, user-agent PostmanRuntime/7.37.3, connection keep-alive, postman-token d38fc4cf-fca4-49a8-b600-82846000e995, host localhost:7171, accept-encoding gzip, deflate, br, content-length 62, content-type application/json}, 
 :server-port 7171, 
 :servlet-request #object[org.eclipse.jetty.server.Request 0x385fdd0c Request(POST //localhost:7171/users)@385fdd0c], 
 :components {:db #components.db.AtomDatabase{:atom-database #atom[{#uuid "11e735a5-feaa-458a-8c62-449ba5aa60dc" {:name Chaves, :surname S., :age 8, :accounts [{:account-uuid #uuid "f061f807-fd80-4536-9242-7d084a6c2b81", :status Active, :type checking, :amount 0} {:account-uuid #uuid "0f17da9d-9a0c-4332-9b7f-9a7f965f0299", :status Active, :type savings, :amount 0}]}, #uuid "e291f340-7e1b-4f78-9cd6-22afeb04eebc" {:name Quico, :surname S., :age 7}, #uuid "4b96037f-f9a3-490f-99f3-5f9b32006edc" {:name Chiquinha, :surname S., :age 7}, #uuid "fb3281be-11c4-4907-83d0-722df301032f" {:name Seu Madruga, :surname S., :age 42}, #uuid "457d6d45-0d79-45c8-a743-892170ce356d" {:name Dona Florinda, :surname S., :age 35}, #uuid "7c82a5e7-56f3-49f1-8342-18862e05b2f9" {:name fbgfgfg, :surname UMM, :age 5}} 0x5856ccfd]}}, 
 :content-length 62, 
 :content-type application/json,
 :path-info /users,
 :character-encoding UTF-8, 
 :url-for #delay[{:status :pending, :val nil} 0xfe44c28],
 :uri /users,
 :server-name localhost, 
 :query-string nil, 
 :path-params {}, 
 :body #object[org.eclipse.jetty.server.HttpInputOverHTTP 0x29fee77e HttpInputOverHTTP@29fee77e[c=62,q=0,[0]=null,s=STREAM]], 
 :scheme :http, 
 :request-method :post, 
 :context-path } ✅✅✅✅✅✅✅✅✅✅✅✅