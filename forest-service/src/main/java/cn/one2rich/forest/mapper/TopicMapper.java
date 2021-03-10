package cn.one2rich.forest.mapper;

import cn.one2rich.forest.dto.admin.TagDTO;
import cn.one2rich.forest.dto.admin.TopicDTO;
import cn.one2rich.forest.entity.Tag;
import cn.one2rich.forest.entity.Topic;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/** @author ronger */
public interface TopicMapper extends BaseMapper<Topic> {
  /**
   * 获取导航主题
   *
   * @return
   */
  List<Topic> selectTopicNav();

  /**
   * @param topicUri
   * @return
   */
  TopicDTO selectTopicByTopicUri(@Param("topicUri") String topicUri);

  /**
   *
   * @param page
   * @param idTopic
   * @return
   */
  IPage<TagDTO> selectTopicTag(Page<?> page, @Param("idTopic") Integer idTopic);

  /**
   * 更新
   *
   * @param idTopic
   * @param topicTitle
   * @param topicUri
   * @param topicIconPath
   * @param topicNva
   * @param topicStatus
   * @param topicSort
   * @param topicDescription
   * @param topicDescriptionHtml
   * @return
   */
  Integer update(
          @Param("idTopic") Integer idTopic,
          @Param("topicTitle") String topicTitle,
          @Param("topicUri") String topicUri,
          @Param("topicIconPath") String topicIconPath,
          @Param("topicNva") String topicNva,
          @Param("topicStatus") String topicStatus,
          @Param("topicSort") Integer topicSort,
          @Param("topicDescription") String topicDescription,
          @Param("topicDescriptionHtml") String topicDescriptionHtml);

  /**
   * @param idTopic
   * @param tagTitle
   * @return
   */
  IPage<Tag> selectUnbindTagsById(
          Page<?> page, @Param("idTopic") Integer idTopic, @Param("tagTitle") String tagTitle);

  Integer insertTopicTag(@Param("idTopic") Integer idTopic, @Param("idTag") Integer idTag);

  /**
   * @param idTopic
   * @param idTag
   * @return
   */
  Integer deleteTopicTag(@Param("idTopic") Integer idTopic, @Param("idTag") Integer idTag);
}
