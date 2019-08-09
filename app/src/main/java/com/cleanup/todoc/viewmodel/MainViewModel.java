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
import com.cleanup.todoc.ui.MainActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;

public class MainViewModel extends ViewModel {

    //REPOSITORY
    private final TaskRepository taskRepository;
    private final Executor executor;

    //DATA
    @NonNull
    private LiveData<List<Task>> allTasks;
    private MutableLiveData<List<Task>> mutableLiveDataTasks = new MutableLiveData<>();
    @NonNull
    public LiveData<List<Project>> allProjects;

    public MainViewModel(TaskRepository taskRepository, Executor executor) {
        this.taskRepository = taskRepository;
        this.executor = executor;

        allTasks = taskRepository.getAllTasks();

        allProjects = taskRepository.getAllProjects();
    }

    // -------------
    //FOR TASKS
    // -------------
    @NonNull
    public LiveData<List<Task>> getAllTasks(){
        return allTasks;
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
    @NonNull
    public LiveData<List<Project>> getAllProjects() {
        return allProjects;
    }

    public void deleteProject(final Project project){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                taskRepository.deleteProject(project);
            }
        });
    }

    // -------------
    // SORTING ORDER
    // -------------
    public List<Task> sortTasksOrder(MainActivity.SortingType sortingType){

        List<Task> sortedTasks = allTasks.getValue();

        switch (sortingType){
            case ALPHABETICAL:
                Collections.sort(sortedTasks, new Task.TaskAZComparator());
                break;
            case ALPHABETICAL_INVERTED:
                break;
            case RECENT_FIRST:
                break;
            case NONE:
                break;
        }
        return sortedTasks;
    }
}
