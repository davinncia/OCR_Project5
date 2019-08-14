package com.cleanup.todoc.ui;

import android.content.res.ColorStateList;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cleanup.todoc.R;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.ui.model.UiTaskModel;

import java.util.List;

/**
 * <p>Adapter which handles the list of tasks to display in the dedicated RecyclerView.</p>
 *
 * @author Gaëtan HERFRAY
 */
public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskViewHolder> {
    /**
     * The list of tasks the adapter deals with
     */
    //private List<Task> mTasks;

    //private List<Project> mProjects;
    private List<UiTaskModel> mUiTaskModels;

    /**
     * The listener for when a task needs to be deleted
     */
    @NonNull
    private final DeleteTaskListener deleteTaskListener;

    /**
     * Instantiates a new TasksAdapter.
     *
     *
     */
    TasksAdapter(@NonNull final DeleteTaskListener deleteTaskListener) {
        this.deleteTaskListener = deleteTaskListener;
    }

    /**
     * Updates the list of tasks the adapter deals with.
     *
     * @param //tasks the list of tasks the adapter deals with to set
     */
    /*
    void updateTasks(@NonNull final List<Task> tasks, @NonNull final List<Project> projects) {
        this.mTasks = tasks;
        this.mProjects = projects;
        notifyDataSetChanged();
    }
    */
    void updateTasks(List<UiTaskModel> uiTaskModels){
        this.mUiTaskModels = uiTaskModels;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_task, viewGroup, false);
        return new TaskViewHolder(view, deleteTaskListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int position) {
        //taskViewHolder.bind(mTasks.get(position));
        taskViewHolder.lblTaskName.setText(mUiTaskModels.get(position).getTaskName());
        taskViewHolder.lblProjectName.setText(mUiTaskModels.get(position).getProjectName());
        taskViewHolder.imgProject.setSupportImageTintList(ColorStateList.valueOf(mUiTaskModels.get(position).getProjectColor()));
        taskViewHolder.imgDelete.setTag(mUiTaskModels.get(position).getTaskId());

    }

    @Override
    public int getItemCount() {

        //return mTasks != null? mTasks.size() : 0;
        return mUiTaskModels != null? mUiTaskModels.size() : 0;
    }



    /**
     * Listener for deleting tasks
     */
    public interface DeleteTaskListener {
        /**
         * Called when a task needs to be deleted.
         *
         * @param taskId the id of the task that needs to be deleted
         */
        void onDeleteTask(int taskId);
    }

    /**
     * <p>ViewHolder for task items in the tasks list</p>
     *
     * @author Gaëtan HERFRAY
     */
    class TaskViewHolder extends RecyclerView.ViewHolder {
        /**
         * The circle icon showing the color of the project
         */
        private final AppCompatImageView imgProject;

        /**
         * The TextView displaying the name of the task
         */
        private final TextView lblTaskName;

        /**
         * The TextView displaying the name of the project
         */
        private final TextView lblProjectName;

        /**
         * The deleteTaskById icon
         */
        private final AppCompatImageView imgDelete;

        /**
         * The listener for when a task needs to be deleted
         */
        private final DeleteTaskListener deleteTaskListener;

        /**
         * Instantiates a new TaskViewHolder.
         *
         * @param itemView the view of the task item
         * @param deleteTaskListener the listener for when a task needs to be deleted to set
         */
        TaskViewHolder(@NonNull View itemView, @NonNull DeleteTaskListener deleteTaskListener) {
            super(itemView);

            this.deleteTaskListener = deleteTaskListener;

            imgProject = itemView.findViewById(R.id.img_project);
            lblTaskName = itemView.findViewById(R.id.lbl_task_name);
            lblProjectName = itemView.findViewById(R.id.lbl_project_name);
            imgDelete = itemView.findViewById(R.id.img_delete);

            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final int tag = (int) view.getTag();
                    //if (tag instanceof Task) {
                    //    TaskViewHolder.this.deleteTaskListener.onDeleteTask((Task) tag);
                    //}

                    TaskViewHolder.this.deleteTaskListener.onDeleteTask(tag);


                    }
            });
        }

        /**
         * Binds a task to the item view.
         *
         * @param task the task to bind in the item view
         */
        /*
        void bind(Task task) {
            lblTaskName.setText(task.getName());
            imgDelete.setTag(task);

            //TODO 12: get project by id

            final Project taskProject = getCorrespondingProject(task, mProjects);

            if (taskProject != null) {
                imgProject.setSupportImageTintList(ColorStateList.valueOf(taskProject.getColor()));
                lblProjectName.setText(taskProject.getName());
            } else {
                imgProject.setVisibility(View.INVISIBLE);
                lblProjectName.setText("");
            }

        }

        private Project getCorrespondingProject(Task task, List<Project> projects){
            for (Project project : projects){
                if (task.getProjectId() == project.getId()){
                    return project;
                }
            }
            return null;
        }
        */
    }
}
