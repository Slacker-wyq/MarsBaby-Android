package com.mars.baby.model;

import java.util.List;

public class FriendModel extends BaseModel<List<FriendModel.DataBean>> {
    public static class DataBean {
        /**
         * gmtModified : null
         * gmtCreate : 2019-02-27 08:57:41.0
         * fid : 1
         * ffuid : 17
         * ftuid : 15
         * tuserDO : {"gmtModified":"2019-02-18 11:02:24.0","gmtCreate":"2019-02-18 11:02:24.0","uid":15,"upass":"123","upic":"http://bmob-cdn-18278.b0.upaiyun.com/2019/02/18/2cfc4b4e0d8e48f58fe88c8831ce90cd.jpg","uhold":null,"utype":1,"unickname":"火星宝宝","uphone":"17328760951","uapply":2}
         */

        private Object gmtModified;
        private String gmtCreate;
        private int fid;
        private int ffuid;
        private int ftuid;
        private TuserDOBean tuserDO;

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

        public int getFid() {
            return fid;
        }

        public void setFid(int fid) {
            this.fid = fid;
        }

        public int getFfuid() {
            return ffuid;
        }

        public void setFfuid(int ffuid) {
            this.ffuid = ffuid;
        }

        public int getFtuid() {
            return ftuid;
        }

        public void setFtuid(int ftuid) {
            this.ftuid = ftuid;
        }

        public TuserDOBean getTuserDO() {
            return tuserDO;
        }

        public void setTuserDO(TuserDOBean tuserDO) {
            this.tuserDO = tuserDO;
        }

        public static class TuserDOBean {
            /**
             * gmtModified : 2019-02-18 11:02:24.0
             * gmtCreate : 2019-02-18 11:02:24.0
             * uid : 15
             * upass : 123
             * upic : http://bmob-cdn-18278.b0.upaiyun.com/2019/02/18/2cfc4b4e0d8e48f58fe88c8831ce90cd.jpg
             * uhold : null
             * utype : 1
             * unickname : 火星宝宝
             * uphone : 17328760951
             * uapply : 2
             */

            private String gmtModified;
            private String gmtCreate;
            private int uid;
            private String upass;
            private String upic;
            private Object uhold;
            private int utype;
            private String unickname;
            private String uphone;
            private int uapply;

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

            public int getUtype() {
                return utype;
            }

            public void setUtype(int utype) {
                this.utype = utype;
            }

            public String getUnickname() {
                return unickname;
            }

            public void setUnickname(String unickname) {
                this.unickname = unickname;
            }

            public String getUphone() {
                return uphone;
            }

            public void setUphone(String uphone) {
                this.uphone = uphone;
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
