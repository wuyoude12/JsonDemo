package com.test.jsondemo;

import java.util.List;

/**
 * Created by wuyoude on 2015/12/9.
 */
public class ProvinceID {


    private int Code;
    private String Msg;
    private ProvinceID.Data Data;

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

    public ProvinceID.Data getData() {
        return Data;
    }

    public void setData(ProvinceID.Data Data) {
        this.Data = Data;
    }

    public class Data {

        private List<ProvinceID.Data.Province> Province;

        public  List<ProvinceID.Data.Province> getProvince() {
            return Province;
        }

        public void setProvince(List<ProvinceID.Data.Province> Province) {
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
