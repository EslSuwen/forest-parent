package cn.one2rich.forest.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.one2rich.forest.entity.Permission;
import cn.one2rich.forest.entity.Role;
import cn.one2rich.forest.entity.User;
import cn.one2rich.forest.mapper.PermissionMapper;
import cn.one2rich.forest.service.PermissionService;
import cn.one2rich.forest.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author CodeGenerator
 * @date 2018/05/29
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission>
    implements PermissionService {
  @Resource private PermissionMapper permissionMapper;

  @Resource private RoleService roleService;

  @Override
  public List<Permission> selectPermissionByUser(User sysUser) {
    List<Permission> list = new ArrayList<>();
    List<Role> roles = roleService.selectRoleByUser(sysUser);
    roles.forEach(role -> list.addAll(permissionMapper.selectMenuByIdRole(role.getIdRole())));
    HashSet hashSet = new HashSet(list);
    list.clear();
    list.addAll(hashSet);
    return list;
  }
}
