
package be.florien.teambuilder.fragment;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;

import be.florien.joinorm.architecture.WhereCondition;
import be.florien.joinorm.architecture.WhereStatement;
import be.florien.teambuilder.R;
import be.florien.teambuilder.adapter.GenerationAdapter;
import be.florien.teambuilder.adapter.TypeAdapter;
import be.florien.teambuilder.loader.GenerationLoader;
import be.florien.teambuilder.loader.TypeListLoader;
import be.florien.teambuilder.model.DualStringTranslation;
import be.florien.teambuilder.model.Generation;
import be.florien.teambuilder.model.Type;
import be.florien.teambuilder.model.table.PokemonFormTable;
import be.florien.teambuilder.model.table.PokemonSpecieTable;
import be.florien.teambuilder.model.table.PokemonTable;

import java.util.List;

public class PokemonSpecieListFilterDialogFragment extends DialogFragment {

    // TODO design for dropdown (bottom right arrow)
    // TODO button "No filter"

    private static final int LOADER_GENERATION = 1;
    private static final int LOADER_TYPE = 0;
    private static final String SAVE_SELECTED_FILTERS = "SAVE_SELECTED_FILTERS";

    public static class SpecieFilter implements Parcelable {
        Integer type1;
        Integer type2;
        public Integer generation;
        boolean mega;

        //
        // public DBWhereImplementation getWhere() {
        //
        // DBWhereImplementation where = new DBWhereImplementation();
        // if (type1 != 0 || type2 != 0) {
        // int nbType;
        // String value;
        // if (type1 == 0) {
        // nbType = 1;
        // value = "(" + type2 + ")";
        // } else if (type2 == 0) {
        // nbType = 1;
        // value = "(" + type1 + ")";
        // } else {
        // nbType = 2;
        // value = "(" + type1 + ", " + type2 + ")";
        //
        // }
        // where.addWhere(new WhereStatement(PokemonTable.TABLE_NAME + "." + PokemonTable.COLUMN_ID,
        // "(SELECT pokemon_id from pokemon_types where type_id in " + value + " group by pokemon_id having count(*) = " + nbType
        // + ")",
        // WhereCondition.IN));
        // }
        // if (generation != 0) {
        // where.addWhere(new WhereStatement(PokemonSpecieTable.TABLE_NAME + "." + PokemonSpecieTable.COLUMN_GENERATION_ID, generation));
        // }
        // if (mega) {
        // where.addWhere(new WhereStatement(PokemonFormTable.TABLE_NAME + "." + PokemonFormTable.COLUMN_IS_MEGA, 1));
        // }
        // return where;
        // }

        SpecieFilter() {
        }

        private SpecieFilter(Parcel in) {
            type1 = in.readInt();
            type2 = in.readInt();
            generation = in.readInt();
            mega = in.readInt() == 1;
        }

