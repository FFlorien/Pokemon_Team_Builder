package be.florien.teambuilder.loader;

import android.content.Context;

import be.florien.teambuilder.database.helper.DBPokedexHelper;
import be.florien.teambuilder.model.table.GenerationTable;
import be.florien.joinorm.queryhandling.JOQueryHelper;
import be.florien.teambuilder.database.table.TranslationTableField;
import be.florien.teambuilder.model.Generation;

import java.util.List;

public class GenerationLoader extends AbstractAsyncTaskLoader<List<Generation>> {

    public GenerationLoader(Context context) {
        super(context);
    }

    @Override
    public List<Generation> loadInBackground() {
        GenerationTable table = new GenerationTable().selectId().selectGenerationNames(TranslationTableField.forGeneration());
        JOQueryHelper dbQueryHelper = new JOQueryHelper(new DBPokedexHelper(getContext()));
        return  dbQueryHelper.queryList(table);
    }

}
