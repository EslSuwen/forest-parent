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

/** @author ronger */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("forest_notification")
public class Notification implements Serializable, Cloneable {
  /** 主键 */
  @TableId(value = "id", type = IdType.AUTO)
  private Integer idNotification;
  /** 用户id */
  private Integer idUser;
  /** 数据类型 */
  private String dataType;
  /** 数据id */
  private Integer dataId;
  /** 数据摘要 */
  private String dataSummary;
  /** 是否已读 */
  private String hasRead;
  /** 是否已读 */
  @JSONField(format = "yyyy-MM-dd HH:mm:ss")
  private Date createdTime;
}
