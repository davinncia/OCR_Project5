package com.davinciapp.todoc.di;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.davinciapp.todoc.repository.TaskRepository;
import com.davinciapp.todoc.viewmodel.AddTaskViewModel;
import com.davinciapp.todoc.viewmodel.MainViewModel;

import java.util.concurrent.Executor;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private static ViewModelFactory sFactory;

    private final TaskRepository mTaskRepository;
    private final Executor mExecutor;


    private ViewModelFactory(TaskRepository taskRepository, Executor executor) {
        mTaskRepository = taskRepository;
        mExecutor = executor;
    }

    static ViewModelFactory getInstance(TaskRepository taskRepository, Executor executor){

        if (sFactory == null){
            sFactory = new ViewModelFactory(taskRepository, executor);
        }
        return sFactory;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)){
            return (T) new MainViewModel(mTaskRepository, mExecutor);
        } else if (modelClass.isAssignableFrom(AddTaskViewModel.class)){
            return (T) new AddTaskViewModel(mTaskRepository, mExecutor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
