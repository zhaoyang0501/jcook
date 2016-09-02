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
                        <h5>待办任务 </h5>
                        <div class="ibox-tools">
                        </div>
                    </div>
                    
                    <div class="ibox-content">
                        <form role="form" class="form-inline">
                            <div class="form-group">
                                <label for="exampleInputEmail2" class="sr-only">事项名称</label>
                                <input type="text" placeholder="事项名称" id="_name" class="form-control">
                            </div>
                            <button class="btn btn-primary" type="button" id='_search'>查询</button>
                        </form>
                    </div>
                    
                    <div class="ibox-content">
                         <table ID='dt_table_view' class="table table-striped table-bordered table-hover dataTables-example">
                            <thead>
                                <tr>
									<th>事项名称</th>
									<th>收到日期</th>
									<th>提交人</th>
									<th>当前步骤</th>
									<th>状态</th>
									<th>操作</th>
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
    <script src="${pageContext.request.contextPath}/js/jquerydatatable.defaults.js?sf=1"></script>
    <script>
    <c:if test="${state=='success'}">
	  toastr.success('${tip}');
	 </c:if>
    $.common.setContextPath('${pageContext.request.contextPath}');
    
    var table=null;
    
    function submit_form(){
    	$.ajax({
    		   type: "POST",
    		   url:  $.common.getContextPath() + "/user/save",
    		   data: $("form").serialize(),
    		   success: function(msg){
    		     if(msg.code==1){
    		    	 toastr.success('操作成功');
    		    	 table.draw();
    		     }
    		     layer.closeAll() ;
    		   }
    		});
     }
    
    function fun_delete(id){
    	$.ajax({
 		   url:  $.common.getContextPath() + "/user/delete?id="+id,
 		   success: function(msg){
 		     if(msg.code==1){
 		    	 toastr.success('操作成功');
 		    	 table.draw();
 		     }
 		     layer.closeAll() ;
 		   }
 		});
     }
    
    function fun_update(id){
    	$.ajax({
 		   url:  $.common.getContextPath() + "/user/get?id="+id,
 		   success: function(msg){
 		     if(msg.code==1){
 		    	$("input[name='id']").val(msg.datas.id);
 		    	$("input[name='chinesename']").val(msg.datas.chinesename);
 		    	$("radio[name='sex']").val(msg.datas.sex);
 		   		$("input[name='username']").val(msg.datas.username);
 				$("input[name='tel']").val(msg.datas.tel);
 				$("input[name='email']").val(msg.datas.email);
 				$("textarea[name='remark']").val(msg.datas.remark);
 		    	layer.open({
      			  type: 1,
      			  skin: 'layui-layer-rim', 
      			  content: $("#_form"),
      			  area: "800px"
      			});
 		     }
 		   }
 		});
     }
    
    $(document).ready(function(){
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
	            "ajax": {
	                "url":  $.common.getContextPath() + "/workflow/tasktodolist",
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
				                        return "<a tager='_blank' href='javascript:void(0)' onclick='fun_delete("+data+")'>签收 </a>"+
				                        "<a tager='_blank' href='javascript:void(0)' onclick='fun_update("+data+")'>处理</a>";
				                    },
				                    "targets":5
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
