$(document).ready(function() {

  $('#btn-register').click(function() {
    $('#v-login').hide();
    $('#v-register').fadeIn('1000');
  });

  $('#btn-recover').click(function() {
    $('#v-login').hide();
    $('#v-recover').fadeIn('1000');
  });

  $('#btn-login').click(function() {
    $('#v-register').hide();
    $('#v-login').fadeIn('1000');
  });

  $('#btn-login2').click(function() {
    $('#v-recover').hide();
    $('#v-login').fadeIn('1000');
  });

});

!function ($) {

  $(function(){

	// tooltip
    $('.block_head').tooltip({
      selector: "a[rel=tooltip]"
    })
  
	// tooltip
    $('.block_content').tooltip({
      selector: "a[rel=tooltip2]",
	  placement: 'left'
    })

    // popover
    $("a[rel=popover]")
      .popover()
      .click(function(e) {
        e.preventDefault()
      })
  })

// Modified from the original jsonpi https://github.com/benvinegar/jquery-jsonpi
$.ajaxTransport('jsonpi', function(opts, originalOptions, jqXHR) {
  var url = opts.url;

  return {
    send: function(_, completeCallback) {
      var name = 'jQuery_iframe_' + jQuery.now()
        , iframe, form

      iframe = $('<iframe>')
        .attr('name', name)
        .appendTo('head')

      form = $('<form>')
        .attr('method', opts.type) // GET or POST
        .attr('action', url)
        .attr('target', name)

      $.each(opts.params, function(k, v) {

        $('<input>')
          .attr('type', 'hidden')
          .attr('name', k)
          .attr('value', typeof v == 'string' ? v : JSON.stringify(v))
          .appendTo(form)
      })

      form.appendTo('body').submit()
    }
  }
})

}(window.jQuery)