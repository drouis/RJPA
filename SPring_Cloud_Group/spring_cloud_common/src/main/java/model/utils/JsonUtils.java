package model.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Result;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class JsonUtils {
    private static ObjectMapper mapper = new ObjectMapper();
    public JsonUtils() {
    }

    public static void writeJson(HttpServletResponse response, Result responseData) {
        PrintWriter writer;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        response.setStatus(200);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            writer = response.getWriter();
            writer.write(objectMapper.writeValueAsString(responseData));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> T serializable(String json, Class<T> clazz) {
        if (StringUtils.isEmpty(json)) {
            return null;
        } else {
            try {
                return mapper.readValue(json, clazz);
            } catch (IOException var3) {
                return null;
            }
        }
    }

    public static <T> T serializable(String json, TypeReference<T> reference) {
        if (StringUtils.isEmpty(json)) {
            return null;
        } else {
            try {
                return mapper.readValue(json, reference);
            } catch (IOException var3) {
                return null;
            }
        }
    }

    public static String deserializer(Object json) {
        if (json == null) {
            return null;
        } else {
            try {
                return mapper.writeValueAsString(json);
            } catch (JsonProcessingException var2) {
                return null;
            }
        }
    }

    static {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
}