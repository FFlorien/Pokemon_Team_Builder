
package be.florien.teambuilder.adapter;

import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import be.florien.teambuilder.R;
import be.florien.teambuilder.model.Pokemon;
import be.florien.teambuilder.model.PokemonMoveForPokemon;
import be.florien.teambuilder.model.PokemonMoveMethodEnum;
import be.florien.teambuilder.model.PokemonSpecie;

public class PokemonSpecieForMoveAdapter extends AbsPokemonSpecieAdapter {

    @Override
    public int getCount() {
        return (mTypes == null ? 0 : super.getCount());
    }

    protected void setSpecialContent(View convertView, PokemonSpecie specie) {
    }

    protected void setSpecialContentForForm(View convertView, ViewGroup parent, Pokemon pokemon) {
        TextView level = ViewHolder.get(convertView, R.id.level);
        String moveMethod = "";
        for (PokemonMoveForPokemon method : pokemon.pokemon_moves) {
            moveMethod += ((TextUtils.isEmpty(moveMethod) ? "" : ", "))
                    + PokemonMoveMethodEnum.getLabel(parent.getContext(), method.pokemon_move_method_id, method.level);
        }
        level.setText(Html.fromHtml(moveMethod));
    }
}
