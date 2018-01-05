package enums;

/**
 *
 * @author kz
 * @date 2018-01-03
 */
public class ResultEnum {

    public ResultEnum() {
    }

    public static enum STATUS {
        SUCCESS(Integer.valueOf(0), "成功"),
        FAIL(Integer.valueOf(1), "失败"),
        ERROR(Integer.valueOf(2), "错误");

        private Integer code;
        private String desc;

        public Integer getCode() {
            return this.code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getDesc() {
            return this.desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        private STATUS(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }
}
