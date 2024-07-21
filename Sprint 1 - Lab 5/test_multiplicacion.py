import unittest
import sys

from multiplicacion import multiplicar
class TestMultiplicar(unittest.TestCase):
    def test_multiplicar(self):
        self.assertEqual(multiplicar(3, 2), 6)
        self.assertEqual(multiplicar(-1, 1), -1)
        self.assertEqual(multiplicar(-1, -1), 1)
        self.assertEqual(multiplicar(sys.maxsize, 1), sys.maxsize)
        self.assertEqual(multiplicar(sys.maxsize, sys.maxsize), sys.maxsize * sys.maxsize)
        self.assertEqual(multiplicar(0, sys.maxsize), 0)
if __name__ == '__main__':
    unittest.main()