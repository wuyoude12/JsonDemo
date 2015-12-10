package com.test.jsonbean;

import java.util.List;

/**
 * Created by wuyoude on 2015/12/10.
 */
public class CityBean {
    private int Code;
    private String Msg;
    private Data Data;

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String Msg) {
        this.Msg = Msg;
    }

    public CityBean.Data getData() {
        return Data;
    }

    public void setData(CityBean.Data Data) {
        this.Data = Data;
    }

    public class Data{

        private List<City> City;
        public List<CityBean.Data.City> getCity() {
            return City;
        }

        public void setCity(List<CityBean.Data.City> City) {
            this.City = City;
        }

        public class City{

            private String CityId;
            private String CityName;

            public String getCityId() {
                return CityId;
            }

            public void setCityId(String CityId) {
                this.CityId = CityId;
            }

            public String getCityName() {
                return CityName;
            }

            public void setCityName(String CityName) {
                this.CityName = CityName;
            }
        }

    }

}
