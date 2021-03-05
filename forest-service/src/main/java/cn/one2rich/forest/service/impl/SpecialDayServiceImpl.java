package cn.one2rich.forest.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.one2rich.forest.entity.SpecialDay;
import cn.one2rich.forest.mapper.SpecialDayMapper;
import cn.one2rich.forest.service.SpecialDayService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/** @author ronger */
@Service
public class SpecialDayServiceImpl extends ServiceImpl<SpecialDayMapper, SpecialDay>
    implements SpecialDayService {

  @Resource private SpecialDayMapper specialDayMapper;
}
