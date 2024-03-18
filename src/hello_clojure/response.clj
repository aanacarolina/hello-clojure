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

