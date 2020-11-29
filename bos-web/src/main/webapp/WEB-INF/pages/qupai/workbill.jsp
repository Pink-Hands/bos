<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>业务通知单</title>
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

<script type="text/javascript">
	
	function doRepeat(){
		var rows = $('#grid').datagrid("getSelections");
		if(rows.length == 0 ){
		}else{
			var array = new Array();//数组
			for(var i=0;i<rows.length;i++){
				var workbill = rows[i];
				var id = workbill.id;
				array.push(id);//将id加入数组
			}
			var ids = array.join(",");//用,连接数组
			location.href = "workbillAction_attachbill.action?ids="+ids;//手动跳转,将数据发送过去同时刷新页面
		}
	}
	
	function doCancel(){
		var rows = $('#grid').datagrid("getSelections");
		if(rows.length == 0 ){
		}else{
			$.messager.confirm("提示","确认对选中的工单进行销单吗",function(r){
			if(r){
				//确认
				var array = new Array();//数组
				for(var i=0;i<rows.length;i++){
					var workbill = rows[i];
					var id = workbill.id;
					array.push(id);//将id加入数组
				}
				var ids = array.join(",");//用,连接数组
				location.href = "workbillAction_deleteBatch.action?ids="+ids;//手动跳转,将数据发送过去同时刷新页面
			}else{
				//取消
			}
		})
		}
	}
	
	function doSearch(){
		$('#searchWindow').window("open");
	}
	function doUndo(){
		$.post('workbillAction_pageQuery.action',function(data){
			$("#grid").datagrid("load", data);//用load方法发送ajax请求提交数据
		});
	}
	
	//工具栏
	var toolbar = [ 
	<shiro:hasPermission name="workbill-search">
	{
		id : 'button-search',	
		text : '查询',
		iconCls : 'icon-search',
		handler : doSearch
	}, {
		id : 'button-undo',
		text : '清除查询条件',
		iconCls : 'icon-undo',
		handler : doUndo
	},
	</shiro:hasPermission>
	
	<shiro:hasPermission name="workbill-attachbill">
	{
		id : 'button-repeat',
		text : '追单',
		iconCls : 'icon-redo',
		handler : doRepeat
	}, 
	</shiro:hasPermission>
	
	<shiro:hasPermission name="workbill-delete">
	{
		id : 'button-cancel',	
		text : '销单',
		iconCls : 'icon-cancel',
		handler : doCancel
	}
	</shiro:hasPermission>
	];
	// 定义列
	var columns = [ [ {
		field : 'id',
		checkbox : true,
	}, {
		field : 'qpNoticebill.id',
		title : '通知单号',
		width : 230,
		align : 'center',
		formatter : function(data, row, index) {
			return row.qpNoticebill.id;
		}
	},{
		field : 'type',
		title : '工单类型',
		width : 100,
		align : 'center'
	}, {
		field : 'pickstate',
		title : '取件状态',
		width : 120,
		align : 'center'
	}, {
		field : 'buildtimeString',
		title : '工单生成时间',
		width : 150,
		align : 'center'
	}, {
		field : 'attachbilltimes',
		title : '追单次数',
		width : 80,
		align : 'center'
	}, {
		field : 'bcStaff.name',
		title : '取派员',
		width : 100,
		align : 'center',
		formatter : function(data, row, index) {
			return row.bcStaff.name;
		}
	}, {
		field : 'bcStaff.telephone',
		title : '取派员联系方式',
		width : 100,
		align : 'center',
		formatter : function(data, row, index) {
			return row.bcStaff.telephone;
		}
	} ] ];
	
	$(function(){
		// 先将body隐藏，再显示，不会出现页面刷新效果
		$("body").css({visibility:"visible"});
		
		// 收派标准数据表格
		$('#grid').datagrid( {
			iconCls : 'icon-forward',
			fit : true,
			border : true,
			rownumbers : true,
			striped : true,
			pageList: [30,50,100],
			pagination : true,
			toolbar : toolbar,
			url :  "workbillAction_pageQuery.action",
			idField : 'id',
			columns : columns,
			onDblClickRow : doDblClickRow
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
		
		// 查询分区
		$('#searchWindow').window({
	        title: '查询分区',
	        width: 400,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 400,
	        resizable:false
	    });
		$("#btn").click(function(){
			var p = $("#searchForm").serializeJson();//将查询表单中的输入内容转化为json
			$("#grid").datagrid("load", p);//用load方法发送ajax请求提交数据
			$("#searchForm").get(0).reset();// 重置查询表单
			$("#searchWindow").window("close"); // 关闭窗口
		});
	});

	function doDblClickRow(){
	}
</script>
</head>
<body class="easyui-layout" style="visibility: hidden;">
	<div region="center" border="false">
		<table id="grid"></table>
	</div>

	<!-- 查询分区 -->
	<div class="easyui-window" title="查询窗口" id="searchWindow" collapsible="false" minimizable="false" maximizable="false"
		style="top: 20px; left: 200px">
		<div style="overflow: auto; padding: 5px;" border="false">
			<form id="searchForm">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">查询条件</td>
					</tr>
					<tr>
						<td align="right">客户电话</td>
						<td>
							<input type="text" name="qpNoticebill.telephone" class="easyui-validatebox"/>
						</td>
					</tr>
					<tr>
						<td align="right">取派员</td>
						<td>
							<input type="text" name="bcStaff.name" class="easyui-validatebox"/>
						</td>
					</tr>
					<tr>
						<td align="right">受理时间：从</td>
						<td>
							<input type="text" name="buildtimestart" class="easyui-datetimebox"/>
						</td>
					</tr>
					<tr>
						<td align="right">到</td>
						<td>
							<input type="text" name="buildtimeend" class="easyui-datetimebox"/>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<a id="btn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>