package com.cleanup.todoc.di;

import android.content.Context;

import com.cleanup.todoc.repository.TaskRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {

    public static ViewModelFactory provideViewModelFactory(Context context){
        TaskRepository taskRepository = new TaskRepository(context);
        Executor executor = Executors.newSingleThreadExecutor();
        return new ViewModelFactory(taskRepository, executor);
    }
}
