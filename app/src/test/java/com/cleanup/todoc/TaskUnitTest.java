package com.cleanup.todoc;

import android.widget.ArrayAdapter;

import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.ui.model.UiTaskModel;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

/**
 * Unit tests for tasks
 *
 * @author Gaëtan HERFRAY
 */
public class TaskUnitTest {

    @Test
    public void test_projects() {
        //TODO: not testable here as we got to access db now
        final Task task1 = new Task(1, "task1", new Date().getTime());
        final Task task2 = new Task(2, "task2", new Date().getTime());
        final Task task3 = new Task(3, "task3", new Date().getTime());
        final Task task4 = new Task(4, "task4", new Date().getTime());

       // assertEquals("Projet Tartampion", task1.getProject().getName());
       // assertEquals("Projet Lucidia", task2.getProject().getName());
       // assertEquals("Projet Circus", task3.getProject().getName());
       // assertNull(task4.getProject());
    }

    @Test
    public void test_az_comparator() {
        final UiTaskModel uiTask1 = new UiTaskModel(1, 1, "aaa", "Tart", 123);
        final UiTaskModel uiTask2 = new UiTaskModel(1, 2, "bbb", "Circ", 124);
        final UiTaskModel uiTask3 = new UiTaskModel(1, 3, "ccc", "Luci", 125);

        final ArrayList<UiTaskModel> uiTasks = new ArrayList<>();
        uiTasks.add(uiTask1);
        uiTasks.add(uiTask2);
        uiTasks.add(uiTask3);
        Collections.sort(uiTasks, new UiTaskModel.UiTaskAZComparator());

        assertSame(uiTask1, uiTasks.get(0));
        assertSame(uiTask2, uiTasks.get(1));
        assertSame(uiTask3, uiTasks.get(2));
    }

    @Test
    public void test_za_comparator() {
        final UiTaskModel uiTask1 = new UiTaskModel(1, 1, "aaa", "Tart", 123);
        final UiTaskModel uiTask2 = new UiTaskModel(1, 2, "bbb", "Circ", 124);
        final UiTaskModel uiTask3 = new UiTaskModel(1, 3, "ccc", "Luci", 125);

        final ArrayList<UiTaskModel> uiTasks = new ArrayList<>();
        uiTasks.add(uiTask1);
        uiTasks.add(uiTask2);
        uiTasks.add(uiTask3);
        Collections.sort(uiTasks, new UiTaskModel.UiTaskZAComparator());

        assertSame(uiTask1, uiTasks.get(2));
        assertSame(uiTask2, uiTasks.get(1));
        assertSame(uiTask3, uiTasks.get(0));
    }

    @Test
    public void test_recent_comparator() {
        final UiTaskModel uiTask1 = new UiTaskModel(1, 1, "aaa", "Tart", 123);
        final UiTaskModel uiTask2 = new UiTaskModel(1, 2, "bbb", "Circ", 124);
        final UiTaskModel uiTask3 = new UiTaskModel(1, 3, "ccc", "Luci", 125);

        final ArrayList<UiTaskModel> uiTasks = new ArrayList<>();
        uiTasks.add(uiTask1);
        uiTasks.add(uiTask2);
        uiTasks.add(uiTask3);
        Collections.sort(uiTasks, new UiTaskModel.UiTaskRecentComparator());

        assertSame(uiTask1, uiTasks.get(2));
        assertSame(uiTask2, uiTasks.get(1));
        assertSame(uiTask3, uiTasks.get(0));
    }

    @Test
    public void test_old_comparator() {
        final UiTaskModel uiTask1 = new UiTaskModel(1, 1, "aaa", "Tart", 123);
        final UiTaskModel uiTask2 = new UiTaskModel(1, 2, "bbb", "Circ", 124);
        final UiTaskModel uiTask3 = new UiTaskModel(1, 3, "ccc", "Luci", 125);

        final ArrayList<UiTaskModel> uiTasks = new ArrayList<>();
        uiTasks.add(uiTask1);
        uiTasks.add(uiTask2);
        uiTasks.add(uiTask3);
        Collections.sort(uiTasks, new UiTaskModel.UiTaskOldComparator());

        assertSame(uiTask1, uiTasks.get(0));
        assertSame(uiTask2, uiTasks.get(1));
        assertSame(uiTask3, uiTasks.get(2));
    }
}