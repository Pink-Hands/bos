<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>工作单批量导入</title>
<!-- 导入jquery核心类库 -->
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<!-- 导入easyui类库 -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/ext/portal.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/default.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/ext/jquery.portal.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/ext/jquery.cookie.js"></script>
<script src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.ocupload-1.1.2.js"></script>

<script type="text/javascript">
	$(function(){
		$("#grid").datagrid({
			url : 'workordermanageAction_pageQuery.action',
			toolbar : [
				{
					id : 'btn-search',
					text : '查找',
					iconCls : 'icon-search',
					handler : doSearch
				},{
					id : 'btn-refresh',
					text : '刷新',
					iconCls : 'icon-reload',
					handler : refresh
				},{
					id : 'btn-download',
					text : '模板下载',
					iconCls : 'icon-save',
					handler : function(){
						location.href = "workordermanageAction_exportXls.action";
					}
				},
				<shiro:hasPermission name="workordermanage-import">
				{
					id : 'btn-upload',
					text : '批量导入',
					iconCls : 'icon-redo'
				}  
				</shiro:hasPermission>
			],
			columns : [[
				{
					field : 'id',
					title : '编号',
					width : 80 ,
					align : 'center'
				},{
					field : 'arrivecity',
					title : '到达地',
					width : 120 ,
					align : 'center'
				},{
					field : 'product',
					title : '产品',
					width : 80 ,
					align : 'center'
				},{
					field : 'prodtimelimit',
					title : '产品时限',
					width : 90 ,
					align : 'center'
				},{
					field : 'prodtype',
					title : '产品类型',
					width : 90 ,
					align : 'center'
				},{
					field : 'sendername',
					title : '发件人姓名',
					width : 70 ,
					align : 'center'
				},{
					field : 'senderphone',
					title : '发件人电话',
					width : 120 ,
					align : 'center'
				},{
					field : 'senderaddr',
					title : '发件人地址',
					width : 250 ,
					align : 'center'
				},{
					field : 'receivername',
					title : '收件人姓名',
					width : 70 ,
					align : 'center'
				},{
					field : 'receiverphone',
					title : '收件人电话',
					width : 120 ,
					align : 'center'
				},{
					field : 'receiveraddr',
					title : '收件人地址',
					width : 250 ,
					align : 'center'
				},{
					field : 'actlweit',
					title : '实际重量',
					width : 60 ,
					align : 'center'
				}
			]],
			pageList: [10,20,30],
			pagination : true,
			striped : true,
			singleSelect: true,
			rownumbers : true,
			onDblClickRow : doDblClickRow,
			fit : true // 占满容器
		});
		
		// 一键上传
		$("#btn-upload").upload({
			 name: 'workordermanagerFile',
		     action: 'workordermanageAction_importXls.action',  // 提交请求action路径
		});
		
		// 查询或修改
		$('#searchoreditWindow').window({
			title : '查询或修改',
			width : 600,
			modal : true,
			shadow : true,
			closed : true,
			height : 400,
			resizable : false
		});
		
		//工具方法,将表单的输入数据转化为json格式
		$.fn.serializeJson = function() {
			var serializeObj = {};
			var array = this.serializeArray();
			$(array).each(
					function() {
						if (serializeObj[this.name]) {
							if ($.isArray(serializeObj[this.name])) {
								serializeObj[this.name].push(this.value);
							} else {
								serializeObj[this.name] = [
										serializeObj[this.name], this.value ];
							}
						} else {
							serializeObj[this.name] = this.value;
						}
					});
			return serializeObj;
		};
	});
	
	function doSearch() {
		$("#searchoreditForm").get(0).reset();
		$('#searchoreditWindow').window("open");
	}
	//修改数据
	function doDblClickRow(rowIndex, rowData) {
		<shiro:hasPermission name="workordermanage-edit">
		workordermanageid = rowData.id;
		//rowData为被双击目标的数据
		$('#searchoreditWindow').window("open");
		//load方法回显数据
		$('#searchoreditForm').form("load",rowData); 
		var arrayname = null;
		$.post('workordermanageAction_findAllId.action',function(dataname){//获取所有id
			arrayname = dataname.split(",");//用,分割取出数组
		});
		$.extend($.fn.validatebox.defaults.rules, {
			workorderid : {
				validator : function(value, param) {
					var result = true;
					for(var i=0;i<arrayname.length;i++){
						if(value!=workordermanageid && value==arrayname[i]){//排除当前id
							result = false;//如果id已被使用过
						}
					}
					return result;
				},
				message : '该id已被使用'
			}
		});
		</shiro:hasPermission>
	}
	
	function refresh(){
		$.post('workordermanageAction_pageQuery.action',function(data){
			$("#grid").datagrid("load", data);//用load方法发送ajax请求提交数据
		});					
	}
