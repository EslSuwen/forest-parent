package cn.one2rich.forest.dto;

import lombok.Data;

/** @author ronger */
@Data
public class TokenUser {

  private Integer idUser;

  private String account;

  private String nickname;

  private String token;

  private String avatarType;

  private String avatarUrl;

  private Integer weights;
}
