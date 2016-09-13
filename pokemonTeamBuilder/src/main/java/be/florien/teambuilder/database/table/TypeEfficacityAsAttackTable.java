
package be.florien.teambuilder.database.table;

import be.florien.databasecomplexjoins.architecture.DBTable;
import be.florien.teambuilder.model.TypeEfficacyAsAttack;

public class TypeEfficacityAsAttackTable extends DBTable<TypeEfficacyAsAttack> {

    public static final String TABLE_NAME = "type_efficacy";

    public static final String COLUMN_DAMAGE_TYPE = "damage_type_id";
    public static final String COLUMN_TARGET_TYPE = "target_type_id";
    public static final String COLUMN_DAMAGE_FACTOR = "damage_factor";


    public TypeEfficacityAsAttackTable() {
        super(TABLE_NAME, TypeEfficacyAsAttack.class);
    }

    @Override
    public TypeEfficacityAsAttackTable selectId() {
        selectId(COLUMN_TARGET_TYPE);
        return this;
    }
    
    public TypeEfficacityAsAttackTable selectDamageFactor() {
        selectInt(COLUMN_DAMAGE_FACTOR);
        return this;
    }
    
    public TypeEfficacityAsAttackTable selectTargetType(TypeTable table) {
        table.setAlias("typeTargetted");
        selectTable(table);
        return this;
    }

    @Override
    public String getJoinToInnerTable(DBTable<?> field) {
        if (field instanceof TypeTable) {
            return getJoinOnRef(field, COLUMN_TARGET_TYPE, false);
        }
        return "";
    }

    @Override
    public String getId() {
        return COLUMN_TARGET_TYPE;
    }

}
