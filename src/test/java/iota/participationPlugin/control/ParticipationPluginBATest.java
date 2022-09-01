package iota.participationPlugin.control;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;


@QuarkusTest
class ParticipationPluginBATest {

    @Test
    public void testMonth() {
        final List<String> TEST_LIST = new ArrayList<>(List.of("2021-09,2021-10,2021-11,2021-12,2022-01,2022-02,2022-03,2022-04,2022-05,2022-06,2022-07,2022-08".split(",")));
        YearMonth ym = YearMonth.now(ZoneId.of("America/Montreal"));
        List<String> testSet = new ArrayList<>();
        for (int i = 11; i >= 0; i--) {
            testSet.add(String.valueOf(ym.minusMonths(i)));
        }

        Assertions.assertEquals(testSet, TEST_LIST);
        System.out.println(testSet);
    }

    @Test
    public void test() {

    }


}