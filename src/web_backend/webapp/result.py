class Result:
    """
    This class encapsulates result of one or more reachable zones;
    and provides methods for populating the result, which shall be called
    by the calculation module.
    Sample result:
    _d={
        "success":True,
        "result":{
            "0":{"coordinate": [{"lat":1.1,"long":2.2}, {"lat":2.2,"long":3.3}]},
            "2":{"coordinate": [{"lat":1.2,"long":2.5}]}
        }
      }
    """
    def __init__(self):
        self._d = { "success":False, "result":{}}
        self.cur_mode = -1    # transport mode that are being calculated
    
    def set_success_true(self):
        self._d["success"] = True
    
    def set_cur_mode(self, cur_mode):
        self.cur_mode = cur_mode
    
    def add_coordinate(self, lat, lng):
        # Add a pair of coordinates to the current focused transport mode.
        if self.cur_mode == -1:
            raise Exception("* cur_mode not set")
        if self.cur_mode not in self._d["result"]:
            print self._d
            self._d["result"][self.cur_mode] = {"coordinate": []}
        
        self._d["result"][self.cur_mode]["coordinate"].append({"lat":lat, "long":lng})

    
    def get(self):
        return self._d