        public static Parcelable.Creator<SpecieFilter> CREATOR = new Creator<PokemonSpecieListFilterDialogFragment.SpecieFilter>() {

            @Override
            public SpecieFilter[] newArray(int size) {
                return new SpecieFilter[size];
            }

            @Override
            public SpecieFilter createFromParcel(Parcel source) {
                return new SpecieFilter(source);
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(type1);
            dest.writeInt(type2);
            dest.writeInt(generation);
            dest.writeInt(mega ? 1 : 0);
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((generation == null) ? 0 : generation.hashCode());
            result = prime * result + (mega ? 1231 : 1237);
            result = prime * result + ((type1 == null) ? 0 : type1.hashCode());
            result = prime * result + ((type2 == null) ? 0 : type2.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            SpecieFilter other = (SpecieFilter) obj;
            if (generation == null) {
                if (other.generation != null)
                    return false;
            } else if (!generation.equals(other.generation))
                return false;
            if (mega != other.mega)
                return false;
            if (type1 == null) {
                if (other.type1 != null)
                    return false;
            } else if (!type1.equals(other.type1))
                return false;
            if (type2 == null) {
                if (other.type2 != null)
                    return false;
            } else if (!type2.equals(other.type2))
                return false;
            return true;
        }

        public void setFilter(PokemonTable pokemonTable, PokemonSpecieTable specieTable, PokemonFormTable formTable) {

            if (type1 != 0 || type2 != 0) {
                int nbType;
                String value;
                if (type1 == 0) {
                    nbType = 1;
                    value = "(" + type2 + ")";
                } else if (type2 == 0) {
                    nbType = 1;
                    value = "(" + type1 + ")";
                } else {
                    nbType = 2;
                    value = "(" + type1 + ", " + type2 + ")";

                }
                pokemonTable.addWhere(
                        new WhereStatement(
                                PokemonTable.COLUMN_ID,
                                "(SELECT pokemon_id from pokemon_types where type_id in " + value + " group by pokemon_id having count(*) = "
                                        + nbType
                                        + ")",
                                WhereCondition.IN));
            }
            if (generation != 0) {
                specieTable.addWhere(new WhereStatement("generation_id", generation));
            }
            if (mega) {
                formTable.addWhere(new WhereStatement(PokemonFormTable.COLUMN_IS_MEGA, 1));
            }
        }

        // TODO regenerate equals at each field change! #IMPORTANT

    }

    public interface SpecieListFilter {
        void setFilter(SpecieFilter filter);

        SpecieFilter getFilter();
    }

    private Spinner mTypeSpinner1;
    private Spinner mTypeSpinner2;
    private Spinner mGenerationSpinner;
    private CompoundButton mMegaButton;

    private SpecieFilter mFilter;

    public static PokemonSpecieListFilterDialogFragment newInstance() {
        return new PokemonSpecieListFilterDialogFragment();
    }

    public PokemonSpecieListFilterDialogFragment() {
        super();
        setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(Bundle arg0) {
        super.onActivityCreated(arg0);
        getLoaderManager().initLoader(LOADER_TYPE, null, mTypeCallback);
        getLoaderManager().initLoader(LOADER_GENERATION, null, mGenerationCallback);
    }

    /*
     * Dirty fix for compatibility library
     */
    @Override
    public void onDestroyView() {
        if (getDialog() != null && getRetainInstance())
            getDialog().setDismissMessage(null);
        super.onDestroyView();
    }

    @Override
    public void onSaveInstanceState(Bundle arg0) {
        mFilter = new SpecieFilter();
        mFilter.type1 = mTypeSpinner1.getSelectedItemPosition();
        mFilter.type2 = mTypeSpinner2.getSelectedItemPosition();
        mFilter.generation = mGenerationSpinner.getSelectedItemPosition();
        mFilter.mega = mMegaButton.isChecked();
        arg0.putParcelable(SAVE_SELECTED_FILTERS, mFilter);
        super.onSaveInstanceState(arg0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getDialog().setTitle(R.string.filters);
        View v = inflater.inflate(R.layout.fragment_specie_filter_dialog, container, false);
        mTypeSpinner1 = (Spinner) v.findViewById(R.id.type_spinner1);
        mTypeSpinner2 = (Spinner) v.findViewById(R.id.type_spinner2);
        mGenerationSpinner = (Spinner) v.findViewById(R.id.generation_spinner);
        mMegaButton = (CompoundButton) v.findViewById(R.id.mega_check);
        Button mOkButton = (Button) v.findViewById(R.id.ok);
        mOkButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                SpecieFilter filter = new SpecieFilter();
                filter.type1 = ((Type) mTypeSpinner1.getSelectedItem()).id;
                filter.type2 = ((Type) mTypeSpinner2.getSelectedItem()).id;
                filter.generation = ((Generation) mGenerationSpinner.getSelectedItem()).id;
                filter.mega = mMegaButton.isChecked();
                ((SpecieListFilter) getParentFragment()).setFilter(filter);
                PokemonSpecieListFilterDialogFragment.this.dismiss();
            }
        });
        if (savedInstanceState != null) {
            mFilter = savedInstanceState.getParcelable(SAVE_SELECTED_FILTERS);
            mMegaButton.setChecked(mFilter.mega);
        }
        return v;
    }

    private LoaderCallbacks<List<Type>> mTypeCallback = new LoaderCallbacks<List<Type>>() {

        @Override
        public Loader<List<Type>> onCreateLoader(int arg0, Bundle arg1) {
            return new TypeListLoader(getActivity());
        }

        @Override
        public void onLoadFinished(Loader<List<Type>> loader, List<Type> types) {
            if (types.get(0).id != 0) {
                Type type = new Type();
                type.id = 0;
                type.type_names = new DualStringTranslation();
                type.type_names.first = getString(R.string.all);
                types.add(0, type);
            }
            TypeAdapter typeAdapter = new TypeAdapter();
            typeAdapter.setItems(types);
            mTypeSpinner1.setAdapter(typeAdapter);
            mTypeSpinner2.setAdapter(typeAdapter);
            if (mFilter == null) {
                mFilter = ((SpecieListFilter) getParentFragment()).getFilter();
            }
            if (mFilter != null) {
                mTypeSpinner1.setSelection(mFilter.type1 == null ? 0 : mFilter.type1);
                mTypeSpinner2.setSelection(mFilter.type2 == null ? 0 : mFilter.type2);
                mMegaButton.setChecked(mFilter.mega);
            }
        }

        @Override
        public void onLoaderReset(Loader<List<Type>> arg0) {
        }
    };

    private LoaderCallbacks<List<Generation>> mGenerationCallback = new LoaderCallbacks<List<Generation>>() {

        @Override
        public Loader<List<Generation>> onCreateLoader(int arg0, Bundle arg1) {
            return new GenerationLoader(getActivity());
        }

        @Override
        public void onLoadFinished(Loader<List<Generation>> loader, List<Generation> generations) {
            if (generations.get(0).id != 0) {
                Generation generation = new Generation();
                generation.id = 0;
                generation.generation_names = new DualStringTranslation();
                generation.generation_names.first = getString(R.string.all);
                generations.add(0, generation);
            }
            GenerationAdapter generationAdapter = new GenerationAdapter();
            generationAdapter.setItems(generations);
            mGenerationSpinner.setAdapter(generationAdapter);
            if (mFilter == null) {
                mFilter = ((SpecieListFilter) getParentFragment()).getFilter();
            }
            if (mFilter != null) {
                mGenerationSpinner.setSelection(mFilter.generation == null ? 0 : mFilter.generation);
            }
        }

        @Override
        public void onLoaderReset(Loader<List<Generation>> arg0) {
        }
    };

}
