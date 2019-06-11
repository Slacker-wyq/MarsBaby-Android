package com.mars.baby.model;

public class UserModel  extends BaseModel<UserModel.DataBean>{
    public static class DataBean {
        /**
         * upic : http://bmob-cdn-18278.b0.upaiyun.com/2019/02/18/2cfc4b4e0d8e48f58fe88c8831ce90cd.jpg
         * uid : 15
         * uhold : null
         * uphone : 17328760951
         * unickname : 火星宝宝
         * upass : 123
         * gmtCreate : 2019-02-18 11:02:24.0
         * gmtModified : 2019-02-18 11:02:24.0
         * utype : 0
         * uapply : 0
         */

        private String upic;
        private int uid;
        private Object uhold;
        private String uphone;
        private String unickname;
        private String upass;
        private String gmtCreate;
        private String gmtModified;
        private int utype;
        private int uapply;

        public String getUpic() {
            return upic;
        }

        public void setUpic(String upic) {
            this.upic = upic;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public Object getUhold() {
            return uhold;
        }

        public void setUhold(Object uhold) {
            this.uhold = uhold;
        }

        public String getUphone() {
            return uphone;
        }

        public void setUphone(String uphone) {
            this.uphone = uphone;
        }

        public String getUnickname() {
            return unickname;
        }

        public void setUnickname(String unickname) {
            this.unickname = unickname;
        }

        public String getUpass() {
            return upass;
        }

        public void setUpass(String upass) {
            this.upass = upass;
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

        public int getUtype() {
            return utype;
        }

        public void setUtype(int utype) {
            this.utype = utype;
        }

        public int getUapply() {
            return uapply;
        }

        public void setUapply(int uapply) {
            this.uapply = uapply;
        }
    }
}
