package com.cleanup.todoc.data.preferences;

import android.content.Context;

public interface SortingPreferences {

    void saveSortingMethod(Context context, String sortingMethod);

    String getSortingMethod(Context context);
}
