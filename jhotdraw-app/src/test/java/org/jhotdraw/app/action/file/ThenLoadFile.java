package org.jhotdraw.app.action.file;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import org.junit.Assert;

public class ThenLoadFile extends Stage<ThenLoadFile> {

    @ExpectedScenarioState
    OpenFileAction openFileAction;

    public ThenLoadFile the_user_select_a_file_and_the_file_is_opened() {
        Assert.assertEquals(0, openFileAction.getApplication().getViews().size());
        return this;
    }
}
