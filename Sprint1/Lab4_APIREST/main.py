from fastapi import FastAPI, HTTPException

app = FastAPI()


@app.get("/")
def read_root():
    return {"message": "Bienvenido a la API de operaciones matemáticas"}
    # uvicorn main:app --reload
    # http://127.0.0.1:8000


@app.get("/suma/")
def suma(a: int, b: int):
    return {"resultado": a + b}
    # http://127.0.0.1:8000/suma/?a=3&b=2

@app.get("/resta/")
def resta(a: int, b: int):
    return {"resultado": a - b}
    # http://127.0.0.1:8000/resta/?a=3&b=2

@app.get("/multiplicacion/")
def multiplicacion(a: int, b: int):
    return {"resultado": a * b}
    # http://127.0.0.1:8000/multiplicacion/?a=3&b=2

@app.get("/division/")
def division(a: int, b: int):
    if b == 0:
        raise HTTPException(status_code=400, detail="No se puede dividir por cero")
    return {"resultado": a / b}
    # http://127.0.0.1:8000/division/?a=3&b=2
    # http://127.0.0.1:8000/division/?a=3&b=0

if __name__ == "__main__":
    import uvicorn

    uvicorn.run(app, host="127.0.0.1", port=8000)
