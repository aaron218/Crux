package net.newstring.crux.core.lang;


import net.newstring.crux.core.annotation.stable;

import java.util.Arrays;
import java.util.Random;

/**
 *
 */
@stable
public final class RandomUtil {

    private static Random rnd = null;

    private RandomUtil() {
    }

    /**
     * 初始化随机数发生器。
     */
    private static void initRnd() {
        if (rnd == null) {
            rnd = new Random();
        }
    }

    /**
     * 计算并返回无重复值的以 <code>min</code> 为下限 <code>max</code> 为上限的随机整数数组。
     * 最大值最小值参数会自动重排，按照实际最大值最小值范围来处理
     *
     * @param min 随机整数下限（包含）
     * @param max 随机整数上限（包含）
     * @param len 结果数组长度
     * @return 结果数组
     */
    @stable
    public static int[] getLotteryArray(int min, int max, int len) {
        //参数校验及性能优化
        if (len < 0) {
            return null;  //长度小于 0 的数组不存在
        }
        if (len == 0) {
            return new int[0];  //返回长度为 0 的数组
        }
        if (min > max) {  //校正参数 min max0
            int t = min;
            min = max;
            max = t;
        }
        final int LEN = max - min + 1;  //种子个数
        if (len > LEN) {
            len = LEN;
        }
        //计算无重复值随机数组
        initRnd();  //初始化随机数发生器
        int[] seed = new int[LEN];  //种子数组
        for (int i = 0, n = min; i < LEN; ) {
            seed[i++] = n++;
        } //初始化种子数组
        for (int i = 0, j = 0, t = 0; i < len; ++i) {
            j = rnd.nextInt(LEN - i) + i;
            t = seed[i];
            seed[i] = seed[j];
            seed[j] = t;
        }
        return Arrays.copyOf(seed, len);
    }

    /**
     * 获取随机的布尔值 并且按照真值比例给出，比例数值限定在0-1之间，大于1的，视为1，小于0的，视为0
     *
     * @param trueRate 真值比例
     * @return 布尔对象
     */
    public static Boolean getRandomBoolean(Double trueRate) {
        if (trueRate >= 1.0) {
            return true;
        } else if (trueRate <= 0.0) {
            return false;
        }
        return (Math.random() <= trueRate);
    }

    /**
     * 获取随机的布尔值，真假各半
     *
     * @return 布尔对象
     */
    public static Boolean getRandomBoolean() {
        return getRandomBoolean(0.5);
    }

    /**
     * 获取一个随机的Int值
     *
     * @return 随机Int值
     */
    @stable
    public synchronized static Integer getRandomIntValue() {
        return (new Double(Math.random() * Integer.MAX_VALUE)).intValue();
    }

    /**
     * 获取一个随机的Long值
     *
     * @return 随机Long值
     */
    @stable
    public synchronized static Long getRandomLongValue() {
        return (new Double(Math.random() * Long.MAX_VALUE)).longValue();
    }

    /**
     * 获取一个随机的Float值
     *
     * @return 随机Float值
     */
    @stable
    public synchronized static Float getRandomFloatValue() {
        return (new Double(Math.random() * Float.MAX_VALUE)).floatValue();
    }

    /**
     * 获取一个随机的Double值
     *
     * @return 随机Double值
     */
    @stable
    public synchronized static Double getRandomDoubleValue() {
        return (Math.random() * Double.MAX_VALUE);
    }

    /**
     * 获取一个随机的Int值 可以设置最大值，最大值不能达到
     *
     * @return 随机Int值
     */
    @stable
    public synchronized static Integer getRandomIntValue(Integer max) {
        return (new Double(Math.random() * max)).intValue();
    }

    /**
     * 获取一个随机的Long值 可以设置最大值，最大值不能达到
     *
     * @return 随机Long值
     */
    @stable
    public synchronized static Long getRandomLongValue(Long max) {
        return (new Double(Math.random() * max)).longValue();
    }

    /**
     * 获取一个随机的Float值 可以设置最大值，最大值不能达到
     *
     * @return 随机Float值
     */
    @stable
    public synchronized static Float getRandomFloatValue(Float max) {
        return (new Double(Math.random() * max)).floatValue();
    }

    /**
     * 获取一个随机的Double值 可以设置最大值，最大值不能达到
     *
     * @return 随机Double值
     */
    @stable
    public synchronized static Double getRandomDoubleValue(Double max) {
        return (Math.random() * max);
    }

}