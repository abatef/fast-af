package com.snd.fileupload.exceptions;

import com.snd.fileupload.dtos.DrugInfoDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoResourceFound(NoResourceFoundException e) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Resource not found");
        errorResponse.put("message", e.getMessage());
        errorResponse.put("cause", (e.getCause() != null) ? e.getCause().toString() : "No cause available");

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<DrugInfoDto> handle(Exception e) {
        DrugInfoDto infoDto = new DrugInfoDto();
        infoDto.setId(0);
        infoDto.setName("عطية خول");
        infoDto.setForm("عطية خول برضو");
        return new ResponseEntity<>(infoDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
