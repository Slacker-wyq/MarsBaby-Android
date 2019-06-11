package com.mars.baby.model;

import java.util.List;

public class AttentModel extends BaseModel<List<AttentModel.DataBean>> {
    public static class DataBean {
        /**
         * atuid : 15
         * afuid : 17
         * tuserDO : {"utype":0,"uapply":0,"uid":15,"uphone":"17328760951","upass":"123","unickname":"火星宝宝","upic":"http://bmob-cdn-18278.b0.upaiyun.com/2019/02/18/2cfc4b4e0d8e48f58fe88c8831ce90cd.jpg","uhold":null,"gmtModified":"2019-02-18 11:02:24.0","gmtCreate":"2019-02-18 11:02:24.0"}
         * gmtModified : 2019-02-20 11:38:51.0
         * gmtCreate : 2019-02-20 11:38:51.0
         * aid : 1
         */

        private int atuid;
        private int afuid;
        private TuserDOBean tuserDO;
        private String gmtModified;
        private String gmtCreate;
        private int aid;

        public int getAtuid() {
            return atuid;
        }

        public void setAtuid(int atuid) {
            this.atuid = atuid;
        }

        public int getAfuid() {
            return afuid;
        }

        public void setAfuid(int afuid) {
            this.afuid = afuid;
        }

        public TuserDOBean getTuserDO() {
            return tuserDO;
        }

        public void setTuserDO(TuserDOBean tuserDO) {
            this.tuserDO = tuserDO;
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

        public int getAid() {
            return aid;
        }

        public void setAid(int aid) {
            this.aid = aid;
        }

        public static class TuserDOBean {
            /**
             * utype : 0
             * uapply : 0
             * uid : 15
             * uphone : 17328760951
             * upass : 123
             * unickname : 火星宝宝
             * upic : http://bmob-cdn-18278.b0.upaiyun.com/2019/02/18/2cfc4b4e0d8e48f58fe88c8831ce90cd.jpg
             * uhold : null
             * gmtModified : 2019-02-18 11:02:24.0
             * gmtCreate : 2019-02-18 11:02:24.0
             */

            private int utype;
            private int uapply;
            private int uid;
            private String uphone;
            private String upass;
            private String unickname;
            private String upic;
            private Object uhold;
            private String gmtModified;
            private String gmtCreate;

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

            public Object getUhold() {
                return uhold;
            }

            public void setUhold(Object uhold) {
                this.uhold = uhold;
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
        }
    }
}
