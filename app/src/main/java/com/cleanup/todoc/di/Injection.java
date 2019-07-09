package com.cleanup.todoc.di;

import com.cleanup.todoc.data.preferences.AppSortingPreferences;
import com.cleanup.todoc.data.preferences.SortingPreferences;

public class Injection {

    private static SortingPreferences preferences = new AppSortingPreferences();

    public static SortingPreferences getSortingPreferences(){
        return preferences;
    }

}
