
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
import android.support.v7.app.ActionBarActivity;
import android.support.v7.view.ActionMode;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import be.florien.teambuilder.R;
import be.florien.teambuilder.activity.PokemonSpecieDetailActivity;
import be.florien.teambuilder.adapter.PokemonSpecieAdapter;
import be.florien.teambuilder.fragment.PokemonSpecieListFilterDialogFragment.SpecieFilter;
import be.florien.teambuilder.fragment.PokemonSpecieListFilterDialogFragment.SpecieListFilter;
import be.florien.teambuilder.loader.AddPokemonUserLoader;
import be.florien.teambuilder.loader.DeletePokemonUserLoader;
import be.florien.teambuilder.loader.PokemonSpecieListLoader;
import be.florien.teambuilder.loader.TypeListLoader;
import be.florien.teambuilder.loader.UserPokemonSpecieCatchedListLoader;
import be.florien.teambuilder.model.PokemonSpecie;
import be.florien.teambuilder.model.Type;
import be.florien.teambuilder.model.UserPokemonSpecieCaught;

import java.util.ArrayList;
import java.util.List;

public class PokemonSpecieListFragment extends ListFragment implements SpecieListFilter {

    // TODO selector for filter when a filter is applied

    /*
     * FIELDS
     */
    private final int LOADER_ID_SPECIES = 10;
    private final int LOADER_ID_TYPES = 11;
    private final int LOADER_ID_POKEMON_CATCHED = 12;
    private final int LOADER_ID_ADD_POKEMON_USER = 13;
    private final int LOADER_ID_DELETE_POKEMON_USER = 14;

    private boolean mShouldResetScroll = false;

    private int mCheckCount = 0;
    private int mCheckForDelete = 0;

    private FrameLayout mDetailContainer;
    private TextView mDetailInstruction;
    private TextView mEmpty;

    private PokemonSpecieAdapter mAdapter = new PokemonSpecieAdapter();

