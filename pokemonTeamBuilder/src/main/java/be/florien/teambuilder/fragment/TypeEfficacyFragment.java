
package be.florien.teambuilder.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import be.florien.teambuilder.R;
import be.florien.teambuilder.activity.TypeDetailActivity;
import be.florien.teambuilder.loader.TypeListLoader;
import be.florien.teambuilder.loader.TypeLoader;
import be.florien.teambuilder.model.Type;
import be.florien.teambuilder.model.TypeEfficacyAsAttack;
import be.florien.teambuilder.model.TypeEfficacyAsDefense;
import be.florien.teambuilder.model.TypeEnum;

import java.util.List;

public class TypeEfficacyFragment extends Fragment {
    // TODO: selector on type

    /*
     * FIELDS AND CONSTANTS
     */

    private static final String ARG_INITIAL_TYPE_0 = "ARG_INITIAL_TYPE_0";
    private static final String ARG_INITIAL_TYPE_1 = "ARG_INITIAL_TYPE_1";
    private static final String ARG_IS_ATTACK = "ARG_IS_ATTACK";

    private static final int LOADER_TYPES_ID = 0;
    private static final int LOADER_TYPE_0_ID = 1;
    private static final int LOADER_TYPE_1_ID = 2;

    public static final int NO_TYPE = -5;

    private RelativeLayout mIneffectiveLayout;
    private RelativeLayout mNearlyIneffectiveLayout;
    private RelativeLayout mNotVeryEffectiveLayout;
    private RelativeLayout mEffectiveLayout;
    private RelativeLayout mSuperEffectiveLayout;
    private RelativeLayout mMegaEffectiveLayout;
    private int mIneffectiveNextId;
    private int mNearlyIneffectiveNextId;
    private int mNotVeryEffectiveNextId;
    private int mEffectiveNextId;
    private int mSuperEffectiveNextId;
    private int mMegaEffectiveNextId;

    private int mMargin4dp;
    private int mMargin2dp;
    private int mColumns;
    private MarginLayoutParams mBaseParams;

    private boolean mIsViewReady = false;

    private boolean mIsAttack = true;

    private int mType0ID;
    private int mType1ID = NO_TYPE;
    private Type mType0;
    private Type mType1;

    private List<Type> mTypeList;
    private SparseArray<Type> mTypeSparseArray;
    private SparseArray<TypeEfficacyAsDefense> mType1Efficacy;

    private Handler mHandler = new Handler();

    /*
     * NEW INSTANCES
     */

