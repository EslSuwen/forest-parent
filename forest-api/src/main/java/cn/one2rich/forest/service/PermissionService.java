package cn.one2rich.forest.service;

import cn.one2rich.forest.entity.Permission;
import cn.one2rich.forest.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author CodeGenerator
 * @date 2018/05/29
 */
public interface PermissionService extends IService<Permission> {

  /**
   * 获取用户权限
   *
   * @param sysUser
   * @return
   */
  List<Permission> selectPermissionByUser(User sysUser);
}
