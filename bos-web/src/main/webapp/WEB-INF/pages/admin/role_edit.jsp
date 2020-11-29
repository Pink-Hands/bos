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
<!-- 导入ztree类库 -->
<link rel="stylesheet" href="${pageContext.request.contextPath }/js/ztree/zTreeStyle.css" type="text/css" />
<script src="${pageContext.request.contextPath }/js/ztree/jquery.ztree.all-3.5.js" type="text/javascript"></script>
<script type="text/javascript">
	$(function() {
		// 授权树初始化
		var setting = {
			data : {
				key : {
					title : "t"
				},
				simpleData : {
					enable : true
				}
			},
			check : {
				enable : true,//使用ztree的勾选效果
			}
		};

		$.ajax({
				url : '${pageContext.request.contextPath}/functionAction_listajax.action',//所需数据结构与之前ztree的一致(name变为text)
				type : 'POST',
				dataType : 'json',
				success : function(data) {
					$.fn.zTree.init($("#functionTree"), setting, data);
					var urlinfo = window.location.href;//获取传递来的url
					var idd = urlinfo.split("?")[1].split("=")[1];//获取传递来的id
					$.post('roleAction_findById.action',{'roleId':idd},function(data2){
						$('#roleForm').form("load",data2);//回显部分数据
						var functions = data2.authFunctions;//获取角色对应的权限
						var treeObj = $.fn.zTree.getZTreeObj("functionTree");//获取zTree
						for(var i=0;i<functions.length;i++){
							var node = treeObj.getNodeByParam("id", functions[i].id, null);//根据id找出节点
							treeObj.checkNode(node, true, false);//根据节点回显角色对应的权限
						}
						treeObj.expandAll(true);//设置数默认全部展开
						});
				},
				error : function(msg) {
					alert('树加载异常!');
				}
			});
		
		
		// 点击保存
		$('#save').click(function() {
			var v = $("#roleForm").form("validate");
			if (v) {
				var treeObj = $.fn.zTree.getZTreeObj("functionTree");//根据ztree的id获取ztree对象
				var nodes = treeObj.getCheckedNodes(true);//获取选中的项
				var array = new Array();//声明集合
				for (var i = 0; i < nodes.length; i++) {
					var id = nodes[i].id;
					array.push(id);//将id加进去集合
				}
				var functionIds = array.join(",");//用,连接array内所有元素
				$("input[name=functionIds]").val(functionIds);//为隐藏域赋值
				$("#roleForm").submit();
			} else {
				$.messager.alert("提示", "不符合要求！", "warning");
			}
		});
	});
</script>
</head>
<body class="easyui-layout">
	<div region="north" style="height: 31px; overflow: hidden;" split="false" border="false">
		<div class="datagrid-toolbar">
			<a id="save" icon="icon-edit" href="#" class="easyui-linkbutton" plain="true">修改</a>
		</div>
	</div>
	<div region="center" style="overflow: auto; padding: 5px;" border="false">
		<form id="roleForm" method="post" action="roleAction_edit.action">
			<input type="hidden" name="id">
			<input type="hidden" name="functionIds">
			<table class="table-edit" width="80%" align="center">
				<tr class="title">
					<td colspan="2">角色信息</td>
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
					<td>描述</td>
					<td>
						<textarea name="description" rows="4" cols="60"></textarea>
					</td>
				</tr>
				<tr>
					<td>授权</td>
					<td>
						<ul id="functionTree" class="ztree"></ul>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>