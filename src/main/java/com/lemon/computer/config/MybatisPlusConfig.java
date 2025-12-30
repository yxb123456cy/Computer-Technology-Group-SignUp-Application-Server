package com.lemon.computer.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.*;
import com.lemon.computer.common.constants.GlobalSystemConstants;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@MapperScan(GlobalSystemConstants.MAPPER_SCAN_PATH)
@Configuration
public class MybatisPlusConfig {

    /**
     * 配置MybatisPLus插件;
     * @return MybatisPlusInterceptor
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        DataChangeRecorderInnerInterceptor dataChangeRecorderInnerInterceptor = new DataChangeRecorderInnerInterceptor();
        // 配置安全阈值，限制批量更新或删除的记录数不超过 10000 条
        dataChangeRecorderInnerInterceptor.setBatchUpdateLimit(GlobalSystemConstants.BATCH_UPDATE_LIMIT).openBatchUpdateLimitation();
        //添加数据变动记录插件;
        interceptor.addInnerInterceptor(dataChangeRecorderInnerInterceptor);
        // 添加非法SQL拦截器
        interceptor.addInnerInterceptor(new IllegalSQLInnerInterceptor());
        //防全表更新与删除插件
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        // 配置乐观锁插件;
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL)); // 如果配置多个插件, 切记分页最后添加
        // 如果有多数据源可以不配具体类型, 否则都建议配上具体的 DbType
        return interceptor;
    }
}
