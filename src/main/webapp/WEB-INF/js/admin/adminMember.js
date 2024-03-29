$(function(){
	getMemberList();
	
	$(document).on('click', '.list-paginator_page', function(){

		var currentPage = $(this).text();
		
		getCurrentPageList(currentPage);
	});
	
	$(document).on('click', '.list-paginator_prev', function(){
		
		var pageNum = $('.list-paginator_page.sm.selected').text();
		
		getCurrentPageList(pageNum-1);
		
	});
	
	$(document).on('click', '.list-paginator_next', function(){
		
		var pageNum = $('.list-paginator_page.sm.selected').text();
		
		getCurrentPageList(pageNum+1);
		
	});
	
	$(document).on('click', '.member-delete-btn', function(){
		
		var isDelete = confirm("해당 회원 정보를 삭제하시겠습니까?");
		var member_no = $(this).prev().val();
		var pageNum = $('.list-paginator_page.sm.selected').text();
		
		if(isDelete) {
			$.ajax({
				url: 'deleteMember',
				type: 'post',
				dataType: 'json',
				data: {
					'member_no': member_no
				},
				error:function(request,status,error){
				    alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
				},
				success: function(redata){
					alert("게시글 삭제를 완료했습니다.");
					getCurrentPageList(pageNum);
				}
			});
		}
	});
	
	$("#search-community").keyup(function(key){
		
		if(key.keyCode == 13) {
			var keyword = $(this).val().trim();
			console.log(keyword);
			
			if(keyword != "") {
				getFilterMemberList(keyword);
			} else {
				getMemberList();
			}
			
		}
	})
	
});


// 브라우저 시작할 때 데이터 가져오는 ajax
function getMemberList() {
	
	$.ajax({
		url: 'getMemberList',
		type: 'post',
		dataType: 'json',
		error:function(request,status,error){
		    alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}, success: function(redata){
			// 넘어오는 데이터 => List zrvo
			var replyList = "";
			var pagingList = "";
			
			$(".condition-status").text("회원 목록 ("+redata.totalCount+"개)");
			
			$("#page").empty();
			$("tbody").empty();
			
			var jsonType = JSON.parse(redata.jsonList);
			
			for(var i=0; i<jsonType.length; i++) {

				replyList += `
					<tr>
						<td>`+(jsonType.length-i)+`</td>
						<td style="text-overflow: ellipsis;">
							`+jsonType[i].member_id+`
						</td>
						<td>`+jsonType[i].member_domain+`</td>
						<td>`+jsonType[i].member_nickname+`</td>
						<td>
							<input type="hidden" class="member_seq_no" value="`+jsonType[i].member_no+`">
							<button type="button" class="member-delete-btn">삭제</button>
						</td>
					</tr>
				`
			}
			
			// 페이징 처리
			if(redata.startPage < redata.currentPage){
				pagingList += `
					<li style="display: inline-block; vertical-align: bottom;">
						<button class="list-paginator_prev" type="button">
							<img src="/image/common/left-arrow-outline-button.png">
						</button>
					</li>
				`
			}
			for(var i=redata.startPage; i<=redata.endPage; i++) {
				if(i != redata.currentPage) {
					pagingList +=`
						<li style="display: inline-block; vertical-align: bottom;">
							<button class="list-paginator_page sm" type="button">`+i+`</button>
						</li>
					`
				} else {
					pagingList +=`
						<li style="display: inline-block; vertical-align: bottom;">
							<button class="list-paginator_page sm selected" type="button">`+i+`</button>
						</li>
					`
				}
			}
			if(redata.currentPage < redata.totalPage) {
				pagingList += `
					<li style="display: inline-block; vertical-align: bottom;">
						<button class="list-paginator_next" type="button">
							<img src="/image/common/right-arrow-outline-button.png">
						</button>
					</li>
				`
			}
			
			$("#page").append(pagingList);
			$("tbody").append(replyList);
			
		}
	});
}

