package cn.one2rich.forest.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.one2rich.forest.entity.CurrencyRule;
import cn.one2rich.forest.mapper.CurrencyRuleMapper;
import cn.one2rich.forest.service.CurrencyRuleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/** @author ronger */
@Service
public class CurrencyRuleServiceImpl extends ServiceImpl<CurrencyRuleMapper, CurrencyRule>
    implements CurrencyRuleService {

  @Resource private CurrencyRuleMapper currencyRuleMapper;
}
