package com.cleanup.todoc.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cleanup.todoc.R;
import com.cleanup.todoc.di.Injection;
import com.cleanup.todoc.di.ViewModelFactory;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.ui.model.UiTaskModel;
import com.cleanup.todoc.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Home activity of the application which is displayed when the user opens the app.</p>
 * <p>Displays the list of tasks.</p>
 *
 * @author Gaëtan HERFRAY
 */
public class MainActivity extends AppCompatActivity implements TasksAdapter.DeleteTaskListener {
    /**
     * List of all projects available in the application
     */
    //TODO 0: get projects from project_table
    private List<Project> allProjects = new ArrayList<>();

    /**
     * The adapter which handles the list of tasks
     */
    private final TasksAdapter adapter = new TasksAdapter(this);

    /**
     * Dialog to create a new task
     */
    @Nullable
    public AlertDialog dialog = null;

    /**
     * EditText that allows user to set the name of a task
     */
    @Nullable
    private EditText dialogEditText = null;

    /**
     * Spinner that allows the user to associate a project to a task
     */
    @Nullable
    private Spinner dialogSpinner = null;

    /**
     * The RecyclerView which displays the list of tasks
     */
    // Suppress warning is safe because variable is initialized in onCreate
    @SuppressWarnings("NullableProblems")
    @NonNull
    private RecyclerView listTasks;

    /**
     * The TextView displaying the empty state
     */
    // Suppress warning is safe because variable is initialized in onCreate
    @SuppressWarnings("NullableProblems")
    @NonNull
    private TextView lblNoTasks;

    private MainViewModel taskViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        listTasks = findViewById(R.id.list_tasks);
        lblNoTasks = findViewById(R.id.lbl_no_task);



        listTasks.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        listTasks.setAdapter(adapter);

        findViewById(R.id.fab_add_task).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddTaskDialog();
            }
        });

        //TODO 1: set an observer of our viewmodel that refresh the adapter's data
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this);
        taskViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel.class);
/*
        taskViewModel.getAllProjects().observe(this, new Observer<List<Project>>() {
            @Override
            public void onChanged(List<Project> projects) {
                allProjects = projects;
            }
        });

        taskViewModel.tasksLiveData.observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                //TODO 4: inform the adapter that data changed and actualize UI
                if (tasks == null || tasks.size() == 0){
                    lblNoTasks.setVisibility(View.VISIBLE);
                    listTasks.setVisibility(View.GONE);
                } else {
                    lblNoTasks.setVisibility(View.GONE);
                    listTasks.setVisibility(View.VISIBLE);
                    adapter.updateTasks(tasks, allProjects);
                }

            }
        });
*/

        taskViewModel.sortedTasks.observe(this, new Observer<List<UiTaskModel>>() {
            @Override
            public void onChanged(List<UiTaskModel> uiTaskModels) {
                if (uiTaskModels == null || uiTaskModels.size() == 0){
                    lblNoTasks.setVisibility(View.VISIBLE);
                    listTasks.setVisibility(View.GONE);
                } else {
                    lblNoTasks.setVisibility(View.GONE);
                    listTasks.setVisibility(View.VISIBLE);
                    adapter.updateTasks(uiTaskModels);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.filter_alphabetical) {
            //sortMethod = getString(R.string.ALPHABETICAL);
            taskViewModel.setSortingType(SortingType.ALPHABETICAL);
        } else if (id == R.id.filter_alphabetical_inverted) {
            //sortMethod = getString(R.string.ALPHABETICAL_INVERTED);
            taskViewModel.setSortingType(SortingType.ALPHABETICAL_INVERTED);
        } else if (id == R.id.filter_oldest_first) {
            //sortMethod = getString(R.string.OLD_FIRST);
            taskViewModel.setSortingType(SortingType.OLDEST_FIRST);
        } else if (id == R.id.filter_recent_first) {
            //sortMethod = getString(R.string.RECENT_FIRST);
            taskViewModel.setSortingType(SortingType.RECENT_FIRST);
        }

        //updateTasks();

        return super.onOptionsItemSelected(item);
    }

    //TODO Nino: I had to delete by id now as Task object is not accessible anymore
    @Override
    public void onDeleteTask(int taskId) {
        //TODO 3: deleteTaskById in database
        taskViewModel.deleteTaskById(taskId);
        //tasks.remove(task);
        //updateTasks();
    }


    //------------//
    //ALERT DIALOG//
    //------------//
    /**
     * Shows the DialogFragment for adding a Task
     */
    private void showAddTaskDialog() {

        FragmentManager fm = getSupportFragmentManager();
        AddTaskDialogFragment dialog = AddTaskDialogFragment.newInstance();
        dialog.show(fm, "");
    }


    public enum SortingType {
        ALPHABETICAL,
        ALPHABETICAL_INVERTED,
        OLDEST_FIRST,
        RECENT_FIRST,
        NONE
    }
}
