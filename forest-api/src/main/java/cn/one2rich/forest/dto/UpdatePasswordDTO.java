package cn.one2rich.forest.dto;

import lombok.Data;

/** @author ronger */
@Data
public class UpdatePasswordDTO {

  private Integer idUser;

  private String password;
}
