package org.jhotdraw.samples.svg.figures;

/**
 * The `ArcControlValueCalculator` class is responsible for calculating the control
 * value used in drawing rounded rectangles to control the curvature of the corners.
 */
public class ArcControlValueCalculator {

    /**
     * Private constructor to prevent instantiation of the `ArcControlValueCalculator` class.
     */
    private ArcControlValueCalculator() {
    }

    /**
     * Calculates the control value for the arc of a rounded rectangle.
     *
     * @return The calculated arc control value.
     */
    public static double calculateArcControlValue() {
        double angle = calculateAngle();
        double a = calculateA(angle);
        double b = calculateB(angle);
        double c = calculateC(a, b);
        return calculateFinalControlValue(a, b, c);
    }

    private static double calculateAngle() {
        return Math.PI / 4.0;
    }

    private static double calculateA(double angle) {
        return 1.0 - Math.cos(angle);
    }

    private static double calculateB(double angle) {
        return Math.tan(angle);
    }

    private static double calculateC(double a, double b) {
        return Math.sqrt(1.0 + b * b) - 1 + a;
    }

    private static double calculateFinalControlValue(double a, double b, double c) {
        double cv = 4.0 / 3.0 * a * b / c;
        return 1.0 - cv;
    }
}