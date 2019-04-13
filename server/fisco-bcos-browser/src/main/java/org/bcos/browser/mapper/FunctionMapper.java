package org.bcos.browser.mapper;

import org.apache.ibatis.annotations.Param;
import org.bcos.browser.entity.dto.Function;
import org.springframework.stereotype.Repository;

@Repository
public interface FunctionMapper {

    void add(Function function);

    Function getFunctionById(@Param(value = "methodId") String methodId);

}
