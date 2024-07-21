import unittest
import sys

from division import dividir

class TestDividir(unittest.TestCase):
    def test_dividir(self):
        self.assertEqual(dividir(6, 2), 3)
        with self.assertRaises(ZeroDivisionError):
            dividir(1, 0)
        self.assertEqual(dividir(-1, 1), -1)
        self.assertEqual(dividir(-6, -2), 3)
        self.assertEqual(dividir(sys.maxsize, sys.maxsize), 1)
        self.assertEqual(dividir(0, sys.maxsize), 0)
if __name__ == '__main__':
    unittest.main()