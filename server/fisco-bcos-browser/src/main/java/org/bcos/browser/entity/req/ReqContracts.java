package org.bcos.browser.entity.req;

import java.util.List;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.bcos.browser.base.ConstantCode;
import org.bcos.browser.entity.dto.Contract;

@Data
public class ReqContracts {
    private List<Contract> data;
}
