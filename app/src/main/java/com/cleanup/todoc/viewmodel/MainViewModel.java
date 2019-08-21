package com.cleanup.todoc.viewmodel;

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
import java.util.List;
import java.util.concurrent.Executor;

import static com.cleanup.todoc.ui.MainActivity.SortingType.NONE;

public class MainViewModel extends ViewModel {

    //REPOSITORY
    private final TaskRepository taskRepository;
    private final Executor executor;

    //SORTING
    private MutableLiveData<MainActivity.SortingType> mSortingTypeLiveData = new MutableLiveData<>();

    private LiveData<List<UiTaskModel>> tasksLiveData;

    public MediatorLiveData<List<UiTaskModel>> sortedTasks = new MediatorLiveData<>();

    // -------------
    // CONSTRUCTOR
    // -------------
    public MainViewModel(final TaskRepository taskRepository, Executor executor) {
        this.taskRepository = taskRepository;
        this.executor = executor;

        mSortingTypeLiveData.setValue(NONE);

        //Tasks as source
        tasksLiveData = Transformations.switchMap(taskRepository.getAllTasks(), new Function<List<Task>, LiveData<List<UiTaskModel>>>() {
            @Override
            public LiveData<List<UiTaskModel>> apply(final List<Task> taskList) {

                //Project as source
                return Transformations.map(taskRepository.getAllProjects(), new Function<List<Project>, List<UiTaskModel>>() {
                    @Override
                    public List<UiTaskModel> apply(List<Project> projectList) {

                        List<UiTaskModel> result = new ArrayList<>();

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

        //Adding data as source to our mediator
        sortedTasks.addSource(tasksLiveData, new Observer<List<UiTaskModel>>() {
            @Override
            public void onChanged(List<UiTaskModel> uiTaskModels) {
                sortedTasks.setValue(combineDataAndSortingType(tasksLiveData, mSortingTypeLiveData));

            }
        });

        //Adding order as source to our mediator
        sortedTasks.addSource(mSortingTypeLiveData, new Observer<MainActivity.SortingType>() {
            @Override
            public void onChanged(MainActivity.SortingType sortingType) {
                sortedTasks.setValue(combineDataAndSortingType(tasksLiveData, mSortingTypeLiveData));
            }
        });
    }

    // -------------
    // ORDERING
    // -------------
    private List<UiTaskModel> combineDataAndSortingType(LiveData<List<UiTaskModel>> tasksLiveData, MutableLiveData<MainActivity.SortingType> sortingTypeLiveData) {

        if (tasksLiveData.getValue() == null || sortingTypeLiveData.getValue() == null){
            return new ArrayList<>();
        }

        List<UiTaskModel> listToSort = tasksLiveData.getValue();

        switch (sortingTypeLiveData.getValue()){
            case NONE:
                return tasksLiveData.getValue();
            case ALPHABETICAL:
                Collections.sort(listToSort, new UiTaskModel.UiTaskAZComparator());
                return listToSort;
            case ALPHABETICAL_INVERTED:
                Collections.sort(listToSort, new UiTaskModel.UiTaskZAComparator());
                return listToSort;
            case OLDEST_FIRST:
                Collections.sort(listToSort, new UiTaskModel.UiTaskOldComparator());
                return listToSort;
            case RECENT_FIRST:
                Collections.sort(listToSort, new UiTaskModel.UiTaskRecentComparator());
                return listToSort;
            default:
                return listToSort;
        }
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
        this.mSortingTypeLiveData.setValue(sortingType);
    }


    // -------------
    // FOR PROJECTS
    // -------------

    public void deleteProject(final Project project){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                taskRepository.deleteProject(project);
            }
        });
    }

}
