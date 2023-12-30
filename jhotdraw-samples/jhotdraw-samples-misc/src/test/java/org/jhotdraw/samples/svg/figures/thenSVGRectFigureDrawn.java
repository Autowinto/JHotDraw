package org.jhotdraw.samples.svg.figures;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import org.junit.Assert;

import java.awt.image.BufferedImage;

import static org.junit.Assert.assertNotEquals;

public class thenSVGRectFigureDrawn extends Stage<thenSVGRectFigureDrawn> {

    @ExpectedScenarioState
    public SVGRectFigure rectFigure;

    @ExpectedScenarioState
    public BufferedImage bufferedImage;
    @ExpectedScenarioState
    double x;
    @ExpectedScenarioState
    double height;
    public thenSVGRectFigureDrawn rectangleExists() {
        assertNotEquals(1, bufferedImage.getRGB((int) x, (int) (height / 2)));
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
