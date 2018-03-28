package cn.webank.bcos.browser.base;


import cn.webank.bcos.browser.entity.base.BaseRspEntity;
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
 * Controller base class
 */
public abstract class BaseController {
    private static Logger LOGGER =  LoggerFactory.getLogger(BaseController.class);

    /**
     * Whether there is a parameter check does not pass
     */
    protected void checkParamResult(BindingResult bindingResult){
        if(!bindingResult.hasErrors()){
            return;
        }

        //Get parameter validation error message
        String errorMsg = getParamValidFaildMessage(bindingResult);
        if(StringUtils.isBlank(errorMsg)){
            LOGGER.error("errorMsg is empty");
            throw new BcosBrowserException(ConstantCode.GET_PARAM_VAILD_RETCODE_FAIL);
        }

        RetCode retCode = null;
        try {
            //Turn into a JSON object
            JSONObject jsonObject = JSON.parseObject(errorMsg);
            retCode = JSONObject.toJavaObject(jsonObject, RetCode.class);
        }catch (Exception ex){
            LOGGER.error("retCodeJson convert error:"+ex.getMessage());
            throw new BcosBrowserException(ConstantCode.GET_PARAM_VAILD_RETCODE_FAIL);
        }

        //Throws an exception wrapped by RetCode
        throw new BcosBrowserException(retCode);
    }

    /**
     * Get parameter validation error message
     */
    private String getParamValidFaildMessage(BindingResult bindingResult){
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

        //Return error message
        return objectError.getDefaultMessage();
    }


    /**
     * Capture processing exception：BcosBrowserException
     */
    @ResponseBody
    @ExceptionHandler(value = BcosBrowserException.class)
  //  @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected BaseRspEntity BcosBrowserExceptionHandler(BcosBrowserException exc){
        //The default system exception
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
     * Capture processing exception：RuntimeException
     */
    @ResponseBody
    @ExceptionHandler(value = RuntimeException.class)
   // @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected BaseRspEntity RuntimeExceptionHandler(RuntimeException exc){
        LOGGER.error("exception",exc);
        //The default system exception
        RetCode retCode =ConstantCode.SYSTEM_EXCEPTION;

        BaseRspEntity bre = new BaseRspEntity(retCode);
        LOGGER.error("response:{}", JSON.toJSONString(bre));
        return bre;
    }
}
