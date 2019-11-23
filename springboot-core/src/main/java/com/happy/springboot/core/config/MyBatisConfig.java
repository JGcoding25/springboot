package com.happy.springboot.core.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * mybatis配置
 *
 * @author Happy
 * @date 2019/11/20
 */
@Configuration
@EnableTransactionManagement
@MapperScan({"com.happy.springboot.core.dao"})
public class MyBatisConfig {
}
