# Libraries
import unittest

# Local
from webapp import create_app
from webapp.controller import Controller

class BaseTestCase(unittest.TestCase):

    def setUp(self):
        #self.db_fd, flaskr.app.config['DATABASE'] = tempfile.mkstemp()
        #app.config['TESTING'] = True
        #self.app = app.test_client()
        #flaskr.init_db()
        create_app("test")
        from webapp import app #, db
        self.c = app.test_client() # or only app?
        
        

    def tearDown(self):
        #os.close(self.db_fd)
        #os.unlink(flaskr.app.config['DATABASE'])
        pass
    
    def test_case1(self):
        self.assertEqual(1, 1)
        self.assertEqual(self.c.get('/')._status_code,302)
        
    def test_case2(self):
        request_args = {'lat':100.55,
                        'long': 200.66,
                        'time': 45,
                        'day': 20,
                        'month': 6,
                        'year': 2010,
                        'mode_code': 1,
                        'constraint': 45}
        ctr = Controller(request_args)
        val = ctr.validate()
        self.assertEqual(val,True)
