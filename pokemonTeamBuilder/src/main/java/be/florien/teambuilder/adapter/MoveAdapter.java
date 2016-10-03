
package be.florien.teambuilder.adapter;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import be.florien.teambuilder.R;
import be.florien.teambuilder.model.Move;
import be.florien.teambuilder.model.MoveDamageClassEnum;
import be.florien.teambuilder.model.MoveMetaAilmentClassEnum;
import be.florien.teambuilder.model.TypeEnum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class MoveAdapter extends AbstractBaseAdapter<Move> implements SectionIndexer {

    HashMap<String, Integer> mIndexer;
    String[] mSections;

    public void setItems(List<Move> items) {
        if(items == null){
            super.setItems(items);
            return;
        }
        mIndexer = new HashMap<String, Integer>(); // stores the positions for the start of each letter

        int size = items.size();
        for (int i = size - 1; i >= 0; i--) {
            String element = items.get(i).move_names.first;
            // We store the first letter of the word, and its index.
            mIndexer.put(element.substring(0, 1), i);
        }
        Set<String> keys = mIndexer.keySet(); // set of letters

        Iterator<String> keyIt = keys.iterator();
        ArrayList<String> keyList = new ArrayList<String>();

        while (keyIt.hasNext()) {
            String key = keyIt.next();
            keyList.add(key);
        }
        Collections.sort(keyList);// sort the keylist
        mSections = new String[keyList.size()]; // simple conversion to array
        keyList.toArray(mSections);
        super.setItems(items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_move, parent, false);
        }
        Move move = getItem(position);
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
        if (move.move_meta.meta_ailment_id > 0) {
            ailment.setVisibility(View.VISIBLE);
            ailment.setImageResource(MoveMetaAilmentClassEnum.getDrawableRes(move.move_meta.meta_ailment_id));
        } else {
            ailment.setVisibility(View.GONE);
        }
        if (move.move_damage_classes.id != 1) {
            power.setText(Html.fromHtml(parent.getContext().getString(R.string.power, move.power)));
        } else {
            power.setText("");
        }
        if (move.machines != null) {
            machine.setText(move.machines.items.item_names.first);
        } else {
            machine.setText("");

        }
        pp.setText(Html.fromHtml(parent.getContext().getString(R.string.pp, move.pp)));
        damage_class.setImageResource(MoveDamageClassEnum.getDrawableRes(move.move_damage_classes.id));
        return convertView;
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        String letter = mSections[sectionIndex];
        return mIndexer.get(letter);
    }

    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }

    @Override
    public Object[] getSections() {
        return mSections; // to string will be called to display the letter
    }
}
