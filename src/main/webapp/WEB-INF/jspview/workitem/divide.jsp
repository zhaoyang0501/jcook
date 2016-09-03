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
                        <h5>任务分配</h5>
                        <div class="ibox-tools">
                        </div>
                    </div>
                    <div class="ibox-content">
                        <div class="row">
                            <div class="col-sm-12 b-r">
			                           <form class="form-horizontal" action="${pageContext.request.contextPath}/workitem/doapprove/${task.id}/${prcessInstanceid}" method="post">
			                           	<input type="hidden" name="id" value="${bean.id }"/>
			                           	<input type="hidden" id="pass" name="pass" value="true"/>
			                           	<table class='table table-bordered'>
			                           		<thead>
			                           		<tr style="text-align: center;" ><td colspan="6" ><h3>任务详情<h3></h3></td></tr>
			                           		</thead>
			                           		<tbody>
			                           			<tr>
			                           				<td>任务标题</td>
			                           				<td> <input value="${bean.title }" required="required" name='title' type="text" class="form-control">	</td>
			                           			</tr>
			                           			<tr>
			                           				<td>任务说明</td>
			                           				<td> <textarea required="required" name='remark' rows="4" cols="" style="width: 100%" >${bean.remark }</textarea></td>
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
			                           				<td>分配给</td>
			                           				<td> 
						                                <select name='handleusers' class="chzn-select " multiple  data-placeholder="推荐组员" class="chosen-select" style="width:350px;" tabindex="2">
						                                	<c:forEach items="${users }" var="user">
						                                		<option value="${user.id }">${user.chinesename}</option>
						                                	</c:forEach>
						                                </select>
			                           				</td>
			                           			</tr>
			                           			
			                           			<tr>	
			                           				<td>组长</td>
			                           				<td> 
						                                <select  name='leader.id' required="required" class="form-control"  >
						                                	<c:forEach items="${users }" var="user">
						                                		<option value="${user.id }">${user.chinesename}</option>
						                                	</c:forEach>
						                                </select>
			                           				</td>
			                           			</tr>
			                           			
			                           			
			                           			<tr>	
			                           				<td>附件</td>
			                           				<td> 
								                        	<a href="#">XX.doc</a>
			                           				</td>
			                           			</tr>
			                           			
			                           			<tr>
			                           				<td>提示</td>
			                           				<td > 
			                           					 <h4>提示</h4>
			                               					 <ol>
										    					<li>点击分配,任务将会推送到推荐组员中，可以修改推荐组员以及组长</li>
										    				</ol>
			                           				</td>
			                           			</tr>
			                           		</tbody>
			                           	</table> 
			                           	
                                   </div>
		                           	
			                        <div class="col-sm-12 b-r">
			                           <h5>处理意见</h5>
											  <div class="form-group">
													 <label>意见</label>
														<textarea id="approvals" name="approvals" rows="2"  style="widows: 100%"  class="form-control"></textarea>
												</div>
										
												
												 <div class="form-group">
															    <button type="submit" class="btn btn-primary" onclick="$('#pass').val('true')">分配</button>
														
																<button type="submit" class="btn  btn-warning" onclick="$('#pass').val('false')">驳回</button>
													</div>
									
									</div>
									</form>
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
