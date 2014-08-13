jQuery(document).ready(function($){"use strict";if($('#myTab').length){$('#myTab a').click(function(e){e.preventDefault();$(this).tab('show');});}
if($("[data-toggle='tooltip']").length){$("[data-toggle='tooltip']").tooltip();}
$('#carousel').flexslider({animation:"slide",controlNav:false,animationLoop:false,slideshow:false,itemWidth:182,itemMargin:0,asNavFor:'#slider'});$('#slider').flexslider({animation:"slide",controlNav:false,animationLoop:false,slideshow:true,sync:"#carousel"});if($(".mycarousel").length){$('.mycarousel').jcarousel({wrap:'circular'});}
if($('.gallery_video').length){$(".gallery_video a[rel^='prettyPhoto']").prettyPhoto({animation_speed:'slow',slideshow:10000,hideflash:true});}
window.onload=function(){$('.latest-vidios').each(function()
{$(this).jScrollPane({showArrows:$(this).is('.arrow')});var api=$(this).data('jsp');var throttleTimeout;$(window).bind('resize',function()
{if(!throttleTimeout){throttleTimeout=setTimeout(function()
{api.reinitialise();throttleTimeout=null;},50);}});})}});document.createElement('header');document.createElement('hgroup');document.createElement('nav');document.createElement('menu');document.createElement('section');document.createElement('article');document.createElement('aside');document.createElement('footer');