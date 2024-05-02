package main

import (
	"fmt"
	"html/template"
	"log"
	"net/http"
)

func loggingMiddleware(next http.Handler) http.Handler {
	return http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		log.Printf("Request received: %s %s", r.Method, r.URL.Path)
		next.ServeHTTP(w, r)
		log.Println("Request handled successfully")
	})
}

func main() {

	fs := http.FileServer(http.Dir("static"))
	http.Handle("/static/", http.StripPrefix("/static/", fs))

	http.Handle("/", loggingMiddleware(http.HandlerFunc(handle_index)))
	http.Handle("/mathmarathon", loggingMiddleware(http.HandlerFunc(handle_mathmarathon)))

	http.Handle("/signin", loggingMiddleware(http.HandlerFunc(handle_signin)))

	log.Print("Listening on :42069")
	log.Fatal(http.ListenAndServe(":42069", nil))
}

func handle_index(w http.ResponseWriter, r *http.Request) {
	tmpl := template.Must(template.ParseFiles("views/index.html"))
	tmpl.Execute(w, nil)
}

func handle_mathmarathon(w http.ResponseWriter, r *http.Request) {
	tmpl := template.Must(template.ParseFiles("views/mathmarathon.html"))
	tmpl.Execute(w, nil)
}

var account_state = "Sign In"

func handle_signin(w http.ResponseWriter, r *http.Request) {
	if account_state == "Sign In" {
		account_state = "Log Out"
	} else {
		account_state = "Sign In"
	}
	fmt.Fprintf(w, account_state)
}
