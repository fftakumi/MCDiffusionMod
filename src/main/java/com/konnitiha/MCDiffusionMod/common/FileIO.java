package com.konnitiha.MCDiffusionMod.common;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class FileIO {
    private static byte[] getImage(String _url) throws IOException {
        URL url = new URL(_url);

        try {
            InputStream in = url.openStream();
            return in.readAllBytes();
        }catch (Exception e) {
            System.out.println(e.toString());
            return new byte[0];
        }
    }
    public class ImageGetter {
        public static final String URL = "ここに取得したい画像が置いてあるURLを書いてね";
        public static final String IMAGENAME = "ここに取得した後の画像の名前を書いてね(拡張子を忘れずに 例：hoge.jpg)";
        public static void main(String[] args) {
            try {
                getImage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        private static void getImage() throws IOException {
            URL url = new URL(URL);
            InputStream in = url.openStream();
            FileOutputStream out = new FileOutputStream(IMAGENAME);
            try {
                outWriter(in, out);
            } finally {
                out.close();
                in.close();
            }
            System.out.println("取得完了♪");
        }
        private static void outWriter(InputStream in, OutputStream out)
                throws IOException {
            byte[] buf = new byte[1024];
            int len = 0;

            while ((len = in.read(buf)) > 0)
                out.write(buf, 0, len);
        }
    }

}
