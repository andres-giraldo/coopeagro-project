<%-- 
    Document   : Productos
    Created on : 26/10/2015, 12:44:06 PM
    Author     : Pipe
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <c:import url="/jsp/general/Head.jsp" />
        <script type="text/javascript" src="js/Productos.js"></script>
        <title>Productos</title>
    </head>
    <body>
        <div class="container-fluid">
            <c:import url="/jsp/general/Alertas.jsp" />
            <div class="row">
                <div class="col-xs-12 col-sm-3 col-md-3 col-lg-3" id="menu">
                    <c:import url="/jsp/general/Menu.jsp" />
                </div>
                <div class="col-xs-12 col-sm-9 col-md-9 col-lg-9" id="contenido">
                    <form autocomplete="off" action="./Producto" method="POST" id="formularioProductos">
                        <c:import url="/jsp/general/Eliminacion.jsp" />
                        <h2>PRODUCTOS</h2>
                        Los campos marcados con (*) son obligatorios
                        <br>
                        <br>
                        <div class="row">
                            <input type="hidden" id="idProducto" name="idProducto" value="${idProducto}"/>
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 form-group">
                                <label for="codigo">* Código:</label> 
                                <input class="form-control" type="text" id="codigo" name="codigo" value="${codigo}"/>
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 form-group">
                                <label for="nombre">* Nombre:</label> 
                                <input class="form-control" type="text" id="nombre" name="nombre" value="${nombre}" />
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 form-group">
                                <label for="unidadMedida">* Unidad de medida:</label>
                                <br/>
                                <select class="form-control" id="unidadMedida" name="unidadMedida">
                                    <option value="0">Seleccione</option>
                                    <c:forEach items="${unidadesMedida}" var="unidad">
                                        <option value="${unidad.unidadMedida}" <c:if test="${unidadMedida == unidad.unidadMedida}">selected</c:if> >${unidad.unidadMedida}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 form-group">
                                <label for="valor">* Precio de venta:</label>
                                <div class="input-group">
                                    <span class="input-group-addon">$</span>
                                    <input class="form-control" type="text" id="valor" name="valor" value="${valor}" />
                                </div>
                            </div>
                        </div>
                        <div class="row form-group" align="center">
                            <button class="btn btn-default" type="button" name="accion" id="consultar" value="listar" onclick="listarDatos();">Listar</button>
                            <button class="btn btn-default" type="submit" name="accion" id="guardar" value="guardar">Guardar</button>
                            <button class="btn btn-default" type="button" name="accion" id="limpiar" value="limpiar" onclick="limpiarDatos();">Limpiar</button>
                        </div>
                        <div id="tablaProductos"></div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
