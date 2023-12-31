package org.jhotdraw.gui.BDD.Display;

import com.tngtech.jgiven.junit5.ScenarioTest;
import org.junit.jupiter.api.Test;

public class ToolPaletteTestDisplay extends ScenarioTest<GivenToolPaletteDisplay, WhenUserInteractsDisplay, ThenOutcomeDisplay> {

    @Test
    void scenario_The_user_wants_to_hide_a_palette_on_the_tool_bar() {
        given().a_tool_palette_is_shown();
        when().the_user_wants_to_hide_a_given_palette();
        then().the_user_clicks_the_hide_button_and_the_palette_is_hidden();
    }
}
