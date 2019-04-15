package org.bcos.browser.entity.req;

import java.util.List;
import org.bcos.browser.entity.dto.Function;
import lombok.Data;

@Data
public class ReqFunction {
    private List<Function> data;
}
