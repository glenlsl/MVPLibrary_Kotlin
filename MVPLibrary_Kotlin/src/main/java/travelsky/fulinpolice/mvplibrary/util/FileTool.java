package travelsky.fulinpolice.mvplibrary.util;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.facebook.common.file.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.Okio;
import solin.mvplibrary.app.CommonApp;

/**
 * Created by Solin on 2017/3/31.
 */

public class FileTool {
    public static FileTool get() {
        return Builder.util;
    }

    private final String[][] MIME_MapTable = {
            //{后缀名，MIME类型}
            {".3gp", "video/3gpp"},
            {".apk", "application/vnd.android.package-archive"},
            {".asf", "video/x-ms-asf"},
            {".avi", "video/x-msvideo"},
            {".bin", "application/octet-stream"},
            {".bmp", "image/bmp"},
            {".c", "text/plain"},
            {".class", "application/octet-stream"},
            {".conf", "text/plain"},
            {".cpp", "text/plain"},
            {".doc", "application/msword"},
            {".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
            {".xls", "application/vnd.ms-excel"},
            {".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
            {".exe", "application/octet-stream"},
            {".gif", "image/gif"},
            {".gtar", "application/x-gtar"},
            {".gz", "application/x-gzip"},
            {".h", "text/plain"},
            {".htm", "text/html"},
            {".html", "text/html"},
            {".jar", "application/java-archive"},
            {".java", "text/plain"},
            {".jpeg", "image/jpeg"},
            {".jpg", "image/jpeg"},
            {".js", "application/x-javascript"},
            {".log", "text/plain"},
            {".m3u", "audio/x-mpegurl"},
            {".m4a", "audio/mp4a-latm"},
            {".m4b", "audio/mp4a-latm"},
            {".m4p", "audio/mp4a-latm"},
            {".m4u", "video/vnd.mpegurl"},
            {".m4v", "video/x-m4v"},
            {".mov", "video/quicktime"},
            {".mp2", "audio/x-mpeg"},
            {".mp3", "audio/x-mpeg"},
            {".mp4", "video/mp4"},
            {".mpc", "application/vnd.mpohun.certificate"},
            {".mpe", "video/mpeg"},
            {".mpeg", "video/mpeg"},
            {".mpg", "video/mpeg"},
            {".mpg4", "video/mp4"},
            {".mpga", "audio/mpeg"},
            {".msg", "application/vnd.ms-outlook"},
            {".ogg", "audio/ogg"},
            {".pdf", "application/pdf"},
            {".png", "image/png"},
            {".pps", "application/vnd.ms-powerpoint"},
            {".ppt", "application/vnd.ms-powerpoint"},
            {".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"},
            {".prop", "text/plain"},
            {".rc", "text/plain"},
            {".rmvb", "audio/x-pn-realaudio"},
            {".rtf", "application/rtf"},
            {".sh", "text/plain"},
            {".tar", "application/x-tar"},
            {".tgz", "application/x-compressed"},
            {".txt", "text/plain"},
            {".wav", "audio/x-wav"},
            {".wma", "audio/x-ms-wma"},
            {".wmv", "audio/x-ms-wmv"},
            {".wps", "application/vnd.ms-works"},
            {".xml", "text/plain"},
            {".z", "application/x-compress"},
            {".zip", "application/x-zip-compressed"},
            {"", "*/*"}
    };

    public void openFiles(String filesPath) {
        Uri uri = Uri.parse("file://" + filesPath);
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);

        String type = getMIMEType(filesPath);
        intent.setDataAndType(uri, type);
        if (!type.equals("*/*")) {
            try {
                CommonApp.get().startActivity(intent);
            } catch (Exception e) {
                CommonApp.get().startActivity(showOpenTypeDialog(filesPath));
            }
        } else {
            CommonApp.get().startActivity(showOpenTypeDialog(filesPath));
        }
    }

    //显示打开方式
    public void show(String filesPath) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        CommonApp.get().startActivity(showOpenTypeDialog(filesPath));
    }

    public static Intent showOpenTypeDialog(String param) {
        Log.e("ViChildError", "showOpenTypeDialog");
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "*/*");
        return intent;
    }

    /**
     * 根据文件后缀名获得对应的MIME类型。
     */
    private String getMIMEType(String fileName) {
        String type = "*/*";
        //获取后缀名前的分隔符"."在fName中的位置。
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex < 0) {
            return type;
        }
    /* 获取文件的后缀名*/
        String end = fileName.substring(dotIndex, fileName.length()).toLowerCase();
        if (TextUtils.equals(end, ""))
            return type;
        //在MIME和文件类型的匹配表中找到对应的MIME类型。
        for (int i = 0; i < MIME_MapTable.length; i++) { //MIME_MapTable??在这里你一定有疑问，这个MIME_MapTable是什么？
            if (end.equals(MIME_MapTable[i][0]))
                type = MIME_MapTable[i][1];
        }
        return type;
    }

    /**
     * 删除文件
     *
     * @param path
     */
    public void deleteFile(String path) {
        File file = new File(path);
        deleteFile(file);
    }

    public void deleteFile(String fileDir, String fileName) {
        File file = new File(fileDir, fileName);
        deleteFile(file);
    }

    /**
     * 删除文件
     *
     * @param file
     */
    public void deleteFile(File file) {
        if (!file.exists()) {
            return;
        }
        if (file.isFile()) {
            file.delete();
        } else if (file.isDirectory()) {
            File files[] = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                deleteFile(files[i]);
            }
        }
        file.delete();
    }

    /**
     * 写入断点续传文件
     */
    public void writeCache(ResponseBody responseBody, String fileDir, String fileName) throws IOException {
        File file = new File(fileDir, fileName);
        FileUtils.mkdirs(file.getParentFile());
        BufferedSink sink = null;
        //下载文件到本地
        try {
            sink = Okio.buffer(Okio.sink(file));
            sink.writeAll(responseBody.source());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (sink != null)
                    sink.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 直接写入文件
     */
    private void saveFile(ResponseBody body, String fileDir, String fileName) throws IOException {
        InputStream is;
        byte[] buf = new byte[2048];
        int len;
        FileOutputStream fos;
        is = body.byteStream();
        File dir = new File(fileDir);
        FileUtils.mkdirs(dir);
        File file = new File(dir, fileName);
        fos = new FileOutputStream(file);
        while ((len = is.read(buf)) != -1) {
            fos.write(buf, 0, len);
        }
        fos.flush();
        is.close();
        fos.close();
    }

    static class Builder {
        public static FileTool util = new FileTool();
    }
}
