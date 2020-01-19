package com.plf.learn.elasticjob;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

public class FileBackupJob implements SimpleJob{

	//每次任务执行要备份文件的数量
	private final int FETCH_SIZE=1;
	
	//文件列表
	public static List<FileCustom> files = new ArrayList<>();
	
	@Override
	public void execute(ShardingContext shardingContext) {
		System.out.println("作业分片:"+shardingContext.getShardingItem());
		//获取未备份文件
		List<FileCustom> fileCustoms = fetchUnBackupFiles(FETCH_SIZE);
		//进行文件备份
		backupFiles(fileCustoms);
	}
	
	/**
	 * 获取未备份文件
	 * @param count
	 * @return
	 */
	public List<FileCustom> fetchUnBackupFiles(int count){
		//要取的文件列表
		List<FileCustom> fetchList = new ArrayList<>();
		
		int num = 0;
		for(FileCustom fileCustom:files) {
			if(num >= count) {
				break;
			}
			
			if(!fileCustom.getBackUp()) {
				fetchList.add(fileCustom);
				num++;
			}
		}
		System.out.printf("time:%s,获取文件%d个\n",LocalDateTime.now(),num);
		return fetchList;
	}
	
	/**
	 * 文件备份
	 * @param files
	 */
	public void backupFiles(List<FileCustom> files) {
		for (FileCustom fileCustom : files) {
			fileCustom.setBackUp(true);
			System.out.printf("time:%s,备份文件,名称:%s,类型:%s\n",LocalDateTime.now(),fileCustom.getName(),fileCustom.getType());
		}
	}

}