</script>
</head>
<body class="easyui-layout">
	<div region="center">
		<table id="grid"></table>
	</div>
	
	<!-- 修改分区 -->
	<div class="easyui-window" title="修改分区" id="searchoreditWindow" collapsible="false" minimizable="false"
		maximizable="false" style="top: 20px; left: 200px">
		<div style="height: 31px; overflow: hidden;" split="false" border="false">
			<div class="datagrid-toolbar">
				<a id="searchbut" icon="icon-search" href="#" class="easyui-linkbutton" plain="true">查找</a>
				<a id="editbut" icon="icon-edit" href="#" class="easyui-linkbutton" plain="true">修改</a>
				<script type="text/javascript">
					$(function() {
						//查找
						$("#searchbut").click(function() {
							var p = $("#searchoreditForm").serializeJson();//将查询表单中的输入内容转化为json
							$("#grid").datagrid("load", p);//用load方法发送ajax请求提交数据
							$('#searchoreditWindow').window("close");//关闭查询窗口
						});
						
						//修改
						$("#editbut").click(function() {
							$("input[name='oldId']").val(workordermanageid);
							var v = $("#searchoreditForm").form("validate");
							if (v) {
								//校验通过
								$("#searchoreditForm").submit();
							} else {
								//校验失败
								$.messager.alert("提示", "提交的内容不符合要求！", "error");
							}
						});
					});
				</script>
			</div>
		</div>

		<div style="overflow: auto; padding: 5px;" border="false">
			<form id="searchoreditForm" method="post" action="workordermanageAction_edit.action">
				<input type="hidden" name="oldId">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">分区信息</td>
					</tr>
					<tr>
						<td>编号</td>
						<td>
							<input type="text" name="id" data-options="validType:'workorderid'" class="easyui-validatebox"/>
						</td>
					</tr>
					<tr>
						<td>到达地</td>
						<td>
							<input type="text" name="arrivecity" class="easyui-validatebox"/>
						</td>
					</tr>
					<tr>
						<td>产品</td>
						<td>
							<input type="text" name="product" class="easyui-validatebox"/>
						</td>
					</tr>
					<tr>
						<td>产品时限</td>
						<td>
							<input type="text" name="prodtimelimit" class="easyui-validatebox"/>
						</td>
					</tr>
					<tr>
						<td>发件人电话</td>
						<td>
							<input type="text" name=senderphone class="easyui-validatebox"/>
						</td>
					</tr>
					<tr>
						<td>发件人地址</td>
						<td>
							<input type="text" name="senderaddr" class="easyui-validatebox" style="width: 250px;"/>
						</td>
					</tr>
					<tr>
						<td>收件人电话</td>
						<td>
							<input type="text" name="receiverphone" class="easyui-validatebox"/>
						</td>
					</tr>
					<tr>
						<td>收件人地址</td>
						<td>
							<input type="text" name="receiveraddr" class="easyui-validatebox"style="width: 250px;" />
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>