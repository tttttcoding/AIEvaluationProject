package com.example.aiteach.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ToolsDTO {
    @JsonProperty("tools")
    private List<ToolDTO> toolList;

    public ToolsDTO(List<ToolDTO> toolList) {
        this.toolList = toolList;
    }

    @JsonProperty("tools")
    public List<ToolDTO> getToolList() {
        return toolList;
    }

    @JsonProperty("tools")
    public void setToolList(List<ToolDTO> toolList) {
        this.toolList = toolList;
    }
}
