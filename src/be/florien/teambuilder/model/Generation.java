
package be.florien.teambuilder.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Generation implements Parcelable {

    public int id;
    public String identifier;
    public DualStringTranslation generation_names;

    public static Parcelable.Creator<Generation> CREATOR = new Creator<Generation>() {

        @Override
        public Generation[] newArray(int size) {
            return new Generation[size];
        }

        @Override
        public Generation createFromParcel(Parcel source) {
            return new Generation(source);
        }
    };

    public Generation() {
    }

    private Generation(Parcel source) {
        id = source.readInt();
        identifier = source.readString();
        generation_names = source.readParcelable(DualStringTranslation.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(identifier);
        dest.writeParcelable(generation_names, flags);
    }

}
