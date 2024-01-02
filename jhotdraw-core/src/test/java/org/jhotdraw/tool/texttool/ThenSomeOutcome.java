package org.jhotdraw.tool.texttool;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.figure.TextFigure;
import org.junit.Assert;


public class ThenSomeOutcome extends Stage<ThenSomeOutcome> {
    @ExpectedScenarioState
    DrawingView view;
    @ExpectedScenarioState
    TextFigure figure;

    public ThenSomeOutcome The_Text_Must_Be_The_Same() {
        Drawing drawing = view.getDrawing();

        TextFigure figureA = (TextFigure)  drawing.getChild(0);
        String text = figureA.getText();

        Assert.assertSame(text, figure.getText());

        return this;
    }

    public ThenSomeOutcome Unedited_Text_Must_Be_The_Same() {
        Drawing drawing = view.getDrawing();

        TextFigure figureA = (TextFigure) drawing.getChild(0);
        String text = figureA.getText();

        Assert.assertSame(text, figure.getText());

        return this;
    }


}
