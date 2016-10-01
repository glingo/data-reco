
+function ($) {

	'use strict';

	function TableHeader (el, options) {
		this.$element  = $(el)
		this.options   = $.extend({}, Button.DEFAULTS, options)
	}
	
	TableHeader.prototype.DEFAULTS = {
	
	}
	
	function Plugin(option) {
		return this.each(function () {
		  var $this = $(this)
		  var data  = $this.data('table.header')

		  if (!data) $this.data('table.header', (data = new TableHeader(this)))
		  if (typeof option == 'string') data[option].call($this)
		})
	}

  var old = $.fn.alert

  $.fn.alert             = Plugin
  $.fn.alert.Constructor = Alert


  // ALERT NO CONFLICT
  // =================

  $.fn.alert.noConflict = function () {
    $.fn.alert = old
    return this
  }
}(jQuery);