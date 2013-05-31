from flask import jsonify

from webapp import app

import trimet_getters

@app.route("/")
def hello():
	return "Hello World!"

@app.route("/arrival/<int:stop_id>")
def feed_arrival(stop_id):
	return jsonify(trimet_getters.get_arrival(stop_id))
