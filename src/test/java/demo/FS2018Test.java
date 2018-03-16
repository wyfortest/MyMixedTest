package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertTrue;

/**
 * @description: 2018佛山阳光采购项目接口列表测试
 * @author: WangYu
 * @date: 2018/3/6
 */
public class FS2018Test {
    private static WebDriver dr;

    @BeforeClass
    public static void setUp() {
        dr = new FirefoxDriver();
        dr.get("http://localhost:7001/2018_FSDataCenterAPI_Git/index");
    }

    @Test
    public void methodJustforTest(){
        assertTrue(false);
    }

    @Test(enabled = true)
    public void testInterface() {
        List<WebElement> parDiv =dr.findElements(By.className("none-table"));
        WebElement title,testBtn, submitBtn, result;
        String resultText = "";
        int countR=0,countE=0;
        WebDriverWait wait = new WebDriverWait(dr,3);

        for (int i=1; i<parDiv.size()-3; i++) {
            title = parDiv.get(i).findElement(By.cssSelector(".search"));
            testBtn = parDiv.get(i).findElement(By.linkText("测试"));
            testBtn.click();

            submitBtn = parDiv.get(i).findElement(By.tagName("button"));
            wait.until(ExpectedConditions.visibilityOf(submitBtn));
            submitBtn.click();

            result = parDiv.get(i).findElement(By.cssSelector(".container-result span.basic-info-right"));
            wait.until(ExpectedConditions.visibilityOf(result));
            resultText = result.getText();

            if (resultText.contains("\"Code\":\"EMI_R_00")){
                countR++;
            }
            if (resultText.contains("\"Code\":\"EMI_E_")){
                countE++;
                System.out.println("countE="+countE+"==================\n"+title.getText());
            }
        }
        System.out.println("countR="+countR);
    }

    @AfterClass
    public static void tearDown(){
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        dr.quit();
    }
}
