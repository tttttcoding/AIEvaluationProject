package com.example.aiteach.controller;

import com.example.aiteach.DTO.*;
import com.example.aiteach.service.impl.ToolService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ToolController {
    @Autowired
    private ToolService toolService;

    @GetMapping("/tools")
    public ResponseEntity<ToolsDTO> getTools(@RequestParam(required = true) String category,
                                   @RequestParam(required = false,defaultValue = "desc") String order){
        System.out.println(category);
        return new ResponseEntity<>(
                toolService.getTools(category,order),
                HttpStatusCode.valueOf(200)
                );
    }

    @GetMapping("tools/categories")
    public ResponseEntity<CategoryDTO> getCategories(){
        return new ResponseEntity<>(
                toolService.getCategories(),
                HttpStatusCode.valueOf(200)
        );
    }

    @GetMapping("tools/{toolId}")
    public ResponseEntity<ToolAndRatesDTO> getToolAndRates(@PathVariable Long toolId){
        return new ResponseEntity<>(
                toolService.getToolAndRates(toolId),
                HttpStatusCode.valueOf(200)
        );
    }

    @PostMapping("tools")
    public ResponseEntity<MessageDTO> toolRegister(@RequestBody ToolCreateRequestDTO toolCreateRequestDTO){
        return new ResponseEntity<>(
                toolService.toolRegister(toolCreateRequestDTO),
                HttpStatusCode.valueOf(200)
        );
    }

    @PutMapping("tools/{toolId}")
    public ResponseEntity<MessageDTO> toolPut(@PathVariable Long toolId,
                                              @RequestBody ToolCreateRequestDTO toolCreateRequestDTO){
        return new ResponseEntity<>(
                toolService.toolPut(toolId,toolCreateRequestDTO),
                HttpStatusCode.valueOf(200)
        );
    }

    @DeleteMapping("tools/{toolId}")
    public ResponseEntity<MessageDTO> toolDelete(@PathVariable Long toolId,
                                                 @RequestBody AuthorizationDTO authorizationDTO){
        return new ResponseEntity<>(
                toolService.toolDelete(authorizationDTO.getAuthorization(),toolId),
                HttpStatusCode.valueOf(200)
        );
    }

    @PostMapping("tools/{toolId}/ratings")
    public ResponseEntity<MessageDTO> createToolRate(@PathVariable Long toolId,
                                                 @RequestBody UserRatingDTO userRatingDTO){
        return new ResponseEntity<>(
                toolService.createToolRate(toolId,userRatingDTO),
                HttpStatusCode.valueOf(201)
        );
    }
}
