package cn.one2rich.forest.mapper;

import cn.one2rich.forest.entity.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission> {

  List<Permission> selectMenuByIdRole(@Param("role") Integer role);
}
