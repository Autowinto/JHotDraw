package org.jhotdraw.app.action.file;

import com.tngtech.jgiven.junit.ScenarioTest;
import org.junit.Test;

public class BDDTest extends ScenarioTest<GivenApplicationShown, WhenUserOpensFile, ThenLoadFile> {
    @Test
    public void The_user_wants_to_open_a_file() {
        given().the_application_is_shown();
        when().the_user_wants_to_open_a_file();
        then().the_user_select_a_file_and_the_file_is_opened();
    }
}
