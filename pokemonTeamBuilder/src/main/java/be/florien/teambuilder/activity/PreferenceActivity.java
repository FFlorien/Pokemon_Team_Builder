package be.florien.teambuilder.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import be.florien.teambuilder.R;
import be.florien.teambuilder.fragment.PreferenceFragment;

public class PreferenceActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportFragmentManager().beginTransaction().add(R.id.content,new PreferenceFragment(), PreferenceFragment.class.getName()).commit();
    }

}
