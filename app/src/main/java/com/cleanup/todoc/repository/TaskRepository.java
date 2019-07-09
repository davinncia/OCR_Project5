package com.cleanup.todoc.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.dao.TaskDao;
import com.cleanup.todoc.dao.TaskDataBase;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskRepository {

    private TaskDao taskDao;

    private LiveData<List<Task>> allTasks;

    //Application will be use as context to create our database instance
    public TaskRepository(Application application){
        TaskDataBase dataBase = TaskDataBase.getInstance(application);
        taskDao = dataBase.taskDao();
        allTasks = taskDao.getAllTasks();
    }

    public void insert(Task task){
        new InsertTaskAsyncTask(taskDao).execute(task);
    }

    public void delete(Task task){
        new DeleteTaskAsyncTask(taskDao).execute(task);
    }

    public LiveData<List<Task>> getAllTasks(){
        //This is executed in background by Room
        return allTasks;
    }

    //Room doesn't allow database actions in main thread
    private static class InsertTaskAsyncTask extends AsyncTask<Task, Void, Void>{

        private TaskDao taskDao;

        public InsertTaskAsyncTask(TaskDao taskDao){
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            taskDao.insert(tasks[0]);
            return null;
        }
    }

    private static class DeleteTaskAsyncTask extends AsyncTask<Task, Void, Void>{

        private TaskDao taskDao;

        public DeleteTaskAsyncTask(TaskDao taskDao){
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            taskDao.delete(tasks[0]);
            return null;
        }
    }
}
