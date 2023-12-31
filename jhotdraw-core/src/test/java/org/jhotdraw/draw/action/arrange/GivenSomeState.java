package org.jhotdraw.draw.action.arrange;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import org.jhotdraw.draw.DefaultDrawing;
import org.jhotdraw.draw.DefaultDrawingView;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.figure.Figure;
import org.jhotdraw.draw.figure.RectangleFigure;

public class GivenSomeState extends Stage<GivenSomeState> {

    @ProvidedScenarioState
    Drawing drawing = new DefaultDrawing();

    @ProvidedScenarioState
    DrawingView view = new DefaultDrawingView();

    @ProvidedScenarioState
    Figure figureB;

    Figure figureA, figureC;

    public GivenSomeState I_Create_Three_Figures() {
        this.figureA = new RectangleFigure(0, 0, 10, 10);
        this.figureB = new RectangleFigure(2, 2, 10, 10);
        this.figureC = new RectangleFigure(4, 4, 10, 10);

        return this;
    }

    public GivenSomeState I_Add_The_Figures_To_My_Drawing() {
        drawing.add(this.figureA);
        drawing.add(this.figureB);
        drawing.add(this.figureC);

        view.setDrawing(drawing);

        return this;
    }
}
