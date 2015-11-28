var productos = {};
var productosSeleccionados = {};
    
$(function() {
    listarDatos();
    pintarDetalles();
    $("#cliente").typeahead({
        onSelect: function(item) {
            $('#idCliente').val(item.value);
        },
        ajax: {
            url: "Venta",
            timeout: 500,
            displayField: "label",
            valueField: 'value',
            triggerLength: 1,
            method: "POST",
            preDispatch: function(query) {
                return {parametro: query, accion: "completarCliente"};
            }
        }
    });
    $("#producto").typeahead({
        onSelect: function(item) {
            productosSeleccionados[item.value] = productos[item.value];
            pintarDetalles();
        },
        ajax: {
            url: "Venta",
            timeout: 500,
            displayField: "label",
            valueField: 'value',
            triggerLength: 1,
            method: "POST",
            preDispatch: function(query) {
                return {parametro: query, accion: "completarProducto"};
            },
            preProcess: function(data) {
                if (data !== undefined) {
                    for (var i = 0; i < data.length; i++) {
                        var producto = data[i];
                        if(productos[producto.value] === undefined){
                            productos[producto.value] = producto;
                        }
                    }
                }
                return data;
            }
        }
    });
});

function pintarDetalles() {
    var html = '';
    var totalPedido = 0;
    if (!isEmpty(productosSeleccionados)) {
        html += '<ul class="list-group">';
        for (var producto in productosSeleccionados) {
            producto = productos[producto];
            html += '<li class="list-group-item" id="item'+producto.value+'">';
            html +=     '<div class="row">';
            html +=         '<div class="col-xs-10 col-sm-11 col-md-11 col-lg-11">';
            html +=             '<div class="row">';
            html +=                 '<input type="hidden" id="idProducto'+producto.value+'" name="idProductos" value="'+producto.value+'"/>';
            html +=                 '<div class="col-xs-4 col-sm-3 col-md-3 col-lg-3">';
            html +=                     producto.codigo;
            html +=                 '</div>';
            html +=                 '<div class="hidden-xs col-sm-3 col-md-3 col-lg-3">';
            html +=                     producto.nombre;
            html +=                 '</div>';
            html +=                 '<div class="col-xs-4 col-sm-3 col-md-3 col-lg-3">';
            html +=                     '<input type="text" class="form-control" id="valor'+producto.value+'" name="valores" value="'+producto.valor+'" onblur="actualizarValores('+producto.value+')" readonly="true"/>';
            html +=                 '</div>';
            html +=                 '<div class="col-xs-4 col-sm-3 col-md-3 col-lg-3">';
            html +=                     '<input type="text" class="form-control" id="cantidad'+producto.value+'" name="cantidades" value="'+producto.cantidad+'" onblur="actualizarCantidades('+producto.value+')"/>';
            html +=                 '</div>';
            html +=             '</div>';
            html +=         '</div>';
            html +=         '<div class="col-xs-2 col-sm-1 col-md-1 col-lg-1">';
            html +=             '<span class="glyphicon glyphicon-remove" onclick="borrarProducto('+producto.value+');"></span>';
            html +=         '</div>';
            html +=     '</div>';
            html += '</li>';
            totalPedido += producto.valor * producto.cantidad;
        }
        html += '</ul>';
    } else {
        html += '<h3>No se han seleccionado productos</h3>';
    }
    $('#tablaProductos').html(html);
    $('#totalPedido').val(totalPedido);
}

function isEmpty(obj) {
    for(var prop in obj) {
        if(obj.hasOwnProperty(prop))
            return false;
    }
    return true;
}

function borrarProducto(idProducto){
    delete productosSeleccionados[idProducto];
    delete productos[idProducto];
    $("#item"+idProducto).remove();
    pintarDetalles();
}

function actualizarCantidades(idProducto){
    var cantidad = $("#cantidad"+idProducto).val();
    var producto = productos[idProducto];
    if(isNaN(cantidad) || cantidad <= 0){
        cantidad = producto.cantidad;
        $("#cantidad"+idProducto).val(cantidad);
    }
    producto.cantidad = cantidad;
    productos[idProducto] = producto;
    productosSeleccionados[idProducto] = producto;
    pintarDetalles();
}

function actualizarValores(idProducto){
    var valor = $("#valor"+idProducto).val();
    var producto = productos[idProducto];
    if(isNaN(valor) || valor <= 0){
        valor = producto.valor;
        $("#valor"+idProducto).val(valor);
    }
    producto.valor = valor;
    productos[idProducto] = producto;
    productosSeleccionados[idProducto] = producto;
    pintarDetalles();
}

function listarDatos(){
    $.ajax({
        type    :"POST",
        url     :"Venta",
        dataType:"html",
        data    :{accion:"listar"},
        success: function(data) {
            if(data !== undefined){
                $('#tablaVentas').html(data);
            }
        },
        error: function(){
        }
    });
}


