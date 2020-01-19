package com.plf.learn.elasticjob;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;

public class JobMain {

	//zookeeper端口
	private static final int ZOOKEEPER_PORT = 2181;
	//zookeeper链接
	private static final String ZOOKEEPER_CONNECTION_STRING="localhost:"+ZOOKEEPER_PORT;
	//定时任务命名空间
	private static final String JOB_NAMESPACE="elastic-job-example-java";
	
	
	public static void main(String[] args) {
		//制造一些测试数据
		generateTestFiles();
		
		//配置注册中心
		CoordinatorRegistryCenter registryCenter = setUpRegitryCenter();
		
		//启动任务
		startJob(registryCenter);
		
	}
	
	
	//zk配置及创建注册中心
	private static CoordinatorRegistryCenter setUpRegitryCenter() {
		//zk配置
		ZookeeperConfiguration zookeeperConfiguration=new ZookeeperConfiguration(ZOOKEEPER_CONNECTION_STRING,JOB_NAMESPACE);
		//减少zk超时时间
		zookeeperConfiguration.setBaseSleepTimeMilliseconds(100);
		
		//创建注册中心
		CoordinatorRegistryCenter zookeeperRegistryCenter = new ZookeeperRegistryCenter(zookeeperConfiguration);
		
		zookeeperRegistryCenter.init();
		
		return zookeeperRegistryCenter;
	}
	
	
	private static void startJob(CoordinatorRegistryCenter registryCenter) {
		//jobName 任务名称, cron 调度表达式, shardingTotalCount 作业分页数量
		JobCoreConfiguration jobCoreConfiguration = JobCoreConfiguration.newBuilder("files-job", "0/3 * * * * ?", 1).build();
		//SimpleJobConfiguration
		SimpleJobConfiguration simpleJobConfiguration=new SimpleJobConfiguration(jobCoreConfiguration, FileBackupJob.class.getCanonicalName());
		
		//
		new JobScheduler(registryCenter,LiteJobConfiguration.newBuilder(simpleJobConfiguration).overwrite(true).build()).init();
	}
	
	
	//生成测试数据
	private static void generateTestFiles() {
		for(int i=1;i<11;i++) {
			FileBackupJob.files.add(new FileCustom(String.valueOf(i+10),"文件"+(i+10),"text","content:"+(i+10)));
			FileBackupJob.files.add(new FileCustom(String.valueOf(i+10),"文件"+(i+20),"image","content:"+(i+20)));
			FileBackupJob.files.add(new FileCustom(String.valueOf(i+10),"文件"+(i+30),"radio","content:"+(i+30)));
			FileBackupJob.files.add(new FileCustom(String.valueOf(i+10),"文件"+(i+40),"video","content:"+(i+40)));
		}
		System.out.println("测试数据生成成功");
	}
}
