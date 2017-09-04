package com.ace.ucv;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DotNotationExtractor {

    private ObjectMapper  mapper;

    public DotNotationExtractor() {
        mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public String flatten(Organization organization) throws JsonProcessingException {
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, Object> map = extract(organization);
        map.forEach((key,value)->{
            stringBuilder.append("\"");
            stringBuilder.append(key);
            stringBuilder.append("\":");
            if(value instanceof JSONArray){
                stringBuilder.append(value);
                stringBuilder.append(",");
            }
            else{
                stringBuilder.append("\"");
                stringBuilder.append(value);
                stringBuilder.append("\",");
            }
        });

        String result = stringBuilder.toString();

        return result.substring(0, result.length() - 1);
    }

    public Map<String, Object> extract(Organization organization) throws JsonProcessingException {
        Map<String,Object> dotNotation = new HashMap<>();
        String json = mapper.writeValueAsString(organization);
        System.out.println(json);
        JSONObject jsonObject = new JSONObject(json);
        extractDotNotation(jsonObject,null,dotNotation);

        return dotNotation;
    }

    private void extractDotNotation(JSONObject jsonObject, String parrent, Map<String, Object> map){
        Set<String> keys = jsonObject.keySet();
        String parentWithDot = null;
        if(parrent != null){
            parentWithDot = parrent + ".";
        }
        else{
            parentWithDot = "";
        }


        for(String key : keys){
            Object value = jsonObject.get(key);
            if(value instanceof JSONObject){
                extractDotNotation((JSONObject) value, parentWithDot + key,map);
            }
            else{
                map.put(parentWithDot + key,value);
            }
        }
    }
}
