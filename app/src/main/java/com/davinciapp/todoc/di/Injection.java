package com.davinciapp.todoc.di;

import android.content.Context;

import com.davinciapp.todoc.repository.TaskRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {

    public static ViewModelFactory provideViewModelFactory(Context context){
        TaskRepository taskRepository = new TaskRepository(context);
        Executor executor = Executors.newSingleThreadExecutor();
        return ViewModelFactory.getInstance(taskRepository, executor);
    }
}
