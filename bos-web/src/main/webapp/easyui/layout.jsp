<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>物流管理系统</title>

<%-- 引入对jq和easyui的支持；${}指目录下的webapp,相对路径 --%>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/local/easyui-lang-zh_CN.js"></script>
<%-- 引入对ztree的支持 --%>
<link rel="stylesheet" href="${pageContext.request.contextPath }/js/ztree/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/ztree/jquery.ztree.all-3.5.js"></script>


</head>
<body class="easyui-layout">

	<div title="管理系统" style="height: 100px" data-options="region:'north'">
		<!-- 
		控制面板按钮
		menu定义下拉菜单 
		class="menu-sep" 分割线
		-->
		<div id="controlsys" style="position: absolute;right: 5px;top:70px;">
			<a data-options="iconCls:'icon-help',menu:'#con-op'" class="easyui-menubutton">控制面板</a>
			<div id="con-op">
				<div data-options="iconCls:'icon-edit'">修改密码</div>
				<div>联系管理员</div>
				<div class="menu-sep"></div>
				<div>退出系统</div>
			</div>
		</div>
	</div>

	<div title="系统菜单" style="width: 200px" data-options="region:'west'">
		<!-- 
		制作折叠面板 class="easyui-accordion"
		fit:true 自填充
		iconCls:'icon-edit' 加图标,定义在icon.css中
		-->
		<div class="easyui-accordion" data-options="fit:true">
			<div data-options="iconCls:'icon-add'" title="面板1">
				<a id="but1" class="easyui-linkbutton">添加选项卡</a>
				<script type="text/javascript">
					//绑定按钮but1
					$('#but1').click(function() {
						//判断标签是否存在
						var e = $("#mytabs").tabs("exists","系统管理");
						if (e) {
							//如果存在则选中即可
							$('#mytabs').tabs("select", "系统管理");
						} else {
							//如果不存在,则添加标签
							$('#mytabs').tabs("add",{
								title : '系统管理',
								iconCls : 'icon-edit',
								closable : true,
								//页面内容,用内联框架
								content : '<iframe frameborder="0" height="100%" width="100%" src="https://www.baidu.com"></iframe>'
							});
						};
					});
				</script>
			</div>
			<div data-options="iconCls:'icon-redo'" title="面板2">
				<!-- 使用标准JSON方式构造 -->
				<!-- 展示ztree效果 -->
				<ul id="ztree1" class="ztree"></ul>
				<script type="text/javascript">
					$(function() {
						//动态创建ztree,setting使用默认值
						var setting = {};
						//构造节点
						var zNodes = [ 
									{"name":"节点一","children":[
																{"name":"节点1.1"},
																{"name":"节点1.2"},
																{"name":"节点1.3"}	
																]}, 
									{"name":"节点二"},
									{"name":"节点三"} 
									];
						//初始化ztree
						$.fn.zTree.init($('#ztree1'), setting, zNodes);
					});
				</script>
			</div>

			<div data-options="iconCls:'icon-save'" title="面板3">
			<!-- 使用简单JSON方式构造ztree -->
			<ul id="ztree2" class="ztree"></ul>
				<script type="text/javascript">
					$(function() {
						//动态创建ztree,修改setting使用简单JSON
						var setting2 = {
								data: {
									simpleData: {
										enable: true
									}
								}
						};
						//构造节点,id是自己的id,pId是父级id;3是2的子节点,2是1的子节点
						var zNodes2 = [ 
									{"id":"1","pId":"0","name":"节点一"}, 
									{"id":"2","pId":"1","name":"节点二"},
									{"id":"3","pId":"2","name":"节点三"} 
									];
						//初始化ztree
						$.fn.zTree.init($('#ztree2'), setting2, zNodes2);
					});
				</script>
			</div>
			
			<div data-options="iconCls:'icon-cut'" title="面板4">
			<!-- 使用简单JSON方式构造ztree -->
			<ul id="ztree3" class="ztree"></ul>
				<script type="text/javascript">
					$(function() {
						//动态创建ztree,修改setting
						var setting3 = {
								data: {
									simpleData: {
										enable: true//允许简单JSON
									}
								},
								//响应点击
								callback: {
									onClick: function(event,treeId,treeNode){
										//如果不是根目录
										if(treeNode.page != undefined){
											var e3 = $("#mytabs").tabs("exists",treeNode.name);
											if (e3) {
												//如果存在则选中即可
												$('#mytabs').tabs("select", treeNode.name);
											}else{
												//不存在则打开新标签
												$("#mytabs").tabs("add",{
													title:treeNode.name,
													closable:true,
													content : '<iframe frameborder="0" height="100%" width="100%" src="'+treeNode.page+'"></iframe>'
												});
											}
										}
									}
								}
						};
						//ajax方式,获取JSON;jQ提供了ajax、post、get、load、jetJSON、getScript方法
						//获取webapp/json/menu.json中的数据
						var url = "${pageContext.request.contextPath}/json/menu.json";
						//post方式显示url返回的数据data
						$.post(url,{},function(data){
							$.fn.zTree.init($('#ztree3'), setting3, data);
						},'json');
					});
				</script>
			</div>
		</div>
	</div>

	<!-- 
	制作标签栏 class="easyui-tabs" 
	closable:true 可关闭选项卡
	-->
	<div data-options="region:'center'">
		<div id="mytabs" class="easyui-tabs" data-options="fit:true">
			<div data-options="iconCls:'icon-reload',closable:true" title="面板1">
			<!-- 用easyUI的datagrid制造表格 -->
			<table id="mytable">
			</table>
			<script type="text/javascript">
			$(function(){
				$("#mytable").datagrid({
					//定义标题,title是标题,field是url中数据所对应的标签,checkbox是选择框
					columns:[[
						{checkbox:true},
						{title:'id',field:'id'},
						{title:'父id',field:'pId'},
						{title:'名称',field:'name'},
						{title:'页面',field:'page'}
					]],
					//获取数据的路径
					url:'${pageContext.request.contextPath}/json/menu.json',
					//显示行号
					rownumbers:true,
					//只可单选
					singleSelect:true,
					//定义工具栏,text是工具名称,iconCls是图标,handler是绑定单击事件
					toolbar:[
						{text:'添加',iconCls:'icon-add',
							handler:function(){
								alert("添加")
							}},
						{text:'删除',iconCls:'icon-remove'},
						{text:'修改',iconCls:'icon-edit'},
						{text:'查询',iconCls:'icon-search'}
					],
					//分页条,传递的数据是page(页码)和rows(每页显示的条目数),数据内要写好total(总条目数)和rows[{},{}](json响应回要显示的内容)
					pagination:true
				})
			})
			</script>
			</div>
			<div data-options="iconCls:'icon-print',closable:true" title="面板2">222</div>
			<div data-options="iconCls:'icon-tip',closable:true" title="面板3">333</div>
		</div>
	</div>

	<div style="width: 200px" data-options="region:'east'">东</div>
	<div style="height: 50px" data-options="region:'south'">南</div>

</body>
</html>