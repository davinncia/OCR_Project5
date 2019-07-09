package com.cleanup.todoc.dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.cleanup.todoc.model.Task;

@Database(entities = Task.class, version = 1)
public abstract class TaskDataBase extends RoomDatabase {

    public static TaskDataBase instance;

    //Synchronized means only one thread at a time can access it
    public static synchronized TaskDataBase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), TaskDataBase.class, "task_name")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}