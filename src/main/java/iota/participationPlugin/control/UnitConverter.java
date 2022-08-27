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
        StringBuilder stringBuilder = new StringBuilder();
        if (Objects.equals(singleEventStakingEntity.getSymbol(), IotaSymbolsEnum.MICRO_ASMB.getName())) {
            Formatter formatter = new Formatter(stringBuilder, Locale.GERMAN);
            formatter.format("%d", singleEventStakingEntity.getRewarded() / 1000000);
            stringBuilder.append(" ").append(IotaSymbolsEnum.ASMB.getName());
            singleEventStakingEntity.setFormattedReward(stringBuilder.toString());
        }

        if (Objects.equals(singleEventStakingEntity.getSymbol(), IotaSymbolsEnum.SMR.getName())) {
            Formatter formatter = new Formatter(stringBuilder, Locale.GERMAN);
            formatter.format("%d", singleEventStakingEntity.getRewarded() / 100000);
            stringBuilder.append(" ").append(IotaSymbolsEnum.SMR.getName());
            singleEventStakingEntity.setFormattedReward(stringBuilder.toString());
        }
    }

    public Long getConvertedASMB(SingleEventStakingEntity singleEventStakingEntity, Long value) {

        if (Objects.equals(singleEventStakingEntity.getSymbol(), IotaSymbolsEnum.MICRO_ASMB.getName())) {
            return value / 1000000;
        }

        if (Objects.equals(singleEventStakingEntity.getSymbol(), IotaSymbolsEnum.SMR.getName())) {
            return value / 100000;
        }

        return null;
    }

}
