package bachelorDegree.service;

import bachelorDegree.model.Oscillator;
import org.jzy3d.analysis.AbstractAnalysis;
import org.jzy3d.chart.factories.AWTChartComponentFactory;
import org.jzy3d.colors.Color;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.maths.Range;
import org.jzy3d.plot3d.builder.Builder;
import org.jzy3d.plot3d.builder.Mapper;
import org.jzy3d.plot3d.builder.concrete.OrthonormalGrid;
import org.jzy3d.plot3d.primitives.Shape;
import org.jzy3d.plot3d.rendering.canvas.Quality;

public class Oscillator2DMapper extends AbstractAnalysis {
    private Oscillator oscillatorX;
    private Oscillator oscillatorY;

    public Oscillator2DMapper setParameters(Oscillator oscillatorX, Oscillator oscillatorY) {
        this.oscillatorX = oscillatorX;
        this.oscillatorY = oscillatorY;
        return this;
    }

    @Override
    public void init(){
        Mapper mapper = new Mapper() {
            @Override
            public double f(double x, double y) {
                return Math.pow(oscillatorX.getValueOfArgument(x),2)
                        *
                        Math.pow(oscillatorY.getValueOfArgument(y),2);
            }
        };
        int steps = 100;
        float L = dynamicRange(oscillatorX.getN(),oscillatorY.getN());
        Range range = new Range(-L/2,L/2);
        final Shape surface = Builder.buildOrthonormal(new OrthonormalGrid(range, steps, range, steps), mapper);
        surface.setColorMapper(new ColorMapper(
                new ColorMapRainbow(),
                surface.getBounds().getZmin(),
                surface.getBounds().getZmax(),
                new Color(1, 1, 1,0.9f)));

        surface.setFaceDisplayed(true);
        surface.setWireframeDisplayed(false);

        // Creating a chart
        chart = AWTChartComponentFactory.chart(Quality.Advanced, getCanvasType());
        chart.getScene().getGraph().add(surface);


    }
    public static float dynamicRange(int nX, int nY){
        return (Math.max(nX,nY))/6.0f + 5.0f;
    }
}
