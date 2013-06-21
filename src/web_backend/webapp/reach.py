class Reach:
    """
    This class encapsulates request arguments from the Android app,
    validates inputs, processes inputs and creates results which is
    ready for jsonify.
    """
    def __init__(self, request_args):
        try:
            self.lat = request_args["lat"]
            self.lng = request_args["long"]
            self.time = request_args["time"]
            self.day = request_args["day"]
            self.month = request_args["month"]
            self.year = request_args["year"]
            self.mode_code = request_args["mode_code"]
            self.constraint = request_args["constraint"]
        except KeyError:
            raise Exception("Missing arguments")
        
        if not self.validate():
            raise Exception("Ill-formated arguments")
            
    def validate(self):
        # lat, lng          -> float
        # time              -> integer (minute ?th in day)
        # day, month, year  -> integer (check if date is legal) 
        # mode_code         -> integer (0..?)
        # constraint        -> integer (minutes)
        return True

    def calculate_simple(self):
        """
        Phase 1 simple routing (circles).
        """
        pass
    
    def calculate_2(self):
        """
        Phase 2 refined routing.
        """
        pass
    
    def get_result(self):
        return {"success":True, "poligon":"..."}
