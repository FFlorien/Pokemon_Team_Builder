
package be.florien.teambuilder.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import be.florien.teambuilder.R;
import be.florien.teambuilder.activity.PokemonSpecieListForMoveActivity;
import be.florien.teambuilder.activity.TypeDetailActivity;
import be.florien.teambuilder.loader.MoveLoader;
import be.florien.teambuilder.model.Move;
import be.florien.teambuilder.model.MoveDamageClassEnum;
import be.florien.teambuilder.model.MoveMetaAilmentClassEnum;
import be.florien.teambuilder.model.Type;
import be.florien.teambuilder.model.TypeEnum;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MoveDetailFragment extends Fragment {

    // TODO: assign value
    // TODO: adapt a lot more the design
    // TODO: selector on type

    /*
     * CONSTANT AND FIELDS
     */

    private static final int LOADER_ID = 0;

    private static final String ARG_INITIAL_MOVE = "ARG_INITIAL_MOVE";

    private Button mPokemonSpeciesButton;
    private TextView mName;
    private TextView mType;
    private TextView mPP;
    private TextView mMachine;
    private ImageView mDamageClass;
    private TextView mDamageClassText;
    private TextView mPower;
    private TextView mCritRate;
    private TextView mHealing;
    private TextView mAccuracy;
    private TextView mHits;
    private TextView mTurns;
    private TextView mMoveMetaCategory;
    private TextView mEffect;
    private ImageView mAilments;
    private TextView mAilmentsText;
    private TextView mTarget;
    private TextView mPriority;
    private TextView mFlavor;
    private TextView mFlinch;
    private TextView mStat;
    private TextView mFlags;
    private TextView mRecoil;
    private TextView mGeneration;

    private Move mMove;

    /*
     * NEW INSTANCES
     */

    public static MoveDetailFragment newInstance(Move move) {
        MoveDetailFragment fragment = new MoveDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_INITIAL_MOVE, move);
        fragment.setArguments(bundle);
        return fragment;
    }

    /*
     * FRAGMENT'S METHODS
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_move_detail, container, false);
        mName = (TextView) view.findViewById(R.id.name);
        mType = (TextView) view.findViewById(R.id.type_sticker);
        mPP = (TextView) view.findViewById(R.id.pp);
        mMachine = (TextView) view.findViewById(R.id.method);
        mDamageClass = (ImageView) view.findViewById(R.id.damage_class);
        mDamageClassText = (TextView) view.findViewById(R.id.damage_class_text);
        mPower = (TextView) view.findViewById(R.id.power);
        mCritRate = (TextView) view.findViewById(R.id.critical);
        mHealing = (TextView) view.findViewById(R.id.healing);
        mAccuracy = (TextView) view.findViewById(R.id.accuracy);
        mHits = (TextView) view.findViewById(R.id.hits);
        mTurns = (TextView) view.findViewById(R.id.turns);
        mMoveMetaCategory = (TextView) view.findViewById(R.id.meta_category);
        mEffect = (TextView) view.findViewById(R.id.effect);
        mAilments = (ImageView) view.findViewById(R.id.ailment);
        mAilmentsText = (TextView) view.findViewById(R.id.ailment_text);
        mTarget = (TextView) view.findViewById(R.id.target);
        mPriority = (TextView) view.findViewById(R.id.priority);
        mFlavor = (TextView) view.findViewById(R.id.flavor);
        mFlinch = (TextView) view.findViewById(R.id.flinch);
        mStat = (TextView) view.findViewById(R.id.stat);
        mFlags = (TextView) view.findViewById(R.id.flag);
        mRecoil = (TextView) view.findViewById(R.id.recoil);
        mGeneration = (TextView) view.findViewById(R.id.generation);
        mPokemonSpeciesButton = (Button) view.findViewById(R.id.pokemon_species_button);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mMove = getArguments().getParcelable(ARG_INITIAL_MOVE);

        getLoaderManager().initLoader(LOADER_ID, null, mMoveLoaderCallback);
        if (MoveDamageClassEnum.getDamageClass(mMove.move_damage_classes.id) != MoveDamageClassEnum.STATUS) {
        }
        getActivity().setTitle(getString(R.string.detail_title, mMove.move_names.first));
    }

    /*
     * PUBLIC METHODS
     */

    public void changeMove(Move move) {
        mMove = move;

        getLoaderManager().restartLoader(LOADER_ID, null, mMoveLoaderCallback);
        getActivity().setTitle(getString(R.string.detail_title, mMove.move_names.first));

    }

    /*
     * PRIVATE METHODS
     */

    private void setInfos() {

        if (MoveDamageClassEnum.getDamageClass(mMove.move_damage_classes.id) != MoveDamageClassEnum.STATUS) {
            setEfficacy();
        }

        mPokemonSpeciesButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(PokemonSpecieListForMoveActivity.newIntent(getActivity(), mMove));
            }
        });

        mType.getBackground().setColorFilter(TypeEnum.getColorFilter(mMove.types.id, getActivity()));
        mType.setText(mMove.types.type_names.first);

        mType.setTag(mMove.types);
        mType.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(TypeDetailActivity.newIntent(getActivity(), (Type) v.getTag()));
            }
        });
        mName.setText(mMove.move_names.first);
        if (mMove.move_meta.move_meta_ailments.id > 0) {
            mAilments.setVisibility(View.VISIBLE);
            mAilmentsText.setVisibility(View.VISIBLE);
            mAilments.setImageResource(MoveMetaAilmentClassEnum.getDrawableRes(mMove.move_meta.move_meta_ailments.id));
            mAilmentsText.setText(mMove.move_meta.move_meta_ailments.move_meta_ailment_names.first);
        } else {
            mAilments.setVisibility(View.GONE);
            mAilmentsText.setVisibility(View.GONE);
        }
        if (mMove.move_damage_classes.id != 1) {
            mPower.setText(Html.fromHtml(getString(R.string.power, mMove.power)));
        } else {
            mPower.setVisibility(View.GONE);
        }
        if (mMove.machines != null) {
            mMachine.setText(mMove.machines.items.item_names.first);
        } else {
            mMachine.setVisibility(View.GONE);
        }
        mPP.setText(Html.fromHtml(getString(R.string.pp, mMove.pp)));
        mDamageClass.setImageResource(MoveDamageClassEnum.getDrawableRes(mMove.move_damage_classes.id));
        mDamageClassText.setText(mMove.move_damage_classes.move_damage_class_prose.first);
        setEffect();

        setTextOrMakeItGone(mCritRate, R.string.crit_rate, mMove.move_meta.crit_rate);
        setTextOrMakeItGone(mHealing, R.string.healing, mMove.move_meta.healing);
        setTextOrMakeItGone(mAccuracy, R.string.accuracy, mMove.accuracy);
        setTextOrMakeItGone(mHits, R.string.hits, mMove.move_meta.min_hits, mMove.move_meta.max_hits);
        setTextOrMakeItGone(mTurns, R.string.turns, mMove.move_meta.min_turns, mMove.move_meta.max_turns);
        // setTextOrMakeItGone(mMoveMetaCategory, R.string.turns, mMove.move_meta.min_turns, mMove.move_meta.max_turns);
        // TODO table language private TextView mMoveMetaCategory;
        // TODO table private TextView mTarget;
        setTextOrMakeItGone(mPriority, R.string.priority, mMove.priority);
        // TODO table private TextView mFlavor;
        setTextOrMakeItGone(mFlinch, R.string.flinch, mMove.move_meta.flinch_chance);
        // TODO table private TextView mStat;
        // TODO table private TextView mFlags;
        setTextOrMakeItGone(mRecoil, R.string.recoil, mMove.move_meta.recoil);
        // TODO table language private TextView mGeneration;

        // TODO table move_meta_category(_prose)
        // mMoveMetaCategory.setText(mMove.move_meta.meta_category_id);
    }

    private void setEffect() {
        String effect = mMove.move_effects.move_effect_prose.second;
        if (TextUtils.isEmpty(effect)) {
            effect = mMove.move_effects.move_effect_prose.first;
        }
        if (!TextUtils.isEmpty(effect)) {
            effect = effect.replace("$effect_chance", String.valueOf(mMove.effect_chance));
            Pattern referencePattern = Pattern.compile("\\[[\\w\\s]*\\]\\{\\S*:(\\S*)\\}");
            Matcher referenceMatch = referencePattern.matcher(effect);
            effect = referenceMatch.replaceAll("$1");
        }
        mEffect.setText(effect);
    }

    private void setTextOrMakeItGone(TextView textview, int stringRes, int value) {
        if (value > 0) {
            textview.setText(Html.fromHtml(getString(stringRes, value)));
        } else {
            textview.setVisibility(View.GONE);
        }
    }

    private void setTextOrMakeItGone(TextView textview, int stringRes, int value1, int value2) {
        if (value1 > 0) {
            textview.setText(Html.fromHtml(getString(stringRes, value1, value2)));
        } else {
            textview.setVisibility(View.GONE);
        }
    }

    private void setEfficacy() {
        TypeEfficacyFragment fragment = (TypeEfficacyFragment) getChildFragmentManager().findFragmentByTag(TypeEfficacyFragment.class.getName());
        if (fragment == null) {
            fragment = TypeEfficacyFragment.newInstance(mMove.types.id, true);
            getChildFragmentManager().beginTransaction().replace(R.id.frame_type_efficacy, fragment, TypeEfficacyFragment.class.getName()).commit();
        }
    }

    private LoaderCallbacks<Move> mMoveLoaderCallback = new LoaderCallbacks<Move>() {

        @Override
        public Loader<Move> onCreateLoader(int id, Bundle argument) {
            return new MoveLoader(getActivity(), mMove.id);
        }

        @Override
        public void onLoadFinished(Loader<Move> loader, Move result) {
            mMove = result;
            setInfos();
            setEfficacy();
        }

        @Override
        public void onLoaderReset(Loader<Move> loader) {
        }
    };

}
