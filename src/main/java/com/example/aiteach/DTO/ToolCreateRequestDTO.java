package com.example.aiteach.DTO;

public class ToolCreateRequestDTO {
    private String Authorization;
    private ToolCreateDTO tool;

    public ToolCreateRequestDTO(String Authorization, ToolCreateDTO tool) {
        this.Authorization = Authorization;
        this.tool = tool;
    }

    public String getAuthorization() {
        return this.Authorization;
    }

    public void setAuthorization(String authorization) {
        this.Authorization = authorization;
    }

    public ToolCreateDTO getTool() {
        return tool;
    }

    public void setTool(ToolCreateDTO tool) {
        this.tool = tool;
    }
}
