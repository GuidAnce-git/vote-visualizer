package iota.participationPlugin.control;

import iota.participationPlugin.entity.NodeEntity;
import iota.participationPlugin.entity.SingleEventStakingEntity;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;
import java.util.Locale;
import java.util.Objects;

@ApplicationScoped
public class UnitConverter {

    public long convertIotaToMiota(Long iota) {

        return Double.valueOf(iota / Math.pow(10, 6)).longValue();

    }

    public String convertIotaToHigherUnitsWithUnit(SingleEventStakingEntity singleEventStakingEntity) {

        if (singleEventStakingEntity.getStaked() > 1000000000000000D) {
            return String.format("%.1f", singleEventStakingEntity.getStaked() / Math.pow(10, 15)) + " " + IotaSymbolsEnum.PIOTA.getName();
        } else if (singleEventStakingEntity.getStaked() > 1000000000000D) {
            return String.format("%.1f", singleEventStakingEntity.getStaked() / Math.pow(10, 12)) + " " + IotaSymbolsEnum.TIOTA.getName();
        } else if (singleEventStakingEntity.getStaked() > 1000000000D) {
            return String.format("%.1f", singleEventStakingEntity.getStaked() / Math.pow(10, 9)) + " " + IotaSymbolsEnum.GIOTA.getName();
        } else if (singleEventStakingEntity.getStaked() > 1000000D) {
            return String.format("%.1f", singleEventStakingEntity.getStaked() / Math.pow(10, 6)) + " " + IotaSymbolsEnum.MIOTA.getName();
        } else if (singleEventStakingEntity.getStaked() > 1000D) {
            return singleEventStakingEntity.getStaked() + " " + IotaSymbolsEnum.MIOTA.getName();
        }
        return null;
    }

    public String convertToHigherUnit(SingleEventStakingEntity singleEventStakingEntity) {
        StringBuilder stringBuilder = new StringBuilder();
        if (Objects.equals(singleEventStakingEntity.getSymbol(), IotaSymbolsEnum.MICRO_ASMB.getName())) {
            Formatter formatter = new Formatter(stringBuilder, Locale.GERMAN);
            formatter.format("%(,d", singleEventStakingEntity.getRewarded() / 1000000);
            stringBuilder.append(" ").append(IotaSymbolsEnum.ASMB.getName());
            return stringBuilder.toString();
        }

        if (Objects.equals(singleEventStakingEntity.getSymbol(), IotaSymbolsEnum.SMR.getName())) {
            Formatter formatter = new Formatter(stringBuilder, Locale.GERMAN);
            formatter.format("%(,d", singleEventStakingEntity.getRewarded() / 100000);
            stringBuilder.append(" ").append(IotaSymbolsEnum.SMR.getName());
            return stringBuilder.toString();
        }
        return null;
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

    public String convertMilestoneToDate(Long currentMilestone) {
        NodeEntity nodeEntity = NodeEntity.findById(1L);
        long seconds = (nodeEntity.getConfirmedMilestoneIndex() - currentMilestone) * 10;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return LocalDateTime.now().minusSeconds(seconds).format(formatter);
    }

}
