
package be.florien.teambuilder.database.table;

import be.florien.joinorm.architecture.DBTable;
import be.florien.teambuilder.model.TypeEfficacyAsDefense;

public class TypeEfficacityAsDefenseTable extends DBTable<TypeEfficacyAsDefense> {

    public static final String TABLE_NAME = "type_efficacy";

    public static final String COLUMN_DAMAGE_TYPE = "damage_type_id";
    public static final String COLUMN_TARGET_TYPE = "target_type_id";
    public static final String COLUMN_DAMAGE_FACTOR = "damage_factor";


    public TypeEfficacityAsDefenseTable() {
        super(TABLE_NAME, TypeEfficacyAsDefense.class);
    }

    @Override
    public TypeEfficacityAsDefenseTable selectId() {
        selectId(COLUMN_DAMAGE_TYPE);
        return this;
    }
    
    public TypeEfficacityAsDefenseTable selectDamageFactor() {
        selectInt(COLUMN_DAMAGE_FACTOR);
        return this;
    }
    
    public TypeEfficacityAsDefenseTable selectDamageType(TypeTableTmpForPokemon table) {
        table.setAlias("typeAttacking");
        selectTable(table);
        return this;
    }

    @Override
    public String getJoinToInnerTable(DBTable<?> field) {
        if (field instanceof TypeTableTmpForPokemon) {
            return getJoinOnRef(field, COLUMN_DAMAGE_TYPE, false);
        }
        return "";
    }

    @Override
    public String getId() {
        return COLUMN_DAMAGE_TYPE;
    }

}
