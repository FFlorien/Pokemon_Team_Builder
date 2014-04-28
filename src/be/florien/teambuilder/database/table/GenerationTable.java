
package be.florien.teambuilder.database.table;

import be.florien.databasecomplexjoins.architecture.DBTable;
import be.florien.teambuilder.model.Generation;

public class GenerationTable extends DBTable<Generation> {
    public static final String TABLE_NAME = "generations";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_IDENTIFIER = "identifier";

    public static final String TABLE_LANGUAGE_NAME = "generation_names";

    private static final String COLUMN_LANGUAGE_ID = "generation_id";
    private static final String COLUMN_LANGUAGE_NAME = "name";

    public GenerationTable() {
        super(TABLE_NAME, Generation.class);
    }
    
    @Override
    public GenerationTable selectId() {
        selectId(COLUMN_ID);
        return this;
    }

    public GenerationTable selectIdentifier() {
        selectString(COLUMN_IDENTIFIER);
        return this;
    }
    public GenerationTable selectName() {
        selectTable(new TranslationTableField(TABLE_LANGUAGE_NAME, COLUMN_LANGUAGE_ID, COLUMN_LANGUAGE_NAME));
        return this;
    }

    @Override
    public String getJoinToInnerTable(DBTable<?> field) {
        if(field instanceof TranslationTableField){
            return ((TranslationTableField) field).getJoinToTranslatedTable(getDataName() + "." + getId());
        }
        return "";
    }

    @Override
    public String getId() {
        return COLUMN_ID;
    }

}
