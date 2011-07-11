var selectSkills = function() {
	$("#sk").children().each(function(index,elem){
		var $elem = $(elem);
		if($elem.attr("id")!="sk_soc") {
			setTimeout(function() {
				$(elem).addClass("apply-icon");
			}, (index+1)*750);
		}
	});
};
$(function() {
	$("#click-n-clear").click(function() {
		$(".hover").removeClass("hover");
	});
	$("#activate-hack").click(function() {
		$(".item a").bind('click', func1);
		$(".item ul li a").bind('click', func2);
		
	});
	$("#deactivate-hack").click(function() {
		$(".item a").unbind('click', func1);
		$(".item ul li a").unbind('click', func2);
		$("#click-n-clear").unbind('click', func3);
	});
});

/**
 * Hack for demo: hover is not available thus one simulate it by click...
 */
var func1 = function() {
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
};
var func2 = function() {
	$(this).toggleClass("hover");
};
