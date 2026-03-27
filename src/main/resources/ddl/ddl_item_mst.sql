create table public.item_mst (
  item_id uuid not null
  , item character varying(900) not null
  , item_code character varying(60)
  , unit_price integer default 0
  , quantity integer default 0
  , unit character varying(40)
  , item_detail text
  , sales_tax character(10) default '10'
  , withholding_tax integer default 0
  , delete_flg character(1) default '0' not null
  , updated_at timestamp(6) without time zone not null
  , updated_staff_id uuid not null
  , primary key (item_id)
);
