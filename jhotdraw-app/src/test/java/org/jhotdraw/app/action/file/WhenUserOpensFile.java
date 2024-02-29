package org.jhotdraw.app.action.file;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import org.mockito.Mockito;

import java.awt.event.ActionEvent;

public class WhenUserOpensFile extends Stage<WhenUserOpensFile> {

    @ExpectedScenarioState
    OpenFileAction openFileAction;

    public WhenUserOpensFile the_user_wants_to_open_a_file() {
        ActionEvent mockedEvent = Mockito.mock(ActionEvent.class);
        openFileAction.actionPerformed(mockedEvent);
        return this;
    }
}
