
package be.florien.teambuilder.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RadioButton;

import be.florien.teambuilder.R;
import be.florien.teambuilder.loader.TypeLoader;
import be.florien.teambuilder.model.Type;

public class TypeDetailFragment extends Fragment {

    // TODO: assign value
    // TODO: adapt a lot more the design
    // TODO: when clicking for the detail of a type, redirect to the good navigation, not always a new activity
    // TODO: Tablet navigation :-/

    private static final int LOADER_TYPE_ID = 0;

    private static final String ARG_INITIAL_TYPE = "ARG_INITIAL_TYPE";
    private static final String ARG_INITIAL_ROLE = "ARG_INITIAL_ROLE";

    private RadioButton mAttackButton;
    private RadioButton mDefenseButton;
    private boolean mIsAttack = true;

    private Type mType;

    private Handler mHandler = new Handler();

    public static TypeDetailFragment newInstance(Type type) {
        TypeDetailFragment fragment = new TypeDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_INITIAL_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_type_detail, container, false);
        mAttackButton = (RadioButton) view.findViewById(R.id.attack);
        mDefenseButton = (RadioButton) view.findViewById(R.id.defense);
        mAttackButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mIsAttack) {
                    return;
                }
                mIsAttack = true;
                setInfos();
            }
        });
        mDefenseButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!mIsAttack) {
                    return;
                }
                mIsAttack = false;
                setInfos();
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mType = getArguments().getParcelable(ARG_INITIAL_TYPE);
        if (savedInstanceState != null) {
            mIsAttack = savedInstanceState.getBoolean(ARG_INITIAL_ROLE);
        }

        getLoaderManager().initLoader(LOADER_TYPE_ID, null, mTypeCallback);
        // getLoaderManager().initLoader(LOADER_TYPE_LIST_ID, null, mTypeListCallback);
        getActivity().setTitle(getString(R.string.detail_title, mType.type_names.first));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(ARG_INITIAL_ROLE, mIsAttack);
    }

    public void changeType(Type move) {
        mType = move;

        getLoaderManager().restartLoader(LOADER_TYPE_ID, null, mTypeCallback);
        getActivity().setTitle(getString(R.string.detail_title, mType.type_names.first));
    }

    private void setInfos() {
        TypeEfficacyFragment fragment = (TypeEfficacyFragment) getChildFragmentManager().findFragmentByTag(TypeEfficacyFragment.class.getName());
        if (fragment == null) {
            fragment = TypeEfficacyFragment.newInstance(mType.id, mIsAttack);
            getChildFragmentManager().beginTransaction().replace(R.id.frame_type_efficacy, fragment, TypeEfficacyFragment.class.getName()).commit();
        } else {
            fragment.setType(mType.id, mIsAttack);
        }
    }

    private LoaderCallbacks<Type> mTypeCallback = new LoaderCallbacks<Type>() {

        @Override
        public Loader<Type> onCreateLoader(int id, Bundle argument) {
            return new TypeLoader(getActivity(), mType.id);
        }

        @Override
        public void onLoadFinished(Loader<Type> loader, Type result) {
            mType = result;
            mHandler.post(new Runnable() {

                @Override
                public void run() {
                    setInfos();
                }
            });
        }

        @Override
        public void onLoaderReset(Loader<Type> loader) {
        }

    };

    // private LoaderCallbacks<List<Type>> mTypeListCallback = new LoaderCallbacks<List<Type>>() {
    //
    // @Override
    // public Loader<List<Type>> onCreateLoader(int arg0, Bundle arg1) {
    // return new TypeListLoader(getActivity());
    // }
    //
    // @Override
    // public void onLoadFinished(Loader<List<Type>> loader, List<Type> types) {
    // mTypeList = types;
    // mIsTypeListReady = true;
    // if (mIsInfosReady) {
    // mHandler.post(new Runnable() {
    //
    // @Override
    // public void run() {
    // setInfos();
    // }
    // });
    // }
    // }
    //
    // @Override
    // public void onLoaderReset(Loader<List<Type>> loader) {
    // }
    // };
}
