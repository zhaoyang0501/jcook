<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
     <%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<html lang="en">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta charset="utf-8" />
		<title></title>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
		<!-- bootstrap & fontawesome -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/font-awesome/4.5.0/css/font-awesome.min.css" />
		<!-- page specific plugin styles -->
		<!-- text fonts -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/fonts.googleapis.com.css" />
		<!-- ace styles -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/ace.min.css" class="ace-main-stylesheet" id="main-ace-style" />
		<!--[if lte IE 9]>
			<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/ace-part2.min.css" class="ace-main-stylesheet" />
		<![endif]-->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/ace-skins.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/ace-rtl.min.css" />
		<!--[if lte IE 9]>
		  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/ace-ie.min.css" />
		<![endif]-->
		<!-- inline styles related to this page -->
		<!-- ace settings handler -->
		<script src="${pageContext.request.contextPath}/assets/js/ace-extra.min.js"></script>
		<!-- HTML5shiv and Respond.js for IE8 to support HTML5 elements and media queries -->
		<!--[if lte IE 8]>
		<script src="${pageContext.request.contextPath}/assets/js/html5shiv.min.js"></script>
		<script src="${pageContext.request.contextPath}/assets/js/respond.min.js"></script>
		<![endif]-->
	</head>

	<body class="no-skin">
		<%@include file="../top.jsp" %>
		<div class="main-container ace-save-state" id="main-container">
		<%@include file="../menu.jsp" %>
			<div class="main-content">
					<div class="breadcrumbs ace-save-state" id="breadcrumbs">
						<ul class="breadcrumb">
							<li>
								<i class="ace-icon fa fa-home home-icon"></i>
								<a href="#">首页</a>
							</li>
							<li class="active" targeturl='${pageContext.request.contextPath}/workflow/taskdone' >任务详情</li>
						</ul>
					</div>

				<div class="page-content">
						 <div class="row">
						 <div class="col-sm-12">
							 <ul class="steps"> 
										<li data-step="1" class="active">
											<span class="step">1</span>
											<span class="title">提交任务</span>
										</li>
	
										<li data-step="2">
											<span class="step">2</span>
											<span class="title">分配任务</span>
										</li>
	
										<li data-step="3">
											<span class="step">3</span>
											<span class="title">处理任务</span>
										</li>
	
										<li data-step="4">
											<span class="step">4</span>
											<span class="title">任务结束</span>
										</li>
								</ul>
						 </div>
						 
                            <div class="col-sm-12 table-responsive ">
			                           	<table class='table table-bordered'>
			                           		<thead>
			                           		<tr  ><th style="text-align: center;" colspan="2" >任务详情</th></tr>
			                           		</thead>
			                           		<tbody>
			                           			<tr>
			                           				<td>任务标题</td>
			                           				<td> ${bean.title }	</td>
			                           			</tr>
			                           			<tr>
			                           				<td>任务说明</td>
			                           				<td> ${bean.remark }</td>
			                           			</tr>
			                           			
			                           			<tr>	
			                           				<td>任务拟开展日期</td>
			                           				<td> 
								                        	<fmt:formatDate value="${bean.beginDate }" pattern="yyyy-MM-dd"/>
			                           				</td>
			                           			</tr>
			                           				
			                           			<tr>	
			                           				<td>任务拟结束日期</td>
			                           				<td> 
			                           				     	<fmt:formatDate value="${bean.endDate }" pattern="yyyy-MM-dd"/>
			                           			
			                           				</td>
			                           			</tr>
			                           			<tr>	
			                           				<td>附件</td>
			                           				<td> 
								                        	<c:forEach items="${bean.files }" var="file">
								                        		<a href="${pageContext.request.contextPath}/upload/${file }">${file }</a>
								                        		<br/>
								                        	</c:forEach>	
			                           				</td>
			                           			</tr>
			                           			<tr>	
			                           				<td>推荐组员</td>
			                           				<td> 
						                                <c:forEach items="${bean.applyusers }" var="bean">
						                                	${bean.chinesename }&nbsp;&nbsp;&nbsp;
						                                </c:forEach>
			                           				</td>
			                           			</tr>
			                           			<tr>	
			                           				<td>实际任务处理人</td>
			                           				<td> 
						                                <c:forEach items="${bean.handleusers }" var="bean">
						                                	${bean.chinesename }&nbsp;&nbsp;&nbsp;
						                                </c:forEach>
			                           				</td>
			                           			</tr>
			                           			<tr>	
			                           				<td>组长</td>
			                           				<td> 
						                                ${bean.leader.chinesename }
			                           				</td>
			                           			</tr>
			                           			
			                           			
			                           			
			                           			
			                           			<tr>
			                           				<td>管理员处理意见</td>
			                           				<td> 
						                                	${bean.approve}
			                           				</td>
			                           			</tr>
			                           		</tbody>
			                           	</table> 
                                   </div>
		                           	
									   <div class="col-sm-12 table-responsive">
									     <h5>流程信息</h5>
											<%@include file="../workflow/history.jsp" %>
		                          </div>
                        </div>
				</div>
				<!-- /.page-content -->
			</div><!-- /.main-content -->
			<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
				<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
			</a>
		</div><!-- /.main-container -->

		<!--[if !IE]> -->
		<script src="${pageContext.request.contextPath}/assets/js/jquery-2.1.4.min.js"></script>

		<!-- <![endif]-->

		<!--[if IE]>
		<script src="${pageContext.request.contextPath}/assets/js/jquery-1.11.3.min.js"></script>
		<![endif]-->
		<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='${pageContext.request.contextPath}/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
		<script src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>

		<!-- page specific plugin scripts -->

		<!--[if lte IE 8]>
		  <script src="${pageContext.request.contextPath}/assets/js/excanvas.min.js"></script>
		<![endif]-->
		<script src="${pageContext.request.contextPath}/assets/js/jquery-ui.custom.min.js"></script>
		<script src="${pageContext.request.contextPath}/assets/js/jquery.ui.touch-punch.min.js"></script>
		<!-- ace scripts -->
		<script src="${pageContext.request.contextPath}/assets/js/ace-elements.min.js"></script>
		<script src="${pageContext.request.contextPath}/assets/js/ace.min.js"></script>
  <!-- Data Tables -->
    <script src="${pageContext.request.contextPath}/js/plugins/dataTables/jquery.dataTables.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/plugins/dataTables/dataTables.bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/common.js?v=1.0.0"></script>
    <script src="${pageContext.request.contextPath}/js/plugins/toastr/toastr.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquerydatatable.defaults.js?sf=1"></script>
	</body>
	<script>
    $.common.setContextPath('${pageContext.request.contextPath}');
    <c:if test="${response.code=='1'}">
	  toastr.success('${response.msg}');
	</c:if>
    var table=null;
  
    
    $(document).ready(function(){
    	
    	if("${task.name}"=='提交任务'){
    		$(".steps li").removeClass("active");
    		$(".steps li").eq(0).addClass("active");
    	}
    	else if("${task.name}"=='分配任务'){
    		$(".steps li").removeClass("active");
    		$(".steps li").eq(0).addClass("active");
    		$(".steps li").eq(1).addClass("active");
    	}else if("${task.name}"=='处理任务'){
    		$(".steps li").removeClass("active");
    		$(".steps li").eq(0).addClass("active");
    		$(".steps li").eq(1).addClass("active");
    		$(".steps li").eq(2).addClass("active");
    	}else{
    		$(".steps li").removeClass("active");
    		$(".steps li").eq(0).addClass("active");
    		$(".steps li").eq(1).addClass("active");
    		$(".steps li").eq(2).addClass("active");
    		$(".steps li").eq(3).addClass("active");
    	}
    	
        	$("#_new").click(function(){
        		$("input[name='id']").val("");
 		    	$("input[name='chinesename']").val("");
 		    	$("radio[name='sex']").val("");
 		   		$("input[name='username']").val("");
 				$("input[name='tel']").val("");
 				$("input[name='email']").val("");
 				$("textarea[name='remark']").val("");
        		layer.open({
        			  type: 1,
        			  skin: 'layui-layer-rim', //加上边框
        			  content: $("#_form"),
        			  area: "800px"
        			});
        	});
        	table=$('#dt_table_view').DataTable( {
        		"dom": "rt<'row'<'col-sm-5'i><'col-sm-7'p>>",
	            "ajax": {
	                "url":  $.common.getContextPath() + "/workflow/taskdonelist",
	                "type": "POST",
	                "dataSrc": "datas"
	              },
				"columns" : [{
					"data" : "title"
				}, {
					"data" : "createDate"
				},{
					"data" : "creater.chinesename",
				},{
					"data" : "stepName",
				},{
					"data" : "state",
				},{
					"data" : "id",
				}] ,
				 "columnDefs": [
				                {
				                    "render": function ( data, type, row ) {
				                        return  "<a tager='_blank'  href='${pageContext.request.contextPath}/workflow/taskhistory/"+row.processInstanceId+"' >详情</a>";
				                    },
				                    "targets":5
				                },
				                {
				                    "render": function ( data, type, row ) {
				                        return  "<span class='label label-primary'>"+data+"</span>";
				                    },
				                    "targets":3
				                },
				                {
				                    "render": function ( data, type, row ) {
				                    	if(data=='进行中')
				                       	 return  "<span class='label label-primary'>"+data+"</span>";
				                        else
				                        	return  "<span class='label label-danger'>"+data+"</span>";
				                    },
				                    "targets":4
				                }
				               
				            ],
        		"initComplete": function () {
        			var api = this.api();
        			$("#_search").on("click", function(){
            		 	api.draw();
        			} );
        		} 
        	 } ).on('preXhr.dt', function ( e, settings, data ) {
		        	data.name = $("#_name").val();
		        	return true;
		     } ).on('xhr.dt', function ( e, settings, json, xhr ) {
		    		 $(".dataTables_processing").hide();	
		     } )
        });
    </script>
</html>
