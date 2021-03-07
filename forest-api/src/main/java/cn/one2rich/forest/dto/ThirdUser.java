package cn.one2rich.forest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 三方登录dto
 *
 * @author suwen
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThirdUser {

  private String uuid;

  private String username;

  private String nickname;

  private String email;

  private String avatar;
}
