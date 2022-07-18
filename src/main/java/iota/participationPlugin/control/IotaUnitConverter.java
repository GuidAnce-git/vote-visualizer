package iota.participationPlugin.control;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class IotaUnitConverter {

    public String convertFromIotaToUnits(Double iota){

        if (iota > 1000000000000000D){
            return String.format("%.1f", iota / Math.pow(10, 15)) + " Piota";
        }else if (iota > 1000000000000D) {
            return String.format("%.1f", iota / Math.pow(10, 12)) + " Tiota";
        }else if (iota > 1000000000D) {
            return String.format("%.1f", iota / Math.pow(10, 9)) + " Giota";
        }else if (iota > 1000000D) {
            return String.format("%.1f", iota / Math.pow(10, 6)) + " Miota";
        }else if (iota > 1000D) {
            return iota + " Iota";
        }
        return null;
    }

}
