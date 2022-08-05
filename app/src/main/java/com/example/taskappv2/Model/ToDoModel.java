package com.example.taskappv2.Model;

public class ToDoModel {
    // Each individual task has 3 attributes: id,status task
    private int id; //id - which we refer in the database, so that we can execute the
                    // queries based on the ID of the task
    private int status; //status - acts as the boolean variable for if a task has been
                        // completed or not.
    private String task; //String task is the actual text value of the task which is
                        // written in the application


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}


//MODEL FOR RECYCLERVIEW






