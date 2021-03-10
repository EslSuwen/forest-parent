package cn.one2rich.forest.service;

import cn.one2rich.forest.dto.NotificationDTO;
import cn.one2rich.forest.entity.Notification;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 消息通知接口类
 *
 * @author ronger
 */
public interface NotificationService extends IService<Notification> {
  /**
   * 获取未读消息数据
   *
   *
   * @param page
   * @param idUser
   * @return
   */
  IPage<Notification> findUnreadNotifications(Page<?> page, Integer idUser);

  /**
   * 获取消息数据
   *
   *
   * @param page
   * @param idUser
   * @return
   */
  IPage<NotificationDTO> findNotifications(Page<?> page, Integer idUser);

  /**
   * 获取消息数据
   *
   * @param idUser
   * @param dataId
   * @param dataType
   * @return
   */
  Notification findNotification(Integer idUser, Integer dataId, String dataType);

  /**
   * 创建系统通知
   *
   * @param idUser
   * @param dataId
   * @param dataType
   * @param dataSummary
   * @return
   */
  Integer save(Integer idUser, Integer dataId, String dataType, String dataSummary);

  /**
   * 标记消息已读
   *
   * @param id
   */
  Integer readNotification(Integer id);
}
