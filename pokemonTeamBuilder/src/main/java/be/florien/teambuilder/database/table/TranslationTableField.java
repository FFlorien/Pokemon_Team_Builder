
package be.florien.teambuilder.database.table;

import be.florien.joinorm.annotation.JoCustomJoin;
import be.florien.joinorm.architecture.DBData;
import be.florien.joinorm.architecture.DBTable;
import be.florien.joinorm.architecture.WhereStatement;
import be.florien.joinorm.primitivefield.IntField;
import be.florien.teambuilder.appconfiguration.Constant;
import be.florien.teambuilder.model.DualStringTranslation;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class TranslationTableField extends DBTable<DualStringTranslation> {

    //TODO add fairy translation...

    public static TranslationTableField forGeneration() {
        return new TranslationTableField("generation_names", "generation_id", "name");
    }

    public static TranslationTableField forItem() {
        return new TranslationTableField("item_names", "item_id", "name");
    }

    public static TranslationTableField forLanguage() {
        return new TranslationTableField("language_names", "language_id", "name");
    }

    public static TranslationTableField forMove() {
        return new TranslationTableField("move_names", "move_id", "name");
    }

    public static TranslationTableField forMoveDamageClass() {
        return new TranslationTableField("move_damage_class_prose", "move_damage_class_id", "name", "description");
    }

    public static TranslationTableField forMoveMetaAilment() {
        return new TranslationTableField("move_meta_ailment_names", "move_meta_ailment_id", "name");
    }

    public static TranslationTableField forPokemonForm() {
        return new TranslationTableField("pokemon_form_names", "pokemon_form_id", "form_name", "pokemon_name");
    }

    public static TranslationTableField forPokemonSpecie() {
        return new TranslationTableField("pokemon_species_names", "pokemon_species_id", "name");
    }

    public static TranslationTableField forType() {
        return new TranslationTableField("type_names", "type_id", "name");
    }

    public TranslationTableField(String tableName, String fieldIdName, String translationFieldName) {
        super(tableName, DualStringTranslation.class);
        dataName = tableName;
        mTranslationTableName = tableName;
        mFieldIdName = fieldIdName;
        selectId();
        selectString(translationFieldName);
    }

    public TranslationTableField(String tableName, String fieldIdName, String translationFieldName, String translationFieldName2) {
        super(tableName, DualStringTranslation.class);
        dataName = tableName;
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
        mCompleteTranslations = new HashMap<>();
        mCompleteTranslations.put("move_damage_class_names", Arrays.asList(1, 9, 10));
        mCompleteTranslations.put("move_effect_names", Collections.singletonList(9));
        mCompleteTranslations.put("move_meta_ailment", Collections.singletonList(9));
        mCompleteTranslations.put("move_names", Arrays.asList(1, 5, 9));
        mCompleteTranslations.put("pokemon_form_names", Arrays.asList(5, 9));
        mCompleteTranslations.put("pokemon_specie_names", Arrays.asList(1, 5, 6, 9));
        mCompleteTranslations.put(TypeTableTmpForPokemon.TABLE_LANGUAGE_NAME, Collections.singletonList(9));
        mCompleteTranslations.put("item_names", Collections.singletonList(9));
    }

    private static final HashMap<String, List<Integer>> mIncompleteTranslations;

    static {
        mIncompleteTranslations = new HashMap<>();
        mIncompleteTranslations.put("move_effect_names", Collections.singletonList(10));
        mIncompleteTranslations.put("move_meta_ailment_names", Collections.singletonList(10));
        mIncompleteTranslations.put("move_names", Arrays.asList(6, 7, 8, 10));
        mIncompleteTranslations.put("pokemon_specie_names", Arrays.asList(2, 3, 4, 10));
        mIncompleteTranslations.put(TypeTableTmpForPokemon.TABLE_LANGUAGE_NAME, Arrays.asList(1, 5, 6, 7, 8, 10));
        mIncompleteTranslations.put("item_names", Arrays.asList(1, 5, 6, 7, 8, 10));
    }

    @JoCustomJoin(getParams = "getDataName() + \".\" + getId()")
    public String getJoinToTranslatedTable(String idOfTable){
        return "LEFT JOIN " + mTranslationTableName + " ON " + mTranslationTableName + "." + mFieldIdName + " = " + idOfTable;
    }

    @Override
    public String getJoinToInnerTable(DBTable<?> foreignTable) {
        return "";
    }

    @Override
    public List<String> getId() {
        return Collections.singletonList(mTranslationTableName + "." + mFieldIdName);
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
            field = modelClass.getField("id");
        } else if (!mIsFirstParsed) {
            mIsFirstParsed = true;
            field = modelClass.getField("first");
        } else {
            field = modelClass.getField("second");
        }
        return field;
    }
}
