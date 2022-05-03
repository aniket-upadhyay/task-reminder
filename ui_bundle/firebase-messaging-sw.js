// Get registration token. Initially this makes a network call, once retrieved
// subsequent calls to getToken will return from cache.

importScripts('https://www.gstatic.com/firebasejs/7.23.0/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/7.23.0/firebase-messaging.js');
var firebaseConfig = {
    //Enter you configuration details
  };
  // Initialize Firebase
  firebase.initializeApp(firebaseConfig);

const messaging = firebase.messaging();
messaging.getToken({vapidKey : "<Enter Vapid Key>"})
.then((currentToken) => {
    if (currentToken) {
        //TODO: Send token to server
        console.log('Token is generated');
    } else {
      // Show permission request.
      console.log('No registration token available. Request permission to generate one.');
      // Show permission UI.
    }
  }).catch((err) => {
    console.log('An error occurred while retrieving token. ', err);
  });


  // If you would like to customize notifications that are received in the
// background (Web app is closed or not in browser focus) then you should
// implement this optional method.
// [START background_handler]
messaging.setBackgroundMessageHandler(function(payload) {
    console.log('[firebase-messaging-sw.js] Received background message ', payload);
    // Customize notification here
    const notificationTitle = 'Background Message Title';
    const notificationOptions = {
      body: 'Background Message body.'
    };
  
    return self.registration.showNotification(notificationTitle,
      notificationOptions);
  });