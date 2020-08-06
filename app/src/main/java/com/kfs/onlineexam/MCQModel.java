package com.kfs.onlineexam;

public class MCQModel {
    String q,a1,a2,a3,a4;
    int ans;
    int selectedAnswerPosition;
    boolean c1,c2,c3,c4;

    public int getSelectedAnswerPosition() {
        return selectedAnswerPosition;
    }

    public void setSelectedAnswerPosition(int selectedAnswerPosition) {
        this.selectedAnswerPosition = selectedAnswerPosition;
    }

    public int getAns() {
        return ans;
    }

    public void setAns(int ans) {
        this.ans = ans;
    }

    public MCQModel() {
    }

    public MCQModel(String q, String a1, String a2, String a3, String a4,int ans) {
        this.q = q;
        this.a1 = a1;
        this.a2 = a2;
        this.a3 = a3;
        this.a4 = a4;
        this.ans = ans;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public String getA1() {
        return a1;
    }

    public void setA1(String a1) {
        this.a1 = a1;
    }

    public String getA2() {
        return a2;
    }

    public void setA2(String a2) {
        this.a2 = a2;
    }

    public String getA3() {
        return a3;
    }

    public void setA3(String a3) {
        this.a3 = a3;
    }

    public String getA4() {
        return a4;
    }

    public void setA4(String a4) {
        this.a4 = a4;
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
}
