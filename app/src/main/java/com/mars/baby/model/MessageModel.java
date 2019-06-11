package com.mars.baby.model;

import java.util.List;

public class MessageModel extends BaseModel<List<MessageModel.DataBean>> {
    public static final int TYPE_RECEIVED=0;
    public static final int TYPE_SENT=1;
    public static class DataBean {
        /**
         * type : 0
         * mfid : 15
         * mtid : 17
         * mcontent : 123(15给17)
         * gmtModified : null
         * gmtCreate : 2019-02-20 15:52:05.0
         * mid : 1
         */

        private int type;
        private int mfid;
        private int mtid;
        private String mcontent;
        private Object gmtModified;
        private String gmtCreate;
        private int mid;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getMfid() {
            return mfid;
        }

        public void setMfid(int mfid) {
            this.mfid = mfid;
        }

        public int getMtid() {
            return mtid;
        }

        public void setMtid(int mtid) {
            this.mtid = mtid;
        }

        public String getMcontent() {
            return mcontent;
        }

        public void setMcontent(String mcontent) {
            this.mcontent = mcontent;
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

        public int getMid() {
            return mid;
        }

        public void setMid(int mid) {
            this.mid = mid;
        }
    }
}
