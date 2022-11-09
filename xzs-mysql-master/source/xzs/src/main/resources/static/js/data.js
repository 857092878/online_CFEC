$(document).ready(function(){
	// 用户日志
	$.ajax({
	    url:"http://localhost:8000/api/chart/userlog",
	    type:"POST",
	    data:"",
	    success: function(data){
			// console.log(data);
			// console.log("================");
			var ul_list = $(".ul_list ");
			ul_list.empty();
			
			for(var i=0;i<data.length;i++){
			
				ul_list.append(
				`
				<div class="ul_listIn">
				    <ul class="ul_con">
				        <li id="userlog1">`+ data[i].content+`</li>
				        <li id="userlog2">`+ data[i].createTime +`</li>
				    </ul>
				</div>
				`
				)
			}
			},
	    error: function(){
			console.log("错误")
		}
	});
	
	// 试题数
	$.ajax({
	    url:"http://localhost:8000/api/chart/testNum",
	    type:"POST",
	    data:"",
	    success: function(data){
			$("#shu1").text(data.examPaperCount);
			$("#shu2").text(data.questionCount);
			$("#shu3").text(data.doExamPaperCount);
			$("#shu4").text(data.doQuestionCount);			
			},
	    error: function(){
			console.log("错误")
		}
	});
	
	// // 用户活跃度
	// $.ajax({
	//     url:"http://localhost:8000/api/chart/userActivity",
	//     type:"POST",
	//     data:"",
	//     success: function(data){
	// 		console.log(data);
	// 		// console.log(data.index);
	// 		},
	//     error: function(){
	// 		console.log("错误")
	// 	}
	// });
	
	// $.ajax({
	//     url:"http://localhost:8000/api/chart/questionGrade",
	//     type:"POST",
	//     data:"",
	//     success: function(data){
	// 		// console.log("成功");
	// 		console.log(data);
	// 		option.xAxis.data = data.grade;
	// 		option.series[1].data = data.count;
	// 		var myChart = echarts.init(document.getElementById('chartmain'));
	// 		// 使用刚指定的配置项和数据显示图表。
	// 		myChart.setOption(option);
	// 		},
	//     error: function(){
	// 		console.log("错误")
	// 	}
	// });
	
	// // 答题错误率排行
	// $.ajax({
	//     url:"http://localhost:8000/api/chart/userActivity",
	//     type:"POST",
	//     data:"",
	//     success: function(data){
	// 		console.log(data);
	// 		// console.log(data.index);
	// 		},
	//     error: function(){
	// 		console.log("错误")
	// 	}
	// });
	
	// 学科题目排行
	// $.ajax({
	//     url:"http://localhost:8000/api/chart/subjectNum",
	//     type:"POST",
	//     data:"",
	//     success: function(data){
	// 		console.log(data);
	// 		// console.log(data.index);
	// 		},
	//     error: function(){
	// 		console.log("错误")
	// 	}
	// });
	
	// 用户监听器
	$.ajax({
	    url:"http://localhost:8000/api/chart/userListener",
	    type:"POST",
	    data:"",
	    success: function(data){
			 $('.shu.shu11').text(data.loginByToday);
			 $('.shu.shu22').text(data.answerByMonth);
			 $('.shu.shu33').text(data.adminCount);
			 $('.shu.shu44').text(data.loginByMonth);
			 $('.shu.shu55').text(data.allAnswer);
			 $('.shu.shu66').text(data.userCount);
			},
	    error: function(){
			console.log("错误")
		}
	});
	
	// 学生做题排行
	$.ajax({
	    url:"http://localhost:8000/api/chart/userCorrect",
	    type:"POST",
	    data:"",
	    success: function(data){
			$(".userTest").empty();
			$(data).each(function(){
				$(".userTest").append(`
				<li>
					<div class="liIn">
				    	<div class="liIn_left"><span class="bot"></span><span class="zi">`+this.username+`</span></div>
				        <div class="liIn_line"><div class="line_lineIn" style="width:98.5%;"></div></div>
				        <p class="num">`+this.correct+`</p>
				    </div>
				</li>
				`)
			})		
			},
	    error: function(){
			console.log("错误")
		}
	});
})