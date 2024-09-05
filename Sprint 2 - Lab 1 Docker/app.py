from flask import Flask,render_template, request
from flask_mysqldb import MySQL
import MySQLdb.cursors



app = Flask(__name__)

app.config['MYSQL_HOST'] = 'mysql'
app.config['MYSQL_USER'] = 'root'
app.config['MYSQL_PASSWORD'] = 'password'
app.config['MYSQL_DB'] = 'todo_app'

mysql = MySQL(app)

@app.route('/')
def index():
    # creamos la tabla tasks si no existe
    #Creating a connection cursor
    cursor = mysql.connection.cursor()

    #Executing SQL Statements
    cursor.execute(''' CREATE TABLE IF NOT EXISTS tasks(ID BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY, name VARCHAR(512)) ''')

    #Saving the Actions performed on the DB
    mysql.connection.commit()

    #Closing the cursor
    cursor.close()

    cursor = mysql.connection.cursor(MySQLdb.cursors.DictCursor)
    cursor.execute(''' SELECT * FROM tasks ''')
    tasks = cursor.fetchall()
    cursor.close()

    return render_template('index.html', tasks = tasks, len = len(tasks))

@app.route('/add', methods = ['POST', 'GET'])
def add():
    # check if is GET or POST
    if request.method == 'GET':
        return index()

    task = request.form['title']
    cursor = mysql.connection.cursor()
    cursor.execute(''' INSERT INTO tasks(name) VALUES(%s)''',(task,))
    mysql.connection.commit()
    cursor.close()
    return index()


@app.route('/delete/<int:id>')
def delete(id):
    cursor = mysql.connection.cursor()
    cursor.execute(''' DELETE FROM tasks WHERE ID = %s''',(id,))
    mysql.connection.commit()
    cursor.close()
    return index()

if __name__ == '__main__':
    app.run()