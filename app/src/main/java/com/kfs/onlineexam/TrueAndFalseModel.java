package com.kfs.onlineexam;

public class TrueAndFalseModel {

        String q;
        int selectedAnswerPosition , ans;
        boolean c1,c2;

    public int getAns() {
        return ans;
    }

    public void setAns(int ans) {
        this.ans = ans;
    }

    public void setSelectedAnswerPosition(int selectedAnswerPosition) {
            this.selectedAnswerPosition = selectedAnswerPosition;
        }

        public int getSelectedAnswerPosition() {
        return selectedAnswerPosition;
        }


        public boolean isC1() {

            return c1;
        }

        public void setC1(boolean c1) {
            this.c1 = c1;
            if(isC1())
            {
                setC2(false);
            }

        }

        public boolean isC2() {

            return c2;
        }

        public void setC2(boolean c2) {
            this.c2 = c2;
            if(isC2())
            {
                setC1(false);
            }

        }
        public String getQ() {
            return q;
        }


        public void setQ(String q) {
            this.q = q;
        }



}
