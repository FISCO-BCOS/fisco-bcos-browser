package org.bcos.browser.base;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.bcos.browser.base.exception.BaseException;
import org.bcos.browser.entity.base.BaseResponse;
import org.bcos.browser.util.JsonTools;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

/**
 * BaseController.
 * 
 */
@Slf4j
public abstract class BaseController {
    /**
     * checkParamResult.
     * 
     * @param bindingResult checkResult
     */
    protected BaseResponse checkParamResult(BindingResult bindingResult) throws BaseException {
        if (!bindingResult.hasErrors()) {
            return null;
        }

        String errorMsg = getParamValidFaildMessage(bindingResult);
        if (StringUtils.isBlank(errorMsg)) {
            log.warn("OnWarning:param exception. errorMsg is empty");
            throw new BaseException(ConstantCode.PARAM_VAILD_FAIL);
        }

        RetCode retCode = null;
        try {
            retCode = JsonTools.stringToObj(errorMsg, RetCode.class);
        } catch (Exception ex) {
            log.warn("OnWarning:retCodeJson convert error:" + ex.getMessage());
            throw new BaseException(ConstantCode.PARAM_VAILD_FAIL);
        }

        throw new BaseException(retCode);
    }

    private String getParamValidFaildMessage(BindingResult bindingResult) {
        List<ObjectError> errorList = bindingResult.getAllErrors();
        if (errorList == null) {
            log.warn("onWarning:errorList is empty!");
            return null;
        }

        ObjectError objectError = errorList.get(0);
        if (objectError == null) {
            log.warn("onWarning:objectError is empty!");
            return null;
        }
        return objectError.getDefaultMessage();
    }
}
