
package be.florien.teambuilder.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MoveDamageClass implements Parcelable {

    public int id;
    public DualStringTranslation move_damage_class_prose;
    
    public static Parcelable.Creator<MoveDamageClass> CREATOR = new Creator<MoveDamageClass>() {
        
        @Override
        public MoveDamageClass[] newArray(int size) {
            return new MoveDamageClass[size];
        }
        
        @Override
        public MoveDamageClass createFromParcel(Parcel source) {
            return new MoveDamageClass(source);
        }
    };

    public MoveDamageClass() {
    }

    private MoveDamageClass(Parcel source) {
        id = source.readInt();
        move_damage_class_prose = source.readParcelable(DualStringTranslation.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeParcelable(move_damage_class_prose, flags);
    }

}
