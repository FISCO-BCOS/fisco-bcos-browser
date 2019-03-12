package org.bcos.browser.entity.dto;

import javax.validation.constraints.NotNull;
import lombok.Data;
import org.bcos.browser.base.ConstantCode;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class Group {
    @NotNull(message = ConstantCode.GROUP_ID_IS_EMPTY)
    private int groupId;
    @NotBlank(message = ConstantCode.GROUP_NAME_IS_EMPTY)
    private String groupName;
    private String groupDesc;
}
