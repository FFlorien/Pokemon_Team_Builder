package be.florien.teambuilder.fragment;

import android.os.Parcel;
import android.os.Parcelable;

import be.florien.joinorm.architecture.WhereCondition;
import be.florien.joinorm.architecture.WhereStatement;
import be.florien.teambuilder.database.table.TypeTableTmpForPokemon;
import be.florien.teambuilder.model.MoveDamageClassEnum;
import be.florien.teambuilder.model.MoveMetaAilmentClassEnum;
import be.florien.teambuilder.model.TypeEnum;
import be.florien.teambuilder.model.table.MoveMetaTable;
import be.florien.teambuilder.model.table.MoveTable;
import be.florien.teambuilder.model.table.TypeTable;

/**
 * Created by FlamentF on 04-10-16.
 */
public class MoveFilter implements Parcelable {

    public Integer typeId;
    public Integer damageClassId;
    public Integer ailmentId;
    public Integer ailmentSelection;
    public Integer minPP;
    public Integer minPower;

    // TODO regenerate equals at each field change! #IMPORTANT

    public static Parcelable.Creator<MoveFilter> CREATOR = new Creator<MoveFilter>() {

        @Override
        public MoveFilter[] newArray(int size) {
            return new MoveFilter[size];
        }

        @Override
        public MoveFilter createFromParcel(Parcel source) {
            return new MoveFilter(source);
        }
    };

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((ailmentId == null) ? 0 : ailmentId.hashCode());
        result = prime * result + ((ailmentSelection == null) ? 0 : ailmentSelection.hashCode());
        result = prime * result + ((damageClassId == null) ? 0 : damageClassId.hashCode());
        result = prime * result + ((minPP == null) ? 0 : minPP.hashCode());
        result = prime * result + ((minPower == null) ? 0 : minPower.hashCode());
        result = prime * result + ((typeId == null) ? 0 : typeId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MoveFilter other = (MoveFilter) obj;
        if (ailmentId == null) {
            if (other.ailmentId != null)
                return false;
        } else if (!ailmentId.equals(other.ailmentId))
            return false;
        if (ailmentSelection == null) {
            if (other.ailmentSelection != null)
                return false;
        } else if (!ailmentSelection.equals(other.ailmentSelection))
            return false;
        if (damageClassId == null) {
            if (other.damageClassId != null)
                return false;
        } else if (!damageClassId.equals(other.damageClassId))
            return false;
        if (minPP == null) {
            if (other.minPP != null)
                return false;
        } else if (!minPP.equals(other.minPP))
            return false;
        if (minPower == null) {
            if (other.minPower != null)
                return false;
        } else if (!minPower.equals(other.minPower))
            return false;
        if (typeId == null) {
            if (other.typeId != null)
                return false;
        } else if (!typeId.equals(other.typeId))
            return false;
        return true;
    }

    public MoveFilter() {
    }

    private MoveFilter(Parcel in) {
        typeId = in.readInt();
        damageClassId = in.readInt();
        ailmentId = in.readInt();
        ailmentSelection = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(typeId);
        dest.writeInt(damageClassId);
        dest.writeInt(ailmentId);
        dest.writeInt(ailmentSelection);
    }

    public void setFilter(TypeTable typeTable, MoveMetaTable metaTable, MoveTable table) {

        if (typeId != null && typeId != TypeEnum.ALL.getId()) {
            typeTable.addWhere(new WhereStatement(TypeTableTmpForPokemon.COLUMN_TYPE_ID, typeId, WhereCondition.EQUAL));
        }
        if (ailmentId != null && ailmentId != MoveMetaAilmentClassEnum.ALL.getId()) {
            metaTable.addWhere(new WhereStatement(MoveMetaTable.COLUMN_META_AILMENT_ID, ailmentId));
        }
        if (damageClassId != null && damageClassId != MoveDamageClassEnum.ALL.getId()) {
            table.addWhere(new WhereStatement(MoveTable.COLUMN_MOVE_DAMAGE_CLASSES, damageClassId));
        }
        if (minPower != null && minPower != 0) {
            table.addWhere(new WhereStatement(MoveTable.COLUMN_POWER, minPower, WhereCondition.MORE_EQUAL));
        }
        if (minPP != null && minPP != 0) {
            table.addWhere(new WhereStatement(MoveTable.COLUMN_PP, minPP, WhereCondition.MORE_EQUAL));
        }
    }
}