import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.conditions.ExactText;
import com.codeborne.selenide.conditions.Visible;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.util.Locale;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryServiceTest {
    @BeforeEach
    void setup() {
        open("http://localhost:9999");

    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        Faker faker = new Faker(new Locale("ru"));
        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id=date] input").press(Keys.chord(Keys.LEFT_CONTROL, "a"), Keys.DELETE).setValue(firstMeetingDate);
        $("[data-test-id=name] input").setValue(validUser.getName());
        $("[data-test-id=phone] input").setValue(validUser.getPhone());
        $("[data-test-id=agreement]").click();
        $(Selectors.byText("Запланировать")).click();
        $(Selectors.withText("Успешно")).shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id='success-notification'] .notification__content")
                .shouldHave(exactText("Встреча успешно запланирована на " + firstMeetingDate))
                .shouldBe(visible);
        $("[data-test-id=date] input").press(Keys.chord(Keys.LEFT_CONTROL, "a"), Keys.DELETE).setValue(secondMeetingDate);
        $$("button").findBy(text("Запланировать")).click();
        $("[data-test-id=replan-notification] div.notification__title").shouldHave(text("Необходимо подтверждение"));
        $("[data-test-id='replan-notification'] div.notification__content").shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"))
                .shouldBe(visible);
        $$("button").find(text("Перепланировать")).click();
        $("[data-test-id='success-notification'] .notification__content").shouldHave(text("Встреча успешно запланирована на " + secondMeetingDate))
                .shouldBe(visible);

    }
}

