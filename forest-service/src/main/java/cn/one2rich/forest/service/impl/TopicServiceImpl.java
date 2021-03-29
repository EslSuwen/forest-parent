package cn.one2rich.forest.service.impl;

import cn.one2rich.forest.dto.admin.TopicDTO;
import cn.one2rich.forest.dto.admin.TopicTagDTO;
import cn.one2rich.forest.dto.result.Result;
import cn.one2rich.forest.entity.Tag;
import cn.one2rich.forest.entity.Topic;
import cn.one2rich.forest.mapper.TopicMapper;
import cn.one2rich.forest.service.TopicService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/** @author ronger */
@Service
public class TopicServiceImpl extends ServiceImpl<TopicMapper, Topic> implements TopicService {

  @Override
  public List<Topic> findTopicNav() {
    LambdaQueryWrapper<Topic> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(Topic::getPid, 0).or().isNull(Topic::getPid).orderByDesc(Topic::getTopicSort);
    // 得到一级节点资源列表
    List<Topic> topicNavList = list(wrapper);
    if (!topicNavList.isEmpty()) {
      topicNavList.forEach(this::findAllChild);
    }
    return topicNavList;
  }

  public void findAllChild(Topic topic) {
    LambdaQueryWrapper<Topic> wrapper =
        new LambdaQueryWrapper<Topic>()
            .eq(Topic::getPid, topic.getIdTopic())
            .orderByDesc(Topic::getTopicSort);
    List<Topic> childList = list(wrapper);
    topic.setChildren(childList);
    if (!childList.isEmpty()) {
      childList.forEach(this::findAllChild);
    }
  }

  @Override
  public Topic findTopicByTopicUri(String topicUri) {
    return getOne(new LambdaQueryWrapper<Topic>().eq(Topic::getTopicUri, topicUri));
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public Result<?> saveTopic(Topic topic) {
    if (topic.getIdTopic() == null) {
      if (StringUtils.isBlank(topic.getTopicTitle())) {
        return Result.error("标签名不能为空!");
      } else {
        if (!list(new LambdaQueryWrapper<Topic>().eq(Topic::getTopicTitle, topic.getTopicTitle()))
            .isEmpty()) {
          return Result.error("专题 '" + topic.getTopicTitle() + "' 已存在!");
        }
      }
      Topic newTopic = new Topic();
      newTopic.setTopicTitle(topic.getTopicTitle());
      newTopic.setTopicUri(topic.getTopicUri());
      newTopic.setTopicIconPath(topic.getTopicIconPath());
      newTopic.setTopicNva(topic.getTopicNva());
      newTopic.setTopicStatus(topic.getTopicStatus());
      newTopic.setTopicSort(topic.getTopicSort());
      newTopic.setTopicDescription(topic.getTopicDescription());
      newTopic.setTopicDescriptionHtml(topic.getTopicDescriptionHtml());
      newTopic.setCreatedTime(new Date());
      newTopic.setUpdatedTime(topic.getCreatedTime());
      save(newTopic);
    } else {
      topic.setCreatedTime(new Date());
      baseMapper.update(
          topic.getIdTopic(),
          topic.getTopicTitle(),
          topic.getTopicUri(),
          topic.getTopicIconPath(),
          topic.getTopicNva(),
          topic.getTopicStatus(),
          topic.getTopicSort(),
          topic.getTopicDescription(),
          topic.getTopicDescriptionHtml());
    }
    return Result.OK(topic);
  }

  @Override
  public IPage<Tag> findUnbindTagsById(Page<?> page, Integer idTopic, String tagTitle) {
    if (StringUtils.isBlank(tagTitle)) {
      tagTitle = "";
    }
    return baseMapper.selectUnbindTagsById(page, idTopic, tagTitle);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public Result<?> bindTopicTag(TopicTagDTO topicTag) {
    Integer result = baseMapper.insertTopicTag(topicTag.getIdTopic(), topicTag.getIdTag());
    return result != 0 ? Result.OK(topicTag) : Result.error("操作失败!");
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public Result<?> unbindTopicTag(TopicTagDTO topicTag) {
    Integer result = baseMapper.deleteTopicTag(topicTag.getIdTopic(), topicTag.getIdTag());
    return result != 0 ? Result.OK(topicTag) : Result.error("操作失败!");
  }

  @Override
  public Result<?> findTagsByTopicUri(Page<?> page, String topicUri) {
    TopicDTO topic = baseMapper.selectTopicByTopicUri(topicUri);
    if (topic == null) {
      return Result.error("专题不存在！");
    }
    return Result.OK(baseMapper.selectTopicTag(page, topic.getIdTopic()));
  }
}
