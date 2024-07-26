CREATE TABLE conta (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            data_vencimento DATE NOT NULL,
                            data_pagamento DATE,
                            valor DECIMAL(10, 2) NOT NULL,
                            descricao VARCHAR(255),
                            situacao ENUM('pago', 'não pago') NOT NULL
);