$(document).ready(function() {
    listarDatos();
});

jQuery(document).ready(function($) {
    $('#cantidadTotal').click(function(e) {
        var p = $('#producto').val();
        $.ajax({
             type: "POST",
             url: "Inventario",
             dataType:"json",
             data: {accion:"cantidadTotal",producto: p},
             success: function(data) {
                 $("#cantidadTotal").val(data.cantidadTotal);
             },
             error: function(err) {
                 alert('Error' + err);
             }
         }); 
    });
});

function limpiarDatos(){
    $('#idInventario').val('');
    $('#fecha').val('');
    $('#producto').val('');
    $('#cantidadComprometida').val('');
    $('#cantidadTotal').val('');
    $('#disponibilidad').val('');
}

function consultarInventario(id){
    $.ajax({
        type    :"POST",
        url     :"Inventario",
        dataType:"json",
        data    :{accion:"consultar",idInventario:id},
        success: function(data) {
            if(data !== undefined){
                $("#idInventario").val(data.id !== undefined ? data.id : "");
                $("#fecha").val(data.fecha !== undefined ? data.fecha : "");
                $("#producto").val(data.producto !== undefined ? data.producto : "");
                $("#cantidadComprometida").val(data.cantidadComprometida !== undefined ? data.cantidadComprometida : "");
                $("#cantidadTotal").val(data.cantidadTotal !== undefined ? data.cantidadTotal : "");
            }
        },
        error: function(){
        }
    });
}

function listarDatos(){
    var fecha = $('#fecha').val() !== undefined && $('#fecha').val() !== "" ? $('#fecha').val() : null;
    var producto = $('#producto').val() !== undefined && $('#producto').val() !== "" ? $('#producto').val() : null;
    $.ajax({
        type    :"POST",
        url     :"Inventario",
        dataType:"html",
        data    :{accion:"listar",fecha:fecha,producto:producto},
        success: function(data) {
            if(data !== undefined){
                $('#tablaInventarios').html(data);
            }
        },
        error: function(){
        }
    });
}


