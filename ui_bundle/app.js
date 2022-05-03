// Your web app's Firebase configuration
  // For Firebase JS SDK v7.20.0 and later, measurementId is optional
  var firebaseConfig = {
    //Enter you configuration details
  };
  // Initialize Firebase
  firebase.initializeApp(firebaseConfig);
  firebase.analytics(); 
  // Get registration token. Initially this makes a network call, once retrieved
// subsequent calls to getToken will return from cache.
const messaging = firebase.messaging();
messaging.requestPermission()
.then(function(){
    return messaging.getToken();
})
.then(function(token) {
  console.log(token);
  localStorage.setItem('fcmToken', token);
 
})
.catch(function(err) {
    console.log("Error! ", err);
})


messaging.onMessage((payload) => {
    console.log('Message received. ', payload);
    notificationTitle = payload.notification.title;
    notificationOptions = {
    body: payload.notification.body,
    
    };
    var notification = new Notification(notificationTitle,notificationOptions);
  });