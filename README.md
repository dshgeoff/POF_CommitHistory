# POF_CommitHistory

## Objective
Create an Android app written in Java to show commit histories for a particular repository on GitHub. 
Behaviour and UI
The app should consist of one Activity with two Fragments (list and detail view)
Each list item should show the committer's image and some details for a commit.


The date and login field should continuously animate between showing the date and the login (username). The animation should be a cross-fade animation with duration of 500ms, with pauses of 1000ms after each i.e. 1000ms showing the username, 500ms cross-fade to showing the date, 1000ms showing the date, 500ms cross-fade to showing the username again, and so on.


The list items should be clickable. Once an item is clicked:
- The selected list item should change its background to green.
- The detail view should open at the bottom of the screen, showing the details about the commit.
- The detail view should be dismissible with the X button. Once dismissed, the selected list item becomes unselected and the item background reverts to its original colour.
- The committer's image in the detail view should be clickable and should open the associated URL in an external web browser on the device.
- When the detail view is not visible, the list view should fill the whole screen. When the detail view is visible, it should have a fixed height of 250dp.



## Requirements and Notes
- You must write your app in Java.
- Your completed project must import into Android Studio and build successfully.
- You do not need to support screen rotation.
- You may use any third-party libraries you wish (for image loading, networking, threading etc).
- Do not cut and paste heavily from examples or repos online as we want to see original code that you have written.
- Your app must use two Android Fragments, one for the list view and one for the detail view.
- We recommend that you use the following GitHub Endpoint: https://api.github.com/repos/definitelytyped/definitelytyped/commits. This endpoint uses “definitelytyped/definitelytyped” repository which has plenty of commit history, but feel free to use a different repository if you prefer as long as it shows a variety of commits from several different committers. Note: the GitHub API does not require an auth token unless you make too many requests (5000+ per day). If you run into this problem, you can get a free auth token to increase your limit from here: https://developer.github.com/v3/#rate-limiting
- For the list items, you should use the following fields from the GitHub API response:
  - .commit.committer.date
  - .commit.message
  - .committer.login
  - .committer.avatar_url
- For the detail view, you should use the following fields from the GitHub API response:
  - .commit.committer.date
  - .commit.message
  - .committer.login
  - .committer.html_url
  - .committer.avatar_url
  - .sha
- We recommend a maximum of 10 hours for this exercise.

## Bonus Credit

Feel free to attempt any of these extra tasks if you have time:

- Add some animation for when the detail view is shown and hidden.
- Place the detail view fragment in an Android bottom sheet so that it can be dragged up and down.
- Use the GitHub API endpoint returned in field .committer.url (an example URL looks like https://api.github.com/users/GITHUBUSERNAME) to fetch the date when the committer’s account was created (field .created_at) and show this in the detail view too.
- Instead of cross-fading between the date and username for every list item, only animate every other item (e.g. items 1, 3, 5 etc.).
