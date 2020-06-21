package ro.ucv.ace.brandmanagement;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.ucv.ace.sentiment.PolarityAnalyzer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class TwitterCustomerFeedback implements DisposableBean {

    private final WebDriver driver = new ChromeDriver();

    private final PolarityAnalyzer polarityAnalyzer;

    @Autowired
    public TwitterCustomerFeedback(PolarityAnalyzer polarityAnalyzer) {
        this.polarityAnalyzer = polarityAnalyzer;
    }

    @SneakyThrows
    public List<CustomerFeedbackResponse> getTwitterResponsesAndComputePolarities(String accountName, String tweetId) {
        String url = String.format("https://twitter.com/%s/status/%s", accountName, tweetId);
        driver.get(url);
        WebDriverWait wait = new WebDriverWait(driver, 30);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        Set<String> tweetResponses = new HashSet<>();
        scrollAndGetMoreResponses(wait, js, tweetResponses);

        return tweetResponses.stream()
                .filter(StringUtils::isNotEmpty)
                .map(tweetResponse -> {
                    PolarityAnalyzer.Polarity polarity = polarityAnalyzer.computePolarity(tweetResponse);
                    return new CustomerFeedbackResponse(tweetResponse, polarity.name());
                })
                .collect(Collectors.toList());
    }

    private void scrollAndGetMoreResponses(WebDriverWait wait, JavascriptExecutor js, Set<String> responses) {
        while (true) {
//            waitUntil(wait, createTwitterResponseLocator());
            Long lastHeight = (Long) js.executeScript("return document.body.scrollHeight");

            waitUntil(wait, createTwitterResponseLocator());
            driver.findElements(createTwitterResponseLocator()).stream()
                    .map(WebElement::getText)
                    .forEach(responses::add);

            js.executeScript(String.format("window.scrollTo(0, %s)", lastHeight + 500));
            sleep(2000);
            Long newHeight = (Long) js.executeScript("return document.body.scrollHeight");
            if (lastHeight.equals(newHeight)) {
                CLICK_RESULT clickResult = clickMoreResponses();
                if (clickResult.equals(CLICK_RESULT.NO_ELEMENT)) {
                    break;
                }
                sleep(500);
            }
        }
    }

    private CLICK_RESULT clickMoreResponses() {

        do {
            CLICK_RESULT clickResult = tryClick();
            if (clickResult.equals(CLICK_RESULT.SUCCESS) || clickResult.equals(CLICK_RESULT.NO_ELEMENT)) {
                return clickResult;
            }
        } while (true);
    }

    private void waitUntil(WebDriverWait wait, By by) {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
    }

    private CLICK_RESULT tryClick() {
        List<WebElement> moreReplayButtons = driver.findElements(createTwitterLoadMoreResponsesLocator());
        if (moreReplayButtons.isEmpty()) {
            return CLICK_RESULT.NO_ELEMENT;
        }

        try {
            moreReplayButtons.get(0).click();
            return CLICK_RESULT.SUCCESS;
        } catch (Exception e) {
            log.error("Stale web element on click");
            return CLICK_RESULT.ERROR;
        }
    }

    private enum CLICK_RESULT {
        NO_ELEMENT, SUCCESS, ERROR
    }

    private By createTwitterLoadMoreResponsesLocator() {
        return By.cssSelector("div[class='css-1dbjc4n r-16y2uox r-1wbh5a2 r-1777fci']");
    }

    private By createTwitterResponseLocator() {
        return By.cssSelector("div[class='css-901oao r-hkyrab r-1qd0xha r-a023e6 r-16dba41 r-ad9z0x r-bcqeeo r-bnwqim r-qvutc0']");
    }


    @SneakyThrows
    private void sleep(int milis) {
        Thread.sleep(milis);
    }

    @Override
    public void destroy() throws Exception {
        driver.quit();
    }
}
