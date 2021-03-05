package cn.one2rich.forest.dto;

import lombok.Data;

/** @author ronger */
@Data
public class UserDTO {

  private Integer idUser;

  private String account;

  private String avatarType;

  private String avatarUrl;

  private String nickname;

  private String signature;
}
