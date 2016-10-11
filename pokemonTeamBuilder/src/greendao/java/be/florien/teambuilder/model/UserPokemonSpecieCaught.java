
package be.florien.teambuilder.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "pokemon_specie_caught")
public class UserPokemonSpecieCaught implements Parcelable {

    @Id
    public int id;

    public UserPokemonSpecieCaught() {
    }

    private UserPokemonSpecieCaught(Parcel in) {
        id = in.readInt();
    }

    @Generated(hash = 856909908)
    public UserPokemonSpecieCaught(int id) {
        this.id = id;
    }

    @Transient
    public static Creator<UserPokemonSpecieCaught> CREATOR = new Creator<UserPokemonSpecieCaught>() {

        @Override
        public UserPokemonSpecieCaught[] newArray(int size) {
            return new UserPokemonSpecieCaught[size];
        }

        @Override
        public UserPokemonSpecieCaught createFromParcel(Parcel source) {
            return new UserPokemonSpecieCaught(source);
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

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
