# Taskmaster

Jon Rumsey's TaskMaster

## Description

An MVP Exploratory Task Management App for Android.

Developed in Java using AndroidStudio, this app leverages AWS Amplify and GraphQL to store Task 
and Team Name data. Locally stored data includes an un-authenticated username.

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

Tuesday 28-June:

- Completed Room DB integration.
- Updated style and added a background.
- Added 4 new Espresso Tests.
- Added unittests.
- Fixed back-button functionality.
- Added ability to destroy certain Activities after work is completed using built-in 'finish()' method.

Wednesday 29-June:

- Removed references to Room and previous Entity and Model "TaskModel".
- Created a Task Resource using AWS Amplify CLI.
- Refactored DB references to use AWS Amplify data instead of Room.
- Modified Add Task Activity to save Task Data to DynamoDB.
- Refactored HomeActivity RecyclerView to display DynamoDB entries.
- Encapsulated Activities with entry-point Class "TaskMasterApplication".
- Espresso Tests were not changed, and still operate.
- Added screenshots of progress to this readme.

Thursday 30-June:

- Added Team model and implemented Bi-directional 'has many' relationship.
- Added 3 Teams (hard coded for now) via mutations.
- Deleted the AllTasks Activity (was not longer in use).
- Added Spinner to select a Team on the AddTask Activity (In progress).

Saturday 2-July:

- Debugged adding Team to a Task, now functional and DB-backed.

Tuesday 5-July:

- Debugged app, updated comments to comment strings, removed some dead code, validated functionality.
- Working through publication steps...

Wednesday 6-July:

- Implemented Cognito through Amplify Auth.
- Enabled self-registration.
- Enabled logon capability.
- Enabled logoff capability.

Friday 8-July

- Allow users to optionally select an image to attach to a new task.
- If a user attaches an image to the task it should be uploaded to S3 and associated with that task.
- Task Detail should display an image within the activity if one is associated with the selected task.

## Feature Tasks

Feature Tasks are broken down into the following sub sections.

### Add Attach An Image Functionality

- [ ] Allow user to optionally attach an image to a new Task.
- [ ] A selected image is uploaded to S3 and associated with that task.
- [ ] Image associated with the Task is displayed within the Task Detail activity.

### Integrate Login Functionality with Cognito

- [X] Create a Register and Login Activity.
- [X] Enable Authentication through Amplify.AUTH and Cognito.
- [X] Capture user email address and a password in order to register.
- [X] Enable Email verification / Registration functionality for new user.
- [X] Enable registered user logon.
- [X] Maintain authenticated user session through all Activities.

### Publishing App to GoogleStore

- [X] Get GoogleDev account.
- [X] Update app-level build.gradle with new package-ID (in this case 'example' to 'jrmz').
- [X] Update 'package=' value in AndroidManifest.xml.
- [X] Update package imports and debug code until package references to your new package name are resolved (see next subsection).
- [X] Name the app "Jon Rumsey's TaskMaster" 
- [X] Configured app as "Free", which cannot be changed to "Paid".
- [X] There is no restricted content, Malware, Intellectual Property, or MUS in this app.
- [X] Early Internal Testing was NOT configured.
- [X] GooglePlay suggested Content Rating of E-Everyone (US) or 3+ (IARC Generic, most other parts of the world).
- [X] The app collects a sample name but has no registration or PII submission requirements.
- [X] Added PRIVACYPOLICY.md file.
- [X] Target Audience is 18+.
- [X] Data Safety declares no data collection and no data shared with 3rd parties.
- [X] Added custom icon file using Image Asset Studio.
- [ ] Add App short and full descriptions.
- [ ] Create and upload: Feature Graphic (1024x500px, 1MB PNG|JPG).
- [ ] Create and upload: At least 2 320px to 3840px PHONE screenshots.
- [ ] ~~Create and upload: At least one 7-inch Tablet screenshot 320px to 3840px.~~
- [ ] ~~Create and upload: At least one 10-inch Tablet screenshot 320 to 3840px.~~

#### A Note About setContentView Method Errors on Rename

Highlight 'R', select 'More Action...', select 'Import Class (com.your_name_space.your_project_name)'.

If 'your_name_space' and/or 'your_project_name' do not appear as importable package statements, 
then you'll need to resolve errors in 'setContentView()' method calls:

1. Sync project with Gradle files and check for errors (recurring task). 
2. Build the project again and look for more 'setContentView()' errors and any others (recurring task).
3. Reload AndroidStudio.
4. Repeat all above steps.

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
- [X] Refactor RecyclerView to display all Task Entities in the Database.

### Add a Task

