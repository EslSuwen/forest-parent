package cn.one2rich.forest.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/** @author ronger */
@Data
public class UserInfoDTO implements Serializable {

  private Integer idUser;

  private String account;

  private String avatarType;

  private String avatarUrl;

  private String nickname;

  private String email;

  private String phone;

  private String status;

  private String roleIds;

  private String sex;

  private String signature;

  @JSONField(format = "yyyy-MM-dd HH:mm")
  private Date lastLoginTime;
}
