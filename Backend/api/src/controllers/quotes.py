from flask import Blueprint, request, jsonify
import sys, os
import repository as Repo

quotes_page = Blueprint('quotes_page', __name__, template_folder='templates')

def filterDataBy(filterFunction, data):
    return jsonify(list(filter(filterFunction, data)))

@quotes_page.route('/quotes', methods = ['GET'])
def getQuotes():
    return jsonify(Repo.fetchQuotes())

@quotes_page.route('/quotes/add', methods = ['POST'])
def postQuote():
    data = request.get_json()
    if ("quote" not in data or
        "isRegret" not in data):
        return "quote or regret missing", 400
    
    if ("author" not in data):
        data["author"] = "unknown"
    data["hits"] = 0
    return "ok", 200

@quotes_page.route('/quotes/pathways', methods = ['GET'])
def getPathways():
    return filterDataBy(lambda quote : not quote["isRegret"], Repo.fetchQuotes())

@quotes_page.route('/quotes/regrets', methods = ['GET'])
def getRegrets():
    return filterDataBy(lambda quote : quote["isRegret"], Repo.fetchQuotes())