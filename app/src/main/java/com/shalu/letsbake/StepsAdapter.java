package com.shalu.letsbake;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.shalu.letsbake.utils.BakingJsonUtils.results;

/**
 * Created by sarum on 12/2/2017.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder> {

    String mStepNames[];
    StepsAdapter.OnStepClickListener mCallback;

    StepsAdapter(Context context) {
        mCallback =(StepsAdapter.OnStepClickListener) context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutId = R.layout.step;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutId,parent,shouldAttachToParentImmediately);
        StepsAdapter.ViewHolder viewHolder = new StepsAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mShortDescription.setText(mStepNames[position]);
    }

    @Override
    public int getItemCount() {
        if(mStepNames!=null)
            return mStepNames.length;
        else
            return 0;
    }

    public interface OnStepClickListener{
        void onStepClicked(int position);
    }


    public void setmStepNames(String[] steps) {
        mStepNames = steps;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_short)
        TextView mShortDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mCallback.onStepClicked(getAdapterPosition());
        }
    }
}
