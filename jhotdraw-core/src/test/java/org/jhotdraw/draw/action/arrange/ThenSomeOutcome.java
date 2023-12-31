package org.jhotdraw.draw.action.arrange;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.figure.Figure;
import java.util.Set;
import static org.junit.Assert.assertSame;

public class ThenSomeOutcome extends Stage<ThenSomeOutcome> {

    @ExpectedScenarioState
    DrawingView view;

    @ExpectedScenarioState
    Figure figureB;

    @ExpectedScenarioState
    Set<Figure> selectedFigures;

    public ThenSomeOutcome The_Second_Figure_Must_Be_In_Front() {

        Drawing drawing = this.view.getDrawing();

        Figure figureInFront = drawing.getFiguresFrontToBack().get(0);

        assertSame(figureInFront, this.figureB);

        return this;
    }

    public ThenSomeOutcome The_Second_Figure_Must_Be_In_Back() {

        Drawing drawing = this.view.getDrawing();

        Figure figureInFront = drawing.getFiguresFrontToBack().get(drawing.getFiguresFrontToBack().size() - 1);

        assertSame(figureInFront, this.figureB);

        return this;
    }
}
