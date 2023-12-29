package org.jhotdraw.draw.action.arrange;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.action.BringToFrontAction;
import org.jhotdraw.draw.action.SendToBackAction;
import org.jhotdraw.draw.figure.Figure;
import java.util.LinkedHashSet;
import java.util.Set;

public class WhenSomeAction extends Stage<WhenSomeAction> {

    @ExpectedScenarioState
    DrawingView view;

    @ProvidedScenarioState
    Set<Figure> selectedFigures;


    public WhenSomeAction I_Select_The_Second_Figure() {
            this.selectedFigures = new LinkedHashSet<>();

            this.selectedFigures.add(this.view.getDrawing().getFiguresFrontToBack().get(1));

            return this;
    }

    public WhenSomeAction I_Bring_The_Selected_Figure_In_Front() {
        BringToFrontAction.bringToFront(this.view, this.selectedFigures);

        return this;
    }

    public WhenSomeAction I_Send_The_Selected_Figure_To_Back() {
        SendToBackAction.sendToBack(this.view, this.selectedFigures);

        return this;
    }
}
