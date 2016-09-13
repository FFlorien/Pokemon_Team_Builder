package be.florien.teambuilder.model;

import android.os.Parcel;
import android.os.Parcelable;

public class TypeEfficacyAsAttack implements Parcelable {
    
    public Type typeTargetted;
    public int damage_factor;
    public int target_type_id;

    public TypeEfficacyAsAttack() {
    }

    private TypeEfficacyAsAttack(Parcel in) {
        typeTargetted = in.readParcelable(Type.class.getClassLoader());
        damage_factor = in.readInt();
        target_type_id = in.readInt();
    }

    public static final Parcelable.Creator<TypeEfficacyAsAttack> CREATOR = new Parcelable.Creator<TypeEfficacyAsAttack>() {
        public TypeEfficacyAsAttack createFromParcel(Parcel in) {
            return new TypeEfficacyAsAttack(in);
        }

        public TypeEfficacyAsAttack[] newArray(int size) {
            return new TypeEfficacyAsAttack[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(typeTargetted, flags);
        dest.writeInt(damage_factor);
        dest.writeInt(target_type_id);
    }

}
