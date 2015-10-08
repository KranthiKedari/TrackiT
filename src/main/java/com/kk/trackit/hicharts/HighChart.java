package com.kk.trackit.hicharts;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by kkedari on 8/6/15.
 */
public class HighChart {
    @JsonProperty("chart")
    private Chart chart;

    @JsonProperty("title")
    private Title title;

    @JsonProperty("subtitle")
    private Title subtitle;

    @JsonProperty("xAxis")
    private Axis xAxis;

    @JsonProperty("yAxis")
    private List<Axis> yAxis;

    @JsonProperty("legend")
    private Legend legend;

    @JsonProperty("tooltip")
    private ToolTip tooltip;

    @JsonProperty("plotOptions")
    private Object plotOptions;

    @JsonProperty("series")
    private List<Series> series;

    public HighChart() {
        this.chart = new Chart();
        this.title = new Title();
        this.subtitle = new Title();
        this.legend = new Legend();
        this.tooltip = new ToolTip();
    }


    public Chart getChart() {
        return chart;
    }

    public void setChart(Chart chart) {
        this.chart = chart;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public Title getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(Title subtitle) {
        this.subtitle = subtitle;
    }

    public Axis getxAxis() {
        return xAxis;
    }

    public void setxAxis(Axis xAxis) {
        this.xAxis = xAxis;
    }

    public List<Axis> getyAxis() {
        return yAxis;
    }

    public void setyAxis(List<Axis> yAxis) {
        this.yAxis = yAxis;
    }

    public Legend getLegend() {
        return legend;
    }

    public void setLegend(Legend legend) {
        this.legend = legend;
    }

    public ToolTip getTooltip() {
        return tooltip;
    }

    public void setTooltip(ToolTip tooltip) {
        this.tooltip = tooltip;
    }

    public Object getPlotOptions() {
        return plotOptions;
    }

    public void setPlotOptions(Object plotOptions) {
        this.plotOptions = plotOptions;
    }

    public List<Series> getSeries() {
        return series;
    }

    public void setSeries(List<Series> series) {
        this.series = series;
    }
}
