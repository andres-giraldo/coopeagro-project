<%-- 
    Document   : Venta
    Created on : 28-nov-2015, 11:20:24
    Author     : YEISSON
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <c:import url="/jsp/general/Head.jsp" />
        <script type="text/javascript" src="js/Ventas.js"></script>
        <script type="text/javascript">
            function consultarDetalles(id)
            {
                document.getElementById("idVenta").value = id;
                document.formularioVentas.submit();
            }
            
            function cancelar(id)
            {
                document.getElementById("idVenta").value = id;
                document.formularioVentas.submit();
            }
        </script>
        <title>Ventas</title>
    </head>
    <body>
        <div class="container-fluid">
            <c:import url="/jsp/general/Alertas.jsp" />
            <div class="row">
                <div class="col-xs-12 col-sm-3 col-md-3 col-lg-3" id="menu">
                    <c:import url="/jsp/general/Menu.jsp" />
                </div>
                <div class="col-xs-12 col-sm-9 col-md-9 col-lg-9" id="contenido">
                    <form autocomplete="off" action="./Venta" method="POST" id="formularioVentas">
                        <h2>NUEVA VENTA</h2>
                        Los campos marcados con (*) son obligatorios
                        <br>
                        <br>
                        <div class="row">
                            <input type="hidden" id="idVenta" name="idVenta" value="${idVenta}"/>
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 form-group">
                                <label for="fecha">* Fecha de compra:</label> 
                                <input class="form-control" type="text" id="fecha" name="fecha" readonly="true" value="${fecha}" />
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 form-group">
                                <label for="fechaEntrega">* Fecha de entrega:</label> 
                                <input class="form-control" type="text" id="fechaEntrega" name="fechaEntrega" value="${fechaEntrega}"/>
                                <script type="text/javascript">
                                    $(function() {
                                        $('#fechaEntrega').datetimepicker({format: 'dd/mm/yyyy', language:'es', weekStart:true, todayBtn:true, autoclose:true, todayHighlight:true, startView:2, minView:2});
                                    });
                                </script>
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 form-group">
                                <label for="direccion">* Dirección:</label> 
                                <input class="form-control" type="text" id="direccion" name="direccion" value="${direccion}"/>
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 form-group">
                                <label for="remitente">* Remitente:</label> 
                                <input class="form-control" type="text" id="direccion" name="remitente" value="${remitente}"/>
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 form-group">
                                <label for="totalPedido">Total:</label> 
                                <div class="input-group">
                                    <span class="input-group-addon">$</span>
                                    <input class="form-control" type="text" id="totalPedido" name="totalPedido" readonly="true" value="0" />
                                </div>
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 form-group">
                                <label for="cliente">* Cliente:</label> 
                                <input class="form-control" type="text" id="cliente" name="cliente" value="${cliente}" />
                                <input class="form-control" type="hidden" id="idCliente" name="idCliente" value="${idCliente}" />
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 form-group">
                                <label for="producto">* Adicionar producto:</label> 
                                <input class="form-control" type="text" id="producto" name="producto"/>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 form-group">
                                <div id="tablaProductos">
                                    <ul class="list-group">
                                        <li class="list-group-item" id="item3">
                                            <div class="row">
                                                <div class="col-xs-10 col-sm-11 col-md-11 col-lg-11">
                                                    <div class="row">
                                                        <div class="col-xs-5 col-sm-3 col-md-3 col-lg-3">
                                                            PS564D76
                                                        </div>
                                                        <div class="hidden-xs col-sm-3 col-md-3 col-lg-3">
                                                            Papa criolla
                                                        </div>
                                                        <div class="col-xs-3 col-sm-3 col-md-3 col-lg-3">
                                                            $ 5000
                                                        </div>
                                                        <div class="col-xs-4 col-sm-3 col-md-3 col-lg-3">
                                                            <input type="text" class="form-control" id="cantidad1" name="cantidades" value="1"/>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-xs-2 col-sm-1 col-md-1 col-lg-1">
                                                    <span class="glyphicon glyphicon-remove" onclick="$('#item3').remove();"></span>
                                                </div>
                                            </div>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <div class="row form-group" align="center">
                            <button class="btn btn-default" type="submit" name="accion" id="guardarVenta" value="guardarVenta">Guardar</button>
                            <button class="btn btn-default" type="submit" name="accion" id="cancelarVenta" value="cancelarVenta">Cancelar</button>
                            <button class="btn btn-default" type="button" name="accion" id="limpiar" value="limpiar" onclick="limpiarDatos();">Limpiar</button>
                        </div>
                        <div id="tablaVentas"></div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
