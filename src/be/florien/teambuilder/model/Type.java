
package be.florien.teambuilder.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Type implements Parcelable {

    public int id;
    public String identifier;
    public DualStringTranslation type_names;
    public Generation generations;
    public List<TypeEfficacyAsAttack> attack;
    public List<TypeEfficacyAsDefense> defense;

    public Type() {
    }

    private Type(Parcel in) {
        id = in.readInt();
        identifier = in.readString();
        type_names = in.readParcelable(DualStringTranslation.class.getClassLoader());
        generations = in.readParcelable(Generation.class.getClassLoader());
        attack = new ArrayList<TypeEfficacyAsAttack>();
        defense = new ArrayList<TypeEfficacyAsDefense>();
        in.readTypedList(attack, TypeEfficacyAsAttack.CREATOR);
        in.readTypedList(defense, TypeEfficacyAsDefense.CREATOR);
    }

    public static final Parcelable.Creator<Type> CREATOR = new Parcelable.Creator<Type>() {
        public Type createFromParcel(Parcel in) {
            return new Type(in);
        }

        public Type[] newArray(int size) {
            return new Type[size];
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
        dest.writeParcelable(type_names, flags);
        dest.writeParcelable(generations, flags);
        dest.writeTypedList(attack);
        dest.writeTypedList(defense);
    }
}
