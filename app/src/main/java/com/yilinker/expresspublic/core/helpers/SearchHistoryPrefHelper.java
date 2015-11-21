package com.yilinker.expresspublic.core.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Created by Jeico.
 */
public class SearchHistoryPrefHelper
{
    private static final Logger logger = Logger.getLogger(SearchHistoryPrefHelper.class.getSimpleName());

    private static final String PREF_SEARCH_HISTORY                     = "pref_search_history";
    private static final String HISTORY                                 = "history";

    /**
     * TODO
     * @param context
     * @return
     */
    public static List<String> getHistory(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_SEARCH_HISTORY, Context.MODE_PRIVATE);
        //Retrieve the values
        Set<String> history = sharedPreferences.getStringSet(HISTORY, new HashSet<String>());

        List<String> historyArr = new ArrayList<>();

        Iterator it = history.iterator();
        while(it.hasNext())
        {
            historyArr.add(it.next().toString());
        }

        return historyArr;
    }

    /**
     * TODO
     * @param context
     * @param keyword
     */
    public static void addKeywordToHistory(Context context, String keyword)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_SEARCH_HISTORY, Context.MODE_PRIVATE);
        //Retrieve the values
        Set<String> history = sharedPreferences.getStringSet(HISTORY, new HashSet<String>());

        if (history.contains(keyword)) {
            return;
        }
        else
        {
            logger.severe("add to history: " + keyword);
            // Add to history
            history.add(keyword);

            // Save history
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putStringSet(HISTORY, history);
            editor.apply();
        }
    }
}
