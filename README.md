# Taskmaster

## Daily Change Log

Weds and Thurs, June 23 and 24:

- Implemented Homepage, Add a Task, and All Tasks Activities. 
- Configured layout of all three Activities. 
- Enabled button-based navigation and retained back-button functionality. 
- Added styling. 

Saturday 25-June: 

- Implemented TaskModel to carry data.
- Added RecyclerView on AllTasks Activity.
- Added Spinner element to show hard-coded tasks in a list.

Monday 27-June:

- Set up Room.
- Refactored RecyclerView.
- Modified AddTask to utilize local DB.
- Set up Details Page to show Task Description and Title.

## Feature Tasks

Feature Tasks are broken down into the following sub sections.

### Homepage

- [X] Create a Homepage.
- [X] The main page should be built out to match the wireframe.
- [X] Should have a heading at the top of the page, an image to mock the “my tasks” view.
- [X] Should have buttons at the bottom of the page.
- [X] Should allow going to the “add tasks” and “all tasks” page.
- [X] Create a Task Detail page. It should have a title at the top of the page, and a Lorem Ipsum description.
- [X] Create a Settings page. It should allow users to enter their username and hit save.
- [X] The main page should be modified to contain three different buttons with hardcoded task titles. When a user taps one of the titles, it should go to the Task Detail page, and the title at the top of the page should match the task title that was tapped on the previous page.
- [X] The homepage should also contain a button to visit the Settings page, and once the user has entered their username, it should display “{username}’s tasks” above the three task buttons.
- [X] Refactor home page to use a RecyclerView for displaying Task data. Will be hard-coded data for now.
- [X] Implement a Task Class with "States" for the tasks i.e.: "New", "Assigned", "In Progress", or "Complete".
- [X] Implement a ViewAdapter class to display data from list of Tasks
- [X] Create at least 3 hard-coded tasks in ~~MainActivity~~ HomeActivity.
- [X] A tapped task in RecyclerView will launch the ~~DetailPage~~ TaskDetailActivity with the correct Task Title displayed.
- [ ] Refactor RecyclerView to display all Task Entities in the Database.

### Add a Task

- [X] The “Add a Task” page allows users to type in details about a new task.
- [X] Must show a title and a body.
- [X] When users click the “submit” button, show a “submitted!” label on the page.
- [ ] Modify Add Task Form to save data entered as a Task in the local database. *in process*

### All Tasks

- [X] The 'all tasks' page should just be an image with a back button; it needs no functionality.

### Details Tasks

- [ ] Ensure the description and status of a tapped task are also displayed on the detail page.
- [ ] Ensure the Title is still displayed.
- [ ] Decide whether to pass the entire Entity or to only pass an ID using an Intent.

### Implementing Room For Data Management

- [X] Set up Room in the App and modify the Task Class to be an Entity.

### Styling and Stretch Goals

Stretch goals:

- [X] Decide on a color scheme.
- [X] Decide font families. For now will stick with the built-in Android font-families.
- [ ] Allow the user to specify on their settings page how many tasks should be shown on the homepage. Use this to dynamically create buttons for as many tasks as the user requests.
- [X] Also display the description of the task rather than Lorem Ipsum text.

## Screenshots

### Latest Updates

The solution is incomplete, so no screenshots have been provided.

### Previous Updates In Descending Order

![My Tasks Activity (Home Page)](./app/screenshots/Taskmaster_Weds__MyTasksActivity.png)

![Add Task Activity](./app/screenshots/Taskmaster_Weds__AddTaskActivity.png)

![Add Task Activity - Submit Button Clicked](./app/screenshots/Taskmaster_Weds__AddTaskActivity_Submitted.png)

![All Tasks Activity](./app/screenshots/Taskmaster_Weds__AllTasksActivity.png)

![Revamped Home Page](./app/screenshots/Taskmaster_Thurs__HomeActivity.png)

![Adding a Task Step 1](./app/screenshots/Taskmaster_Thurs__AddTask_1.png)

![Adding a Task Step 2](./app/screenshots/Taskmaster_Thurs__AddTask_2.png)

![Adding a Task Step 3](./app/screenshots/Taskmaster_Thurs__AddTask_3.png)

![All Tasks Activity](./app/screenshots/Taskmaster_Thurs__AllTasksActivity.png)

![Task Detail Activity](./app/screenshots/Taskmaster_Thurs__TaskDetail.png)

![User Settings Activity 1](./app/screenshots/Taskmaster_Thurs__UserSettingsActivity_1.png)

![User Settings Activity 2](./app/screenshots/Taskmaster_Thurs__UserSettingsActivity_2.png)

![User Settings Activity 3](./app/screenshots/Taskmaster_Thurs__UserSettingsActivity_3.png)

![Home Page Activity (Home Page)](./app/screenshots/Taskmaster_Sat__HomeActivityScroller.png)

![All Tasks Activity with RecyclerView](./app/screenshots/Taskmaster_Sat__AllTaskActivityWithRecyclerView.png)

## Tests

- [X] Write unittests for any custom methods built.

*From hereon out* utilize Espresso Tests to validate functionality.

Link to [Unittest Tests Package](./app/src/test/java/com/example/taskmaster/TaskmasterUnitTests.java)

Link to [Espresso Tests Package](./app/src/androidTest/java/com/example/taskmaster/HomeActivityTestAllTasksDisplays.java)

## References and Attributions

As a guide I utilized the Class Repository and this [Medium.com article](https://medium.com/@haxzie/using-intents-and-extras-to-pass-data-between-activities-android-beginners-guide-565239407ba0)
