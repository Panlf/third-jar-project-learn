package com.plf.learn.screw;

import java.util.ArrayList;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import cn.smallbun.screw.core.Configuration;
import cn.smallbun.screw.core.engine.EngineConfig;
import cn.smallbun.screw.core.engine.EngineFileType;
import cn.smallbun.screw.core.engine.EngineTemplateType;
import cn.smallbun.screw.core.execute.DocumentationExecute;
import cn.smallbun.screw.core.process.ProcessConfig;
import org.junit.jupiter.api.Test;

public class ScrewDocument {
	/**
	 * 文档生成
	 */
	@Test
	public void documentGeneration() {
	   String fileOutputDir = "D:\\TempData\\Screw";
		
		//数据源
	   HikariConfig hikariConfig = new HikariConfig();
	   hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
	   hikariConfig.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/rbac?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2b8");
	   hikariConfig.setUsername("root");
	   hikariConfig.setPassword("root");
	   //设置可以获取tables remarks信息
	   hikariConfig.addDataSourceProperty("useInformationSchema", "true");
	   hikariConfig.setMinimumIdle(2);
	   hikariConfig.setMaximumPoolSize(5);
	   DataSource dataSource = new HikariDataSource(hikariConfig);
	   //生成配置
	   EngineConfig engineConfig = EngineConfig.builder()
	         //生成文件路径
	         .fileOutputDir(fileOutputDir)
	         //打开目录
	         .openOutputDir(true)
	         //文件类型
	         .fileType(EngineFileType.HTML)
	         //生成模板实现
	         .produceType(EngineTemplateType.freemarker)
	         //自定义文件名称
	         .fileName("RBAC数据文档").build();

	   //忽略表
	   ArrayList<String> ignoreTableName = new ArrayList<>();
	   ignoreTableName.add("test");
	   //ignoreTableName.add("test_group");
	   //忽略表前缀
	   ArrayList<String> ignorePrefix = new ArrayList<>();
	   //ignorePrefix.add("sys_");
	   //忽略表后缀    
	   ArrayList<String> ignoreSuffix = new ArrayList<>();
	   //ignoreSuffix.add("_test");
	   ProcessConfig processConfig = ProcessConfig.builder()
	         //指定生成逻辑、当存在指定表、指定表前缀、指定表后缀时，将生成指定表，其余表不生成、并跳过忽略表配置	
			 //根据名称指定表生成
			 .designatedTableName(new ArrayList<>())
			 //根据表前缀生成
			 .designatedTablePrefix(new ArrayList<>())
			 //根据表后缀生成	
			 .designatedTableSuffix(new ArrayList<>())
	         //忽略表名
	         .ignoreTableName(ignoreTableName)
	         //忽略表前缀
	         .ignoreTablePrefix(ignorePrefix)
	         //忽略表后缀
	         .ignoreTableSuffix(ignoreSuffix).build();
	   //配置
	   Configuration config = Configuration.builder()
	         //版本
	         .version("1.0.0")
	         //描述
	         .description("数据库设计文档生成")
	         //数据源
	         .dataSource(dataSource)
	         //生成配置
	         .engineConfig(engineConfig)
	         //生成配置
	         .produceConfig(processConfig)
	         .build();
	   //执行生成
	   new DocumentationExecute(config).execute();
	}
}