    public static TypeEfficacyFragment newInstance(int type, boolean isAttack) {
        TypeEfficacyFragment fragment = new TypeEfficacyFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_INITIAL_TYPE_0, type);
        bundle.putInt(ARG_INITIAL_TYPE_1, NO_TYPE);
        bundle.putBoolean(ARG_IS_ATTACK, isAttack);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static TypeEfficacyFragment newInstance(int type0ID, int type1ID) {
        TypeEfficacyFragment fragment = new TypeEfficacyFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_INITIAL_TYPE_0, type0ID);
        bundle.putInt(ARG_INITIAL_TYPE_1, type1ID);
        bundle.putBoolean(ARG_IS_ATTACK, false);
        fragment.setArguments(bundle);
        return fragment;
    }

    /*
     * FRAGMENT'S METHODS
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mType0ID = getArguments().getInt(ARG_INITIAL_TYPE_0);
        mType1ID = getArguments().getInt(ARG_INITIAL_TYPE_1);
        if (savedInstanceState != null) {
            mIsAttack = savedInstanceState.getBoolean(ARG_IS_ATTACK);
        } else {
            mIsAttack = getArguments().getBoolean(ARG_IS_ATTACK);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mIsViewReady = false;

        mColumns = getResources().getInteger(R.integer.type_efficacy_columns);
        mIneffectiveNextId = mColumns;
        mNearlyIneffectiveNextId = mColumns;
        mNotVeryEffectiveNextId = mColumns;
        mEffectiveNextId = mColumns;
        mSuperEffectiveNextId = mColumns;
        mMegaEffectiveNextId = mColumns;

        View view = inflater.inflate(R.layout.fragment_type_efficacy, container, false);

        mIneffectiveLayout = (RelativeLayout) view.findViewById(R.id.ineffective_rel_layout);
        mNearlyIneffectiveLayout = (RelativeLayout) view.findViewById(R.id.nearly_ineffective_rel_layout);
        mNotVeryEffectiveLayout = (RelativeLayout) view.findViewById(R.id.not_very_effective_rel_layout);
        mEffectiveLayout = (RelativeLayout) view.findViewById(R.id.effective_rel_layout);
        mSuperEffectiveLayout = (RelativeLayout) view.findViewById(R.id.super_effective_rel_layout);
        mMegaEffectiveLayout = (RelativeLayout) view.findViewById(R.id.mega_effective_rel_layout);

        ViewTreeObserver mViewObserver = mSuperEffectiveLayout.getViewTreeObserver();
        if (mViewObserver.isAlive()) {
            mViewObserver.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

                @SuppressWarnings("deprecation")
                @Override
                public void onGlobalLayout() {
                    if (mSuperEffectiveLayout.getViewTreeObserver().isAlive()) {
                        mSuperEffectiveLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                    mIsViewReady = true;
                    if (mTypeSparseArray != null) {
                        setInfos();
                    }
                }
            });
        }
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(LOADER_TYPES_ID, null, mTypeListCallback);
        getLoaderManager().initLoader(LOADER_TYPE_0_ID, null, mType0Callback);
        if (mType1ID != NO_TYPE) {
            getLoaderManager().initLoader(LOADER_TYPE_1_ID, null, mType1Callback);

        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(ARG_IS_ATTACK, mIsAttack);
    }

    /*
     * PUBLIC METHODS
     */

    public void setType(int typeID, boolean isAttack) {
        if (typeID != NO_TYPE) {
            mType0ID = typeID;
        }
        mIsAttack = isAttack;
        getLoaderManager().restartLoader(LOADER_TYPE_0_ID, null, mType0Callback);
    }

    public void setType(int type0, int type1) {
        mType0ID = type0;
        mType1ID = type1;
        mIsAttack = false;
        getLoaderManager().restartLoader(LOADER_TYPE_0_ID, null, mType0Callback);
        if (mType1ID != NO_TYPE) {
            getLoaderManager().restartLoader(LOADER_TYPE_1_ID, null, mType1Callback);
        }
    }

    /*
     * PRIVATES METHODS
     */

    private void setInfos() {
        if (mType0 == null || (mType1ID != NO_TYPE && mType1Efficacy == null) || !mIsViewReady || mTypeSparseArray == null) {
            return;
        }
        mIneffectiveLayout.removeViews(1, mIneffectiveNextId - mColumns);
        mNearlyIneffectiveLayout.removeViews(1, mNearlyIneffectiveNextId - mColumns);
        mNotVeryEffectiveLayout.removeViews(1, mNotVeryEffectiveNextId - mColumns);
        mEffectiveLayout.removeViews(1, mEffectiveNextId - mColumns);
        mSuperEffectiveLayout.removeViews(1, mSuperEffectiveNextId - mColumns);
        mMegaEffectiveLayout.removeViews(1, mMegaEffectiveNextId - mColumns);
        mIneffectiveNextId = mColumns;
        mNearlyIneffectiveNextId = mColumns;
        mNotVeryEffectiveNextId = mColumns;
        mEffectiveNextId = mColumns;
        mSuperEffectiveNextId = mColumns;
        mMegaEffectiveNextId = mColumns;
        mMargin4dp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
        mMargin2dp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());
        mBaseParams = new ViewGroup.MarginLayoutParams((mIneffectiveLayout.getWidth() - (mMargin4dp * (mColumns + 1))) / mColumns,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48, getResources().getDisplayMetrics()));
        mBaseParams.topMargin = mMargin4dp;

        if (mIsAttack) {
            for (TypeEfficacyAsAttack efficacy : mType0.attack) {
                Type type = efficacy.typeTargetted;

                putTypeInCategory(type, efficacy.damage_factor);
            }
        } else {
            List<TypeEfficacyAsDefense> defense = mType0.defense;
            for (TypeEfficacyAsDefense efficacy : defense) {
                Type type = efficacy.typeAttacking;
                double damage = efficacy.damage_factor;
                if (mType1Efficacy != null) {
                    double damage2 = mType1Efficacy.get(type.id).damage_factor;
                    damage = (damage / 100) * (damage2 / 100) * 100;
                }

                putTypeInCategory(type, (int) damage);
            }

        }

        if (mIneffectiveNextId == mColumns) {
            mIneffectiveLayout.setVisibility(View.GONE);
        } else {
            mIneffectiveLayout.setVisibility(View.VISIBLE);
        }
        if (mNearlyIneffectiveNextId == mColumns) {
            mNearlyIneffectiveLayout.setVisibility(View.GONE);
        } else {
            mNearlyIneffectiveLayout.setVisibility(View.VISIBLE);
        }
        if (mNotVeryEffectiveNextId == mColumns) {
            mNotVeryEffectiveLayout.setVisibility(View.GONE);
        } else {
            mNotVeryEffectiveLayout.setVisibility(View.VISIBLE);
        }
        if (mEffectiveNextId == mColumns) {
            mEffectiveLayout.setVisibility(View.GONE);
        } else {
            mEffectiveLayout.setVisibility(View.VISIBLE);
        }
        if (mSuperEffectiveNextId == mColumns) {
            mSuperEffectiveLayout.setVisibility(View.GONE);
        } else {
            mSuperEffectiveLayout.setVisibility(View.VISIBLE);
        }
        if (mMegaEffectiveNextId == mColumns) {
            mMegaEffectiveLayout.setVisibility(View.GONE);
        } else {
            mMegaEffectiveLayout.setVisibility(View.VISIBLE);
        }
    }

    private void putTypeInCategory(Type type, int damage_factor) {
        TextView typeView = (TextView) LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.item_type, null);
        typeView.setTag(mTypeSparseArray.get(type.id));
        typeView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(TypeDetailActivity.newIntent(getActivity(), (Type) v.getTag()));
            }
        });
        typeView.getBackground().setColorFilter(TypeEnum.getColorFilter(type.id, getActivity().getApplicationContext()));
        typeView.setText(mTypeSparseArray.get(type.id).type_names.first);
        LayoutParams params = new LayoutParams(mBaseParams);
        params.alignWithParent = true;
        switch (damage_factor) {
            case 0:
                setLayoutParams(params, mIneffectiveNextId);
                typeView.setId(mIneffectiveNextId);
                mIneffectiveLayout.addView(typeView, params);
                mIneffectiveNextId++;
                break;
            case 25:
                setLayoutParams(params, mNearlyIneffectiveNextId);
                typeView.setId(mNearlyIneffectiveNextId);
                mNearlyIneffectiveLayout.addView(typeView, params);
                mNearlyIneffectiveNextId++;
                break;
            case 50:
                setLayoutParams(params, mNotVeryEffectiveNextId);
                typeView.setId(mNotVeryEffectiveNextId);
                mNotVeryEffectiveLayout.addView(typeView, params);
                mNotVeryEffectiveNextId++;
                break;
            case 100:
                setLayoutParams(params, mEffectiveNextId);
                typeView.setId(mEffectiveNextId);
                mEffectiveLayout.addView(typeView, params);
                mEffectiveNextId++;
                break;
            case 200:
                setLayoutParams(params, mSuperEffectiveNextId);
                typeView.setId(mSuperEffectiveNextId);
                mSuperEffectiveLayout.addView(typeView, params);
                mSuperEffectiveNextId++;
                break;
            case 400:
                setLayoutParams(params, mMegaEffectiveNextId);
                typeView.setId(mMegaEffectiveNextId);
                mMegaEffectiveLayout.addView(typeView, params);
                mMegaEffectiveNextId++;
                break;
        }
    }

    private void setLayoutParams(RelativeLayout.LayoutParams params, int id) {
        if (id % mColumns == 0) {
            params.leftMargin = mMargin4dp;
            params.rightMargin = mMargin2dp;
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        } else if ((id + 1) % mColumns == 0) {
            params.leftMargin = mMargin2dp;
            params.rightMargin = mMargin4dp;
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        } else {
            params.leftMargin = mMargin2dp;
            params.rightMargin = mMargin2dp;
            params.addRule(RelativeLayout.RIGHT_OF, id - 1);
        }
        if (id < mColumns * 2) {
            params.addRule(RelativeLayout.BELOW, R.id.efficacy_text);
        } else {
            params.addRule(RelativeLayout.BELOW, id - mColumns);
        }

    }

    /*
     * LOADERCALLBACKS
     */

    private LoaderCallbacks<List<Type>> mTypeListCallback = new LoaderCallbacks<List<Type>>() {

        @Override
        public Loader<List<Type>> onCreateLoader(int arg0, Bundle arg1) {
            return new TypeListLoader(getActivity());
        }

        @Override
        public void onLoadFinished(Loader<List<Type>> loader, List<Type> types) {
            mTypeList = types;
            mHandler.post(new Runnable() {

                @Override
                public void run() {
                    mTypeSparseArray = new SparseArray<Type>();
                    for (Type type : mTypeList) {
                        mTypeSparseArray.put(type.id, type);
                    }
                    setInfos();
                }
            });
        }

        @Override
        public void onLoaderReset(Loader<List<Type>> loader) {
        }
    };

    // TODO stop loader if setType (restart doesn't do it?)

    private LoaderCallbacks<Type> mType0Callback = new LoaderCallbacks<Type>() {

        @Override
        public Loader<Type> onCreateLoader(int id, Bundle argument) {
            return new TypeLoader(getActivity(), mType0ID);
        }

        @Override
        public void onLoadFinished(Loader<Type> loader, Type result) {
            mType0 = result;
            setInfos();
        }

        @Override
        public void onLoaderReset(Loader<Type> loader) {
        }
    };

    private LoaderCallbacks<Type> mType1Callback = new LoaderCallbacks<Type>() {

        @Override
        public Loader<Type> onCreateLoader(int id, Bundle argument) {
            return new TypeLoader(getActivity(), mType1ID);
        }

        @Override
        public void onLoadFinished(Loader<Type> loader, Type result) {
            mType1 = result;
            mType1Efficacy = new SparseArray<TypeEfficacyAsDefense>();
            for (TypeEfficacyAsDefense defense : mType1.defense) {
                mType1Efficacy.put(defense.typeAttacking.id, defense);
            }
            setInfos();
        }

        @Override
        public void onLoaderReset(Loader<Type> loader) {
        }
    };
}
