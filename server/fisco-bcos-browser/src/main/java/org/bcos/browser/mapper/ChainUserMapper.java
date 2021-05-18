package org.bcos.browser.mapper;

import org.bcos.browser.entity.dto.ChainUser;
import org.bcos.browser.entity.dto.UserQueryParam;
import org.bcos.browser.entity.rsp.RsqChainUser;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * user data interface.
 */
@Repository
public interface ChainUserMapper {
    void addChainUser(ChainUser chainUser);

    Integer queryChainUserCount(UserQueryParam userQueryParam);

    List<RsqChainUser> queryChainUserList(UserQueryParam userQueryParam);
}
