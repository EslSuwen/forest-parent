package cn.one2rich.forest.mapper;

import cn.one2rich.forest.entity.UserDic;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * UserDicMapper
 *
 * @author suwen
 * @date 2021/2/4 09:11
 */
public interface UserDicMapper extends BaseMapper<UserDic> {

  /**
   * 加载所有字典
   *
   * @return
   */
  List<String> getAllDic();

  /**
   * 加载所有字典信息
   *
   * @return
   */
  List<UserDic> getAll();

  /**
   * 增加字典
   *
   * @return
   */
  void addDic(@Param("dic") String userDic);

  /**
   * 删除字典
   *
   * @param id
   */
  void deleteDic(@Param("id") String id);

  /**
   * 更新字典
   *
   * @param id
   * @param userDic
   */
  void updateDic(@Param("id") Integer id, @Param("dic") String userDic);
}
