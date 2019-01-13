package com.andy.util;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.kjetland.jackson.jsonSchema.JsonSchemaConfig;
import com.kjetland.jackson.jsonSchema.JsonSchemaGenerator;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.ValidationMessage;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;

/**
 * Created By Ray on 2017/9/22.
 */
@Component
public class JsonUtil {

    private static Map<Class,JsonSchema> objectSchema = new HashMap<Class, JsonSchema>();
    private static Map<Class,JsonSchema> arraySchema  = new HashMap<Class, JsonSchema>();

    /**
     * JSON 验证对象
     *
     * @param clazz
     * @param jsonNode
     * @return
     */
    public  Set<ValidationMessage> validate(Class clazz, JsonNode jsonNode){
        JsonSchema schema = objectSchema.get(clazz);
        if (schema == null){
            JsonSchemaFactory factory = new JsonSchemaFactory();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
            JsonSchemaGenerator jsonSchemaGenerator = new JsonSchemaGenerator(objectMapper, JsonSchemaConfig.html5EnabledSchema());
            JsonNode jsonSchema = jsonSchemaGenerator.generateJsonSchema(clazz);
            schema = factory.getSchema(jsonSchema);
            objectSchema.put(clazz,schema);
        }

        return schema.validate(jsonNode);
    }

    /**
     * JSON 验证对象
     *
     * @param clazz
     * @param jsonString
     * @return
     */
    public Set<ValidationMessage> validate(Class clazz, String jsonString){

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode =  mapper.readTree(jsonString);
            return validate(clazz,jsonNode);
        } catch (IOException e) {
            ValidationMessage.Builder builder = new ValidationMessage.Builder();
            return new HashSet<ValidationMessage>(Arrays.asList(builder.code("1000").path("$").format(new MessageFormat("{0}: is not valid json string")).type("all").build()));
        }

    }

    /**
     * JSON 验证数组
     *
     * @param clazz
     * @param jsonNode
     * @return
     */
    public Set<ValidationMessage> validateArray(Class clazz, JsonNode jsonNode) {
        JsonSchema schema = arraySchema.get(clazz);
        if (schema == null){
            JsonSchemaFactory factory = new JsonSchemaFactory();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
            JsonSchemaGenerator jsonSchemaGenerator = new JsonSchemaGenerator(objectMapper, JsonSchemaConfig.html5EnabledSchema());
            JsonNode jsonSchema = jsonSchemaGenerator.generateJsonSchema(objectMapper.getTypeFactory().constructArrayType(clazz));
            schema = factory.getSchema(jsonSchema);
            arraySchema.put(clazz,schema);
        }

        return schema.validate(jsonNode);
    }

    /**
     * JSON 验证数组
     *
     * @param clazz
     * @param jsonString
     * @return
     */
    public Set<ValidationMessage> validateArray(Class clazz, String jsonString){

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode =  mapper.readTree(jsonString);
            return validateArray(clazz,jsonNode);
        } catch (IOException e) {
            ValidationMessage.Builder builder = new ValidationMessage.Builder();
            return new HashSet<ValidationMessage>(Arrays.asList(builder.code("1000").path("$").format(new MessageFormat("{0}: is not valid json string")).type("all").build()));
        }

    }

    /**
     * 对象转JSON字符串
     *
     * @param object
     * @return
     */
    public String toJsonString(Object object){
        if (object == null){
            return null;
        }
        return JSON.toJSONString(object);
    }



    /**
     * JSON字符串转对象
     *
     * @param jsonString
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T parse(String jsonString, Class<T> clazz) {
        return JSON.parseObject(jsonString, clazz);
    }

    /**
     * JSON字符串转数组对象
     *
     * @param jsonString
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> List<T> parseArray(String jsonString, Class<T> clazz){
        return JSON.parseArray(jsonString,clazz);
    }


    /**
     *将json转换为具体业务实体bean
     * @param data
     * @param outputClass
     * @return
     */
    public  <T> T json2Object(String data,Class<T> outputClass){
        ObjectMapper mapper = new ObjectMapper();
        try {
            if(StringUtil.isNotEmpty(data)){
                return mapper.readValue(data,outputClass);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将指定的json串
     * @param jsonStr
     * @return
     */
    public  Map json2Map(String jsonStr){
        ObjectMapper mapper = new ObjectMapper();
        try {
            if(StringUtil.isNotEmpty(jsonStr)){
                return mapper.readValue(jsonStr, Map.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new HashMap();
    }

    /**
     * 将map转化成json串
     * @param map
     * @return
     */
    public  String map2Json(Map map){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(map);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将object转化成json串
     * @param obj
     * @return
     */
    public String object2Json(Object obj){
        if (obj == null){
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将object转化成json串，将null转换成了空字符串
     * @param obj
     * @return
     */
    public String object2JsonNoNull(Object obj){
        if (obj == null){
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer() {
            @Override
            public void serialize(Object arg0, JsonGenerator arg1, SerializerProvider arg2) throws IOException, JsonProcessingException {
                arg1.writeString("");
            }
        });
        try {
            return mapper.writeValueAsString(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
