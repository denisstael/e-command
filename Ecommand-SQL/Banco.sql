﻿
CREATE SEQUENCE Usuario_seq;

CREATE TABLE Usuario (
  CodUsuario INT NOT NULL DEFAULT NEXTVAL ('Usuario_seq'),
  Senha VARCHAR(45) NOT NULL,
  Nome VARCHAR(45) NOT NULL,
  tipo VARCHAR(15) NOT NULL CHECK (tipo IN ('Gerente','Garçom','Cozinheiro')),
  PRIMARY KEY (CodUsuario)
);

CREATE SEQUENCE Mesa_seq;

CREATE TABLE Mesa (
  idMesa INT NOT NULL DEFAULT NEXTVAL ('Mesa_seq'),
  PRIMARY KEY (idMesa)
);

CREATE SEQUENCE Produto_seq;

CREATE TABLE Produto (
  CodProduto INT NOT NULL DEFAULT NEXTVAL ('Produto_seq'),
  Nome VARCHAR(45) NOT NULL,
  Descricao VARCHAR(200) NOT NULL,
  Quantidade DECIMAL(10,3) NOT NULL,
  medida VARCHAR(2) NOT NULL CHECK (medida IN ('Kg','Lt','Un')),
  PRIMARY KEY (CodProduto) 
);

CREATE SEQUENCE Prato_seq;

CREATE TABLE Prato (
  CodPrato INT NOT NULL DEFAULT NEXTVAL ('Prato_seq'),
  Nome VARCHAR(45) NOT NULL,
  Descricao VARCHAR(200) NOT NULL,
  Imagem VARCHAR (200) NOT NULL,
  Preco DECIMAL(10,2) NOT NULL,
  cardapio BOOLEAN DEFAULT FALSE,
  Quantidade INT DEFAULT 0,
  Tipo VARCHAR(15) NOT NULL CHECK(Tipo IN ('Bebida','Comida')),
  PRIMARY KEY (CodPrato)
);

CREATE SEQUENCE Comanda_seq;

CREATE TABLE Comanda (
  CodComanda INT NOT NULL DEFAULT NEXTVAL('Comanda_seq'),
  PrecoTotalComanda DECIMAL(10,2) NOT NULL DEFAULT 0,
  StatusComanda VARCHAR(10) CHECK(StatusComanda IN ('Aberto','Fechado')),
  PRIMARY KEY (CodComanda)
);

CREATE SEQUENCE Pedido_seq;

CREATE TABLE Pedido (
  CodPedido INT NOT NULL DEFAULT NEXTVAL ('Pedido_seq'),
  Mesa_idMesa INT NOT NULL,
  Garcom_Usuario_CodUsuario INT,
  Cozinheiro_Usuario_CodUsuario INT,
  Observacao VARCHAR(200),
  precototal DECIMAL(10,2) NOT NULL DEFAULT 0,
  Data_Pedido DATE NOT NULL,
  PRIMARY KEY (CodPedido),
  CONSTRAINT fk_Mesa_has_Prato_Mesa1
    FOREIGN KEY (Mesa_idMesa)
    REFERENCES Mesa (idMesa)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT fk_Pedido_Garcom1
    FOREIGN KEY (Garcom_Usuario_CodUsuario)
    REFERENCES Usuario (CodUsuario)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT fk_Pedido_Cozinheiro1
    FOREIGN KEY (Cozinheiro_Usuario_CodUsuario)
    REFERENCES Usuario (CodUsuario)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE INDEX fk_Mesa_has_Prato_Mesa1_idx ON Pedido (Mesa_idMesa);
CREATE INDEX fk_Pedido_Garcom1_idx ON Pedido (Garcom_Usuario_CodUsuario);
CREATE INDEX fk_Pedido_Cozinheiro1_idx ON Pedido (Cozinheiro_Usuario_CodUsuario);

CREATE SEQUENCE PedidoPrato_seq;

CREATE TABLE PedidoPrato(
  CodPedPrato INT NOT NULL DEFAULT NEXTVAL ('PedidoPrato_seq'),
  CodPrato INT NOT NULL,
  CodPedido INT NOT NULL,
  CodCozinheiro INT,
  CodGarcom INT,
  PRIMARY KEY (CodPedPrato),
  FOREIGN KEY (CodPrato) REFERENCES Prato (CodPrato)
  ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (CodPedido) REFERENCES Pedido (CodPedido)
  ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (CodCozinheiro) REFERENCES Usuario (CodUsuario)
  ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (CodGarcom) REFERENCES Usuario (CodUsuario)
  ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE PedidoComanda (
  CodPedido INT NOT NULL,
  CodComanda INT NOT NULL,
  PRIMARY KEY (CodPedido, CodComanda),
  CONSTRAINT FK_COMANDA
    FOREIGN KEY (CodComanda)
    REFERENCES Comanda (CodComanda)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT FK_PEDIDO
    FOREIGN KEY (CodPedido)
    REFERENCES Pedido (CodPedido)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);
CREATE TABLE ImgCardapio(
  CodImagem INT NOT NULL,
  ImagemCardapio VARCHAR (200),
  PRIMARY KEY (CodImagem)
  );
  
  CREATE SEQUENCE Relatorio_seq;
  
  CREATE TABLE Relatorio(
  
  Id_relatorio INT NOT NULL DEFAULT NEXTVAL('Relatorio_seq'),
  TextoRelatorio VARCHAR(8000),
  PRIMARY KEY (Id_relatorio)
  );
/*
CREATE SEQUENCE Avaliacao_seq;
CREATE TABLE Avaliacao(
  CodAvaliacao INT NOT NULL DEFAULT NEXTVAL ('Avaliacao_seq'),
  Comentario VARCHAR(200),
  NotaAvaliacao INT NOT NULL CHECK (NotaAvaliacao IN (1,2,3,4,5)
);
CREATE TABLE AvaliacaoComanda(
  CodComanda INT NOT NULL,
  CodAvaliacao INT NOT NULL,
  PRIMARY KEY (CodComanda,CodAvaliacao),
  CONSTRAINT FK_COMANDA
    FOREIGN KEY (CodComanda)
    REFERENCES Comanda (CodComanda)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT FK_AVALIACAO
    FOREIGN KEY (CodAvaliacao)
    REFERENCES Avaliacao (CodAvaliacao)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);*/