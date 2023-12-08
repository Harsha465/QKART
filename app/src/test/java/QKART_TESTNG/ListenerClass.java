package QKART_TESTNG;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;



public class ListenerClass implements ITestListener {


    public void onStart(ITestContext context) {
        System.out.println("onStart method started");

    }

    public void onTestStart(ITestResult result) {
        System.out.println("New Test Started" + result.getName());
        QKART_Tests.takeScreenshot("StartTestCase", result.getName());


    }

    public void onTestSuccess(ITestResult result) {
        System.out.println("onTestSuccess Method" + result.getName());
        QKART_Tests.takeScreenshot("TestSuccess", result.getName());

    }

    @Override
    public void onTestFailure(ITestResult result) {
        ITestListener.super.onTestFailure(result);
        QKART_Tests.takeScreenshot("TestFailure", result.getName());

    }
}