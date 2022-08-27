package iota.participationPlugin.entity;


import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;


@Entity
public class SingleEventPayloadEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private Long type;

    @Column(length = 1024)
    private String text;

    @Column(length = 1024)
    private String symbol;
    private Long numerator;
    private Long denominator;
    private Long requiredMinimumRewards;

    @Column(length = 5120)
    private String additionalInfo;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn
    private Set<SingleEventQuestionsEntity> questions;

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<SingleEventQuestionsEntity> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<SingleEventQuestionsEntity> questions) {
        this.questions = questions;
    }
}
