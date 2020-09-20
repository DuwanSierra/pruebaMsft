
CREATE TABLE Producto (
                idProducto IDENTITY NOT NULL,
                nombre VARCHAR(255) NOT NULL,
                precio DECIMAL(15,2) NOT NULL,
                CONSTRAINT idProducto PRIMARY KEY (idProducto)
);
COMMENT ON COLUMN Producto.idProducto IS 'primary key producto';
COMMENT ON COLUMN Producto.nombre IS 'Nombre producto';
COMMENT ON COLUMN Producto.precio IS 'precio producto';


CREATE TABLE Cliente (
                idCliente IDENTITY NOT NULL,
                nombre VARCHAR(255) NOT NULL,
                apellido VARCHAR(255) NOT NULL,
                dni VARCHAR(255) NOT NULL,
                telefono DECIMAL(15) NOT NULL,
                email VARCHAR(255) NOT NULL,
                CONSTRAINT idCliente PRIMARY KEY (idCliente)
);
COMMENT ON COLUMN Cliente.idCliente IS 'PRIMARY KEY';
COMMENT ON COLUMN Cliente.nombre IS 'Nombre cliente';
COMMENT ON COLUMN Cliente.apellido IS 'APELLIDO';
COMMENT ON COLUMN Cliente.dni IS 'DNI';
COMMENT ON COLUMN Cliente.email IS 'EMAIL';


CREATE TABLE Venta (
                idVenta IDENTITY NOT NULL,
                idCliente DECIMAL(15) NOT NULL,
                fecha DATE NOT NULL,
                CONSTRAINT idVenta PRIMARY KEY (idVenta)
);
COMMENT ON COLUMN Venta.idVenta IS 'primary key venta';
COMMENT ON COLUMN Venta.idCliente IS 'PRIMARY KEY';
COMMENT ON COLUMN Venta.fecha IS 'fecha venta';


CREATE TABLE DetalleVenta (
                idDetalleVenta IDENTITY NOT NULL,
                idProducto DECIMAL(15) NOT NULL,
                idVenta DECIMAL(15) NOT NULL,
                CONSTRAINT idDetalleVenta PRIMARY KEY (idDetalleVenta)
);
COMMENT ON COLUMN DetalleVenta.idDetalleVenta IS 'primary key';
COMMENT ON COLUMN DetalleVenta.idProducto IS 'primary key producto';
COMMENT ON COLUMN DetalleVenta.idVenta IS 'primary key venta';


ALTER TABLE DetalleVenta ADD CONSTRAINT Producto_DetalleVenta_fk
FOREIGN KEY (idProducto)
REFERENCES Producto (idProducto)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Venta ADD CONSTRAINT Cliente_Venta_fk
FOREIGN KEY (idCliente)
REFERENCES Cliente (idCliente)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE DetalleVenta ADD CONSTRAINT Venta_DetalleVenta_fk
FOREIGN KEY (idVenta)
REFERENCES Venta (idVenta)
ON DELETE NO ACTION
ON UPDATE NO ACTION;
