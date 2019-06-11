package com.mars.baby.model;

import java.util.List;

public class OrderModel extends BaseModel<List<OrderModel.DataBean>> {
    public static class DataBean {
        /**
         * oid : 1
         * ouser : {"uphone":"13870176810","uid":17,"upass":"123","unickname":"键盘22","upic":"http://bmob-cdn-18278.b0.upaiyun.com/2019/02/21/188299e87c704896903d0d9f16bffe70.jpg","uhold":null,"gmtCreate":"2019-02-20 11:38:39.0","gmtModified":"2019-02-21 13:38:39.0","utype":0,"uapply":1}
         * otuser : {"uphone":"17328760951","uid":15,"upass":"123","unickname":"火星宝宝","upic":"http://bmob-cdn-18278.b0.upaiyun.com/2019/02/18/2cfc4b4e0d8e48f58fe88c8831ce90cd.jpg","uhold":null,"gmtCreate":"2019-02-18 11:02:24.0","gmtModified":"2019-02-18 11:02:24.0","utype":0,"uapply":0}
         * ocomment : null
         * otime : 2019年02月21日
         * oaddress : 深圳龙华
         * otype : 课外学习
         * ochildtype : 数学
         * ostatus : 0
         * gmtCreate : 2019-02-21 14:16:18.0
         * gmtModified : 2019-02-21 14:15:13.0
         * ouid : 17
         * otuid : 15
         */

        private int oid;
        private OuserBean ouser;
        private OtuserBean otuser;
        private String ocomment;
        private String otime;
        private String oaddress;
        private String otype;
        private String ochildtype;
        private int ostatus;
        private String gmtCreate;
        private String gmtModified;
        private int ouid;
        private int otuid;

        public int getOid() {
            return oid;
        }

        public void setOid(int oid) {
            this.oid = oid;
        }

        public OuserBean getOuser() {
            return ouser;
        }

        public void setOuser(OuserBean ouser) {
            this.ouser = ouser;
        }

        public OtuserBean getOtuser() {
            return otuser;
        }

        public void setOtuser(OtuserBean otuser) {
            this.otuser = otuser;
        }

        public String getOcomment() {
            return ocomment;
        }

        public void setOcomment(String ocomment) {
            this.ocomment = ocomment;
        }

        public String getOtime() {
            return otime;
        }

        public void setOtime(String otime) {
            this.otime = otime;
        }

        public String getOaddress() {
            return oaddress;
        }

        public void setOaddress(String oaddress) {
            this.oaddress = oaddress;
        }

        public String getOtype() {
            return otype;
        }

        public void setOtype(String otype) {
            this.otype = otype;
        }

        public String getOchildtype() {
            return ochildtype;
        }

        public void setOchildtype(String ochildtype) {
            this.ochildtype = ochildtype;
        }

        public int getOstatus() {
            return ostatus;
        }

        public void setOstatus(int ostatus) {
            this.ostatus = ostatus;
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

        public int getOuid() {
            return ouid;
        }

        public void setOuid(int ouid) {
            this.ouid = ouid;
        }

        public int getOtuid() {
            return otuid;
        }

        public void setOtuid(int otuid) {
            this.otuid = otuid;
        }

        public static class OuserBean {
            /**
             * uphone : 13870176810
             * uid : 17
             * upass : 123
             * unickname : 键盘22
             * upic : http://bmob-cdn-18278.b0.upaiyun.com/2019/02/21/188299e87c704896903d0d9f16bffe70.jpg
             * uhold : null
             * gmtCreate : 2019-02-20 11:38:39.0
             * gmtModified : 2019-02-21 13:38:39.0
             * utype : 0
             * uapply : 1
             */

            private String uphone;
            private int uid;
            private String upass;
            private String unickname;
            private String upic;
            private Object uhold;
            private String gmtCreate;
            private String gmtModified;
            private int utype;
            private int uapply;

            public String getUphone() {
                return uphone;
            }

            public void setUphone(String uphone) {
                this.uphone = uphone;
            }

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
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

            public Object getUhold() {
                return uhold;
            }

            public void setUhold(Object uhold) {
                this.uhold = uhold;
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

        public static class OtuserBean {
            /**
             * uphone : 17328760951
             * uid : 15
             * upass : 123
             * unickname : 火星宝宝
             * upic : http://bmob-cdn-18278.b0.upaiyun.com/2019/02/18/2cfc4b4e0d8e48f58fe88c8831ce90cd.jpg
             * uhold : null
             * gmtCreate : 2019-02-18 11:02:24.0
             * gmtModified : 2019-02-18 11:02:24.0
             * utype : 0
             * uapply : 0
             */

            private String uphone;
            private int uid;
            private String upass;
            private String unickname;
            private String upic;
            private Object uhold;
            private String gmtCreate;
            private String gmtModified;
            private int utype;
            private int uapply;

            public String getUphone() {
                return uphone;
            }

            public void setUphone(String uphone) {
                this.uphone = uphone;
            }

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
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

            public Object getUhold() {
                return uhold;
            }

            public void setUhold(Object uhold) {
                this.uhold = uhold;
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
}
