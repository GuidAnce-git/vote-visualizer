package iota.participationPlugin;

import java.io.Serializable;
import java.util.ArrayList;

public class PayloadDO implements Serializable {
    public int type;
    public ArrayList<QuestionDO> questions;
    public String text;
    public String symbol;
    public int numerator;
    public int denominator;
    public int requiredMinimumRewards;
    public String additionalInfo;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ArrayList<QuestionDO> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<QuestionDO> questions) {
        this.questions = questions;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getNumerator() {
        return numerator;
    }

    public void setNumerator(int numerator) {
        this.numerator = numerator;
    }

    public int getDenominator() {
        return denominator;
    }

    public void setDenominator(int denominator) {
        this.denominator = denominator;
    }

    public int getRequiredMinimumRewards() {
        return requiredMinimumRewards;
    }

    public void setRequiredMinimumRewards(int requiredMinimumRewards) {
        this.requiredMinimumRewards = requiredMinimumRewards;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
}
