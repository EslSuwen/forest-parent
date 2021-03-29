package cn.one2rich.forest.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/** @author ronger */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("forest_topic")
public class Topic {
  /** 主键 */
  @TableId(value = "id", type = IdType.AUTO)
  private Integer idTopic;
  /** 父ip */
  private Integer pid;
  /** 是否为子结点 */
  private Boolean isLeaf;
  /** 专题标题 */
  private String topicTitle;
  /** 专题路径 */
  private String topicUri;
  /** 专题描述 */
  private String topicDescription;
  /** 专题类型 */
  private String topicType;
  /** 专题序号;10 */
  private Integer topicSort;
  /** 专题图片路径 */
  private String topicIconPath;
  /** 0：作为导航1：不作为导航;0 */
  private String topicNva;
  /** 专题下标签总数;0 */
  private Integer topicTagCount;
  /** 0：正常1：禁用;0 */
  private String topicStatus;
  /** 创建时间 */
  private Date createdTime;
  /** 更新时间 */
  private Date updatedTime;
  /** 专题描述 Html */
  private String topicDescriptionHtml;
  /** 子结点 */
  @TableField(exist = false)
  private List<Topic> children;
}
