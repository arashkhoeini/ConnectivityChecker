# ConnectivityChecker
An Open library for android to check for Internet connection.

If you do a lot of Internet Request in your Android application, and you need check for Internet connection every single time,
You probably gonna need this library. This is a very simple library which checks for Internet connection and if the user was connected to Internet, It will do the network task. Else it will show an AlertDialog. 

<h2>How to Use?</h2>
For using this library, you need to add this as module. To do so, first download it, then go to your project in android studio and select File >New >Import Module... 

<h3>Initializaion</h3>
  Right now there is only one class in this library, ConnectivityChecker.
  The class ConnectivityChecker has two constructor. The first one takes 2 arguments, and the second one takes 5 arguments. 
  The first constructor builds AlertDialog with default texts for message, retry button, and setting button. But the second constructor has three more arguments and takes these texts from you.
  
<h3>Checking for connection</h3>
The class ConnectivityChecker has a main method which you are going to use when sending internet requests, <i>doIfConnected(Doable task)</i>. This method takes one Doable object and check for Internet Connection. If user was connected, it performs the method <i>doTask()</i>. Else it will show the AlertDialog. 

<h3>Other Methods</h3>
<i>isDialogCancelable(Boolean cancelable)</i> : Alert Dialog is cancelable by default. If you want to change this behaviour, you can use this method.

<i>setDialogAlertMessage(String message)</i> : You can change alertDialog message by using this method.

<i>setCustomDialog(AlertDialog customDialog)</i> : If you want to use a different AlertDialog, You can use this method. 

<h2>Usage Example</h2>
```
Button b = (Button) findViewById(R.id.button);
b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityChecker cc = new ConnectivityChecker(getApplicationContext(),MainActivity.this, "Oops! No internet" , "Retry" , "Setting");
                cc.doIfConnected(new Doable() {
                    @Override
                    public void doTask() {
                        Toast.makeText(getApplicationContext(),"Doing Task.... ",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
```
