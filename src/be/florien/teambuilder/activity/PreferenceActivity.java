package be.florien.teambuilder.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import be.florien.teambuilder.R;
import be.florien.teambuilder.fragment.PreferenceFragment;

public class PreferenceActivity extends ActionBarActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);
        getSupportFragmentManager().beginTransaction().add(android.R.id.content,new PreferenceFragment(), PreferenceFragment.class.getName()).commit();
    }

}
