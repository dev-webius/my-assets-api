package net.webius.myassets.component;

import net.webius.myassets.util.DateUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest @DisplayName("DateCalculator 테스트")
public class DateUtilsTest {
    private final LocalDate beginAt = LocalDate.of(2025, 1, 1);
    private final LocalDate endAt = LocalDate.of(2025, 7, 1); // Total Month: 6

    @Test @DisplayName("beginAt 이전에 currentMonth 계산")
    public void calculateCurrentMonthBefore() {
        var targetAt = LocalDate.of(2024, 11, 11);

        assertThat(DateUtils.getCurrentMonth(beginAt, endAt, targetAt)).isEqualTo(0);
    }

    @Test @DisplayName("currentMonth 계산")
    public void calculateCurrentMonth() {
        var targetAt = LocalDate.of(2025, 3, 11);

        assertThat(DateUtils.getCurrentMonth(beginAt, endAt, targetAt)).isEqualTo(3);
    }

    @Test @DisplayName("endAt 이후에 currentMonth 계산")
    public void calculateCurrentMonthAfter() {
        var targetAt = LocalDate.of(2026, 3, 1);

        assertThat(DateUtils.getCurrentMonth(beginAt, endAt, targetAt)).isEqualTo(6);
    }
}
