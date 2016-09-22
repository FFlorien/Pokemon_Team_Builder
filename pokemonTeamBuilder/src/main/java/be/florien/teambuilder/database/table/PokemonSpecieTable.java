
package be.florien.teambuilder.database.table;

import be.florien.joinorm.architecture.DBTable;
import be.florien.teambuilder.model.table.GenerationTable;
import be.florien.teambuilder.model.PokemonSpecie;

public class PokemonSpecieTable extends DBTable<PokemonSpecie> {

    public static final String TABLE_NAME = "pokemon_species";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_IDENTIFIER = "identifier";
    public static final String COLUMN_GENERATION_ID = "generation_id";
    public static final String COLUMN_EVOLVE_FROM = "evolves_from_species_id";
    public static final String COLUMN_EVOLUTION_CHAIN = "evolution_chain_id";
    public static final String COLUMN_COLOR = "color_id";
    public static final String COLUMN_SHAPE = "shape_id";
    public static final String COLUMN_HABITAT = "habitat_id";
    public static final String COLUMN_GENDER_RATE = "gender_rate";
    public static final String COLUMN_CAPTURE_RATE = "capture_rate";
    public static final String COLUMN_BASE_HAPINESS = "base_hapiness";
    public static final String COLUMN_HATCH_COUNTER = "hatch_counter";
    public static final String COLUMN_GROWTH_RATE = "growth_rate_id";
    public static final String COLUMN_ORDER = "order";

    public static final String TABLE_LANGUAGE_NAME = "pokemon_species_names";

    public static final String COLUMN_LANGUAGE_SPECIES_ID = "pokemon_species_id";
    public static final String COLUMN_LANGUAGE_LANGUAGE_ID = "local_language_id";
    public static final String COLUMN_LANGUAGE_NAME = "name";

    /*
     * translated| local_language_id |local_language_id 718|1 493|2 562|3 500|4 718|5 718|6 718|9 54|10
     */

    public PokemonSpecieTable() {
        super(TABLE_NAME, PokemonSpecie.class);
    }

    @Override
    public PokemonSpecieTable selectId() {
        selectId(COLUMN_ID);
        return this;
    }

    public PokemonSpecieTable selectIdentifier() {
        selectString(COLUMN_IDENTIFIER);
        return this;
    }

    public PokemonSpecieTable selectGenderRate() {
        selectDouble(COLUMN_GENDER_RATE);
        return this;
    }

    public PokemonSpecieTable selectCaptureRate() {
        selectDouble(COLUMN_CAPTURE_RATE);
        return this;
    }

    public PokemonSpecieTable selectBaseHappiness() {
        selectInt(COLUMN_BASE_HAPINESS);
        return this;
    }

    public PokemonSpecieTable selectHatchCounter() {
        selectInt(COLUMN_HATCH_COUNTER);
        return this;
    }

    public PokemonSpecieTable selectPokemon(PokemonTable table) {
        selectTable(table);
        return this;
    }

    public PokemonSpecieTable selectGeneration(GenerationTable table) {
        selectTable(table);
        return this;
    }

    public PokemonSpecieTable selectEvolveFrom(PokemonSpecieTable table) {
        selectTable(table);
        return this;
    }

    public PokemonSpecieTable selectName() {
        selectTable(new TranslationTableField(TABLE_LANGUAGE_NAME, COLUMN_LANGUAGE_SPECIES_ID, COLUMN_LANGUAGE_NAME));
        return this;
    }

    @Override
    public String getJoinToInnerTable(DBTable<?> field) {
        if (field instanceof PokemonTable) {
            return getJoinOnId(field, PokemonTable.COLUMN_SPECIES_ID, false);
        }else if(field instanceof TranslationTableField){
            return ((TranslationTableField)field).getJoinToTranslatedTable(getDataName() + "." + getId());
        }
        return "";
    }

    @Override
    public String getId() {
        return COLUMN_ID;
    }

}
