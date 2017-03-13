package com.gordon.rawe;

import com.qiniu.android.common.Zone;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UploadManager;

import java.util.List;

/**
 * Created by gordon on 3/12/17.
 */

public class QiniuClient {
    private QiniuClient() {
        Configuration config = new Configuration.Builder()
                .chunkSize(256 * 1024)  //分片上传时，每片的大小。 默认256K
                .putThreshhold(512 * 1024)  // 启用分片上传阀值。默认512K
                .connectTimeout(10) // 链接超时。默认10秒
                .responseTimeout(60) // 服务器响应超时。默认60秒
                .zone(Zone.zone0) // 设置区域，指定不同区域的上传域名、备用域名、备用IP。
                .build();
        uploadManager = new UploadManager(config);
    }

    private UploadManager uploadManager;

    private static class Holder {
        static QiniuClient instance = new QiniuClient();
    }

    public static QiniuClient getInstance() {
        return Holder.instance;
    }

    public void uploadPictures(List<String> files) {

    }
}
