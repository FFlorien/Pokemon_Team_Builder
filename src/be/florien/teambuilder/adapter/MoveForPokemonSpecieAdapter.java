
package be.florien.teambuilder.adapter;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import be.florien.teambuilder.R;
import be.florien.teambuilder.model.Move;
import be.florien.teambuilder.model.MoveDamageClassEnum;
import be.florien.teambuilder.model.MoveMetaAilmentClassEnum;
import be.florien.teambuilder.model.PokemonMoveForPokemon;
import be.florien.teambuilder.model.PokemonMoveMethodEnum;
import be.florien.teambuilder.model.TypeEnum;

public class MoveForPokemonSpecieAdapter extends AbstractBaseAdapter<PokemonMoveForPokemon> {


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_move, parent, false);
        }
        PokemonMoveForPokemon pokemonMoveForMove = getItem(position);
        Move move = pokemonMoveForMove.moves;
        TextView type_sticker = ViewHolder.get(convertView, R.id.type_sticker);
        TextView name = ViewHolder.get(convertView, R.id.name);
        ImageView ailment = ViewHolder.get(convertView, R.id.ailment);
        TextView power = ViewHolder.get(convertView, R.id.power);
        TextView pp = ViewHolder.get(convertView, R.id.pp);
        TextView machine = ViewHolder.get(convertView, R.id.method);
        ImageView damage_class = ViewHolder.get(convertView, R.id.damage_class);

        type_sticker.getBackground().setColorFilter(TypeEnum.getColorFilter(move.types.id, parent.getContext()));
        type_sticker.setText(move.types.type_names.first);
        name.setText(move.move_names.first);
        if (move.move_meta.move_meta_ailments.id > 0) {
            ailment.setVisibility(View.VISIBLE);
            ailment.setImageResource(MoveMetaAilmentClassEnum.getDrawableRes(move.move_meta.move_meta_ailments.id));
        } else {
            ailment.setVisibility(View.GONE);
        }
        if (move.move_damage_classes.id != 1) {
            power.setText(Html.fromHtml(parent.getContext().getString(R.string.power, move.power)));
        } else {
            power.setText("");
        }
        if (pokemonMoveForMove.pokemon_move_method_id != 4) {
            machine.setText(Html.fromHtml(PokemonMoveMethodEnum.getLabel(parent.getContext(), pokemonMoveForMove.pokemon_move_method_id, pokemonMoveForMove.level)));
        } else if (move.machines != null) {
            machine.setText(Html.fromHtml("<b>" + move.machines.items.item_names.first + "<\b>"));
        } else {
            machine.setText("");

        }
        pp.setText(Html.fromHtml(parent.getContext().getString(R.string.pp, move.pp)));
        damage_class.setImageResource(MoveDamageClassEnum.getDrawableRes(move.move_damage_classes.id));
        return convertView;
    }
}
