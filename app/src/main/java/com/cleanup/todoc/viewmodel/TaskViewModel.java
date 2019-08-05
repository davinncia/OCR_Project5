package com.cleanup.todoc.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.TaskRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class TaskViewModel extends ViewModel {

    //REPOSITORY
    private final TaskRepository taskRepository;
    private final Executor executor;

    //DATA
    @Nullable
    public LiveData<List<Task>> allTasks;
    @Nullable
    public LiveData<List<Project>> allProjects;

    public TaskViewModel(TaskRepository taskRepository, Executor executor) {
        this.taskRepository = taskRepository;
        this.executor = executor;
        getAllTasks();
        getAllProjects();
    }

    // -------------
    //FOR TASKS
    // -------------
    public void getAllTasks(){
        if (allTasks == null){
            allTasks = taskRepository.getAllTasks();
        }
    }

    public void insert(final Task task){
        executor.execute(new Runnable() {
           @Override
            public void run() {
               taskRepository.insert(task);
           }
        });
    }

    public void delete(final Task task){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                taskRepository.delete(task);
            }
        });
    }

    // -------------
    // FOR PROJECTS
    // -------------
    public void getAllProjects() {
        if (allProjects == null){
            allProjects = taskRepository.getAllProjects();
        }
    }

    public void deleteProject(Project project){
        taskRepository.deleteProject(project);
    }

}
