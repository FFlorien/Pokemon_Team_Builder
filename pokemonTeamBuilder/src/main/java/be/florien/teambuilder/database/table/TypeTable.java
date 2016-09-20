
package be.florien.teambuilder.database.table;

import be.florien.joinorm.architecture.DBTable;
import be.florien.joinorm.generated.GenerationTable;
import be.florien.teambuilder.model.Type;

public class TypeTable extends DBTable<Type> {

    public static final String TABLE_NAME = "types";
    public static final String TABLE_LANGUAGE_NAME = "type_names";
    public static final String TABLE_EFFICACITY = "type_efficacity";

    public static final String COLUMN_TYPE_ID = "id";
    private static final String COLUMN_TYPE_IDENTIFIER = "identifier";
    private static final String COLUMN_TYPE_GENERATION_ID = "generation_id";

    private static final String COLUMN_LANGUAGE_TYPE_ID = "type_id";
    private static final String COLUMN_LANGUAGE_NAME = "name";

    public TypeTable() {
        super(TABLE_NAME, Type.class);
    }

    @Override
    public TypeTable selectId() {
        selectId(COLUMN_TYPE_ID);
        return this;
    }

    public TypeTable selectIdentifier() {
        selectString(COLUMN_TYPE_IDENTIFIER);
        return this;
    }

    public TypeTable selectGeneration(GenerationTable genTable) {
        selectTable(genTable);
        return this;
    }
    
    public TypeTable selectTypeEfficacityAsAttack(TypeEfficacityAsAttackTable table) {
        table.setAlias("attack");
        selectTable(table);
        return this;
    }
    
    public TypeTable selectTypeEfficacityAsDefense(TypeEfficacityAsDefenseTable table) {
        table.setAlias("defense");
        selectTable(table);
        return this;
    }

    @Override
    public String getJoinToInnerTable(DBTable<?> field) {
        if (field instanceof GenerationTable) {
            return getJoinOnRef(field, COLUMN_TYPE_GENERATION_ID, false);
        }else if(field instanceof TranslationTableField){
            return ((TranslationTableField)field).getJoinToTranslatedTable(getDataName() + "." + getId());
        }else if(field instanceof TypeEfficacityAsAttackTable){
            return getJoinOnId(field, TypeEfficacityAsAttackTable.COLUMN_DAMAGE_TYPE, false);
        }else if(field instanceof TypeEfficacityAsDefenseTable){
            return getJoinOnId(field, TypeEfficacityAsDefenseTable.COLUMN_TARGET_TYPE, false);
        }
        return "";
    }

    @Override
    public String getId() {
        return COLUMN_TYPE_ID;
    }

    public TypeTable selectName() {
        selectTable(new TranslationTableField(TABLE_LANGUAGE_NAME, COLUMN_LANGUAGE_TYPE_ID, COLUMN_LANGUAGE_NAME));
        return this;
    }

}
