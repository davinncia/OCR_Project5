package com.cleanup.todoc.repository;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.data.dao.ProjectDao;
import com.cleanup.todoc.data.dao.TaskDao;
import com.cleanup.todoc.data.dao.TaskDataBase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

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

    public void delete(Task task){
        taskDao.delete(task);
    }

    public LiveData<List<Task>> getAllTasks(){
        //This is executed in background by Room
        return allTasks;
    }

    public void insertProject(Project project){

        new InsertProjectAsyncTask(projectDao).execute(project);
    }

    public void deleteProject(Project project){
        new DeleteProjectAsyncTask(projectDao).execute(project);
    }

    public LiveData<List<Project>> getAllProjects(){
        //This is executed in background by Room
        return allProjects;
    }



    //Room doesn't allow database actions in main thread
    private static class InsertProjectAsyncTask extends AsyncTask<Project, Void, Void>{

        private ProjectDao projectDao;

        public InsertProjectAsyncTask(ProjectDao projectDao) {
            this.projectDao = projectDao;
        }

        @Override
        protected Void doInBackground(Project... projects) {
            projectDao.insert(projects[0]);
            return null;
        }
    }

    private static class DeleteProjectAsyncTask extends AsyncTask<Project, Void, Void>{

        private ProjectDao projectDao;

        public DeleteProjectAsyncTask(ProjectDao projectDao){
            this.projectDao = projectDao;
        }

        @Override
        protected Void doInBackground(Project... projects) {
           projectDao.delete(projects[0]);
            return null;
        }
    }
}
