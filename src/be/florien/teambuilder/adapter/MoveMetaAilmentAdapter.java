
package be.florien.teambuilder.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import be.florien.teambuilder.R;
import be.florien.teambuilder.model.MoveMetaAilment;
import be.florien.teambuilder.model.MoveMetaAilmentClassEnum;

public class MoveMetaAilmentAdapter extends AbstractBaseAdapter<MoveMetaAilment> {

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_text, parent, false);
        }
        TextView text = ViewHolder.get(convertView, R.id.text);
        ImageView image = ViewHolder.get(convertView, R.id.image);
        MoveMetaAilment ailment = getItem(position);
        text.setText(ailment.move_meta_ailment_names.first);
        image.setImageResource(MoveMetaAilmentClassEnum.getDrawableRes(ailment.id));
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_text, parent, false);
        }
        TextView text = ViewHolder.get(convertView, R.id.text);
        ImageView image = ViewHolder.get(convertView, R.id.image);
        MoveMetaAilment ailment = getItem(position);
        text.setText(ailment.move_meta_ailment_names.first);
        image.setImageResource(MoveMetaAilmentClassEnum.getDrawableRes(ailment.id));
        return convertView;
    }

}
