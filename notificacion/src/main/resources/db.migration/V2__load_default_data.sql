INSERT INTO 'ecommerce_notificacion'.'notificacion'  -- Especifica la base de datos y la tabla donde se insertarán los datos
            ('asunto','mensaje','fecha') -- Columnas de la tabla en las que se insertarán valores
VALUES -- Indica que a continuación vienen los valores a insertar
    ('Despacho del pedido', -- Valor para la columna 'asunto': título del mensaje
    'Tu pedido salio de la bodega central', -- Valor para 'mensaje': contenido del aviso (nota: tiene error "salid" en lugar de "salió")
    '20/05/2026') -- Valor para 'fecha': fecha del aviso (formato DD/MM/YYYY)