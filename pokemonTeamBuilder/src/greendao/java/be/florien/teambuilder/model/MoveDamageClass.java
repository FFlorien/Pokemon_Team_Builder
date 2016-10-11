
package be.florien.teambuilder.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "move_damage_classes")
public class MoveDamageClass implements Parcelable {

    @Id
    public int id;
    @Transient()
    public DualStringTranslation move_damage_class_prose;

    @Transient
    public static Creator<MoveDamageClass> CREATOR = new Creator<MoveDamageClass>() {
        
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

    @Generated(hash = 990026789)
    public MoveDamageClass(int id) {
        this.id = id;
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

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
