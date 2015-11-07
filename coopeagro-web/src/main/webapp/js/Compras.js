$(function() {
    $("#agricultor").typeahead({
        onSelect: function(item) {
            $('#idAgricultor').val(item.value);
        },
        ajax: {
            url: "Compra",
            timeout: 500,
            displayField: "label",
            valueField: 'value',
            triggerLength: 1,
            method: "POST",
            preDispatch: function(query) {
                return {parametro: query, accion: "completarAgricultor"};
            }
        }
    });
    $("#empleado").typeahead({
        onSelect: function(item) {
            $('#idEmpleado').val(item.value);
        },
        ajax: {
            url: "Compra",
            timeout: 500,
            displayField: "label",
            valueField: 'value',
            triggerLength: 1,
            method: "POST",
            preDispatch: function(query) {
                return {parametro: query, accion: "completarEmpleado"};
            }
        }
    });
    $("#producto").typeahead({
        onSelect: function(item) {
            console.log(item);
        },
        ajax: {
            url: "Compra",
            timeout: 500,
            displayField: "label",
            valueField: 'value',
            triggerLength: 1,
            method: "POST",
            preDispatch: function(query) {
                return {parametro: query, accion: "completarProducto"};
            }
        }
    });
});