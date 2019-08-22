package org.bcos.browser.entity.req;

import java.util.List;

import org.bcos.browser.entity.dto.Contract;

import lombok.Data;

@Data
public class ReqContracts {
    private List<Contract> data;
}
