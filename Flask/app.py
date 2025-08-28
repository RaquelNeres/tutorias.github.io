from flask import Flask, render_template   # procura por um template, .html, na pasta templates

def create_app():
    app = Flask(__name__)  # represento o arquivo atual

    @app.route('/') # pagina padrão
    def home():
        return render_template("home.html")  # procura por um template na pasta templates

    @app.route("/about")
    def about():
        return render_template("about.html")
    
    return app