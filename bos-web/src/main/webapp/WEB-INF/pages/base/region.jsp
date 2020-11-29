<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>区域设置</title>
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
	function doAdd() {
		$('#addRegionWindow').window("open");
	}

	function doView() {
		$('#searchRegionWindow').window("open");
	}
	function doUndo() {
		$.post('regionAction_pageQuery.action',function(data){
			$("#grid").datagrid("load", data);//用load方法发送ajax请求提交数据
		});
	}

	function doDelete() {
		var rows = $('#grid').datagrid("getSelections");
		if(rows.length == 0 ){
			//没选中,提示选择
			$.messager.alert("提示","请选择需要停用的取派员","warning");
		}else{
			//选中了,提示确定
			$.messager.confirm("提示","确认要停用选中的取派员吗",function(r){
			if(r){
				//确认
				var array = new Array();//数组
				for(var i=0;i<rows.length;i++){
					var staff = rows[i];
					var id = staff.id;
					array.push(id);//将id加入数组
				}
				var ids = array.join(",");//用,连接数组
				location.href = "regionAction_deleteBatch.action?ids="+ids;//手动跳转,将数据发送过去同时刷新页面
			}else{
				//取消
			}
		})
		}
	}

	//工具栏
	var toolbar = [ 
	<shiro:hasPermission name="region-search">
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
	
	<shiro:hasPermission name="region-add">
	{
		id : 'button-add',
		text : '增加',
		iconCls : 'icon-add',
		handler : doAdd
	},
	</shiro:hasPermission>
	
	<shiro:hasPermission name="region-import">	
	{
		id : 'button-import',
		text : '导入',
		iconCls : 'icon-redo'
	}, 
	</shiro:hasPermission>
	
	<shiro:hasPermission name="region-delete">
	{
		id : 'button-delete',
		text : '删除',
		iconCls : 'icon-cancel',
		handler : doDelete
	}
	</shiro:hasPermission>
	];
	// 定义列
	var columns = [ [ {
		field : 'id',
		checkbox : true,
	}, {
		field : 'province',
		title : '省',
		width : 120,
		align : 'center'
	}, {
		field : 'city',
		title : '市',
		width : 120,
		align : 'center'
	}, {
		field : 'district',
		title : '区',
		width : 120,
		align : 'center'
	}, {
		field : 'postcode',
		title : '邮编',
		width : 120,
		align : 'center'
	}, {
		field : 'shortcode',
		title : '简码',
		width : 120,
		align : 'center'
	}, {
		field : 'citycode',
		title : '城市编码',
		width : 200,
		align : 'center'
	} ] ];

	$(function() {
		// 先将body隐藏，再显示，不会出现页面刷新效果
		$("body").css({
			visibility : "visible"
		});

		// 收派标准数据表格
		$('#grid').datagrid({
			iconCls : 'icon-forward',
			fit : true,
			border : false,
			rownumbers : true,
			striped : true,
			pageList : [ 25, 50, 100 ],
			pagination : true,
			toolbar : toolbar,
			url : "regionAction_pageQuery.action",
			idField : 'id',
			columns : columns,
			onDblClickRow : doDblClickRow
		});

		// 添加区域窗口
		$('#addRegionWindow').window({
			title : '添加修改区域',
			width : 400,
			modal : true,
			shadow : true,
			closed : true,
			height : 400,
			resizable : false
		});
		
		// 修改区域窗口
		$('#editRegionWindow').window({
			title : '添加修改区域',
			width : 400,
			modal : true,
			shadow : true,
			closed : true,
			height : 400,
			resizable : false
		});

		//查询区域的窗口
		$('#searchRegionWindow').window({
	        title: '查询区域',
	        width: 400,
	        modal: true,//遮罩效果
	        shadow: true,
	        closed: true,//加载页面时不打开窗口
	        height: 400,
	        resizable:false//大小是否可调
	    });
		
		//导入按钮
		$("#button-import").upload({
			action:'regionAction_importXls.action',
			name:'regionFile'
		});
	});

	function doDblClickRow(rowIndex, rowData) {
		<shiro:hasPermission name="region-edit">
		$('#editRegionWindow').window("open");
		$('#editRegionForm').form("load",rowData); 
		</shiro:hasPermission>
	}
