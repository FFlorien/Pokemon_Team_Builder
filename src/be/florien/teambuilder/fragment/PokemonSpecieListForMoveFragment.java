
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
import be.florien.teambuilder.activity.PokemonSpecieDetailActivity;
import be.florien.teambuilder.adapter.PokemonSpecieForMoveAdapter;
import be.florien.teambuilder.fragment.PokemonSpecieListFilterDialogFragment.SpecieFilter;
import be.florien.teambuilder.fragment.PokemonSpecieListFilterDialogFragment.SpecieListFilter;
import be.florien.teambuilder.loader.PokemonSpecieForMoveListLoader;
import be.florien.teambuilder.loader.TypeListLoader;
import be.florien.teambuilder.model.Move;
import be.florien.teambuilder.model.PokemonSpecie;
import be.florien.teambuilder.model.Type;

import java.util.List;

public class PokemonSpecieListForMoveFragment extends ListFragment implements SpecieListFilter {
    private static final String ARG_MOVE_INIT = "ARG_MOVE_INIT";
    private final int LOADER_ID_SPECIES = 10;
    private final int LOADER_ID_TYPES = 11;

    private boolean mShouldResetScroll = false;

    private FrameLayout mDetailContainer;
    private TextView mDetailInstruction;
    private TextView mEmpty;

    private PokemonSpecieForMoveAdapter mAdapter = new PokemonSpecieForMoveAdapter();

    private SpecieFilter mFilter;
    private Move mMove;

    public static PokemonSpecieListForMoveFragment newInstance(Move move) {
        PokemonSpecieListForMoveFragment fragment = new PokemonSpecieListForMoveFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_MOVE_INIT, move);
        fragment.setArguments(args);
        return fragment;
    }

    public PokemonSpecieListForMoveFragment() {
        super();
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        mEmpty = (TextView) view.findViewById(android.R.id.empty);
        mDetailContainer = (FrameLayout) view.findViewById(R.id.detail);
        mDetailInstruction = (TextView) view.findViewById(R.id.instruction);
        mEmpty.setText(R.string.loading_pokemons);
        return view;
    }

    @SuppressLint("NewApi")
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            mMove = (Move) getArguments().getParcelable(ARG_MOVE_INIT);
            getActivity().setTitle(getString(R.string.move_pokemon_specie_list_title, mMove.move_names.first));
        } else {
            getActivity().setTitle(R.string.pokemon_specie_list_title);
        }

        Fragment fragment = getChildFragmentManager().findFragmentByTag(PokemonSpecieDetailFragment.class.getName());
        if (mDetailInstruction != null && fragment == null) {
            mDetailInstruction.setText(R.string.choose_a_specie);
        }
        setListAdapter(mAdapter);
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
        getLoaderManager().initLoader(LOADER_ID_SPECIES, null, mPokemonListCallback);
        getLoaderManager().initLoader(LOADER_ID_TYPES, null, mTypeListCallback);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        PokemonSpecie pokemonSpecie = (PokemonSpecie) l.getItemAtPosition(position);
        if (mDetailContainer == null) {
            startActivity(PokemonSpecieDetailActivity.newIntent(getActivity(), pokemonSpecie));
        } else {
            mDetailInstruction.setVisibility(View.GONE);
            PokemonSpecieDetailFragment fragment = (PokemonSpecieDetailFragment) getChildFragmentManager().findFragmentByTag(
                    PokemonSpecieDetailFragment.class.getName());
            if (fragment == null) {
                fragment = PokemonSpecieDetailFragment.newInstance(pokemonSpecie);
                getChildFragmentManager().beginTransaction().replace(R.id.detail, fragment, PokemonSpecieDetailFragment.class.getName()).commit();
            } else {
                fragment.changePokemonSpecie(pokemonSpecie);
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        // Inflate the menu items for use in the action bar
        inflater.inflate(R.menu.pokemon_specie_list_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String tagDialogFragment = PokemonSpecieListFilterDialogFragment.class.getName();
        PokemonSpecieListFilterDialogFragment dialogFragment = (PokemonSpecieListFilterDialogFragment) getChildFragmentManager().findFragmentByTag(
                tagDialogFragment);
        if (dialogFragment == null) {
            dialogFragment = PokemonSpecieListFilterDialogFragment.newInstance();
        }
        dialogFragment.show(getChildFragmentManager(), tagDialogFragment);
        return true;
    }

    private LoaderCallbacks<List<PokemonSpecie>> mPokemonListCallback = new LoaderCallbacks<List<PokemonSpecie>>() {

        @Override
        public Loader<List<PokemonSpecie>> onCreateLoader(int arg0, Bundle arg1) {
            mEmpty.setText(R.string.loading_pokemons);
            mAdapter.setItems(null);
            return new PokemonSpecieForMoveListLoader(getActivity(), mFilter, mMove.id);
        }

        @Override
        public void onLoadFinished(Loader<List<PokemonSpecie>> loader, List<PokemonSpecie> pokemonSpecies) {
            if (mShouldResetScroll) {
                setListAdapter(mAdapter);
                mShouldResetScroll = false;
            }
            mEmpty.setText(R.string.empty_pokemon);
            mAdapter.setItems(pokemonSpecies);
        }

        @Override
        public void onLoaderReset(Loader<List<PokemonSpecie>> loader) {
        }
    };

    private LoaderCallbacks<List<Type>> mTypeListCallback = new LoaderCallbacks<List<Type>>() {

        @Override
        public Loader<List<Type>> onCreateLoader(int arg0, Bundle arg1) {
            return new TypeListLoader(getActivity());
        }

        @Override
        public void onLoadFinished(Loader<List<Type>> loader, List<Type> types) {
            mAdapter.setTypes(types);
        }

        @Override
        public void onLoaderReset(Loader<List<Type>> loader) {
        }
    };

    @Override
    public void setFilter(SpecieFilter filter) {
        if (!filter.equals(mFilter)) {
            mFilter = filter;
            mShouldResetScroll = true;
            getLoaderManager().restartLoader(LOADER_ID_SPECIES, null, mPokemonListCallback);
        }
    }

    @Override
    public SpecieFilter getFilter() {
        return mFilter;
    }

}
