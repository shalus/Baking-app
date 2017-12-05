package com.shalu.letsbake;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shalu.letsbake.idlingResource.SimpleIdlingResource;
import com.shalu.letsbake.utils.BakingJsonUtils;
import com.shalu.letsbake.utils.NetworkUtils;

import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;;
import static com.shalu.letsbake.utils.BakingJsonUtils.results;

/**
 * Created by sarum on 11/12/2017.
 */

public class MainListFragment extends Fragment {



    private static final int BAKE_LOADER_ID = 14;
   /* static class RecipeMain {
        static String[] recipeNames;
        static String[] recipeImg;
    }*/
    @BindView(R.id.rv_items) RecyclerView itemRecyclerView;
    @BindView(R.id.tv_error_message_display) TextView mErrorText;
    @BindView(R.id.pb_loading_indicator) ProgressBar mProgressbar;
    ItemListAdapter mAdapter;
    private Unbinder unbinder;
    SimpleIdlingResource idlingResource;


    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
          ItemListAdapter.OnRecipeClickListener mCallback = (ItemListAdapter.OnRecipeClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnRecipeClickListener");
        }
    }

    public MainListFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_list, container);
        unbinder = ButterKnife.bind(this,view);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), numberOfColumns());
        itemRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new ItemListAdapter(getContext());
        itemRecyclerView.setAdapter(mAdapter);
        Loader<Object> loader = getLoaderManager().getLoader(BAKE_LOADER_ID);
        if(loader == null)
            getLoaderManager().initLoader(BAKE_LOADER_ID,null,recipeLoader);
        else
            getLoaderManager().restartLoader(BAKE_LOADER_ID,null,recipeLoader);
        MainActivity activity = (MainActivity) getActivity();
        idlingResource = (SimpleIdlingResource) activity.getIdlingResource();

        return view;
    }

    private int numberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int widthDivider = 400;
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        if(nColumns < 1) return 1;
        return nColumns;
    }
    LoaderManager.LoaderCallbacks<Void> recipeLoader = new LoaderManager.LoaderCallbacks<Void>() {

        @Override
        public Loader<Void> onCreateLoader(final int id, final Bundle args) {

            return new AsyncTaskLoader<Void>(getContext()) {
                @Override
                protected void onStartLoading() {

                    if(id == BAKE_LOADER_ID) {
                        if (results!= null)
                            deliverResult(null);
                        else
                            forceLoad();
                    }
                    if ( idlingResource != null) {
                        idlingResource.setIdleState(false);
                    }
                    mProgressbar.setVisibility(View.VISIBLE);
                }

                @Override
                public Void loadInBackground() {
                  try {
                  if(results==null) {
                        URL requestUrl = NetworkUtils.buildUrl();
                        MainActivity.jsonResponse = NetworkUtils
                                .getResponseFromHttpUrl(requestUrl);
                        BakingJsonUtils
                                .extractDetailsFromJson(MainActivity.jsonResponse);
                  }
                //recipeNames = new String[results.length];
                  //recipeImg = new String[results.length]

                /*for(int i = 0; i < results.length; i++) {
                    recipeNames[i] = results[i].name;
                    recipeImg[i] = results[i].image;
                    Log.d("Main", recipeNames[i]);
                }*/

                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                    return null;
                }
                @Override
                public void deliverResult(Void v) {
                    //recipeNames = data;
                    super.deliverResult(null);
                }
            };
        }

        @Override
        public void onLoadFinished(Loader<Void> loader, Void v) {
            mProgressbar.setVisibility(View.INVISIBLE);
            if (idlingResource != null) {
                idlingResource.setIdleState(true);
            }
            if (results != null) {
                showRecipesView();
                mAdapter.setmRecipeDetails();

            } else {
                showErrorMessage();
            }
        }

        @Override
        public void onLoaderReset(Loader<Void> loader) {

        }
    };
    private void showRecipesView() {
        itemRecyclerView.setVisibility(View.VISIBLE);
        mErrorText.setVisibility(View.INVISIBLE);


    }
    private void showErrorMessage(){
        itemRecyclerView.setVisibility(View.INVISIBLE);
        mErrorText.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
