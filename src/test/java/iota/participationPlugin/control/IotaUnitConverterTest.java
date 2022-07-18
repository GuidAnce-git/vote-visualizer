package iota.participationPlugin.control;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.*;
@QuarkusTest
class IotaUnitConverterTest {

    @Inject
    IotaUnitConverter iotaUnitConverter;

    @Test
    public void test(){
        System.out.println(iotaUnitConverter.convertFromIotaToUnits(990711336124690D));


    }

}