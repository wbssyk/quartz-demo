package com.shi.quartzdemo.job;

import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.impl.consumer.PullResultExt;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Job1 implements BaseJob {
    private static final Map<MessageQueue, Long> offsetTable = new HashMap<MessageQueue, Long>();

    private static Logger _log = LoggerFactory.getLogger(Job1.class);

    public void execute(JobExecutionContext context)
            throws JobExecutionException {
        _log.error("Hello Job执行时间: " + new Date());
        DefaultMQPullConsumer consumer = new DefaultMQPullConsumer("pullConsumer");
        consumer.setNamesrvAddr("192.168.1.200:9876;192.168.1.201:9876;192.168.1.202:9876");
        try {
            consumer.start();
            Set<MessageQueue> mqs = consumer.fetchSubscribeMessageQueues("Topic-3");
            for (MessageQueue mq : mqs) {
                System.out.println("Consume from the queue: " + mq);
                //	long offset = consumer.fetchConsumeOffset(mq, true);
                //	PullResultExt pullResult =(PullResultExt)consumer.pull(mq, null, getMessageQueueOffset(mq), 32);
                //消息未到达默认是阻塞10秒，private long consumerPullTimeoutMillis = 1000 * 10;
                PullResultExt pullResult = (PullResultExt) consumer.pullBlockIfNotFound(mq, null, getMessageQueueOffset(mq), 2);
                putMessageQueueOffset(mq, pullResult.getNextBeginOffset());
                switch (pullResult.getPullStatus()) {
                    case FOUND:
                        List<MessageExt> messageExtList = pullResult.getMsgFoundList();
                        for (MessageExt m : messageExtList) {
                            System.out.println(new String(m.getBody()));
                        }
                        break;
                    case NO_MATCHED_MSG:
                        break;
                    case NO_NEW_MSG:
                        break;
                    case OFFSET_ILLEGAL:
                        break;
                    default:
                        break;
                }
            }
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        }

    }

    private static void putMessageQueueOffset(MessageQueue mq, long offset) {
        offsetTable.put(mq, offset);
    }

    private static long getMessageQueueOffset(MessageQueue mq) {
        Long offset = offsetTable.get(mq);
        if (offset != null)
            return offset;
        return 0;
    }
}  
