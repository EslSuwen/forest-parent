package cn.one2rich.forest.service.impl;

import cn.one2rich.forest.entity.UserDic;
import cn.one2rich.forest.lucene.dic.Dictionary;

import cn.one2rich.forest.mapper.UserDicMapper;
import cn.one2rich.forest.service.UserDicService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * UserDicServiceImpl
 *
 * @author suwen
 * @date 2021/2/4 09:26
 */
@Service
public class UserDicServiceImpl extends ServiceImpl<UserDicMapper, UserDic>
    implements UserDicService {

  @Resource private UserDicMapper userDicMapper;

  @Override
  public List<String> getAllDic() {

    return userDicMapper.getAllDic();
  }

  @Override
  public List<UserDic> getAll() {
    return userDicMapper.getAll();
  }

  @Override
  public void addDic(String dic) {
    userDicMapper.addDic(dic);
    writeUserDic();
  }

  @Override
  public void deleteDic(String id) {
    userDicMapper.deleteDic(id);
    writeUserDic();
  }

  @Override
  public void updateDic(UserDic userDic) {
    userDicMapper.updateDic(userDic.getId(), userDic.getDic());
    writeUserDic();
  }

  @Override
  public void writeUserDic() {
    try {
      String filePath = "cn/one2rich/forest/lucene/userDic/";
      File file = new File(filePath);
      if (!file.exists()) {
        file.mkdirs();
      }
      FileOutputStream stream = new FileOutputStream(file + "/userDic.dic", false);
      OutputStreamWriter outfw = new OutputStreamWriter(stream, StandardCharsets.UTF_8);
      PrintWriter fw = new PrintWriter(new BufferedWriter(outfw));
      userDicMapper
          .getAllDic()
          .forEach(
              each -> {
                fw.write(each);
                fw.write("\r\n");
              });
      fw.flush();
      fw.close();
      Dictionary.getSingleton().updateUserDict();
    } catch (IOException | IllegalStateException e) {
      e.printStackTrace();
    }
  }
}
