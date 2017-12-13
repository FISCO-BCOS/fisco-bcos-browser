package org.bcos.browser.base;


import org.bcos.browser.entity.base.BaseRspEntity;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
 * 控制器的基类
 */
public abstract class BaseController {
    private static Logger LOGGER =  LoggerFactory.getLogger(BaseController.class);

    /**
     * 是否有参数校验不通过
     */
    protected void checkParamResult(BindingResult bindingResult){
        //没有错误信息
        if(!bindingResult.hasErrors()){
            return;
        }

        //获取参数校验的错误信息
        String errorMsg = getParamValidFaildMessage(bindingResult);
        if(StringUtils.isBlank(errorMsg)){
            LOGGER.error("errorMsg is empty");
            throw new FiscoBcosBrowserException(ConstantCode.GET_PARAM_VAILD_RETCODE_FAIL);
        }

        RetCode retCode = null;
        try {
            //转成JSON对象
            JSONObject jsonObject = JSON.parseObject(errorMsg);
            //转成ObjectError java对象
            retCode = JSONObject.toJavaObject(jsonObject, RetCode.class);
        }catch (Exception ex){
            LOGGER.error("retCodeJson convert error:"+ex.getMessage());
            throw new FiscoBcosBrowserException(ConstantCode.GET_PARAM_VAILD_RETCODE_FAIL);
        }

        //抛出一个由RetCode封装的异常
        throw new FiscoBcosBrowserException(retCode);
    }

    /**
     * 获取参数校验的错误信息
     */
    private String getParamValidFaildMessage(BindingResult bindingResult){
        //获取所有的错误信息
        List<ObjectError> errorList =  bindingResult.getAllErrors();
        LOGGER.info("errorList:{}",JSON.toJSONString(errorList));
        if(errorList==null){
            LOGGER.warn("errorList is empty!");
            return null;
        }

        ObjectError objectError = errorList.get(0);
        if(objectError==null){
            LOGGER.warn("objectError is empty!");
            return null;
        }

        //返回错误信息
        return objectError.getDefaultMessage();
    }


    /**
     * 捕获处理异常：FiscoBcosBrowserException
     */
    @ResponseBody
    @ExceptionHandler(value = FiscoBcosBrowserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected BaseRspEntity FiscoBcosBrowserExceptionHandler(FiscoBcosBrowserException exc){
        //默认系统异常
        RetCode retCode =ConstantCode.SYSTEM_EXCEPTION;

        LOGGER.error("exception",exc);
        if(exc!=null&&exc.getRsc()!=null){
            retCode = exc.getRsc();
        }

        BaseRspEntity bre = new BaseRspEntity(retCode);
        LOGGER.error("response:{}", JSON.toJSONString(bre));
        return bre;
    }

    /**
     * 捕获处理异常：RuntimeException
     */
    @ResponseBody
    @ExceptionHandler(value = RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected BaseRspEntity RuntimeExceptionHandler(RuntimeException exc){
        LOGGER.error("exception",exc);
        //默认系统异常
        RetCode retCode =ConstantCode.SYSTEM_EXCEPTION;

        BaseRspEntity bre = new BaseRspEntity(retCode);
        LOGGER.error("response:{}", JSON.toJSONString(bre));
        return bre;
    }
}
