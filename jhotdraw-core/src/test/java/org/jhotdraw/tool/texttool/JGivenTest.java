package org.jhotdraw.tool.texttool;

import com.tngtech.jgiven.junit.ScenarioTest;
import org.junit.Assert;
import org.junit.Test;

public class JGivenTest extends ScenarioTest<GivenSomeState, WhenSomeAction, ThenSomeOutcome> {
    @Test
    public void CreateTextTest() {
        given().I_Create_A_Text_Object().and().I_Add_The_Text_To_My_Drawing();
        when().I_Write_In_My_Text_Object();
        then().The_Text_Must_Be_The_Same();

        Assert.assertTrue(true);
    }

    @Test
    public void EditTextTest() {
        given().I_Choose_A_Text_Object();
        when().I_Select_The_Text_Object();
        then().Unedited_Text_Must_Be_The_Same();
    }
}
