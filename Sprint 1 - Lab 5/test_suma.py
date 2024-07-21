import unittest
import sys

from suma import sumar
class TestSumar(unittest.TestCase):
    def test_sumar(self):
        self.assertEqual(sumar(3, 2), 5)
        self.assertEqual(sumar(-1, 1), 0)
        self.assertEqual(sumar(-1, -1), -2)
        self.assertEqual(sumar(sys.maxsize, 1), sys.maxsize + 1)
        self.assertEqual(sumar(sys.maxsize, sys.maxsize), sys.maxsize * 2)
        self.assertEqual(sumar(0, sys.maxsize), sys.maxsize)
if __name__ == '__main__':
    unittest.main()