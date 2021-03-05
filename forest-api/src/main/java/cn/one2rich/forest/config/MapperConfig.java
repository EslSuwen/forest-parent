package cn.one2rich.forest.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/** @author suwen */
@Configuration
@MapperScan({"cn.one2rich.forest.mapper"})
public class MapperConfig {}
