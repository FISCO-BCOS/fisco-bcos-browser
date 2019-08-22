package org.bcos.browser.entity.req;

import org.bcos.browser.base.ConstantCode;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 登录入参实体类
 */
public class ReqLoginEntity{
    @NotEmpty(message = ConstantCode.LOGIN_FAIL_USERNAME_EMPTY)
    private String userName;
    @NotEmpty(message = ConstantCode.LOGIN_FAIL_PASSWORD_EMPTY)
    private String password;
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"userName\":\"")
                .append(userName).append('\"');
        sb.append(",\"password\":\"")
                .append(password).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
