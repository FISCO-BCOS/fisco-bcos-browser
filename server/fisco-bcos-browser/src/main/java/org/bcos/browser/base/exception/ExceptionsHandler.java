package org.bcos.browser.base.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.bcos.browser.base.ConstantCode;
import org.bcos.browser.base.RetCode;
import org.bcos.browser.entity.base.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public BaseResponse myExceptionHandler(BaseException exception) throws Exception {
        log.warn("catch business exception", exception);
        RetCode retCode = Optional.ofNullable(exception).map(BaseException::getRetCode)
                .orElse(ConstantCode.SYSTEM_ERROR);

        BaseResponse rep = new BaseResponse(retCode);
        log.warn("business exception return:{}", mapper.writeValueAsString(rep));
        return rep;
    }

    /**
     * exceptionHandler.
     * 
     * @param exc e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public BaseResponse exceptionHandler(Exception exc) {
        log.info("catch  exception", exc);
        RetCode retCode = ConstantCode.SYSTEM_ERROR;
        BaseResponse rep = new BaseResponse(retCode);
        try {
            log.warn("system exception return:{}", mapper.writeValueAsString(rep));
        } catch (JsonProcessingException ex) {
            log.warn("system exception", ex);
        }
        return rep;
    }
}
