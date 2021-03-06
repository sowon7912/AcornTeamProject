
/*************************************************************
					MAIN BANNER SLIDER SETTINGS
*************************************************************/
var time; //슬라이드 넘어가는 시간
var $carouselLi;
var carouselCount; //carousel 사진 갯수
var currentIndex; //현재 보여지는 슬라이드 인덱스 값
var caInterval;

//사진 연결
var imgW;
$(document).ready(function(){
	carouselInit(500, 2000);
});

$(window).resize(function() {
	carousel_setImgPosition();
});

//초기 설정
function carouselInit( height, t ){
	time = t;
	$(".carousel_section").height(height);
	$carouselLi = $(".carousel_section > ul > li");
	carouselCount = $carouselLi.length;
	carousel_setImgPosition();
	carousel();
}

function carousel_setImgPosition(){
	
	imgW = $carouselLi.width(); //사진 한장의 너비
	//이미지 위치 조정
	for(var i = 0; i<carouselCount; i++){
		if(i==currentIndex){
			$carouselLi.eq(i).css("left", 0);
		}else{
			$carouselLi.eq(i).css("left", imgW);
		}
	}
}

function carousel(){
	//사진 넘기기
	//사진 하나가 넘어간 후 다시 꼬리에 붙어야함
	//화면에 보이는 슬라이드만 보이기
	ceInterval = setInterval(function() {
		var left = "-" + imgW;
	
		//현재 슬라이드 왼쪽으로 이동 (마이너스지정)
		$carouselLi.eq(currentIndex).animate( {left: left}, function(){
			$carouselLi.eq(currentIndex).css("left", imgW);
		
			if( currentIndex == ( carouselCount -1 ))
			{
				currentIndex = 0;
			}
			else
			{
				currentIndex ++;
			}
		});
	
		//다음 슬라이드 화면으로
		if( currentIndex == ( carouselCount - 1 ))
		{
			//마지막 슬라이드가 넘어갈 때는 처음 슬라이드가 보이도록
			$carouselLi.eq(0).animate( { left: 0} );
		}
		else
		{
			$carouselLi.eq(currentIndex+1).animate( { left: 0 } );
		}
	}, time);
}


//current position
var pos = 0;
//number of slides
var totalSlides;
//get the slide width
var sliderWidth;
var slideInterval = 1000 * 2.5;
var imgUrl;
$(document).ready(function(){
	/*****************
      LOGIN, LOGOUT ALERT
	 *****************/
	$("#flash_notice").delay(1500).fadeOut();
	
	totalSlides = $('.home-header__banner-container ul.home-header__banner-items li').length;
	//sliderWidth = $('.home-header__banner-container').width();
    /*****************
     BUILD THE SLIDER
    *****************/
    //set width to be 'x' times the number of slides
    //$('.home-header__banner-container ul.home-header__banner').width(sliderWidth*totalSlides);
    
    //next slide    
    $('#next').click(function(){
        slideRight();
    });
    
    //previous slide
    $('#previous').click(function(){
        slideLeft();
    });
       
    
    /*************************
     //*> OPTIONAL SETTINGS
    ************************/
    //automatic slider
    var autoSlider = setInterval(slideRight, slideInterval);
    
    //for each slide 
    $.each($('.home-header__banner-container ul.home-header__banner-items li'), function() { 
       //create a pagination
       var li = document.createElement('li');
       $('#pagination-wrap ul').append(li);    
    });
    
    //pagination
    pagination();
    
    //hide/show controls/btns when hover
    //pause automatic slide when hover
    $('.home-header__banner-container').hover(
      function(){ $(this).addClass('active'); clearInterval(autoSlider); }, 
      function(){ $(this).removeClass('active'); autoSlider = setInterval(slideRight, slideInterval); }
    );
    
});//DOCUMENT READY

$(window).resize(function(){
	resetImageSize();
});

function resetImageSize(){
	sliderWidth = $('.home-header__banner-container').width();
}
	/***********
	 SLIDE LEFT
	************/
	function slideLeft(){
	    pos--;
	    if(pos==-1){ pos = totalSlides-1; }
	    imgUrl = $('.home-header__banner-items li').eq(pos).find('.pc-banner').css('background-image');
	    $('.home-header__banner-container ul.home-header__banner .pc-banner').css('background-image', imgUrl);
	    
	    //*> optional
	    pagination();
	}

	/************
	 SLIDE RIGHT
	*************/
	function slideRight(){
	    pos++;
	    if(pos==totalSlides){ pos = 0; }
	    imgUrl = $('.home-header__banner-items li').eq(pos).find('.pc-banner').css('background-image');
	    $('.home-header__banner-container ul.home-header__banner .pc-banner').css('background-image', imgUrl);
	    
	    //*> optional 
	    pagination();
	}
	
	/************************
	 //*> OPTIONAL SETTINGS
	************************/
	function pagination(){
    	$('#pagination-wrap ul li').removeClass('active');
    	$('#pagination-wrap ul li:eq('+pos+')').addClass('active');
	}
	
