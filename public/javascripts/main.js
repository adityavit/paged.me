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
