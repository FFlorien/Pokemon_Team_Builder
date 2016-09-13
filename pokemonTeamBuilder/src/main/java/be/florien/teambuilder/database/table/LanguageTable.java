
package be.florien.teambuilder.database.table;

import be.florien.databasecomplexjoins.architecture.DBTable;
import be.florien.teambuilder.model.Language;

public class LanguageTable extends DBTable<Language> {

    public static final String TABLE_NAME = "languages";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ISO_639 = "iso_639";
    public static final String COLUMN_ISO_3166 = "iso_3166";
    public static final String COLUMN_IDENTIFIER = "identifier";
    public static final String COLUMN_ORDER = "order";

    public static final String TABLE_LANGUAGE_NAME = "language_names";

    public static final String COLUMN_LANGUAGE_ID = "language_id";
    public static final String COLUMN_LANGUAGE_NAME = "name";

    public LanguageTable() {
        super(TABLE_NAME, Language.class);
    }

    @Override
    public LanguageTable selectId() {
        selectId(COLUMN_ID);
        return this;
    }
    
    public LanguageTable selectName(){
        selectTable(new TranslationTableField(TABLE_LANGUAGE_NAME, COLUMN_LANGUAGE_ID, COLUMN_LANGUAGE_NAME));
        return this;
    }

    @Override
    public String getJoinToInnerTable(DBTable<?> foreignTable) {
        if(foreignTable instanceof TranslationTableField){
            return ((TranslationTableField)foreignTable).getJoinToTranslatedTable(getDataName() + "." + getId());
        }
        return "";
    }

    @Override
    public String getId() {
        return COLUMN_ID;
    }

}
