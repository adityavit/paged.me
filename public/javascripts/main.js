$(function(){
    $("#form1").submit(function(){
        $.post('@{addStyle()}', $("#form1").serialize(), function(style){
            $(":input", "#form1").not(":submit").val("");
            $("ul").prepend('<li>' + style.field + '  :  ' + style.value + '</li>');
//            $("#posted").html("successfully posted!" + style.field + ", " + style.value);
        }, 'json');
//        $("#form1")[0].reset();
        return false;
    });
});
//FB.ui({
//    method: 'stream.publish',
//    message: 'geeez! shrrrts is cool :P',
//    attachment: {
//        name: 'shrrts.com',
//        caption: 'The New Shrrrts',
//        description: ('checkout the coolest new shrrrts place.'),
//        href: 'http://shrrts.com'
//    },
//    action_links: [{
//        text: 'shrrts.com',
//        href: 'http://shrrts.com'
//    }],
//    user_message_prompt: 'Share your thoughts about Connect'
//}, function(response){
//    if (response && response.post_id) {
//        alert('Post was published.');
//    }
//    else {
//        alert('Post was not published.');
//    }
//});
