		//省份
		var sheng = ["北京市", "天津市", "上海市", "重庆市", "河北省", "山西省", "内蒙古", "辽宁省", "吉林省", "黑龙江省", "江苏省",
			"浙江省", "安徽省", "福建省", "江西省", "山东省", "河南省", "湖北省", "湖南省", "广东省", "广西", "海南省", "四川省", "贵州省",
			"云南省", "西藏", "陕西省", "甘肃省", "青海省", "宁夏", "新疆", "香港", "澳门", "台湾省"
		];
		//市，区
		var cityarr = [
			//北京市0
			["东城区", "西城区", "崇文区", "宣武区", "朝阳区", "丰台区", "石景山区", "海淀区", "门头沟区", "房山区", "通州区", "顺义区",
				"昌平区", "大兴区", "怀柔区", "平谷区", "密云县", "延庆县", "延庆镇"
			],
			//天津市1
			["和平区", "河东区", "河西区", "南开区", "河北区", "红桥区", "塘沽区", "汉沽区", "大港区", "东丽区", "西青区", "津南区", "北辰区",
				"武清区", "宝坻区", "蓟县", "宁河县", "芦台镇", "静海县", "静海镇"
			],
			//上海市	2
			["黄浦区", "卢湾区", "徐汇区", "长宁区", "静安区", "普陀区", "闸北区", "虹口区", "杨浦区", "闵行区", "宝山区", "嘉定区",
				"浦东新区", "金山区", "松江区", "青浦区", "南汇区", "奉贤区", "崇明县", "城桥镇"
			]
		];
		//街道
		var areaarr = [
			//北京街道
			[
				["安定门街道", "建国门街道", " 朝阳门街道", "东直门街道", "东华门街道 ", "和平里街道", "北新桥街道", "交道口街道", "景山街道",
					"东四街道", "天坛街道", "东花市街道", "前门街道", "龙潭街道", "永定门外街道", "崇文门外街道", "体育馆路街道"
				],

				["西长安街街道", "新街口街道", "月坛街道", "展览路街道", "德胜街道", "金融街街道", "什刹海街道", "大栅栏街道", "天桥街道", "椿树街道",
					"陶然亭街道", "广安门内街道", "牛街街道", "白纸坊街道", "广安门外街道"
				],

				["前门街道", "崇文门外街道", "东花市街道", "龙潭街道", "体育馆路街道", "天坛街道", "永定门外街道"]
			],
			//天津街道
			[
				["劝业场街道", "小白楼街道", "五大道街道", "新兴街道", "南营门街道", "南市街道"],
				["大王庄街道", "大直沽街道", "中山门街道", "富民路街道", "二号桥街道", "春华街道", "唐家口街道", "向阳楼街道", "常州道街道",
					"上杭路街道", "东新街道", "鲁山道街道", "天津铁厂街道"
				]
			],
			//上海街道
			[
				["南京东路", "外滩", "瑞金二路", "淮海中路", "豫园", "打浦桥", "老西门", "小东门", "五里桥", "半淞园路"],
				["打浦桥街道", "淮海中路街道", "瑞金二路街道", "五里桥街道"]

			]
		];
/*		var pro = document.getElementById("provice");
		var city = document.getElementById("city");
		var areas = document.getElementById("area");*/

			//获取省份的下拉列表对象
		var prov = document.getElementById("prov");
		//市级
		var city = document.getElementById("city");
		//区级
		var areas = document.getElementById("area");
		
		//添加省级数据到下拉列表中
		for(var i=0;i<sheng.length;i++){
			//给省份列表添加值 文本值  value值
			prov.options.add(new Option(sheng[i],i));
		}
		
		//省份的下标
		var v;
		//通过改变省份获取城市的数据
		function getCity(obj){
			//根据你点击的省份获取当前省份的value
			v = obj.value;
			//市级数据[省份的下标]
			var c=cityarr[v];
			//区级数据[省份下标（市级数据）][市级下标（县级数据）]
			var a = areaarr[v][0];
			//清空数据
			city.options.length=0;
			areas.options.length=0;
			//循环添加市级数据
			for(var i=0;i<c.length;i++){
				city.options.add(new Option(c[i],i));
			}
			//循环添加县级数据
			for(var i=0;i<a.length;i++){
				areas.options.add(new Option(a[i],i));
			}
		}
		//改变市级的数据从而获取县级数据
		function getArea(obj){
			//获取市级数据的下标
			var s = obj.selectedIndex;
			var a = areaarr[v][s];
			//清空县级数据
			areas.options.length=0;
			for(var i=0;i<a.length;i++){
				areas.options.add(new Option(a[i],i));
			}
		}
		function showCity()
		{
			var is = prov.selectedIndex;
			var s = prov.options[is].text;
			var ic = city.selectedIndex;
			var c = city.options[ic].text;
			var ia = areas.selectedIndex;
			var a = areas.options[ia].text;
			
			document.write(s+c+a);
		}
