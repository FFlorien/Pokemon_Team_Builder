package be.florien.teambuilder.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable {
    
    public int id;
    public String identifier;
    public int category_id;
    public int cost;
    public int fling_power;
    public int fling_effect_id;
    public DualStringTranslation item_names;
    
    public Item(){
    }
    
    private Item(Parcel in){
        id = in.readInt();
        identifier = in.readString();
        category_id = in.readInt();
        cost = in.readInt();
        fling_power = in.readInt();
        fling_effect_id = in.readInt();
        item_names = in.readParcelable(DualStringTranslation.class.getClassLoader());
    }
    
    public static Parcelable.Creator<Item> CREATOR = new Creator<Item>() {
        
        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
        
        @Override
        public Item createFromParcel(Parcel source) {
            return new Item(source);
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(identifier);
        dest.writeInt(category_id);
        dest.writeInt(cost);
        dest.writeInt(fling_power);
        dest.writeInt(fling_effect_id);
        dest.writeParcelable(item_names, flags);
    }

}
