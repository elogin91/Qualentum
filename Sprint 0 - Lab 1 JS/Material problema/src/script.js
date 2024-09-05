// Array to store all tasks
const tasks = [];

// Function to add a new task to the array tasks
function addTask() {
    const taskInput = document.getElementById('taskInput');
    const name = taskInput.value.trim();

    if (name !== '') {
        const id = tasks.length ? tasks[tasks.length - 1].id + 1 : 1;

        // Creating a new task
        const task = {
            name: name,
            id: id,
            completed: false,
        }

        tasks.push(task);

        // Clean input value
        taskInput.value = '';

        console.log('Tarea agregada:', task);
        console.log('Lista de tareas:', tasks);

        renderTasks();
    } else {
        alert('Error al agregar la tarea.')
    }
}

// Changing task to completed
function toggleTaskCompleted(id) {
    const task = tasks.find(task => task.id === id);
    if (task) {
        task.completed = !task.completed;
        console.log(`Tarea ${id} completada: ${task.completed}`);
    }
    renderTasks();

}

// Deleting task to the list
function deleteTask(id) {
    const taskIndex = tasks.findIndex(task => task.id === id);
    if (taskIndex !== -1) {
        tasks.splice(taskIndex, 1);
        console.log(`Tarea ${id} eliminada`);
    }
    renderTasks();

}

// Rendering HTML adding new task to the list of tasks
function renderTasks() {
    // Clean previous HTML list
    const taskList = document.getElementById('taskList');
    taskList.innerHTML = '';

    // Add element to the list
    tasks.forEach(task => {
        const taskItem = document.createElement('li');

        const article = document.createElement('article');

        const checkbox = document.createElement('input');
        checkbox.type = 'checkbox';
        checkbox.className = 'task-checkbox';
        checkbox.checked = task.completed;
        checkbox.addEventListener('change', () => toggleTaskCompleted(task.id));

        const span = document.createElement('span');
        span.className = 'task-text';
        span.textContent = task.name;

        const trashIcon = document.createElement('i');
        trashIcon.className = 'fa fa-trash';
        trashIcon.addEventListener("click", () => deleteTask(task.id));

        article.appendChild(checkbox);
        article.appendChild(span);

        taskItem.appendChild(article);
        taskItem.appendChild(trashIcon);

        taskList.appendChild(taskItem);

        console.log('Lista de tareas:', tasks);
    });
}

document.getElementById('addTaskButton').addEventListener('click', addTask);
