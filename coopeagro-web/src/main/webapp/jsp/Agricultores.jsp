<%-- 
    Document   : Agricultores
    Created on : 27-oct-2015, 10:16:24
    Author     : YEISSON
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <c:import url="/jsp/general/Head.jsp" />
        <script type="text/javascript" src="js/Agricultores.js"></script>
        <title>Agricultores</title>
    </head>
    <body>
        <div class="container-fluid">
            <c:import url="/jsp/general/Alertas.jsp" />
            <div class="row">
                <div class="col-xs-12 col-sm-3 col-md-3 col-lg-3" id="menu">
                    <c:import url="/jsp/general/Menu.jsp" />
                </div>
                <div class="col-xs-12 col-sm-9 col-md-9 col-lg-9" id="contenido">
                    <form autocomplete="off" action="./Agricultor" method="POST" id="formularioAgricultores">
                        <c:import url="/jsp/general/Eliminacion.jsp" />
                        <h2>AGRICULTORES</h2>
                        Los campos marcados con (*) son obligatorios
                        <br>
                        <br>
                        <div class="row">
                            <input type="hidden" id="isEditar" name="isEditar" value="${isEditar}"/>
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 form-group">
                                <label for="documento">* Documento:</label> 
                                <input class="form-control" type="text" id="documento" name="documento" value="${documento}"/>
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 form-group">
                                <label for="tipoDocumento">* Tipo Documento:</label>
                                <br/>
                                <select class="form-control" id="tipoDocumento" name="tipoDocumento">
                                    <option value="0">Seleccione</option>
                                    <c:forEach items="${tiposDocumento}" var="tipo">
                                        <option value="${tipo.tipoDocumento}" <c:if test="${tipoDocumento == tipo.tipoDocumento}">selected</c:if> >${tipo.tipoDocumento}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 form-group">
                                <label for="nombre">* Nombre:</label> 
                                <input class="form-control" type="text" id="nombre" name="nombre" value="${nombre}"/>
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 form-group">
                                <label for="apellido1">* Primer apellido:</label> 
                                <input class="form-control" type="text" id="apellido1" name="apellido1" value="${apellido1}"/>
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 form-group">
                                <label for="apellido2">  Segundo apellido:</label> 
                                <input class="form-control" type="text" id="apellido2" name="apellido2" value="${apellido2}"/>
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 form-group">
                                <label for="telefono">* Teléfono:</label> 
                                <input class="form-control" type="text" id="telefono" name="telefono" value="${telefono}"/>
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 form-group">
                                <label for="celular">* Celular:</label> 
                                <input class="form-control" type="text" id="celular" name="celular" value="${celular}"/>
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 form-group">
                                <label for="correo">* Correo:</label> 
                                <input class="form-control" type="text" id="correo" name="correo" value="${correo}"/>
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 form-group">
                                <label for="direccion">* Dirección:</label> 
                                <input class="form-control" type="text" id="direccion" name="direccion" value="${direccion}"/>
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 form-group">
                                <label for="fechaRegistro">* Fecha registro:</label> 
                                <input class="form-control" type="text" id="fechaRegistro" name="fechaRegistro" value="${fechaRegistro}"/>
                                <script type="text/javascript">
                                    $(function() {
                                        $('#fechaRegistro').datetimepicker({format: 'dd/mm/yyyy', language:'es', weekStart:true, todayBtn:true, autoclose:true, todayHighlight:true, startView:2, minView:2});
                                    });
                                </script>
                            </div>
                        </div>
                        <div class="row form-group" align="center">
                            <button class="btn btn-default" type="button" name="accion" id="consultar" value="listar" onclick="listarDatos();">Listar</button>
                            <button class="btn btn-default" type="submit" name="accion" id="guardar" value="guardar">Guardar</button>
                            <button class="btn btn-default" type="button" name="accion" id="limpiar" value="limpiar" onclick="limpiarDatos();">Limpiar</button>
                        </div>
                        <div id="tablaAgricultores"></div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
