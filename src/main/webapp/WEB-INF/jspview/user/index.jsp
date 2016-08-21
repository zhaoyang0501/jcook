<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>H+ 后台主题UI框架 - 基本表单</title>
    <meta name="keywords" content="">
    <meta name="description" content="">
    <link rel="shortcut icon" href="favicon.ico">
     <link href="${pageContext.request.contextPath}/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/plugins/iCheck/custom.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/animate.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.min.css?v=4.1.0" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/css/plugins/dataTables/dataTables.bootstrap.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/css/plugins/toastr/toastr.min.css" rel="stylesheet">
</head>
<body >
    <div class="wrapper wrapper-content animated fadeInRight">
       <div class="row">
            <div class="col-sm-12">
                <div class="ibox ">
                  
                    <div class="ibox-title">
                        <h5>用户管理 </h5>
                        <div class="ibox-tools">
                        </div>
                    </div>
                    
                    <div class="ibox-content">
                        <form role="form" class="form-inline">
                            <div class="form-group">
                                <label for="exampleInputEmail2" class="sr-only">用户名</label>
                                <input type="text" placeholder="员工工号" id="_name" class="form-control">
                            </div>
                            <button class="btn btn-primary" type="button" id='_search'>查询</button>
                            <button class="btn btn-primary" type="button" id='_new'>新建</button>
                        </form>
                    </div>
                    
                    <div class="ibox-content">
                         <table ID='dt_table_view' class="table table-striped table-bordered table-hover dataTables-example">
                            <thead>
                                <tr>
									<th>id</th>
									<th>用户名</th>
									<th>真实姓名</th>
									<th>电话</th>
									<th>电子邮件</th>
									<th>性别</th>
									<th>备注</th>
									<th>删除</th>
								</tr>
                            </thead>
                       		 
                       		 <tbody>
                            </tbody>
                          </table>
                    </div>
                </div>
                
            </div>
        </div>
        
        
   </div>
   <div id='_form'>
       <div class="ibox-content">
  <div class="row">
                            <div class="col-sm-12 b-r">
		                           <form class="form-horizontal" action="${pageContext.request.contextPath}/admin/user/create" method="post">
		                           	<table class='table table-bordered'>
		                           		<thead>
		                           		<tr style="text-align: center;" ><td colspan="6" ><h3>审计人员信息登记表<h3></h3></td></tr>
		                           		</thead>
		                           		<tbody>
		                           			<tr>
		                           				<td>姓名</td>
		                           				<td> <input name='name' type="text" class="form-control"></td>
		                           			</tr>
		                           			
		                           			<tr>
		                           			<td>性别</td>
		                           				<td>
												  <label class='checkbox-inline'>
												    <input type="radio" name="sex"  value="男" checked>
												    	男
												   </label>
												   <label class='checkbox-inline'>
												    <input type="radio" name="sex"  value="女">
												 	   女
												  </label>
		                           				</td>
		                           			</tr>
		                           			
		                           			<tr>	
		                           				<td>出生日期</td>
		                           				<td> 
							                        <input name='birthDay' type="text" class="form-control input-group date" >
		                           				</td>
		                           			</tr>
		                           				
		                           			<tr>
		                           				<td>工号</td>
		                           				<td> <input name='username' type="text" class="form-control"></td>
		                           			</tr>
		                           			
		                           			<tr>
		                           				<td>电话</td>
		                           				<td> <input name='tel' type="text" class="form-control"></td>
		                           			</tr>
		                           			
		                           			
		                           			<tr>
		                           				<td>email</td>
		                           				<td> <input name='email'  type="text" class="form-control"></td>
		                           			</tr>
		                           			<tr>
		                           				<td>家庭地址</td>
		                           				<td> <input name='address' type="text" class="form-control"></td>
		                           				</tr>
		                           			<tr>
		                           				<td>工作单位</td>
		                           				<td> 
			                           				<input name='job' type="text" class="form-control">
												</td>
												</tr>
		                           			<tr>
												<td>政治面貌</td>
		                           				<td> <select name='party' class="form-control">
		                           						<option value='群众'>群众</option>
		                           						<option value='党员积极分子'>党员积极分子</option>
		                           						<option value='党员'>党员</option>
		                           					</select></td>
		                           			</tr>
		                           			
		                           			
		                           			<tr>
		                           				<td>角色</td>
		                           				<td > 
			                           				<select name='role' class="form-control">
		                           						<option value='审计人员'>审计人员</option>
		                           						<option value='组长'>组长</option>
		                           						<option value='管理员'>管理员</option>
		                           					</select>
												</td>
		                           			</tr>
		                           			
		                           			<tr>
		                           				<td>备注</td>
		                           				<td> 
		                           					<textarea name='remark' rows="4" cols="" style="width: 80%"></textarea>
		                           				</td>
		                           			</tr>
		                           			<tr>
		                           				<td>提示</td>
		                           				<td > 
		                           					 <h4>提示</h4>
		                               					 <ol>
									    					<li>新员工必须先登记</li>
									    				</ol>
		                           				</td>
		                           			</tr>
		                           			<tr>
		                           				<td colspan="6"> 
		                           					 <div class="col-sm-4 col-sm-offset-2">
		                                  			  		<button class="btn btn-primary" type="submit">提交</button>
		                                   				    <button class="btn btn-white" type="submit">取消</button>
		                               				 </div>
		                           				</td>
		                           			</tr>
		                           		</tbody>
		                           	</table> 
		                           	</form>
                            </div>
                        </div>
                        </div>
   </div>
    <script src="${pageContext.request.contextPath}/js/plugins/dataTables/jquery.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js?v=3.3.6"></script>
    <script src="${pageContext.request.contextPath}/js/content.min.js?v=1.0.0"></script>
    <script src="${pageContext.request.contextPath}/js/plugins/iCheck/icheck.min.js"></script>
     <script src="${pageContext.request.contextPath}/js/plugins/datapicker/bootstrap-datepicker.js"></script>
      
      <script src="${pageContext.request.contextPath}/js/plugins/layer/layer.js"></script>
    <!-- Data Tables -->
    <script src="${pageContext.request.contextPath}/js/plugins/dataTables/jquery.dataTables.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/plugins/dataTables/dataTables.bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/common.js?v=1.0.0"></script>
    <script src="${pageContext.request.contextPath}/js/plugins/toastr/toastr.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquerydatatable.defaults.js"></script>
    
    <script>
   	
    $.common.setContextPath('${pageContext.request.contextPath}');
        $(document).ready(function(){
        	$("#_new").click(function(){
        		layer.open({
        			  type: 1,
        			  skin: 'layui-layer-rim', //加上边框
        			  content: $("#_form"),
        			  area: "600px"
        			});
        	});
        	<c:if test="${state=='success'}">
	  		  toastr.success('${tip}');
	       </c:if>
        	var table=$('#dt_table_view').DataTable( {
	            "ajax": {
	                "url":  $.common.getContextPath() + "/user/list",
	                "type": "POST",
	                "dataSrc": "datas"
	              },
				"columns" : [{
					"data" : "id"
				}, {
					"data" : "username"
				},{
					"data" : "chinesename",
				},{
					"data" : "tel",
				},{
					"data" : "email",
				},{
					"data" : "sex",
				},{
					"data" : "remark",
				},{
					"data" : "id",
				}] ,
				 "columnDefs": [
				                {
				                    "render": function ( data, type, row ) {
				                        return "<a tager='_blank' href='delete?id="+data+"'>删除</a>";
				                    },
				                    "targets":8
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
</body>

</html>
