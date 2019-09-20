package bachelorDegree.services;

import bachelorDegree.model.Oscillator;
import org.jzy3d.analysis.AbstractAnalysis;
import org.jzy3d.analysis.AnalysisLauncher;
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
    public void start(Oscillator oscillatorX, Oscillator oscillatorY){

        try {
            AnalysisLauncher.open(new Oscillator2DMapper()
                    .setParameters(oscillatorX,oscillatorY));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() throws Exception {
        Mapper mapper = new Mapper() {
            @Override
            public double f(double x, double y) {
                return oscillatorX.getValueOfArgument(x)
                        *oscillatorY.getValueOfArgument(y);
            }
        };
        int steps = 50;
        Range range = new Range(-2,2);
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
}
