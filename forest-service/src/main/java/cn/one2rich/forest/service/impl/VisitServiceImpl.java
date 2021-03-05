package cn.one2rich.forest.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.one2rich.forest.entity.Visit;
import cn.one2rich.forest.mapper.VisitMapper;
import cn.one2rich.forest.service.VisitService;
import org.springframework.stereotype.Service;

/** @author ronger */
@Service
public class VisitServiceImpl extends ServiceImpl<VisitMapper, Visit> implements VisitService {}
