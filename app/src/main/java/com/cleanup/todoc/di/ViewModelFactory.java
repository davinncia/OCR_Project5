package com.cleanup.todoc.di;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cleanup.todoc.repository.TaskRepository;
import com.cleanup.todoc.viewmodel.TaskViewModel;

import java.util.concurrent.Executor;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final TaskRepository mTaskRepository;
    private final Executor mExecutor;

    public ViewModelFactory(TaskRepository taskRepository, Executor executor) {
        mTaskRepository = taskRepository;
        mExecutor = executor;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TaskViewModel.class)){
            return (T) new TaskViewModel(mTaskRepository, mExecutor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
