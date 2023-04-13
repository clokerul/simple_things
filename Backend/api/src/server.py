from flask import Flask, jsonify

app = Flask(__name__)

quotes = [
    {
        "id": 1,
        "quote": "Your mind will lead you to glorious places, but you have to let it.",
        "author": "unkown",
        "isRegret": False,
        "hits": 12345,
    },
    {
        "id": 2,
        "quote": "To offer respect is free, you cannot know the future of the person you are speaking to right now and how" +\
            " it will affect yours.",
        "author": "unkown",
        "isRegret": False,
        "hits": 4123
    },
    {
        "id": 3,
        "quote": "Falling in love is like playing russian rullete with your heart, be very very carefull.",
        "author": "Mircea Tanase",
        "isRegret": True,
        "hits": 5123
    },
]

users = [
    {
        "id": 1,
        "username": "george",
        "password": "1",
        "cookie": "thisisacookie123"
    }
]

# Todo change to db call
def fetchQuotes():
    return quotes

def fetchUsers():
    return users

def filterDataBy(filterFunction, data):
    return jsonify(list(filter(filterFunction, data)))

@app.route('/users')
def getUsers():
    return jsonify(users)

@app.route('/users/<username>')
def getUsersByusername(username):
    return filterDataBy(lambda user : user["username"] == username, fetchUsers())

@app.route('/login/<username>/<password>')
def postLogin(username, password):
    user = list(filter(lambda user : user["username"] == username, fetchUsers()))[0]
    if (user["password"] == password):
        return f'{user["cookie"]}', 200
    else:
        return 'Denied', 401
    
@app.route('/quotes')
def getQuotes():
    return jsonify(fetchQuotes())

@app.route('/pathways')
def getPathways():
    return filterDataBy(lambda quote : not quote["isRegret"], fetchQuotes())

@app.route('/regrets')
def getRegrets():
    return filterDataBy(lambda quote : quote["isRegret"], fetchQuotes())

@app.route('/')
def hello():
    return 'This is a test environment, thank you for visiting!.\n'