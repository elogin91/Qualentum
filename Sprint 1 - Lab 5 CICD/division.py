
def dividir(a, b):
    if b == 0:
        raise ZeroDivisionError("División por cero no permitida")
    return a / b

if __name__ == "__main__":
    print(dividir(5, 3))