package be.florien.teambuilder.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Machine implements Parcelable {
    
    public int machine_number;
    public int version_group_id;
    public int item_id;
    public int move_id;
    public Item items;
    public Move moves;
    
    public Machine(){
    }
    
    private Machine(Parcel in){
        machine_number = in.readInt();
        version_group_id = in.readInt();
        item_id = in.readInt();
        move_id = in.readInt();
        items = in.readParcelable(Item.class.getClassLoader());
        moves = in.readParcelable(Move.class.getClassLoader());
    }
    
    public static Parcelable.Creator<Machine> CREATOR = new Creator<Machine>() {
        
        @Override
        public Machine[] newArray(int size) {
            return new Machine[size];
        }
        
        @Override
        public Machine createFromParcel(Parcel source) {
            return new Machine(source);
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(machine_number);
        dest.writeInt(version_group_id);
        dest.writeInt(item_id);
        dest.writeInt(move_id);
        dest.writeParcelable(items, flags);
        dest.writeParcelable(moves, flags);
    }

}
