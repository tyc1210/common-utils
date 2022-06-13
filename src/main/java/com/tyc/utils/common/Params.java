package com.tyc.utils.common;

import com.alibaba.fastjson.JSON;
import com.tyc.utils.ParamUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.util.StringUtils;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/12 0012.
 */
public class Params {

    private Map<String, String> pMap = null;
    private String postParamJson;

    public Params() {
    }

    public Params(HttpServletRequest request) {
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // 使用linkedHashMap是保证map是有序的，为后面组装非排序参数使用
        pMap = new LinkedHashMap<>();
        Map<String, String[]> reqMap = request.getParameterMap();

        reqMap.forEach((k, v) -> {
            String[] varr = v;
            if (varr != null && varr.length > 0) {
                this.pMap.put(k, v[0]);
            } else {
                this.pMap.put(k, "");
            }
        });
    }

    public Params(HttpServletRequest request, boolean postBody) {
        try {
            InputStream is = request.getInputStream();
            this.postParamJson = IOUtils.toString(is, Charset.forName("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void parse() {
        this.pMap = ParamUtils.parseQString(postParamJson);
    }

    public String getString(String k) {
        if (pMap == null || StringUtils.isEmpty(pMap.get(k))) {
            return "";
        }

        return this.pMap.get(k);
    }

    public BigDecimal getDecimal(String k) {
        if (pMap == null || StringUtils.isEmpty(pMap.get(k))) {
            return new BigDecimal(0);
        }

        return new BigDecimal(this.pMap.get(k));
    }

    public Double getDouble(String k) {
        if (pMap == null || StringUtils.isEmpty(pMap.get(k))) {
            return 0.00;
        }

        return Double.valueOf(this.pMap.get(k));
    }

    public Integer getInteger(String k) {
        if (pMap == null || StringUtils.isEmpty(pMap.get(k))) {
            return 0;
        }

        return Integer.valueOf(this.pMap.get(k));
    }

    public Long getLong(String k) {
        if (pMap == null || StringUtils.isEmpty(pMap.get(k))) {
            return 0L;
        }

        return Long.valueOf(this.pMap.get(k));
    }

    public Map<String, String> getReqMap() {
        return pMap;
    }

    public <T> T getPostClass(Class<T> clazz) {
        if (!StringUtils.isEmpty(postParamJson)) {
            return (T) JSON.parseObject(this.postParamJson, clazz);
        }

        return null;
    }

    public String getPostParamJson() {
        return postParamJson;
    }

    public void setPostParamJson(String postParamJson) {
        this.postParamJson = postParamJson;
    }
}
