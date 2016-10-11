
package be.florien.teambuilder.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "move_meta_ailments")
public class MoveMetaAilment implements Parcelable {

    @Id
    public int id;
    public String identifier;
    @Transient()
    public DualStringTranslation move_meta_ailment_names;
    
    public MoveMetaAilment(){
    }
    
    private MoveMetaAilment(Parcel in) {
        id = in.readInt();
        identifier = in.readString();
        move_meta_ailment_names = in.readParcelable(DualStringTranslation.class.getClassLoader());
    }

    @Generated(hash = 1246172939)
    public MoveMetaAilment(int id, String identifier) {
        this.id = id;
        this.identifier = identifier;
    }

    @Transient
    public static Creator<MoveMetaAilment> CREATOR = new Creator<MoveMetaAilment>() {
        
        @Override
        public MoveMetaAilment[] newArray(int size) {
            return new MoveMetaAilment[size];
        }
        
        @Override
        public MoveMetaAilment createFromParcel(Parcel source) {
            return new MoveMetaAilment(source);
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
        dest.writeParcelable(move_meta_ailment_names, flags);
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
