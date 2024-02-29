package org.jhotdraw.app.action.file;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;

import org.jhotdraw.api.app.Application;
import org.junit.Test;
import org.mockito.Mockito;

public class GivenApplicationShown extends Stage<GivenApplicationShown> {

    @ProvidedScenarioState
    OpenFileAction openFileAction;

    @Test
    public GivenApplicationShown the_application_is_shown() {
        Application app = Mockito.mock(Application.class);
        this.openFileAction = new OpenFileAction(app);
        return this;
    }
}
