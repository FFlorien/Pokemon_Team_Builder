
package be.florien.teambuilder.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.OnNavigationListener;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import be.florien.teambuilder.R;
import be.florien.teambuilder.activity.MoveListForPokemonSpecieActivity;
import be.florien.teambuilder.activity.TypeDetailActivity;
import be.florien.teambuilder.loader.PokemonSpecieLoader;
import be.florien.teambuilder.model.Pokemon;
import be.florien.teambuilder.model.PokemonSpecie;
import be.florien.teambuilder.model.Type;
import be.florien.teambuilder.model.TypeEnum;

import java.util.ArrayList;

public class PokemonSpecieDetailFragment extends Fragment implements OnNavigationListener {

    // TODO: assign value (Stats: important && from another table)
    // TODO: adapt a lot more the design
    // TODO: selector on type
    // TODO: loading view

    /*
     * CONSTANT AND FIELDS
     */

    private static final int LOADER_ID = 0;

    private static final String ARG_INITIAL_SPECIE = "ARG_INITIAL_SPECIE";

    private TextView mName;
    private TextView mTypeText0;
    private TextView mTypeText1;
    private Button mMovesButton;

    private PokemonSpecie mPokemonSpecie;
    private int mPokemonIndex;

    /*
     * NEW INSTANCE
     */

    public static PokemonSpecieDetailFragment newInstance(PokemonSpecie specie) {
        PokemonSpecieDetailFragment fragment = new PokemonSpecieDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_INITIAL_SPECIE, specie);
        fragment.setArguments(bundle);
        return fragment;
    }

    /*
     * FRAGMENT'S METHODS
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pokemon_specie_detail, container, false);
        mName = (TextView) view.findViewById(R.id.name);
        mTypeText0 = (TextView) view.findViewById(R.id.type_sticker1);
        mTypeText1 = (TextView) view.findViewById(R.id.type_sticker2);
        mMovesButton = (Button) view.findViewById(R.id.moves_button);
        mMovesButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(MoveListForPokemonSpecieActivity.newIntent(getActivity(), mPokemonSpecie.pokemon.get(mPokemonIndex),
                        mPokemonSpecie.pokemon_species_names.first));
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPokemonSpecie = getArguments().getParcelable(ARG_INITIAL_SPECIE);
        setHasOptionsMenu(true);

        getLoaderManager().initLoader(LOADER_ID, null, mSpecieLoaderCallback);
        setEfficacy();
    }

    @Override
    public boolean onNavigationItemSelected(int position, long id) {
        setPokemon(position);
        return true;
    }

    /*
     * PUBLIC METHODS
     */

    public void changePokemonSpecie(PokemonSpecie specie) {
        mPokemonSpecie = specie;

        getLoaderManager().restartLoader(LOADER_ID, null, mSpecieLoaderCallback);
        getActivity().setTitle(getString(R.string.detail_title, mPokemonSpecie.pokemon_species_names.first));
        setEfficacy();
    }

    /*
     * PRIVATE METHODS
     */

    private void setActionBarMenu() {
        ActionBar actionBar = ((ActionBarActivity) getActivity()).getSupportActionBar();
        if (mPokemonSpecie.pokemon.size() > 1) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
            ArrayList<String> itemsName = new ArrayList<String>();
            for (int pokemonIndex = 0; pokemonIndex < mPokemonSpecie.pokemon.size(); pokemonIndex++) {
                itemsName.add(pokemonIndex == 0 ?
                        mPokemonSpecie.pokemon_species_names.first
                        : mPokemonSpecie.pokemon.get(pokemonIndex).pokemon_forms.get(0).pokemon_form_names.second);
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, itemsName);
            actionBar.setListNavigationCallbacks(adapter, this);
        } else {
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
            getActivity().setTitle(getString(R.string.detail_title, mPokemonSpecie.pokemon_species_names.first));
        }
    }

    private void setEfficacy() {
        TypeEfficacyFragment fragment = (TypeEfficacyFragment) getChildFragmentManager().findFragmentByTag(TypeEfficacyFragment.class.getName());
        if (fragment == null) {
            if (mPokemonSpecie.pokemon.get(mPokemonIndex).types.size() > 1) {
                fragment = TypeEfficacyFragment.newInstance(mPokemonSpecie.pokemon.get(mPokemonIndex).types.get(0).id,
                        mPokemonSpecie.pokemon.get(mPokemonIndex).types.get(1).id);
            } else {
                fragment = TypeEfficacyFragment.newInstance(mPokemonSpecie.pokemon.get(mPokemonIndex).types.get(0).id, false);
            }
            getChildFragmentManager().beginTransaction().replace(R.id.frame_type_efficacy, fragment, TypeEfficacyFragment.class.getName()).commit();
        } else {
            if (mPokemonSpecie.pokemon.get(mPokemonIndex).types.size() > 1) {
                fragment.setType(mPokemonSpecie.pokemon.get(mPokemonIndex).types.get(0).id,
                        mPokemonSpecie.pokemon.get(mPokemonIndex).types.get(1).id);
            } else {
                fragment.setType(mPokemonSpecie.pokemon.get(mPokemonIndex).types.get(0).id, false);
            }
        }
    }

    private void setPokemon(int pokemonIndex) {
        mPokemonIndex = pokemonIndex;
        Pokemon pokemon = mPokemonSpecie.pokemon.get(pokemonIndex);
        setTypeText(pokemon.types.get(0), mTypeText0);
        if (pokemon.types.size() > 1) {
            mTypeText1.setVisibility(View.VISIBLE);
            setTypeText(pokemon.types.get(1), mTypeText1);
        } else {
            mTypeText1.setVisibility(View.GONE);
        }
        mName.setText(pokemonIndex == 0 ? mPokemonSpecie.pokemon_species_names.first : pokemon.pokemon_forms.get(0).pokemon_form_names.first);
        setEfficacy();
    }

    private void setTypeText(Type type, TextView textType) {
        textType.getBackground().setColorFilter(TypeEnum.getColorFilter(type.id, getActivity()));
        textType.setText(type.type_names.first);
        textType.setTag(type);
        textType.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(TypeDetailActivity.newIntent(getActivity(), (Type) v.getTag()));
            }
        });
    }

    private LoaderCallbacks<PokemonSpecie> mSpecieLoaderCallback = new LoaderCallbacks<PokemonSpecie>() {

        @Override
        public Loader<PokemonSpecie> onCreateLoader(int id, Bundle argument) {
            return new PokemonSpecieLoader(getActivity(), mPokemonSpecie.id);
        }

        @Override
        public void onLoadFinished(Loader<PokemonSpecie> loader, PokemonSpecie result) {
            mPokemonSpecie = result;
            setPokemon(0);
            setActionBarMenu();
        }

        @Override
        public void onLoaderReset(Loader<PokemonSpecie> loader) {
        }
    };

}
