package com.cleanup.todoc.data.dao;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

@Database(entities = {Task.class, Project.class}, version = 1)
public abstract class TaskDataBase extends RoomDatabase {

    public static TaskDataBase instance;

    public abstract TaskDao taskDao();
    public abstract ProjectDao projectDao();

    //Synchronized means only one thread at a time can access it
    public static synchronized TaskDataBase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), TaskDataBase.class, "task_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }



    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {

        //Populating our db when first created with the 3 projects given
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateProjectDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateProjectDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private ProjectDao projectDao;

        private PopulateProjectDbAsyncTask(TaskDataBase db){
            projectDao = db.projectDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            projectDao.insert(new Project(1, "Projet Tartampion", 0xFFEADAD1));
            projectDao.insert(new Project(2, "Projet Lucidia", 0xFFB4CDBA));
            projectDao.insert(new Project(3, "Projet Circus", 0xFFA3CED2));
            Log.d("debuglog", "PROJECTS INSERTED");
            return null;
        }
    }
}