package cn.one2rich.forest.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.one2rich.forest.dto.result.Result;
import cn.one2rich.forest.entity.Role;
import cn.one2rich.forest.entity.User;
import cn.one2rich.forest.mapper.RoleMapper;
import cn.one2rich.forest.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author CodeGenerator
 * @date 2018/05/29
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
  @Resource private RoleMapper roleMapper;

  @Override
  public List<Role> selectRoleByUser(User sysUser) {
    return roleMapper.selectRoleByIdUser(sysUser.getIdUser());
  }

  @Override
  public List<Role> findByIdUser(Integer idUser) {
    return roleMapper.selectRoleByIdUser(idUser);
  }

  @Override
  @Transactional
  public Result<?> updateStatus(Integer idRole, String status) {
    Integer result = roleMapper.updateStatus(idRole, status);
    return result != 0 ? Result.OK() : Result.error("更新失败!");
  }

  @Override
  public Role saveRole(Role role) {
    if (role.getIdRole() == null) {
      role.setCreatedTime(new Date());
      role.setUpdatedTime(role.getCreatedTime());
      save(role);
    } else {
      role.setCreatedTime(new Date());
      roleMapper.update(role.getIdRole(), role.getName(), role.getInputCode(), role.getWeights());
    }
    return role;
  }
}
