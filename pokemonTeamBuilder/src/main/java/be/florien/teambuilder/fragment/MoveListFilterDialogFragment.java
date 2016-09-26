
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
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

import be.florien.joinorm.architecture.WhereCondition;
import be.florien.joinorm.architecture.WhereStatement;
import be.florien.teambuilder.R;
import be.florien.teambuilder.adapter.MoveDamageClassAdapter;
import be.florien.teambuilder.adapter.MoveMetaAilmentAdapter;
import be.florien.teambuilder.adapter.TypeAdapter;
import be.florien.teambuilder.database.table.TypeTableTmpForPokemon;
import be.florien.teambuilder.loader.MoveDamageClassLoader;
import be.florien.teambuilder.loader.MoveMaxValuesLoader;
import be.florien.teambuilder.loader.MoveMetaAilmentLoader;
import be.florien.teambuilder.loader.TypeListLoader;
import be.florien.teambuilder.model.DualStringTranslation;
import be.florien.teambuilder.model.MoveDamageClass;
import be.florien.teambuilder.model.MoveDamageClassEnum;
import be.florien.teambuilder.model.MoveMetaAilment;
import be.florien.teambuilder.model.MoveMetaAilmentClassEnum;
import be.florien.teambuilder.model.Type;
import be.florien.teambuilder.model.TypeEnum;
import be.florien.teambuilder.model.table.MoveMetaTable;
import be.florien.teambuilder.model.table.MoveTable;
import be.florien.teambuilder.model.table.TypeTable;

import java.util.List;

public class MoveListFilterDialogFragment extends DialogFragment {

    // TODO: Loading screen (empty view)
    // TODO button "No filter"

    private static final String SAVE_SELCTED_FILTERS = "SAVE_SELCTED_FILTERS";
    private static final int LOADER_TYPE = 0;
    private static final int LOADER_DAMAGE_CLASS = 1;
    private static final int LOADER_AILMENT = 2;
    private static final int LOADER_MAXVALUES = 3;

    public static class MaxValue {
        public int pp;
        public int power;
    }

    public static class MoveFilter implements Parcelable {

        public Integer typeId;
        public Integer damageClassId;
        public Integer ailmentId;
        public Integer ailmentSelection;
        public Integer minPP;
        public Integer minPower;

        // TODO regenerate equals at each field change! #IMPORTANT

        public static Parcelable.Creator<MoveFilter> CREATOR = new Creator<MoveListFilterDialogFragment.MoveFilter>() {

            @Override
            public MoveFilter[] newArray(int size) {
                return new MoveFilter[size];
            }

            @Override
            public MoveFilter createFromParcel(Parcel source) {
                return new MoveFilter(source);
            }
        };

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((ailmentId == null) ? 0 : ailmentId.hashCode());
            result = prime * result + ((ailmentSelection == null) ? 0 : ailmentSelection.hashCode());
            result = prime * result + ((damageClassId == null) ? 0 : damageClassId.hashCode());
            result = prime * result + ((minPP == null) ? 0 : minPP.hashCode());
            result = prime * result + ((minPower == null) ? 0 : minPower.hashCode());
            result = prime * result + ((typeId == null) ? 0 : typeId.hashCode());
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
            MoveFilter other = (MoveFilter) obj;
            if (ailmentId == null) {
                if (other.ailmentId != null)
                    return false;
            } else if (!ailmentId.equals(other.ailmentId))
                return false;
            if (ailmentSelection == null) {
                if (other.ailmentSelection != null)
                    return false;
            } else if (!ailmentSelection.equals(other.ailmentSelection))
                return false;
            if (damageClassId == null) {
                if (other.damageClassId != null)
                    return false;
            } else if (!damageClassId.equals(other.damageClassId))
                return false;
            if (minPP == null) {
                if (other.minPP != null)
                    return false;
            } else if (!minPP.equals(other.minPP))
                return false;
            if (minPower == null) {
                if (other.minPower != null)
                    return false;
            } else if (!minPower.equals(other.minPower))
                return false;
            if (typeId == null) {
                if (other.typeId != null)
                    return false;
            } else if (!typeId.equals(other.typeId))
                return false;
            return true;
        }

        public MoveFilter() {
        }

        private MoveFilter(Parcel in) {
            typeId = in.readInt();
            damageClassId = in.readInt();
            ailmentId = in.readInt();
            ailmentSelection = in.readInt();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(typeId);
            dest.writeInt(damageClassId);
            dest.writeInt(ailmentId);
            dest.writeInt(ailmentSelection);
        }

