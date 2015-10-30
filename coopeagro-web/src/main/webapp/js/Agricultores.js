$(document).ready(function() {
    listarDatos();
});

function limpiarDatos(){
    $('#isEditar').val('');
    $('#documento').val('');
    $('#tipoDocumento').val('');
    $('#nombre').val('');
    $('#apellido1').val('');
    $('#apellido2').val('');
    $('#telefono').val('');
    $('#celular').val('');
    $('#correo').val('');
    $('#fechaRegistro').val('');
    $('#direccion').val('');
}

function consultarAgricultor(documento, tipoDocumento){
    $.ajax({
        type    :"POST",
        url     :"Agricultor",
        dataType:"json",
        data    :{accion:"consultar", documento:documento, tipoDocumento:tipoDocumento},
        success: function(data) {
            if(data !== undefined){
                $("#documento").val(data.documento !== undefined ? data.documento : "");
                $("#tipoDocumento").val(data.tipoDocumento !== undefined ? data.tipoDocumento : "");
                $("#nombre").val(data.nombre !== undefined ? data.nombre : "");
                $("#apellido1").val(data.apellido1 !== undefined ? data.apellido1 : "");
                $("#apellido2").val(data.apellido2 !== undefined ? data.apellido2 : "");
                $("#telefono").val(data.telefono !== undefined ? data.telefono : "");
                $("#celular").val(data.celular !== undefined ? data.celular : "");
                $("#correo").val(data.correo !== undefined ? data.correo : "");
                $("#fechaRegistro").val(data.fechaRegistro !== undefined ? data.fechaRegistro : "");
                $("#direccion").val(data.direccion !== undefined ? data.direccion : "");
            }
        },
        error: function(err){
            alert('Error' + err);
        }
    });
}

function listarDatos(){
    var documento = $('#documento').val() !== undefined && $('#documento').val() !== "" ? $('#documento').val() : null;
    var tipoDocumento = $('#tipoDocumento').val() !== undefined && $('#tipoDocumento').val() !== "" ? $('#tipoDocumento').val() : null;
    $.ajax({
        type    :"POST",
        url     :"Agricultor",
        dataType:"html",
        data    :{accion:"listar",documento:documento,tipoDocumento:tipoDocumento},
        success: function(data) {
            if(data !== undefined){
                $('#tablaAgricultores').html(data);
            }
        },
        error: function(){
        }
    });
}


