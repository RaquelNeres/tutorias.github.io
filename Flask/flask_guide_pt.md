# Guia Completo do Flask em Português

## Índice
1. [Introdução ao Flask](#introdução-ao-flask)
2. [O que é HTTP e como o Flask se encaixa](#o-que-é-http-e-como-o-flask-se-encaixa)
3. [Instalação e Configuração Inicial](#instalação-e-configuração-inicial)
4. [Primeira Aplicação Flask](#primeira-aplicação-flask)
5. [Rotas e URLs](#rotas-e-urls)
6. [Templates e HTML](#templates-e-html)
7. [Arquivos Estáticos (CSS, JS, Imagens)](#arquivos-estáticos)
8. [Formulários e Requisições HTTP](#formulários-e-requisições-http)
9. [Banco de Dados com Flask](#banco-de-dados-com-flask)
10. [Ambientes Virtuais](#ambientes-virtuais)
11. [WSGI: Web Server Gateway Interface](#wsgi-web-server-gateway-interface)
12. [Deploy na Nuvem](#deploy-na-nuvem)
13. [Boas Práticas e Estrutura de Projeto](#boas-práticas)

## Introdução ao Flask

Flask é um microframework web para Python, criado por Armin Ronacher. É chamado de "micro" porque não requer ferramentas ou bibliotecas específicas - mantém o núcleo simples, mas extensível.

### Características Principais:
- **Simplicidade**: Fácil de aprender e usar
- **Flexibilidade**: Não impõe estruturas rígidas
- **Extensibilidade**: Grande ecossistema de extensões
- **Compatibilidade**: Baseado em WSGI
- **Minimalismo**: Core pequeno com funcionalidades essenciais

## O que é HTTP e como o Flask se encaixa

HTTP é o protocolo dos sites da web. A internet o utiliza para interagir com computadores e servidores, para se comunicar com eles.

### Ciclo HTTP Básico:
1. **Requisição**: Cliente (navegador) envia uma solicitação HTTP para o servidor
2. **Processamento**: Servidor processa a requisição
3. **Resposta**: Servidor envia resposta HTTP de volta
4. **Renderização**: Navegador exibe o conteúdo recebido

### Papel do Flask:
Escreveremos código que ficará responsável pelo processamento do lado do servidor. Nosso código receberá solicitações. Ele descobrirá com o que essas solicitações estão lidando e o que estão pedindo. Ele também descobrirá qual resposta deve ser enviada ao usuário.

## Instalação e Configuração Inicial

### Instalação via pip:
```bash
pip install Flask
```

### Verificação da Instalação:
```python
import flask
print(flask.__version__)
```

## Primeira Aplicação Flask

### Exemplo Básico (app.py):
```python
from flask import Flask

# Criar instância da aplicação
app = Flask(__name__)

# Definir rota principal
@app.route("/")
def home():
    return "Olá, Mundo! Esta é minha primeira aplicação Flask!"

# Executar aplicação
if __name__ == "__main__":
    app.run(debug=True)
```

### Executando a Aplicação:
```bash
python app.py
```

### Explicação do Código:
- **Linha 1**: aqui, importamos o módulo do Flask e criamos um servidor da web do Flask a partir do módulo do Flask.
- **Linha 4**: __name__ representa o arquivo atual. Neste caso, estamos falando de main.py. Esse arquivo atual representará minha aplicação da web.
- **Linha 6**: representa a página padrão. Por exemplo, se eu vou a um site da web, como o "google.com/", sem nada após a barra. Essa será a página padrão do Google.
- **Linha 11**: debug=True permite que erros do Python apareçam na página da web. Isso nos ajudará a identificar os erros.

## Rotas e URLs

### Rotas Básicas:
```python
from flask import Flask

app = Flask(__name__)

@app.route("/")
def home():
    return "Página Inicial"

@app.route("/sobre")
def sobre():
    return "Página Sobre"

@app.route("/contato")
def contato():
    return "Página de Contato"
```

### Rotas com Parâmetros:
```python
@app.route("/usuario/<nome>")
def usuario(nome):
    return f"Olá, {nome}!"

@app.route("/post/<int:id>")
def mostrar_post(id):
    return f"Post ID: {id}"

@app.route("/preco/<float:valor>")
def mostrar_preco(valor):
    return f"Preço: R$ {valor:.2f}"
```

### Métodos HTTP:
```python
from flask import request

@app.route("/login", methods=["GET", "POST"])
def login():
    if request.method == "POST":
        return "Processando login..."
    else:
        return "Formulário de login"

@app.route("/api/dados", methods=["GET"])
def obter_dados():
    return {"dados": "informações importantes"}

@app.route("/api/criar", methods=["POST"])
def criar_item():
    return {"status": "criado"}
```

### URL Building:
```python
from flask import url_for

@app.route("/perfil/<nome>")
def perfil(nome):
    return f"Perfil de {nome}"

@app.route("/")
def home():
    # Gera URL para a função perfil
    url_perfil = url_for('perfil', nome='joao')
    return f'<a href="{url_perfil}">Ver perfil de João</a>'
```

## Templates e HTML

### Estrutura de Diretórios:
```
projeto/
├── app.py
├── templates/
│   ├── base.html
│   ├── home.html
│   └── sobre.html
└── static/
    ├── css/
    ├── js/
    └── images/
```

### Template Base (templates/base.html):
```html
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>{% block title %}Minha Aplicação{% endblock %}</title>
    <link rel="stylesheet" href="{{ url_for('static', filename='css/style.css') }}">
</head>
<body>
    <nav>
        <ul>
            <li><a href="{{ url_for('home') }}">Home</a></li>
            <li><a href="{{ url_for('sobre') }}">Sobre</a></li>
            <li><a href="{{ url_for('contato') }}">Contato</a></li>
        </ul>
    </nav>
    
    <main>
        {% block content %}{% endblock %}
    </main>
    
    <footer>
        <p>&copy; 2024 Minha Aplicação Flask</p>
    </footer>
</body>
</html>
```

### Template Filho (templates/home.html):
```html
{% extends "base.html" %}

{% block title %}Home - Minha Aplicação{% endblock %}

{% block content %}
<h1>Bem-vindo à Minha Aplicação Flask!</h1>
<p>Esta é a página inicial da nossa aplicação.</p>

{% if usuario %}
    <p>Olá, {{ usuario.nome }}!</p>
{% else %}
    <p>Por favor, faça login.</p>
{% endif %}

<ul>
{% for item in lista_itens %}
    <li>{{ item }}</li>
{% endfor %}
</ul>
{% endblock %}
```

### Usando Templates no Python:
```python
from flask import Flask, render_template

app = Flask(__name__)

@app.route("/")
def home():
    usuario = {"nome": "Maria", "email": "maria@email.com"}
    itens = ["Item 1", "Item 2", "Item 3"]
    return render_template("home.html", usuario=usuario, lista_itens=itens)

@app.route("/sobre")
def sobre():
    return render_template("sobre.html")
```

### Filtros Jinja2:
```html
<!-- Em qualquer template -->
<p>Nome em maiúsculas: {{ usuario.nome|upper }}</p>
<p>Data formatada: {{ data|strftime('%d/%m/%Y') }}</p>
<p>Preço: {{ preco|round(2) }}</p>
<p>Lista com vírgulas: {{ lista|join(', ') }}</p>

<!-- Condicionais avançadas -->
{% if usuarios|length > 0 %}
    <p>Temos {{ usuarios|length }} usuários cadastrados.</p>
{% endif %}
```

## Arquivos Estáticos

### Estrutura CSS (static/css/style.css):
```css
/* Reset básico */
* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}

/* Navegação */
nav ul {
    list-style: none;
    display: flex;
    background-color: #333;
}

nav ul li {
    margin: 0;
}

nav ul li a {
    display: block;
    padding: 15px 20px;
    color: white;
    text-decoration: none;
}

nav ul li a:hover {
    background-color: #555;
}

/* Conteúdo principal */
main {
    padding: 20px;
    max-width: 1200px;
    margin: 0 auto;
}

/* Footer */
footer {
    background-color: #333;
    color: white;
    text-align: center;
    padding: 20px;
    margin-top: 50px;
}
```

### Usando Arquivos Estáticos:
```html
<!-- No template HTML -->
<link rel="stylesheet" href="{{ url_for('static', filename='css/style.css') }}">
<script src="{{ url_for('static', filename='js/script.js') }}"></script>
<img src="{{ url_for('static', filename='images/logo.png') }}" alt="Logo">
```

### Servindo Arquivos Estáticos no Python:
```python
from flask import send_from_directory

@app.route('/downloads/<filename>')
def download_arquivo(filename):
    return send_from_directory('uploads', filename)
```

## Formulários e Requisições HTTP

### Formulário HTML:
```html
<!-- templates/contato.html -->
{% extends "base.html" %}

{% block content %}
<h1>Entre em Contato</h1>

<form method="POST" action="{{ url_for('processar_contato') }}">
    <div>
        <label for="nome">Nome:</label>
        <input type="text" id="nome" name="nome" required>
    </div>
    
    <div>
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required>
    </div>
    
    <div>
        <label for="mensagem">Mensagem:</label>
        <textarea id="mensagem" name="mensagem" rows="5" required></textarea>
    </div>
    
    <div>
        <button type="submit">Enviar</button>
    </div>
</form>
{% endblock %}
```

### Processando Formulários:
```python
from flask import Flask, render_template, request, redirect, url_for, flash

app = Flask(__name__)
app.secret_key = 'sua_chave_secreta_aqui'

@app.route("/contato")
def contato():
    return render_template("contato.html")

@app.route("/processar-contato", methods=["POST"])
def processar_contato():
    nome = request.form.get("nome")
    email = request.form.get("email")
    mensagem = request.form.get("mensagem")
    
    # Validações
    if not nome or not email or not mensagem:
        flash("Todos os campos são obrigatórios!", "erro")
        return redirect(url_for("contato"))
    
    # Processar dados (salvar no banco, enviar email, etc.)
    print(f"Nome: {nome}")
    print(f"Email: {email}")
    print(f"Mensagem: {mensagem}")
    
    flash("Mensagem enviada com sucesso!", "sucesso")
    return redirect(url_for("contato"))
```

### Upload de Arquivos:
```python
from werkzeug.utils import secure_filename
import os

UPLOAD_FOLDER = 'uploads'
ALLOWED_EXTENSIONS = {'txt', 'pdf', 'png', 'jpg', 'jpeg', 'gif'}

app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER

def arquivo_permitido(filename):
    return '.' in filename and \
           filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS

@app.route('/upload', methods=['GET', 'POST'])
def upload_arquivo():
    if request.method == 'POST':
        if 'arquivo' not in request.files:
            flash('Nenhum arquivo selecionado')
            return redirect(request.url)
        
        arquivo = request.files['arquivo']
        
        if arquivo.filename == '':
            flash('Nenhum arquivo selecionado')
            return redirect(request.url)
        
        if arquivo and arquivo_permitido(arquivo.filename):
            filename = secure_filename(arquivo.filename)
            arquivo.save(os.path.join(app.config['UPLOAD_FOLDER'], filename))
            flash('Arquivo enviado com sucesso!')
            return redirect(url_for('upload_arquivo'))
    
    return render_template('upload.html')
```

## Banco de Dados com Flask

### Usando SQLite com Flask:
```python
import sqlite3
from flask import Flask, g

app = Flask(__name__)
DATABASE = 'database.db'

def get_db():
    if 'db' not in g:
        g.db = sqlite3.connect(DATABASE)
        g.db.row_factory = sqlite3.Row
    return g.db

def close_db(error):
    db = g.pop('db', None)
    if db is not None:
        db.close()

def init_db():
    with app.app_context():
        db = get_db()
        db.executescript('''
            CREATE TABLE IF NOT EXISTS usuarios (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nome TEXT NOT NULL,
                email TEXT UNIQUE NOT NULL,
                senha TEXT NOT NULL,
                criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            );
            
            CREATE TABLE IF NOT EXISTS posts (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                titulo TEXT NOT NULL,
                conteudo TEXT NOT NULL,
                usuario_id INTEGER,
                criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (usuario_id) REFERENCES usuarios (id)
            );
        ''')
        db.commit()

@app.teardown_appcontext
def close_db(error):
    close_db(error)

# Rotas para CRUD
@app.route('/usuarios')
def listar_usuarios():
    db = get_db()
    usuarios = db.execute('SELECT * FROM usuarios').fetchall()
    return render_template('usuarios.html', usuarios=usuarios)

@app.route('/usuario/<int:id>')
def ver_usuario(id):
    db = get_db()
    usuario = db.execute('SELECT * FROM usuarios WHERE id = ?', (id,)).fetchone()
    if usuario is None:
        return "Usuário não encontrado", 404
    return render_template('usuario.html', usuario=usuario)
```

### Usando Flask-SQLAlchemy (ORM):
```python
from flask import Flask
from flask_sqlalchemy import SQLAlchemy
from datetime import datetime

app = Flask(__name__)
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///app.db'
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False
db = SQLAlchemy(app)

# Modelos
class Usuario(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    nome = db.Column(db.String(100), nullable=False)
    email = db.Column(db.String(120), unique=True, nullable=False)
    senha = db.Column(db.String(200), nullable=False)
    criado_em = db.Column(db.DateTime, default=datetime.utcnow)
    posts = db.relationship('Post', backref='autor', lazy=True)

class Post(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    titulo = db.Column(db.String(200), nullable=False)
    conteudo = db.Column(db.Text, nullable=False)
    criado_em = db.Column(db.DateTime, default=datetime.utcnow)
    usuario_id = db.Column(db.Integer, db.ForeignKey('usuario.id'), nullable=False)

# Criar tabelas
@app.before_first_request
def criar_tabelas():
    db.create_all()

# Rotas CRUD
@app.route('/api/usuarios', methods=['GET'])
def api_usuarios():
    usuarios = Usuario.query.all()
    return {
        'usuarios': [
            {
                'id': u.id,
                'nome': u.nome,
                'email': u.email,
                'criado_em': u.criado_em.isoformat()
            } for u in usuarios
        ]
    }

@app.route('/api/usuario', methods=['POST'])
def criar_usuario():
    dados = request.json
    
    usuario = Usuario(
        nome=dados['nome'],
        email=dados['email'],
        senha=dados['senha']  # Em produção, use hash da senha!
    )
    
    db.session.add(usuario)
    db.session.commit()
    
    return {'status': 'sucesso', 'id': usuario.id}
```

## Ambientes Virtuais

Usamos o virtualenv para criar um ambiente isolado para seu projeto em Python. Isso significa que cada projeto pode ter suas próprias dependências, independentemente de quais outras dependências os outros projetos possam ter.

### Criando Ambiente Virtual:
```bash
# Instalar virtualenv
pip install virtualenv

# Criar ambiente virtual
virtualenv venv
# ou usando python -m venv
python -m venv venv

# Ativar no Windows
venv\Scripts\activate

# Ativar no Linux/Mac
source venv/bin/activate

# Instalar Flask no ambiente virtual
pip install flask

# Desativar ambiente
deactivate
```

### Gerenciando Dependências:
```bash
# Gerar arquivo de requirements
pip freeze > requirements.txt

# Instalar dependências de outro projeto
pip install -r requirements.txt
```

### Exemplo de requirements.txt:
```
Flask==2.3.3
Flask-SQLAlchemy==3.0.5
Werkzeug==2.3.7
Jinja2==3.1.2
```

## WSGI: Web Server Gateway Interface

WSGI (Web Server Gateway Interface) é um padrão que define como servidores web se comunicam com aplicações web Python. Flask é uma aplicação WSGI.

### Como o WSGI Funciona:
1. **Servidor Web** (Apache, Nginx) recebe requisição HTTP
2. **Servidor WSGI** (Gunicorn, uWSGI) processa a requisição
3. **Aplicação Flask** gera resposta
4. Resposta volta pelo mesmo caminho

### Estrutura WSGI Básica:
```python
def application(environ, start_response):
    """
    environ: dicionário com informações da requisição
    start_response: função para iniciar resposta
    """
    status = '200 OK'
    headers = [('Content-Type', 'text/html')]
    start_response(status, headers)
    return [b'Hello World!']

# Flask internamente implementa essa interface
```

### Flask como Aplicação WSGI:
```python
from flask import Flask

app = Flask(__name__)

@app.route('/')
def home():
    return 'Hello Flask!'

# app é uma aplicação WSGI válida
# Pode ser usada com qualquer servidor WSGI
```

### Configuração para Produção:
```python
# wsgi.py - arquivo para deploy
from app import app

if __name__ == "__main__":
    app.run()

# Para usar com Gunicorn:
# gunicorn --bind 0.0.0.0:8000 wsgi:app
```

### Middleware WSGI:
```python
class LoggingMiddleware:
    def __init__(self, app):
        self.app = app
    
    def __call__(self, environ, start_response):
        print(f"Requisição para: {environ['PATH_INFO']}")
        return self.app(environ, start_response)

# Aplicar middleware
app.wsgi_app = LoggingMiddleware(app.wsgi_app)
```

## Deploy na Nuvem

### Preparando para Deploy:
```python
# app.py - versão para produção
import os
from flask import Flask

app = Flask(__name__)
app.config['SECRET_KEY'] = os.environ.get('SECRET_KEY') or 'dev-key'

@app.route('/')
def home():
    return 'Aplicação em Produção!'

if __name__ == '__main__':
    port = int(os.environ.get('PORT', 5000))
    app.run(host='0.0.0.0', port=port)
```

### Arquivos de Configuração:

#### requirements.txt:
```
Flask==2.3.3
gunicorn==21.2.0
```

#### Procfile (para Heroku):
```
web: gunicorn app:app
```

#### app.yaml (para Google Cloud):
```yaml
runtime: python39

handlers:
- url: /static
  static_dir: static

- url: /.*
  script: auto

env_variables:
  SECRET_KEY: "sua-chave-secreta-aqui"
```

### Deploy no Heroku:
```bash
# Instalar Heroku CLI e fazer login
heroku login

# Criar aplicação
heroku create nome-da-sua-app

# Configurar variáveis de ambiente
heroku config:set SECRET_KEY=sua-chave-secreta

# Deploy
git add .
git commit -m "Deploy inicial"
git push heroku main
```

### Deploy no Google Cloud:
```bash
# Instalar Google Cloud SDK
# Fazer login: gcloud auth login

# Deploy
gcloud app deploy

# Ver aplicação
gcloud app browse
```

## Boas Práticas e Estrutura de Projeto

### Estrutura de Projeto Recomendada:
```
meu_projeto/
├── app/
│   ├── __init__.py
│   ├── routes/
│   │   ├── __init__.py
│   │   ├── main.py
│   │   └── auth.py
│   ├── models/
│   │   ├── __init__.py
│   │   └── user.py
│   ├── templates/
│   │   ├── base.html
│   │   └── index.html
│   └── static/
│       ├── css/
│       ├── js/
│       └── images/
├── tests/
├── venv/
├── config.py
├── requirements.txt
├── run.py
└── README.md
```

### Configuração Modular (config.py):
```python
import os

class Config:
    SECRET_KEY = os.environ.get('SECRET_KEY') or 'dev-secret-key'
    SQLALCHEMY_TRACK_MODIFICATIONS = False

class DevelopmentConfig(Config):
    DEBUG = True
    SQLALCHEMY_DATABASE_URI = os.environ.get('DEV_DATABASE_URL') or 'sqlite:///dev.db'

class ProductionConfig(Config):
    DEBUG = False
    SQLALCHEMY_DATABASE_URI = os.environ.get('DATABASE_URL') or 'sqlite:///prod.db'

config = {
    'development': DevelopmentConfig,
    'production': ProductionConfig,
    'default': DevelopmentConfig
}
```

### Factory Pattern (app/__init__.py):
```python
from flask import Flask
from flask_sqlalchemy import SQLAlchemy
from config import config

db = SQLAlchemy()

def create_app(config_name='default'):
    app = Flask(__name__)
    app.config.from_object(config[config_name])
    
    # Inicializar extensões
    db.init_app(app)
    
    # Registrar Blueprints
    from app.routes.main import main_bp
    from app.routes.auth import auth_bp
    
    app.register_blueprint(main_bp)
    app.register_blueprint(auth_bp, url_prefix='/auth')
    
    return app
```

### Usando Blueprints (app/routes/main.py):
```python
from flask import Blueprint, render_template

main_bp = Blueprint('main', __name__)

@main_bp.route('/')
def home():
    return render_template('index.html')

@main_bp.route('/sobre')
def sobre():
    return render_template('sobre.html')
```

### Arquivo Principal (run.py):
```python
from app import create_app
import os

app = create_app(os.environ.get('FLASK_ENV') or 'default')

if __name__ == '__main__':
    app.run(debug=True)
```

### Testes Unitários (tests/test_basic.py):
```python
import unittest
from app import create_app

class BasicTestCase(unittest.TestCase):
    def setUp(self):
        self.app = create_app('testing')
        self.client = self.app.test_client()
        
    def test_home_page(self):
        response = self.client.get('/')
        self.assertEqual(response.status_code, 200)
        
    def test_about_page(self):
        response = self.client.get('/sobre')
        self.assertEqual(response.status_code, 200)

if __name__ == '__main__':
    unittest.main()
```

### Executando Testes:
```bash
python -m pytest tests/
# ou
python -m unittest discover tests/
```

## Recursos Adicionais

### Extensões Populares do Flask:
- **Flask-SQLAlchemy**: ORM para banco de dados
- **Flask-Login**: Gerenciamento de autenticação
- **Flask-WTF**: Formulários e CSRF protection
- **Flask-Mail**: Envio de emails
- **Flask-Admin**: Interface administrativa
- **Flask-RESTful**: APIs REST
- **Flask-JWT-Extended**: Autenticação JWT

### Links Úteis:
- [Documentação Oficial do Flask](https://flask.palletsprojects.com/)
- [Flask Mega Tutorial](https://blog.miguelgrinberg.com/post/the-flask-mega-tutorial-part-i-hello-world)
- [Awesome Flask](https://github.com/humiaozuzu/awesome-flask)
- [Flask Extensions](https://flask.palletsprojects.com/en/2.3.x/extensions/)

### Comandos Úteis:
```bash
# Executar aplicação em modo debug
flask --app app.py --debug run

# Executar em porta específica
flask --app app.py run --port 8000

# Executar acessível externamente
flask --app app.py run --host 0.0.0.0

# Shell interativo com contexto da aplicação
flask --app app.py shell

# Executar comandos customizados
flask --app app.py init-db
```

Este guia fornece uma base sólida para começar a trabalhar com Flask, desde conceitos básicos até práticas avançadas para aplicações em produção. Lembre-se sempre de consultar a documentação oficial para informações mais detalhadas e atualizadas.