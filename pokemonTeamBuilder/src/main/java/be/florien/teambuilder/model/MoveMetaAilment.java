
package be.florien.teambuilder.model;

import android.os.Parcel;
import android.os.Parcelable;

import be.florien.joinorm.annotation.JoId;
import be.florien.joinorm.annotation.JoIgnore;
import be.florien.joinorm.annotation.JoJoin;
import be.florien.joinorm.annotation.JoTable;
import be.florien.teambuilder.database.table.TranslationTableField;

@JoTable(isGeneratingWrite = false)
public class MoveMetaAilment implements Parcelable {

    @JoId
    public int id;
    public String identifier;
    @JoJoin(getTableClass = TranslationTableField.class)
    public DualStringTranslation move_meta_ailment_names;
    
    public MoveMetaAilment(){
    }
    
    private MoveMetaAilment(Parcel in) {
        id = in.readInt();
        identifier = in.readString();
        move_meta_ailment_names = in.readParcelable(DualStringTranslation.class.getClassLoader());
    }

    @JoIgnore
    public static Parcelable.Creator<MoveMetaAilment> CREATOR = new Creator<MoveMetaAilment>() {
        
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

}
