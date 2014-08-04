<!DOCTYPE html>
<html>
<head>
  <title>Video.js | HTML5 Video Player</title>

  <!-- Chang URLs to wherever Video.js files will be hosted -->
  <link href="video-js/video-js.css" rel="stylesheet" type="text/css">
  <!-- video.js must be in the <head> for older IEs to work. -->
  <script src="video-js/video.js"></script>

  <!-- Unless using the CDN hosted version, update the URL to the Flash SWF -->
  <script>
    videojs.options.flash.swf = "video-js/video-js.swf";
  </script>


</head>
<body>

  <video id="example_video_1" class="video-js vjs-default-skin" controls preload="none" width="608" height="336"
      poster="video.png"
      data-setup="{}">
    <source src="http://localhost:8080/base/rest/stream?digitalItemId=1&videoId=1" type='video/mp4' />
    <p class="vjs-no-js">To view this video please enable JavaScript, and consider upgrading to a web browser that <a href="http://videojs.com/html5-video-support/" target="_blank">supports HTML5 video</a></p>
  </video>

</body>
</html>
