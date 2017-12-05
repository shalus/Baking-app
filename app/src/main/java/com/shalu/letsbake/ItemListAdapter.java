package com.shalu.letsbake;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.shalu.letsbake.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.shalu.letsbake.utils.BakingJsonUtils.results;

/**
 * Created by sarum on 11/12/2017.
 */

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemViewHolder> {

    String mRecipeNames[];
    String mImg[];
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
        if(mImg[position]==null || mImg[position].equals("")) {
            switch (position) {
                case 0: {
                    holder.mImg.setImageResource(R.drawable.nutella_pie);
                    break;
                }
                case 1: {
                    holder.mImg.setImageResource(R.drawable.brownies);
                    break;
                }
                case 2: {
                    holder.mImg.setImageResource(R.drawable.yellow_cake1);
                    break;
                }
                case 3: {
                    holder.mImg.setImageResource(R.drawable.cheese_cake);
                    break;
                }
            }
        }
        else {
            Picasso.with(holder.mImg.getContext())
                    .load(mImg[position].toString())
                    .into(holder.mImg);
        }


    }

    @Override
    public int getItemCount() {
        if(mRecipeNames!=null)
            return mRecipeNames.length;
        else
            return 0;
    }

    public void setmRecipeDetails() {
        mRecipeNames = new String[results.length];
        mImg = new String[results.length];
        for(int i = 0; i < results.length; i++) {
            mRecipeNames[i] = results[i].name;
            mImg[i] = results[i].image;
        }
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
