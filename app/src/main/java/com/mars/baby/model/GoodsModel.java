package com.mars.baby.model;

import java.util.List;

public class GoodsModel extends BaseModel <List<GoodsModel.DataBean>>{
    public static class DataBean {
        /**
         * gid : 1
         * gname : 美赞臣奶粉
         * gminiage : 0
         * gbigage : 1
         * gcomment : 早自 1905 年创立至今，美赞臣已有一百多年的历史。作为全球知名的婴幼儿营养品品牌，致力为全球婴幼儿提供科学营养，给他们带来一生最好的开始，是美赞臣矢志不渝的使命。目前，美赞臣生产的 70 多种营养产品行销 50 多个国家和地区。
         * gmtModified : null
         * gmtCreate : 2019-02-28 13:42:22.0
         */

        private int gid;
        private String gname;
        private int gminiage;
        private int gbigage;
        private String gcomment;
        private Object gmtModified;
        private String gmtCreate;

        public int getGid() {
            return gid;
        }

        public void setGid(int gid) {
            this.gid = gid;
        }

        public String getGname() {
            return gname;
        }

        public void setGname(String gname) {
            this.gname = gname;
        }

        public int getGminiage() {
            return gminiage;
        }

        public void setGminiage(int gminiage) {
            this.gminiage = gminiage;
        }

        public int getGbigage() {
            return gbigage;
        }

        public void setGbigage(int gbigage) {
            this.gbigage = gbigage;
        }

        public String getGcomment() {
            return gcomment;
        }

        public void setGcomment(String gcomment) {
            this.gcomment = gcomment;
        }

        public Object getGmtModified() {
            return gmtModified;
        }

        public void setGmtModified(Object gmtModified) {
            this.gmtModified = gmtModified;
        }

        public String getGmtCreate() {
            return gmtCreate;
        }

        public void setGmtCreate(String gmtCreate) {
            this.gmtCreate = gmtCreate;
        }
    }
}
