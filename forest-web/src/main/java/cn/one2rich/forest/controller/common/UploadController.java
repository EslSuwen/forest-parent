package cn.one2rich.forest.controller.common;

import cn.one2rich.forest.core.exception.BaseApiException;
import cn.one2rich.forest.core.exception.ErrorCode;
import cn.one2rich.forest.dto.LinkToImageUrlDTO;
import cn.one2rich.forest.dto.TokenUser;
import cn.one2rich.forest.dto.result.Result;
import cn.one2rich.forest.jwt.def.JwtConstants;
import cn.one2rich.forest.util.FileUtils;
import cn.one2rich.forest.util.UserUtils;
import cn.one2rich.forest.util.Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * 文件上传控制器
 *
 * @author ronger
 */
@Api(value = "文件上传", tags = "文件上传")
@Log4j2
@RestController
@RequestMapping("/api/upload")
public class UploadController {

  @Value("resource.pic-path:../static")
  private String HEAD_PATH;

  private static final String UPLOAD_SIMPLE_URL = "/api/upload/file";
  private static final String UPLOAD_URL = "/api/upload/file/batch";
  private static final String LINK_TO_IMAGE_URL = "/api/upload/file/link";

  @ApiOperation("单文件上传")
  @PostMapping("/file")
  public Result<?> uploadPicture(
      @RequestParam(value = "file", required = false) MultipartFile multipartFile,
      @RequestParam(defaultValue = "1") Integer type) {
    if (multipartFile == null) {
      return Result.error("请选择要上传的文件");
    }
    String typePath = getTypePath(type);
    // 图片存储路径
    String ctxHeadPicPath = HEAD_PATH;
    String dir = ctxHeadPicPath + "/" + typePath;
    File file = new File(dir);
    if (!file.exists()) {
      file.mkdirs(); // 创建文件根目录
    }
    String localPath = Utils.getProperty("resource.file-path") + "/" + typePath + "/";
    String orgName = multipartFile.getOriginalFilename();
    String fileName = System.currentTimeMillis() + "." + FileUtils.getExtend(orgName).toLowerCase();
    String savePath = file.getPath() + File.separator + fileName;
    File saveFile = new File(savePath);
    try {
      FileCopyUtils.copy(multipartFile.getBytes(), saveFile);
    } catch (IOException e) {
      return Result.error("上传失败!");
    }
    return Result.OK(localPath + fileName);
  }

  @ApiOperation("多文件上传")
  @PostMapping("/file/batch")
  public Object batchFileUpload(
      @RequestParam(value = "file[]", required = false) MultipartFile[] multipartFiles,
      @RequestParam(defaultValue = "1") Integer type) {
    String typePath = getTypePath(type);
    // 图片存储路径
    String ctxHeadPicPath = HEAD_PATH;
    String dir = ctxHeadPicPath + "/" + typePath;
    System.out.println(dir);
    File file = new File(dir);
    if (!file.exists()) {
      file.mkdirs(); // 创建文件根目录
    }

    String localPath = Utils.getProperty("resource.file-path") + "/" + typePath + "/";
    Map<String, Object> succMap = new HashMap<>(10);
    Set<Object> errFiles = new HashSet<>();

    for (MultipartFile multipartFile : multipartFiles) {
      String orgName = multipartFile.getOriginalFilename();
      String fileName =
          System.currentTimeMillis() + "." + FileUtils.getExtend(orgName).toLowerCase();
      String savePath = file.getPath() + File.separator + fileName;
      File saveFile = new File(savePath);
      try {
        FileCopyUtils.copy(multipartFile.getBytes(), saveFile);
        succMap.put(orgName, localPath + fileName);
      } catch (IOException e) {
        errFiles.add(orgName);
      }
    }
    Map<String, Object> data = new HashMap<>(2);
    data.put("errFiles", errFiles);
    data.put("succMap", succMap);
    Map<String, Object> result = new HashMap<>(1);
    result.put("data", data);
    log.info(result);
    return result;
  }

  private static String getTypePath(Integer type) {
    String typePath;
    switch (type) {
      case 0:
        typePath = "avatar";
        break;
      case 1:
        typePath = "article";
        break;
      case 2:
        typePath = "tags";
        break;
      default:
        typePath = "images";
    }
    return typePath;
  }

  @ApiOperation("多文件上传token获取")
  @GetMapping("/token")
  public Result<Map<String, Object>> uploadToken(HttpServletRequest request)
      throws BaseApiException {
    String authHeader = request.getHeader(JwtConstants.AUTHORIZATION);
    if (StringUtils.isBlank(authHeader)) {
      throw new BaseApiException(ErrorCode.UNAUTHORIZED);
    }
    TokenUser tokenUser = UserUtils.getTokenUser(authHeader);
    Map<String, Object> map = new HashMap<>(2);
    map.put("uploadToken", tokenUser.getToken());
    map.put("uploadURL", UPLOAD_URL);
    map.put("linkToImageURL", LINK_TO_IMAGE_URL);
    return Result.OK(map);
  }

  @ApiOperation("图片连接关联")
  @PostMapping("/file/link")
  public Result<Map<String, Object>> linkToImageUrl(
      @RequestBody LinkToImageUrlDTO linkToImageUrlDTO) throws IOException {
    String url = linkToImageUrlDTO.getUrl();
    URL link = new URL(url);
    HttpURLConnection conn = (HttpURLConnection) link.openConnection();
    // 设置超时间为3秒
    conn.setConnectTimeout(3 * 1000);
    // 防止屏蔽程序抓取而返回403错误
    conn.setRequestProperty(
        "User-Agent",
        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.75 Safari/537.36");
    conn.setRequestProperty("referer", "");
    // 得到输入流
    InputStream inputStream = conn.getInputStream();
    // 获取自己数组
    byte[] getData = readInputStream(inputStream);
    Integer type = linkToImageUrlDTO.getType();
    if (Objects.isNull(type)) {
      type = 1;
    }
    String typePath = getTypePath(type);
    // 图片存储路径
    String ctxHeadPicPath = HEAD_PATH;
    String dir = ctxHeadPicPath + "/" + typePath;
    File file = new File(dir);
    if (!file.exists()) {
      file.mkdirs(); // 创建文件根目录
    }

    String localPath = Utils.getProperty("resource.file-path") + "/" + typePath + "/";

    String orgName = url.substring(url.lastIndexOf("."));
    String fileName = System.currentTimeMillis() + FileUtils.getExtend(orgName).toLowerCase();

    String savePath = file.getPath() + File.separator + fileName;

    Map<String, Object> data = new HashMap<>(2);
    File saveFile = new File(savePath);
    try {
      FileCopyUtils.copy(getData, saveFile);
      data.put("originalURL", url);
      data.put("url", localPath + fileName);
    } catch (IOException e) {
      data.put("message", "上传失败!");
    }
    return Result.OK(data);
  }

  /**
   * 从输入流中获取字节数组
   *
   * @param inputStream
   * @return
   * @throws IOException
   */
  public static byte[] readInputStream(InputStream inputStream) throws IOException {
    byte[] buffer = new byte[1024];
    int len = 0;
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    while ((len = inputStream.read(buffer)) != -1) {
      bos.write(buffer, 0, len);
    }
    bos.close();
    return bos.toByteArray();
  }
}