    private Handler mHandler = new Handler();
    private BroadcastReceiver mConfigChangeReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            mHandler.post(new Runnable() {

                @Override
                public void run() {
                    getLoaderManager().restartLoader(LOADER_ID_SPECIES, null, mPokemonListCallback);
                    getLoaderManager().restartLoader(LOADER_ID_TYPES, null, mTypeListCallback);
                }
            });
        }
    };

    private ActionMode mActionMode;
    private MenuItem mCaptureMenuItem;

    private SpecieFilter mFilter;

    /*
     * INSTANTIATION
     */
    public static PokemonSpecieListFragment newInstance() {
        PokemonSpecieListFragment fragment = new PokemonSpecieListFragment();
        return fragment;
    }

    public PokemonSpecieListFragment() {
        super();
        setRetainInstance(true);
    }

    /*
     * FRAGMENT'S CALLBACKS
     */

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
        getActivity().setTitle(R.string.pokemon_specie_list_title);

        getActivity().registerReceiver(mConfigChangeReceiver, new IntentFilter(PreferenceFragment.REFRESH));

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

        getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        getListView().setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (mActionMode == null) {
                    ((ActionBarActivity) getActivity()).startSupportActionMode(mActionModeCallback);
                }
                boolean itemChecked = !getListView().isItemChecked(position);
                getListView().setItemChecked(position, itemChecked);
                setItemChecked(position, itemChecked);
                return true;
            }
        });

        if (mCheckCount > 0) {
            ((ActionBarActivity) getActivity()).startSupportActionMode(mActionModeCallback);
        }

        getLoaderManager().initLoader(LOADER_ID_SPECIES, null, mPokemonListCallback);
        getLoaderManager().initLoader(LOADER_ID_TYPES, null, mTypeListCallback);
        getLoaderManager().initLoader(LOADER_ID_POKEMON_CATCHED, null, mCatchedListCallback);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if (mActionMode != null) {
            boolean itemChecked = getListView().isItemChecked(position);
            setItemChecked(position, itemChecked);
            return;
        }

        getListView().setItemChecked(position, false);
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

    /*
     * PRIVATE
     */

    private void setItemChecked(int position, boolean itemChecked) {
        getListView().setItemChecked(position, itemChecked);
        if (itemChecked) {
            mCheckCount++;
            if (mAdapter.isPokemonCatched(position)) {
                mCheckForDelete++;
            }
        } else {
            mCheckCount--;
            if (mAdapter.isPokemonCatched(position)) {
                mCheckForDelete--;
            }
        }
        if (mCheckCount == 0) {
            mActionMode.finish();
        } else {
            if (mCheckCount == mCheckForDelete) {
                mCaptureMenuItem.setIcon(R.drawable.ico_menu_pokemon_remove);
            } else {
                mCaptureMenuItem.setIcon(R.drawable.ico_menu_pokemon_add);
            }
        }
    }

    /*
     * CALLBACKS
     */

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
            mCaptureMenuItem = null;
            getListView().clearChoices();
            getListView().requestLayout();
            mCheckCount = 0;
            mCheckForDelete = 0;

        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.pokemon_specie_context_menu, menu);
            mActionMode = mode;
            mCaptureMenuItem = menu.findItem(R.id.capture);
            return true;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.capture:
                    if (mCheckForDelete == mCheckCount) {
                        getLoaderManager().restartLoader(LOADER_ID_DELETE_POKEMON_USER, null, mDeletePokemonUser);
                    } else {
                        getLoaderManager().restartLoader(LOADER_ID_ADD_POKEMON_USER, null, mAddPokemonUser);
                    }
                    mode.finish(); // Action picked, so close the CAB
                    return true;
                default:
                    return false;
            }
        }

    };

    /*
     * LOADERCALLBACKS
     */

    private LoaderCallbacks<List<PokemonSpecie>> mPokemonListCallback = new LoaderCallbacks<List<PokemonSpecie>>() {

        @Override
        public Loader<List<PokemonSpecie>> onCreateLoader(int arg0, Bundle arg1) {
            mEmpty.setText(R.string.loading_pokemons);
            mAdapter.setItems(null);
            return new PokemonSpecieListLoader(getActivity(), mFilter);
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

    private LoaderCallbacks<List<UserPokemonSpecieCaught>> mCatchedListCallback = new LoaderCallbacks<List<UserPokemonSpecieCaught>>() {

        @Override
        public Loader<List<UserPokemonSpecieCaught>> onCreateLoader(int arg0, Bundle arg1) {
            return new UserPokemonSpecieCatchedListLoader(getActivity());
        }

        @Override
        public void onLoadFinished(Loader<List<UserPokemonSpecieCaught>> loader, List<UserPokemonSpecieCaught> catched) {
            mAdapter.setCatched(catched);
        }

        @Override
        public void onLoaderReset(Loader<List<UserPokemonSpecieCaught>> loader) {
        }
    };

    private LoaderCallbacks<Void> mAddPokemonUser = new LoaderCallbacks<Void>() {

        @Override
        public Loader<Void> onCreateLoader(int arg0, Bundle arg1) {
            List<Integer> pokemonsUser = new ArrayList<Integer>();
            SparseBooleanArray checkedItemPositions = getListView().getCheckedItemPositions();
            int position;
            while ((position = checkedItemPositions.indexOfValue(true)) >= 0) {
                PokemonSpecie pokemonSpecie = (PokemonSpecie) getListAdapter().getItem(checkedItemPositions.keyAt(position));
                pokemonsUser.add(pokemonSpecie.id);
                checkedItemPositions.delete(checkedItemPositions.keyAt(position));
            }
            getListView().clearChoices();
            getListView().requestLayout();
            return new AddPokemonUserLoader(getActivity(), pokemonsUser);
        }

        @Override
        public void onLoadFinished(Loader<Void> loader, Void types) {
            mHandler.post(new Runnable() {

                @Override
                public void run() {
                    getLoaderManager().restartLoader(LOADER_ID_POKEMON_CATCHED, null, mCatchedListCallback);
                }
            });
        }

        @Override
        public void onLoaderReset(Loader<Void> loader) {
        }
    };

    private LoaderCallbacks<Void> mDeletePokemonUser = new LoaderCallbacks<Void>() {

        @Override
        public Loader<Void> onCreateLoader(int arg0, Bundle arg1) {
            List<Integer> pokemonsUser = new ArrayList<Integer>();
            SparseBooleanArray checkedItemPositions = getListView().getCheckedItemPositions();
            int position;
            while ((position = checkedItemPositions.indexOfValue(true)) >= 0) {
                PokemonSpecie pokemonSpecie = (PokemonSpecie) getListAdapter().getItem(checkedItemPositions.keyAt(position));
                pokemonsUser.add(pokemonSpecie.id);
                checkedItemPositions.delete(checkedItemPositions.keyAt(position));
            }
            getListView().clearChoices();
            getListView().requestLayout();
            return new DeletePokemonUserLoader(getActivity(), pokemonsUser);
        }

        @Override
        public void onLoadFinished(Loader<Void> loader, Void types) {
            mHandler.post(new Runnable() {

                @Override
                public void run() {
                    getLoaderManager().restartLoader(LOADER_ID_POKEMON_CATCHED, null, mCatchedListCallback);
                }
            });
        }

        @Override
        public void onLoaderReset(Loader<Void> loader) {
        }
    };

    /*
     * SPECIELISTFILTER
     */

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
