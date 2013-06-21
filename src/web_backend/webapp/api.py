# Libraries
from flask import jsonify, request

# Local
from webapp import app
import trimet_getters
from reach import Reach

@app.route("/")
def hello():
    return "Hello World!"

@app.route("/arrival/<int:stop_id>")
def feed_arrival(stop_id):
    return jsonify(trimet_getters.get_arrival(stop_id))

#<hostname>/json?lat=1.111111&long=2.222222&time=200&day=03&month=11&year=2013&mode_code=0&constraint=15
@app.route("/json")
def feed_reach():
    # TODO: add logging
    try:
        reach = Reach(request.args)
        reach.calculate_simple()
        return jsonify(reach.get_result())
    except:
        return jsonify({"success":False})
