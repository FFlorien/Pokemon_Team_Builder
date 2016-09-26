
package be.florien.teambuilder.database.table;

import be.florien.joinorm.architecture.DBTable;
import be.florien.teambuilder.model.Pokemon;

public class PokemonTable extends DBTable<Pokemon> {

    public static final String TABLE_NAME = "pokemon";

    public static final String TABLE_TYPE_NAME = "pokemon_types";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_IDENTIFIER = "identifier";
    public static final String COLUMN_SPECIES_ID = "species_id";
    public static final String COLUMN_HEIGHT = "height";
    public static final String COLUMN_WEIGHT = "weight";
    public static final String COLUMN_BASE_EXPERIENCE = "base_experience";
    public static final String COLUMN_ORDER = "order";

    public static final String COLUMN_TYPE_POKEMON_ID = "pokemon_id";
    public static final String COLUMN_TYPE_TYPE_ID = "type_id";
    public static final String COLUMN_TYPE_SLOT = "slot";

    public PokemonTable() {
        super(TABLE_NAME, Pokemon.class);
    }

    @Override
    public PokemonTable selectId() {
        selectId(COLUMN_ID);
        return this;
    }

    public PokemonTable selectIdentifier() {
        selectString(COLUMN_IDENTIFIER);
        return this;
    }

    public PokemonTable selectType(TypeTableTmpForPokemon typeTable) {
        selectTable(typeTable);
        return this;
    }

    public PokemonTable selectForm(PokemonFormTable formTable) {
        selectTable(formTable);
        return this;
    }

    public PokemonTable selectPokemonMove(PokemonMoveForPokemonTable moveTable) {
        selectTable(moveTable);
        return this;
    }

    @Override
    public String getJoinToInnerTable(DBTable<?> field) {
        if (field instanceof TypeTableTmpForPokemon) {
            return "JOIN " + TABLE_TYPE_NAME + " ON " + getDataName() + "." + getId() + " = " + TABLE_TYPE_NAME + "." + COLUMN_TYPE_POKEMON_ID +
                    " JOIN " + field.getDataName() + " ON " + field.getDataName() + "." + TypeTableTmpForPokemon.COLUMN_TYPE_ID + " = " + TABLE_TYPE_NAME + "."
                    + COLUMN_TYPE_TYPE_ID;
        } else if (field instanceof PokemonFormTable) {
            return getJoinOnId(field, PokemonFormTable.COLUMN_POKEMON_ID, false);
        } else if (field instanceof PokemonMoveForPokemonTable) {
            return getJoinOnId(field, PokemonMoveForPokemonTable.COLUMN_POKEMON_ID, false);
        }
        return "";
    }

    @Override
    public String getId() {
        return COLUMN_ID;
    }

}
