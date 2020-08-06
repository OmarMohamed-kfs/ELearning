package com.kfs.onlineexam;

public class StudentMCQModel {

    String q,ans1,an2,ans3,ans4,realAns;
    int selectedAnswerPosition;
    boolean c1,c2,c3,c4;

    public String getAns1() {
        return ans1;
    }

    public void setAns1(String ans1) {
        this.ans1 = ans1;
    }

    public String getAn2() {
        return an2;
    }

    public void setAn2(String an2) {
        this.an2 = an2;
    }

    public String getAns3() {
        return ans3;
    }

    public void setAns3(String ans3) {
        this.ans3 = ans3;
    }

    public String getAns4() {
        return ans4;
    }

    public void setAns4(String ans4) {
        this.ans4 = ans4;
    }

    public String getRealAns() {
        return realAns;
    }

    public void setRealAns(String realAns) {
        this.realAns = realAns;
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

    public boolean isC3() {

        return c3;
    }

    public void setC3(boolean c3) {
        this.c3 = c3;
        if(isC3())
        {
            setC1(false);
            setC2(false);
            setC4(false);
        }

    }

    public boolean isC4() {

        return c4;
    }

    public void setC4(boolean c4) {
        this.c4 = c4;
        if(isC4())
        {
            setC1(false);
            setC2(false);
            setC3(false);
        }

    }
    public String getQ() {
        return q;
    }


    public void setQ(String q) {
        this.q = q;
    }


}
