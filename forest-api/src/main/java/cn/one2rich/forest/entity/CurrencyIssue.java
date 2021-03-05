package cn.one2rich.forest.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 货币发行记录
 *
 * @author ronger
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("forest_currency_issue")
public class CurrencyIssue {
  /** 主键 */
  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;
  /** 发行数额 */
  private BigDecimal issueValue;
  /** 发行人 */
  private Integer createdBy;
  /** 发行时间 */
  private Date createdTime;
}
