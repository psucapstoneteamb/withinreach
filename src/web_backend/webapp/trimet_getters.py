import slumber

KEY = '9225A10883A8FAF36A86602C'                  # trimet's appID
TRIMET_URL = 'http://developer.trimet.org/ws/V1/' # trimet GTFS-realtime

def get_arrival(stop_id):
    getter = slumber.API(TRIMET_URL)

    # template: /arrivals/locIDs/6849,6850/
    raw_arrival = getter.arrivals.locIDs(stop_id).appID(KEY).json(True).get()
    return raw_arrival
