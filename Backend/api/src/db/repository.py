quotes = [
    {
        "id": 6,
        "quote": "Your mind will lead you to glorious places, but you have to let it.",
        "author": "unkown",
        "isRegret": False,
        "hits": 12345,
    },
    {
        "id": 7,
        "quote": "To offer respect is free, you cannot know the future of the person you are speaking to right now and how" +\
            " it will affect yours.",
        "author": "unkown",
        "isRegret": False,
        "hits": 4123
    },
    {
        "id": 8,
        "quote": "Falling in love is like playing russian rullete with your heart, be very very carefull.",
        "author": "Mircea Tanase",
        "isRegret": True,
        "hits": 5123
    },
]
next_id = 9

# Todo change to db call
def fetchQuotes():
    print(quotes)
    return quotes

def addQuote(quote):
    global next_id
    quote["id"] = next_id
    next_id += 1
    print(quotes)
    quotes.append(quote)