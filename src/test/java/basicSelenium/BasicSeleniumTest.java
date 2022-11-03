package basicSelenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Date;

public class BasicSeleniumTest {

    WebDriver driver;

    @BeforeEach
    public void setup() {
        System.out.println("setup");
        System.setProperty("webdriver.chrome.driver", "src/test/resources/driver/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        driver = new ChromeDriver(options);
        driver.get("http://todo.ly/");
    }

    @AfterEach
    public void cleanup(){
        System.out.println("cleanup");
        driver.quit();
    }

    @Test
    public void verifyCRUDProject () throws InterruptedException {

        // login
        driver.findElement(By.xpath("//img[contains(@src,'pagelogin')]")).click();
        driver.findElement(By.id("ctl00_MainContent_LoginControl1_TextBoxEmail")).sendKeys("marcelo.equise@bootcamp.com");
        driver.findElement(By.id("ctl00_MainContent_LoginControl1_TextBoxPassword")).sendKeys("123qwe");
        driver.findElement(By.id("ctl00_MainContent_LoginControl1_ButtonLogin")).click();
        Thread.sleep(1000);
        Assertions.assertTrue(driver.findElement(By.id("ctl00_HeaderTopControl1_LinkButtonLogout")).isDisplayed()
                                    ,"ERROR login was incorrect");

        // create
        String nameProject = "Marcelo" + new Date().getTime();
        driver.findElement(By.xpath("//td[text()='Add New Project']")).click();
        driver.findElement(By.id("NewProjNameInput")).sendKeys(nameProject);
        driver.findElement(By.id("NewProjNameButton")).click();
        Thread.sleep(1000);

        int actualResult = driver.findElements(By.xpath("//td[text()='"+nameProject+"']")).size();
        Assertions.assertTrue(actualResult >= 1, "ERROR The project was not created");

        // update
        nameProject = "Update" + new Date().getTime();
        driver.findElement(By.xpath("//div[contains(@style,'block')]/img")).click();
        driver.findElement(By.xpath("//ul[@id=\"projectContextMenu\"]//a[text()='Edit']")).click();
        driver.findElement(By.xpath("//td/div/input[@id='ItemEditTextbox']")).clear();
        driver.findElement(By.xpath("//td/div/input[@id='ItemEditTextbox']")).sendKeys(nameProject);
        driver.findElement(By.xpath("//td/div/img[@id='ItemEditSubmit']")).click();
        Thread.sleep(1000);

        actualResult = driver.findElements(By.xpath("//td[text()='"+nameProject+"']")).size();
        Assertions.assertTrue(actualResult >= 1, "ERROR The project was not updated");

        // delete
        driver.findElement(By.xpath("//div[contains(@style,'block')]/img")).click();
        driver.findElement(By.id("ProjShareMenuDel")).click();
        driver.switchTo().alert().accept();
        Thread.sleep(1000);

        actualResult = driver.findElements(By.xpath("//td[text()='"+nameProject+"']")).size();
        Assertions.assertTrue(actualResult == 0, "ERROR The project was not removed");
    }

    @Test
    public void verifyCRUDTask () throws InterruptedException {

        // login
        driver.findElement(By.xpath("//img[contains(@src,'pagelogin')]")).click();
        driver.findElement(By.id("ctl00_MainContent_LoginControl1_TextBoxEmail")).sendKeys("marcelo.equise@bootcamp.com");
        driver.findElement(By.id("ctl00_MainContent_LoginControl1_TextBoxPassword")).sendKeys("123qwe");
        driver.findElement(By.id("ctl00_MainContent_LoginControl1_ButtonLogin")).click();
        Thread.sleep(1000);

        // create project
        String nameProject = "Marcelo" + new Date().getTime();
        driver.findElement(By.xpath("//td[text()='Add New Project']")).click();
        driver.findElement(By.id("NewProjNameInput")).sendKeys(nameProject);
        driver.findElement(By.id("NewProjNameButton")).click();
        Thread.sleep(1000);

        // create task
        String nameTask = "newTask" + new Date().getTime();
        driver.findElement(By.id("NewItemContentInput")).sendKeys(nameTask);
        driver.findElement(By.id("NewItemAddButton")).click();
        Thread.sleep(1000);

        int actualTasks = driver.findElements(By.xpath("//td/div[text()='"+nameTask+"']")).size();
        Assertions.assertTrue(actualTasks >= 1, "ERROR the task was not created");

        // update task
        driver.findElement(By.xpath("//td/div[text()='"+nameTask+"']")).click();
        driver.findElement(By.xpath("//td/div/div[@id=\"ItemEditDiv\"]/textarea[@id=\"ItemEditTextbox\"]")).clear();
        nameTask = "UpdatedTask" + new Date().getTime();
        driver.findElement(By.xpath("//td/div/div[@id=\"ItemEditDiv\"]/textarea[@id=\"ItemEditTextbox\"]")).sendKeys(nameTask);
        driver.findElement(By.xpath("//td/div/div[@id=\"ItemEditDiv\"]/textarea[@id=\"ItemEditTextbox\"]")).sendKeys(Keys.ENTER);
        Thread.sleep(1000);

        actualTasks = driver.findElements(By.xpath("//td/div[text()='"+nameTask+"']")).size();
        Assertions.assertTrue(actualTasks >= 1, "ERROR the task was not updated");
    }
}
