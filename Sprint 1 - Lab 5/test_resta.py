import unittest
import sys

from resta import restar

class TestRestar(unittest.TestCase):
    def test_restar(self):
        self.assertEqual(restar(3, 2), 1)
        self.assertEqual(restar(1, 1), 0)
        self.assertEqual(restar(-1, 1), -2)
        self.assertEqual(restar(-3, -1), -2)
        self.assertEqual(restar(sys.maxsize, 1), sys.maxsize - 1)
        self.assertEqual(restar(sys.maxsize, sys.maxsize), 0)
        self.assertEqual(restar(0, sys.maxsize), -sys.maxsize)
if __name__ == '__main__':
    unittest.main()