package com.plf.learn.kafka.lower;

import kafka.api.FetchRequest;
import kafka.api.FetchRequestBuilder;
import kafka.cluster.Broker;
import kafka.javaapi.FetchResponse;
import kafka.javaapi.PartitionMetadata;
import kafka.javaapi.TopicMetadata;
import kafka.javaapi.TopicMetadataRequest;
import kafka.javaapi.TopicMetadataResponse;
import kafka.javaapi.consumer.SimpleConsumer;
import kafka.javaapi.message.ByteBufferMessageSet;
import kafka.message.MessageAndOffset;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LowerConsumer {
	
	//根据指定的Topic、Partition、offset来获取数据
	public static void main(String[] args) {
		//定义相关参数
		ArrayList<String> broker = new ArrayList<>();
		broker.add("loaclhost");
		
		//端口号
		int port = 9020;
		
		//Topic
		String topic = "kafka-topic";
		
		//分区
		int partition = 0;
		
		//offset
		long offset = 2;
		
		LowerConsumer lowerConsumer = new LowerConsumer();
		
		lowerConsumer.getData(broker, port, topic, partition, offset);
	}
	
	//找分区leader
	private Broker findLeader(List<String> brokers,int port,String topic,int partition){
		for (String broker : brokers) {
			//创键获取分区leader的消费者对象
			SimpleConsumer getLeader = new SimpleConsumer(broker,
					port,
					1000,
					1024*4,
					"getLeader");
			
			//创键一个主题元数据信息请求
			TopicMetadataRequest topicMetadataRequest = new TopicMetadataRequest(Collections.singletonList(topic));
			
			//获取主题元数据返回值
			TopicMetadataResponse metadataResponse = getLeader.send(topicMetadataRequest);
			
			//解析元数据返回值
			List<TopicMetadata> topicMetadataList = metadataResponse.topicsMetadata();
			
			//遍历主题元数据
			for (TopicMetadata topicMetadata : topicMetadataList) {
				//获取多个分区的元数据信息
				List<PartitionMetadata> partitionMetadataList = topicMetadata.partitionsMetadata();
				
				//遍历分区元数据
				for (PartitionMetadata partitionMetadata : partitionMetadataList) {
					if(partition==partitionMetadata.partitionId()){
						return partitionMetadata.leader();
					}
				}	
			}	
		}
		return null;
	}
	
	//获取数据
	private void getData(List<String> brokers,int port,String topic,int partition,long offset){
		//获取分区leader
		Broker leader = findLeader(brokers,port,topic,partition);
		
		if(leader== null){
			return;
		}
		
		String leaderHost = leader.host();
		
		//获取数据的消费者对象
		SimpleConsumer getData = new SimpleConsumer(leaderHost,
				port,
				1000,
				1024*4,
				"getData");
		
		//创建获取数据的对象
		FetchRequest fetchRequest = new FetchRequestBuilder().addFetch(topic,
				partition, 
				offset, 
				100).build();
		
		//获取数据返回值
		FetchResponse fetchResponse = getData.fetch(fetchRequest);
		
		//解析返回值
		ByteBufferMessageSet messageAndOffsets = fetchResponse.messageSet(topic, partition);
		for (MessageAndOffset messageAndOffset : messageAndOffsets) {
			long offset1 = messageAndOffset.offset();
			ByteBuffer payload = messageAndOffset.message().payload();
			byte[] bytes= new byte[payload.limit()];
			payload.get(bytes);
			System.out.println(offset1+":"+new String(bytes));
		}
	}
	
}
