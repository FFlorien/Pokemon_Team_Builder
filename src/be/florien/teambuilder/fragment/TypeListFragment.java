
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
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import be.florien.teambuilder.R;
import be.florien.teambuilder.activity.TypeDetailActivity;
import be.florien.teambuilder.adapter.TypeAdapter;
import be.florien.teambuilder.loader.TypeListLoader;
import be.florien.teambuilder.model.Type;

import java.util.List;

public class TypeListFragment extends ListFragment implements LoaderCallbacks<List<Type>>{

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
                    getLoaderManager().restartLoader(LOADER_ID, null, TypeListFragment.this);
                }
            });
        }
    };

    private boolean mShouldSetAdapter = true;

    private TypeAdapter mAdapter = new TypeAdapter();

    public static TypeListFragment newInstance() {
        TypeListFragment fragment = new TypeListFragment();
        return fragment;
    }

    public TypeListFragment() {
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
        getActivity().setTitle(R.string.type_list_title);
        
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
        Type type = (Type) l.getItemAtPosition(position);
        if (mDetailContainer == null) {
            startActivity(TypeDetailActivity.newIntent(getActivity(), type));
        } else {
            mDetailInstruction.setVisibility(View.GONE);
            TypeDetailFragment fragment = (TypeDetailFragment) getChildFragmentManager().findFragmentByTag(TypeDetailFragment.class.getName());
            if (fragment == null) {
                fragment = TypeDetailFragment.newInstance(type);
                getChildFragmentManager().beginTransaction().replace(R.id.detail, fragment, TypeDetailFragment.class.getName()).commit();
            } else {
                fragment.changeType(type);
            }
        }
    }

    @Override
    public Loader<List<Type>> onCreateLoader(int arg0, Bundle arg1) {
        mEmpty.setText(R.string.loading_move);
        mAdapter.setItems(null);
        return new TypeListLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<Type>> loader, List<Type> types) {
        if (mShouldSetAdapter) {
            setListAdapter(mAdapter);
            mShouldSetAdapter = false;
        }
        mAdapter.setItems(types);
        mEmpty.setText(R.string.empty_type);
    }

    @Override
    public void onLoaderReset(Loader<List<Type>> loader) {
    }

}
