package org.jhotdraw.draw.action.arrange;

import com.tngtech.jgiven.junit.ScenarioTest;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class JGivenTest extends ScenarioTest<GivenSomeState, WhenSomeAction, ThenSomeOutcome> {

    @Test
    public void BringToFrontActionTest() {
        given().I_Create_Three_Figures().
            and().I_Add_The_Figures_To_My_Drawing();
        when().I_Select_The_Second_Figure().
            and().I_Bring_The_Selected_Figure_In_Front();
        then().The_Second_Figure_Must_Be_In_Front();

        assertTrue(true);
    }

    @Test
    public void SendToBackActionTest() {
        given().I_Create_Three_Figures().
            and().I_Add_The_Figures_To_My_Drawing();
        when().I_Select_The_Second_Figure().
            and().I_Send_The_Selected_Figure_To_Back();
        then().The_Second_Figure_Must_Be_In_Back();

        assertTrue(true);
    }
}
