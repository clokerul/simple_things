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
next_id = 4

# Todo change to db call
def fetchQuotes():
    return quotes

def addQuote(quote):
    quote["id"] = next_id
    next_id += 1
    quotes.append(quote)