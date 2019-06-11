package com.mars.baby.model;

import java.util.List;

public class CommentModel extends BaseModel<List<CommentModel.DataBean>> {
    public static class DataBean {
        /**
         * gmtModified : 2019-02-20 14:05:12.0
         * gmtCreate : 2019-02-20 14:05:12.0
         * cid : 1
         * tuserDO : {"gmtModified":"2019-02-20 11:38:39.0","gmtCreate":"2019-02-20 11:38:39.0","uphone":"13870176810","upass":"123","unickname":"键盘","upic":"http://bmob-cdn-18278.b0.upaiyun.com/2019/02/20/2aa482f018894d3a9b7d8035039b3a76.jpg","uid":17,"uhold":null,"utype":0,"uapply":0}
         * cfid : 3
         * ccontent : 不错
         * cuid : 17
         */

        private String gmtModified;
        private String gmtCreate;
        private int cid;
        private TuserDOBean tuserDO;
        private int cfid;
        private String ccontent;
        private int cuid;

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

        public int getCid() {
            return cid;
        }

        public void setCid(int cid) {
            this.cid = cid;
        }

        public TuserDOBean getTuserDO() {
            return tuserDO;
        }

        public void setTuserDO(TuserDOBean tuserDO) {
            this.tuserDO = tuserDO;
        }

        public int getCfid() {
            return cfid;
        }

        public void setCfid(int cfid) {
            this.cfid = cfid;
        }

        public String getCcontent() {
            return ccontent;
        }

        public void setCcontent(String ccontent) {
            this.ccontent = ccontent;
        }

        public int getCuid() {
            return cuid;
        }

        public void setCuid(int cuid) {
            this.cuid = cuid;
        }

        public static class TuserDOBean {
            /**
             * gmtModified : 2019-02-20 11:38:39.0
             * gmtCreate : 2019-02-20 11:38:39.0
             * uphone : 13870176810
             * upass : 123
             * unickname : 键盘
             * upic : http://bmob-cdn-18278.b0.upaiyun.com/2019/02/20/2aa482f018894d3a9b7d8035039b3a76.jpg
             * uid : 17
             * uhold : null
             * utype : 0
             * uapply : 0
             */

            private String gmtModified;
            private String gmtCreate;
            private String uphone;
            private String upass;
            private String unickname;
            private String upic;
            private int uid;
            private Object uhold;
            private int utype;
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
