package com.example.taskmaster;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.taskmaster.database.TaskMasterConverters;
import com.example.taskmaster.models.TaskModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

    }

    @Test
    public void testTaskModelCtorTwoParams() {

    }

    @Test
    public void testTaskModelGettersAndSettersTBD() {
        // this may end up being multiple tests
    }

    @Test
    public void testTaskModelIncrementState() {
        // four or more tests here
    }

    @Test
    public void testTaskModelToString() {
        String expectedResult = "Task Title - Body text";
        TaskModel sut = new TaskModel("Task Title", "Body text");
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