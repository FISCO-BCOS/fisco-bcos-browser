package org.bcos.browser.base.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.bcos.browser.base.ConstantCode;
import org.bcos.browser.base.RetCode;
import org.bcos.browser.entity.base.BaseResponse;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Log4j2
public class ExceptionsHandler {
    @Autowired
    ObjectMapper mapper;

    /**
     * myExceptionHandler.
     * 
     * @param exception e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = BaseException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public BaseResponse myExceptionHandler(BaseException exception) throws Exception {
        log.warn("catch business exception", exception);
        RetCode retCode = Optional.ofNullable(exception).map(BaseException::getRetCode)
                .orElse(ConstantCode.SYSTEM_ERROR);

        BaseResponse rep = new BaseResponse(retCode);
        log.warn("business exception return:{}", mapper.writeValueAsString(rep));
        return rep;
    }

    /**
     * parameter exception:TypeMismatchException
     */
    @ResponseBody
    @ExceptionHandler(value = TypeMismatchException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public BaseResponse typeMismatchExceptionHandler(TypeMismatchException ex) {
        log.warn("catch typeMismatchException", ex);
        RetCode retCode = new RetCode(ConstantCode.PARAM_VAILD_FAIL.getCode(), ex.getMessage());
        BaseResponse bre = new BaseResponse(retCode);
        return bre;
    }

    /**
     * parameter exception:HttpMessageNotReadableException
     */
    @ResponseBody
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public BaseResponse httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException ex) {
        log.warn("catch HttpMessageNotReadableException", ex);
        RetCode retCode = new RetCode(ConstantCode.PARAM_VAILD_FAIL.getCode(), ex.getMessage());
        BaseResponse bre = new BaseResponse(retCode);
        return bre;
    }

    /**
     * catch：RuntimeException.
     */
    @ResponseBody
    @ExceptionHandler(value = RuntimeException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponse exceptionHandler(RuntimeException exc) {
        log.warn("catch RuntimeException", exc);
        BaseResponse bre = new BaseResponse(ConstantCode.SYSTEM_ERROR);
        return bre;
    }

    /**
     * exceptionHandler.
     * 
     * @param exc e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponse exceptionHandler(Exception exc) {
        log.warn("catch  exception", exc);
        RetCode retCode = ConstantCode.SYSTEM_ERROR;
        BaseResponse rep = new BaseResponse(retCode);
        return rep;
    }
}