// 페이지 눌렀을 때 데이터 가져오는 ajax 함수
function getCurrentPageList(pageNum) {
	
	$.ajax({
		url: 'getMemberList',
		type: 'post',
		dataType: 'json',
		data: {
			'pageNum' : pageNum
		},
		error:function(request,status,error){
		    alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}, success: function(redata){
			// 넘어오는 데이터 => List zrvo
			var replyList = "";
			var pagingList = "";
			
			$("#page").empty();
			$("tbody").empty();
			
			var jsonType = JSON.parse(redata.jsonList);
			console.log(jsonType);
			
			for(var i=0; i<jsonType.length; i++) {

				replyList += `
					<tr>
						<td>`+(jsonType.length-i)+`</td>
						<td style="text-overflow: ellipsis;">
							`+jsonType[i].member_id+`
						</td>
						<td>`+jsonType[i].member_domain+`</td>
						<td>`+jsonType[i].member_nickname+`</td>
						<td>
							<input type="hidden" class="member_seq_no" value="`+jsonType[i].member_no+`">
							<button type="button" class="member-delete-btn">삭제</button>
						</td>
					</tr>
				`
			}
			
			console.log(redata);
			
			// 페이징 처리
			if(redata.startPage < redata.currentPage){
				pagingList += `
					<li style="display: inline-block; vertical-align: bottom;">
						<button class="list-paginator_prev" type="button">
							<img src="/image/common/left-arrow-outline-button.png">
						</button>
					</li>
				`
			}
			for(var i=redata.startPage; i<=redata.endPage; i++) {
				if(i != redata.currentPage) {
					pagingList +=`
						<li style="display: inline-block; vertical-align: bottom;">
							<button class="list-paginator_page sm" type="button">`+i+`</button>
						</li>
					`
				} else {
					pagingList +=`
						<li style="display: inline-block; vertical-align: bottom;">
							<button class="list-paginator_page sm selected" type="button">`+i+`</button>
						</li>
					`
				}
			}
			if(redata.currentPage < redata.totalPage) {
				pagingList += `
					<li style="display: inline-block; vertical-align: bottom;">
						<button class="list-paginator_next" type="button">
							<img src="/image/common/right-arrow-outline-button.png">
						</button>
					</li>
				`
			}
			
			$("#page").append(pagingList);
			$("tbody").append(replyList);
			
		}
	});
}

// 검색창에서 엔터 눌렀을 때 검색 조건에 맞는 게시물 리스트 가져오는 ajax 함수
function getFilterMemberList(keyword) {
	
	$.ajax({
		url: 'getFilterMemberList',
		type: 'post',
		dataType: 'json',
		data: {
			'keyword' : keyword
		},
		error:function(request,status,error){
		    alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}, success: function(redata){
			// 넘어오는 데이터 => List zrvo
			var replyList = "";
			var pagingList = "";
			
			$("#page").empty();
			$("tbody").empty();
			
			var jsonType = JSON.parse(redata.jsonList);
			console.log(jsonType);
			
			for(var i=0; i<jsonType.length; i++) {

				replyList += `
					<tr>
						<td>`+(jsonType.length-i)+`</td>
						<td style="text-overflow: ellipsis;">
							`+jsonType[i].member_id+`
						</td>
						<td>`+jsonType[i].member_domain+`</td>
						<td>`+jsonType[i].member_nickname+`</td>
						<td>
							<input type="hidden" class="member_seq_no" value="`+jsonType[i].member_no+`">
							<button type="button" class="member-delete-btn">삭제</button>
						</td>
					</tr>
				`
			}
			
			// 페이징 처리
			if(redata.startPage < redata.currentPage){
				pagingList += `
					<li style="display: inline-block; vertical-align: bottom;">
						<button class="list-paginator_prev" type="button">
							<img src="/image/common/left-arrow-outline-button.png">
						</button>
					</li>
				`
			}
			for(var i=redata.startPage; i<=redata.endPage; i++) {
				if(i != redata.currentPage) {
					pagingList +=`
						<li style="display: inline-block; vertical-align: bottom;">
							<button class="list-paginator_page sm" type="button">`+i+`</button>
						</li>
					`
				} else {
					pagingList +=`
						<li style="display: inline-block; vertical-align: bottom;">
							<button class="list-paginator_page sm selected" type="button">`+i+`</button>
						</li>
					`
				}
			}
			if(redata.currentPage < redata.totalPage) {
				pagingList += `
					<li style="display: inline-block; vertical-align: bottom;">
						<button class="list-paginator_next" type="button">
							<img src="/image/common/right-arrow-outline-button.png">
						</button>
					</li>
				`
			}
			
			$("#page").append(pagingList);
			$("tbody").append(replyList);
			
		}
	});
}