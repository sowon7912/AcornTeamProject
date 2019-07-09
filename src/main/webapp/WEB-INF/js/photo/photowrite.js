$(function(){
	$(".Photo-Write-Form_footer_submit").click(function(){
		alert("글이 전송되었습니다")
		$.ajax({
			url:'write.do',
			type:'post',
			data:{
				'member_no': $('#member_no').val(),
				'photo_image': "test",
				'photo_content': $('#photo_content').val(),
				'photo_hometype': $('#photo_hometype').val(),
				'photo_pyeong': $('#photo_pyeong').val(),
				'hashtag': $('#hashtag').val()
			},
		});
	});
});