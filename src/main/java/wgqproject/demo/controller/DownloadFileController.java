package wgqproject.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wgqproject.demo.entity.User;
import wgqproject.demo.service.UserService;
import wgqproject.demo.util.CSVUtil;
import wgqproject.demo.util.FileUtil;

import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/test")
public class DownloadFileController{
    private Logger logger = LoggerFactory.getLogger(DownloadFileController.class);
    @Autowired
    private UserService userService;
    @GetMapping("/file")
    public boolean exportLog(User user, HttpServletResponse response) {
        try {
            //查询数据库数据
            List<User> list = userService.user();
            //文件表头
            String[] csvHeaders = {"ID", "名字", "年龄", "时间"};

            //设置服务器存储文件的路径
            String savePath = ResourceUtils.getURL("csmsystem/src/main/resources/log").getPath();
            File file = new File(savePath);
            if (!file.exists()) {
                file.mkdirs();
            }

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.DATE,-1);
            Date time = calendar.getTime();
            String date = new SimpleDateFormat("yyyyMMdd_HHmm").format(time);

            String filename = date+"user.csv";
            String localFileName = file + "/" + filename;

            //存储到服务器
            CSVUtil.writeCSV(list, localFileName, csvHeaders);
            //下载到本地
            boolean b = FileUtil.downloadFile(response, savePath, filename);
            if(b){
                return true;
            }else {
                logger.error("下载文件出错");
                return false;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
