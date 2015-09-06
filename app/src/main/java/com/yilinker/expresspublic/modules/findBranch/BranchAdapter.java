package com.yilinker.expresspublic.modules.findBranch;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.yilinker.expresspublic.R;
import com.yilinker.expresspublic.core.interfaces.BranchListener;
import com.yilinker.expresspublic.core.models.Branch;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Jeico.
 */
public class BranchAdapter extends RecyclerView.Adapter<BranchAdapter.BranchViewHolder>
{
    private static final Logger logger = Logger.getLogger(BranchAdapter.class.getSimpleName());

    private Context context;

    private LayoutInflater layoutInflater;

    private List<Branch> branchList;

    private BranchListener listener;

    public BranchAdapter(Context context, List<Branch> branchList, BranchListener listener) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.branchList = branchList;
        this.listener = listener;
    }

    @Override
    public BranchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.holder_branch, parent, false);
        return new BranchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BranchViewHolder holder, int position) {

        Branch branch = branchList.get(position);

        holder.tv_branchName.setText(branch.getName());
        holder.tv_branchAddress.setText(branch.getAddress());
    }

    @Override
    public int getItemCount() {
        return branchList.size();
    }

    class BranchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tv_branchName;
        public TextView tv_branchAddress;
        public Button btn_direction;
        public Button btn_call;

        public BranchViewHolder(View itemView) {
            super(itemView);

            tv_branchName = (TextView) itemView.findViewById(R.id.tv_branchName);
            tv_branchAddress = (TextView) itemView.findViewById(R.id.tv_branchAddress);
            btn_direction = (Button) itemView.findViewById(R.id.btn_direction);
            btn_call = (Button) itemView.findViewById(R.id.btn_call);

            tv_branchName.setOnClickListener(this);
            btn_direction.setOnClickListener(this);
            btn_call.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.tv_branchName:
                    listener.onBranchNameSelected(branchList.get(getAdapterPosition()));
                    break;

                case R.id.btn_direction:
                    listener.onBranchDirectionSelected(branchList.get(getAdapterPosition()));
                    break;

                case R.id.btn_call:
                    listener.onBranchContactNumberSelected(branchList.get(getAdapterPosition()));
                    break;

                default:
                    break;
            }
        }
    }
}
