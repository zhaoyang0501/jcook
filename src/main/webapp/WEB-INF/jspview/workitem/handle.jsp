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
	<link href="${pageContext.request.contextPath}/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/plugins/chosen/chosen.css" rel="stylesheet">
      <link href="${pageContext.request.contextPath}/css/plugins/toastr/toastr.min.css" rel="stylesheet">
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
							<li class="active">任务处理</li>
						</ul>
					</div>

				<div class="page-content">
			               <form class="form-horizontal" action="${pageContext.request.contextPath}/workitem/doapprove/${task.id}/${prcessInstanceid}" method="post">
						<div class="row">
                            <div class="col-sm-12 table-responsive ">
			                           	<input type="hidden" name="id" value="${bean.id }"/>
			                           	<input type="hidden" id="pass" name="pass" value="true"/>
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
		                           	
			                     <div class="col-sm-12 ">
				                        <div class="page-header">
											<h1>处理意见 </h1>
										</div>
										<form role="form" action="${pageContext.request.contextPath}/workitem/doapprove/${task.id}/${prcessInstanceid}" method="post">
											  <div class="form-group">
													 <label>结果说明</label>
														<textarea id="approvals" name="approvals" rows="2"  style="widows: 100%"  class="form-control"></textarea>
												</div>
											<div class="form-group">
													 <label>上传附件</label>
													<input name='file' type="file" class="form-control" />
												</div>
										
												
												 <div class="form-group">
															    <button type="submit" class="btn btn-primary" onclick="$('#pass').val('true')">提交结果</button>
												</div>
									</form>
									</div>	  
									
								
									
								
									
									 <div class="col-sm-12 table-responsive ">
									 <div class="page-header">
												<h1>流程信息 </h1>
											</div>
										<table id='dt_weekReport' class="responsive table table-striped table-bordered table-condensed">
											<thead>
												<tr>
													<th>流程节点</th>
													<th>处理人</th>
													<th>开始时间</th>
													<th>结束时间</th>
													<th>处理意见</th>
												</tr>
											</thead>
											<tbody>
											<c:forEach items="${taskhistory}" var="item">
													<tr>
													<td>${item.name}</td>
													<td>${item.user==null?"":item.user.chinesename}</td>
													<td> <fmt:formatDate value="${item.startTime}" type="time" dateStyle="full" pattern="yyyy-MM-dd HH:mm"/></td>
													<td> <fmt:formatDate value="${item.endTime}" type="time" dateStyle="full" pattern="yyyy-MM-dd HH:mm"/></td>
													<td>${item.approves}</td>
												</tr>
											</c:forEach>
											</tbody>
										</table>
		                          </div>
                        </div>
                        </form>
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
    
      <script src="${pageContext.request.contextPath}/js/plugins/chosen/chosen.jquery.js"></script>
    <script src="${pageContext.request.contextPath}/js/plugins/datapicker/bootstrap-datepicker.js"></script>
	  <script src="${pageContext.request.contextPath}/js/plugins/toastr/toastr.min.js"></script>
	</body>
	<script>
    $.common.setContextPath('${pageContext.request.contextPath}');
	<c:if test="${response.code=='1'}">
	  toastr.success('${response.msg}');
	</c:if>
    var table=null;
  
    
    $(document).ready(function(){
    	 $(".chzn-select").chosen({width:"100%"});
     	
     	$(".date").datepicker({
     		language:  'zh-CN',
 	        weekStart: 1,
 	        todayBtn:  1,
 	        format:'yyyy-mm-dd',
 			autoclose: 1,
 			todayHighlight: 1,
 			startView: 2,
 			minView: 2,
 			forceParse: 0
     		});
        });
    </script>
</html>
