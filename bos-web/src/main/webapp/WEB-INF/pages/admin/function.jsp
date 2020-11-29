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
<script type="text/javascript">
	reloadpid = null;
	$(function(){
		$("#grid").datagrid({
			toolbar : [ 
			<shiro:hasPermission name="function-search">
			{
				id : 'button-search',
				text : '查询',
				iconCls : 'icon-search',
				handler : doSearch
			},{
				id : 'button-undo',
				text : '清除查询条件',
				iconCls : 'icon-undo',
				handler : doUndo
			},
			</shiro:hasPermission>
			
			<shiro:hasPermission name="function-add">
			{
				id : 'button-add',
				text : '添加',
				iconCls : 'icon-add',
				handler : function(){
				location.href='${pageContext.request.contextPath}/page_admin_function_add.action';
				}
			}, 
			</shiro:hasPermission>
			
			<shiro:hasPermission name="function-delete">
			{
				id : 'button-delete',
				text : '删除',
				iconCls : 'icon-cancel',
				handler : doDelete
			}
			</shiro:hasPermission>
			
			],
			url : 'functionAction_pageQuery.action',
			pagination:true,
			pageList: [25,50,80],
			onDblClickRow : doDblClickRow,
			fit:true,
			columns : [[
			  {
				  field : 'code',
				  title : '关键字',
				  width : 200
			  },
			  {
				  field : 'name',
				  title : '名称',
				  width : 200
			  },  
			  {
				  field : 'description',
				  title : '描述',
				  width : 200
			  },  
			  {
				  field : 'generatemenu',
				  title : '是否生成菜单',
				  width : 200,
				  formatter:function(data,row,index){
					  if(data == "1"){
						  return "是";
					  }else if(data == "0"){
						  return "否";
					  }
				  }
			  },  
			  {
				  field : 'zindex',
				  title : '优先级',
				  width : 200
			  },  
			  {
				  field : 'pageaction',
				  title : '路径',
				  width : 200
			  }
			]]
		});
		
		//查询权限的窗口
		$('#searchRoleWindow').window({
	        title: '查询用户',
	        width: 400,
	        modal: true,//遮罩效果
	        shadow: true,
	        closed: true,//加载页面时不打开窗口
	        height: 400,
	        resizable:false//大小是否可调
	    });
		
		// 修改权限窗口
		$('#editRoleWindow').window({
	        title: '修改权限',
	        width: 500,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 400,
	        resizable:false
	    });
	});
	
	//修改
	function doDblClickRow(rowIndex, rowData){
		<shiro:hasPermission name="function-edit">
		//rowData为被双击目标的数据
		$('#editRoleWindow').window("open");
		//load方法回显数据
		$('#editRoleForm').form("load",rowData);
		//获取父id
		reloadpid = rowData.pId;
		//回显combotree
		$("#ccccc").combotree('setValue', reloadpid);
		</shiro:hasPermission>
	}
	//查询
	function doSearch() {
		$('#searchRoleWindow').window("open");
	}
	//清楚查询条件
	function doUndo() {
		$.post('functionAction_pageQuery.action',function(data){
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
				location.href = "functionAction_deleteBatch.action?ids="+ids;//手动跳转,将数据发送过去同时刷新页面
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
	
	<!-- 查询权限的窗口 -->
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
					var p = $("#functionForm").serializeJson();//将查询表单中的输入内容转化为json
					$("#grid").datagrid("load", p);//用load方法发送ajax请求提交数据
					$("#functionForm").get(0).reset();// 重置查询表单
					$('#searchRoleWindow').window("close");//关闭查询窗口
				});
			});
		</script>
		<div region="center" style="overflow: auto; padding: 5px;" border="false">
			<form id="functionForm" method="post" action="functionAction_search.action">
			<input type="hidden" name="id">
			<table class="table-edit" width="80%" align="center">
				<tr class="title">
					<td colspan="2">功能权限信息</td>
				</tr>
				<tr>
					<td width="200">关键字</td>
					<td>
						<input type="text" name="code" class="easyui-validatebox"/>
					</td>
				</tr>
				<tr>
					<td>名称</td>
					<td>
						<input type="text" name="name" class="easyui-validatebox"/>
					</td>
				</tr>
				<tr>
					<td>访问路径</td>
					<td>
						<input type="text" name="pageaction" class="easyui-validatebox"/>
					</td>
				</tr>
				<tr>
					<td>是否生成菜单</td>
					<td>
						<select name="generatemenu" class="easyui-combobox">
							<option value="2">忽略</option>
							<option value="3">不生成</option>
							<option value="4">生成</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>描述</td>
					<td>
						<textarea name="description" rows="2" cols="25"></textarea>
					</td>
				</tr>
			</table>
		</form>
		</div>
	</div>
	
	<!-- 修改权限的窗口 -->
	<div class="easyui-window" title="查询用户" id="editRoleWindow" collapsible="false" minimizable="false"
		maximizable="false" style="top: 20px; left: 200px">
		<div region="north" style="height: 31px; overflow: hidden;" split="false" border="false">
			<div class="datagrid-toolbar">
				<a id="editsave" icon="icon-edit" href="#" class="easyui-linkbutton" plain="true">修改</a>
			</div>
		</div>
		<script type="text/javascript">
			$(function(){
				//为修改按钮绑定事件
				$('#editsave').click(function(){
					var v = $('#editRoleForm').form("validate");
					if (v) {
						$('#editRoleForm').submit();
					} else {
						$.messager("提示","不符合规则","warning");
					}
				});
			});
		</script>
		<div region="center" style="overflow: auto; padding: 5px;" border="false">
			<form id="editRoleForm" method="post" action="functionAction_edit.action">
			<input type="hidden" name="id">
			<table class="table-edit" width="80%" align="center">
				<tr class="title">
					<td colspan="2">功能权限信息</td>
				</tr>
				<tr>
					<td width="200">关键字</td>
					<td>
						<input type="text" name="code" class="easyui-validatebox" data-options="required:true" />
					</td>
				</tr>
				<tr>
					<td>名称</td>
					<td>
						<input type="text" name="name" class="easyui-validatebox" data-options="required:true" />
					</td>
				</tr>
				<tr>
					<td>访问路径</td>
					<td>
						<input type="text" name="pageaction" />
					</td>
				</tr>
				<tr>
					<td>是否生成菜单</td>
					<td>
						<select name="generatemenu" class="easyui-combobox">
							<option value="0">不生成</option>
							<option value="1">生成</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>优先级</td>
					<td>
						<input type="text" name="zindex" class="easyui-numberbox" data-options="required:true" />
					</td>
				</tr>
				<tr>
					<td>父功能点</td>
					<td>
						<input id="ccccc" name="parentFunction.id" class="easyui-combotree" data-options="url:'functionAction_listajax.action'"
							style="width: 170px;" />
					</td>
				</tr>
				<tr>
					<td>描述</td>
					<td>
						<textarea name="description" rows="2" cols="25"></textarea>
					</td>
				</tr>
			</table>
		</form>
		</div>
	</div>
</body>
</html>