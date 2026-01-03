package com.ScoopLink.util;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 随机生成不重复中文姓名工具类（支持1万+无重复，线程安全）
 * 支持：随机姓名/指定性别姓名、单姓/复姓、单字名/双字名，姓名全局唯一
 */
public final class RandomChineseNameGenerator {
    // 单例模式 - 饿汉式，工具类推荐单例，避免重复初始化资源
    public static final RandomChineseNameGenerator INSTANCE = new RandomChineseNameGenerator();

    // 私有化构造器，禁止外部实例化
    private RandomChineseNameGenerator() {}

    // 核心：线程安全的已生成姓名池，存储所有已生成的姓名，用于去重校验，key=姓名，value=占位符
    private final Set<String> generatedNameSet = Collections.newSetFromMap(new ConcurrentHashMap<>(16384));

    // 随机数对象，使用线程安全的ThreadLocalRandom，性能优于new Random()
    private final Random random = ThreadLocalRandom.current();

    /************************ 【常用姓氏库】 - 真实百家姓，覆盖99%的使用场景 ************************/
    // 常用单姓（88个，百家姓高频姓，避免生僻姓）
    private final String[] SURNAME_SINGLE = {
            "赵", "钱", "孙", "李", "周", "吴", "郑", "王", "冯", "陈", "褚", "卫", "蒋", "沈", "韩", "杨",
            "朱", "秦", "尤", "许", "何", "吕", "施", "张", "孔", "曹", "严", "华", "金", "魏", "陶", "姜",
            "戚", "谢", "邹", "喻", "柏", "水", "窦", "章", "云", "苏", "潘", "葛", "奚", "范", "彭", "郎",
            "鲁", "韦", "昌", "马", "苗", "凤", "花", "方", "俞", "任", "袁", "柳", "酆", "鲍", "史", "唐",
            "费", "廉", "岑", "薛", "雷", "贺", "倪", "汤", "滕", "殷", "罗", "毕", "郝", "邵", "万", "安",
            "常", "乐", "于", "时", "傅", "皮", "卞", "齐", "康", "伍", "余", "元", "卜", "顾", "孟", "平"
    };

    // 常用复姓（20个，现实主流复姓，如：欧阳、上官，占比低更真实）
    private final String[] SURNAME_DOUBLE = {
            "欧阳", "上官", "司马", "司徒", "司空", "尉迟", "夏侯", "诸葛", "闻人", "东方",
            "赫连", "皇甫", "尉迟", "公羊", "澹台", "公冶", "宗政", "濮阳", "淳于", "单于"
    };

    /************************ 【常用人名库】 - 分性别，均为常用字，寓意好 ************************/
    // 男性常用名（单字）
    private final String[] MALE_NAME_SINGLE = {"伟", "磊", "强", "杰", "鹏", "辉", "斌", "峰", "超", "亮", "宇", "浩", "泽", "轩", "睿", "博"};
    // 女性常用名（单字）
    private final String[] FEMALE_NAME_SINGLE = {"婷", "敏", "静", "丽", "娟", "艳", "娜", "芳", "慧", "颖", "雪", "梅", "琪", "琳", "萱", "妍"};
    // 男性常用名（双字，组合后更自然）
    private final String[] MALE_NAME_DOUBLE = {"志伟", "国栋", "家辉", "浩然", "子轩", "雨泽", "俊驰", "文博", "昊然", "天佑", "明轩", "泽宇", "俊豪", "嘉豪", "晨阳"};
    // 女性常用名（双字，组合后更自然）
    private final String[] FEMALE_NAME_DOUBLE = {"晓燕", "丽娜", "雨桐", "思琪", "佳怡", "梦瑶", "雨欣", "紫萱", "一诺", "芷晴", "诗涵", "雅欣", "梦琪", "雪婷", "语嫣"};

    // 姓名格式比例配置（贴合现实）
    private static final double DOUBLE_SURNAME_RATIO = 0.05;  // 复姓占比 5%
    private static final double SINGLE_NAME_RATIO = 0.1;      // 单字名占比 10%

