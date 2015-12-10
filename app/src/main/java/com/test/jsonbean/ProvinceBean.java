package com.test.jsonbean;

import java.util.List;

/**
 * Created by wuyoude on 2015/12/9.
 */
public class ProvinceBean {


    private int Code;
    private String Msg;
    private ProvinceBean.Data Data;

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

    public ProvinceBean.Data getData() {
        return Data;
    }

    public void setData(ProvinceBean.Data Data) {
        this.Data = Data;
    }

    public class Data {

        private List<ProvinceBean.Data.Province> Province;

        public  List<ProvinceBean.Data.Province> getProvince() {
            return Province;
        }

        public void setProvince(List<ProvinceBean.Data.Province> Province) {
            this.Province = Province;
        }


        public class Province {

            private String ProvinceId;
            private String ProvinceName;

            public String getProvinceId() {
                return ProvinceId;
            }
            public void setProvinceId(String ProvinceId) {
                this.ProvinceId = ProvinceId;
            }
            public String getProvinceName() {
                return ProvinceName;
            }
            public void setProvinceName(String ProvinceName) {
                this.ProvinceName = ProvinceName;
            }

        }

    }
}
