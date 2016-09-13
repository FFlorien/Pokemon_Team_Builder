
package be.florien.teambuilder.model;

import android.os.Parcel;
import android.os.Parcelable;


public class MoveMetaAilment implements Parcelable {

    public int id;
    public String identifier;
    public DualStringTranslation move_meta_ailment_names;
    
    public MoveMetaAilment(){
    }
    
    private MoveMetaAilment(Parcel in) {
        id = in.readInt();
        identifier = in.readString();
        move_meta_ailment_names = in.readParcelable(DualStringTranslation.class.getClassLoader());
    }

    public static Parcelable.Creator<MoveMetaAilment> CREATOR = new Creator<MoveMetaAilment>() {
        
        @Override
        public MoveMetaAilment[] newArray(int size) {
            return new MoveMetaAilment[size];
        }
        
        @Override
        public MoveMetaAilment createFromParcel(Parcel source) {
            return new MoveMetaAilment(source);
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
        dest.writeParcelable(move_meta_ailment_names, flags);
    }

}
