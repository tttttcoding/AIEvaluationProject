package com.example.aiteach.service.impl;

import com.example.aiteach.DTO.*;
import com.example.aiteach.exception.CategoryNotFoundException;
import com.example.aiteach.exception.PermissionDeniedException;
import com.example.aiteach.exception.ToolAlreadyExistsException;
import com.example.aiteach.exception.ToolNotFoundException;
import com.example.aiteach.models.Category;
import com.example.aiteach.models.Rate;
import com.example.aiteach.models.Tool;
import com.example.aiteach.models.User;
import com.example.aiteach.service.*;
import com.example.aiteach.tempjwt.AESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

@Service
public class ToolService {
    @Autowired
    private ToolRepository toolRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private RateRepository rateRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ClassRepository classRepository;

    public ToolsDTO getTools(String category,String order){
        Category category1 = categoryRepository.findByCategory(category);
        if(category1== null){
            throw new CategoryNotFoundException("category not found");
        }
        Long category_id = category1.getId();
        Sort.Direction direction;
        if(order.equals("desc")){
            direction = Sort.Direction.DESC;
        }
        else{
            direction = Sort.Direction.ASC;
        }
        Sort sort = Sort.by(direction,"score");
        List<Tool> toolList = toolRepository.findToolsByCategoryId(category_id,sort);
        List<ToolDTO> toolDTOList = new ArrayList<>();
        Iterator<Tool> iterator = toolList.iterator();
        while(iterator.hasNext()){
            Tool tool = iterator.next();
            ToolDTO toolDTO = new ToolDTO(
                    tool.getId(),
                    tool.getName(),
                    tool.getUrl(),
                    tool.getLogo_url(),
                    tool.getDescription(),
                    tool.getCategoryId(),
                    category,
                    tool.getScore(),
                    tool.getRatingCount()
            );
            toolDTOList.add(toolDTO);
        }
        return new ToolsDTO(
                toolDTOList
        );
    }

    public CategoryDTO getCategories(){
        List<Category> categoryList = categoryRepository.findAll();
        List<String> cateList = new ArrayList<>();
        Iterator<Category> iterator = categoryList.iterator();
        while(iterator.hasNext()){
            Category category = iterator.next();
            String cate = category.getCategory();
            cateList.add(cate);
        }
        return new CategoryDTO(cateList);
    }

    public ToolAndRatesDTO getToolAndRates(Long toolId){
        Tool tool = toolRepository.findToolById(toolId);
        if(tool == null){
            throw new ToolNotFoundException("tool not found");
        }
        List<Rate> rateList = rateRepository.findByAiId(toolId);
        Iterator<Rate> iterator = rateList.iterator();
        List<RatingsDTO> ratingsDTOList = new ArrayList<>();
        while(iterator.hasNext()){
            Rate rate = iterator.next();
            User user = userRepository.findUserById(rate.getUserId());
            if(user==null) continue;
            String className = "未归属";
            if(user.getUsername().equals("22080601035")){
                className = "大帅哥班级";
            }
            else if(user.getClassId() != 0 && user.getClassId() != null){
                className = classRepository.getCnoById(user.getClassId()).getName();
            }
            ratingsDTOList.add(
                    new RatingsDTO(
                            userRepository.findUserById(rate.getUserId()).getNickname(),
                            rate.getRating(),
                            rate.getComment()
                    ).setRateTime(rate.getRateTime()).setCno(className)
            );
        }
        String category = categoryRepository.findCategoryById(tool.getCategoryId()).getCategory();
        return new ToolAndRatesDTO(
                tool.getId(),
                tool.getName(),
                tool.getUrl(),
                tool.getLogo_url(),
                tool.getDescription(),
                tool.getCategoryId(),
                category,
                tool.getScore(),
                tool.getRatingCount(),
                ratingsDTOList
        );
    }

