package util;

import com.fasterxml.uuid.Generators;
import org.apache.commons.lang3.RandomStringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author kz
 */
public abstract class IdUtil {

    private static final SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    private static final String RANDOM_RANGE = "1234567890ABCDEFGHIGKLMNPQRSDUVWXYZ1234567890";

	public static String randomUUID(){
        UUID uuid = Generators.randomBasedGenerator().generate();
        return uuid.toString();
	}

    /**
     * 生成毫秒级ID
     * @return
     */
    public static synchronized String genMsId(){
        return sf.format(new Date());
    }

    /**
     * 生成随机数
     * @param len
     * @return
     */
    public static synchronized String genRandomNum(int len){
        return RandomStringUtils.randomNumeric(len);
    }

    /**
     * 生成随机码
     * @param len
     * @return
     */
    public static synchronized String genRandomCode(int len){
        return RandomStringUtils.random(len,RANDOM_RANGE);
    }


    /**
     * 生成支付ID
     * @return
     */
    public static synchronized String genPaymentId(){
        return sf.format(new Date()) + RandomStringUtils.randomNumeric(15);
    }

    public static void main(String[] args) {
        System.out.println(IdUtil.genRandomCode(16));
        System.out.println(IdUtil.genRandomNum(6));
    }

}
