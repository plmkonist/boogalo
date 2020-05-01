This is a rhythm game. You choose a song and a difficulty and coroutines manage different components while you tap buttons to the beat.

[info]!
(demo/IMG_3263 (1).gif)

Currently the timing for when the buttons should pop up is hard coded in and only for 1 song. At times there are some issues whereby a clickable button is supposed to disappear but doesnt.

Upon launching the app, you are put into the Main Activity that has 2 buttons. 1 button leads to a song selections activity, and the other leads to the difficulty selection.

Once you choose a song, you can launch the difficulty activity. If you forgo song selection, you are given just the default song.

The difficulty (easy, medium, hard) correlates to how many buttons can appear on the screen at a time. 

After that. the game ends and reads you your score of how many buttons you clicked on the game over activity.


The most difficult part was using coroutines. Each button that appears and disappears after a set time is launched in its own GlobalScope thread, 
but UI can only be changed on the main thread, so when there are lot's of UI updates, the game might lag a little and I believe this is when a button is supposed to disappear from the screen but doesnt
In the future to avoid this problem, I will use a different library rather than buttons for the clickable objects.

I learned a lot about coroutines and synchronization through this project. For example, I learned I had to put a sleep method call in my main thread once the game was won so all the other 
existing threads could be destroyed properly

