$(document).ready(function(){

    var plants = [];
    function plant(){
        this.type = Math.floor(Math.random() * 3);
        this.px = Math.floor(Math.random() * 890) + 10;
        this.py = Math.floor(Math.random() * 490) + 10;

        this.el = $('<div>').addClass('plant').appendTo('body').css('top', this.py + 'px').css('left', this.px + 'px').css('display', 'block');
        if (this.type == 0){
            //eat reds; and you die.
            this.el.css('background', 'red');
        }
        if (this.type == 1){
            //eat blue; +5 pts.
            this.el.css('background', 'blue');
        }
        if (this.type == 2){
            //eat yellow +10 pts.
            this.el.css('background', 'yellow');
        }

    }
    
    var snakes = [];
    function snake(){
        this.score = 0;
        this.dr = 'right';
        this.snkblks = [];
        
        var skblk = new snkblk();
        skblk.el.removeClass('box').addClass('head');
        this.snkblks.push(skblk);
    }
    
    function snkblk(){
        this.el = $('<div>').addClass('box').appendTo('body').css('display', 'block');
        this.xx = parseInt(this.el.css('left').match(/\d*/)[0]);
        this.yy = parseInt(this.el.css('top').match(/\d*/)[0]);
        this.oxx = 0;
        this.oyy = 0;
    }
    
    function init(){
        var pt;
        
        //make a new snake
        var ske = new snake();
        
        //add the snake to list of snakes
        snakes.push(ske);
        
        for (i = 0; i < 10; i++) {
            pt = new plant();
            plants.push(pt);
        }
    }
    
    function scoreboard() {
      $('<div>').addClass('scorebd').appendTo('body').text("use arrow keys to control your snake! eat red plants.").fadeOut(3000);
      $('.scorebd').live('mouseover', function(){
        $(this).css('height', '33px');
      }).live('mouseout', function(){
        $(this).css('height', '30px');
      });
    }
    
    init();
    scoreboard();
    
    var arrow = {
        'left': 37,
        'right': 39,
        'up': 38,
        'down': 40
    };
    
    function movesnake(snke, dr){
        var snkblks = snke.snkblks;
        var moved = false;
        
        for (k = 0; k < snkblks.length; k++) {
            var snk = snkblks[k];
            var el = snk.el;
            var xx = snk.xx;
            var yy = snk.yy;
            
            if (k == 0) {
                if (dr == 'left' && xx - 10 > 0 && snke.dr !== 'right' || snakes.length != 1) {
                    xx = xx - 10;
                    moved = true;
                    snke.dr = 'left';
                }
                if (dr == 'right' && xx + 10 < 900 && snke.dr !== 'left' || snakes.length != 1) {
                    xx = xx + 10;
                    moved = true;
                    snke.dr = 'right';
                }
                if (dr == 'up' && yy - 10 > 0 && snke.dr !== 'down' || snakes.length != 1) {
                    yy = yy - 10;
                    snke.dr = 'up';
                    moved = true;
                }
                if (dr == 'down' && yy + 10 < 600 && snke.dr !== 'up' || snakes.length != 1) {
                    yy = yy + 10;
                    moved = true;
                    snke.dr = 'down';
                }
                
                if (moved == false) 
                    return;
                
                el.css('left', xx + "px");
                el.css('top', yy + "px");
                snk.oxx = snk.xx;
                snk.oyy = snk.yy;
                snk.xx = xx;
                snk.yy = yy;
                
                //plants.forEach(function(pt){
                for (i = 0; i < plants.length; i++) {
                  var pt = plants[i];
                  var xd = Math.abs(pt.px - xx);
                  var yd = Math.abs(pt.py - yy);
                  if (xd < 10 && yd < 10) {
                      $(pt.el).remove();
                    
                      ptyp = pt.type;

                      if (ptyp == 1)
                        snke.score = snke.score + 5;

                      if (ptyp == 2)
                        snke.score = snke.score + 10;

                      if (ptyp == 0) {
                        //you die.
                        var blk = 0;
                        for(i=0; i<snkblks.length; i++){
                          blk = snkblks[i];
                          $(blk.el).fadeOut();
                        };
                        snakes.splice(0, 1);
                        $('.scorebd').text('game over! you are dead : ' + snke.score + ', refresh to play again.').fadeIn();
                        return;
                      }
                    
                      plants.splice(i, 1);
                      $('.scorebd').text('your score : ' + snke.score).fadeIn();

                      for (i=0; i<ptyp; i++) {
                        var skblk = new snkblk();
                        skblk.xx = snkblks[snkblks.length - 1].oxx;
                        skblk.yy = snkblks[snkblks.length - 1].oyy;
                        snkblks.push(skblk);
                        pt = new plant();
                        plants.push(pt);
                      }
                    }
                };
            } else {
              var snk = snkblks[k];
              var snk1 = snkblks[k - 1];
              var el = snk.el;
              snk.oxx = snk.xx;
              snk.oyy = snk.yy;
              snk.xx = snk1.oxx;
              snk.yy = snk1.oyy;
              
              el.css('top', snk.yy + "px");
              el.css('left', snk.xx + "px");
            }
        }
    }
  
  //attach timer to keep moving.
  setInterval(function(){
    var snke = snakes[0];
    movesnake(snke, snke.dr);
  }, 500);
  
  $('body').bind('keydown', function(ev){
      var key = ev.keyCode || ev.which;
      
      //moving only the first snake.
      var snke = snakes[0], dr = '';
      
      if (key == arrow.left)  dr = 'left';
      if (key == arrow.right) dr = 'right';
      if (key == arrow.up)    dr = 'up';
      if (key == arrow.down)  dr = 'down';
      
      movesnake(snke, dr);
  });
});
