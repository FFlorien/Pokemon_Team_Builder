
package be.florien.teambuilder.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

import be.florien.teambuilder.R;
import be.florien.teambuilder.activity.TopActivity.TopMenuItem;
import be.florien.teambuilder.fragment.MoveDetailFragment;
import be.florien.teambuilder.model.Move;

public class MoveDetailActivity extends ActionBarActivity {

    private static final String ARG_INIT_MOVE = "ARG_INIT_MOVE";

    public static Intent newIntent(Context context, Move move) {
        Intent intent = new Intent(context, MoveDetailActivity.class);
        intent.putExtra(ARG_INIT_MOVE, move);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_common);
        Move move = getIntent().getParcelableExtra(ARG_INIT_MOVE);
        MoveDetailFragment fragment = (MoveDetailFragment) getSupportFragmentManager().findFragmentByTag(MoveDetailFragment.class.getName());
        if (fragment == null) {
            fragment = MoveDetailFragment.newInstance(move);
        }
        getSupportFragmentManager().beginTransaction().replace(android.R.id.content, fragment, MoveDetailFragment.class.getName()).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = NavUtils.getParentActivityIntent(this);
            intent.putExtra(TopActivity.UP_MENUPOSITION, TopMenuItem.MOVE.ordinal());
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            NavUtils.navigateUpTo(this, intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
