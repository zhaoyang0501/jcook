<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
     <%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title></title>
    <link rel="shortcut icon" href="favicon.ico">
     <link href="${pageContext.request.contextPath}/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/plugins/iCheck/custom.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/animate.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.min.css?v=4.1.0" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/plugins/toastr/toastr.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/plugins/chosen/chosen.css" rel="stylesheet">
    <style type="text/css">
    
    </style>
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
       <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>任务详情</h5>
                        <div class="ibox-tools">
                        </div>
                    </div>
                    <div class="ibox-content">
                        <div class="row">
                            <div class="col-sm-12 b-r">
			                           <form class="form-horizontal" action="${pageContext.request.contextPath}/workitem/create" method="post">
			                           	<table class='table table-bordered'>
			                           		<thead>
			                           		<tr style="text-align: center;" ><td colspan="6" ><h3>任务详情<h3></h3></td></tr>
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
								                        	<a href="#">XX.doc</a>
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
			                           	</form>
                                   </div>
		                           	
									   <div class="col-sm-12 b-r">
									     <h5>流程信息</h5>
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
                    </div>
                </div>
            </div>
        </div>
        
        
   </div>
    <script src="${pageContext.request.contextPath}/js/jquery.min.js?v=2.1.4"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js?v=3.3.6"></script>
    <script src="${pageContext.request.contextPath}/js/content.min.js?v=1.0.0"></script>
      <script src="${pageContext.request.contextPath}/js/plugins/chosen/chosen.jquery.js"></script>
    <script src="${pageContext.request.contextPath}/js/plugins/datapicker/bootstrap-datepicker.js"></script>
    <script src="${pageContext.request.contextPath}/js/plugins/toastr/toastr.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/common.js?v=1.0.0"></script>
    
    <script>
		$.common.setContextPath('${pageContext.request.contextPath}');
		<c:if test="${response.code=='1'}">
	  		  toastr.success('${response.msg}');
	    </c:if>
		
		
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
</body>

</html>