/*************************************************************
					오늘의 스토리 SETTINGS
*************************************************************/
$('.main_zip_content_link').hover(
	function(){ $(this).find('.main_zip_content_title').addClass('active'); }, 
	function(){ $(this).find('.main_zip_content_title').removeClass('active'); }
);
		    
//$('.main_zip_image-wrap img').hover(
//	function(){$('.main_zip_content_title').addClass('active');},
//	function(){$('.main_zip_content_title').removeClass('active');}
//);
			
/*************************************************************
					오늘의딜 SLIDER SETTINGS
*************************************************************/
//current position
var deal_pos = 0;
//number of slides
var deal_totalSlides;
//get the slide width
var deal_sliderWidth;
$(window).resize(function(){
	deal_sliderWidth = $('.scroller__content ul li').width()+20; 
});
$(document).ready(function(){
	deal_totalSlides = $('.scroller__content ul li').length;
	deal_sliderWidth = $('.scroller__content ul li').width()+20; 
 
	/*****************
     BUILD THE SLIDER
    *****************/
    //set width to be 'x' times the number of slides
    //$('.scroller__content ul.row.home-recommends__content.home-scroll').width(deal_sliderWidth*deal_totalSlides);
    
    //next slide    
    $('.scroller__ui__right').click(function(){
        deal_slideRight();
    });
    
    //previous slide
    $('.scroller__ui__left').click(function(){
        deal_slideLeft();
    });
       
    
    /*************************
     //*> OPTIONAL SETTINGS
    ************************/
    //pagination
    deal_pagination();
    
//    $('.scroller__content').hover(
//    		function(){ $(this).addClass('active');}, 
//    		function(){ $(this).removeClass('active');}
//    );
     
});//DOCUMENT READY

	
	/***********
	 SLIDE LEFT
	************/
	function deal_slideLeft(){
	    deal_pos--;
	    if(deal_pos==-1){ deal_pos = deal_totalSlides-1; }
	    //$('.scroller__content ul.row.home-recommends__content.home-scroll li:first').css('margin-left', -(deal_sliderWidth*deal_pos));
	    $('div.scroller__content.home-scroll-wrap').scrollLeft((deal_sliderWidth*deal_pos));
	    
	    //*> optional
	    deal_pagination();
	}
	
	/************
	 SLIDE RIGHT
	*************/
	function deal_slideRight(){
	    deal_pos++;
	    if(deal_pos==deal_totalSlides){ deal_pos = 0; }
	    //$('.scroller__content ul.row.home-recommends__content.home-scroll li:first').css('margin-left', -(deal_sliderWidth*deal_pos));
	    $('div.scroller__content.home-scroll-wrap').scrollLeft((deal_sliderWidth*deal_pos));
	    
	    //*> optional 
	    deal_pagination();
	}
	
	
	/************************
	 //*> OPTIONAL SETTINGS
	************************/
	
	function deal_pagination(){
	    //$('.scroller__ui').removeClass('active');
	    /*$('.scroller__ui ul li:eq('+deal_pos+')').addClass('active');*/
	    /*if (deal_pos == 0) {
	    	$('.scroller__ui__left').hide();
	    } else {
	    	$('.scroller__ui__left').show();
	    }
		
	    if (deal_pos == (deal_totalSlides - 11)) {
	    	$('.scroller__ui__right').hide();
	    } else {
	    	$('.scroller__ui__right').show();
	    }*/
		var scrollLeft = $('div.scroller__content.home-scroll-wrap').scrollLeft();
		if (scrollLeft == 0) {
	    	$('.scroller__ui__left').hide();
	    } else {
	    	$('.scroller__ui__left').show();
	    }
		var wrapWidth = $('div.scroller__content.home-scroll-wrap').width();
	    if ((scrollLeft+deal_sliderWidth) >= (wrapWidth - deal_sliderWidth)) {
	    	$('.scroller__ui__right').hide();
	    } else {
	    	$('.scroller__ui__right').show();
	    }
	}
   

/*************************************************************
					오늘의 인기사진 RANK
*************************************************************/
	
	
	
/*************************************************************
						베스트 100
*************************************************************/
function changeBestCategory(category){
	$('.production-rank-feed__list-wrap').hide();
	$('#'+category).show();
}
function all() {
	$('div#furniture').removeClass('hide');
}
  
function furniture() {
	$('div#furniture').removeClass('hide');
	$('#categoryNm').val('가구');
}
 
function electronics() {
	$('div#furniture').removeClass('hide');
	$('#categoryNm').val('가전');
}
   
function interior() {
	$('div#furniture').removeClass('hide');
	$('#categoryNm').val('인테리어');
}
   