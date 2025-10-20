<div align="center">

# Проект UI автотестов для сайта RSTQB

<img src="images/ISTQB.svg" width="350">
</div>

> **RSTQB** — это ISTQB в России.
>
> **ISTQB** (International Software Testing Qualifications Board) — международная организация, занимающаяся разработкой
> и утверждением стандартов в области тестирования программного обеспечения.

---

## 📖 Содержание

- [🧩 Используемые технологии в проекте](#технологии)
- [🧪 Тест-кейсы](#тест-кейсы)
- [🚀 Запуск автотестов через терминал](#запуск-автотестов-через-терминал)
- [⚙️ Сборка Jenkins](#сборка-jenkins)
- [📊 Allure](#allure)
- [📊+🧪 Allure TestOps](#testops)
- [🔔 Telegram](#telegram)

---

<div align="center">

<h2>Используемые технологии в проекте</h2>

<table>
  <tr>
    <td align="center"><img width="50" src="images/java-logo.svg"><br>Java</td>
    <td align="center"><img width="50" src="images/git-logo.svg"><br>GitHub</td>
    <td align="center"><img width="50" src="images/gradle-logo.svg"><br>Gradle</td>
    <td align="center"><img width="50" src="images/android.svg"><br>Android</td>
    <td align="center"><img width="50" src="images/appium.svg"><br>Appium</td>
    <td align="center"><img width="50" src="images/browserstack.svg"><br>Browserstack</td>
    <td align="center"><img width="50" src="images/selenide-logo.svg"><br>Selenide</td>
    <td align="center"><img width="50" src="images/IDEA-logo.svg"><br>IntelliJ IDEA</td>
    <td align="center"><img width="50" src="images/junit5-logo.svg"><br>JUnit5</td>
    <td align="center"><img width="50" src="images/lombok.png"><br>Lombok</td>
    <td align="center"><img width="50" src="images/allure.svg"><br>Allure Report</td>
    <td align="center"><img width="50" src="images/allureTestOps.svg"><br>Allure TestOps</td>
    <td align="center"><img width="50" src="images/jenkins.svg"><br>Jenkins</td>
    <td align="center"><img width="50" src="images/telegram.svg"><br>Telegram</td>
  </tr>
</table>

</div>

---

<a id="тест-кейсы"></a>

## 🧪 Тест-кейсы

### Главная страница

- ✅ Проверка видимости логотипа.
- ✅ Кнопка "Поиск" перебрасывает на страницу "Расписание экзаменов".
- ✅ Поиск по дате работает.

### Страница "Материалы ISTQB®"

- ✅ PDF скачивается, соответствующий текст в PDF присутствует.

### Страница "Расписание экзаменов"

- ✅ Поиск по комбинации фильтров "город" и "уровень" возможен.
- ✅ Кнопка перехода на следущющую страницу.
- ✅ Фильтр и его поля видны.

___
<a id="запуск-автотестов-через-терминал"></a>

## 🚀 Запуск автотестов через терминал

### Запустить все тесты локально

```bash
  ./gradlew clean test
```

Также есть возможность вместо **test** указать **smoke** или **regress** и запустятся соответствующие тесты 🤖

### Запустить все тесты удалённо (на selenoid сервере)

```bash
  ./gradlew clean -DselenoidLogin={LOGIN} -DselenoidPass={PASS} test
```

Вместо {LOGIN} и {PASS} необходимо подставить креды от selenoid.

### Получить Allure отчёт

```bash
  ./gradlew allureServe
```

___

<a id="сборка-jenkins"></a>

## <img width="40" style="vertical-align:middle" src="images/jenkins.svg"> </a> Сборка в <a target="_blank" href="https://jenkins.autotests.cloud/"> Jenkins </a>

Jenkins job-а для запуска автотестов этого проекта
располагается <a target="_blank" href="https://jenkins.autotests.cloud/job/C36-andreyalbaev-ui-21"> здесь</a>. Для
запуска сборки необходимо нажать на <code>Build with parameters</code>, выбрать необходимые параметры и нажать
кнопку <code>Build</code>.

### Параметры сборки в Jenkins:

- SELENOID_LOGIN
- SELENOID_PASS
- TESTS_TO_RUN
- ENV
- BROWSER_VERSION
- BROWSER_SIZE
- COMMENT

<div align="center">

<img src="images/JenParameters.png" width="1500">

</div>


❗После выполнения сборки формируются <code>Allure Report</code> - HTML отчёт о прошедших тестах и тестовые прогоны в
TMS <code>Allure TestOps</code>.

---
<a id="allure"></a>

## <img width="35" style="vertical-align:middle" src="images/allure.svg"> </a>  Allure

### Пример отчёта

<img src="images/allureReportExample.png" width="1500">

Как уже было сказано ранее, отчёт формируется автоматически после прогона автотестов.

### Allure attachments

Также после выполнения тестов к отчёту добавляются:

1) Скриншот окна перед закрытием драйвера
2) Логи консоли браузера
3) HTML страницы перед закрытием драйвера
4) Видео выполнения автотеста

На скриншоте ниже это видно в правой части экрана.

<img src="images/allureAttach.png" width="1500">

---
<a id="testops"></a>

## <img width="35" style="vertical-align:middle" src="images/allureTestOps.svg"> </a>  Allure TestOps

Это TMS, которую можно интегрировать, например, с Jenkins и Jira, в данном проекте интегрировано с Jenkins: в TestOps
автоматически выгружаются результаты прохождения автотестов после выполнения job-ы в Jenkins.

<div align="center">
<img src="images/jenkinsAllure.jpg" width="750">

</div>
<div align="center">
<img src="images/testOpsResults.jpg" width="750">

</div>

---

<a id="telegram"></a>

## <img width="35" style="vertical-align:middle" src="images/telegram.svg"> </a>  Telegram уведомления

К проекту также подключен бот , который по завершении прогона автотестов посылает краткую сводку и ссылку на allure
отчёт в telegram<a target="_blank" href="https://t.me/esttonami"> чат</a>.


<div align="center">
<img src="images/tgAlerting.png" width="750">

</div>
