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