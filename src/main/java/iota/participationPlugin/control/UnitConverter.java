package iota.participationPlugin.control;

import iota.participationPlugin.entity.SingleEventStakingEntity;

import javax.enterprise.context.ApplicationScoped;
import java.util.Formatter;
import java.util.Locale;
import java.util.Objects;

@ApplicationScoped
public class UnitConverter {

    public void convertFromIotaToUnits(SingleEventStakingEntity singleEventStakingEntity) {

        if (singleEventStakingEntity.getStaked() > 1000000000000000D) {
            singleEventStakingEntity.setFormattedStaked(String.format("%.1f", singleEventStakingEntity.getStaked() / Math.pow(10, 15)) + " " + IotaSymbolsEnum.PIOTA.getName());
        } else if (singleEventStakingEntity.getStaked() > 1000000000000D) {
            singleEventStakingEntity.setFormattedStaked(String.format("%.1f", singleEventStakingEntity.getStaked() / Math.pow(10, 12)) + " " + IotaSymbolsEnum.TIOTA.getName());
        } else if (singleEventStakingEntity.getStaked() > 1000000000D) {
            singleEventStakingEntity.setFormattedStaked(String.format("%.1f", singleEventStakingEntity.getStaked() / Math.pow(10, 9)) + " " + IotaSymbolsEnum.GIOTA.getName());
        } else if (singleEventStakingEntity.getStaked() > 1000000D) {
            singleEventStakingEntity.setFormattedStaked(String.format("%.1f", singleEventStakingEntity.getStaked() / Math.pow(10, 6)) + " " + IotaSymbolsEnum.MIOTA.getName());
        } else if (singleEventStakingEntity.getStaked() > 1000D) {
            singleEventStakingEntity.setFormattedStaked(singleEventStakingEntity.getStaked() + " " + IotaSymbolsEnum.MIOTA.getName());
        }
    }

    public void convertToHigherUnit(SingleEventStakingEntity singleEventStakingEntity) {

        if (Objects.equals(singleEventStakingEntity.getSymbol(), IotaSymbolsEnum.MICRO_ASMB.getName())) {
            StringBuilder stringBuilder = new StringBuilder();
            Formatter formatter = new Formatter(stringBuilder, Locale.GERMAN);
            formatter.format("%(,.0f", singleEventStakingEntity.getRewarded() / 1000000);
            stringBuilder.append(" ").append(IotaSymbolsEnum.ASMB.getName());
            singleEventStakingEntity.setFormattedReward(stringBuilder.toString());
        }

        if (Objects.equals(singleEventStakingEntity.getSymbol(), IotaSymbolsEnum.SMR.getName())) {
            StringBuilder stringBuilder = new StringBuilder();
            Formatter formatter = new Formatter(stringBuilder, Locale.GERMAN);
            formatter.format("%(,.0f", singleEventStakingEntity.getRewarded() / 100000);
            stringBuilder.append(" ").append(IotaSymbolsEnum.SMR.getName());
            singleEventStakingEntity.setFormattedReward(stringBuilder.toString());
        }


    }

}
