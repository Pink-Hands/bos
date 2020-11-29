<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
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
<!-- 导入ztree类库 -->
<link rel="stylesheet" href="${pageContext.request.contextPath }/js/ztree/zTreeStyle.css" type="text/css" />
<script src="${pageContext.request.contextPath }/js/ztree/jquery.ztree.all-3.5.js" type="text/javascript"></script>
<script type="text/javascript">
	$(function() {
		// 数据表格属性
		$("#grid").datagrid({
			toolbar : [
			<shiro:hasPermission name="role-search">
				{
				id : 'button-search',	
				text : '查询',
				iconCls : 'icon-search',
				handler : doView
			}, {
				id : 'button-undo',
				text : '清除查询条件',
				iconCls : 'icon-undo',
				handler : doUndo
			}, 
			</shiro:hasPermission>
			
			<shiro:hasPermission name="role-add">
			{
				id : 'button-add',
				text : '添加角色',
				iconCls : 'icon-add',
				handler : function() {
					location.href = '${pageContext.request.contextPath}/page_admin_role_add.action';
				}
			} , 
			</shiro:hasPermission>
			
			<shiro:hasPermission name="role-delete">
			{
				id : 'button-delete',
				text : '删除',
				iconCls : 'icon-cancel',
				handler : doDelete
			}
			</shiro:hasPermission>
			
			],
			url : 'roleAction_pageQuery.action',
			pagination : true,
			onDblClickRow : doDblClickRow,
			fit : true,
			pageList: [25,50,80],
			columns : [ [ {
				field : 'code',
				title : '关键字',
				width : 200
			}, {
				field : 'name',
				title : '名称',
				width : 200
			}, {
				field : 'description',
				title : '描述',
				width : 200
			} ] ]
		});
		
		//查询角色的窗口
		$('#searchRoleWindow').window({
	        title: '查询用户',
	        width: 400,
	        modal: true,//遮罩效果
	        shadow: true,
	        closed: true,//加载页面时不打开窗口
	        height: 250,
	        resizable:false//大小是否可调
	    });
	});
	
	//修改
	function doDblClickRow(rowIndex, rowData) {
		<shiro:hasPermission name="staff-edit">
		location.href = '${pageContext.request.contextPath}/page_admin_role_edit.action?id='+rowData.id;
		</shiro:hasPermission>
	}
	
	//查询
	function doView() {
		$('#searchRoleWindow').window("open");
	}
	//清除查询条件
	function doUndo() {
		$.post('roleAction_pageQuery.action',function(data){
			$("#grid").datagrid("load", data);//用load方法发送ajax请求提交数据
		});
	}
	
	//删除
	function doDelete() {
		var rows = $('#grid').datagrid("getSelections");
		if(rows.length == 0 ){
			//没选中,提示选择
			$.messager.alert("提示","请选择需要删除的用户","warning");
		}else{
			//选中了,提示确定
			$.messager.confirm("提示","确认要删除选中的用户吗",function(r){
			if(r){
				//确认
				var array = new Array();//数组
				for(var i=0;i<rows.length;i++){
					var staff = rows[i];
					var id = staff.id;
					array.push(id);//将id加入数组
				}
				var ids = array.join(",");//用,连接数组
				location.href = "roleAction_deleteBatch.action?ids="+ids;//手动跳转,将数据发送过去同时刷新页面
			}else{
				//取消
			}
		})
		}
	}
</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'center'">
		<table id="grid"></table>
	</div>
	
	<!-- 查询角色的窗口 -->
	<div class="easyui-window" title="查询用户" id="searchRoleWindow" collapsible="false" minimizable="false"
		maximizable="false" style="top: 20px; left: 200px">
		<div region="north" style="height: 31px; overflow: hidden;" split="false" border="false">
			<div class="datagrid-toolbar">
				<a id="searchsave" icon="icon-search" href="#" class="easyui-linkbutton" plain="true">查询</a>
			</div>
		</div>
		<script type="text/javascript">
			$(function(){
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
				
				//为提交按钮绑定事件
				$("#searchsave").click(function(){
					var p = $("#searchRoleForm").serializeJson();//将查询表单中的输入内容转化为json
					$("#grid").datagrid("load", p);//用load方法发送ajax请求提交数据
					$("#searchRoleForm").get(0).reset();// 重置查询表单
					$('#searchRoleWindow').window("close");//关闭查询窗口
				});
			});
		</script>
		<div region="center" style="overflow: auto; padding: 5px;" border="false">
			<form id="searchRoleForm" action="roleAction_search.action" method="post">
				<input type="hidden" name="id">
				<table class="table-edit" width="90%" align="center">
					<tr class="title">
						<td colspan="2">角色信息</td>
					</tr>
					<tr>
						<td>关键字</td>
						<td>
							<input type="text" name="code" class="easyui-validatebox" />
						</td>
					</tr>
					<tr>
						<td>名称</td>
						<td>
							<input type="text" name="name" class="easyui-validatebox" />
						</td>
					</tr>
					<tr>
						<td>描述</td>
						<td>
							<input type="text" name="description" class="easyui-validatebox" />
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>