- [X] The “Add a Task” page allows users to type in details about a new task.
- [X] Must show a title and a body.
- [X] When users click the “submit” button, show a “submitted!” label on the page.
- [X] Modify Add Task Form to save data entered as a Task in the local database.
- [X] Add a Spinner to select a Team to assign/associate a Task to.

### All Tasks

- [X] The 'all tasks' page should just be an image with a back button; it needs no functionality.

### Details Tasks

- [X] Ensure the description and status of a tapped task are also displayed on the detail page.
- [X] Ensure the Title is still displayed.
- [X] ~~Decide whether to pass the entire Entity or to~~ only pass ~~an ID~~ using an Intent.

### Implementing Room For Data Management

- [X] Set up Room in the App and modify the Task Class to be an Entity.

### Styling and Stretch Goals

Stretch goals:

- [X] Decide on a color scheme.
- [X] Decide font families. For now will stick with the built-in Android font-families.
- [X] Allow the user to specify on their settings page how many tasks should be shown on the homepage. Use this to dynamically create buttons for as many tasks as the user requests.
- [X] Also display the description of the task rather than Lorem Ipsum text.

## Screenshots

### Implementing Authentication with Cognito

A new user, or a user that has logged out will be brought to the Login/Register Activity:

![User can register or login](./app/screenshots/TaskMaster_CognitoLoggedOut_6July.png)

An unregistered user can begin the registration process from within the app, to gain access:

![User can self-register](./app/screenshots/TaskMaster_UserRegistration_Cognito6July.png)

An unregistered user can register their email and custom password, and provide an emailed verification code to gain access:

![User Registered and enteres correct Verification code](./app/screenshots/TaskMaster_NewUserVerificationCode_Cognito6July.png)

After registering an email and password and entering the verification code, AWS has an entry for the new user:

![User registered using verification code](./app/screenshots/TaskMaster_CognitoUserPool_UserRegStatus_6July.png)

Back at the TaskMaster App, the user can complete login after registration and verification:

![Registered User Can Login](./app/screenshots/TaskMaster_CongnitoLogin_6July.png)

Logged-on user has task list according to user's selected Team (Bravo in this case):

![Logged On Users Team Tasks](./app/screenshots/TaskMaster_CognitoLoggedIn_6July.png)

User can log-out at the MainActivity (Tasks List Screen) and will be brought back to the Login/Register Activity:

![User can register or login](./app/screenshots/TaskMaster_CognitoLoggedOut_6July.png)

### Preparing for Publication to Google Play Store

App Icon:

![App Icon in Apps](./app/screenshots/TaskMaster_AppIcon_5July.png)

Homescreen:

![Home screen](./app/screenshots/TaskMaster_MainActivityWithTasks_5July.png)

User Settings - Change Username:

![User Settings - Change Username](./app/screenshots/TaskMaster_UserSettingsPreSubmit_5July.png)

Homepage - With Custom Username:

![Home screen with custom name](./app/screenshots/TaskMaster_MainActivityWithName_5July.png)

Add Task:

![Adding a Task, assigning to a Team](./app/screenshots/TaskMaster_AddTask_Spinner_CloudyWeds.png)

Homepage - With New Task Displayed:

![Home screen with new Task, Team](./app/screenshots/TaskMaster_MainActivityWithTasks_5July.png)

Task Detail - Task Appears on-screen:

![Task Detail screen with new Task](./app/screenshots/TaskMaster_TaskDetailScreen_5July.png)

### AWS Amplify-connected Implementation

Homepage:

![](./app/screenshots/HomeActivity_CloudyWeds.png)

User Settings - Change User Name:

![](./app/screenshots/TaskMaster_UserSettings_CloudyWeds.png)

Homepage - User Name Sticks:

![](./app/screenshots/TaskMaster_MainActivity_UserSettings_CloudyWeds.png)

Add Task - Spinner With Task Status Selections:

![](./app/screenshots/TaskMaster_AddTask_Spinner_CloudyWeds.png)

Homepage - Updated After Adding A Task (note the status is included):

![](./app/screenshots/TaskMaster_UpdatedTaskList_CloudyWeds.png)

Task Detail - Task Detail with Status (as assigned):

![](./app/screenshots/TaskMaster_TaskDetail_CloudyWeds.png)

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

As a guide I referenced the Class Repository and this [Medium.com article](https://medium.com/@haxzie/using-intents-and-extras-to-pass-data-between-activities-android-beginners-guide-565239407ba0)

Additional *thanks!* go to code samurai and troubleshooting master [Roger Reyes](https://github.com/RogerMReyes)

More *thanks!* to [Raul Zarate](https://github.com/zaratr) for his input while debugging!
