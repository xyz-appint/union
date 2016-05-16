package xyz.appint.union.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

public class FileUtils {
    public static String readFileUTF_8(String filePath) {
        String templetContent = "";
        try {
            FileInputStream fileinputstream = new FileInputStream(filePath);
            int length = fileinputstream.available();
            byte bytes[] = new byte[length];
            fileinputstream.read(bytes);
            fileinputstream.close();
            templetContent = new String(bytes, "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return templetContent;
    }

    /**
     * 写文件
     *
     * @param filePath
     * @param templetContent
     * @return
     */
    public static boolean writeFile(String filePath, String templetContent) {
        return writeFile(filePath, templetContent, "UTF-8");
    }

    public static boolean writeFile(String filePath, String templetContent,
                                    String charsetName) {
        boolean isSucc = false;
        try {
            if (filePath != null && !filePath.equals("")) {
                File file = new File(filePath.substring(0, filePath
                        .lastIndexOf(File.separator)));
                if (file.exists() == false) {
                    file.mkdirs();
                }
            }
            FileOutputStream fout = new FileOutputStream(filePath);
            OutputStreamWriter out = new OutputStreamWriter(
                    new BufferedOutputStream(fout), charsetName);
            out.write(templetContent);
            out.close();
            fout.close();
            isSucc = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isSucc;
    }

    public static boolean wirte(String filePath, File file) {
        boolean isSucc = false;
        try {
            if (filePath != null && !filePath.equals("")) {
                File f = new File(filePath.substring(0, filePath
                        .lastIndexOf("/")));
                if (f.exists() == false) {
                    f.mkdirs();
                }
            }
            FileOutputStream fos = new FileOutputStream(filePath);
            FileInputStream fis = new FileInputStream(file);
            int length = fis.available();
            //byte bytes[] = new byte[length];
            byte[] buffer = new byte[length];
            int len = 0;
            while ((len = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
            fis.close();
            fos.close();
            isSucc = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isSucc;
    }

    public static String getWebFile(String url) {
        InputStream is = null;
        BufferedReader reader = null;
        HttpURLConnection huc = null;
        StringBuffer temp = null;
        try {
            huc = (HttpURLConnection) new URL(url).openConnection();
            huc.setRequestMethod("GET");
            huc.setUseCaches(true);
            huc.connect();

            is = huc.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));

            temp = new StringBuffer();
            String str;
            while ((str = reader.readLine()) != null) {
                temp.append(str + "\r");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
                is.close();
                huc.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return temp.toString();
    }


}
