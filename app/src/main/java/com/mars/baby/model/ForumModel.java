package com.mars.baby.model;

import java.util.List;

public class ForumModel extends BaseModel<List<ForumModel.DataBean>> {

    public static class DataBean {
        /**
         * gmtModified : 2019-02-20 10:28:23.0
         * gmtCreate : 2019-02-20 10:28:23.0
         * attent : false
         * fid : 3
         * fuser : {"utype":0,"uapply":0,"uid":15,"uphone":"17328760951","upass":"123","unickname":"火星宝宝","upic":"http://bmob-cdn-18278.b0.upaiyun.com/2019/02/18/2cfc4b4e0d8e48f58fe88c8831ce90cd.jpg","gmtModified":"2019-02-18 11:02:24.0","gmtCreate":"2019-02-18 11:02:24.0","uhold":null}
         * fcontent : 今天很开心
         * fpic : http://bmob-cdn-18278.b0.upaiyun.com/2019/02/20/95948f8ee67f4b19a742eea6e77d4c0c.jpg
         * freUser : {"utype":0,"uapply":0,"uid":15,"uphone":"17328760951","upass":"123","unickname":"火星宝宝","upic":"http://bmob-cdn-18278.b0.upaiyun.com/2019/02/18/2cfc4b4e0d8e48f58fe88c8831ce90cd.jpg","gmtModified":"2019-02-18 11:02:24.0","gmtCreate":"2019-02-18 11:02:24.0","uhold":null}
         * freUid : 15
         * fuid : 15
         * frelay : 0
         * fvisit : 0
         */

        private String gmtModified;
        private String gmtCreate;
        private boolean attent;
        private int fid;
        private FuserBean fuser;
        private String fcontent;
        private String fpic;
        private FreUserBean freUser;
        private int freUid;
        private int fuid;
        private int frelay;
        private int fvisit;

        public String getGmtModified() {
            return gmtModified;
        }

        public void setGmtModified(String gmtModified) {
            this.gmtModified = gmtModified;
        }

        public String getGmtCreate() {
            return gmtCreate;
        }

        public void setGmtCreate(String gmtCreate) {
            this.gmtCreate = gmtCreate;
        }

        public boolean isAttent() {
            return attent;
        }

        public void setAttent(boolean attent) {
            this.attent = attent;
        }

        public int getFid() {
            return fid;
        }

        public void setFid(int fid) {
            this.fid = fid;
        }

        public FuserBean getFuser() {
            return fuser;
        }

        public void setFuser(FuserBean fuser) {
            this.fuser = fuser;
        }

        public String getFcontent() {
            return fcontent;
        }

        public void setFcontent(String fcontent) {
            this.fcontent = fcontent;
        }

        public String getFpic() {
            return fpic;
        }

        public void setFpic(String fpic) {
            this.fpic = fpic;
        }

        public FreUserBean getFreUser() {
            return freUser;
        }

        public void setFreUser(FreUserBean freUser) {
            this.freUser = freUser;
        }

        public int getFreUid() {
            return freUid;
        }

        public void setFreUid(int freUid) {
            this.freUid = freUid;
        }

        public int getFuid() {
            return fuid;
        }

        public void setFuid(int fuid) {
            this.fuid = fuid;
        }

        public int getFrelay() {
            return frelay;
        }

        public void setFrelay(int frelay) {
            this.frelay = frelay;
        }

        public int getFvisit() {
            return fvisit;
        }

        public void setFvisit(int fvisit) {
            this.fvisit = fvisit;
        }

        public static class FuserBean {
            /**
             * utype : 0
             * uapply : 0
             * uid : 15
             * uphone : 17328760951
             * upass : 123
             * unickname : 火星宝宝
             * upic : http://bmob-cdn-18278.b0.upaiyun.com/2019/02/18/2cfc4b4e0d8e48f58fe88c8831ce90cd.jpg
             * gmtModified : 2019-02-18 11:02:24.0
             * gmtCreate : 2019-02-18 11:02:24.0
             * uhold : null
             */

            private int utype;
            private int uapply;
            private int uid;
            private String uphone;
            private String upass;
            private String unickname;
            private String upic;
            private String gmtModified;
            private String gmtCreate;
            private Object uhold;

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

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public String getUphone() {
                return uphone;
            }

            public void setUphone(String uphone) {
                this.uphone = uphone;
            }

            public String getUpass() {
                return upass;
            }

            public void setUpass(String upass) {
                this.upass = upass;
            }

            public String getUnickname() {
                return unickname;
            }

            public void setUnickname(String unickname) {
                this.unickname = unickname;
            }

            public String getUpic() {
                return upic;
            }

            public void setUpic(String upic) {
                this.upic = upic;
            }

            public String getGmtModified() {
                return gmtModified;
            }

            public void setGmtModified(String gmtModified) {
                this.gmtModified = gmtModified;
            }

            public String getGmtCreate() {
                return gmtCreate;
            }

            public void setGmtCreate(String gmtCreate) {
                this.gmtCreate = gmtCreate;
            }

            public Object getUhold() {
                return uhold;
            }

            public void setUhold(Object uhold) {
                this.uhold = uhold;
            }
        }

        public static class FreUserBean {
            /**
             * utype : 0
             * uapply : 0
             * uid : 15
             * uphone : 17328760951
             * upass : 123
             * unickname : 火星宝宝
             * upic : http://bmob-cdn-18278.b0.upaiyun.com/2019/02/18/2cfc4b4e0d8e48f58fe88c8831ce90cd.jpg
             * gmtModified : 2019-02-18 11:02:24.0
             * gmtCreate : 2019-02-18 11:02:24.0
             * uhold : null
             */

            private int utype;
            private int uapply;
            private int uid;
            private String uphone;
            private String upass;
            private String unickname;
            private String upic;
            private String gmtModified;
            private String gmtCreate;
            private Object uhold;

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

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public String getUphone() {
                return uphone;
            }

            public void setUphone(String uphone) {
                this.uphone = uphone;
            }

            public String getUpass() {
                return upass;
            }

            public void setUpass(String upass) {
                this.upass = upass;
            }

            public String getUnickname() {
                return unickname;
            }

            public void setUnickname(String unickname) {
                this.unickname = unickname;
            }

            public String getUpic() {
                return upic;
            }

            public void setUpic(String upic) {
                this.upic = upic;
            }

            public String getGmtModified() {
                return gmtModified;
            }

            public void setGmtModified(String gmtModified) {
                this.gmtModified = gmtModified;
            }

            public String getGmtCreate() {
                return gmtCreate;
            }

            public void setGmtCreate(String gmtCreate) {
                this.gmtCreate = gmtCreate;
            }

            public Object getUhold() {
                return uhold;
            }

            public void setUhold(Object uhold) {
                this.uhold = uhold;
            }
        }
    }
}
