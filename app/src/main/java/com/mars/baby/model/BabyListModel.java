package com.mars.baby.model;

import java.util.List;

public class BabyListModel extends BaseModel<List<BabyListModel.DataBean>> {
    public static class DataBean {
        /**
         * bage : 2019-01-01 00:00:00.0
         * bpic : http://bmob-cdn-18278.b0.upaiyun.com/2019/02/18/85443b6bb8314ad79cc01a2f74a0187f.jpg
         * bid : 1
         * bheight : 100
         * bweight : 10
         * bname : 张全蛋
         * buid : 15
         * bnick : 狗子
         * bsex : 男
         * gmtCreate : 2019-02-18 16:06:44.0
         * gmtModified : 2019-02-18 16:06:44.0
         */

        private String bage;
        private String bpic;
        private int bid;
        private int bheight;
        private int bweight;
        private String bname;
        private int buid;
        private String bnick;
        private String bsex;
        private String gmtCreate;
        private String gmtModified;

        public String getBage() {
            return bage;
        }

        public void setBage(String bage) {
            this.bage = bage;
        }

        public String getBpic() {
            return bpic;
        }

        public void setBpic(String bpic) {
            this.bpic = bpic;
        }

        public int getBid() {
            return bid;
        }

        public void setBid(int bid) {
            this.bid = bid;
        }

        public int getBheight() {
            return bheight;
        }

        public void setBheight(int bheight) {
            this.bheight = bheight;
        }

        public int getBweight() {
            return bweight;
        }

        public void setBweight(int bweight) {
            this.bweight = bweight;
        }

        public String getBname() {
            return bname;
        }

        public void setBname(String bname) {
            this.bname = bname;
        }

        public int getBuid() {
            return buid;
        }

        public void setBuid(int buid) {
            this.buid = buid;
        }

        public String getBnick() {
            return bnick;
        }

        public void setBnick(String bnick) {
            this.bnick = bnick;
        }

        public String getBsex() {
            return bsex;
        }

        public void setBsex(String bsex) {
            this.bsex = bsex;
        }

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
    }
}
