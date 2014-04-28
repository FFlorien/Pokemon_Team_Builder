
package be.florien.teambuilder.model;

import android.os.Parcel;
import android.os.Parcelable;

public class UserPokemonSpecieCatched implements Parcelable {

    public int id;

    public UserPokemonSpecieCatched() {
    }

    private UserPokemonSpecieCatched(Parcel in) {
        id = in.readInt();
    }

    public static Parcelable.Creator<UserPokemonSpecieCatched> CREATOR = new Creator<UserPokemonSpecieCatched>() {

        @Override
        public UserPokemonSpecieCatched[] newArray(int size) {
            return new UserPokemonSpecieCatched[size];
        }

        @Override
        public UserPokemonSpecieCatched createFromParcel(Parcel source) {
            return new UserPokemonSpecieCatched(source);
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
    }

}
