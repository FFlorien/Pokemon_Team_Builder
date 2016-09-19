
package be.florien.teambuilder.database.table;

import be.florien.joinorm.architecture.DBData;
import be.florien.joinorm.architecture.DBTable;
import be.florien.joinorm.architecture.WhereStatement;
import be.florien.joinorm.primitivefield.IntField;
import be.florien.teambuilder.appconfiguration.Constant;
import be.florien.teambuilder.model.DualStringTranslation;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TranslationTableField extends DBTable<DualStringTranslation> {
    
    //TODO add fairy translation...

    public static final String TABLE_LANGUAGE_NAME = "generation_names";
    private static final String COLUMN_LANGUAGE_ID = "generation_id";
    private static final String COLUMN_LANGUAGE_NAME = "name";

    public static TranslationTableField forGeneration() {
        return new TranslationTableField(TABLE_LANGUAGE_NAME, COLUMN_LANGUAGE_ID, COLUMN_LANGUAGE_NAME);
    }

    public TranslationTableField(String tableName, String fieldIdName, String translationFieldName) {
        super(tableName, DualStringTranslation.class);
        mDataName = tableName;
        mTranslationTableName = tableName;
        mFieldIdName = fieldIdName;
        selectId();
        selectString(translationFieldName);
    }

    public TranslationTableField(String tableName, String fieldIdName, String translationFieldName, String translationFieldName2) {
        super(tableName, DualStringTranslation.class);
        mDataName = tableName;
        mTranslationTableName = tableName;
        mFieldIdName = fieldIdName;
        selectId();
        selectString(translationFieldName);
        selectString(translationFieldName2);
    }

    private String mTranslationTableName;
    private String mFieldIdName;
    private boolean mIsFirstParsed = false;

    private static final HashMap<String, List<Integer>> mCompleteTranslations;
    static {
        mCompleteTranslations = new HashMap<String, List<Integer>>();
        mCompleteTranslations.put(MoveDamageClassTable.TABLE_PROSE_NAME, Arrays.asList(1, 9, 10));
        mCompleteTranslations.put(MoveEffectTable.TABLE_PROSE_NAME, Arrays.asList(9));
        mCompleteTranslations.put(MoveMetaAilmentTable.TABLE_NAME_NAME, Arrays.asList(9));
        mCompleteTranslations.put(MoveTable.TABLE_LANGUAGE_NAME, Arrays.asList(1, 5, 9));
        mCompleteTranslations.put(PokemonFormTable.TABLE_LANGUAGE_NAME, Arrays.asList(5, 9));
        mCompleteTranslations.put(PokemonSpecieTable.TABLE_LANGUAGE_NAME, Arrays.asList(1, 5, 6, 9));
        mCompleteTranslations.put(TypeTable.TABLE_LANGUAGE_NAME, Arrays.asList(9));
        mCompleteTranslations.put(ItemTable.TABLE_LANGUAGE_NAME, Arrays.asList(9));
    }
    private static final HashMap<String, List<Integer>> mIncompleteTranslations;
    static {
        mIncompleteTranslations = new HashMap<String, List<Integer>>();
        mIncompleteTranslations.put(MoveEffectTable.TABLE_PROSE_NAME, Arrays.asList(10));
        mIncompleteTranslations.put(MoveMetaAilmentTable.TABLE_NAME_NAME, Arrays.asList(10));
        mIncompleteTranslations.put(MoveTable.TABLE_LANGUAGE_NAME, Arrays.asList(6, 7, 8, 10));
        mIncompleteTranslations.put(PokemonSpecieTable.TABLE_LANGUAGE_NAME, Arrays.asList(2, 3, 4, 10));
        mIncompleteTranslations.put(TypeTable.TABLE_LANGUAGE_NAME, Arrays.asList(1, 5, 6, 7, 8, 10));
        mIncompleteTranslations.put(ItemTable.TABLE_LANGUAGE_NAME, Arrays.asList(1, 5, 6, 7, 8, 10));
    }
    
    public String getJoinToTranslatedTable(String idOfTable){
        return "LEFT JOIN " + mTranslationTableName + " ON " + mTranslationTableName + "." + mFieldIdName + " = " + idOfTable;
    }

    @Override
    public String getJoinToInnerTable(DBTable<?> foreignTable) {
        return "";
    }

    @Override
    public String getId() {
        return mTranslationTableName + "." + mFieldIdName;
    }
    
    //TODO use addWhere

    @Override
    public String getWhere() {
        int userLanguage = Constant.getLanguageId();
        String selection;
        if (mCompleteTranslations.get(mTranslationTableName) != null && mCompleteTranslations.get(mTranslationTableName).contains(userLanguage)) {
            selection = String.valueOf(userLanguage);
        } else if (mIncompleteTranslations.get(mTranslationTableName) != null
                && mIncompleteTranslations.get(mTranslationTableName).contains(userLanguage)) {
            selection = String.valueOf(userLanguage) + ", 9";
        } else {
            selection = "9";
        }
        return "(" +mTranslationTableName + ".local_language_id IN (" + selection + ") OR " +mTranslationTableName + ".local_language_id IS NULL)";
    }

    @Override
    public TranslationTableField addWhere(WhereStatement statement) {
        return this;
    }

    @Override
    public String getOrderBy() {
        String orderby = mTranslationTableName + ".local_language_id";
        if (Constant.getLanguageId() < 9) {
            orderby += " DESC";
        }
        return orderby;
    }

    @Override
    public TranslationTableField selectId() {
        selectId("local_language_id");
        return this;
    }

    @Override
    public void reset() {
        super.reset();
        mIsFirstParsed = false;
    }

    @Override
    protected Field getFieldToSet(DBData<?> fieldToSet) throws NoSuchFieldException {
        Field field;
        // Log.d("POKEMON", mTableName + ".getFieldToSet("+ fieldToSet.getFieldName() + ") / mCurrent == " + mCurrentObject.id + " - " +
        // mCurrentObject.first + " - " + mCurrentObject.second);
        if (fieldToSet instanceof IntField) {
            field = mClass.getField("id");
        } else if (!mIsFirstParsed) {
            mIsFirstParsed = true;
            field = mClass.getField("first");
        } else {
            field = mClass.getField("second");
        }
        return field;
    }

}
