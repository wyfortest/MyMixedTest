package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertTrue;

/**
 * @description: 2018佛山阳光采购项目接口列表测试
 * @author: WangYu
 * @date: 2018/3/14
 */
public class FS2018fshisTest {
    private static WebDriver dr;

    @BeforeClass
    public static void setUp() {
        dr = new FirefoxDriver();
        dr.get("http://localhost:7001/2018_FSDataCenterAPI_Git/fshis/index");
    }

    @Test
    public void methodJustforTest(){
        assertTrue(true);
    }

    @Test(enabled = true)
    public void testInterface() {
        //获得所有接口，页面左侧的41个链接
        List<WebElement> interfaces =dr.findElements(By.cssSelector(".apimain_a a"));
        WebElement title, submitBtn, result;
        String resultText ;
        int countR=0,countE=0;
        //创建一个Javascript执行实例
        JavascriptExecutor je = (JavascriptExecutor)dr;

        for (int i=0; i<interfaces.size()-3; i++) {
            // IMPORTANT: 下面这一行非常重要，因为此时页面已经刷新了，看似相同的元素实际已经不同了。
            // 若去掉，会抛出StaleElementReferenceException元素引用失效异常
            List<WebElement> faces =dr.findElements(By.cssSelector(".apimain_a a"));
            WebElement intface = faces.get(i);
            //执行js语句，拖拽浏览器滚动条，直到该元素到底部
            je.executeScript("arguments[0].scrollIntoView(true);",intface);
            String href = intface.getAttribute("href");
            dr.get(href);

            title = dr.findElement(By.cssSelector("h2"));
            //获得页面底部“提交测试”按钮
            submitBtn = dr.findElement(By.id("btnCommitTest"));
            //执行js语句，拖拽浏览器滚动条，直到该元素到底部，马上就不可以见
            je.executeScript("arguments[0].scrollIntoView(true);",submitBtn);
            submitBtn.click();

            result = dr.findElement(By.cssSelector("pre#resultJson"));
            resultText = result.getText();

            if (resultText.contains("\"Code\": \"EMI_R_00\"")){
                countR++;
            }
            if (resultText.contains("\"Code\": \"EMI_E_")){
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
