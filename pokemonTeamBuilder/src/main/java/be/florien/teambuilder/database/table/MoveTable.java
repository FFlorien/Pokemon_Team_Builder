
package be.florien.teambuilder.database.table;

import be.florien.joinorm.architecture.DBTable;
import be.florien.teambuilder.model.Move;

public class MoveTable extends DBTable<Move> {

    public static final String TABLE_NAME = "moves";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_IDENTIFIER = "identifier";
    public static final String COLUMN_GENERATION = "generation_id";
    public static final String COLUMN_TYPE = "type_id";
    public static final String COLUMN_POWER = "power";
    public static final String COLUMN_PP = "pp";
    public static final String COLUMN_ACCURACY = "accuracy";
    public static final String COLUMN_PRIORITY = "priority";
    public static final String COLUMN_TARGET = "move_target_id";
    public static final String COLUMN_DAMAGE_CLASS = "damage_class_id";
    public static final String COLUMN_EFFECT = "effect_id";
    public static final String COLUMN_EFFECT_CHANCE = "effect_chance";

    public static final String TABLE_LANGUAGE_NAME = "move_names";

    public static final String COLUMN_LANGUAGE_ID = "move_id";
    public static final String COLUMN_LANGUAGE_LANGUAGE_ID = "local_language_id";
    public static final String COLUMN_LANGUAGE_NAME = "name";

    /*
     * translated | local_language_id 625|1 625|5 589|6 578|7 621|8 625|9 465|10
     */

    public MoveTable() {
        super(TABLE_NAME, Move.class);
    }

    @Override
    public MoveTable selectId() {
        selectId(COLUMN_ID);
        return this;
    }

    public MoveTable selectIdentifier() {
        selectString(COLUMN_IDENTIFIER);
        return this;
    }

    public MoveTable selectGeneration(GenerationTable table) {
        selectTable(table);
        return this;
    }

    public MoveTable selectType(TypeTable table) {
        selectTable(table);
        return this;
    }

    public MoveTable selectPower() {
        selectInt(COLUMN_POWER);
        return this;
    }

    public MoveTable selectPP() {
        selectInt(COLUMN_PP);
        return this;
    }

    public MoveTable selectAccuracy() {
        selectInt(COLUMN_ACCURACY);
        return this;
    }

    public MoveTable selectPriority() {
        selectInt(COLUMN_PRIORITY);
        return this;
    }
    
    public MoveTable selectMachine(MachineTable table) {
        selectTable(table);
        return this;
    }

    public MoveTable selectDamageClass(MoveDamageClassTable table) {
        selectTable(table);
        return this;
    }

    public MoveTable selectEffect() {
        selectTable(new MoveEffectTable());
        return this;
    }

    public MoveTable selectEffectChance() {
        selectInt(COLUMN_EFFECT_CHANCE);
        return this;
    }

    public MoveTable selectMeta(MoveMetaTable table) {
        selectTable(table);
        return this;
    }
    
    public MoveTable selectPokemonMove(PokemonMoveForMoveTable moveTable) {
        selectTable(moveTable);
        return this;
    }

    public MoveTable selectName() {
        selectTable(new TranslationTableField(TABLE_LANGUAGE_NAME, COLUMN_LANGUAGE_ID, COLUMN_LANGUAGE_NAME));
        return this;
    }

    @Override
    public String getJoinToInnerTable(DBTable<?> foreignTable) {
        if (foreignTable instanceof TypeTable) {
            return getJoinOnRef(foreignTable, COLUMN_TYPE, false);
        } else if (foreignTable instanceof GenerationTable) {
            return getJoinOnRef(foreignTable, COLUMN_GENERATION, false);
        } else if (foreignTable instanceof MoveEffectTable) {
            return getJoinOnRef(foreignTable, COLUMN_EFFECT, false);
        } else if (foreignTable instanceof MoveDamageClassTable) {
            return getJoinOnRef(foreignTable, COLUMN_DAMAGE_CLASS, false);
        } else if (foreignTable instanceof MoveMetaTable) {
            return getJoinOnId(foreignTable, MoveMetaTable.COLUMN_ID, false);
        } else if (foreignTable instanceof MachineTable) {
            return getJoinOnId(foreignTable, MachineTable.COLUMN_MOVE_ID, true);
        } else if (foreignTable instanceof PokemonMoveForMoveTable) {
            return getJoinOnId(foreignTable, PokemonMoveForMoveTable.COLUMN_MOVE_ID, true);
        }else if(foreignTable instanceof TranslationTableField){
            return ((TranslationTableField)foreignTable).getJoinToTranslatedTable(getDataName() + "." + getId());
        }
        return "";
    }

    @Override
    public String getId() {
        return COLUMN_ID;
    }

}
