package Handlers;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Scanner;

/**
 * Created by devey on 3/2/17.
 */

public class Serializer {
    Gson gson = new Gson();
    public String ConvertStreamToString(InputStream is)
    {
        return new Scanner(is).useDelimiter("\\A").next();
    }
    public byte[] convertToBytes(String json)
    {
        File htmlFile = new File(json);
        byte[] bytes = new byte[(int)htmlFile.length()];
        return bytes;
    }
    public void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
    public String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }
//    public Object decode(String json)
//    {
//
//    }
//    public String encode(Object o)
//    {
//        return null;
//    }
}
