package iota.participationPlugin.control;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Period;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


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
        //27.10.2022
        //29.07.2022
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate startDate = LocalDate.parse("29.07.2022", formatter).withDayOfMonth(1);
        LocalDate endDate = LocalDate.parse("27.10.2022", formatter).withDayOfMonth(1);
        Period diff = Period.between(startDate, endDate);

        for (int i = 0; i < diff.getMonths() + 1; i++) {
            System.out.println(startDate.plusMonths(i).getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH));
        }
    }


}