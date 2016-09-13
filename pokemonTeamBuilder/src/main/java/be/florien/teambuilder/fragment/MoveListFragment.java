
package be.florien.teambuilder.fragment;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import be.florien.teambuilder.R;
import be.florien.teambuilder.activity.MoveDetailActivity;
import be.florien.teambuilder.adapter.MoveAdapter;
import be.florien.teambuilder.fragment.MoveListFilterDialogFragment.MoveFilter;
import be.florien.teambuilder.fragment.MoveListFilterDialogFragment.MoveListFilter;
import be.florien.teambuilder.loader.MoveListLoader;
import be.florien.teambuilder.model.Move;

import java.util.List;

public class MoveListFragment extends ListFragment implements LoaderCallbacks<List<Move>>, MoveListFilter {

    private final int LOADER_ID = 0;

    private FrameLayout mDetailContainer;
    private TextView mDetailInstruction;
    private TextView mEmpty;
    
    private Handler mHandler = new Handler();
    private BroadcastReceiver mConfigChangeReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            mHandler.post(new Runnable() {

                @Override
                public void run() {
                    getLoaderManager().restartLoader(LOADER_ID, null, MoveListFragment.this);
                }
            });
        }
    };

    private boolean mShouldSetAdapter = true;

    private MoveAdapter mAdapter = new MoveAdapter();

    private MoveFilter mFilter;

    public MoveListFragment() {
        super();
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        mDetailContainer = (FrameLayout) view.findViewById(R.id.detail);
        mDetailInstruction = (TextView) view.findViewById(R.id.instruction);
        mEmpty = (TextView) view.findViewById(android.R.id.empty);
        mEmpty.setText(R.string.loading_move);
        return view;
    }

    @SuppressLint("NewApi")
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.move_list_title);
        
        getActivity().registerReceiver(mConfigChangeReceiver, new IntentFilter(PreferenceFragment.REFRESH));
        
        Fragment fragment = getChildFragmentManager().findFragmentByTag(MoveDetailFragment.class.getName());
        if (mDetailInstruction != null && fragment == null) {
            mDetailInstruction.setText(R.string.choose_a_move);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getListView().setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                    switch (scrollState) {
                        case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                            getListView().setFastScrollAlwaysVisible(false);
                            break;
                        case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                            getListView().setFastScrollAlwaysVisible(true);
                            break;
                        default:
                            break;
                    }
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                }

            });
        }
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Move move = (Move) l.getItemAtPosition(position);
        if (mDetailContainer == null) {
            startActivity(MoveDetailActivity.newIntent(getActivity(), move));
        } else {
            mDetailInstruction.setVisibility(View.GONE);
            MoveDetailFragment fragment = (MoveDetailFragment) getChildFragmentManager().findFragmentByTag(MoveDetailFragment.class.getName());
            if (fragment == null) {
                fragment = MoveDetailFragment.newInstance(move);
                getChildFragmentManager().beginTransaction().replace(R.id.detail, fragment, MoveDetailFragment.class.getName()).commit();
            } else {
                fragment.changeMove(move);
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        // Inflate the menu items for use in the action bar
        inflater.inflate(R.menu.move_list_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String tagDialogFragment = MoveListFilterDialogFragment.class.getName();
        MoveListFilterDialogFragment dialogFragment = (MoveListFilterDialogFragment) getChildFragmentManager().findFragmentByTag(tagDialogFragment);
        if (dialogFragment == null) {
            dialogFragment = MoveListFilterDialogFragment.newInstance();
        }
        dialogFragment.show(getChildFragmentManager(), tagDialogFragment);
        return true;
    }

    @Override
    public Loader<List<Move>> onCreateLoader(int arg0, Bundle arg1) {
        mEmpty.setText(R.string.loading_move);
        mAdapter.setItems(null);
        return new MoveListLoader(getActivity(), mFilter);
    }

    @Override
    public void onLoadFinished(Loader<List<Move>> loader, List<Move> moves) {
        if (mShouldSetAdapter) {
            setListAdapter(mAdapter);
            mShouldSetAdapter = false;
        }
        mAdapter.setItems(moves);
        mEmpty.setText(R.string.empty_move);
    }

    @Override
    public void onLoaderReset(Loader<List<Move>> loader) {
    }

    @Override
    public void setFilter(MoveFilter filter) {
        if (!filter.equals(mFilter)) {
            mShouldSetAdapter = true;
            mFilter = filter;
            getLoaderManager().restartLoader(LOADER_ID, null, this);
        }
    }

    @Override
    public MoveFilter getFilter() {
        return mFilter;
    }

}
