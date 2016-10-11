
package be.florien.teambuilder.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "generations")
public class Generation implements Parcelable {

    @Id
    public int id;
    public String identifier;
    @Transient
    public DualStringTranslation generation_names;
    @Transient
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

    @Generated(hash = 430966589)
    public Generation(int id, String identifier) {
        this.id = id;
        this.identifier = identifier;
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

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

}
