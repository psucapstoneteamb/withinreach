# Libraries
from flask import jsonify, request, redirect, url_for

# Local
from webapp import app
import trimet_getters
from controller import Controller

@app.route("/")
def hello():
    print url_for('static', filename='note.txt')
    return redirect(url_for('static', filename='note.txt'))

@app.route("/arrival/<int:stop_id>")
def feed_arrival(stop_id):
    return jsonify(trimet_getters.get_arrival(stop_id))

#<hostname>/json?lat=1.111111&long=2.222222&time=200&day=03&month=11&year=2013&mode_code=0&constraint=15
@app.route("/json")
def feed_reach():
    # TODO: add logging
    try:
        c = Controller(request.args)
        c.calculate_simple()
        return jsonify(c.get_result())
    except:
        return jsonify({"success":False})

@app.route("/echo")
def echo():
    try:
        Controller(request.args)
        return jsonify({"correct":True, "echo":request.args})
    except:
        return jsonify({"correct":False, "echo":request.args})

