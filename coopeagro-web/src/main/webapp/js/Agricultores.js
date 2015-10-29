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
        data    :{accion:"consultar",documento:documento, tipoDocumento:tipoDocumento},
        success: function(data) {
            if(data !== undefined){
                $("#documento").val(data.llavePrimaria.documento !== undefined ? data.llavePrimaria.documento : "");
                $("#tipoDocumento").val(data.llavePrimaria.tipoDocumento !== undefined ? data.llavePrimaria.tipoDocumento : "");
                $("#nombre").val(data.nombre !== undefined ? data.nombre : "");
                $("#apellido1").val(data.apellidoUno !== undefined ? data.apellidoUno : "");
                $("#apellido2").val(data.apellidoDos !== undefined ? data.apellidoDos : "");
                $("#telefono").val(data.telefono !== undefined ? data.telefono : "");
                $("#celular").val(data.celular !== undefined ? data.celular : "");
                $("#correo").val(data.correo !== undefined ? data.correo : "");
                $("#fechaRegistro").val(data.fechaRegistro !== undefined ? data.fechaRegistro : "");
                $("#direccion").val(data.direccion !== undefined ? data.direccion : "");
            }
        },
        error: function(){
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


