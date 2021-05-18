package org.bcos.browser.entity.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * save chainUser's address, groupId etc.
 */
@Data
@NoArgsConstructor
public class ChainUser {
    private Integer Id;
    private Integer groupId;
    private String address;
    private String transHash;
    private LocalDateTime createTime;
    private LocalDateTime modifyTime;
}
