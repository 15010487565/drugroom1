;(function(global){
	/**
	 * index主JS文件
	 */
	var myScroll,
		pullDownEl, pullDownOffset,
		pullUpEl, pullUpOffset,
		generatedCount = 0;
	var refresh_flag = true;
	var window_width = $(window).width(),
		window_height = $(window).height();
	$('#pic_viewer').css('lineHeight',window_height+"px");

	//var r_url = global.ESN_URL + "/convs/list/qzid/1/mid/1";
	var r_url =  'http://10.2.112.24/wallspace/fakesever.php';

	var IndexMain = function(){
		//var
	}
	
	$('.switch-space .drop-menu').width(window_width);
//	global.proxy.getJID();
	callSDK("getJID"); 
//	var urlStr = window.location.href;
//	var paraObj = genParaObj(urlStr.split('?')[1]);
//	var curSpaceId = paraObj.curSpaceId?paraObj.curSpaceId:0;
//	$('.switch-space .drop-menu').css('max-height',window_height);

/**
	 * 初始化iScroll控件
	 */
	function loaded() {
		pullDownEl = document.getElementById('pullDown');
		pullDownOffset = pullDownEl.offsetHeight;
		pullUpEl = document.getElementById('pullUp');
		pullUpOffset = pullUpEl.offsetHeight;
		
		global.myScroll = new IScroll('#wrapper',{ mouseWheel: true });
//		global.SwitchScroll = new IScroll('#spc-wrapper',{ mouseWheel: true });
		var flag;
		global.myScroll.on('scrollStart',function(){
			if((this.y-this.maxScrollY)<=50 && this.directionY == 1){
				flag = 0;
				$('.pullUpLabel').html( '<img style="width:25px" src="../img/loading.GIF">');
			}
			if(this.y == 0 && this.directionY == -1){
				flag = 1;
				//$('.pullDownLabel').html( '<img style="width:25px" src="../img/loading.GIF">');	
			}
		});
		global.myScroll.on('scrollEnd',function(){
			/*
			* 下拉到底部
			*/ 
			if(flag === 0){
				pullUpAction();
//				global.myScroll.refresh();
				$('.pullUpLabel').html( '');
			}
			if(flag === 1){
				//$('.pullDownLabel').html( '下拉刷新...');
					pullDownAction();
			}
			flag = -1;
		});
			
	}

		var space_list_param={
			page:0,
			pageSize:50,
			userId:"_wallspaceJID_"
		};
//		var space_list_param={
//			page:0,
//			pageSize:200,
//			userId:"wangxin0.udn.yonyou",
//			token:'b59ec83d-de5f-4fec-b469-b79f7da3120f'
//		};
//		global.proxy.getJoinedSpaces(JSON.stringify(space_list_param));
		setTimeout(function(){ callSDK("getJoinedSpaces",space_list_param);},global.proxy?0:80);
		/*$.ajax(
			{
				url:'http://172.20.8.189:9090/sysadmin/plugins/friends/spaceUserRelation/spaceByUser',
				dataType:'json',
				data:JSON.stringify(space_list_param),
				success:function(data_cbk){
					var data = {
						myspaces:data_cbk
					};
					
					var template_invoke = template('myspsmenu',data);
					$('.switch-space .drop-menu').prepend(template_invoke);
				}
			}
		);*/
	/*
	 * 刷新方法
	 */
	global.refreshPage = function () {
//		var param = {
//			wsid:0,//localStorage.cur_space_id,
//			username:"wangxin0.udn.yonyou",
//			page:0,
//			pageSize:200,
//			token:"b59ec83d-de5f-4fec-b469-b79f7da3120f"
//		};
		window.cur_page =1;
		var param = {
//			wsid:localStorage.cur_space_id,
			wsid:curSpaceId,
			userId:"_wallspaceJID_",
			page:window.cur_page,
			pageSize:20
		};
		
//	var data = {
//						con_lists: [{"id":"56014b5de4b00d6bb6630f80","sid":"0","username":"wangxin0.udn.yonyou","replyList":[],"ts":1442925405,"docType":1,"praiseUsers":[],"nickName":"王鑫111","documents":[],"remoteUrl":"","remoteAbstract":"","cont":"<img src=\"..\/img\/face\/smile_1f608.png\" class=\"face-pic\"><img src=\"..\/img\/face\/smile_1f608.png\" class=\"face-pic\"><img src=\"..\/img\/face\/smile_1f609.png\" class=\"face-pic\">","praiseNickNames":[]},{"id":"56012a79e4b0033be842c122","sid":"0","username":"wangxin0.udn.yonyou","replyList":[],"ts":1442916985,"docType":1,"praiseUsers":[],"nickName":"王鑫111","documents":[],"remoteUrl":"","remoteAbstract":"","cont":"呵呵","praiseNickNames":[]},{"id":"56010ae0e4b0033be842c118","sid":"0","username":"wangxin0.udn.yonyou","replyList":[{"id":"56010ae8e4b0033be842c119","userFrom":"wangxin0.udn.yonyou","ts":1442908904,"nickNameTo":"王鑫111","userTo":"wangxin0.udn.yonyou","pid":"56010ae0e4b0033be842c118","cont":"呵呵呵","nickNameFrom":"王鑫111"},{"id":"56012a74e4b0033be842c11f","userFrom":"wangxin0.udn.yonyou","ts":1442916980,"nickNameTo":"王鑫111","userTo":"wangxin0.udn.yonyou","pid":"56010ae0e4b0033be842c118","cont":"去","nickNameFrom":"王鑫111"}],"ts":1442908896,"docType":1,"praiseUsers":[],"nickName":"王鑫111","documents":[],"remoteUrl":"","remoteAbstract":"","cont":"有营养","praiseNickNames":[]},{"id":"55f8093fe4b0686d4e977ef5","sid":"0","username":"shicz.udn.yonyou","replyList":[{"id":"55f80943e4b0686d4e977ef6","userFrom":"shicz.udn.yonyou","ts":1442318659,"nickNameTo":"施成章","userTo":"shicz.udn.yonyou","pid":"55f8093fe4b0686d4e977ef5","cont":"x","nickNameFrom":"施成章"}],"ts":1442318655,"docType":1,"praiseUsers":[],"nickName":"施成章","documents":["5ai9cyjg5s719i23i8bk"],"remoteUrl":"","remoteAbstract":"","cont":"d<img src='https:\/\/im.yyuap.com\/sysadmin\/rest\/resource\/yonyou\/udn\/download?token=2f055b98-66f2-47b3-8919-8ef14a7c2b82&attachId=5ai9cyjg5s719i23i8bk&mediaType=2&downloader=wangxin0.udn.yonyou'\/>","praiseNickNames":[]},{"id":"55f80938e4b0686d4e977ef3","sid":"0","username":"shicz.udn.yonyou","replyList":[],"ts":1442318648,"docType":1,"praiseUsers":["wangxin0.udn.yonyou"],"nickName":"施成章","documents":[],"remoteUrl":"","remoteAbstract":"","cont":"d","praiseNickNames":["王鑫111"]},{"id":"55f7c53fe4b00ede2bd8c7fa","sid":"0","username":"wangxin0.udn.yonyou","replyList":[{"id":"55f7dbdae4b00ede2bd8c825","userFrom":"wangxin0.udn.yonyou","ts":1442307034,"nickNameTo":"王鑫111","userTo":"wangxin0.udn.yonyou","pid":"55f7c53fe4b00ede2bd8c7fa","cont":"Hhe","nickNameFrom":"王鑫111"},{"id":"55f7dbe1e4b00ede2bd8c828","userFrom":"wangxin0.udn.yonyou","ts":1442307041,"nickNameTo":"王鑫111","userTo":"wangxin0.udn.yonyou","pid":"55f7c53fe4b00ede2bd8c7fa","cont":"Mad","nickNameFrom":"王鑫111"},{"id":"55f7dbe7e4b00ede2bd8c82e","userFrom":"wangxin0.udn.yonyou","ts":1442307047,"nickNameTo":"王鑫111","userTo":"wangxin0.udn.yonyou","pid":"55f7c53fe4b00ede2bd8c7fa","cont":"Shit","nickNameFrom":"王鑫111"},{"id":"55fa2ad0a85b3881d685437d","userFrom":"rongqb.udn.yonyou","ts":1442458320,"nickNameTo":"王鑫111","userTo":"wangxin0.udn.yonyou","pid":"55f7c53fe4b00ede2bd8c7fa","cont":"你是谁","nickNameFrom":"荣启彬"},{"id":"55fa2b03a85b3881d685437e","userFrom":"rongqb.udn.yonyou","ts":1442458371,"nickNameTo":"荣启彬","userTo":"rongqb.udn.yonyou","pid":"55f7c53fe4b00ede2bd8c7fa","cont":"猜猜我是谁？","nickNameFrom":"荣启彬"},{"id":"55fa2b29a85b3881d685437f","userFrom":"rongqb.udn.yonyou","ts":1442458409,"nickNameTo":"荣启彬","userTo":"rongqb.udn.yonyou","pid":"55f7c53fe4b00ede2bd8c7fa","cont":"不猜","nickNameFrom":"荣启彬"},{"id":"55fa2b69a85b47fd6ea51043","userFrom":"rongqb.udn.yonyou","ts":1442458473,"nickNameTo":"王鑫111","userTo":"wangxin0.udn.yonyou","pid":"55f7c53fe4b00ede2bd8c7fa","cont":"你是谁？","nickNameFrom":"荣启彬"},{"id":"55fa2d1ca85baf40028970a1","userFrom":"rongqb.udn.yonyou","ts":1442458908,"nickNameTo":"王鑫111","userTo":"wangxin0.udn.yonyou","pid":"55f7c53fe4b00ede2bd8c7fa","cont":"哈喽","nickNameFrom":"荣启彬"},{"id":"55fa2d3ca85baf40028970a2","userFrom":"rongqb.udn.yonyou","ts":1442458940,"nickNameTo":"王鑫111","userTo":"wangxin0.udn.yonyou","pid":"55f7c53fe4b00ede2bd8c7fa","cont":"你好","nickNameFrom":"荣启彬"},{"id":"55fa2d83a85baf40028970a3","userFrom":"rongqb.udn.yonyou","ts":1442459011,"nickNameTo":"王鑫111","userTo":"wangxin0.udn.yonyou","pid":"55f7c53fe4b00ede2bd8c7fa","cont":"哈哈","nickNameFrom":"荣启彬"},{"id":"55fa2e2ba85b156ad4ecae96","userFrom":"rongqb.udn.yonyou","ts":1442459179,"nickNameTo":"荣启彬","userTo":"rongqb.udn.yonyou","pid":"55f7c53fe4b00ede2bd8c7fa","cont":"嘻嘻哈哈","nickNameFrom":"荣启彬"},{"id":"55fa2effa85b156ad4ecae97","userFrom":"rongqb.udn.yonyou","ts":1442459391,"nickNameTo":"荣启彬","userTo":"rongqb.udn.yonyou","pid":"55f7c53fe4b00ede2bd8c7fa","cont":"嘻嘻哈哈","nickNameFrom":"荣启彬"},{"id":"55fa302ba85b08824ce8e031","userFrom":"rongqb.udn.yonyou","ts":1442459691,"nickNameTo":"王鑫111","userTo":"wangxin0.udn.yonyou","pid":"55f7c53fe4b00ede2bd8c7fa","cont":"支持","nickNameFrom":"荣启彬"},{"id":"55fa30f6a85ba25e37e9912a","userFrom":"rongqb.udn.yonyou","ts":1442459894,"nickNameTo":"王鑫111","userTo":"wangxin0.udn.yonyou","pid":"55f7c53fe4b00ede2bd8c7fa","cont":"能发通知吗？","nickNameFrom":"荣启彬"},{"id":"55fa3116a85ba25e37e9912d","userFrom":"rongqb.udn.yonyou","ts":1442459926,"nickNameTo":"王鑫111","userTo":"wangxin0.udn.yonyou","pid":"55f7c53fe4b00ede2bd8c7fa","cont":"能发货吗","nickNameFrom":"荣启彬"},{"id":"55fa313aa85ba25e37e99130","userFrom":"wangxin0.udn.yonyou","ts":1442459962,"nickNameTo":"荣启彬","userTo":"rongqb.udn.yonyou","pid":"55f7c53fe4b00ede2bd8c7fa","cont":"不能","nickNameFrom":"王鑫111"},{"id":"55ffae49a85b68bbdaf601ee","userFrom":"wangxin0.udn.yonyou","ts":1442819657,"nickNameTo":"王鑫111","userTo":"wangxin0.udn.yonyou","pid":"55f7c53fe4b00ede2bd8c7fa","cont":"给自己回复","nickNameFrom":"王鑫111"}],"ts":1442301247,"docType":1,"praiseUsers":[],"nickName":"王鑫111","documents":[],"remoteUrl":"","remoteAbstract":"","cont":"Shit","praiseNickNames":[]},{"id":"55f7c524e4b00ede2bd8c7f9","sid":"0","username":"wangxin0.udn.yonyou","replyList":[{"id":"55fa33d5a85b23eadcf11ba5","userFrom":"rongqb.udn.yonyou","ts":1442460629,"nickNameTo":"王鑫111","userTo":"wangxin0.udn.yonyou","pid":"55f7c524e4b00ede2bd8c7f9","cont":"你好吗？","nickNameFrom":"荣启彬"},{"id":"55fa33e8a85b23eadcf11ba8","userFrom":"wangxin0.udn.yonyou","ts":1442460648,"nickNameTo":"荣启彬","userTo":"rongqb.udn.yonyou","pid":"55f7c524e4b00ede2bd8c7f9","cont":"我很好，你呢","nickNameFrom":"王鑫111"}],"ts":1442301220,"docType":1,"praiseUsers":[],"nickName":"王鑫111","documents":[],"remoteUrl":"","remoteAbstract":"","cont":"Friday is my secr favorite F word.","praiseNickNames":[]},{"id":"55f7c4ebe4b00ede2bd8c7f8","sid":"0","username":"wangxin0.udn.yonyou","replyList":[],"ts":1442301163,"docType":1,"praiseUsers":[],"nickName":"王鑫111","documents":[],"remoteUrl":"","remoteAbstract":"","cont":"Fuck","praiseNickNames":[]},{"id":"55f7c4d9e4b00ede2bd8c7f7","sid":"0","username":"wangxin0.udn.yonyou","replyList":[],"ts":1442301145,"docType":1,"praiseUsers":[],"nickName":"王鑫111","documents":[],"remoteUrl":"","remoteAbstract":"","cont":"Jesus christ","praiseNickNames":[]},{"id":"55f7c3fee4b00ede2bd8c7f6","sid":"0","username":"wangxin0.udn.yonyou","replyList":[],"ts":1442300926,"docType":1,"praiseUsers":[],"nickName":"王鑫111","documents":[],"remoteUrl":"","remoteAbstract":"","cont":"Bloody hell","praiseNickNames":[]},{"id":"55f7c3dfe4b00ede2bd8c7f5","sid":"0","username":"wangxin0.udn.yonyou","replyList":[],"ts":1442300895,"docType":1,"praiseUsers":[],"nickName":"王鑫111","documents":[],"remoteUrl":"","remoteAbstract":"","cont":"Shit","praiseNickNames":[]},{"id":"55f7c3a8e4b00ede2bd8c7f4","sid":"0","username":"wangxin0.udn.yonyou","replyList":[],"ts":1442300840,"docType":1,"praiseUsers":[],"nickName":"王鑫111","documents":[],"remoteUrl":"","remoteAbstract":"","cont":"Life is rather tough!","praiseNickNames":[]},{"id":"55f7bfb8e4b00ede2bd8c7e8","sid":"0","username":"wangxin0.udn.yonyou","replyList":[],"ts":1442299832,"docType":1,"praiseUsers":[],"nickName":"王鑫111","documents":[],"remoteUrl":"","remoteAbstract":"","cont":"The autum is coming","praiseNickNames":[]},{"id":"55f7bfb5e4b00ede2bd8c7e7","sid":"0","username":"zhangxin0.udn.yonyou","replyList":[],"ts":1442299829,"docType":1,"praiseUsers":[],"nickName":"3333","documents":["iq0nmx7a75y8e65cu8pp"],"remoteUrl":"","remoteAbstract":"","cont":"<img src='https:\/\/im.yyuap.com\/sysadmin\/rest\/resource\/yonyou\/udn\/download?token=2f055b98-66f2-47b3-8919-8ef14a7c2b82&attachId=iq0nmx7a75y8e65cu8pp&mediaType=2&downloader=wangxin0.udn.yonyou'\/>","praiseNickNames":[]},{"id":"55f7bf96e4b00ede2bd8c7e5","sid":"0","username":"zhangxin0.udn.yonyou","replyList":[],"ts":1442299798,"docType":1,"praiseUsers":[],"nickName":"3333","documents":["49euvbskcouknu58jsiv","0bcuyv2nf8x6ixxng17f"],"remoteUrl":"","remoteAbstract":"","cont":"<div class = 'img-can '><p style='background-image:url(https:\/\/im.yyuap.com\/sysadmin\/rest\/resource\/yonyou\/udn\/download?token=2f055b98-66f2-47b3-8919-8ef14a7c2b82&attachId=49euvbskcouknu58jsiv&mediaType=2&downloader=wangxin0.udn.yonyou)'><\/p><p style='background-image:url(https:\/\/im.yyuap.com\/sysadmin\/rest\/resource\/yonyou\/udn\/download?token=2f055b98-66f2-47b3-8919-8ef14a7c2b82&attachId=0bcuyv2nf8x6ixxng17f&mediaType=2&downloader=wangxin0.udn.yonyou)'><\/p><\/div>","praiseNickNames":[]},{"id":"55f7bf7de4b00ede2bd8c7e2","sid":"0","username":"zhangxin0.udn.yonyou","replyList":[],"ts":1442299773,"docType":1,"praiseUsers":[],"nickName":"3333","documents":["m49ttk24t1rqsc1hdopc"],"remoteUrl":"","remoteAbstract":"","cont":"<img src='https:\/\/im.yyuap.com\/sysadmin\/rest\/resource\/yonyou\/udn\/download?token=2f055b98-66f2-47b3-8919-8ef14a7c2b82&attachId=m49ttk24t1rqsc1hdopc&mediaType=2&downloader=wangxin0.udn.yonyou'\/>","praiseNickNames":[]},{"id":"55f7b9a3e4b00ede2bd8c7de","sid":"0","username":"zhangxin0.udn.yonyou","replyList":[],"ts":1442298275,"docType":1,"praiseUsers":[],"nickName":"3333","documents":[],"remoteUrl":"","remoteAbstract":"","cont":"老了","praiseNickNames":[]}]
//				};
//		var html = template('convs', data);
//		var $conDOM = $(html);
//		$('#thelist').html($conDOM);
//		global.proxy.getPostList(JSON.stringify(param));
		setTimeout(function(){ callSDK("getPostList",param);},global.proxy?80:120);
		/*$.ajax({
//			url:r_url + '?_='+new Date().getTime(),
			url:'http://172.20.8.189:9090/sysadmin/plugins/friends/dynamic/wallspace/postList',
			type:"post",
			dataType:'json',
			data:JSON.stringify(param),
			success:function(res_data){
				var data = {
						con_lists: res_data
				};
				var html = template('convs', data);
				var $conDOM = $(html);
				var $tem_imgs = $conDOM.find('img');
				$conDOM.find('img:not(.face-pic)').each(function(i){
						$(this).attr('src', 'http://m.qpic.cn/psb?/V13JxPiP2M8tIN/4y8atM4MOiaSphhN9TNiT2HDNUF4aeQLyd01k0s0640!/m/dBwBAAAAAAAA&ek=1&kp=1&pt=0&bo=gAJVAwAAAAAFAPc!&su=059567857&t=5#sce=5-4-3&rf=-0-0&appid=311');
				});
//				var imgs_str = '<div class="img-can">';
//				if($tem_imgs.length>1){
//					$tem_imgs.each(function(i){
//						imgs_str+='<p style="background:url('+$(this).attr('src')+')"></p>';
//						$(this).remove();
//					});
//					imgs_str+='</div>';
//					html = imgs_str+$conDOM.html();
//					$conDOM.prepend(imgs_str);
//				}
//				alert(html)
				$('#thelist').html($conDOM);
				global.myScroll.refresh();
			},
			error:function(data){
			}
		});*/
	}

	refreshPage();
	/**
	 * 下拉刷新
	 * myScroll.refresh();
	 */
	function pullDownAction () {
		refreshPage();
	}

	/**
	 * 上拉加载更多
	 * myScroll.refresh();
	 */
	function pullUpAction () {
		var param = {
//			wsid:localStorage.cur_space_id,
			wsid:curSpaceId,
			userId:"_wallspaceJID_",
			page:++window.cur_page,
			pageSize:20
		};
		callSDK("getPostList",param);
	}

	

	//禁止tochmove事件
//	document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
	//初始化绑定iScroll控件
	document.addEventListener('DOMContentLoaded', loaded, false);


	$(document).on('error','img',function(e){
	});

	


    /**
    * 选择本地文件后的回调方法
    */
	function onUploadImgChange(sender){
        var filePath = sender.value;
        //var fileExt = filePath.substring(filePath.lastIndexOf(".")).toLowerCase();
        try{
            if(typeof FileReader !== 'undefined'){
               for(var i=0;i<sender.files.length;i++){
		               var file = sender.files[i];
		               console.log(file)
		                var fileSize = file.fileSize || file.size;
		              	if(checkFileSize(fileSize)){
		                    var reader = new FileReader();
		                    reader.onload = (function(f){
		                    	return function(e){
		                    		var _p = document.createElement("p");
									_p.style.backgroundImage = "url("+this.result+")";//'<p style="background:url('+filePath+')"></p>'
									/*_img = document.createElement("img");
									_img.attributes['picType'] = 'pic';
									_img.src = data_cbk[i];
									_img.style.width = '40%';
									_img.style.margin = '0 5%';
									_img.style.float = 'left';*/
									document.getElementById('imgcont').appendChild(_p);
		                    	}
		                    	
		                    })(file);
		                    reader.readAsDataURL(file);
		              	}
               }
            }
        }catch(e){
            ("出错了");
        }
    }

   



	/*
	* 发布会话
	*/
	global.sendmessage = function (content,fid,gid){
		//会话数据
    	var _data = {};
		var  $imgList = $('#imgcont').children('p');
		var _content = ($.trim(content));
		if(!_content&&!$('#imgcont').html()){
			showMessage('动态内容不能为空');
			return false;
		}
    	var pic_list = new Array();
    	if($imgList.length > 0){
			$imgList.each(function(i){
				if($(this).css('backgroundImage')){
					var img_url = $(this).css('backgroundImage');
					var path = img_url.split('(')[1].split(')')[0].replace("file://","");
					if(-1!=path.indexOf('base64')){
						path = path.replace(/data:image\/\w{3,4};base64,/g, '')
					}
					pic_list.push(path);
				}
			});
			

    	}
    		
		var topost = {
			files:pic_list,
			post:{
//				sid:global.localStorage.cur_space_id,
				sid:curSpaceId,
				username:"_wallspaceJID_",
				cont: _content.replace(/"/g,'\\"'),
				documents: [],
				docType: 1,
				remoteUrl: "",
				remoteAbstract: "",
				praiseUsers: []

			}
		}
		console.log(topost.post.cont)
//		global.proxy.publishPost(JSON.stringify(topost));
		setTimeout(function(){
					$('#sendmesspage').animate({left:'100%'},200);//.css('left','0%');
				},50);
		$('#messagefield').html('');
		callSDK("publishPost",topost);
//  		$.ajax({
//				url: global.ESN_URL + '/speech/detail/mid/5',
//				type:'post',
//				dataType:'json',
//				data:'{"cont":"'+_content+'","fid":"'+fid+'","gid":"'+gid+'","fm":"2"}',
//				success:function(){
//					//...
//					_content = null;
//					$imgList.remove();
//					refreshPage();
//				},
//				error: function(e){
//					_content = null;
//				}
//			});
    }

	var rep_para= {}; //参数
//	var rep_para= new Array(); //参数
	var $footer=$("footer");
//	var $reply_input = $("input[name='replycont']"); //输入框
	var $reply_input = $("div[name='replycont']"); //输入框
	global.isCanReply=true;

	

	function readyForReply(post,status,reply){
		if(!global.isCanReply){
			return;
		}
		clearState();
		if(reply){
			reply.addClass('reply-item-back');
		}
		$reply_input.val("").trigger('focus');
		$footer.removeClass('hidden');
		post.addClass('ing');
//		var id = post.attr('id');
//		var fid =  post.attr('fid');
//		var replyid = id;
//		var replyuid;
//		var replyname;
		var userfrom = "_wallspaceJID_";
		var data = {
			reply:{
				pid : post.attr('id'),
				userFrom:'_wallspaceJID_'
			}
		}
		switch(status){
			case 0: replyuid  = post.find('.p-auth .u-name').attr('uid');
					replyName = post.find('.p-auth .u-name').html();
				  break;
			case 1: replyuid = reply.attr('fromid');
					replyName = reply.find('.reply').html();
				  break;
			default:break;
		}
		rep_para = data;
//		readyForParam(id,fid,replyid,replyuid,replyname);
		global.cache.replyuid = replyuid;
		global.cache.replyName = replyName;
		$reply_input.attr('placeholder','回复：'+replyName);
	}

	function readyForParam(id,fid,replyid,replyuid,replyname){
		rep_para['id']=id;
		rep_para['feed_id']=fid;
		rep_para['feed_type']=15;
		rep_para['feed_reply_to_id']=replyid;
		rep_para['feed_reply_obj_mid']=replyid;
		rep_para['feed_reply_to_mid']=replyuid;
		rep_para['feed_reply_to_name']=replyname;
	}


	/**
	* 回复
	*/
	$("#send_btn").on("tap",function(){
//		var reply_cont = $reply_input.val();
		var reply_cont = $reply_input.html();
		global.cache.replyCont = reply_cont;
		if(reply_cont.length == 0 || !$(".post-item.ing")){
			if(reply_cont.length == 0){
				$reply_input.trigger('focus');
			}
			return;
		}

		global.isCanReply=false;
		rep_para.reply.cont = ($(this).siblings('.replycont').html().replace(/"/g,'\\"'));
		rep_para.reply.userTo = global.cache.replyuid;
		$reply_input.html('');
//		global.proxy.addTextReply(JSON.stringify(rep_para));
		callSDK("addTextReply",rep_para);
		$footer.addClass('hidden');

	});
	
	
	/*事件绑定 start*/
	$('.switch-tap').on('tap',function(e){
		$('.switch-space').find('.drop-menu').toggleClass('hidden');
	});
	
	
	$(document).on('tap',function(e){
		var $target = $(e.target);
		if(!$(e.target).closest('.switch-tap').length&& 
		!$(e.target).closest('ul').hasClass('drop-menu')){
			$('.drop-menu').addClass('hidden');
		}
		if(!$target.closest('.face-wraper').length && !$target.hasClass('add-face')){
			$('#sendmesspage').find('.faces-container').html('');
		}
//		
		$target.closest('div').hasClass("reply-btn") ? readyForReply($target.closest(".post-item"),0) : $target.hasClass("reply-item") ? readyForReply($target.closest(".post-item"),1,$target) :(function(){if(!($target.hasClass("replycont")||($target.closest('#reply-ops').length)||$target.hasClass('add-face')||$target.closest('div').hasClass('face-list'))){$footer.addClass('hidden');clearState();}})();
	});
	
	$('.add-face').on('tap',function(){
		$target_face_area = $(this).closest('.face-node').find('.faces-container');
		if(!$target_face_area.find('.face-wraper').length){
			$target_face_area.append(face_list);
		}
		$('.face-list ul').width(window_width);
		$('.msg-extra').addClass('hidden');
	});
	
	var face_page = 0;
	$(document).on('touchmove','.face-list',function(e){e.preventDefault();});
	$(document).on('swipeLeft','.face-list',function(e){
		if(face_page>=2)return;
		face_page++;
		$(this).css('marginLeft',-face_page*window_width+'px')
	});
	$(document).on('swipeRight','.face-list',function(){
		if(face_page<=0)return;
		face_page--;
		$(this).css('marginLeft', -face_page*window_width+'px')
	});
	
	/*表情*/
	$(document).on('tap','.face-list img',function(){
		var $tem_img = $(this).clone();
		$tem_img.addClass('face-pic')
		var $face_node = $(this).closest('.face-node');
		$face_node.find('.face-allow').append($tem_img);
		if($face_node.attr('id')=='sendmesspage'){
			$face_node.find('.place-holder').hide();
		}
//		exeSucess = "document.execCommand('insertHtml', false,'<img src=\""
//      + 'https://www.baidu.com/img/bdlogo.png'+ "\" height=auto width=200 ></img>');";
//      console.log(exeSucess)
//      eval(exeSucess);
		
		
		
	});

	$('#messagefield').on('keyup tap blur',function(e){
		if(e.type == 'blur'){
			$('.msg-extra').addClass('hidden');	
		}else{
			$('.msg-extra').removeClass('hidden');
			$(this).siblings('.place-holder').hide();
		}
		
	});
	$('#messagefield').on('paste',function(e){
			e.preventDefault();
			var clip_cont = (e.clipboardData.getData('text/plain'));
			console.log(e.clipboardData.getData('text/plain'));
			$(this).append(htmlspecialchars(clip_cont));
			
	});
	$(this).closest('.face-node').find('.faces-container').html('');
	/*
	 * 调用本地的摄像头
	 */
	$('#catchphoto').on('tap',function(e){
		if(window.proxy){
			window.proxy.startPhotoActivity();
		}else{
			$('#pic_model').removeClass('hidden')
		}
	});

	$('#ios_photo,#ios_loc_pic').on('change', function(e){
		onUploadImgChange(this);
	});
	/*点赞*/
	$(document).on('tap','.post-foot .praise',function(){
		data={
			praise:{
				pid:$(this).closest('.post-item').attr('id'),
				userFrom:'_wallspaceJID_',
				userTo:$(this).closest('.post-item').attr('id')
			}
		};
		var $praise_count = $(this).find('.praise-num');
		if(!$(this).hasClass('praised')){
			$(this).append('<span class="fly-plus1">+1</span>')
			$praise_count.html(parseInt($praise_count.html(),10)+1 ); 
			$(this).addClass('praised');
//			global.proxy.addPraiseReply(JSON.stringify(data));
			callSDK("addPraiseReply",data)
		}else{
			$praise_count.html(parseInt($praise_count.html(),10)-1 ); 
			$(this).removeClass('praised');
//			global.proxy.cancelPraiseReply(JSON.stringify(data));
			callSDK("cancelPraiseReply",data);
			
		}
	});
	$(document).on('tap','.sps-link',function(){
		switchSpace($(this).attr('sid'),$(this).html());
	});
	$(document).on('tap','.p-cont img:not(.face-pic),.p-cont p',function(e){
		var $target = $(e.target);
		if($target.is('p')){
			var $list = $(this).parent().find('p');
			var p_index = $list.index($(this));
			var	append_str = '<ul class="clearfix" style="width:'+$list.length*window_width+'px;-webkit-transform:translateX('+-p_index*window_width+'px);">';
			$list.each(function(){
				var img_url = $(this).css('backgroundImage');
				var path = img_url.split('(')[1].split(')')[0];
					append_str+= ('<li style="width:'+window_width+'px"><img src="'+path+'"/></li>');
//					append_str+= ('<li style="width:'+window_width+'px"><img src="'+'http://www.topdesignmag.com/wp-content/uploads/2010/12/2216.jpg'+'"/></li>');
			});
			append_str+="</ul>"
		}else{
			append_str = '<ul class="clearfix" style="width:'+window_width+'px;-webkit-transform:translateX(0px);">';
			append_str+= ('<li style="width:'+window_width+'px"><img src="'+$(this).attr('src')+'"/></li></ul>');
//			$("#pic_viewer").append($(this).clone()).removeClass('hidden')
		}
		$("#pic_viewer").append(append_str).removeClass('hidden');
	});
	var img_index = 0,startX,startTime,diff,cur_X,$pic_container = $('#pic_viewer').find('ul'),
		limit = $('#pic_viewer').find('li').length;
	$(document).on('touchstart','#pic_viewer img',function(e){
		$('#pic_viewer').find('ul').css('-webkit-transition-duration','0ms')
			cur_X = getCurX();
			startTime = new Date;
			var touch_point = e.touches[0];
			startX = touch_point.clientX;
			//alert(touch_point.clientX)
			limit = $('#pic_viewer').find('li').length;
	})
	$(document).on('touchmove','#pic_viewer img',function(e){
			var touch_point = e.touches[0];
			diff = (touch_point.clientX-startX);
			
//			console.log("diff:"+diff+"cur:"+cur_X+(parseInt(diff,10)+parseInt(cur_X,10)))
//			console.log("diff:"+diff+"curX:"+cur_X+" "+parseInt((diff+cur_X),10));
			$('#pic_viewer').find('ul').css('-webkit-transform','translateX('+(parseInt(diff,10)+parseInt(cur_X,10))+'px)');
//			$('#pic_viewer').find('ul').css('-webkit-transform','translateX('+(parseInt(diff,10)+parseInt(cur_X,10))+'px)');
	})
	$(document).on('touchend','#pic_viewer img',function(e){
			var duration = new Date - startTime;	
			if(!(diff<0&&img_index == limit-1)&&!(diff>0&&img_index == 0)
			&&(Math.abs(diff)>window_width/2 ||( (duration<250)&&Math.abs(diff)>20))){
				diff<0?img_index++:img_index--;
//				alert(-window_width*img_index)
					$('#pic_viewer img').removeClass('enlarge');
					$('#pic_viewer img').css('-webkit-transform','scale(1.0);')

				$('#pic_viewer').find('ul').css('-webkit-transition-duration','500ms')
				setTimeout(function(){
					$('#pic_viewer').find('ul').css('-webkit-transform','translateX('+-window_width*img_index+'px)');
				},50);
				cur_X = getCurX();
			}else{
					$('#pic_viewer').find('ul').css('-webkit-transition-duration','500ms')
					$('#pic_viewer').find('ul').css('-webkit-transform','translateX('+cur_X+'px)');
				
			}
			/*if((diff<0&&img_index == limit-1)||(diff>0&&img_index == 0)
			||(Math.abs(diff)<window_width/2 && (duration>250)||diff<20)){
					$('#pic_viewer').find('ul').css('-webkit-transition-duration','200ms')
					$('#pic_viewer').find('ul').css('-webkit-transform','translateX('+cur_X+'px)');
			}else{
				diff<0?img_index++:img_index--;
//				alert(-window_width*img_index)
				$('#pic_viewer').find('ul').css('-webkit-transition-duration','200ms')
				setTimeout(function(){
					$('#pic_viewer').find('ul').css('-webkit-transform','translateX('+-window_width*img_index+'px)');
				},200);
				cur_X = getCurX();
				
			}*/
			diff = startX = 0;
	})
	var type;
	$(document).on('doubleTap','#pic_viewer *',function(e){
		type = e.type;
		var active_img = $('#pic_viewer img').eq(img_index);
				if(active_img.hasClass('enlarge')){
					active_img.removeClass('enlarge');
					active_img.css('-webkit-transform','scale(1.0);')
				}else{
					active_img.addClass('enlarge');
					active_img.css('-webkit-transform','scale(1.8);')
				}
	});
	$(document).on('tap','#pic_viewer *',function(e){
		type = e.type;
		setTimeout(function(){console.log(type); if(type=='tap'){
			diff = startX = cur_X = img_index = 0;
			$('#pic_viewer').html('').addClass('hidden');
		}},300);
	});
	$(document).on('focusin','.face-allow',function(){
		$(this).closest('.face-node').find('.faces-container').html('');
	});
//	$('header').on('touchmove', function (e) { e.preventDefault(); }, false);
	/*ios拍照、本地图片*/
	$(document).on('tap','#ios_photo,#ios_pic',function(){
		$('#pic_model').addClass('hidden');
		location.href = "wallspace://"+($(this).attr('id')=='ios_pic'?'localPic':'takePhoto');
	});
	$(document).on('tap','#pic_model',function(e){
		 if($(e.target).find('div').length){
		 	$('#pic_model').addClass('hidden');
		 }
	});
	$(document).on('tap','#pic_model .cancel',function(e){
		 	$('#pic_model').addClass('hidden');
	});
	
	var publish_flag = true;
	$(document).on('tap','#sendmessage',function(){
		if(!publish_flag){return false;}
		publish_flag = false;
		$('#sendmesspage').removeClass('hidden');
		setTimeout(function(){
			$('#sendmesspage').animate({left:'0%'},200);//.css('left','0%');
			publish_flag = true;
		},50)
	})
	$(document).on('tap','#sendmesspage .exit',function(){
		if( confirm("确认放弃发布？")){
				$('#sendmesspage').animate({left:'100%'},200,'ease-in',function(){
					$('#sendmesspage').addClass('hidden');
				})//..css('left','100%');
				$('#messagefield').html('');
//				setTimeout(function(){
//					$('#sendmesspage').addClass('hidden');
//				},50000)
		}
	});
	document.getElementById('pic_viewer').addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
	$(document).on('tap','#befirstposter',function(){$('#sendmessage').trigger('tap')})
	
	/*发布动态*/
	$('#publish_btn').on('touchend',function(e){
		e.preventDefault();
		sendmessage($('#messagefield').html(),2,1);
		$('messagefield').val('');
	})
	//$(document).on('keyup','#messagefield',function(e){/*for(var obj in e){console.log(obj)}}*/alert(e.keyCode);})
	
	/*事件绑定end*/
	
	
	
	//清除上次点击缓存
	global.clearState = function(){
		$(".post-item.ing").removeClass("ing");
		$('.reply-item.reply-item-back').removeClass('reply-item-back');
	}
	
	 //检测文件大小
    function checkFileSize(fileSize){
		if(fileSize > 1024*1024*5){
			alert("您上传的文件大于5M,请重新选择！");
			return false;
		}
        return true;
    }
    function getCurX(){
    	var trans = $(document).find('#pic_viewer ul').css('-webkit-transform');
			 var tem = $.inArray(trans,[undefined,'none',null])>=0?0:trans.match(/\-?[0-9]+/g)[0];
			  return tem;
    }
    function htmlspecialchars(str)    
	{    
	    str = str.replace(/&/g, '&amp;');  
	    str = str.replace(/</g, '&lt;');  
	    str = str.replace(/>/g, '&gt;');  
	    str = str.replace(/"/g, '&quot;');  
	    str = str.replace(/'/g, '&#039;');  
	    return str;  
	}  

})(window);
