<template>
   <div>
   <iframe src="https://www.purui.online:8000/index.html"  id="mobsf"  scrolling="no" frameborder="0" style="position:relative;top:0px;left: -50px;"></iframe>
   
   </div>
   </template>
   
   <script>
   export default {
   data () {
   return {
   }
   },
   mounted(){
   /**
   * iframe-宽高自适应显示
   */
   function changeMobsfIframe(){
   const mobsf = document.getElementById('mobsf');
   const deviceWidth = document.body.clientWidth;
   const deviceHeight = document.body.clientHeight;
   mobsf.style.width = (Number(deviceWidth)-114) + 'px'; 
   mobsf.style.height = (Number(deviceHeight)-78) + 'px'; 
   }
   
   changeMobsfIframe()
   
   window.onresize = function(){
   changeMobsfIframe()
   }
   },
   }
   </script>