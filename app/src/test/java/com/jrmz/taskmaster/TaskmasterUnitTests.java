package com.jrmz.taskmaster;

import org.junit.Test;

import static org.junit.Assert.*;

import com.jrmz.taskmaster.database.TaskMasterConverters;
import com.jrmz.taskmaster.models.TaskModel;

import java.util.Date;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TaskmasterUnitTests {

    @Test
    public void testHomeActivitySetupAddTaskButton() {

    }

    @Test
    public void testHomeActivitySetupLoadAllTasksActivityButton() {

    }

    @Test
    public void testHomeActivitySetupUserSettingsButton() {
        // there are no additional classes or methods to test at this time
    }

    @Test
    public void testHomeActivitySetupTasksRecyclerView() {

    }

    @Test
    public void testAddTaskActivitySetupAddThisButton() {

    }

    @Test
    public void testTaskModelCtorBlank() {
        TaskModel sut = new TaskModel();
        assertNotNull("Empty constructor should create an instance.", sut);
    }

    @Test
    public void testTaskModelCtorThreeParams() {
        TaskModel sut = new TaskModel("Title", "Details", "New");
        assertNotNull("TaskModel instantiation should create non-null object",
                sut);
    }

    @Test
    public void testTaskModelGettersAndSetters() {
        String expectedTitle = "Title";
        String expectedBody = "Body";
        String expectedState = "New";
        TaskModel sut = new TaskModel(expectedTitle, expectedBody, expectedState);

        String actualBody = sut.getBody();
        String actualTitle = sut.getTitle();
        String actualState = sut.getState();

        assertEquals("",expectedBody, actualBody);
        assertEquals("",expectedState, actualState);
        assertEquals("",expectedTitle, actualTitle);

        expectedTitle = "NewTitle";
        expectedBody = "New Body";
        expectedState = "Completed";

        sut.setBody(expectedBody);
        sut.setState(expectedState);
        sut.setTitle(expectedTitle);

        actualBody = sut.getBody();
        actualTitle = sut.getTitle();
        actualState = sut.getState();

        assertEquals("",expectedBody, actualBody);
        assertEquals("",expectedState, actualState);
        assertEquals("",expectedTitle, actualTitle);
    }

    @Test
    public void testTaskModelIncrementState() {
        String expectedStartingState = "New";
        String expectedState = "Assigned";
        TaskModel sut = new TaskModel("Title", "Details", expectedStartingState);

        String actualStartingState = sut.getState();
        sut.incrementState();
        String actualResult = sut.getState();
        assertEquals("Increment method should change State from New to In Assigned.",
                expectedState, actualResult);

        sut.incrementState();
        expectedState = "In Progress";
        actualResult = sut.getState();
        assertEquals("Increment method should change State from Assigned to In Progress.",
                expectedState, actualResult);

        sut.incrementState();
        expectedState = "Completed";
        actualResult = sut.getState();
        assertEquals("Increment method should change State from In Process to Completed.",
                expectedState, actualResult);

        sut.incrementState();
        expectedState = "Completed";
        actualResult = sut.getState();
        assertEquals("Increment method should not increment State from Completed.",
                expectedState, actualResult);
    }

    @Test
    public void testTaskModelToString() {
        String expectedResult = "Task Title - Body text";
        TaskModel sut = new TaskModel("Task Title", "Body text", "New");
        String actualResult = sut.toString();
        System.out.println("Actual Result: " + actualResult);
        assertEquals("", expectedResult, actualResult);
    }

    @Test
    public void testTaskModelStateCategoryEnumCTOR() {
        // testing the enum CTOR
    }

    @Test
    public void testTaskModelStateCategoryEnumGetStateText() {
    }

    @Test
    public void testTaskModelStateCategoryEnumValues() {
        var actualResult = TaskModel.StateCategoryEnum.values();
        assertNotNull(actualResult);
    }

    @Test
    public void testTaskModelStateCategoryEnumFromString() {
        var actualResult = TaskModel.StateCategoryEnum.fromString("New");
        assertNotNull("TaskModel.StateCategoryEnum.fromString(\"New\") should not return null.",
                actualResult);
    }

    @Test
    public void testTaskMasterConvertersFromTimeStamp() {
        // Fri Feb 13 15:31:31 PST 2009
        Date actualResult = TaskMasterConverters.fromTimeStamp(1234567891234L);
        System.out.println("TaskMasterConverters.fromTimeStamp returned " + actualResult.toString());
        assertNotNull("A timestamp of type Date should be returned.", actualResult);
    }

    @Test
    public void testTaskMasterConvertersToTimeStamp() {
//        String expectedResult = "Wed Dec 31 16:16:40 PST 1969";
        Long expectedResult = 10000000L;
        Date inputDate = new Date(expectedResult);
        System.out.println("inputDate is: " + inputDate.toString());
        Long actualResult = TaskMasterConverters.toTimeStamp(inputDate);
        System.out.println("TaskMasterConverters.toTimeStamp returned " + actualResult.toString());
        assertEquals("A Date should be converted back to a Unix Epoch timestamp.",
                expectedResult, actualResult);
    }
}