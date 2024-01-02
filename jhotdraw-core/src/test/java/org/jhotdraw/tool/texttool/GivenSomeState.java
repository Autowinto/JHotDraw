package org.jhotdraw.tool.texttool;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import org.jhotdraw.draw.*;
import org.jhotdraw.draw.figure.TextFigure;

public class GivenSomeState extends Stage<GivenSomeState> {

    @ProvidedScenarioState
    DrawingView view = new DefaultDrawingView();

    @ProvidedScenarioState
    Drawing drawing = new DefaultDrawing();

    @ProvidedScenarioState
    TextFigure figure;

    public GivenSomeState I_Create_A_Text_Object() {
        figure = new TextFigure();

        return this;
    }

    public GivenSomeState I_Add_The_Text_To_My_Drawing() {
        drawing.add(figure);
        view.setDrawing(drawing);

        return this;
    }

    public GivenSomeState I_Choose_A_Text_Object() {
        figure = new TextFigure("Some text");
        drawing.add(figure);
        view.setDrawing(drawing);

        return this;
    }

}
