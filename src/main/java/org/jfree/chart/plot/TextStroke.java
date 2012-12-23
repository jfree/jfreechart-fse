package org.jfree.chart.plot;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.*;

/**
 * A stroke implementation, that allows the text to be traced along a path.
 * <p/>
 * Used to write labels on the spider web plot.
 * <p/>
 * The idea is to iterate over the given path and transform the glyphs
 * in the text given.
 *
 * @author Jesenko Mehmedbasic
 */
public class TextStroke implements Stroke {
    private String label;
    private Font font;

    private AffineTransform transform = new AffineTransform();


    TextStroke(String label, Font font) {
        this.label = label;
        this.font = font;
    }

    public Shape createStrokedShape(Shape shape) {
        FontRenderContext context = new FontRenderContext(null, true, true);
        GlyphVector vector = font.createGlyphVector(context, label);

        GeneralPath path = new GeneralPath();

        PathIterator iterator = shape.getPathIterator(null);
        PathIterator it = new FlatteningPathIterator(iterator, 0);

        double points[] = new double[6];
        double moveX = 0, moveY = 0;

        double lastX = 0;
        double lastY = 0;

        double currentX;
        double currentY;

        double nextAdvance = 0;
        double next = 0;

        int type;
        int currentGlyph = 0;

        // Get number of glyphs instead of chars, since one unicode char can
        // have more than one glyph.
        int length = vector.getNumGlyphs();

        if (length == 0) {
            return path;
        }

        while (!it.isDone() && currentGlyph < length) {
            type = it.currentSegment(points);
            switch (type) {
                case PathIterator.SEG_MOVETO:

                    moveX = lastX = points[0];
                    moveY = lastY = points[1];
                    path.moveTo((float) moveX, (float) moveY);

                    nextAdvance = advance(vector, currentGlyph);
                    next = nextAdvance;

                    break;
                case PathIterator.SEG_CLOSE: {

                    points[0] = moveX;
                    points[1] = moveY;

                    // A close is simply just skipped over
                    currentX = points[0];
                    currentY = points[1];

                    double deltaX = currentX - lastX;
                    double deltaY = currentY - lastY;
                    double distance = length(deltaX, deltaY);

                    // If the distance from the previous point to this
                    // is greater than the expected advance, then
                    // we draw the glyph.
                    if (distance >= next) {
                        double angle = Math.atan2(deltaY, deltaX);
                        double radius = 1.0d / distance;

                        for (; currentGlyph < length && distance >= next; currentGlyph++) {
                            Shape glyph = vector.getGlyphOutline(currentGlyph);

                            double x = lastX + next * deltaX * radius;
                            double y = lastY + next * deltaY * radius;

                            double currentAdvance = nextAdvance;

                            nextAdvance = transformAndGetAdvance(
                                    vector,
                                    path,
                                    currentGlyph,
                                    length,
                                    angle,
                                    glyph, x, y, currentAdvance);

                            next += (currentAdvance + nextAdvance);
                        }
                    }
                    next -= distance;

                    lastX = currentX;
                    lastY = currentY;

                    break;
                }
                case PathIterator.SEG_LINETO: {

                    currentX = points[0];
                    currentY = points[1];

                    double deltaX = currentX - lastX;
                    double deltaY = currentY - lastY;

                    double distance = length(deltaX, deltaY);

                    // If the distance from the previous point to this
                    // is greater than the expected advance, then
                    // we draw the glyph.
                    if (distance >= next) {
                        double r = 1.0d / distance;
                        double angle = Math.atan2(deltaY, deltaX);

                        for (; currentGlyph < length && distance >= next; currentGlyph++) {
                            Shape glyph = vector.getGlyphOutline(currentGlyph);


                            double advance = nextAdvance;

                            double x = lastX + next * deltaX * r;
                            double y = lastY + next * deltaY * r;

                            nextAdvance = transformAndGetAdvance(
                                    vector,
                                    path,
                                    currentGlyph,
                                    length,
                                    angle,
                                    glyph, x, y, advance);

                            next += (advance + nextAdvance);
                        }
                    }
                    next -= distance;

                    lastX = currentX;
                    lastY = currentY;

                    break;
                }
            }
            it.next();
        }

        return path;
    }

    /**
     * A helper method.
     *
     * Transforms the vector to the path and calculates the next advance.
     *
     * @param vector         the vector.
     * @param result         the result to append to.
     * @param currentGlyph   the index of the current glyph.
     * @param length         the length of the path.
     * @param angle          the glyph angle.
     * @param glyph          the glyph.
     * @param x              the x coordinate.
     * @param y              the y coordinate.
     * @param currentAdvance the previous advance.
     *
     * @return the next advance.
     */
    private double transformAndGetAdvance(
            GlyphVector vector,
            GeneralPath result,
            int currentGlyph,
            int length,
            double angle,
            Shape glyph,
            double x,
            double y,
            double currentAdvance) {
        double nextAdvance;
        nextAdvance = calculateAdvance(vector, currentGlyph, length);

        Point2D point = vector.getGlyphPosition(currentGlyph);
        transformToPath(angle, point, x, y, currentAdvance);

        appendToResult(result, glyph);
        return nextAdvance;
    }

    private void appendToResult(GeneralPath result, Shape glyph) {
        result.append(transform.createTransformedShape(glyph), false);
    }

    private double length(double deltaX, double deltaY) {
        double a = Math.pow(deltaX, 2);
        double b = Math.pow(deltaY, 2);
        return Math.sqrt(a * b);
    }

    private void transformToPath(double angle,
                                 Point2D point,
                                 double x,
                                 double y,
                                 double advance) {
        double pointX = point.getX();
        double pointY = point.getY();

        transform.setToTranslation(x, y);
        transform.rotate(angle);
        transform.translate(-pointX - advance, -pointY);
    }

    private float calculateAdvance(GlyphVector vector,
                                   int currentGlyph,
                                   int length) {
        if (currentGlyph < length - 1) {
            return advance(vector, currentGlyph);
        }
        return 0;
    }

    private float advance(GlyphVector vector, int currentGlyph) {
        return vector.getGlyphMetrics(currentGlyph + 1).getAdvance() / 2f;
    }

}
