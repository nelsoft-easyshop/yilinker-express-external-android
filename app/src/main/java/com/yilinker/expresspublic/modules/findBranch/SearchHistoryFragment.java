package com.yilinker.expresspublic.modules.findBranch;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yilinker.expresspublic.R;
import com.yilinker.expresspublic.core.contants.BundleKey;
import com.yilinker.expresspublic.core.contants.RequestCode;
import com.yilinker.expresspublic.core.helpers.SearchHistoryPrefHelper;
import com.yilinker.expresspublic.core.interfaces.SearchHistoryListener;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Jeico.
 */
public class SearchHistoryFragment extends Fragment implements SearchHistoryListener {
    private static final Logger logger = Logger.getLogger(SearchHistoryFragment.class.getSimpleName());

    public static SearchHistoryFragment newInstance()
    {
        return new SearchHistoryFragment();
    }

    private List<String> searchHistoryList;

    private SearchHistoryAdapter searchHistoryAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_history, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchHistoryList = new ArrayList<>();

        searchHistoryAdapter = new SearchHistoryAdapter(getActivity(), searchHistoryList, this);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_searchHistory);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(searchHistoryAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        refreshList();
    }

    public void refreshList()
    {
        if(searchHistoryList != null)
        {
            searchHistoryList.clear();

            List<String> tempList =  SearchHistoryPrefHelper.getHistory(getActivity());

            for (String temp : tempList)
            {
                searchHistoryList.add(temp);
            }

            searchHistoryAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onSearchHistorySelected(String keyword) {
        Bundle bundle = new Bundle();
        bundle.putLong(BundleKey.CITY_ID, 0);
        bundle.putString(BundleKey.KEYWORD, keyword);

        Intent intent = new Intent(getActivity(), SelectAreaBranchActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