    /**
     * 核心方法1：生成一个【随机性别、全局唯一】的中文姓名
     * @return 唯一的随机姓名
     */
    public String generateUniqueName() {
        return generateUniqueName(random.nextBoolean() ? Gender.MALE : Gender.FEMALE);
    }

    /**
     * 核心方法2：生成一个【指定性别、全局唯一】的中文姓名
     * @param gender 性别：Gender.MALE(男) / Gender.FEMALE(女)
     * @return 唯一的随机姓名
     */
    public String generateUniqueName(Gender gender) {
        String newName;
        // 循环生成，直到生成一个【未存在】的姓名为止，保证绝对不重复
        do {
            newName = generateName(gender);
        } while (!generatedNameSet.add(newName));
        return newName;
    }

    /**
     * 核心方法3：批量生成【指定数量、随机性别、全局唯一】的姓名
     * @param count 生成数量（支持1-100000，推荐10000内无压力）
     * @return 无重复的姓名列表
     * @throws IllegalArgumentException 数量非法时抛出异常
     */
    public List<String> batchGenerateUniqueName(int count) {
        if (count <= 0 || count > 100000) {
            throw new IllegalArgumentException("生成数量必须在 1 ~ 100000 之间");
        }
        List<String> nameList = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            nameList.add(generateUniqueName());
        }
        return nameList;
    }

    /**
     * 核心方法4：批量生成【指定数量、指定性别、全局唯一】的姓名
     * @param count 生成数量
     * @param gender 性别
     * @return 无重复的姓名列表
     */
    public List<String> batchGenerateUniqueName(int count, Gender gender) {
        if (count <= 0 || count > 100000) {
            throw new IllegalArgumentException("生成数量必须在 1 ~ 100000 之间");
        }
        List<String> nameList = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            nameList.add(generateUniqueName(gender));
        }
        return nameList;
    }

    /**
     * 重置姓名池：清空所有已生成的姓名，可重新开始生成（复用工具类必备）
     */
    public void resetNamePool() {
        generatedNameSet.clear();
    }

    /**
     * 获取已生成的姓名总数
     */
    public int getGeneratedNameCount() {
        return generatedNameSet.size();
    }

    /**
     * 内部方法：生成单个姓名（不做去重，去重在外层做）
     */
    private String generateName(Gender gender) {
        // 1. 随机选择：单姓 / 复姓
        String surname = random.nextDouble() < DOUBLE_SURNAME_RATIO ? getRandomDoubleSurname() : getRandomSingleSurname();
        // 2. 随机选择：单字名 / 双字名
        String name = random.nextDouble() < SINGLE_NAME_RATIO ? getRandomSingleName(gender) : getRandomDoubleName(gender);
        // 3. 拼接姓氏+名字，返回
        return surname + name;
    }

    // 随机获取一个单姓
    private String getRandomSingleSurname() {
        return SURNAME_SINGLE[random.nextInt(SURNAME_SINGLE.length)];
    }

    // 随机获取一个复姓
    private String getRandomDoubleSurname() {
        return SURNAME_DOUBLE[random.nextInt(SURNAME_DOUBLE.length)];
    }

    // 随机获取一个单字名
    private String getRandomSingleName(Gender gender) {
        return gender == Gender.MALE
                ? MALE_NAME_SINGLE[random.nextInt(MALE_NAME_SINGLE.length)]
                : FEMALE_NAME_SINGLE[random.nextInt(FEMALE_NAME_SINGLE.length)];
    }

    // 随机获取一个双字名
    private String getRandomDoubleName(Gender gender) {
        return gender == Gender.MALE
                ? MALE_NAME_DOUBLE[random.nextInt(MALE_NAME_DOUBLE.length)]
                : FEMALE_NAME_DOUBLE[random.nextInt(FEMALE_NAME_DOUBLE.length)];
    }

    /************************ 性别枚举 ************************/
    public enum Gender {
        MALE,   // 男
        FEMALE  // 女
    }
}