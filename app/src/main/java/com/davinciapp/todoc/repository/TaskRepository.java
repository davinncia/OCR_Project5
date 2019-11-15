package com.davinciapp.todoc.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.davinciapp.todoc.data.dao.ProjectDao;
import com.davinciapp.todoc.data.dao.TaskDao;
import com.davinciapp.todoc.data.dao.TaskDataBase;
import com.davinciapp.todoc.model.Project;
import com.davinciapp.todoc.model.Task;

import java.util.List;

public class TaskRepository {

    private TaskDao taskDao;
    private ProjectDao projectDao;

    private LiveData<List<Task>> allTasks;
    private LiveData<List<Project>> allProjects;

    //Application will be use as context to create our database instance
    public TaskRepository(Context context){
        TaskDataBase dataBase = TaskDataBase.getInstance(context);
        taskDao = dataBase.taskDao();
        projectDao = dataBase.projectDao();
        allTasks = taskDao.getAllTasks();
        allProjects = projectDao.getAllProjects();
    }

    public void insert(Task task){
        taskDao.insert(task);
    }

    public void deleteTaskById(int id){
        taskDao.deleteTaskById(id);
    }

    public LiveData<List<Task>> getAllTasks(){
        //This is executed in background by Room
        return allTasks;
    }

    public void insertProject(Project project){
        projectDao.insert(project);
    }

    public void deleteProject(Project project){
        projectDao.delete(project);
    }

    public LiveData<List<Project>> getAllProjects(){
        //This is executed in background by Room
        return allProjects;
    }
}
