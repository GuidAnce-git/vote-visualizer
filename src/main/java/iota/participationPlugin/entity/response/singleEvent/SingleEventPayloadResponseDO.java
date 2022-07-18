package iota.participationPlugin.entity.response.singleEvent;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Set;

public class SingleEventPayloadResponseDO implements Serializable {

    private Long type;
    private String text;
    private String symbol;
    private Long numerator;
    private Long denominator;
    private Long requiredMinimumRewards;

    @Column(length = 5120)
    private String additionalInfo;
    private Set<SingleEventQuestionResponseDO> questions;

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Set<SingleEventQuestionResponseDO> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<SingleEventQuestionResponseDO> questions) {
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

    public Long getNumerator() {
        return numerator;
    }

    public void setNumerator(Long numerator) {
        this.numerator = numerator;
    }

    public Long getDenominator() {
        return denominator;
    }

    public void setDenominator(Long denominator) {
        this.denominator = denominator;
    }

    public Long getRequiredMinimumRewards() {
        return requiredMinimumRewards;
    }

    public void setRequiredMinimumRewards(Long requiredMinimumRewards) {
        this.requiredMinimumRewards = requiredMinimumRewards;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
}
