package cn.one2rich.forest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.one2rich.forest.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {

  List<Role> selectRoleByIdUser(@Param("id") Integer id);

  Role selectRoleByInputCode(@Param("inputCode") String inputCode);

  Integer updateStatus(@Param("idRole") Integer idRole, @Param("status") String status);

  Integer update(
          @Param("idRole") Integer idRole,
          @Param("name") String name,
          @Param("inputCode") String inputCode,
          @Param("weights") Integer weights);
}
