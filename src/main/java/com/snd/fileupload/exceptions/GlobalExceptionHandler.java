package com.snd.fileupload.exceptions;

import com.snd.fileupload.dtos.DrugInfoDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<DrugInfoDto> handle(Exception e) {
        e.printStackTrace();
        DrugInfoDto infoDto = new DrugInfoDto();
        infoDto.setId(0);
        infoDto.setName("عطية خول");
        infoDto.setForm("عطية خول برضو");
        return new ResponseEntity<>(infoDto, HttpStatus.OK);
    }

}
