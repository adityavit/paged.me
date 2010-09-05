$(document).ready(function(){

    var plants = [];
    function plant() {
        this.px = Math.floor(Math.random() * 890) + 10;
        this.py = Math.floor(Math.random() * 490) + 10;
        this.el = $('<div>').addClass('plant').appendTo('body')
                    .css('top', this.py + 'px')
                    .css('left', this.px + 'px')
                    .css('display', 'block');
    }

    var snakes = [];
    function snake() {
        this.dr = 'up';
        this.snkblks = [];
        var skblk = new snkblk();
        skblk.el.removeClass('box').addClass('head');
        this.snkblks.push(skblk);
        
    }

    function snkblk() {
        this.el = $('<div>').addClass('box').appendTo('body').css('display', 'block');
        this.xx = parseInt(this.el.css('left').match(/\d*/)[0]);
        this.yy = parseInt(this.el.css('top').match(/\d*/)[0]);
        this.oxx = 0;
        this.oyy = 0;
    }

    function init() {
        var pt;

        //make a new snake
        var ske = new snake();
        
        //add the snake to list of snakes
        snakes.push(ske);

        for (i=0; i<10; i++){
            pt = new plant();
            plants.push(pt);
        }
    }

    init();

    $('body').bind('keydown', function(ev){
        var key = ev.keyCode || ev.which;
        var arrow = { 'left':37, 'right':39, 'up':38, 'down':40 };
        
        var snk = snakes[0].snkblks[0];

        for (j=0;j<snakes.length;j++){
        var snke = snakes[j];
        var snkblks = snke.snkblks;

        var moved = false;

        for (k=0;k<snkblks.length;k++){
        var snk = snkblks[k];
        var el = snk.el;
        xx = snk.xx;
        yy = snk.yy;
        
        if (k == 0) {
        if (key == arrow.left && xx - 10 > 0 && snke.dr !== 'right' || snakes.length != 1) {
            xx = xx - 10;
            el.css('left', xx + "px" );
            moved = true;
            snke.dr = 'left';
        }
        if (key == arrow.right && xx + 10 < 900 && snke.dr !== 'left' || snakes.length != 1) {
            xx = xx + 10;
            el.css('left', xx + "px" );
            moved = true;
            snke.dr = 'right';
        }
        if (key == arrow.up && yy - 10 > 0 && snke.dr !== 'down' || snakes.length != 1) {
            yy = yy - 10;
            el.css('top', yy + "px" );
            snke.dr = 'up';
            moved = true;
        }
        if (key == arrow.down && yy + 10 < 600 && snke.dr !== 'up' || snakes.length != 1) {
            yy = yy + 10;
            el.css('top', yy + "px" );
            moved = true;
            snke.dr = 'down';
        }
        if (moved == false ) return;
        snk.oxx = snk.xx;
        snk.oyy = snk.yy;
        snk.xx = xx;
        snk.yy = yy;
        
        //plants.forEach(function(pt){
        for (i=0; i<plants.length; i++){
            var pt = plants[i];
            var xd = Math.abs(pt.px - xx);
            var yd = Math.abs(pt.py - yy);
            if (xd < 10 && yd < 10){
                $(pt.el).remove();
                plants.splice(i, 1);
                var skblk = new snkblk();
                skblk.xx = snkblks[snkblks.length - 1].oxx;
                skblk.yy = snkblks[snkblks.length - 1].oyy;
                snkblks.push(skblk);
       //     pt = new plant();
         //   plants.push(pt);
            }
        };
        } else {
            var snk = snkblks[k];
            var snk1 = snkblks[k-1];
            var el = snk.el;
            snk.oxx = snk.xx;
            snk.oyy = snk.yy;
            snk.xx = snk1.oxx;
            snk.yy = snk1.oyy;

            el.css('top', snk.yy + "px" );
            el.css('left', snk.xx + "px" );
        }

        }
        }
    });
});
