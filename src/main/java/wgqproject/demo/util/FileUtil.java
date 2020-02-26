package wgqproject.demo.util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
public class FileUtil {
    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);


    public static File multipartFileToFile(MultipartFile file) throws Exception {

        File toFile = null;
        if (file.equals("") || file.getSize() <= 0) {
            file = null;
        } else {
            InputStream ins = null;
            ins = file.getInputStream();
            toFile = new File(file.getOriginalFilename());
            inputStreamToFile(ins, toFile);
            ins.close();
        }
        return toFile;
    }

    //获取流文件
    private static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除本地临时文件
     *
     * @param file
     */
    public static void delteTempFile(File file) {
        if (file != null) {
            File del = new File(file.toURI());
            del.delete();
        }
    }

    public static String fileToString(File readFile) {
        String str = "";
        try {
            FileInputStream in = new FileInputStream(readFile);
            // size 为字串的长度 ，这里一次性读完
            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);
            in.close();
            str = new String(buffer);
        } catch (IOException e) {
            return null;
        }
        return str;
    }

    /**
     * @param filePath
     * @param fileName
     * @return
     */
    public static boolean downloadFile(HttpServletResponse response,String filePath, String fileName) {

        if (fileName != null) {
            File file = new File(filePath, fileName);
            if (file.exists()) {
                response.setContentType("application/force-download");
                response.setHeader("content-type","application/octet-stream");
                response.setHeader("Content-Disposition","attachment;fileName="+fileName);
                byte[] buffer = new byte[1024];
                FileInputStream fis =  null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream outputStream = response.getOutputStream();
                    int i = 0;
                    while((i=bis.read(buffer))!=-1){
                        outputStream.write(buffer,0,i);
                        System.out.println(bis.available());

                        outputStream.flush();
                    }
                    outputStream.close();
                    logger.info("下载文件成功");
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("下载文件失败："+e);
                    return false;
                }finally {
                    if(bis!=null){
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if(bis!=null){
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }
        return false;
    }
}
