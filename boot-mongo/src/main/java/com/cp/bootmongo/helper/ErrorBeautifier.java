package com.cp.bootmongo.helper;

import com.cp.bootmongo.exception.DocumentException;
import com.cp.bootmongo.validator.DocumentError;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@CommonsLog
public class ErrorBeautifier implements Beautifier {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public String beautify(Object obj) {
        try {
            List<DocumentError> documentErrors = (List<DocumentError>) obj;
            String message = objectMapper.writeValueAsString(documentErrors);
            return message;
        } catch (Exception ex) {
            String id = UUID.randomUUID().toString();
            log.error("Error beautifying  error messages " + id, ex);
            throw new DocumentException("Error beautifying  error messages " + id);
        }

    }
}
