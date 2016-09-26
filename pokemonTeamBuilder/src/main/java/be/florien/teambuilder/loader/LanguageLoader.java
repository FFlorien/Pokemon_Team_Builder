
package be.florien.teambuilder.loader;

import android.content.Context;

import be.florien.teambuilder.database.helper.DBTableQueryHelper;
import be.florien.teambuilder.database.table.TranslationTableField;
import be.florien.teambuilder.model.Language;
import be.florien.teambuilder.model.table.LanguageTable;

import java.util.List;

public class LanguageLoader extends AbstractAsyncTaskLoader<List<Language>> {

    public LanguageLoader(Context context) {
        super(context);
    }

    @Override
    public List<Language> loadInBackground() {
        DBTableQueryHelper<Language> dbQueryHelper = new DBTableQueryHelper<>(getContext());
        return dbQueryHelper.query(new LanguageTable().selectId().selectLanguageNames(TranslationTableField.forLanguage()));
    }

}
