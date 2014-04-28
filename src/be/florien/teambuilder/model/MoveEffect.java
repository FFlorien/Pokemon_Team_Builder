
package be.florien.teambuilder.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MoveEffect implements Parcelable {

    public int id;
    public DualStringTranslation move_effect_prose;
    
    public static Parcelable.Creator<MoveEffect> CREATOR = new Creator<MoveEffect>() {
        
        @Override
        public MoveEffect[] newArray(int size) {
            return new MoveEffect[size];
        }
        
        @Override
        public MoveEffect createFromParcel(Parcel source) {
            return new MoveEffect(source);
        }
    };

    public MoveEffect() {
    }
    
    private MoveEffect(Parcel source) {
        id = source.readInt();
        move_effect_prose = source.readParcelable(DualStringTranslation.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeParcelable(move_effect_prose, flags);
    }

}
