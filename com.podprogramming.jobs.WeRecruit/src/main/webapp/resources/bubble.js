$(function() {
	var shiftHome = function() {
		$(".cloud" ).stop().animate({'left':'520px'}, 300, 'easeOutBack');
		$(".search").stop().animate({'left':'590px'}, 300, 'easeOutBack');
	};
	var unshiftHome = 		function() {
		$(".cloud" ).stop().animate({'left':'410px'}, 300, 'easeOutBack');
		$(".search").stop().animate({'left':'480px'}, 300, 'easeOutBack');
	};

	var shiftCloud = 		function() {
		$(".search").stop().animate({'left':'590px'}, 300, 'easeOutBack');
	};
	var unshiftCloud = 		function() {
		$(".search").stop().animate({'left':'480px'}, 300, 'easeOutBack');
	};

	
	$(".home").hover(shiftHome, unshiftHome);
	$(".cloud").hover(shiftCloud, unshiftCloud);
	
	var revealMenu = function($item) {
		var $this = $item;
        //$this.stop().animate({'top':'70px'}, 300, 'easeOutBack');
        $this.find('img').stop().animate({
            'width'     :'199px',
            'height'    :'199px',
            'top'       :'-25px',
            'left'      :'-25px',
            'opacity'   :'1.0'
        },500,'easeOutBack',function(){
            $(this).parent().find('ul').fadeIn(700);
        });
        $this.find('a:first,h2').addClass('active');
	};
	
	var unrevealMenu = function($item) {
		var $this = $item;
        //$this.stop().animate({'top':'22px'}, 300, 'easeOutBack');
        $this.find('ul').hide();
        $this.find('img').stop().animate({
            'width'     :'52px',
            'height'    :'52px',
            'top'       :'0px',
            'left'      :'0px',
            'opacity'   :'0.1'
        },500,'easeOutBack');
        $this.find('a:first,h2').removeClass('active');
	};

    $('#nav > div').hover(
	    function () { revealMenu($(this)); },
	    function () { unrevealMenu($(this)); });

	/**
	 * Hack for demo: hover is not available thus one simulate it by click...
	 */
	$(".item a").click(function() {
		var $this = $(this).parent();
		if($this.hasClass("hover")) {
			$this.removeClass("hover");
			if($this.hasClass("cloud")) {
				unshiftCloud();
			}
			else if($this.hasClass("home")) {
				unshiftHome();
			}
			unrevealMenu($this);
		}
		else {
			$this.addClass("hover");
			if($this.hasClass("cloud")) {
				shiftCloud();
			}
			else if($this.hasClass("home")) {
				shiftHome();
			}
			revealMenu($this);
		}
	});
	$(".item ul li a").click(function() {
		var $this = $(this);
		if($this.hasClass("hover")) {
			$this.removeClass("hover");
		}
		else {
			$this.addClass("hover");
		}
	});
	$("#click-n-clear").click(function() {
		$(".hover").removeClass("hover");
	});
});