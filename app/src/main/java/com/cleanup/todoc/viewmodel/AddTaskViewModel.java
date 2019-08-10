package com.cleanup.todoc.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.TaskRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class AddTaskViewModel extends ViewModel {

    private Executor mExecutor;
    private TaskRepository mTaskRepository;

    private LiveData<List<Project>> mProjects;

    public AddTaskViewModel(TaskRepository taskRepository, Executor executor) {
        mTaskRepository = taskRepository;
        mExecutor = executor;

        mProjects = mTaskRepository.getAllProjects();
    }

    public LiveData<List<Project>> getProjects(){
        return mProjects;
    }

    public void insertTask(final Task task){
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mTaskRepository.insert(task);
            }
        });
    }
}
