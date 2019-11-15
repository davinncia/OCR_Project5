package com.davinciapp.todoc.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.davinciapp.todoc.R;
import com.davinciapp.todoc.di.Injection;
import com.davinciapp.todoc.di.ViewModelFactory;
import com.davinciapp.todoc.ui.model.UiTaskModel;
import com.davinciapp.todoc.viewmodel.MainViewModel;

import java.util.List;

/**
 * <p>Home activity of the application which is displayed when the user opens the app.</p>
 * <p>Displays the list of tasks.</p>
 *
 * @author Gaëtan HERFRAY
 */
public class MainActivity extends AppCompatActivity implements TasksAdapter.DeleteTaskListener {

    /**
     * The adapter which handles the list of tasks
     */
    private final TasksAdapter adapter = new TasksAdapter(this);

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

        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this);
        taskViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel.class);

        taskViewModel.sortedUiTasks.observe(this, new Observer<List<UiTaskModel>>() {
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
            taskViewModel.setSortingType(SortingType.ALPHABETICAL);
        } else if (id == R.id.filter_alphabetical_inverted) {
            taskViewModel.setSortingType(SortingType.ALPHABETICAL_INVERTED);
        } else if (id == R.id.filter_oldest_first) {
            taskViewModel.setSortingType(SortingType.OLDEST_FIRST);
        } else if (id == R.id.filter_recent_first) {
            taskViewModel.setSortingType(SortingType.RECENT_FIRST);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDeleteTask(int taskId) {
        taskViewModel.deleteTaskById(taskId);
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
