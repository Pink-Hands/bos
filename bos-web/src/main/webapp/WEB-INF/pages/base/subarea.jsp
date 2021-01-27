<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理分区</title>
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
<script src="${pageContext.request.contextPath }/js/highcharts/highcharts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.ocupload-1.1.2.js"></script>

<script type="text/javascript">
	//增加分区
	function doAdd() {
		$('#addSubareaWindow').window("open");
	}
	
	//删除分区
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
				location.href = "subareaAction_deleteBatch.action?ids="+ids;//手动跳转,将数据发送过去同时刷新页面
			}else{
				//取消
			}
		})
		}
	}
	
	//查询分区
	function doSearch() {
		$('#searchWindow').window("open");
	}
	
	//清楚查询条件
	function doUndo() {
		$.post('subareaAction_pageQuery.action',function(data){
			$("#grid").datagrid("load", data);//用load方法发送ajax请求提交数据
		});
	}
	
	//页面导出按钮
	function doExport() {
		//发送请求,弹出保存对话框
		window.location.href = "subareaAction_exportXls.action";
	}

	//工具栏
	var toolbar = [ 
	<shiro:hasPermission name="subarea-search">
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
	
	<shiro:hasPermission name="subarea-add">
	{
		id : 'button-add',
		text : '增加',
		iconCls : 'icon-add',
		handler : doAdd
	},
	</shiro:hasPermission>
	
	<shiro:hasPermission name="subarea-delete">
	{
		id : 'button-delete',
		text : '删除',
		iconCls : 'icon-cancel',
		handler : doDelete
	}, 
	</shiro:hasPermission>
	
	<shiro:hasPermission name="subarea-import">
	{
		id : 'button-import',
		text : '导入',
		iconCls : 'icon-redo',
	}, 
	</shiro:hasPermission>
	
	<shiro:hasPermission name="subarea-export">
	{
		id : 'button-export',
		text : '导出',
		iconCls : 'icon-undo',
		handler : doExport
	}, 
	</shiro:hasPermission>
	
	{
		id : 'button-showHighcharts',
		text : '区域分区分布图',
		iconCls : 'icon-search',
		handler : doShowHighcharts
	}];
	// 定义列
	var columns = [ [ {
		field : 'id',
		checkbox : true,
	}, {
		field : 'showid',
		title : '分拣编号',
		width : 120,
		align : 'center',
		formatter : function(data, row, index) {
			return row.id;
		}
	}, {
		field : 'province',
		title : '省',
		width : 120,
		align : 'center',
		formatter : function(data, row, index) {
			if(row.bcRegion == null){
				return "空";
			}else{
				return row.bcRegion.province;
			} 
		}
	}, {
		field : 'city',
		title : '市',
		width : 120,
		align : 'center',
		formatter : function(data, row, index) {
			if(row.bcRegion == null){
				return "空";
			}else{
				return row.bcRegion.city;
			} 
		}
	}, {
		field : 'district',
		title : '区',
		width : 120,
		align : 'center',
		formatter : function(data, row, index) {
			if(row.bcRegion == null){
				return "空";
			}else{
				return row.bcRegion.district;
			} 
		}
	}, {
		field : 'addresskey',
		title : '关键字',
		width : 120,
		align : 'center'
	}, {
		field : 'startnum',
		title : '起始号',
		width : 100,
		align : 'center'
	}, {
		field : 'endnum',
		title : '终止号',
		width : 100,
		align : 'center'
	}, {
		field : 'single',
		title : '单双号',
		width : 100,
		align : 'center'
	}, {
		field : 'position',
		title : '位置',
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
			border : true,
			rownumbers : true,
			striped : true,
			pageList : [ 25, 50, 100 ],
			pagination : true,
			toolbar : toolbar,
			url : "subareaAction_pageQuery.action",
			idField : 'id',
			columns : columns,
			onDblClickRow : doDblClickRow
		});

		// 添加分区
		$('#addSubareaWindow').window({
			title : '添加分区',
			width : 600,
			modal : true,
			shadow : true,
			closed : true,
			height : 400,
			resizable : false
		});
		
		// 修改分区
		$('#editSubareaWindow').window({
			title : '修改分区',
			width : 600,
			modal : true,
			shadow : true,
			closed : true,
			height : 400,
			resizable : false
		});
		
		//区域分区分布图
		$('#showSubareaWindow').window({
			width : 650,
			modal : true,
			shadow : true,
			closed : true,
			height : 500,
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

		// 查询分区
		$('#searchWindow').window({
			title : '查询分区',
			width : 400,
			modal : true,
			shadow : true,
			closed : true,
			height : 400,
			resizable : false
		});
		
		$("#btn").click(function() {
			var p = $("#searchForm").serializeJson();//将查询表单中的输入内容转化为json
			$("#grid").datagrid("load", p);//用load方法发送ajax请求提交数据
			$("#searchForm").get(0).reset();// 重置查询表单
			$('#searchWindow').window("close");//关闭查询窗口
		});
		
		//导入按钮
		$("#button-import").upload({
			action:'subareaAction_importXls.action',
			name:'subareaFile',
		});
	});
	
	//修改分区
	function doDblClickRow(rowIndex, rowData) {
		<shiro:hasPermission name="staff-edit">
		var subareaid = rowData.id;
		$("input[name='oldId']").val(subareaid);
		//rowData为被双击目标的数据
		$('#editSubareaWindow').window("open");
		//load方法回显数据
		$('#editSubareaForm').form("load",rowData); 
		var regionid = rowData.bcRegion.id
		$('#editSubareaForm').form("load",{
			'bcRegion.id':regionid
		}); 
		
		var arrayid = null;
		$.post('subareaAction_findAllId.action',function(dataid){//获取所有分区id
			
			arrayid = dataid.split(",");//用,分割取出数组
		});
		$.extend($.fn.validatebox.defaults.rules, {
			subareaid : {
				validator : function(value, param) {
					var result = true;
					for(var i=0;i<arrayid.length;i++){
						if(value!= subareaid & value==arrayid[i]){
							result = false;//如果用户名已被使用过
						}
					}
					return result;
				},
				message : '该分拣编号已被使用'
			}
		});
		</shiro:hasPermission>
	}
	
	function doShowHighcharts() {
		$('#showSubareaWindow').window("open");
		
		$.post("subareaAction_findSubareaGroupByProvince.action",function(data){
			
			Highcharts.chart('container', {
				chart: {
					plotBackgroundColor: null,
					plotBorderWidth: null,
					plotShadow: false,
					type: 'pie'
				},
				title: {
					text: '区域分区分布图'
				},
				tooltip: {
					pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'+'<br/>个数: <b>{point.y}</b>'
				},
				plotOptions: {
					pie: {
						allowPointSelect: true,
						cursor: 'pointer',
						dataLabels: {
							enabled: true,
							format: '<b>{point.name}</b>: {point.y}<br/>{point.percentage:.1f}%',
							style: {
								color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
							}
						}
					}
				},
				series: [{
					name: '百分比',
					colorByPoint: true,
					data: data
				}]
			});
		});
	}
	
</script>
</head>
<body class="easyui-layout" style="visibility: hidden;">
	<div region="center" border="false">
		<table id="grid"></table>
	</div>
	
	<!-- 添加分区 -->
	<div class="easyui-window" title="添加分区" id="addSubareaWindow" collapsible="false" minimizable="false"
		maximizable="false" style="top: 20px; left: 200px">
		<div style="height: 31px; overflow: hidden;" split="false" border="false">
			<div class="datagrid-toolbar">
				<a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true">保存</a>
				<script type="text/javascript">
					$(function() {
						$("#save").click(function() {
							var v = $("#addSubareaForm").form("validate");
							if (v) {
								//校验通过
								$("#addSubareaForm").submit();
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
			<form id="addSubareaForm" method="post" action="subareaAction_add.action">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">分区信息</td>
					</tr>
					<tr>
						<td>分拣编码</td>
						<td>
							<input type="text" name="id" class="easyui-validatebox" required="true" />
						</td>
					</tr>
					<tr>
						<td>选择区域</td>
						<td>
							<input class="easyui-combobox" name="bcRegion.id"
								data-options="valueField:'id',textField:'name',mode:'remote',url:'regionAction_listajax.action'" />
						</td>
					</tr>
					<tr>
						<td>关键字</td>
						<td>
							<input type="text" name="addresskey" class="easyui-validatebox" required="true" />
						</td>
					</tr>
					<tr>
						<td>起始号</td>
						<td>
							<input type="text" name="startnum" class="easyui-validatebox" required="true" />
						</td>
					</tr>
					<tr>
						<td>终止号</td>
						<td>
							<input type="text" name="endnum" class="easyui-validatebox" required="true" />
						</td>
					</tr>
					<tr>
						<td>单双号</td>
						<td>
							<select class="easyui-combobox" name="single" style="width: 150px;">
								<option value="0">单双号</option>
								<option value="1">单号</option>
								<option value="2">双号</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>位置信息</td>
						<td>
							<input type="text" name="position" class="easyui-validatebox" required="true" style="width: 250px;" />
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	
	<!-- 修改分区 -->
	<div class="easyui-window" title="修改分区" id="editSubareaWindow" collapsible="false" minimizable="false"
		maximizable="false" style="top: 20px; left: 200px">
		<div style="height: 31px; overflow: hidden;" split="false" border="false">
			<div class="datagrid-toolbar">
				<a id="edit" icon="icon-edit" href="#" class="easyui-linkbutton" plain="true">修改</a>
				<script type="text/javascript">
					$(function() {
						$("#edit").click(function() {
							var v = $("#editSubareaForm").form("validate");
							if (v) {
								//校验通过
								$("#editSubareaForm").submit();
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
			<form id="editSubareaForm" method="post" action="subareaAction_edit.action">
				<input type="hidden" name="oldId">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">分区信息</td>
					</tr>
					<tr>
						<td>分拣编码</td>
						<td>
							<input type="text" name="id" data-options="validType:'subareaid'" class="easyui-validatebox" required="true" />
							<script type="text/javascript">
							$(function() {
								
							});
							</script>
						</td>
					</tr>
					
					<tr>
						<td>选择区域</td>
						<td>
							<input class="easyui-combobox" name="bcRegion.id"
								data-options="valueField:'id',textField:'name',mode:'remote',url:'regionAction_listajax.action'" />
						</td>
					</tr>
					<tr>
						<td>关键字</td>
						<td>
							<input type="text" name="addresskey" class="easyui-validatebox" required="true" />
						</td>
					</tr>
					<tr>
						<td>起始号</td>
						<td>
							<input type="text" name="startnum" class="easyui-validatebox" required="true" />
						</td>
					</tr>
					<tr>
						<td>终止号</td>
						<td>
							<input type="text" name="endnum" class="easyui-validatebox" required="true" />
						</td>
					</tr>
					<tr>
						<td>单双号</td>
						<td>
							<select class="easyui-combobox" name="single" style="width: 150px;">
								<option value="0">单双号</option>
								<option value="1">单号</option>
								<option value="2">双号</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>位置信息</td>
						<td>
							<input type="text" name="position" class="easyui-validatebox" required="true" style="width: 250px;" />
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<!-- 查询分区 -->
	<div class="easyui-window" title="查询分区窗口" id="searchWindow" collapsible="false" minimizable="false" maximizable="false"
		style="top: 20px; left: 200px">
		<div style="overflow: auto; padding: 5px;" border="false">
			<form id="searchForm">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">查询条件</td>
					</tr>
					<tr>
						<td>省</td>
						<td>
							<input type="text" name="bcRegion.province" />
						</td>
					</tr>
					<tr>
						<td>市</td>
						<td>
							<input type="text" name="bcRegion.city" />
						</td>
					</tr>
					<tr>
						<td>区（县）</td>
						<td>
							<input type="text" name="bcRegion.district" />
						</td>
					</tr>
					<tr>
						<td>关键字</td>
						<td>
							<input type="text" name="addresskey" />
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
	
	
	<!-- 区域分区分布图 -->
	<div class="easyui-window" title="区域分区分布图" id="showSubareaWindow" collapsible="false" minimizable="false"
		maximizable="false" style="top: 20px; left: 200px">
		<div id="container" style="min-width:400px;height:400px"></div>
		
	</div>
</body>
</html>