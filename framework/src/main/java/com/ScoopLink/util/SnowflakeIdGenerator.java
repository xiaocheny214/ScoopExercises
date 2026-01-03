package com.ScoopLink.util;

/**
 * 雪花算法ID生成器
 * 64位ID = 1位符号位 + 41位时间戳 + 10位机器ID + 12位序列号
 */
public class SnowflakeIdGenerator {
    
    // 起始时间戳 (2023-01-01)
    private final long twepoch = 1672531200000L;
    
    // 机器ID所占位数
    private final long workerIdBits = 5L;
    
    // 数据中心ID所占位数
    private final long dataCenterIdBits = 5L;
    
    // 序列号所占位数
    private final long sequenceBits = 12L;
    
    // 机器ID最大值
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);
    
    // 数据中心ID最大值
    private final long maxDataCenterId = -1L ^ (-1L << dataCenterIdBits);
    
    // 机器ID偏移量
    private final long workerIdShift = sequenceBits;
    
    // 数据中心ID偏移量
    private final long dataCenterIdShift = sequenceBits + workerIdBits;
    
    // 时间戳偏移量
    private final long timestampLeftShift = sequenceBits + workerIdBits + dataCenterIdBits;
    
    // 序列号掩码
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);
    
    private long workerId; // 工作机器ID
    private long dataCenterId; // 数据中心ID
    private long sequence = 0L; // 序列号
    private long lastTimestamp = -1L; // 上次生成ID的时间戳
    
    public SnowflakeIdGenerator(long workerId, long dataCenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException("Worker ID超出范围，应在[0, " + maxWorkerId + "]之间");
        }
        if (dataCenterId > maxDataCenterId || dataCenterId < 0) {
            throw new IllegalArgumentException("Data Center ID超出范围，应在[0, " + maxDataCenterId + "]之间");
        }
        this.workerId = workerId;
        this.dataCenterId = dataCenterId;
    }
    
    /**
     * 生成下一个ID
     *
     * @return 雪花算法生成的唯一ID
     */
    public synchronized long nextId() {
        long timestamp = timeGen();
        
        // 如果当前时间小于上次生成ID的时间，说明系统时钟回退，抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException("系统时钟回退，请调整系统时间");
        }
        
        // 如果是同一毫秒内生成的ID，则序列号递增
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            
            // 如果序列号达到最大值，则等待下一毫秒
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            // 不同毫秒内，序列号重置为0
            sequence = 0L;
        }
        
        lastTimestamp = timestamp;
        
        // 组合ID
        return ((timestamp - twepoch) << timestampLeftShift) |
               (dataCenterId << dataCenterIdShift) |
               (workerId << workerIdShift) |
               sequence;
    }
    
    /**
     * 等待下一毫秒
     *
     * @param lastTimestamp 上次生成ID的时间戳
     * @return 新的时间戳
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }
    
    /**
     * 获取当前时间戳
     *
     * @return 当前时间戳
     */
    private long timeGen() {
        return System.currentTimeMillis();
    }
    
    /**
     * 将ID转换为字符串
     *
     * @return ID字符串
     */
    public String nextIdAsString() {
        return String.valueOf(nextId());
    }
}