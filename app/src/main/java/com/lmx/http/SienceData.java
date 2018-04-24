package com.lmx.http;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018\4\8 0008.
 */

public class SienceData {
    int code;
    String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private int totalpage;
    private List<ListBean> list;

    public int getTotalpage() {
        return totalpage;
    }

    public void setTotalpage(int totalpage) {
        this.totalpage = totalpage;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable {

        private String scenicId;
        private String cityName;
        private String name;

        public String getScenicId() {
            return scenicId;
        }

        public void setScenicId(String scenicId) {
            this.scenicId = scenicId;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @Override
    public String toString() {
        return "SienceData{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", totalpage=" + totalpage +
                ", list=" + list +
                '}';
    }
}
