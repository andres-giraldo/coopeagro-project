<%-- 
    Document   : Compras
    Created on : 6/11/2015, 08:49:04 PM
    Author     : Pipe
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <c:import url="/jsp/general/Head.jsp" />
        <script type="text/javascript" src="js/Compras.js"></script>
        <title>Compras</title>
    </head>
    <body>
        <div class="container-fluid">
            <c:import url="/jsp/general/Alertas.jsp" />
            <div class="row">
                <div class="col-xs-12 col-sm-3 col-md-3 col-lg-3" id="menu">
                    <c:import url="/jsp/general/Menu.jsp" />
                </div>
                <div class="col-xs-12 col-sm-9 col-md-9 col-lg-9" id="contenido">
                    <form autocomplete="off" action="./Compra" method="POST" id="formularioCompras">
                        <h2>NUEVA COMPRA</h2>
                        Los campos marcados con (*) son obligatorios
                        <br>
                        <div class="row">
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 form-group">
                                <label for="fecha">* Fecha de compra:</label> 
                                <input class="form-control" type="text" id="fecha" name="fecha" readonly="true" value="${fecha}" />
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 form-group">
                                <label for="totalPedido">Total:</label> 
                                <input class="form-control" type="text" id="totalPedido" name="totalPedido" readonly="true" value="$ 0" />
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 form-group">
                                <label for="agricultor">* Agricultor:</label> 
                                <input class="form-control" type="text" id="agricultor" name="agricultor" value="${agricultor}" />
                                <input class="form-control" type="hidden" id="idAgricultor" name="idAgricultor" value="${idAgricultor}" />
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 form-group">
                                <label for="empleado">* Empleado:</label> 
                                <input class="form-control" type="text" id="empleado" name="empleado" value="${empleado}" />
                                <input class="form-control" type="hidden" id="idEmpleado" name="idEmpleado" value="${idEmpleado}" />
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 form-group">
                                <label for="producto">* Adicionar producto:</label> 
                                <input class="form-control" type="text" id="producto" name="producto"/>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 form-group">
                                <div id="tablaProductos">
                                    <ul class="list-group">
                                        <li class="list-group-item" id="item3">
                                            <div class="row">
                                                <div class="col-xs-10 col-sm-11 col-md-11 col-lg-11">
                                                    Martin Moreno
                                                </div>
                                                <div class="col-xs-2 col-sm-1 col-md-1 col-lg-1">
                                                    <span class="glyphicon glyphicon-remove" onclick="$('#item3').remove();"></span>
                                                </div>
                                            </div>
                                        </li>
                                        <li class="list-group-item" id="item4">
                                            <div class="row">
                                                <div class="col-xs-10 col-sm-11 col-md-11 col-lg-11">
                                                    Maria Sanchez
                                                </div>
                                                <div class="col-xs-2 col-sm-1 col-md-1 col-lg-1">
                                                    <span class="glyphicon glyphicon-remove" onclick="$('#item4').remove();"></span>
                                                </div>
                                            </div>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <div class="row form-group" align="center">
                            <button class="btn btn-default" type="submit" name="accion" id="guardarCompra" value="guardarCompra">Guardar</button>
                            <button class="btn btn-default" type="submit" name="accion" id="cancelarCompra" value="cancelarCompra">Cancelar</button>
                            <button class="btn btn-default" type="button" name="accion" id="limpiar" value="limpiar" onclick="limpiarDatos();">Limpiar</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
