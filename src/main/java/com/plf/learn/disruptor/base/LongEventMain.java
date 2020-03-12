package com.plf.learn.disruptor.base;

import java.nio.ByteBuffer;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;

/**
 * 实现Hello World 
 * 	1、建立Event类
 *  2、建立一个工厂Event类，用于创建Event类实例对象
 *  3、需要有一个监听事件类，用于处理数据(Event类)
 *  4、我们需要进行测试代码编写。实例化Disruptor实例，配置一系列参数。
 *  	然后我们对Disruptor实例绑定监听事件类，接受并处理数据
 *  5、在Disruptor中，真正存储数据的核心叫做RingBuffer，我们通过Disruptor实例拿到它，
 *  	然后把数据生产出来，把数据加到RingBuffer的实例对象即可。
 * 
 * */
public class LongEventMain {

	public static void main(String[] args) throws Exception {
		//创建工厂
		EventFactory<LongEvent> factory = new LongEventFactory();
		//创建bufferSize ,也就是RingBuffer大小，必须是2的N次方
		int ringBufferSize = 1024 * 1024; // 
	
		
		/**
		//BlockingWaitStrategy 是最低效的策略，但其对CPU的消耗最小并且在各种不同部署环境中能提供更加一致的性能表现
		WaitStrategy BLOCKING_WAIT = new BlockingWaitStrategy();
		//SleepingWaitStrategy 的性能表现跟BlockingWaitStrategy差不多，对CPU的消耗也类似，但其对生产者线程的影响最小，适合用于异步日志类似的场景
		WaitStrategy SLEEPING_WAIT = new SleepingWaitStrategy();
		//YieldingWaitStrategy 的性能是最好的，适合用于低延迟的系统。在要求极高性能且事件处理线数小于CPU逻辑核心数的场景中，推荐使用此策略；例如，CPU开启超线程的特性
		WaitStrategy YIELDING_WAIT = new YieldingWaitStrategy();
		*/
		
		//创建disruptor
		//1、第一个参数为工厂类对象，用于创建一个个的LongEvent，LongEvent是实际的消费数据
		//2、第二参数为缓冲区大小
		//3、第三个参数ThreadFactory
		//4、第四个参数ProducerType.SINGLE和ProducerType.MULTI
		//5、第五个参数是一种策略 WaitStrategy
		
		Disruptor<LongEvent> disruptor = 
				new Disruptor<LongEvent>(factory, ringBufferSize,  DaemonThreadFactory.INSTANCE, ProducerType.SINGLE, new BlockingWaitStrategy());
		// 连接消费事件方法
		disruptor.handleEventsWith(new LongEventHandler());
		
		// 启动
		disruptor.start();
		
		//Disruptor 的事件发布过程是一个两阶段提交的过程：
		//使用该方法获得具体存放数据的容器ringBuffer(环形结构)
		//发布事件
		RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
		
		LongEventProducer producer = new LongEventProducer(ringBuffer); 
		//LongEventProducerWithTranslator producer = new LongEventProducerWithTranslator(ringBuffer);
		ByteBuffer byteBuffer = ByteBuffer.allocate(8);
		for(long l = 0; l<100; l++){
			byteBuffer.putLong(0, l);
			producer.onData(byteBuffer);
			//Thread.sleep(1000);
		}

		
		disruptor.shutdown();//关闭 disruptor，方法会堵塞，直至所有的事件都得到处理；
	}
}