
package be.florien.teambuilder.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "move_effects")
public class MoveEffect implements Parcelable {

    @Id
    public int id;
    @Transient()
    public DualStringTranslation move_effect_prose;

    @Transient
    public static Creator<MoveEffect> CREATOR = new Creator<MoveEffect>() {
        
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

    @Generated(hash = 1848915505)
    public MoveEffect(int id) {
        this.id = id;
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

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
