package qiniu;

import com.alibaba.fastjson.JSONObject;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @author kz
 * @date 2017年12月1日14:49:00
 * @desc 七牛api
 */
public class QiNiuApi {
    public final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static String uploadToken;
    private static UploadManager uploadManager;
    public static QiNiuApi qiNiu;
    public static Auth auth;
    private static String bucket;
    private static BucketManager bucketManager;

    public QiNiuApi(String ak, String sk, String bucket) {
        auth = Auth.create(ak, sk);
        QiNiuApi.bucket = bucket;
        reload();
        uploadManager = new UploadManager(new Configuration());
        qiNiu = this;
    }

    public static void reload() {
        uploadToken = auth.uploadToken(bucket);
    }

    /**
     * 移动文件，要求空间在同一账号下
     *
     * @param fromBucket  源空间名称
     * @param fromFileKey 源文件名称
     * @param toBucket    目的空间名称
     * @param toFileKey   目的文件名称
     * @param force       强制覆盖空间中已有同名（和 toFileKey 相同）的文件
     * @throws QiniuException
     */
    public static void moveFile(String fromBucket, String fromFileKey, String toBucket, String toFileKey, boolean force) throws QiniuException {
        bucketManager.move(fromBucket, fromFileKey, toBucket, toFileKey, force);
    }

    /**
     * 删除指定空间、文件名的文件
     *
     * @param bucket 空间名称
     * @param key    文件名称
     * @throws QiniuException
     * @link http://developer.qiniu.com/kodo/api/delete
     */
    public static void delete(String bucket, String key) throws QiniuException {
        bucketManager.delete(bucket, key);
    }

    /**
     * 上传文件
     *
     * @param file
     * @return
     * @throws QiniuException
     */
    public static JSONObject upload(File file) throws QiniuException {
        Response resp = uploadManager.put(file, file.getName(), uploadToken);
        return getResult(resp);
    }

    /**
     * 文件流上传
     *
     * @param fileByte
     * @param fileKey
     * @return
     * @throws QiniuException
     */
    public static JSONObject uploadFileByte(byte[] fileByte, String fileKey) throws QiniuException {
        Response resp = uploadManager.put(fileByte, fileKey, uploadToken);
        return getResult(resp);
    }

    /**
     * 获取返回结果
     *
     * @param response
     * @return
     * @throws QiniuException
     */
    public static JSONObject getResult(Response response) throws QiniuException {
        JSONObject jo = new JSONObject();
        if (response.isOK()) {
            String result = response.bodyString();
            jo = JSONObject.parseObject(result);
            jo.put("result_code", "success");
        } else {
            jo.put("result_code", "error");
        }
        return jo;
    }
}
