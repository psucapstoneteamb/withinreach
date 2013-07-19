# Libraries
import unittest

# Local
from webapp import create_app

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
    
    def test_simple(self):
        self.assertEqual(1, 1)
        self.assertEqual(self.c.get('/')._status_code,302)
        

