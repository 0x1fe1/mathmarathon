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

	handle("/", "views/index.html")
	handle("/mathmarathon", "views/mathmarathon.html")
	handle("/mathmarathon/settings", "views/m_settings.html")
	handle("/mathmarathon/rankings", "views/m_rankings.html")
	handle("/mathmarathon/game", "views/m_game.html")

	http.Handle("/signin", loggingMiddleware(http.HandlerFunc(handle_signin)))

	log.Print("Listening on :42069")
	log.Fatal(http.ListenAndServe(":42069", nil))
}

func handle(route, file string) {
	http.Handle(route, loggingMiddleware(
		http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
			tmpl := template.Must(template.ParseFiles(file))
			tmpl.Execute(w, nil)
		})))
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
