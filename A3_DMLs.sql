-- consultas, inserts e updates realizados pelo sistema
-- Sem formatações de campo
insert into pessoas (nome, cpf, datanascimento, genero)
values( ?, ?, ?, ?);

insert into veiculos(marca, modelo, ano, cor)
values ( ?, ?, ?, ?);

-- insert da transferencia
	-- encontra valor da placa e do dono anterior 
    -- armazenar estes valores antes de realizar o insert da transferencia
    select t1.id_pessoa_compra, 
           t1.placa_atual 
	  from transferencias t1
	 where t1.id_veiculo           = new.id_veiculo
	   and t1.status_transferencia = 'C'
	   and t1.id                   = (select max(id) 
										from transferencias t2
									   where t2.id_veiculo = ?);
insert into transferencias (id_veiculo, id_pessoa_compra, id_pessoa_venda, placa_atual, placa_anterior, data)
values ( ?, ?, ?, ?, ?, ?);

-- consulta das pessoas do sistema
select * from pessoas;

-- consulta dos veículos do sistema
select veiculos.id,
       veiculos.marca,
       veiculos.modelo,
       veiculos.ano,
       transferencias.placa_atual,
       pessoas.nome
  from veiculos
 left join transferencias on veiculos.id = transferencias.id_veiculo
 left join pessoas on pessoas.id = transferencias.id_pessoa_compra
 where transferencias.id = (select max(id) 
                              from transferencias 
							 where id_veiculo = veiculos.id
                               and status_transferencia = 'C')
 ;

-- consulta das transferencias dos veículos
SELECT 
    transferencias.id,
    pessoa_compra.nome AS pessoa_compra,
    pessoa_venda.nome AS pessoa_venda,
    veiculos.marca,
    veiculos.modelo,
    veiculos.ano,
    veiculos.cor,
    transferencias.placa_anterior,
    transferencias.placa_atual,
    transferencias.status_veiculo,
    transferencias.status_transferencia,
    transferencias.data
FROM transferencias
        JOIN
    pessoas pessoa_compra ON transferencias.id_pessoa_compra = pessoa_compra.id
        JOIN
    veiculos ON veiculos.id = transferencias.id_veiculo
        LEFT JOIN
    pessoas pessoa_venda ON transferencias.id_pessoa_venda = pessoa_venda.id;

