package cn.one2rich.forest.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 浏览表
 *
 * @author ronger
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("forest_visit")
public class Visit implements Serializable, Cloneable {
  /** 主键 */
  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;
  /** 浏览链接 */
  private String visitUrl;
  /** IP */
  private String visitIp;
  /** User-Agent */
  private String visitUa;
  /** 城市 */
  private String visitCity;
  /** 设备唯一标识 */
  private String visitDeviceId;
  /** 浏览者 id */
  private Integer visitUserId;
  /** 上游链接 */
  private String visitRefererUrl;
  /** 创建时间 */
  @JSONField(format = "yyyy-MM-dd HH:mm:ss")
  private Date createdTime;
  /** 过期时间 */
  @JSONField(format = "yyyy-MM-dd HH:mm:ss")
  private Date expiredTime;
}
