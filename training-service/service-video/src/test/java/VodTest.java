import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.*;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse.PlayInfo;
import org.junit.Test;

import java.util.List;

/**
 * @author ：hc
 * @date ：Created in 2021/1/10 12:53
 * @modified By：
 */
public class VodTest {

    private String accessKeyId = "";
    private String accessKeySecret = "";

    /**
     * 获取视频上传地址和凭证
     */
    @Test
    public void TestGetVideoUrlAndID() throws ClientException {
        // 首先需要一个客户端
        DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(accessKeyId, accessKeySecret);
        // 需要一个上传响应对象
        CreateUploadVideoResponse response = new CreateUploadVideoResponse();
        // 需要上传请求对象
        CreateUploadVideoRequest request = new CreateUploadVideoRequest();
        // 可以通过请求对象来设置一些信息
        request.setTitle("测试视频001");
        request.setFileName("测试视频001.mp4");
        // 客户端执行方法
        response = client.getAcsResponse(request);
        // 可以通过响应对象获取相关信息
        System.out.println("VideoId: " + response.getVideoId());
        System.out.println("UploadAddress: " + response.getUploadAddress());
        System.out.println("UploadAuth: " + response.getUploadAuth());
    }

    /**
     * 获取视频播放地址
     */
    @Test
    public void testGetVideoPlayUrl() throws ClientException {
        // 首先需要一个client
        DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(accessKeyId, accessKeySecret);
        // 创建请求对象，根据视频id获取视频播放地址
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        request.setVideoId("a2503099835347b285dd6d494a96ac91");
        // client进行请求
        GetPlayInfoResponse response = client.getAcsResponse(request);
        // 获取list？
        List<PlayInfo> playInfoList = response.getPlayInfoList();
        for (PlayInfo info : playInfoList) {
            System.out.println("PlayUrl: " + info.getPlayURL());
        }
    }

    /**
     * 上传视频测试
     */
    @Test
    public void uploadVideoTest() {
        // 必填
        String title = "上传SDK测试";
        // 本地文件绝对路径 必填
        String fileName = "D:\\test.mp4";
        // 本地文件上传
        new CreateUploadVideoRequest();
        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        // 分片上传时每个分片的大小
        request.setPartSize(1024 * 1024L);
        // 分片上传时并发线程数
        request.setTaskNum(1);
        UploadVideoImpl uploadVideo = new UploadVideoImpl();
        UploadVideoResponse response = uploadVideo.uploadVideo(request);
        System.out.println(response.getRequestId());
        if (response.isSuccess()) {
            System.out.println("videoId: " + response.getVideoId());
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.println("VideoId=" +  response.getVideoId());
            System.out.println("ErrorCode=" + response.getCode() );
            System.out.println("ErrorMessage=" + response.getMessage());
        }

    }

    /**
     * 删除视频测试
     */
    @Test
    public void testDeleteVideo() throws ClientException {

        DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(accessKeyId, accessKeySecret);
        DeleteVideoRequest request = new DeleteVideoRequest();
        // 支持传入多个id，用逗号分隔
        request.setVideoIds("964112c7c4fb478c9ffd866babca7064,48860e3f311744f8b930315246307cd8,9fcf9f0ece454bec90dd860aeef8ef46,42a28ec6fdca4779af29383a02ccf10c");

        DeleteVideoResponse response = new DeleteVideoResponse();
        try {
            response = client.getAcsResponse(request);
            System.out.println("delete success!");
        } catch (Exception e) {
            System.out.println("ErrorMessage:"+e.getLocalizedMessage());
        }
        System.out.println("requestId:" + response.getRequestId());
    }

    /**
     * 获取视频播放凭证测试
     */
    @Test
    public void testGetVodPlayAuth() throws ClientException {
        // 获取client
        DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(accessKeyId, accessKeySecret);
        // 创建request对象
        GetVideoPlayAuthRequest req = new GetVideoPlayAuthRequest();
        req.setVideoId("c80f83b201514c53b785e25d719c62b9");
        // 使用client发送request请求
        GetVideoPlayAuthResponse response = client.getAcsResponse(req);
        // 获取response对象，得到凭证
        String playAuth = response.getPlayAuth();
        System.out.println("播放凭证为：" + playAuth);
    }

    /**
     * 获取视频详情信息测试
     */
    @Test
    public void testGetVodInfo() throws ClientException {
        // 获取client
        DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(accessKeyId, accessKeySecret);
        // GetVideoInfoRequest
        GetVideoInfoRequest infoRequest = new GetVideoInfoRequest();
        infoRequest.setVideoId("ea5a9e9f785847aba08b91280a03f88e");
        GetVideoInfoResponse response = client.getAcsResponse(infoRequest);
        System.out.println("视频大小：" + response.getVideo().getSize());
        System.out.println("视频时长：" + response.getVideo().getDuration());
    }
}
