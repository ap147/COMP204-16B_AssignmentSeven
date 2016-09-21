COMP204-16B / COMP242-16B Assignment 7
======================================

Due on **Wednesday, 28^(th) September at 11:30pm**.


Android Drawing and Interactions
================================

The goal of this exercise is to become accustomed to drawing in Android, and to gain more experience in using sensors.

Android is described in the [documentation supplied by Google, found here](https://developer.android.com/index.html).


Preamble
========

1. Fork this repository using the button at the top of the page.
  * Set the visibility level of the project to Private.
2. Clone your new repository to your computer using Git.
3. Remember to commit and push regularly!


Overview
========

You will be building an application that draws an object to the screen that interacts
with results from the Accelerometer, as well as user interaction through the use of
touch and drag inputs. The task is loosely specified, but relatively simple as it 
builds on projects demonstrated during class sessions. Leverage the Panopto recordings 
and lecture notes to help you address the problem.


Task
====

* To begin, create a new project in your cloned repository
  * Use API level 23 (Marshmallow)
  * Name your project **BallApp**
  * Start with a blank activity
* Add the project, and a suitable gitignore file to your repository
  * Commit and push your code to your repository, remember to do this regularly
* Begin by implementing a DrawableView as demonstrated in the Friday 16^(th) lecture session
  * Replace the layout with an instance of your DrawableView
  * Add some content to the view such as a simple bouncing cube to ensure that it is working and drawing as you expect
* Update your code so that the application is full screen (Leaving the home/back/switch buttons visible is acceptable)
* Update your code so that your application is locked in **portrait**
  * Test this by rotating the AVD. The application should not freeze and rotate if you have done this right
* Add some text to the top-left corner of your view 
  * Style the text so that it is readable by setting it to a reasonable size and color
  * Every time your view redraws, increment a counter and display this in your view using text
  * This should produce an incrementing counter
* Introduce a circle to your view
  * Style it so that it is large enough that you can comfortably select it with a finger
  * Give it a nice contrasting color
* Update your code so that on each draw, the ball is moved on the screen based on the gravitational force exerted on the device
  * The ball should not pass the edges of the screen
  * Ensure that the ball 'falls' in the correct direction by rotating the AVD. The ball should always fall to the bottom of the virtual window
* Update your code so that it is aware of touch interactions (down, move, up)
  * Modify your code so that when the ball is pressed by a finger, it is 'picked up', and 'dropped' when the finger is lifted from the screen
  * If the finger is dragged on the screen while the ball is picked up, the ball should move to the position of the finger
  * When the ball is picked up, it should not be affected by gravitational force. When it is dropped, it should return to normal behavior
* Modify your code so that when the ball is picked up, it changes color and increases in size
  * When it is dropped, it should return to its original size and color

  

Submission
==========

Upload a zipped copy of your repository to [moodle in the assignment submission](https://elearn.waikato.ac.nz/mod/assign/view.php?id=573556). 
Please download the zip from GitLab using the download button in the top right 
of the project view rather than zipping it from the copy on your local hard drive.


Grading
=======

| Weighting | Allocated to |
|:---------:|--------------|
| 10% | Correct repository usage and settings |
| 90% | Task, allocated based on completion and correctness |
