package com.davinciapp.todoc.ui.model;

import androidx.annotation.ColorInt;

import java.util.Comparator;

public class UiTaskModel {

    @ColorInt
    private int mProjectColor;

    private String mTaskName;

    private int mTaskId;

    private String mProjectName;

    private long mCreationTimestamp;

    public UiTaskModel(int projectColor, int taskId, String taskName, String projectName, long creationTimestamp) {
        mProjectColor = projectColor;
        mTaskId = taskId;
        mTaskName = taskName;
        mProjectName = projectName;
        mCreationTimestamp = creationTimestamp;
    }

    public int getProjectColor() {
        return mProjectColor;
    }

    public String getTaskName() {
        return mTaskName;
    }

    public String getProjectName() {
        return mProjectName;
    }

    public int getTaskId() {
        return mTaskId;
    }


    public static class UiTaskAZComparator implements Comparator<UiTaskModel> {
        @Override
        public int compare(UiTaskModel left, UiTaskModel right) {
            return left.mTaskName.compareTo(right.mTaskName);
        }
    }

    /**
     * Comparator to sort task from Z to A
     */
    public static class UiTaskZAComparator implements Comparator<UiTaskModel> {
        @Override
        public int compare(UiTaskModel left, UiTaskModel right) {
            return right.mTaskName.compareTo(left.mTaskName);
        }
    }

    /**
     * Comparator to sort task from last created to first created
     */
    public static class UiTaskRecentComparator implements Comparator<UiTaskModel> {
        @Override
        public int compare(UiTaskModel left, UiTaskModel right) {
            return (int) (right.mCreationTimestamp - left.mCreationTimestamp);
        }
    }

    /**
     * Comparator to sort task from first created to last created
     */
    public static class UiTaskOldComparator implements Comparator<UiTaskModel> {
        @Override
        public int compare(UiTaskModel left, UiTaskModel right) {
            return (int) (left.mCreationTimestamp - right.mCreationTimestamp);
        }
    }
}
