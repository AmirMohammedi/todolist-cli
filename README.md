# To-Do List (Java + SQLite)

A simple command-line To-Do List application built with Java and SQLite.

## Features
- Add tasks
- View tasks
- Mark tasks as done
- Delete tasks
- Data is stored persistently in an SQLite database

## Requirements
- Java (JDK 8 or higher)
- SQLite JDBC Driver ([Download Here](https://github.com/xerial/sqlite-jdbc/releases))

## Installation
### 1. Clone the Repository
```sh
git clone https://github.com/AmirMohammedi/todolist-cli.git
cd todolist-cli
```

### 2. Download and Add SQLite JDBC Driver
1. Download `sqlite-jdbc-<version>.jar` from [here](https://github.com/xerial/sqlite-jdbc/releases).
2. Place it in the project directory.

### 3. Compile and Run
#### **Windows**
```sh
javac TodoList.java
java -cp ".;sqlite-jdbc-<version>.jar" TodoList
```
#### **Linux/macOS**
```sh
javac TodoList.java
java -cp ".:sqlite-jdbc-<version>.jar" TodoList
```

## Usage
When you run the program, you’ll see the menu:
```
1. Add Task
2. View Tasks
3. Mark Task as Done
4. Delete Task
5. Exit
```
- Select an option and follow the prompts.

## Database Structure
The SQLite database (`todolist.db`) contains a single table:
```sql
CREATE TABLE IF NOT EXISTS tasks (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    description TEXT NOT NULL,
    done INTEGER DEFAULT 0
);
```



