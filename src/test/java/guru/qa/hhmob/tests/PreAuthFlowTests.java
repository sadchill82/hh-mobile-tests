package guru.qa.hhmob.tests;

import guru.qa.hhmob.screens.EntrySplashScreen;
import guru.qa.hhmob.screens.LanguageDialog;
import guru.qa.hhmob.screens.LoginMethodsScreen;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("hh.ru Android — Первый запуск")
@Feature("Pre-auth onboarding flow")
@Owner("sadchill82")
public class PreAuthFlowTests extends BaseTest {

    @Test
    @Tag("smoke")
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("На первом запуске показывается диалог выбора языка")
    void languageDialogShownOnFirstLaunch() {
        LanguageDialog dialog = new LanguageDialog(driver).waitVisible();

        assertThat(dialog.isVisible()).isTrue();
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Тап «Оставить русский язык» закрывает диалог и открывает splash")
    void keepRussianClosesDialog() {
        EntrySplashScreen splash = new LanguageDialog(driver)
                .waitVisible()
                .keepRussian()
                .waitVisible();

        assertThat(splash.hasNewUserButton()).isTrue();
        assertThat(splash.hasExistingAccountButton()).isTrue();
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("На splash-экране есть упоминание политики конфиденциальности")
    void splashHasPolicyDisclaimer() {
        EntrySplashScreen splash = new LanguageDialog(driver)
                .waitVisible()
                .keepRussian()
                .waitVisible();

        assertThat(splash.hasPolicyDisclaimer()).isTrue();
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Тап «Я новый пользователь» открывает экран способов входа")
    void newUserOpensLoginMethods() {
        LoginMethodsScreen methods = new LanguageDialog(driver)
                .waitVisible()
                .keepRussian()
                .waitVisible()
                .tapNewUser()
                .waitVisible();

        assertThat(methods.hasPhoneOption()).isTrue();
        assertThat(methods.hasEmailOption()).isTrue();
        assertThat(methods.hasOtherMethodsOption()).isTrue();
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("На экране входа есть путь для работодателя «Найти сотрудников»")
    void loginScreenHasEmployerPath() {
        LoginMethodsScreen methods = new LanguageDialog(driver)
                .waitVisible()
                .keepRussian()
                .waitVisible()
                .tapNewUser()
                .waitVisible();

        assertThat(methods.hasEmployerOption()).isTrue();
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Кнопка «Назад» на экране входа возвращает на splash")
    void backNavigatesToSplash() {
        EntrySplashScreen splash = new LanguageDialog(driver)
                .waitVisible()
                .keepRussian()
                .waitVisible()
                .tapNewUser()
                .waitVisible()
                .tapBack()
                .waitVisible();

        assertThat(splash.hasNewUserButton()).isTrue();
    }
}
