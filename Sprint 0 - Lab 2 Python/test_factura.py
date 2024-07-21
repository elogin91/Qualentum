import unittest

from factura import calcular_factura
from datetime import datetime

class TestCalcularFactura(unittest.TestCase):
    def test_calculo_factura(self):
        ticket = [
            "1 - filete de ternera - 30,23",
            "4 - coca cola - 4,20",
            "-2 - coca cola - 1,40",
            "1 - pan - 0,90"
        ]

        total, fecha = calcular_factura(ticket)

        self.assertEqual(total, "Total a pagar: 39.36")
        self.assertEqual(fecha, f"Fecha de compra: {datetime.now().strftime("%Y-%m-%d")}")

if __name__ == '__main__':
    unittest.main()
