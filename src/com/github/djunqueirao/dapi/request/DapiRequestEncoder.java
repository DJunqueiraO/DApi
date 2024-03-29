package com.github.djunqueirao.dapi.request;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public abstract class DapiRequestEncoder {
   public static String encodeToUtf8(String format, Object... args) {
      Object[] encodedArgs = args;
      for(int i = 0; i < encodedArgs.length; ++i) {
         try {
            encodedArgs[i] = URLEncoder.encode("" + encodedArgs[i], "UTF-8");
         } catch (UnsupportedEncodingException var5) {
            var5.printStackTrace();
         }
      }
      return String.format(format, encodedArgs);
   }
}
