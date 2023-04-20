from flask import Flask, jsonify

import sys, os
sys.path.append(os.path.join(sys.path[0],'controllers'))
sys.path.append(os.path.join(sys.path[0],'db'))
from quotes import quotes_page

app = Flask(__name__)
app.register_blueprint(quotes_page)

def filterDataBy(filterFunction, data):
    return jsonify(list(filter(filterFunction, data)))

users = [
    {
        "id": 1,
        "username": "george",
        "password": "1",
        "cookie": "thisisacookie123"
    }
]

def fetchUsers():
    return users
### USERS
@app.route('/users')
def getUsers():
    return jsonify(users)

@app.route('/users/<username>')
def getUsersByusername(username):
    return filterDataBy(lambda user : user["username"] == username, fetchUsers())

### LOGIN
@app.route('/login/<username>/<password>')
def postLogin(username, password):
    user = list(filter(lambda user : user["username"] == username, fetchUsers()))[0]
    if (user["password"] == password):
        return f'{user["cookie"]}', 200
    else:
        return 'Denied', 401

@app.route('/')
def hello():
    return 'This is a test environment, thank you for visiting!.\n'