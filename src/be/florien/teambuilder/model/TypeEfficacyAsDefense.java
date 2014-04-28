
package be.florien.teambuilder.model;

import android.os.Parcel;
import android.os.Parcelable;

public class TypeEfficacyAsDefense implements Parcelable {

    public Type typeAttacking;
    public int damage_factor;
    public int damage_type_id;

    public TypeEfficacyAsDefense() {
    }

    private TypeEfficacyAsDefense(Parcel in) {
        typeAttacking = in.readParcelable(Type.class.getClassLoader());
        damage_factor = in.readInt();
        damage_type_id = in.readInt();
    }

    public static final Parcelable.Creator<TypeEfficacyAsDefense> CREATOR = new Parcelable.Creator<TypeEfficacyAsDefense>() {
        public TypeEfficacyAsDefense createFromParcel(Parcel in) {
            return new TypeEfficacyAsDefense(in);
        }

        public TypeEfficacyAsDefense[] newArray(int size) {
            return new TypeEfficacyAsDefense[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(typeAttacking, flags);
        dest.writeInt(damage_factor);
        dest.writeInt(damage_type_id);
    }

}
