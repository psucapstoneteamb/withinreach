from result import Result

class Controller:
    """
    This class encapsulates request arguments from the Android app,
    validates inputs, processes inputs and creates result (self.r) which
    is ready for jsonify.
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
            raise Exception("* Missing arguments")
        
        if not self.validate():
            raise Exception("* Ill-formated arguments")
        
        self.r = Result()
            
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
        mode = int(self.mode_code)
        time = float(self.constraint)
        lat = float(self.lat)
        long = float(self.lng)
        
        if (mode % 2) == 1: #walking, using 4 MPH, or 1/1035 latitude a minute as the walk speed
            self.r.set_cur_mode(1)
            self.r.add_coordinate(lat,long)
            self.r.add_coordinate(lat+(time/1035),long)
        if (mode % 4) >= 2: #biking, using 12 MPH, or 1/345 latitude a minute as the bike speed
            self.r.set_cur_mode(2)
            self.r.add_coordinate(lat,long)
            self.r.add_coordinate(lat+(time/345),long)
        if (mode % 8) >= 4: #transit, using 30 MPH, or 1/138 latitude a minute as the transit speed
            self.r.set_cur_mode(4)
            self.r.add_coordinate(lat,long)
            self.r.add_coordinate(lat+(time/138),long)
        
        
        
        # Set success flag
        self.r.set_success_true()
        print 'HEY'
		
		
    
    def calculate_2(self):
        """
        Phase 2 refined routing.
        """
        pass
    
    def get_result(self):
        return self.r.get()
