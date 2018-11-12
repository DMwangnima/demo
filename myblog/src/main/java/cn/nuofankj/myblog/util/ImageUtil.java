package cn.nuofankj.myblog.util;

import cn.nuofankj.myblog.constant.BlogConstant;
import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ImageUtil {

    private static Configuration cfg = new com.qiniu.storage.Configuration(Zone.zone2());
    private static UploadManager uploadManager = new UploadManager(cfg);

    /**
     * 七牛云上传
     * @param localFilePath 本地地址
     * @param fileName 文件名
     * @param keeyDay 保存天数
     * @return
     */
    public static String upload(String localFilePath, String fileName, int keeyDay) {
        Preconditions.checkArgument(BlogConstant.enable, "不允许上传");
        Auth auth = Auth.create(BlogConstant.accessKey, BlogConstant.secretKey);
        String upToken = auth.uploadToken(BlogConstant.bucket);

        try {
            Response response = uploadManager.put(localFilePath, fileName, upToken);
            DefaultPutRet putRet = (DefaultPutRet)(new Gson()).fromJson(response.bodyString(), DefaultPutRet.class);
            BucketManager bucketManager = new BucketManager(auth, cfg);
            if (keeyDay != 0) {
                bucketManager.deleteAfterDays(BlogConstant.bucket, fileName, keeyDay);
            }

            log.info("result:{},{}", putRet.hash, putRet.key);
        } catch (QiniuException var9) {
            Response r = var9.response;
            log.error(r.toString());
        }

        String finalUrl = String.format("%s/%s", BlogConstant.domainOfBucket, fileName);
        return finalUrl;
    }

    public static void main(String[] args) {
        System.out.println(ImageUtil.upload("C:\\Users\\2978\\Pictures\\Screenshots\\1.png","t.jpg", 1));
    }

}
