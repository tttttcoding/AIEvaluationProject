package com.example.aiteach.util;

import java.io.*;
import java.nio.file.Paths;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.aiteach.AiTeachApplication;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AIEvaluateHttpClient{
    private static final String uploadUrl;
    static {
        File urlFile = new File("ModelURL");
        if(!urlFile.exists()){
            throw new RuntimeException("请在ModelURL文件里写入大模型评估的api,ModelURL需要放在该jar包同路径,如果你只是想使用该系统有关模型评估外的功能，只需创建ModelURL文件即可");
        }
        FileReader fileReader;
        char[] cChar = new char[1005];
        int len;
        try {
            fileReader = new FileReader(urlFile);
            len = fileReader.read(cChar);
            if(len!=-1) {
                uploadUrl = new String(cChar,0,len);
            }
            else{
                uploadUrl = null;
                Logger logger = LoggerFactory.getLogger(AiTeachApplication.class);
                logger.warn("ModelURL文件为空，这会导致评价文件评估的功能出现异常");
            }
            fileReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static String postEvaluate(File userFile,String filename){
        try{
            CloseableHttpClient client = HttpClients.createDefault();
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.STRICT);
//            String currentPath = Paths.get("").toAbsolutePath().toString();
//            File systemFile = new File(currentPath+"/"+"system.docx");
//            System.out.println(systemFile.getAbsolutePath());
//            if(!systemFile.exists()){
//                Logger logger = LoggerFactory.getLogger(AiTeachApplication.class);
//                logger.warn(systemFile.getPath()+"找不到system.docx文件，你需要一个提示词文件才可以使用大模型评估的api");
//                return null;
//            }
//            builder.addBinaryBody("user_prompt_file",userFile, ContentType.APPLICATION_OCTET_STREAM,filename)
//                    .addBinaryBody("system_prompt_file",systemFile,ContentType.APPLICATION_OCTET_STREAM, systemFile.getName());
            builder.addBinaryBody("user_file",userFile, ContentType.APPLICATION_OCTET_STREAM,filename);
            HttpEntity entity = builder.build();
            HttpPost request = new HttpPost(uploadUrl);
            request.setEntity(entity);
            CloseableHttpResponse response = client.execute(request);
            String responseBody = EntityUtils.toString(response.getEntity());
            System.out.println(responseBody);
//            ObjectMapper mapper = new ObjectMapper();
//            Response reponse = mapper.readValue(responseBody,Response.class);
//            Pattern pattern = Pattern.compile("\"evaluation\": \"(.*?)\"},");
//            Matcher matcher = pattern.matcher(responseBody);
//            matcher.find();
//            return matcher.group(1);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            return jsonNode.get("data").get("evaluation").asText();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
class Data{
    private String evaluation;

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public Data(String evaluation) {
        this.evaluation = evaluation;
    }
}
class Response{
    private Integer code;
    private Data data;
    private String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Response(Integer code, Data data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }
}