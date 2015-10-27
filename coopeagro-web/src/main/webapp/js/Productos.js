$(document).ready(function() {
    listarDatos();
});

function limpiarDatos(){
    $('#idProducto').val('');
    $('#codigo').val('');
    $('#nombre').val('');
    $('#valor').val('');
}

function consultarProducto(id){
    $.ajax({
        type    :"POST",
        url     :"Producto",
        dataType:"json",
        data    :{accion:"consultar",idProducto:id},
        success: function(data) {
            if(data !== undefined){
                $("#idProducto").val(data.id !== undefined ? data.id : "");
                $("#codigo").val(data.codigo !== undefined ? data.codigo : "");
                $("#nombre").val(data.nombre !== undefined ? data.nombre : "");
                $("#valor").val(data.valor !== undefined ? data.valor : "");
            }
        },
        error: function(){
        }
    });
}

function listarDatos(){
    var codigo = $('#codigo').val() !== undefined && $('#codigo').val() !== "" ? $('#codigo').val() : null;
    var nombre = $('#nombre').val() !== undefined && $('#nombre').val() !== "" ? $('#nombre').val() : null;
    $.ajax({
        type    :"POST",
        url     :"Producto",
        dataType:"html",
        data    :{accion:"listar",codigo:codigo,nombre:nombre},
        success: function(data) {
            if(data !== undefined){
                $('#tablaProductos').html(data);
            }
        },
        error: function(){
        }
    });
}