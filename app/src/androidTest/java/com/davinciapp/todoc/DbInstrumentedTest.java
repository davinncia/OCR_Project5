package com.davinciapp.todoc;

import android.content.Context;
import android.graphics.Color;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.davinciapp.todoc.data.dao.ProjectDao;
import com.davinciapp.todoc.data.dao.TaskDao;
import com.davinciapp.todoc.data.dao.TaskDataBase;
import com.davinciapp.todoc.model.Project;
import com.davinciapp.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class DbInstrumentedTest {

    private TaskDao mTaskDao;
    private ProjectDao mProjectDao;

    private TaskDataBase db;

    //Executes each task synchronously
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void createDb(){
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        db = Room.inMemoryDatabaseBuilder(context, TaskDataBase.class).build();
        mTaskDao = db.taskDao();
        mProjectDao = db.projectDao();
    }

    @Before
    public void addProject(){
        Project tartampion = new Project(1, "Tartampion", Color.RED);
        mProjectDao.insert(tartampion);
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void getProjects() throws Exception {
        List<Project> projects = LiveDataTestUtils.getValue(mProjectDao.getAllProjects());
        assertEquals("Tartampion", projects.get(0).getName());
    }

    @Test
    public void insertTaskInDb() throws InterruptedException {

        Task task = new Task(1, "test", 1234);
        mTaskDao.insert(task);
        List<Task> tasks = LiveDataTestUtils.getValue(mTaskDao.getAllTasks());
        assertEquals(1, tasks.size());
    }

    @Test
    public void retrieveTaskInDb() throws InterruptedException {

        Task task = new Task(1, "test", 1234);
        mTaskDao.insert(task);
        List<Task> tasks = LiveDataTestUtils.getValue(mTaskDao.getAllTasks());
        assertEquals("test", tasks.get(0).getName());
    }

    @Test
    public void deleteTaskInDb() throws InterruptedException {

        Task task = new Task(1, "test", 1234);
        task.setId(1); //Without this doesn't delete.
        mTaskDao.insert(task);
        List<Task> tasks = LiveDataTestUtils.getValue(mTaskDao.getAllTasks());
        assertEquals("test", tasks.get(0).getName());

        mTaskDao.delete(task);
        List<Task> tasks2 = LiveDataTestUtils.getValue(mTaskDao.getAllTasks());
        assertEquals(0, tasks2.size());
    }

    //Foreign Key CASCADE test
    @Test
    public void deleteProjectDeletesTasks() throws InterruptedException {
        Task task = new Task(1, "test", 1234);
        mTaskDao.insert(task);

        Project tartampion = LiveDataTestUtils.getValue(mProjectDao.getAllProjects()).get(0);
        mProjectDao.delete(tartampion);
        List<Task> tasks = LiveDataTestUtils.getValue(mTaskDao.getAllTasks());
        assertEquals(0, tasks.size());

    }

}
