<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	// 工具栏
	var toolbar = [ {
		id : 'button-search',	
		text : '查询',
		iconCls : 'icon-search',
		handler : doView
	}, {
		id : 'button-delete',
		text : '清除查询条件',
		iconCls : 'icon-undo',
		handler : doUndo
	}, {
		id : 'button-add',
		text : '新增',
		iconCls : 'icon-add',
		handler : doAdd
	}, {
		id : 'button-delete',
		text : '删除',
		iconCls : 'icon-cancel',
		handler : doDelete
	}];
	//定义冻结列
	var frozenColumns = [ [ {
		field : 'id',
		checkbox : true,
		rowspan : 2
	}, {
		field : 'username',
		title : '名称',
		width : 80,
		rowspan : 2
	} ] ];


	// 定义标题栏
	var columns = [ [ {
		field : 'gender',
		title : '性别',
		width : 60,
		rowspan : 2,
		align : 'center'
	}, {
		field : 'birthdayString',
		title : '生日',
		width : 100,
		rowspan : 2,
		align : 'center'
	}, {
		title : '其他信息',
		colspan : 2
	}, {
		field : 'telephone',
		title : '电话',
		width : 150,
		rowspan : 2,
		align : 'center'
	}, {
		field : 'roleNames',
		title : '对应角色',
		width : 500,
		rowspan : 2
	} ], [ {
		field : 'station',
		title : '单位',
		width : 100,
		align : 'center'
	}, {
		field : 'salary',
		title : '工资',
		width : 80,
		align : 'right'
	} ] ];
	$(function(){
		$("body").css({visibility:"visible"});
		// 初始化 datagrid
		// 创建grid
		$('#grid').datagrid( {
			iconCls : 'icon-forward',
			fit : true,
			border : false,
			rownumbers : true,
			striped : true,
			toolbar : toolbar,
			url : "userAction_pageQuery.action",
			pagination:true,
			pageList: [25,50,80],
			idField : 'id', 
			frozenColumns : frozenColumns,
			columns : columns,
			onDblClickRow : doDblClickRow
		});
		
		
		//查询用户的窗口
		$('#searchUserWindow').window({
	        title: '查询用户',
	        width: 400,
	        modal: true,//遮罩效果
	        shadow: true,
	        closed: true,//加载页面时不打开窗口
	        height: 400,
	        resizable:false//大小是否可调
	    });
		
	});
	// 双击
	function doDblClickRow(rowIndex, rowData) {
		location.href="${pageContext.request.contextPath}/page_admin_useredit.action?id="+rowData.id;
		console.info();
	}
	
	//新增
	function doAdd() {
		location.href="${pageContext.request.contextPath}/page_admin_userinfo.action";
	}
	
	//查询
	function doView() {
		$('#searchUserWindow').window("open");
	}
	//清除查询条件
	function doUndo() {
		$.post('userAction_pageQuery.action',function(data){
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
				location.href = "userAction_deleteBatch.action?ids="+ids;//手动跳转,将数据发送过去同时刷新页面
			}else{
				//取消
			}
		})
		}
	}
	
</script>
</head>
<body class="easyui-layout" style="visibility: hidden;">
	<div region="center" border="false">
		<table id="grid"></table>
	</div>

	<!-- 查询用户的窗口 -->
	<div class="easyui-window" title="查询用户" id="searchUserWindow" collapsible="false" minimizable="false"
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
					var p = $("#searchUserForm").serializeJson();//将查询表单中的输入内容转化为json
					$("#grid").datagrid("load", p);//用load方法发送ajax请求提交数据
					$('#searchUserWindow').window("close");//关闭查询窗口
				});
			});
		</script>
		<div region="center" style="overflow: auto; padding: 5px;" border="false">
			<form id="searchUserForm" action="userAction_search.action" method="post">
				<input type="hidden" name="id">
				<table class="table-edit" width="90%" align="center">
					<tr class="title">
						<td colspan="2">用户信息</td>
					</tr>
					<tr>
						<td>名称</td>
						<td>
							<input type="text" name="username" class="easyui-validatebox" />
						</td>
					</tr>
					<tr>
						<td>性别</td>
						<td>
							<input type="text" name="gender" class="easyui-validatebox" />
						</td>
					</tr>
					<tr>
						<td>单位</td>
						<td>
							<input type="text" name="station" class="easyui-validatebox" />
						</td>
					</tr>
					<tr>
						<td>工资</td>
						<td>
							<input type="text" name="salary" class="easyui-validatebox" />
						</td>
					</tr>
					<tr>
						<td>电话</td>
						<td>
							<input type="text" name="telephone" class="easyui-validatebox" />
						</td>
					</tr>
					<tr>
						<td>对应角色</td>
						<td>
							<input type="text" name="roleNames" class="easyui-validatebox" />
						</td>
						<td>只可查询一个</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	
</body>
</html>