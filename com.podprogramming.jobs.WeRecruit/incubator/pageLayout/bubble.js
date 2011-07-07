$(function() {
	$(".home").hover(
		function() {
			$(".cloud" ).stop().animate({'left':'520px'}, 300, 'easeOutBack');
			$(".camera").stop().animate({'left':'590px'}, 300, 'easeOutBack');
		},
		function() {
			$(".cloud" ).stop().animate({'left':'410px'}, 300, 'easeOutBack');
			$(".camera").stop().animate({'left':'480px'}, 300, 'easeOutBack');
		}
	);
	$(".cloud").hover(
		function() {
			$(".camera").stop().animate({'left':'590px'}, 300, 'easeOutBack');
		},
		function() {
			$(".camera").stop().animate({'left':'480px'}, 300, 'easeOutBack');
		}
	);

    $('#nav > div').hover(
    function () {
        var $this = $(this);
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
    },
    function () {
        var $this = $(this);
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
    }
);
});