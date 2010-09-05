<div id="fb-root"></div>
<script src="http://connect.facebook.net/en_US/all.js"></script>
<script type="text/javascript">
FB.init({appId: '${FB_CLIENT}', status: true, cookie: true, xfbml: true});
FB.Event.subscribe('auth.sessionChange', function(response) {
  if (response.session) {
    window.location.reload();
    // A user has logged in, and a new cookie has been saved
  } else {
    // The user has logged out, and the cookie has been cleared
  }
});
