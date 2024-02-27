package org.jhotdraw.tool.texttool;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import org.jhotdraw.draw.figure.TextFigure;
import org.jhotdraw.draw.text.FloatingTextField;

public class WhenSomeAction extends Stage<WhenSomeAction> {

    @ProvidedScenarioState
    TextFigure figure;

    @ProvidedScenarioState
    FloatingTextField textField;

    public WhenSomeAction I_Write_In_My_Text_Object() {
        figure.setText("Written text!");
        return this;
    }
    public WhenSomeAction I_Select_The_Text_Object() {
        textField = new FloatingTextField();
        textField.requestFocus();

        return this;
    }
}
