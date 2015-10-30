$(document).ready(function() {
    listarDatos();
});

function limpiarDatos(){
    $('#idInventario').val('');
    $('#fecha').val('');
    $('#sProducto').val('');
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
                $("#sProducto").val(data.producto !== undefined ? data.producto : "");
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


