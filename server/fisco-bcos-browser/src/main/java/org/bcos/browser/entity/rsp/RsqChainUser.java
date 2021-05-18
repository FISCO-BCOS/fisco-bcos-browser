package org.bcos.browser.entity.rsp;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RsqChainUser{
    private Integer Id;
    private Integer groupId;
    private String address;
    private String transHash;
    private LocalDateTime createTime;
    private LocalDateTime modifyTime;
    private int userId;
    private String userName;
    private String description;
}