        public void setFilter(TypeTable typeTable, MoveMetaTable metaTable, MoveTable table) {

            if (typeId != null && typeId != TypeEnum.ALL.getId()) {
                typeTable.addWhere(new WhereStatement(TypeTableTmpForPokemon.COLUMN_TYPE_ID, typeId, WhereCondition.EQUAL));
            }
            if (ailmentId != null && ailmentId != MoveMetaAilmentClassEnum.ALL.getId()) {
                metaTable.addWhere(new WhereStatement(MoveMetaTable.COLUMN_META_AILMENT_ID, ailmentId));
            }
            if (damageClassId != null && damageClassId != MoveDamageClassEnum.ALL.getId()) {
                table.addWhere(new WhereStatement(MoveTable.COLUMN_MOVE_DAMAGE_CLASSES, damageClassId));
            }
            if (minPower != null && minPower != 0) {
                table.addWhere(new WhereStatement(MoveTable.COLUMN_POWER, minPower, WhereCondition.MORE_EQUAL));
            }
            if (minPP != null && minPP != 0) {
                table.addWhere(new WhereStatement(MoveTable.COLUMN_PP, minPP, WhereCondition.MORE_EQUAL));
            }
        }
    }

    public interface MoveListFilter {
        public void setFilter(MoveFilter filter);

        public MoveFilter getFilter();
    }

    private Spinner mTypeSpinner;
    private Spinner mDamageClassSpinner;
    private Spinner mAilmentSpinner;
    private SeekBar mMinPPSeekBar;
    private SeekBar mMinPowerSeekBar;
    private TextView mMinPPText;
    private TextView mMinPowerText;
    private Button mOkButton;
    private MoveFilter mFilter;

    public static MoveListFilterDialogFragment newInstance() {
        MoveListFilterDialogFragment f = new MoveListFilterDialogFragment();
        return f;
    }

    public MoveListFilterDialogFragment() {
        super();
        setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(Bundle arg0) {
        super.onActivityCreated(arg0);
        getLoaderManager().initLoader(LOADER_TYPE, null, mTypeCallback);
        getLoaderManager().initLoader(LOADER_DAMAGE_CLASS, null, mDamageClassCallback);
        getLoaderManager().initLoader(LOADER_AILMENT, null, mAilmentCallback);
        getLoaderManager().initLoader(LOADER_MAXVALUES, null, mMaxValuesCallback);
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
        mFilter = new MoveFilter();
        mFilter.typeId = mTypeSpinner.getSelectedItemPosition();
        mFilter.ailmentId = ((MoveMetaAilment) mAilmentSpinner.getSelectedItem()).id;
        mFilter.ailmentSelection = mAilmentSpinner.getSelectedItemPosition();
        mFilter.damageClassId = mDamageClassSpinner.getSelectedItemPosition();
        mFilter.minPower = mMinPowerSeekBar.getProgress();
        mFilter.minPP = mMinPPSeekBar.getProgress();
        arg0.putParcelable(SAVE_SELCTED_FILTERS, mFilter);
        super.onSaveInstanceState(arg0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mFilter = savedInstanceState.getParcelable(SAVE_SELCTED_FILTERS);
        }

        getDialog().setTitle(R.string.filters);
        View v = inflater.inflate(R.layout.fragment_move_filter_dialog, container, false);
        mTypeSpinner = (Spinner) v.findViewById(R.id.type_spinner);
        mDamageClassSpinner = (Spinner) v.findViewById(R.id.damage_class_spinner);
        mAilmentSpinner = (Spinner) v.findViewById(R.id.ailment_spinner);
        mMinPPSeekBar = (SeekBar) v.findViewById(R.id.pp_seekbar);
        mMinPowerSeekBar = (SeekBar) v.findViewById(R.id.power_seekbar);
        mMinPPText = (TextView) v.findViewById(R.id.pp_text);
        mMinPowerText = (TextView) v.findViewById(R.id.power_text);

        mMinPPSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mMinPPText.setText(getString(R.string.minimum_pp, progress));
            }
        });
        mMinPowerSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mMinPowerText.setText(getString(R.string.minimum_power, progress));
            }
        });

        mOkButton = (Button) v.findViewById(R.id.ok);
        mOkButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                MoveFilter filter = new MoveFilter();
                filter.typeId = ((Type) mTypeSpinner.getSelectedItem()).id;
                filter.damageClassId = ((MoveDamageClass) mDamageClassSpinner.getSelectedItem()).id;
                filter.ailmentId = ((MoveMetaAilment) mAilmentSpinner.getSelectedItem()).id;
                filter.ailmentSelection = mAilmentSpinner.getSelectedItemPosition();
                filter.minPP = mMinPPSeekBar.getProgress();
                filter.minPower = mMinPowerSeekBar.getProgress();
                ((MoveListFilter) getParentFragment()).setFilter(filter);
                MoveListFilterDialogFragment.this.dismiss();
            }
        });
        return v;
    }

    private LoaderCallbacks<List<Type>> mTypeCallback = new LoaderCallbacks<List<Type>>() {
        @Override
        public Loader<List<Type>> onCreateLoader(int arg0, Bundle arg1) {
            return new TypeListLoader(getActivity());
        }

        @Override
        public void onLoadFinished(Loader<List<Type>> loader, List<Type> types) {
            if (types.get(0).id != TypeEnum.ALL.getId()) {
                Type typeAll = new Type();
                typeAll.id = TypeEnum.ALL.getId();
                typeAll.type_names = new DualStringTranslation();
                typeAll.type_names.first = getString(R.string.all);
                types.add(0, typeAll);
            }
            TypeAdapter typeAdapter = new TypeAdapter();
            typeAdapter.setItems(types);
            mTypeSpinner.setAdapter(typeAdapter);
            if (mFilter == null) {
                mFilter = ((MoveListFilter) getParentFragment()).getFilter();
            }
            if (mFilter != null) {
                mTypeSpinner.setSelection(mFilter.typeId == null ? 0 : mFilter.typeId);
            }
        }

        @Override
        public void onLoaderReset(Loader<List<Type>> arg0) {
        }
    };

    private LoaderCallbacks<List<MoveDamageClass>> mDamageClassCallback = new LoaderCallbacks<List<MoveDamageClass>>() {
        @Override
        public Loader<List<MoveDamageClass>> onCreateLoader(int arg0, Bundle arg1) {
            return new MoveDamageClassLoader(getActivity());
        }

        @Override
        public void onLoadFinished(Loader<List<MoveDamageClass>> loader, List<MoveDamageClass> damageClasses) {
            if (damageClasses.get(0).id != MoveDamageClassEnum.ALL.getId()) {
                MoveDamageClass damageClassAll = new MoveDamageClass();
                damageClassAll.id = MoveDamageClassEnum.ALL.getId();
                damageClassAll.move_damage_class_prose = new DualStringTranslation();
                damageClassAll.move_damage_class_prose.first = getString(R.string.all);
                damageClasses.add(0, damageClassAll);
            }
            MoveDamageClassAdapter damageClassAdapter = new MoveDamageClassAdapter();
            damageClassAdapter.setItems(damageClasses);
            mDamageClassSpinner.setAdapter(damageClassAdapter);
            if (mFilter == null) {
                mFilter = ((MoveListFilter) getParentFragment()).getFilter();
            }
            if (mFilter != null) {
                mDamageClassSpinner.setSelection(mFilter.damageClassId == null ? 0 : mFilter.damageClassId);
            }
        }

        @Override
        public void onLoaderReset(Loader<List<MoveDamageClass>> arg0) {
        }
    };

    private LoaderCallbacks<List<MoveMetaAilment>> mAilmentCallback = new LoaderCallbacks<List<MoveMetaAilment>>() {
        @Override
        public Loader<List<MoveMetaAilment>> onCreateLoader(int arg0, Bundle arg1) {
            return new MoveMetaAilmentLoader(getActivity());
        }

        @Override
        public void onLoadFinished(Loader<List<MoveMetaAilment>> loader, List<MoveMetaAilment> ailments) {
            if (ailments.get(0).id != MoveMetaAilmentClassEnum.ALL.getId()) {
                MoveMetaAilment ailmentAll = new MoveMetaAilment();
                ailmentAll.id = MoveMetaAilmentClassEnum.ALL.getId();
                ailmentAll.move_meta_ailment_names = new DualStringTranslation();
                ailmentAll.move_meta_ailment_names.first = getString(R.string.all);
                ailments.add(0, ailmentAll);
            }
            MoveMetaAilmentAdapter ailmentAdapter = new MoveMetaAilmentAdapter();
            ailmentAdapter.setItems(ailments);
            mAilmentSpinner.setAdapter(ailmentAdapter);
            if (mFilter == null) {
                mFilter = ((MoveListFilter) getParentFragment()).getFilter();
            }
            if (mFilter != null) {
                mAilmentSpinner.setSelection(mFilter.ailmentSelection == null ? 0 : mFilter.ailmentSelection);
            }
        }

        @Override
        public void onLoaderReset(Loader<List<MoveMetaAilment>> arg0) {
        }
    };

    private LoaderCallbacks<MaxValue> mMaxValuesCallback = new LoaderCallbacks<MaxValue>() {
        @Override
        public Loader<MaxValue> onCreateLoader(int arg0, Bundle arg1) {
            return new MoveMaxValuesLoader(getActivity());
        }

        @Override
        public void onLoadFinished(Loader<MaxValue> loader, MaxValue maxValues) {
            mMinPPSeekBar.setMax(maxValues.pp);
            mMinPowerSeekBar.setMax(maxValues.power);
            if (mFilter == null) {
                mFilter = ((MoveListFilter) getParentFragment()).getFilter();
            }
            if (mFilter != null) {
                mMinPPSeekBar.setProgress(mFilter.minPP);
                mMinPowerSeekBar.setProgress(mFilter.minPower);
            }
        }

        @Override
        public void onLoaderReset(Loader<MaxValue> arg0) {
        }
    };

}
