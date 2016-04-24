var URLUser, URLGenerarInformes;
var SERVER_URL = URLGenerarInformes || 'http://localhost:8080/VideoGenerator';
var user_info, user_id = sessionStorage.user_detail_id || localStorage.getItem('usuario_id');

/**
 * Pedir imagen nueva al servidor.
 */
var getImage = function() {
	$.ajax({
		url : SERVER_URL + "/process/getImage"
	}).then(function(serverInfo) {
		$('#image1').attr('src', "data:image/png;base64," + serverInfo);
	});
}

/**
 * Actualizar porcentaje de la barra de progreso circular.
 */
var updatingPercentage = function() {
	
	// Recuperar el valor introducido en el input de porcentaje
	var val = parseInt($(this).val());
	var $circle = $('#svg #bar');

	if (isNaN(val)) {
		val = 100;
	} else {
		// Normalizamos el valor de porcentaje a utilizar
		if (val < 0) {
			val = 0;
		}
		if (val > 100) {
			val = 100;
		}

		// Recuperamos el radio del circulo y calculamos su perimetro
		var r = $circle.attr('r');
		var p = 2 * Math.PI * r;

		// Calculamos el porcentaje de perimetro que representa el valor
		// introducido con respecto al perimetro total del circulo
		var pct = ((100 - val) / 100) * p;

		// El introducir el porcentaje calculado como valor del atributo
		// 'strokeDashoffset' tiene como efecto que solo se muestre ese
		// porcentaje de borde del circulo, lo que conseguira el efecto
		// de barra de progreso circular buscado
		$circle.css({
			strokeDashoffset : pct
		});

		// Introducimos el valor de porcentaje en el atributo de usuario
		// 'data-pct' que mostrara el porcentaje en el interior
		$('#cont').attr('data-pct', val);
	}
}

/**
 * Onload.
 */
$(function() {
	// Cuando se modifique el input del porcentaje actualizamos la barra de progreso
	$('#percent').on('change', updatingPercentage);
	
	// Actualizamos imagen cada 200 milisegundos
	setInterval(getImage, 125);
});