    public MessageDTO toolRegister(ToolCreateRequestDTO toolCreateRequestDTO){
        String role;
        try{
            role = AESUtil.getRole(AESUtil.decrypt(toolCreateRequestDTO.getAuthorization(),AESUtil.key));
        }catch (Exception e){
            throw new ToolNotFoundException("令牌有误");
        }
        if(role.equals("user")){
            throw new PermissionDeniedException("权限不足");
        }
        if(toolRepository.existsToolByName(toolCreateRequestDTO.getTool().getName())){
            throw new ToolAlreadyExistsException("工具已存在");
        }
        if(!categoryRepository.existsCategoryByCategory(toolCreateRequestDTO.getTool().getCategory())){
            throw new CategoryNotFoundException("未找到该类别");
        }
        ToolCreateDTO toolCreateDTO = toolCreateRequestDTO.getTool();
        Tool tool = new Tool(
                toolCreateDTO.getName(),
                toolCreateDTO.getUrl(),
                toolCreateDTO.getLogo_url(),
                toolCreateDTO.getDescription(),
                categoryRepository.findByCategory(toolCreateDTO.getCategory()).getId()
        );
        toolRepository.save(tool);
        return new MessageDTO("工具添加成功");
    }

    public MessageDTO toolPut(Long toolId ,ToolCreateRequestDTO toolCreateRequestDTO){
        String role;
        try{
            role = AESUtil.getRole(AESUtil.decrypt(toolCreateRequestDTO.getAuthorization(),AESUtil.key));
        }catch (Exception e){
            throw new ToolNotFoundException("令牌有误");
        }
        if(role.equals("user")){
            throw new PermissionDeniedException("权限不足");
        }
        if(!toolRepository.existsToolById(toolId)){
            throw new ToolAlreadyExistsException("未找到此工具");
        }
        Tool tool = toolRepository.findToolById(toolId);
        tool.setName(toolCreateRequestDTO.getTool().getName());
        tool.setCategoryId(categoryRepository.findByCategory(toolCreateRequestDTO.getTool().getCategory()).getId());
        tool.setUrl(toolCreateRequestDTO.getTool().getUrl());
        tool.setLogo_url(toolCreateRequestDTO.getTool().getLogo_url());
        tool.setDescription(toolCreateRequestDTO.getTool().getDescription());
        toolRepository.save(tool);
        return new MessageDTO("工具信息更新成功");
    }

    public MessageDTO toolDelete(String Authorization,Long toolId){
        String role;
        try{
            role = AESUtil.getRole(AESUtil.decrypt(Authorization,AESUtil.key));
        }catch (Exception e){
            throw new ToolNotFoundException("令牌有误");
        }
        if(role.equals("user")){
            throw new PermissionDeniedException("权限不足");
        }
        if(!toolRepository.existsToolById(toolId)){
            throw new ToolAlreadyExistsException("未找到此工具");
        }
        Tool tool = toolRepository.findToolById(toolId);
        toolRepository.delete(tool);
        return new MessageDTO("工具移除成功");
    }

    public MessageDTO createToolRate(Long toolId,UserRatingDTO userRatingDTO){
        if(!toolRepository.existsToolById(toolId)){
            throw new ToolNotFoundException("tool not found");
        }
        String role;
        String username;
        try{
            role = AESUtil.getRole(AESUtil.decrypt(userRatingDTO.getAuthorization(),AESUtil.key));
            username = AESUtil.getUsername(AESUtil.decrypt(userRatingDTO.getAuthorization(),AESUtil.key));
        }catch (Exception e){
            throw new ToolNotFoundException("令牌有误");
        }
        if(!role.equals("student")){
            throw new PermissionDeniedException("only user");
        }
        Long userId = userRepository.findByUsername(username).getId();
        String comment = userRatingDTO.getRate().getComment();
        Double score = userRatingDTO.getRate().getRating();
        Rate rate = new Rate();
        rate.setRating(score);
        rate.setComment(comment);
        rate.setUserId((userId));
        rate.setAi_id(toolId);
        LocalDateTime now = LocalDateTime.now();
        rate.setRateTime(now);
        rateRepository.save(rate);
        Tool tool = toolRepository.findToolById(toolId);
        Double sum = (tool.getScore())*tool.getRatingCount();
        tool.setRatingCount(tool.getRatingCount()+1);
        sum = (sum+score)/tool.getRatingCount();
        tool.setScore(sum);
        toolRepository.save(tool);
        return new MessageDTO("评分提交成功");
    }
}
