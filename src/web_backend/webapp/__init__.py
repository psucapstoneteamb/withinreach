# This module encapsulates everything related to selecting and loading configurations,
# creating the Flask app, and running it.
#
# Different run modes:
#   development - local, personal development server (default)
#   teamtest    - deploy on Heroku
#   production  - <not yet config>

import os
import logging
from flask import Flask
#from flask.ext.sqlalchemy import SQLAlchemy
from flask_debugtoolbar import DebugToolbarExtension

app = None
app_run_args = {}

#db = SQLAlchemy()

def create_app(run_mode="development"):
	# Create Flask app
	global app
	app = Flask("webapp")

	# Load base configuration
	app.config.from_object("webapp.base_settings")

	# Config default host and port
	global app_run_args
	app_run_args = {"port": 5000, "host": "127.0.0.1"}

	if run_mode == "development":
		app.config["DEBUG"] = True
		app.config["SECRET_KEY"] = "development_key"
		toolbar = DebugToolbarExtension(app)

	elif run_mode == "teamtest":
		app.config["DEBUG"] = False
		app_run_args["host"] = "0.0.0.0"
		# Get port number from Heroku environment variable
		app_run_args["port"] = int(os.environ.get("PORT", 5000))

	elif run_mode == "production":
		logging.error("Not yet config production run mode")

	else:
		logging.error("Did not recognize run mode '%s'" % run_mode)
		return

	# TODO Initialize the database

	# Initialize application
	# Import the api (views), to apply the decorators which use the global app object.
	import webapp.api

def run_app():
	app.run(**app_run_args)
