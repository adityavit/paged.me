$(document).ready(function(){
  $('.navigator').live('mouseenter', function(){
    $(this).animate({height: '55px'}, 200)
  }).live('mouseleave', function(){
    $(this).animate({height: '50px'}, 200)
  });
  
  var curimg = 0, prefix = 'http://static.itsabt.me/folio/'
  imgs = [
    "uttara_0023c7bb731e639888bc5803ce67b9c583a69c95383e8c5a99b292af.png",
    "uttara_6d0ed5a61ea8f5b0b9f98d6f6746540b9f7a1f66b0e9c776d7a94025.png",
    "uttara_0b5457d6bc503e7be1cf06b7299657251b06f1ee3f684c6b26de7954.png",
    "uttara_72f3e64aff94ec8a91899cd2291816973198c3fb0a356a1ce9573bea.png",
    "uttara_118b96bb7e53c9c962faa283b0cd30605a04f723670bc9e70a60bf83.png",
    "uttara_8a34403f9d7d4579c75583ae9da99058cfe39ed62f129a5ba7d605c4.png",
    "uttara_13cb1da3244857c131cde472c6617258a2eb7a584b2e35fbe43467b5.png",
    "uttara_8f604cd961c3c34402c184f830e76861f458706916ed20ccaf9358cb.png",
    "uttara_1764a2a926a368844448da5d9eee35ce60b86d6968470f144a472d14.png",
    "uttara_970f19e8773f580f32c497629d6e458977349534b44614a20a7e608a.png",
    "uttara_31b232c8aa62019c6eb8852d7bd1dfc6560477d39d0827c14e216461.png",
    "uttara_9bce41c69ccb62382165af39c20874b30e967d0587cc29ed6814731b.png",
    "uttara_33dab40e7122efdbff5f2e12b3600822314cd407d369f897179cb8fb.png",
    "uttara_9fcfbb191e71a03f4650e11e84ce68fa1a4fef3ddb5ab1b6f141d061.png",
    "uttara_41915a43ddb032a9bc0ef1102f0dff299ced4eeb8aac7743f045e1fb.png",
    "uttara_ab004921d152824e393154608f6fbab36ebdd1236ef6fdd586d3a9d1.png",
    "uttara_47aea208d47e5650f4372eb4428e3bbd5a989ec98da93d2b4118a646.png",
    "uttara_c2692827cbd957701bb6400841b1cd2a8a27f3718bd6d10201b3ce71.png",
    "uttara_4c5d2f63e9fb47951e9e616237521b7f2f63bb4cb27821c852c97eba.png",
    "uttara_c88cef3a8a6bb453258e952b24be2e463264e1928e747698b8055331.png",
    "uttara_4c76918ee653840e4ec5a194ecaf9a2e933b85d6562dfeb1e988c032.png",
    "uttara_d0c0f90ea144d13c0d6f270dcf6fe095a3d6b92c80d1459a3d330c9b.png",
    "uttara_4fcc460c68114827ad396d45f926ec7c96c8330abf7541f3b393d613.png",
    "uttara_d42b0b2e93abd252026ab3b3459be1556597c817973ee20d3b6fe724.png",
    "uttara_50227ce9d88010f6e2c8e9b999d1ec0f4db489cf35bcc8690bfe0b80.png",
    "uttara_f992e816765b9e7adbe33e1883b8c28fbf5bfcd04b0abe6addb99f8f.png",
    "uttara_5a16bd246eb1c14f33b7feeaea0b57dcb004cc2820a13869cf8c3638.png",
    "uttara_fcaa74946c749a9bed13f2bb24558d9c7e8836980ce37f45883eba8c.png",
    "uttara_5e43567df51b307e69de1f5b7291459e844447ce5f6a3bb5a6cea166.png"
  ]
  
  $("img.workimg").attr("src", prefix + imgs[0]);
  
  $('body').bind('keydown', function(ev){
      var key = ev.keyCode || ev.which;
      var arrow = {
          'left': 37,
          'right': 39,
      };
      
      if (key == arrow.left && curimg > 0) {
        curimg = curimg - 1;
      }
      if (key == arrow.right && curimg < imgs.length - 1) {
        curimg = curimg + 1;
      }
      $("img.workimg").attr("src", prefix + imgs[curimg]);
  });
  
});

