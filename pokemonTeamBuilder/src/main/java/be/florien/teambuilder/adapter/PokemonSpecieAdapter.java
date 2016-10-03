
package be.florien.teambuilder.adapter;

import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import be.florien.teambuilder.R;
import be.florien.teambuilder.model.Pokemon;
import be.florien.teambuilder.model.PokemonSpecie;
import be.florien.teambuilder.model.UserPokemonSpecieCaught;

import java.util.ArrayList;
import java.util.List;

public class PokemonSpecieAdapter extends AbsPokemonSpecieAdapter {
    private List<Integer> mCatched;

    public void setCatched(List<UserPokemonSpecieCaught> catched) {
        mCatched = new ArrayList<>();
        for (UserPokemonSpecieCaught pkmnCatched : catched) {
            mCatched.add(pkmnCatched.id);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return (mTypes == null || mCatched == null ? 0 : super.getCount());
    }

    protected void setSpecialContent(View convertView, PokemonSpecie specie) {
        ImageView pokeball = ViewHolder.get(convertView, R.id.pokeball_selection);

        int identifier = convertView.getContext().getResources()
                .getIdentifier(String.format("pkm%03d", specie.id), "drawable", convertView.getContext().getPackageName());
        if(identifier == 0){
            pokeball.setImageDrawable(null);
            return;
        }
        Drawable sprite = convertView.getContext().getResources().getDrawable(identifier);

        if (!mCatched.contains(specie.id)) {
            PorterDuffColorFilter filter = new PorterDuffColorFilter(convertView.getContext().getResources().getColor(R.color.grey),
                    Mode.SRC_ATOP);
            sprite.setColorFilter(filter);
        }else{
            sprite.setColorFilter(null);
        }
        pokeball.setImageDrawable(sprite);
    }

    protected void setSpecialContentForForm(View convertView, ViewGroup parent, Pokemon pokemon) {
    }

    public boolean isPokemonCatched(int position) {
        PokemonSpecie specie = getItem(position);
        return mCatched.contains(specie.id);
    }
}
