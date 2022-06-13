package com.tyc.utils;

import com.tyc.utils.encrypt.MD5BCrypt;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

public class ParamUtils {

    public static final String SCHAR_AND = "&";
    public static final String SCHAR_OR = "|";
    public static final String SCHAR_EMPTY = "";
    public static final Boolean VALUE_ONLY = true;
    public static final Boolean JOIN_NULL = true;


    public static String getAuthString(Map<String, String> requestMap, String appSecert) {
        if (requestMap == null) return "";
        // 按参数首字母排序
        SortedMap<String, String> sortMap = sortMapAsc(requestMap);
        Set<String> keySet = sortMap.keySet();
        Iterator<String> iter = keySet.iterator();
        StringBuffer linkStringBuff = new StringBuffer();
        while (iter.hasNext()) {
            String key = iter.next();
            String value = sortMap.get(key).replaceAll("\r", "").replaceAll("\n", "");
            linkStringBuff.append(value);
        }
        String authString = MD5BCrypt.md5(linkStringBuff.toString() + appSecert);
        return authString;
    }

    /**
     * 去除sign参数并将参数转化为字符串,用于加密回调中的sign验证
     *
     * @param requestMap  回调参数map
     * @param separator   参数间的分隔符
     * @param valueOnly   是否需要key true: "key1=value1&key2=value2..."; false: "value1&value2&value3..."
     * @param signKeyName 排除要加密的参数，多为sign本身
     * @param ifNull      若value为null时,此value拼接字符串
     * @param appendNull  是否拼接value为空的key=
     * @return
     */
    public static StringBuffer getParamsString(Map<String, String> requestMap, String separator, boolean valueOnly, String signKeyName, boolean appendNull, String ifNull) {
        if (requestMap == null) return new StringBuffer("");
        if (signKeyName == null) signKeyName = "";
        // 按参数首字母排序
        SortedMap<String, String> sortMap = sortMapAsc(requestMap);
        Set<String> keySet = sortMap.keySet();
        Iterator<String> iter = keySet.iterator();
        StringBuffer linkStringBuff = new StringBuffer();
        if (valueOnly) {
            while (iter.hasNext()) {
                String key = iter.next();
                if (signKeyName.equals(key))// 如果是sign字段，字不作为签名校验参数
                    continue;
                String value = sortMap.get(key);
                if (StringUtils.isEmpty(value) && !appendNull)
                    continue;
                value = (value == null) ? ifNull : value.replaceAll("\r", "").replaceAll("\n", "");
                linkStringBuff.append(value).append(separator);
            }
        } else {
            while (iter.hasNext()) {
                String key = iter.next();
                if (signKeyName.equals(key))// 如果是sign字段，字不作为签名校验参数
                    continue;
                String value = sortMap.get(key);
                if (StringUtils.isEmpty(value) && !appendNull)
                    continue;
                value = (value == null) ? ifNull : value.replaceAll("\r", "").replaceAll("\n", "");
                linkStringBuff.append(key).append("=").append(value).append(separator);
            }
        }
        if (!StringUtils.isEmpty(separator))
            linkStringBuff.deleteCharAt(linkStringBuff.length() - 1);//除去最后个分隔符 若有需要则在外面相加
        return linkStringBuff;
    }

