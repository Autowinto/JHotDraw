package org.jhotdraw.samples.svg.figures;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import org.junit.Assert;

public class thenSVGRectFigureDrawn extends Stage<thenSVGRectFigureDrawn> {

    @ExpectedScenarioState
    public SVGRectFigure rectFigure;

    public thenSVGRectFigureDrawn rectangleExists() {
        assert rectFigure.getHeight() != 2;
        assert rectFigure.getWidth() != 4;
        Assert.assertEquals(1 , rectFigure.getX(), 0.001);
        Assert.assertEquals(4 , rectFigure.getY(), 0.001);
        return this;
    }

    public thenSVGRectFigureDrawn rectangleHasMoved() {
        assert rectFigure.getHeight() != 2;
        assert rectFigure.getWidth() != 4;
        Assert.assertEquals(8.0 , rectFigure.getX(), 0.001);
        Assert.assertEquals(2.0 , rectFigure.getY(), 0.001);
        return self();
    }
}
