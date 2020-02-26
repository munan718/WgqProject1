package wgqproject.demo.util;

import com.csvreader.CsvWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Iterator;

public class CSVUtil {
    private static final Logger logger = LoggerFactory.getLogger(CSVUtil.class);

    /**
     *
     * @param list 具体数据
     * @param csvFilePath 文件路径
     * @param csvHeaders 表头
     * @param <T>
     */
    public static <T> void writeCSV(Collection<T> list,String csvFilePath,String[] csvHeaders){
        try {
            //定义路径、分隔符、编码  注意分隔符英文与中文的区别
            CsvWriter csvWriter = new CsvWriter(csvFilePath, ',', Charset.forName("UTF-8"));

            //写表头
            csvWriter.writeRecord(csvHeaders);

            //写内容
            Iterator<T> iterator = list.iterator();

            while(iterator.hasNext()){
                T next = (T)iterator.next();
                //获取类属性
                Field[] fields = next.getClass().getDeclaredFields();
                String[] csvContent = new String[fields.length];
                for (int i = 0; i < fields.length; i++) {
                    Field field = fields[i];
                    String name = field.getName();
                    String getMethodName = "get" +name.substring(0,1).toUpperCase()+name.substring(1);

                    try {
                        Class clazz= next.getClass();
                        Method method = clazz.getMethod(getMethodName, new Class[]{});
                        Object value = method.invoke(next, new Object[]{});
                        if(value==null){
                            continue;
                        }
                        //取值并赋值给数组
                        String textValue = value.toString();
                        csvContent[i] = textValue;
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    }
                }
                csvWriter.writeRecord(csvContent);
            }
            csvWriter.close();
            logger.info("csv文件写入成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
