package com.yilinker.expresspublic.modules.findBranch;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.yilinker.expresspublic.R;
import com.yilinker.expresspublic.core.interfaces.SearchHistoryListener;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Jeico.
 */
public class SearchHistoryAdapter extends RecyclerView.Adapter<SearchHistoryAdapter.SearchHistoryViewHolder>
{
    private static final Logger logger = Logger.getLogger(SearchHistoryAdapter.class.getSimpleName());

    private Context context;

    private LayoutInflater layoutInflater;

    private List<String> searchHistoryList;

    private SearchHistoryListener listener;

    public SearchHistoryAdapter(Context context, List<String> searchHistoryList, SearchHistoryListener listener)
    {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.searchHistoryList = searchHistoryList;
        this.listener = listener;
    }


    @Override
    public SearchHistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.holder_search_history, parent, false);
        return new SearchHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchHistoryViewHolder holder, int position) {

        holder.tv_searchHistoryItem.setText(searchHistoryList.get(position));
    }

    @Override
    public int getItemCount() {
        return searchHistoryList.size();
    }


    class SearchHistoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView tv_searchHistoryItem;

        public SearchHistoryViewHolder(View itemView) {
            super(itemView);

            tv_searchHistoryItem = (TextView) itemView.findViewById(R.id.tv_searchHistoryItem);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onSearchHistorySelected(searchHistoryList.get(getAdapterPosition()));
        }
    }
}
