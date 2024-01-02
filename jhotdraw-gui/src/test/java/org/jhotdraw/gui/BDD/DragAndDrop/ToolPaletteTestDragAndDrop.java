package org.jhotdraw.gui.BDD.DragAndDrop;

import com.tngtech.jgiven.junit5.ScenarioTest;
import org.junit.jupiter.api.Test;

class ToolPaletteTestDragAndDrop extends ScenarioTest<GivenToolPaletteDragAndDrop, WhenUserInteractsDragAndDrop, ThenOutcomeDragAndDrop> {

    @Test
    void Scenario_Toolbar_can_be_dragged_from_theTool_palette_bar_by_the_user() {
        given().the_tool_palette_is_visible_and_can_be_interacted_with();
        when().the_user_wants_to_change_the_position_of_a_tool();
        then().the_toolbar_is_placed_according_to_user_preference();
    }
}
