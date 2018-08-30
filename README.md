# ![CF](http://i.imgur.com/7v5ASc8.png) Map Tasks App

## Resources
* [Adding Maps](https://developer.android.com/training/maps/index.html)

## Feature Tasks
Create an app where users see a list of tasks, see whether each task
is complete or not, and can choose a task to view on a map with a
start and an end point.

You should manually add data to the Firebase database yourself. Your
app is not required to create the create tasks in the database. Your
app should have some visual indication whether a task is complete or
not, and allow users to toggle whether it's done and update the database.

Add a button that a user can press to center the map on their location.

Add a button that toggles between:
* Live-updating the map based on updates to the user location
* Disabling the live-updating of the map

## Reminders
You must check to see if the user has granted fine and course GPS permissions.
You must manually initiate the dialog that requests the user for permisions
(by using the requestPermissions() built-in method). After you properly check
to make sure you have proper permissions you may need to suppress a warning
because Android Studio can't properly tell that you're properly checked
permissions.

Remember that you should use the "android" `Manifest.permission.` and not the
one that's associated with your apps package. If you type
`Manifest.permission.` and the auto-complete doesn't offer many permission
options then delete and restart typing `Manifest` and make sure to choose the
`Manifest` associated with Android, not your app's package name.

## Stretch Goals
* Integrate with Firebase Auth to have users sign in
* Build another activity that allows users to create tasks with descriptions,
  start and end points on the map and saved to the datbase.

## Submission Instructions
* Work in a fork of this repository
* Work in a branch on your fork
* Write all of your code in a directory named `lab-` + `<your name>` **e.g.** `lab-susan`
* Open a pull request to this repository
* Submit on canvas a question and observation, how long you spent, and a link to
  your pull request
