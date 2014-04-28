
package be.florien.teambuilder.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import be.florien.teambuilder.R;
import be.florien.teambuilder.adapter.AbstractBaseAdapter;
import be.florien.teambuilder.appconfiguration.Constant;
import be.florien.teambuilder.loader.LanguageLoader;
import be.florien.teambuilder.model.Language;

import java.util.List;

public class PreferenceFragment extends Fragment {

    public static final String REFRESH = "be.florien.teambuilder.REFRESH";
    private static final int Language_LOADER_ID = 0;
    private Spinner mLanguageSpinner;
    private Button mOKButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_preference, container, false);
        mLanguageSpinner = (Spinner) view.findViewById(R.id.language_spinner);
        mOKButton = (Button) view.findViewById(R.id.ok);
        mOKButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Constant.setLanguageId(mLanguageAdapter.getItem(mLanguageSpinner.getSelectedItemPosition()).id);
                getActivity().sendBroadcast(new Intent(REFRESH));
                getActivity().finish();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getLoaderManager().initLoader(Language_LOADER_ID, null, mLanguageCallback);
    }

    private LoaderCallbacks<List<Language>> mLanguageCallback = new LoaderCallbacks<List<Language>>() {

        @Override
        public Loader<List<Language>> onCreateLoader(int arg0, Bundle arg1) {
            return new LanguageLoader(getActivity());
        }

        @Override
        public void onLoadFinished(Loader<List<Language>> arg0, List<Language> languages) {
            mLanguageAdapter.setItems(languages);
            mLanguageSpinner.setAdapter(mLanguageAdapter);
            mLanguageSpinner.setSelection(Constant.getLanguageId() - 1);
        }

        @Override
        public void onLoaderReset(Loader<List<Language>> arg0) {
        }
    };

    private AbstractBaseAdapter<Language> mLanguageAdapter = new AbstractBaseAdapter<Language>() {

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_text, parent, false);
            }

            ((TextView) convertView).setText(getItem(position).language_names.first);
            return convertView;
        }
    };

}
