package com.davinciapp.todoc.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.davinciapp.todoc.model.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM task_table ")
    LiveData<List<Task>> getAllTasks();

    @Query("DELETE FROM task_table WHERE id = :taskId")
    void deleteTaskById(int taskId);

    @Insert
    void insert(Task task);

    @Delete
    void delete(Task task);



}
