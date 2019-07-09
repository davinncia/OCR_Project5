package com.cleanup.todoc.data.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class AppSortingPreferences implements SortingPreferences {

    public static final String SHARED_PREF_FILE = "SHARED_PREF_SORT";
    public static final String SHARED_PREF_KEY = "SHARED_PREF_SORTING_METHOD";

    private SharedPreferences sharedPreferences;

    @Override
    public void saveSortingMethod(Context context, String sortingMethod) {

        if (sharedPreferences == null){
            sharedPreferences = context.getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE);
        }

        sharedPreferences.edit().putString(SHARED_PREF_KEY, sortingMethod).apply();
    }

    @Override
    public String getSortingMethod(Context context) {
        if (sharedPreferences == null){
            sharedPreferences = context.getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE);
        }

        return sharedPreferences.getString(SHARED_PREF_KEY, "NONE");
    }
}