    /**
     * 去除sign参数并将参数转化为字符串,用于加密回调中的sign验证
     *
     * @param requestMap 回调参数map
     * @param separator  参数间的分隔符
     * @param valueOnly  是否需要key true: "key1=value1&key2=value2..."; false: "value1&value2&value3..."
     * @param outKeys    排除要加密的参数，多为sign本身
     * @param ifNull     若value为null时,此value拼接字符串
     * @param appendNull 是否拼接value为空的key=
     * @return
     */
    public static StringBuffer joinWithMultiOutKey(Map<String, String> requestMap, String separator, boolean valueOnly, String[] outKeys, boolean appendNull, String ifNull) {
        if (requestMap == null) {
            return new StringBuffer("");
        }
        List<String> outKeyList = new ArrayList();
        if (outKeys != null && outKeys.length > 0) {
            outKeyList = Arrays.asList(outKeys);
        }
        // 按参数首字母排序
        SortedMap<String, String> sortMap = sortMapAsc(requestMap);
        Set<String> keySet = sortMap.keySet();
        Iterator<String> iter = keySet.iterator();
        StringBuffer linkStringBuff = new StringBuffer();
        if (valueOnly) {
            while (iter.hasNext()) {
                String key = iter.next();
                if (outKeyList.contains(key.trim()))// 如果是sign字段，字不作为签名校验参数
                    continue;
                String value = sortMap.get(key);
                if (StringUtils.isEmpty(value) && !appendNull)
                    continue;
                value = (value == null) ? ifNull : value.replaceAll("\r", "").replaceAll("\n", "");
                linkStringBuff.append(value).append(separator);
            }
        } else {
            while (iter.hasNext()) {
                String key = iter.next();
                if (outKeyList.contains(key.trim()))// 如果是sign字段，字不作为签名校验参数
                    continue;
                String value = sortMap.get(key);
                if (StringUtils.isEmpty(value) && !appendNull)
                    continue;
                value = (value == null) ? ifNull : value.replaceAll("\r", "").replaceAll("\n", "");
                linkStringBuff.append(key).append("=").append(value).append(separator);
            }
        }
        if (!StringUtils.isEmpty(separator))
            linkStringBuff.deleteCharAt(linkStringBuff.length() - 1);//除去最后个分隔符 若有需要则在外面相加
        return linkStringBuff;
    }

    public static SortedMap<String, String> sortMapAsc(Map<String, String> requestMap) {
        if (requestMap == null) return null;
        // 按参数首字母排序
        SortedMap<String, String> sortMap = new TreeMap<String, String>(new Comparator<String>() {
            public int compare(String param1, String param2) {
                return param1.compareTo(param2);
            }
        });
        sortMap.putAll(requestMap);
        return sortMap;
    }

    public static String createLinkString(Map<String, String> requestMap) {
        if (requestMap == null || requestMap.size() == 0) return "";
        StringBuffer linkStringBuff = new StringBuffer();
        Set<String> paramSet = requestMap.keySet();
        Iterator<String> iter = paramSet.iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            String value = "";
            if (requestMap.get(key) != null)
                value = requestMap.get(key).replaceAll("\r", "").replaceAll("\n", "");
            linkStringBuff.append(key).append("=").append(value).append("&");
        }
        linkStringBuff.delete(linkStringBuff.length() - 1, linkStringBuff.length());
        return linkStringBuff.toString();
    }

    public static String createToken() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String getStreamData(InputStream in, String coding) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, coding));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        while ((line = br.readLine()) != null) {
            buffer.append(line);
        }
        String streamData = URLDecoder.decode(buffer.toString(), coding);
        return streamData;
    }

    public static Map<String, String> getRequestMap(HttpServletRequest request) throws Exception {
        Map<String, String> param = new HashMap<String, String>();
        String coding = request.getCharacterEncoding();
        Map<String, String[]> map = (Map<String, String[]>) request.getParameterMap();

        if (map == null) {
            return null;
        }
        ;
        Set<String> keySet = map.keySet();
        Iterator<String> iter = keySet.iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            String value = (map.get(key))[0];
            value = new String(value.getBytes(coding), "UTF-8");
            param.put(key, value);
        }

        return param;
    }

    /**
     * 将参数字符串解析为map，注意参数中尽量不要带等于号
     *
     * @param str
     * @return
     */
    public static Map<String, String> parseQString(String str) {
        Map<String, String> map = new LinkedHashMap<>();

        String[] paramPairs = str.split("&");

        for (String pairs : paramPairs) {
            String[] kv = pairs.split("=");

            map.put(kv[0], kv[1]);
        }
        return map;
    }

    public static String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String urlDecode(String s) {
        try {
            return URLDecoder.decode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return "";
    }

}
