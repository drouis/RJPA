package model.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by yjr on 2017/6/26.
 */
public final class UUIDGenerate {
    public  static String getNextId() {
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmss");
        StringBuffer str = new StringBuffer();
        str.append(f.format(new Date()));
        str.append(UUID.randomUUID().toString().replaceAll("-",""));
        return str.toString();
    }

    public  static String getNextUUID() {
        return UUID.randomUUID().toString();
    }

    public  static String getNextIdWithRondom() {
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmss");
        StringBuffer str = new StringBuffer();
        str.append(f.format(new Date()));
        str.append(model.utils.UUID.create().toString());
        str.append(RandomUtils.randomAlphabeticByLowercase(10));
        return str.toString();
    }



    public static void main(String[] args) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(f.format(new Date()));
        for(int i=0;i<10000;i++){
          System.out.println(UUIDGenerate.getNextId());
        }
        System.out.println(f.format(new Date()));

    }

}
