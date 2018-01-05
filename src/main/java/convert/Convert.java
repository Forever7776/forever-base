package convert;

/**
 *
 * @author kz
 * @date 2017-11-06
 * 转换类
 */
public class Convert {

    /**
     * 两个数组组合成一个大数组
     *
     * @param src 源数组
     * @param dest 传递的数组
     * @param result 最终返回大数组
     * @return
     */
    public static String[] systemArrayCopy(String[] src, String[] dest, String[] result) {
        System.arraycopy(src, 0, result, 0, src.length);
        System.arraycopy(dest, 0, result, result.length - dest.length, dest.length);
        return result;
    }
}
