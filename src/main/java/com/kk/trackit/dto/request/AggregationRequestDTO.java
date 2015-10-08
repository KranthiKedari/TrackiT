package com.kk.trackit.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kk.trackit.dto.aggregations.AggregationDTO;
import com.kk.trackit.dto.aggregations.GraphElement;

/**
 * Created by kkedari on 8/31/15.
 */
public class AggregationRequestDTO {

    @JsonProperty("aggregation")
    private AggregationDTO aggregationDTO;

    @JsonProperty("element")
    private GraphElement graphElement;


    public AggregationRequestDTO() {

    }

    public AggregationRequestDTO(AggregationDTO aggregationDTO, GraphElement graphELement) {
        this.aggregationDTO = aggregationDTO;
        this.graphElement = graphELement;
    }
    public AggregationDTO getAggregationDTO() {
        return aggregationDTO;
    }

    public void setAggregationDTO(AggregationDTO aggregationDTO) {
        this.aggregationDTO = aggregationDTO;
    }

    public GraphElement getGraphElement() {
        return graphElement;
    }

    public void setGraphElement(GraphElement graphElement) {
        this.graphElement = graphElement;
    }
}
