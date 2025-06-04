-- consultas, inserts e updates realizados pelo sistema
-- Sem formatações de campo
-- 1 - cadastrar pessoas
insert into pessoa (nome, cpf, datanascimento, genero)
values( ?, ?, ?, ?);

-- 2 - cadastrar veículos
insert into veiculo(marca, modelo, ano, cor)
values ( ?, ?, ?, ?);

-- 3 Transferir veículo
-- insert da transferencia
	-- encontra valor da placa e do dono anterior 
    -- armazenar estes valores em variáveis java antes de realizar o insert da transferencia
    select t1.id_pessoa_compra, -- será passado como pessoa_venda 
           t1.placa_atual   -- será a placa_anterior
	  from transferencia t1
	 where t1.id_veiculo           = ? -- id do carro a ser transferido
	   and t1.status_transferencia = 'N'
	   and t1.id                   = (select max(id) 
										from transferencias t2
									   where t2.id_veiculo = ?);
insert into transferencia (id_veiculo, id_pessoa_compra, id_pessoa_venda, placa_atual, placa_anterior, data)
values ( ?, ?, ?, ?, ?, ?);

-- após insert, alterar status da transferencia da ultima transferencia deste veículos
	-- encontra última transferencia
select id -- guarda este valor
  from transferencia
 where id <> ? -- id da transferencia recém realizada
   and id_veiculo = ? -- id do veículo recém transferido
   and status_transferencia = 'N'
 ;
	-- realiza update na última transferencia
update transferencias
   set status_transferencia = 'A'
 where id = ?; -- id do select acima
 -- ------------------------------------------------------------------------------
 -- 4 - Consultar Veículo por placa
 select p.nome,
		p.cpf,
		v.marca,
        v.modelo,
        v.ano,
        v.cor,
        t.placa_atual
   from veiculo       v,
		transferencia t,
        pessoa        p
  where t.status_transferencia    = 'N'
    and t.id_veiculo              = v.id
    and t.id_pessoa_compra        = p.id
    and t.placa_atual             = ?;

-- 5- Consultar veículos por pessoa
select p.nome,
	   p.cpf,
       p.dataNascimento,
       p.genero,
       v.marca,
	   v.modelo,
	   v.ano,
	   v.cor,
	   t.placa_atual
  from veiculo       v,
	   transferencia t,
	   pessoa        p
 where t.status_transferencia    = 'N'
   and t.id_veiculo              = v.id
   and t.id_pessoa_compra        = p.id
   and upper(p.nome) like upper('%?%');

-- 6 - consultar histórico do veículo
select v.marca,
	   v.modelo,
	   v.ano,
	   v.cor,
       p1.nome as comprador,
       p1.cpf as cpfComprador,
       p2.nome as vendedor,
       p2.cpf as pcfVendedor,
       t.placa_atual,
       t.placa_anterior,
       t.data as dataTransferencia,
       case t.status_transferencia 
         when 'N' then 'Nova (atual)'
         when 'A' then 'Antiga' end as status
  from transferencia t
  join veiculo v on t.id_veiculo = v.id
  join pessoa p1 on t.id_pessoa_compra = p1.id
  left join pessoa p2 on t.id_pessoa_venda = p2.id
 where v.id = ?;
   
-- 7- qtd de veículos por marca
select upper(v.marca) as marca, 
	   count(*) as qtd
  from veiculo v
 group by upper(v.marca);

-- 8 - qtd veiculos transferidos por tempo
select count(*)
  from transferencia t
 where t.data between ? and ?;

-- 9 Consultar veículos com placa no modelo antigo
select v.marca,
	   v.modelo,
       v.ano,
       v.cor,
       t.placa_atual
  from transferencia t,
	   veiculo v
 where t.status_transferencia = 'N'
   and t.id_veiculo = v.id
   and substr(t.placa_atual,5,1) in ('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')
