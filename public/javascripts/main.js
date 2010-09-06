$(document).ready(function(){
  $("body.prakhar a.profile").live('click', function(){
    $("body.prakhar div.contact").fadeOut(600, function(){
      $("body.prakhar div.profile").fadeIn(1500);
    });
  });
  $("body.prakhar a.contact").live('click', function(){
    $("body.prakhar div.profile").fadeOut(600, function(){
      $("body.prakhar div.contact").fadeIn(1500);
    });
  });
});

