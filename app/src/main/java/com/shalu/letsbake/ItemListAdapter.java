package com.shalu.letsbake;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.shalu.letsbake.utils.NetworkUtils;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sarum on 11/12/2017.
 */

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemViewHolder> {

    String mRecipeNames[];
    OnRecipeClickListener mCallback;

    public interface OnRecipeClickListener {
        void onRecipeClicked(int position);
    }

    ItemListAdapter(Context context) {
        mCallback =(OnRecipeClickListener) context;
    }
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutId = R.layout.recipie_card;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutId,parent,shouldAttachToParentImmediately);
        ItemViewHolder viewHolder = new ItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        URL url = NetworkUtils.buildUrl();
        holder.mRecipieName.setText(mRecipeNames[position]);
        switch(position) {
            case 0: {holder.mImg.setImageResource(R.drawable.nutella_pie);break;}
            case 1: {holder.mImg.setImageResource(R.drawable.brownies);break;}
            case 2: {holder.mImg.setImageResource(R.drawable.yellow_cake1);break;}
            case 3: {holder.mImg.setImageResource(R.drawable.cheese_cake);break;}
        }

    }

    @Override
    public int getItemCount() {
        if(mRecipeNames!=null)
            return mRecipeNames.length;
        else
            return 0;
    }

    public void setmRecipeNames(String[] names) {
        mRecipeNames = names;
        notifyDataSetChanged();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_name) TextView mRecipieName;
        @BindView(R.id.iv_image) ImageView mImg;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mCallback.onRecipeClicked(getAdapterPosition());
        }
    }
}
