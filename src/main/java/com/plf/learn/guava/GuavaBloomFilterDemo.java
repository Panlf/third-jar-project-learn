package com.plf.learn.guava;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.util.Random;

/**
 * @author panlf
 * @date 2021/7/21
 */
public class GuavaBloomFilterDemo {

    public static void main(String[] args) {
        // 放入 1000 万条数据
        int total = 10000000;
        BloomFilter<CharSequence> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), total);
        for (int i = 0; i < total; i++) {
            bloomFilter.put("" + i);
        }

        // 随机查找 10000 个数
        long startTime = System.currentTimeMillis();
        int count1 = 0;
        int count2 = 0;
        for (int i = 0; i < 10000; i++) {
            int num = new Random().nextInt(100000000);
            if (num < total) {
                count1++;
            }
            String numStr = num + "";
            if (bloomFilter.mightContain(numStr)) {
                count2++;
            }
        }
        System.out.println("匹配数据:" + count2 + "条，耗时" + (System.currentTimeMillis() - startTime) + "ms");
        if (count1 != count2) {
            System.out.println("误判:" + Math.abs(count2 - count1) + "条");
        }
    }

}