</script>
</head>
<body class="easyui-layout" style="visibility: hidden;">
	<div region="center" border="false">
		<table id="grid"></table>
	</div>
	<!-- 添加区域 -->
	<div class="easyui-window" title="区域添加" id="addRegionWindow" collapsible="false" minimizable="false" maximizable="false" style="top: 20px; left: 200px">
		<div region="north" style="height: 31px; overflow: hidden;" split="false" border="false">
			<div class="datagrid-toolbar">
				<a id="addsave" icon="icon-save" href="#" class="easyui-linkbutton" plain="true">保存</a>
			</div>
		</div>
		<script type="text/javascript">
			$(function(){
				//为保存按钮绑定事件
				$("#addsave").click(function(){
					var v = $("#addRegionForm").form("validate");
					if(v){
						$("#addRegionForm").submit();
					}
				});
			});
		</script>
		<div region="center" style="overflow: auto; padding: 5px;" border="false">
			<form id="addRegionForm" action="regionAction_add.action" method="post">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">区域信息</td>
					</tr>
					<tr>
						<td>id</td>
						<td>
							<input type="text" name="id" class="easyui-validatebox" required="true"/>
						</td>
					</tr>
					<tr>
						<td>省</td>
						<td>
							<input type="text" name="province" class="easyui-validatebox" required="true" />
						</td>
					</tr>
					<tr>
						<td>市</td>
						<td>
							<input type="text" name="city" class="easyui-validatebox" required="true" />
						</td>
					</tr>
					<tr>
						<td>区</td>
						<td>
							<input type="text" name="district" class="easyui-validatebox" required="true" />
						</td>
					</tr>
					<tr>
						<td>邮编</td>
						<td>
							<input type="text" name="postcode" class="easyui-validatebox" required="true" />
						</td>
					</tr>
					<tr>
						<td>简码</td>
						<td>
							<input type="text" name="shortcode" class="easyui-validatebox" required="true" />
						</td>
					</tr>
					<tr>
						<td>城市编码</td>
						<td>
							<input type="text" name="citycode" class="easyui-validatebox" required="true" />
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	
	<!-- 修改区域 -->
	<div class="easyui-window" title="区域修改" id="editRegionWindow" collapsible="false" minimizable="false" maximizable="false" style="top: 20px; left: 200px">
		<div region="north" style="height: 31px; overflow: hidden;" split="false" border="false">
			<div class="datagrid-toolbar">
				<a id="editsave" icon="icon-edit" href="#" class="easyui-linkbutton" plain="true">修改</a>
			</div>
		</div>
		<script type="text/javascript">
			$(function(){
				//为保存按钮绑定事件
				$("#editsave").click(function(){
					var v = $("#editRegionForm").form("validate");
					if(v){
						$("#editRegionForm").submit();
					}
				});
			});
		</script>
		<div region="center" style="overflow: auto; padding: 5px;" border="false">
			<form id="editRegionForm" action="regionAction_edit.action" method="post">
				<input type="hidden" name="id">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">区域信息</td>
					</tr>
					<tr>
						<td>省</td>
						<td>
							<input type="text" name="province" class="easyui-validatebox" required="true" />
						</td>
					</tr>
					<tr>
						<td>市</td>
						<td>
							<input type="text" name="city" class="easyui-validatebox" required="true" />
						</td>
					</tr>
					<tr>
						<td>区</td>
						<td>
							<input type="text" name="district" class="easyui-validatebox" required="true" />
						</td>
					</tr>
					<tr>
						<td>邮编</td>
						<td>
							<input type="text" name="postcode" class="easyui-validatebox" required="true" />
						</td>
					</tr>
					<tr>
						<td>简码</td>
						<td>
							<input type="text" name="shortcode" class="easyui-validatebox" required="true" />
						</td>
					</tr>
					<tr>
						<td>城市编码</td>
						<td>
							<input type="text" name="citycode" class="easyui-validatebox" required="true" />
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	
	<!-- 查询区域 -->
	<div class="easyui-window" title="区域修改" id="searchRegionWindow" collapsible="false" minimizable="false" maximizable="false" style="top: 20px; left: 200px">
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
				var p = $("#searchRegionForm").serializeJson();//将查询表单中的输入内容转化为json
				$("#grid").datagrid("load", p);//用load方法发送ajax请求提交数据
				$("#searchRegionForm").get(0).reset();// 重置查询表单
				$('#searchRegionWindow').window("close");//关闭查询窗口
			});
		});
		</script>
		<div region="center" style="overflow: auto; padding: 5px;" border="false">
			<form id="searchRegionForm" action="regionAction_search.action" method="post">
				<input type="hidden" name="id">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">区域信息</td>
					</tr>
					<tr>
						<td>省</td>
						<td>
							<input type="text" name="province" class="easyui-validatebox"/>
						</td>
					</tr>
					<tr>
						<td>市</td>
						<td>
							<input type="text" name="city" class="easyui-validatebox"/>
						</td>
					</tr>
					<tr>
						<td>区</td>
						<td>
							<input type="text" name="district" class="easyui-validatebox"/>
						</td>
					</tr>
					<tr>
						<td>邮编</td>
						<td>
							<input type="text" name="postcode" class="easyui-validatebox"/>
						</td>
					</tr>
					<tr>
						<td>简码</td>
						<td>
							<input type="text" name="shortcode" class="easyui-validatebox"/>
						</td>
					</tr>
					<tr>
						<td>城市编码</td>
						<td>
							<input type="text" name="citycode" class="easyui-validatebox"/>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>