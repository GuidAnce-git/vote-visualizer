package iota.participationPlugin.entity.mapper;

import iota.participationPlugin.entity.SingleEventAnswersEntity;
import iota.participationPlugin.entity.SingleEventQuestionsEntity;
import iota.participationPlugin.entity.response.singleEvent.SingleEventAnswerResponseDO;
import iota.participationPlugin.entity.response.singleEvent.SingleEventQuestionResponseDO;

import javax.enterprise.context.ApplicationScoped;
import java.util.HashSet;

@ApplicationScoped
public class QuestionMapper {

    public SingleEventQuestionsEntity mapQuestionDOtoEntity(SingleEventQuestionResponseDO singleEventQuestionResponseDO){
        SingleEventQuestionsEntity singleEventQuestionsEntity = new SingleEventQuestionsEntity();
        singleEventQuestionsEntity.setText(singleEventQuestionResponseDO.getText());
        singleEventQuestionsEntity.setAdditionalInfo(singleEventQuestionResponseDO.getAdditionalInfo());
        singleEventQuestionsEntity.setAnswers(new HashSet<>());
        for (SingleEventAnswerResponseDO singleEventAnswerResponseDO : singleEventQuestionResponseDO.getAnswers()){
            SingleEventAnswersEntity singleEventAnswersEntity = new SingleEventAnswersEntity();
            singleEventAnswersEntity.setText(singleEventAnswerResponseDO.getText());
            singleEventAnswersEntity.setValue(singleEventAnswerResponseDO.getValue());
            singleEventAnswersEntity.setAdditionalInfo(singleEventAnswerResponseDO.getAdditionalInfo());
            singleEventQuestionsEntity.getAnswers().add(singleEventAnswersEntity);
        }
        return singleEventQuestionsEntity;
    }
}
