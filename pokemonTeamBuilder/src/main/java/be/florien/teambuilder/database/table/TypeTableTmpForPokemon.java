
package be.florien.teambuilder.database.table;

import be.florien.joinorm.annotation.JoCustomJoin;
import be.florien.joinorm.architecture.DBTable;
import be.florien.teambuilder.model.table.*;
import be.florien.teambuilder.model.Type;
import be.florien.teambuilder.model.table.GenerationTable;

public class TypeTableTmpForPokemon extends DBTable<Type> {

    public static final String TABLE_NAME = "types";
    public static final String TABLE_LANGUAGE_NAME = "type_names";
    public static final String TABLE_EFFICACITY = "type_efficacity";

    public static final String COLUMN_TYPE_ID = "id";
    private static final String COLUMN_TYPE_IDENTIFIER = "identifier";
    private static final String COLUMN_TYPE_GENERATION_ID = "generation_id";

    private static final String COLUMN_LANGUAGE_TYPE_ID = "type_id";
    private static final String COLUMN_LANGUAGE_NAME = "name";

    public TypeTableTmpForPokemon() {
        super(TABLE_NAME, Type.class);
    }

    @JoCustomJoin(getParams = "this, (TypeTableTmpForPokemon) innerTable", getTableFor = be.florien.teambuilder.model.table.PokemonTable.class)
    public String getTypeJoin(be.florien.teambuilder.model.table.PokemonTable tablePokemon, be.florien.teambuilder.database.table.TypeTableTmpForPokemon field) {
        return "JOIN pokemon_types ON " + tablePokemon.getDataName() + "." + tablePokemon.getId() + " = pokemon_types.pokemon_id" +
                " JOIN " + field.getDataName() + " ON " + field.getDataName() + ".id = pokemon_types.types_id";
    }

    @Override
    public TypeTableTmpForPokemon selectId() {
        selectId(COLUMN_TYPE_ID);
        return this;
    }

    public TypeTableTmpForPokemon selectIdentifier() {
        selectString(COLUMN_TYPE_IDENTIFIER);
        return this;
    }

    public TypeTableTmpForPokemon selectGeneration(GenerationTable genTable) {
        selectTable(genTable);
        return this;
    }
    
    public TypeTableTmpForPokemon selectTypeEfficacityAsAttack(TypeEfficacityAsAttackTable table) {
        table.setAlias("attack");
        selectTable(table);
        return this;
    }
    
    public TypeTableTmpForPokemon selectTypeEfficacityAsDefense(TypeEfficacityAsDefenseTable table) {
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

    public TypeTableTmpForPokemon selectName() {
        selectTable(new TranslationTableField(TABLE_LANGUAGE_NAME, COLUMN_LANGUAGE_TYPE_ID, COLUMN_LANGUAGE_NAME));
        return this;
    }

}