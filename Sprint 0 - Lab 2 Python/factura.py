from datetime import datetime

def calcular_factura(ticket):
    total = 0.0
    for item in ticket:
        unidades, producto, precio = item.split(" - ")
        unidades = int(unidades)
        precio = float(precio.replace(',', '.'))
        if unidades < 0:
            total -= precio
        else:
            total += precio

    total_con_iva = total * 1.16
    fecha_compra = datetime.now().strftime("%Y-%m-%d")

    return f"Total a pagar: {total_con_iva:.2f}", f"Fecha de compra: {fecha_compra}"

if __name__ == "__main__":
    ticket = [
        "1 - filete de ternera - 30,23",
        "4 - coca cola - 4,20",
        "-2 - coca cola - 1,40",
        "1 - pan - 0,90"
    ]

    total, fecha = calcular_factura(ticket)
    print(total)
    print(fecha)
