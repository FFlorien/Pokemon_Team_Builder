
package be.florien.teambuilder.database.table;

import be.florien.joinorm.architecture.DBTable;
import be.florien.teambuilder.model.MoveEffect;

public class MoveEffectTable extends DBTable<MoveEffect> {

    public static final String TABLE_NAME = "move_effects";

    public static final String COLUMN_ID = "id";

    public static final String TABLE_PROSE_NAME = "move_effect_prose";

    public static final String COLUMN_PROSE_ID = "move_effect_id";
    public static final String COLUMN_PROSE_LANGUAGE_ID = "local_language_id";
    public static final String COLUMN_PROSE_DESCRIPTION = "effect";
    public static final String COLUMN_PROSE_SHORT = "short_effect";

    public MoveEffectTable() {
        super(TABLE_NAME, MoveEffect.class);
        selectTable(new TranslationTableField(TABLE_PROSE_NAME, COLUMN_PROSE_ID, COLUMN_PROSE_SHORT, COLUMN_PROSE_DESCRIPTION));
    }

    @Override
    public String getJoinToInnerTable(DBTable<?> innerTable) {
        if(innerTable instanceof TranslationTableField){
            return ((TranslationTableField)innerTable).getJoinToTranslatedTable(getDataName() + "." + getId());
        }
        return "";
    }

    @Override
    public String getId() {
        return COLUMN_ID;
    }

    @Override
    public MoveEffectTable selectId() {
        selectId(COLUMN_ID);
        return this;
    }

}
