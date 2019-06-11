package com.mars.baby.model;

import java.util.List;

public class RecordListModel extends BaseModel<List<RecordListModel.DataBean>> {
    public static class DataBean {
        /**
         * gmtCreate : 2019-02-19 17:24:08.0
         * gmtModified : 2019-02-19 17:24:08.0
         * rid : 1
         * rbid : 1
         * rcontent : 宝宝出生
         * rtime : null
         * rpic : http://bmob-cdn-18278.b0.upaiyun.com/2019/02/19/2e6497d0557e48318342cf9cfb861d6c.jpg
         * rtype : 0
         */

        private String gmtCreate;
        private String gmtModified;
        private int rid;
        private int rbid;
        private String rcontent;
        private Object rtime;
        private String rpic;
        private int rtype;

        public String getGmtCreate() {
            return gmtCreate;
        }

        public void setGmtCreate(String gmtCreate) {
            this.gmtCreate = gmtCreate;
        }

        public String getGmtModified() {
            return gmtModified;
        }

        public void setGmtModified(String gmtModified) {
            this.gmtModified = gmtModified;
        }

        public int getRid() {
            return rid;
        }

        public void setRid(int rid) {
            this.rid = rid;
        }

        public int getRbid() {
            return rbid;
        }

        public void setRbid(int rbid) {
            this.rbid = rbid;
        }

        public String getRcontent() {
            return rcontent;
        }

        public void setRcontent(String rcontent) {
            this.rcontent = rcontent;
        }

        public Object getRtime() {
            return rtime;
        }

        public void setRtime(Object rtime) {
            this.rtime = rtime;
        }

        public String getRpic() {
            return rpic;
        }

        public void setRpic(String rpic) {
            this.rpic = rpic;
        }

        public int getRtype() {
            return rtype;
        }

        public void setRtype(int rtype) {
            this.rtype = rtype;
        }
    }
}
