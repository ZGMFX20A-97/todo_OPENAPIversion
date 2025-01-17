package com.example.todoApi.advice;

import com.example.todoapi.model.BadRequestError;
import com.example.todoapi.model.InvalidParam;
import lombok.NonNull;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ElementKind;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class BadRequestErrorCreator {

    public static BadRequestError from(@NonNull MethodArgumentNotValidException ex) {
        var invalidParamsList= createInvalidParamList(ex);
        var error = new BadRequestError();;
        error.setInvalidParams(invalidParamsList);
        return error;
    }

    private static List<InvalidParam> createInvalidParamList(MethodArgumentNotValidException ex) {
        return ex.getFieldErrors()
                .stream()
                .map(BadRequestErrorCreator::createInvalidParam
                )
                .toList();
    }

    private static InvalidParam createInvalidParam(FieldError fieldError) {
        var invalidParam=new InvalidParam();
        invalidParam.setName(fieldError.getField());
        invalidParam.setReason(fieldError.getDefaultMessage());
        return invalidParam;
    }

    public static BadRequestError from(ConstraintViolationException e) {
        var invalidParamList= createInvalidParamList(e);
        var error=new BadRequestError();
        error.setInvalidParams(invalidParamList);
        return error;
    }

    private static List<InvalidParam> createInvalidParamList(ConstraintViolationException e) {
        return e.getConstraintViolations()
                .stream()
                .map(BadRequestErrorCreator::createInvalidParam).toList();
    }

    private static InvalidParam createInvalidParam(ConstraintViolation<?> violation) {
        var parameterOpt=StreamSupport.stream(violation.getPropertyPath().spliterator(),false)
                .filter(node->node.getKind().equals(ElementKind.PARAMETER)).findFirst();
        var invalidParam=new InvalidParam();
        parameterOpt.ifPresent(p->invalidParam.setName(p.getName()));
        invalidParam.setReason(violation.getMessage());
        return invalidParam;
    }
}
