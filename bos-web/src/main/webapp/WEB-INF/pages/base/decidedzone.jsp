<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理定区/调度排班</title>
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
	function doAdd() {
		$('#addDecidedzoneWindow').window("open");
	}
	function onLoadSuccess(data) {
		$('#editsubareaedGrid').datagrid('selectAll');
	}

	function doEdit() {
		var rows = $('#grid').datagrid("getSelections");
		if(rows.length == 0 ){
			//没选中
		}else{
			if(rows.length > 1){
				//选中多于1个
				$.messager.alert("提示","每次只可选择一个定区进行修改","warning");
			}else{
				//只选中1个
				var editData = rows[0];
				$("input[name='oldId']").val(editData.id);//传递原id
				$('#editDecidedzoneWindow').window("open");
				//load方法回显数据
				$('#editDecidedzoneForm').form("load",editData);
				$('#editDecidedzoneForm').form("load",{
					'bcStaff.id':editData.bcStaff.id//回显取派员
				});
				
				$('#editsubareaedGrid').datagrid({
					fit : true,
					striped : true,
					rownumbers : false,
					fitColumns : true,
					singleSelect:false,
					onLoadSuccess:onLoadSuccess,
					url : "subareaAction_findListByDecidedzoneId.action?decidedzoneId="+editData.id,
					columns : [ [ {
						field : 'subareaid',
						title : '编号',
						width : 50,
						align : 'center',
						checkbox : true,
					}, {
						field : 'addresskey',
						title : '关键词',
						width : 120,
						align : 'center',
					}, {
						field : 'position',
						title : '位置',
						width : 120,
						align : 'center',
					} ] ]
				
				});
			}
		}
	}

	function doDelete() {
		var rows = $('#grid').datagrid("getSelections");
		if(rows.length == 0 ){
			//没选中,提示选择
			$.messager.alert("提示","请选择需要删除的定区","warning");
		}else{
			//选中了,提示确定
			$.messager.confirm("提示","确认要删除选中的定区吗",function(r){
			if(r){
				//确认
				var array = new Array();//数组
				for(var i=0;i<rows.length;i++){
					var staff = rows[i];
					var id = staff.id;
					array.push(id);//将id加入数组
				}
				var ids = array.join(",");//用,连接数组
				location.href = "decidedzoneAction_deleteBatch.action?ids="+ids;//手动跳转,将数据发送过去同时刷新页面
			}else{
				//取消
			}
		})
		}
	}

	function doSearch() {
		$('#searchWindow').window("open");
	}
	function doUndo() {
		$.post('decidedzoneAction_pageQuery.action',function(data){
			$("#grid").datagrid("load", data);//用load方法发送ajax请求提交数据
		});
	}
	
	//关联客户
	function doAssociations() {
		//获取选中的行
		var rows = $("#grid").datagrid("getSelections");
		if(rows.length != 1){
			//选中的行数不为1
			$.messager.alert("提示","请选择一个定区进行操作！","warning");
		}else{
			$('#customerWindow').window('open');
			$("#noassociationSelect").empty();//清除左边下拉框缓存
			$("#associationSelect").empty();//清除右边下拉框缓存
			//发送ajax请求,通过crm远程调用数据,获取未关联客户名单
			var url_1 = "decidedzoneAction_findListNotAssociaton.action";
			$.post(url_1,function(data){
				for(var i=0;i<data.length;i++){
					var id = data[i].id;
					var name = data[i].name;
					var telephone = data[i].telephone;
					name = name + "(" + telephone + ")";
					$("#noassociationSelect").append("<option value='"+id+"'>"+name+"</option>");//往下拉框中追加显示
				}
			});
			
			//发送ajax请求,通过crm远程调用数据,获取已关联客户名单
			var url_2 = "decidedzoneAction_findListHasAssociaton.action";
			var decidedzoneId = rows[0].id;//获取被选中行的id
			$.post(url_2,{"id":decidedzoneId},function(data){
				for(var i=0;i<data.length;i++){
					var id = data[i].id;
					var name = data[i].name;
					var telephone = data[i].telephone;
					name = name + "(" + telephone + ")";
					$("#associationSelect").append("<option value='"+id+"'>"+name+"</option>");//往下拉框中追加显示
				}
			});
		}
	}

	//工具栏
	var toolbar = [ 
	<shiro:hasPermission name="decidedzone-search">
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
	
	<shiro:hasPermission name="decidedzone-add">
	{
		id : 'button-add',
		text : '增加',
		iconCls : 'icon-add',
		handler : doAdd
	}, 
	</shiro:hasPermission>
	
	<shiro:hasPermission name="decidedzone-edit">
	{
		id : 'button-edit',
		text : '修改',
		iconCls : 'icon-edit',
		handler : doEdit
	}, 
	</shiro:hasPermission>
	
	<shiro:hasPermission name="decidedzone-delete">
	{
		id : 'button-delete',
		text : '删除',
		iconCls : 'icon-cancel',
		handler : doDelete
	}, 
	</shiro:hasPermission>
	
	<shiro:hasPermission name="decidedzone-association">
	{
		id : 'button-association',
		text : '关联客户',
		iconCls : 'icon-sum',
		handler : doAssociations
	} 
	</shiro:hasPermission>
	];
	// 定义列
	var columns = [ [ {
		field : 'id',
		title : '定区编号',
		width : 120,
		align : 'center'
	}, {
		field : 'name',
		title : '定区名称',
		width : 120,
		align : 'center'
	}, {
		field : 'bcStaff.name',
		title : '负责人',
		width : 120,
		align : 'center',
		formatter : function(data, row, index) {
			return row.bcStaff.name;
		}
	}, {
		field : 'bcStaff.telephone',
		title : '联系电话',
		width : 120,
		align : 'center',
		formatter : function(data, row, index) {
			return row.bcStaff.telephone;
		}
	}, {
		field : 'bcStaff.station',
		title : '所属公司',
		width : 120,
		align : 'center',
		formatter : function(data, row, index) {
			return row.bcStaff.station;
		}
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
			url : "decidedzoneAction_pageQuery.action",
			idField : 'id',
			columns : columns,
			onDblClickRow : doDblClickRow
		});

		// 添加定区
		$('#addDecidedzoneWindow').window({
			title : '添加定区',
			width : 600,
			modal : true,
			shadow : true,
			closed : true,
			height : 400,
			resizable : false
		});
		// 修改定区
		$('#editDecidedzoneWindow').window({
			title : '修改定区',
			width : 600,
			modal : true,
			shadow : true,
			closed : true,
			height : 700,
			resizable : false
		});

		// 查询定区
		$('#searchWindow').window({
			title : '查询定区',
			width : 400,
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
		
		$("#btn").click(function() {
			var p = $("#searchForm").serializeJson();//将查询表单中的输入内容转化为json
			$("#grid").datagrid("load", p);//用load方法发送ajax请求提交数据
			$("#searchForm").get(0).reset();// 重置查询表单
			$('#searchWindow').window("close");//关闭查询窗口
		});

	});

	function doDblClickRow(index,data) {
		$('#association_subarea').datagrid({
			fit : true,
			border : true,
			rownumbers : true,
			striped : true,
			url : "subareaAction_findListByDecidedzoneId.action?decidedzoneId="+data.id,
			columns : [ [ {
				field : 'id',
				title : '分拣编号',
				width : 120,
				align : 'center'
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
			} ] ]
		});
		
		$('#association_customer').datagrid({
			fit : true,
			border : true,
			rownumbers : true,
			striped : true,
			url : "decidedzoneAction_findListHasAssociaton.action?id="+data.id,
			columns : [ [ {
				field : 'id',
				title : '客户编号',
				width : 120,
				align : 'center',
			}, {
				field : 'name',
				title : '客户名称',
				width : 120,
				align : 'center',
			}, {
				field : 'station',
				title : '所属单位',
				width : 120,
				align : 'center',
			} ] ]
		});
	}
</script>
</head>
<body class="easyui-layout" style="visibility: hidden;">
	<div region="center" border="false">
		<table id="grid"></table>
	</div>
	<div region="south" border="false" style="height: 150px">
		<div id="tabs" fit="true" class="easyui-tabs">
			<div title="关联分区" id="subArea" style="width: 100%; height: 100%; overflow: hidden">
				<table id="association_subarea"></table>
			</div>
			<div title="关联客户" id="customers" style="width: 100%; height: 100%; overflow: hidden">
				<table id="association_customer"></table>
			</div>
		</div>
	</div>

	<!-- 添加定区 -->
	<div class="easyui-window" title="定区添加" id="addDecidedzoneWindow" collapsible="false" minimizable="false"
		maximizable="false" style="top: 20px; left: 200px">
		<div style="height: 31px; overflow: hidden;" split="false" border="false">
			<div class="datagrid-toolbar">
				<a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true">保存</a>
				<script type="text/javascript">
					$(function() {
						$("#save").click(function() {
							var v = $("#addDecidedzoneForm").form("validate");
							if(v){
								//表单正确
								$("#addDecidedzoneForm").submit();
							}else{
								//表单有误
								$.messager.alert("提示","内容不合规范！","error");
							}
						});
					});
				</script>
			</div>
		</div>

		<div style="overflow: auto; padding: 5px;" border="false">
			<form id="addDecidedzoneForm" method="post" action="decidedzoneAction_add.action">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">定区信息</td>
					</tr>
					<tr>
						<td>定区编码</td>
						<td>
							<input type="text" name="id" class="easyui-validatebox" required="true" />
						</td>
					</tr>
					<tr>
						<td>定区名称</td>
						<td>
							<input type="text" name="name" class="easyui-validatebox" required="true" />
						</td>
					</tr>
					<tr>
						<td>选择取派员</td>
						<td>
							<input class="easyui-combobox" name="bcStaff.id"
								data-options="valueField:'id',textField:'name',url:'staffAction_listajax.action'" />
						</td>
					</tr>
					<tr height="300">
						<td valign="top">关联分区</td>
						<td>
							<table id="subareaGrid" class="easyui-datagrid" border="false" style="width: 300px; height: 300px"
								data-options="url:'subareaAction_listajax.action',fitColumns:true">
								<thead>
									<tr>
										<th data-options="field:'subareaid',width:30,checkbox:true">编号</th>
										<th data-options="field:'addresskey',width:150">关键字</th>
										<th data-options="field:'position',width:200,align:'right'">位置</th>
									</tr>
								</thead>
							</table>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	
	<!-- 修改定区 -->
	<div class="easyui-window" title="定区修改" id="editDecidedzoneWindow" collapsible="false" minimizable="false"
		maximizable="false" style="top: 20px; left: 200px">
		<div style="height: 31px; overflow: hidden;" split="false" border="false">
			<div class="datagrid-toolbar">
				<a id="editsave" icon="icon-edit" href="#" class="easyui-linkbutton" plain="true">修改</a>
				<script type="text/javascript">
					$(function() {
						$("#editsave").click(function() {
							var v = $("#editDecidedzoneForm").form("validate");
							if(v){
								//表单正确
								$("#editDecidedzoneForm").submit();
							}else{
								//表单有误
								$.messager.alert("提示","内容不合规范！","error");
							}
						});
					});
				</script>
			</div>
		</div>

		<div style="overflow: auto; padding: 5px;" border="false">
			<form id="editDecidedzoneForm" method="post" action="decidedzoneAction_edit.action">
				<input type="hidden" name="oldId">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">定区信息</td>
					</tr>
					<tr>
						<td>定区编码</td>
						<td>
							<input type="text" name="id" class="easyui-validatebox" required="true" />
						</td>
					</tr>
					<tr>
						<td>定区名称</td>
						<td>
							<input type="text" name="name" class="easyui-validatebox" required="true" />
						</td>
					</tr>
					<tr>
						<td>选择取派员</td>
						<td>
							<input class="easyui-combobox" name="bcStaff.id"
								data-options="valueField:'id',textField:'name',url:'staffAction_listajax.action'" />
						</td>
					</tr>
					<tr height="250">
						<td valign="top">关联分区</td>
						<td>
							<table id="editsubareaGrid" class="easyui-datagrid" border="false" style="width: 300px; height: 250px"
								data-options="url:'subareaAction_listajax.action',fitColumns:true,singleSelect:false">
								<thead>
									<tr>
										<th data-options="field:'subareaid',width:30,checkbox:true">编号</th>
										<th data-options="field:'addresskey',width:150">关键字</th>
										<th data-options="field:'position',width:200,align:'right'">位置</th>
									</tr>
								</thead>
							</table>
						</td>
					</tr>
					<tr height="200">
						<td valign="top">已关联分区</td>
						<td>
							<table id="editsubareaedGrid" class="easyui-datagrid" border="false" style="width:300px; height: 200px"
								data-options="singleSelect:false">
							</table>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	
	<!-- 查询定区 -->
	<div class="easyui-window" title="查询定区窗口" id="searchWindow" collapsible="false" minimizable="false" maximizable="false"
		style="top: 20px; left: 200px">
		<div style="overflow: auto; padding: 5px;" border="false">
			<form id="searchForm">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">查询条件</td>
					</tr>
					<tr>
						<td>定区编码</td>
						<td>
							<input type="text" name="id" class="easyui-validatebox"/>
						</td>
					</tr>
					<tr>
						<td>所属单位</td>
						<td>
							<input type="text" name="bcStaff.station" class="easyui-validatebox"/>
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

	<!-- 关联客户窗口 -->
	<div modal=true class="easyui-window" title="关联客户窗口" id="customerWindow" collapsible="false" closed="true" minimizable="false"
		maximizable="false" style="top: 20px; left: 200px; width: 400px; height: 300px;">
		<div style="overflow: auto; padding: 5px;" border="false">
			<form id="customerForm" action="decidedzoneAction_assigncustomerstodecidedzone.action"
				method="post">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="3">关联客户</td>
					</tr>
					<tr>
						<td>
							<input type="hidden" name="id" id="customerDecidedZoneId" />
							<select id="noassociationSelect" multiple="multiple" size="10"></select>
						</td>
						<td>
							<input type="button" value="》》" id="toRight">
							<br />
							<input type="button" value="《《" id="toLeft">
							<script type="text/javascript">
								$(function(){
									$("#toRight").click(function(){
										//在右边追加左边选中的项目,append是移动的效果,追加后左边的会消失
										$("#associationSelect").append($("#noassociationSelect option:selected"));
									});
									$("#toLeft").click(function(){
										$("#noassociationSelect").append($("#associationSelect option:selected"));
									});
									
									//为 关联客户 按钮绑定事件
									$("#associationBtn").click(function(){
										//传递被选中的定区id
										var rows = $("#grid").datagrid("getSelections");
										var id = rows[0].id;
										$("input[name=id]").val(id);//赋值定区id
										//提交表单前将右侧下拉框全部选中
										$("#associationSelect option").attr("selected","selected");
										$("#customerForm").submit();//提交表单
									});
								});
							</script>
						</td>
						<td>
							<select id="associationSelect" name="customerIds" multiple="multiple" size="10"></select>
						</td>
					</tr>
					<tr>
						<td colspan="3">
							<a id="associationBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'">关联客户</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>