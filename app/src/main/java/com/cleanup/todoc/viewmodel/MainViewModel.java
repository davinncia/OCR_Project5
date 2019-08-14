package com.cleanup.todoc.viewmodel;

import android.graphics.Color;
import android.util.Log;

import androidx.annotation.ColorInt;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.TaskRepository;
import com.cleanup.todoc.ui.MainActivity;
import com.cleanup.todoc.ui.model.UiTaskModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.Executor;

public class MainViewModel extends ViewModel {

    //REPOSITORY
    private final TaskRepository taskRepository;
    private final Executor executor;

    //SORTING
    private MainActivity.SortingType sortingType = MainActivity.SortingType.ALPHABETICAL;


    //LiveData to expose
    private LiveData<List<UiTaskModel>> tasksLiveData;

    public MediatorLiveData<List<UiTaskModel>> sortedTasks = new MediatorLiveData<>();


/*
    @NonNull
    private LiveData<List<Project>> allProjects;
*/

    // -------------
    // CONSTRUCTOR
    // -------------
    public MainViewModel(final TaskRepository taskRepository, Executor executor) {
        this.taskRepository = taskRepository;
        this.executor = executor;

        //TODO Nino : not triggered when sortingType changes...

        //Tasks as source
        tasksLiveData = Transformations.switchMap(taskRepository.getAllTasks(), new Function<List<Task>, LiveData<List<UiTaskModel>>>() {
            @Override
            public LiveData<List<UiTaskModel>> apply(final List<Task> taskList) {

                //Project as source
                return Transformations.map(taskRepository.getAllProjects(), new Function<List<Project>, List<UiTaskModel>>() {
                    @Override
                    public List<UiTaskModel> apply(List<Project> projectList) {

                        List<UiTaskModel> result = new ArrayList<>();

                        //TreeSet<Task> sortedTasks = new TreeSet<>(sortTasksOrder(sortingType));
                        //sortedTasks.addAll(taskList);

                        for (Task task : taskList){

                            for (Project project : projectList){

                                if (task.getProjectId() == project.getId()){

                                    result.add(new UiTaskModel(project.getColor(), task.getId(), task.getName(), project.getName(), task.getCreationTimestamp()));
                                }
                            }
                        }
                        return result;
                    }
                });
            }
        });

        //Adding a source to our mediator
        sortedTasks.addSource(tasksLiveData, new Observer<List<UiTaskModel>>() {
            @Override
            public void onChanged(List<UiTaskModel> uiTaskModels) {
                sortedTasks.setValue(sortTasksOrder(tasksLiveData.getValue(), sortingType));
            }
        });

    }

    // -------------
    //FOR TASKS
    // -------------

    public void deleteTaskById(final int id){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                taskRepository.deleteTaskById(id);

            }
        });
    }

    public void setSortingType(final MainActivity.SortingType sortingType){
        this.sortingType = sortingType;

        //TODO : we need somehow to trigger our LiveData !
        sortedTasks.setValue(sortTasksOrder(sortedTasks.getValue(), sortingType));
    }

    // -------------
    // FOR PROJECTS
    // -------------
    /*
    @NonNull
    public LiveData<List<Project>> getAllProjects() {
        return allProjects;
    }
    */

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

    /*
    private Comparator<Task> sortTasksOrder(MainActivity.SortingType sortingType){

        switch (sortingType){
            case ALPHABETICAL:
                return new Task.TaskAZComparator();
            case ALPHABETICAL_INVERTED:
               return new Task.TaskZAComparator();
            case OLDEST_FIRST:
                return new Task.TaskOldComparator();
            case RECENT_FIRST:
                return new Task.TaskRecentComparator();
            case NONE:
                break;
        }
        return null;
    }
    */


    private List<UiTaskModel> sortTasksOrder(List<UiTaskModel> uiTasks, MainActivity.SortingType sortingType){


        switch (sortingType){
            case ALPHABETICAL:
                Collections.sort(uiTasks, new UiTaskModel.UiTaskAZComparator());
                break;
            case ALPHABETICAL_INVERTED:
                Collections.sort(uiTasks, new UiTaskModel.UiTaskZAComparator());
                break;
            case OLDEST_FIRST:
                Collections.sort(uiTasks, new UiTaskModel.UiTaskOldComparator());
                break;
            case RECENT_FIRST:
                Collections.sort(uiTasks, new UiTaskModel.UiTaskRecentComparator());
                break;
            case NONE:
                break;
        }
        return uiTasks;
    }


}
