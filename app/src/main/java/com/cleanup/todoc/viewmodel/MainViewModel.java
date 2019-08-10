package com.cleanup.todoc.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.TaskRepository;
import com.cleanup.todoc.ui.MainActivity;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;

public class MainViewModel extends ViewModel {

    //REPOSITORY
    private final TaskRepository taskRepository;
    private final Executor executor;

    //SORTING
    private MainActivity.SortingType sortingType = MainActivity.SortingType.NONE;

    //DATA
    @NonNull
    private LiveData<List<Task>> allTasks;
    public MediatorLiveData<List<Task>> mediatorTasks = new MediatorLiveData<>();

    @NonNull
    private LiveData<List<Project>> allProjects;

    // -------------
    // CONSTRUCTOR
    // -------------
    public MainViewModel(TaskRepository taskRepository, Executor executor) {
        this.taskRepository = taskRepository;
        this.executor = executor;

        allTasks = taskRepository.getAllTasks();
        mediatorTasks.addSource(allTasks, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                mediatorTasks.setValue(sortTasksOrder(sortingType));
            }
        });

        allProjects = taskRepository.getAllProjects();
    }

    // -------------
    //FOR TASKS
    // -------------

    public void deleteTask(final Task task){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                taskRepository.delete(task);
            }
        });
    }

    public void setSortingType(final MainActivity.SortingType sortingType){
        this.sortingType = sortingType;
        mediatorTasks.setValue(sortTasksOrder(sortingType));
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
                Collections.sort(sortedTasks, new Task.TaskZAComparator());
                break;
            case OLDEST_FIRST:
                Collections.sort(sortedTasks, new Task.TaskOldComparator());
                break;
            case RECENT_FIRST:
                Collections.sort(sortedTasks, new Task.TaskRecentComparator());
                break;
            case NONE:
                break;
        }
        return sortedTasks;
    }
}
