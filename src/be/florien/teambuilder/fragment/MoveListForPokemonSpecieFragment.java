
package be.florien.teambuilder.fragment;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
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
import be.florien.teambuilder.adapter.MoveForPokemonSpecieAdapter;
import be.florien.teambuilder.fragment.MoveListFilterDialogFragment.MoveFilter;
import be.florien.teambuilder.fragment.MoveListFilterDialogFragment.MoveListFilter;
import be.florien.teambuilder.loader.PokemonMoveForPokemonListLoader;
import be.florien.teambuilder.model.Move;
import be.florien.teambuilder.model.Pokemon;
import be.florien.teambuilder.model.PokemonMoveForPokemon;

import java.util.List;

public class MoveListForPokemonSpecieFragment extends ListFragment implements LoaderCallbacks<List<PokemonMoveForPokemon>>, MoveListFilter {
    private static final String ARG_POKEMON_INIT = "ARG_POKEMON_INIT";
    private static final String ARG_POKEMON_NAME_INIT = "ARG_POKEMON_NAME_INIT";

    private final int LOADER_ID = 0;

    private FrameLayout mDetailContainer;
    private TextView mDetailInstruction;
    private TextView mEmpty;

    private boolean mShouldSetAdapter = true;

    private MoveForPokemonSpecieAdapter mAdapter = new MoveForPokemonSpecieAdapter();

    private MoveFilter mFilter;

    private Pokemon mPokemon;
    
    public static MoveListForPokemonSpecieFragment newInstance(Pokemon pokemon, String name){
        MoveListForPokemonSpecieFragment fragment = new MoveListForPokemonSpecieFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_POKEMON_INIT, pokemon);
        args.putString(ARG_POKEMON_NAME_INIT, name);
        fragment.setArguments(args);
        return fragment;
    }

    public MoveListForPokemonSpecieFragment() {
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
        if (getArguments() != null) {
            mPokemon = (Pokemon) getArguments().getParcelable(ARG_POKEMON_INIT);
            String name = getArguments().getString(ARG_POKEMON_NAME_INIT);
            getActivity().setTitle(getString(R.string.pokemon_specie_move_list_title,name));
        } else {
            getActivity().setTitle(R.string.move_list_title);
        }
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
        Move move = ((PokemonMoveForPokemon) l.getItemAtPosition(position)).moves;
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
    public Loader<List<PokemonMoveForPokemon>> onCreateLoader(int arg0, Bundle arg1) {
        mEmpty.setText(R.string.loading_move);
        return new PokemonMoveForPokemonListLoader(getActivity(), mFilter, mPokemon.id);
    }

    @Override
    public void onLoadFinished(Loader<List<PokemonMoveForPokemon>> loader, List<PokemonMoveForPokemon> moves) {
        if (mShouldSetAdapter) {
            setListAdapter(mAdapter);
            mShouldSetAdapter = false;
        }
        mAdapter.setItems(moves);
        mEmpty.setText(R.string.empty_move);
    }

    @Override
    public void onLoaderReset(Loader<List<PokemonMoveForPokemon>> loader) {
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
