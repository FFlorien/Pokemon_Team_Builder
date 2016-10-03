
package be.florien.teambuilder.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import be.florien.teambuilder.R;
import be.florien.teambuilder.activity.TopActivity.TopMenuItem;
import be.florien.teambuilder.fragment.TypeDetailFragment;
import be.florien.teambuilder.model.Type;

public class TypeDetailActivity extends AppCompatActivity {

    private static final String ARG_INIT_TYPE = "ARG_INIT_TYPE";

    public static Intent newIntent(Context context, Type type) {
        Intent intent = new Intent(context, TypeDetailActivity.class);
        intent.putExtra(ARG_INIT_TYPE, type);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_common);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Type type = getIntent().getParcelableExtra(ARG_INIT_TYPE);
        TypeDetailFragment fragment = (TypeDetailFragment) getSupportFragmentManager().findFragmentByTag(TypeDetailFragment.class.getName());
        if (fragment == null) {
            fragment = TypeDetailFragment.newInstance(type);
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment, TypeDetailFragment.class.getName()).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = NavUtils.getParentActivityIntent(this);
            intent.putExtra(TopActivity.UP_MENUPOSITION, TopMenuItem.TYPE.ordinal());
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            NavUtils.navigateUpTo(this, intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
